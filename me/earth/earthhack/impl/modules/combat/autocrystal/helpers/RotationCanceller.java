//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.modules.*;

public class RotationCanceller implements Globals
{
    private static final ModuleCache<PacketFly> PACKETFLY;
    private final StopWatch timer;
    private final Setting<Integer> maxCancel;
    private final AutoCrystal module;
    private volatile CPacketPlayer last;
    
    public RotationCanceller(final AutoCrystal module, final Setting<Integer> maxCancel) {
        this.timer = new StopWatch();
        this.module = module;
        this.maxCancel = maxCancel;
    }
    
    public void onGameLoop() {
        if (this.last != null && this.timer.passed(this.maxCancel.getValue())) {
            this.sendLast();
        }
    }
    
    public synchronized void onPacket(final PacketEvent.Send<? extends CPacketPlayer> event) {
        if (event.isCancelled() || RotationCanceller.PACKETFLY.isEnabled()) {
            return;
        }
        this.reset();
        if (Managers.ROTATION.isBlocking()) {
            return;
        }
        event.setCancelled(true);
        this.last = event.getPacket();
        this.timer.reset();
    }
    
    public synchronized boolean setRotations(final RotationFunction function) {
        if (this.last == null) {
            return false;
        }
        final double x = this.last.getX(Managers.POSITION.getX());
        final double y = this.last.getX(Managers.POSITION.getY());
        final double z = this.last.getX(Managers.POSITION.getZ());
        final float yaw = Managers.ROTATION.getServerYaw();
        final float pitch = Managers.ROTATION.getServerPitch();
        final boolean onGround = this.last.isOnGround();
        final ICPacketPlayer accessor = (ICPacketPlayer)this.last;
        final float[] r = function.apply(x, y, z, yaw, pitch);
        if (r[0] - yaw != 0.0 && r[1] - pitch != 0.0) {
            if (accessor.isRotating()) {
                accessor.setYaw(r[0]);
                accessor.setPitch(r[1]);
                this.sendLast();
            }
            else if (accessor.isMoving()) {
                this.last = PacketUtil.positionRotation(x, y, z, r[0], r[1], onGround);
                this.sendLast();
            }
            else {
                this.last = PacketUtil.rotation(r[0], r[1], onGround);
                this.sendLast();
            }
            return true;
        }
        if (!accessor.isRotating() && !accessor.isMoving() && onGround == Managers.POSITION.isOnGround()) {
            this.last = null;
            return true;
        }
        this.sendLast();
        return true;
    }
    
    public void reset() {
        if (this.last != null && RotationCanceller.mc.player != null) {
            this.sendLast();
        }
    }
    
    public synchronized void drop() {
        this.last = null;
    }
    
    private synchronized void sendLast() {
        final CPacketPlayer packet = this.last;
        if (packet != null && RotationCanceller.mc.player != null) {
            NetworkUtil.sendPacketNoEvent((Packet<?>)packet);
            this.module.runPost();
        }
        this.last = null;
    }
    
    static {
        PACKETFLY = Caches.getModule(PacketFly.class);
    }
}
