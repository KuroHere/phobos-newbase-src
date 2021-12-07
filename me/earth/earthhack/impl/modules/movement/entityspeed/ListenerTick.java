//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entityspeed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.boatfly.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTick extends ModuleListener<EntitySpeed, TickEvent>
{
    private static final ModuleCache<BoatFly> BOAT_FLY;
    
    public ListenerTick(final EntitySpeed module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (!event.isSafe()) {
            return;
        }
        final Entity riding = ListenerTick.mc.player.getRidingEntity();
        if (riding == null) {
            return;
        }
        final double cosYaw = Math.cos(Math.toRadians(ListenerTick.mc.player.rotationYaw + 90.0f));
        final double sinYaw = Math.sin(Math.toRadians(ListenerTick.mc.player.rotationYaw + 90.0f));
        BlockPos pos = new BlockPos(ListenerTick.mc.player.posX + 2.0 * cosYaw + 0.0 * sinYaw, ListenerTick.mc.player.posY, ListenerTick.mc.player.posZ + (2.0 * sinYaw - 0.0 * cosYaw));
        final BlockPos down = new BlockPos(ListenerTick.mc.player.posX + 2.0 * cosYaw + 0.0 * sinYaw, ListenerTick.mc.player.posY - 1.0, ListenerTick.mc.player.posZ + (2.0 * sinYaw - 0.0 * cosYaw));
        if (!riding.onGround && !ListenerTick.mc.world.getBlockState(pos).getMaterial().blocksMovement() && !ListenerTick.mc.world.getBlockState(down).getMaterial().blocksMovement() && ((EntitySpeed)this.module).noStuck.getValue()) {
            EntitySpeed.strafe(0.0);
            ((EntitySpeed)this.module).stuckTimer.reset();
            return;
        }
        pos = new BlockPos(ListenerTick.mc.player.posX + 2.0 * cosYaw + 0.0 * sinYaw, ListenerTick.mc.player.posY, ListenerTick.mc.player.posZ + (2.0 * sinYaw - 0.0 * cosYaw));
        if (ListenerTick.mc.world.getBlockState(pos).getMaterial().blocksMovement() && ((EntitySpeed)this.module).noStuck.getValue()) {
            EntitySpeed.strafe(0.0);
            ((EntitySpeed)this.module).stuckTimer.reset();
            return;
        }
        pos = new BlockPos(ListenerTick.mc.player.posX + cosYaw + 0.0 * sinYaw, ListenerTick.mc.player.posY + 1.0, ListenerTick.mc.player.posZ + (sinYaw - 0.0 * cosYaw));
        if (ListenerTick.mc.world.getBlockState(pos).getMaterial().blocksMovement() && ((EntitySpeed)this.module).noStuck.getValue()) {
            EntitySpeed.strafe(0.0);
            ((EntitySpeed)this.module).stuckTimer.reset();
            return;
        }
        if (ListenerTick.mc.player.movementInput.jump) {
            ((EntitySpeed)this.module).jumpTimer.reset();
        }
        if (((EntitySpeed)this.module).stuckTimer.passed(((EntitySpeed)this.module).stuckTime.getValue()) || !((EntitySpeed)this.module).noStuck.getValue()) {
            if (!riding.isInWater() && !ListenerTick.BOAT_FLY.isEnabled() && !ListenerTick.mc.player.movementInput.jump && ((EntitySpeed)this.module).jumpTimer.passed(1000L) && !PositionUtil.inLiquid()) {
                if (riding.onGround) {
                    riding.motionY = 0.4;
                }
                riding.motionY = -0.4;
            }
            EntitySpeed.strafe(((EntitySpeed)this.module).speed.getValue());
            if (((EntitySpeed)this.module).resetStuck.getValue()) {
                ((EntitySpeed)this.module).stuckTimer.reset();
            }
        }
    }
    
    static {
        BOAT_FLY = Caches.getModule(BoatFly.class);
    }
}
