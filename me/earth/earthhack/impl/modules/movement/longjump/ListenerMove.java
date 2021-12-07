//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.longjump.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;

final class ListenerMove extends ModuleListener<LongJump, MoveEvent>
{
    public ListenerMove(final LongJump module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((LongJump)this.module).mode.getValue() == JumpMode.Normal) {
            if (ListenerMove.mc.player.moveStrafing <= 0.0f && ListenerMove.mc.player.field_191988_bg <= 0.0f) {
                ((LongJump)this.module).stage = 1;
            }
            if (MathUtil.round(ListenerMove.mc.player.posY - (int)ListenerMove.mc.player.posY, 3) == MathUtil.round(0.943, 3)) {
                final EntityPlayerSP player = ListenerMove.mc.player;
                player.motionY -= 0.03;
                event.setY(event.getY() - 0.03);
            }
            if (((LongJump)this.module).stage == 1 && MovementUtil.isMoving()) {
                ((LongJump)this.module).stage = 2;
                ((LongJump)this.module).speed = ((LongJump)this.module).boost.getValue() * MovementUtil.getSpeed() - 0.01;
            }
            else if (((LongJump)this.module).stage == 2) {
                ((LongJump)this.module).stage = 3;
                event.setY(ListenerMove.mc.player.motionY = 0.424);
                ((LongJump)this.module).speed *= 2.149802;
            }
            else if (((LongJump)this.module).stage == 3) {
                ((LongJump)this.module).stage = 4;
                final double difference = 0.66 * (((LongJump)this.module).distance - MovementUtil.getSpeed());
                ((LongJump)this.module).speed = ((LongJump)this.module).distance - difference;
            }
            else {
                if (ListenerMove.mc.world.getCollisionBoxes((Entity)ListenerMove.mc.player, ListenerMove.mc.player.getEntityBoundingBox().offset(0.0, ListenerMove.mc.player.motionY, 0.0)).size() > 0 || ListenerMove.mc.player.isCollidedVertically) {
                    ((LongJump)this.module).stage = 1;
                }
                ((LongJump)this.module).speed = ((LongJump)this.module).distance - ((LongJump)this.module).distance / 159.0;
            }
            MovementUtil.strafe(event, ((LongJump)this.module).speed = Math.max(((LongJump)this.module).speed, MovementUtil.getSpeed()));
            float moveForward = ListenerMove.mc.player.movementInput.field_192832_b;
            float moveStrafe = ListenerMove.mc.player.movementInput.moveStrafe;
            float rotationYaw = ListenerMove.mc.player.rotationYaw;
            if (moveForward == 0.0f && moveStrafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
            }
            else if (moveForward != 0.0f) {
                if (moveStrafe >= 1.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                    moveStrafe = 0.0f;
                }
                else if (moveStrafe <= -1.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                    moveStrafe = 0.0f;
                }
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }
            final double cos = Math.cos(Math.toRadians(rotationYaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0f));
            event.setX(moveForward * ((LongJump)this.module).speed * cos + moveStrafe * ((LongJump)this.module).speed * sin);
            event.setZ(moveForward * ((LongJump)this.module).speed * sin - moveStrafe * ((LongJump)this.module).speed * cos);
        }
    }
}
