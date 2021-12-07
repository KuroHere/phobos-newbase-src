//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.network.*;

public class Confirmer extends SubscriberImpl
{
    private final StopWatch placeTimer;
    private final StopWatch breakTimer;
    private BlockPos current;
    private AxisAlignedBB bb;
    private boolean placeConfirmed;
    private boolean breakConfirmed;
    private boolean newVer;
    private boolean valid;
    private int placeTime;
    
    public Confirmer() {
        this.placeTimer = new StopWatch();
        this.breakTimer = new StopWatch();
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnObject.class, e -> {
            final SPacketSpawnObject p = (SPacketSpawnObject)e.getPacket();
            if (p.getType() == 51) {
                this.confirmPlace(p.getX(), p.getY(), p.getZ());
            }
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSoundEffect.class, e -> {
            final SPacketSoundEffect p2 = (SPacketSoundEffect)e.getPacket();
            if (p2.getCategory() == SoundCategory.BLOCKS && p2.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                this.confirmBreak(p2.getX(), p2.getY(), p2.getZ());
            }
        }));
    }
    
    public void setPos(final BlockPos pos, final boolean newVer, final int placeTime) {
        this.newVer = newVer;
        if (pos == null) {
            this.current = null;
            this.valid = false;
        }
        else {
            final BlockPos crystalPos = new BlockPos((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
            this.current = crystalPos;
            this.bb = this.createBB(crystalPos, newVer);
            this.valid = true;
            this.placeConfirmed = false;
            this.breakConfirmed = false;
            this.placeTime = ((placeTime < 50) ? 0 : placeTime);
            this.placeTimer.reset();
        }
    }
    
    public void confirmPlace(final double x, final double y, final double z) {
        if (this.valid && !this.placeConfirmed) {
            final BlockPos p = new BlockPos(x, y, z);
            if (p.equals((Object)this.current)) {
                this.placeConfirmed = true;
                this.breakTimer.reset();
            }
            else if (this.placeTimer.passed(this.placeTime)) {
                final AxisAlignedBB currentBB = this.bb;
                if (currentBB != null && currentBB.intersectsWith(this.createBB(x, y, z, this.newVer))) {
                    this.valid = false;
                }
            }
        }
    }
    
    public void confirmBreak(final double x, final double y, final double z) {
        if (this.valid && !this.breakConfirmed && this.placeConfirmed) {
            final BlockPos current = this.current;
            if (current != null && current.distanceSq(x, y, z) < 144.0) {
                if (current.equals((Object)new BlockPos(x, y, z))) {
                    this.breakConfirmed = true;
                }
                else {
                    this.valid = false;
                }
            }
        }
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public boolean isPlaceConfirmed(final int placeConfirm) {
        if (!this.placeConfirmed && this.placeTimer.passed(placeConfirm)) {
            return this.valid = false;
        }
        return this.placeConfirmed && this.valid;
    }
    
    public boolean isBreakConfirmed(final int breakConfirm) {
        if (this.placeConfirmed && !this.breakConfirmed && this.breakTimer.passed(breakConfirm)) {
            return this.valid = false;
        }
        return this.breakConfirmed && this.valid;
    }
    
    private AxisAlignedBB createBB(final BlockPos crystalPos, final boolean newVer) {
        return this.createBB(crystalPos.getX() + 0.5f, crystalPos.getY(), crystalPos.getZ() + 0.5f, newVer);
    }
    
    private AxisAlignedBB createBB(final double x, final double y, final double z, final boolean newVer) {
        return new AxisAlignedBB(x - 1.0, y, z - 1.0, x + 1.0, y + (newVer ? 1 : 2), z + 1.0);
    }
    
    public static Confirmer createAndSubscribe(final EventBus bus) {
        final Confirmer confirmer = new Confirmer();
        bus.subscribe(confirmer);
        return confirmer;
    }
}
