//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.jesus;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.jesus.mode.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;

final class ListenerMotion extends ModuleListener<Jesus, MotionUpdateEvent>
{
    public ListenerMotion(final Jesus module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (ListenerMotion.mc.player.isDead || ListenerMotion.mc.player.isSneaking() || !((Jesus)this.module).timer.passed(800L)) {
            return;
        }
        switch (((Jesus)this.module).mode.getValue()) {
            case Dolphin: {
                if (PositionUtil.inLiquid() && ListenerMotion.mc.player.fallDistance < 3.0f && !ListenerMotion.mc.player.isSneaking()) {
                    ListenerMotion.mc.player.motionY = 0.1;
                }
                return;
            }
            case Trampoline: {
                if (event.getStage() != Stage.PRE) {
                    break;
                }
                if (PositionUtil.inLiquid(false) && !ListenerMotion.mc.player.isSneaking()) {
                    ListenerMotion.mc.player.onGround = false;
                }
                final Block block = ListenerMotion.mc.world.getBlockState(new BlockPos(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ)).getBlock();
                if (((Jesus)this.module).jumped && !ListenerMotion.mc.player.capabilities.isFlying && !ListenerMotion.mc.player.isInWater()) {
                    if (ListenerMotion.mc.player.motionY < -0.3 || ListenerMotion.mc.player.onGround || ListenerMotion.mc.player.isOnLadder()) {
                        ((Jesus)this.module).jumped = false;
                        return;
                    }
                    ListenerMotion.mc.player.motionY = ListenerMotion.mc.player.motionY / 0.9800000190734863 + 0.08;
                    final EntityPlayerSP player = ListenerMotion.mc.player;
                    player.motionY -= 0.03120000000005;
                }
                if (ListenerMotion.mc.player.isInWater() || ListenerMotion.mc.player.isInLava()) {
                    ListenerMotion.mc.player.motionY = 0.1;
                    break;
                }
                if (!ListenerMotion.mc.player.isInLava() && block instanceof BlockLiquid && ListenerMotion.mc.player.motionY < 0.2) {
                    ListenerMotion.mc.player.motionY = 0.5;
                    ((Jesus)this.module).jumped = true;
                }
                break;
            }
        }
        if (event.getStage() == Stage.PRE && !PositionUtil.inLiquid() && PositionUtil.inLiquid(true) && !PositionUtil.isMovementBlocked() && ListenerMotion.mc.player.ticksExisted % 2 == 0) {
            event.setY(event.getY() + 0.02);
        }
    }
}
