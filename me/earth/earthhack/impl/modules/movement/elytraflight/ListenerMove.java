//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.elytraflight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;

final class ListenerMove extends ModuleListener<ElytraFlight, MoveEvent>
{
    public ListenerMove(final ElytraFlight module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        final ItemStack stack = ListenerMove.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (stack.getItem() == Items.ELYTRA && ItemElytra.isBroken(stack)) {
            switch (((ElytraFlight)this.module).mode.getValue()) {
                case Wasp: {
                    if (!ListenerMove.mc.player.isElytraFlying()) {
                        return;
                    }
                    final double vSpeed = ListenerMove.mc.gameSettings.keyBindJump.isKeyDown() ? ((ElytraFlight)this.module).vSpeed.getValue() : (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown() ? (-((ElytraFlight)this.module).vSpeed.getValue()) : 0.0);
                    event.setY(vSpeed);
                    ListenerMove.mc.player.setVelocity(0.0, 0.0, 0.0);
                    ListenerMove.mc.player.motionY = vSpeed;
                    ListenerMove.mc.player.moveForward = (float)vSpeed;
                    if (MovementUtil.noMovementKeys() && !ListenerMove.mc.gameSettings.keyBindJump.isKeyDown() && !ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        event.setX(0.0);
                        event.setY(0.0);
                        event.setY(((boolean)((ElytraFlight)this.module).antiKick.getValue()) ? ((double)(-((ElytraFlight)this.module).glide.getValue())) : 0.0);
                        return;
                    }
                    MovementUtil.strafe(event, ((ElytraFlight)this.module).hSpeed.getValue());
                    break;
                }
                case Packet: {
                    if (!ListenerMove.mc.player.onGround || !((ElytraFlight)this.module).noGround.getValue()) {
                        if (((ElytraFlight)this.module).accel.getValue()) {
                            if (((ElytraFlight)this.module).lag) {
                                ((ElytraFlight)this.module).speed = 1.0;
                                ((ElytraFlight)this.module).lag = false;
                            }
                            if (((ElytraFlight)this.module).speed < ((ElytraFlight)this.module).hSpeed.getValue()) {
                                final ElytraFlight elytraFlight = (ElytraFlight)this.module;
                                elytraFlight.speed += 0.1;
                            }
                            if (((ElytraFlight)this.module).speed - 0.1 > ((ElytraFlight)this.module).hSpeed.getValue()) {
                                final ElytraFlight elytraFlight2 = (ElytraFlight)this.module;
                                elytraFlight2.speed -= 0.1;
                            }
                        }
                        else {
                            ((ElytraFlight)this.module).speed = ((ElytraFlight)this.module).hSpeed.getValue();
                        }
                        if (!MovementUtil.anyMovementKeys() && !ListenerMove.mc.player.isCollided && ((ElytraFlight)this.module).antiKick.getValue()) {
                            if (((ElytraFlight)this.module).timer.passed(1000L)) {
                                ((ElytraFlight)this.module).lag = true;
                                final EntityPlayerSP player = ListenerMove.mc.player;
                                player.motionX += 0.03 * Math.sin(Math.toRadians(++((ElytraFlight)this.module).kick * 4));
                                final EntityPlayerSP player2 = ListenerMove.mc.player;
                                player2.motionZ += 0.03 * Math.cos(Math.toRadians(((ElytraFlight)this.module).kick * 4));
                            }
                        }
                        else {
                            ((ElytraFlight)this.module).timer.reset();
                            ((ElytraFlight)this.module).lag = false;
                        }
                        if (((ElytraFlight)this.module).vertical.getValue() && ListenerMove.mc.player.movementInput.jump) {
                            ListenerMove.mc.player.motionY = ((ElytraFlight)this.module).vSpeed.getValue();
                            event.setY(((ElytraFlight)this.module).vSpeed.getValue());
                        }
                        else if (ListenerMove.mc.player.movementInput.sneak) {
                            ListenerMove.mc.player.motionY = -((ElytraFlight)this.module).vSpeed.getValue();
                            event.setY(-((ElytraFlight)this.module).vSpeed.getValue());
                        }
                        else if (((ElytraFlight)this.module).ncp.getValue()) {
                            if (ListenerMove.mc.player.ticksExisted % 32 != 0 || ((ElytraFlight)this.module).lag || (Math.abs(event.getX()) < 0.05 && Math.abs(event.getZ()) < 0.05)) {
                                event.setY(ListenerMove.mc.player.motionY = -2.0E-4);
                            }
                            else {
                                ((ElytraFlight)this.module).speed -= ((ElytraFlight)this.module).speed / 2.0 * 0.1;
                                ListenerMove.mc.player.motionY = -2.0E-4;
                                event.setY(0.006200000000000001);
                            }
                        }
                        else {
                            event.setY(ListenerMove.mc.player.motionY = 0.0);
                        }
                        event.setX(event.getX() * (((ElytraFlight)this.module).lag ? 0.5 : ((ElytraFlight)this.module).speed));
                        event.setZ(event.getZ() * (((ElytraFlight)this.module).lag ? 0.5 : ((ElytraFlight)this.module).speed));
                        break;
                    }
                    break;
                }
                case Boost: {
                    if (ListenerMove.mc.player.isElytraFlying() && ((ElytraFlight)this.module).noWater.getValue() && ListenerMove.mc.player.isInWater()) {
                        return;
                    }
                    if (ListenerMove.mc.player.movementInput.jump && ListenerMove.mc.player.isElytraFlying()) {
                        final float yaw = ListenerMove.mc.player.rotationYaw * 0.017453292f;
                        final EntityPlayerSP player3 = ListenerMove.mc.player;
                        player3.motionX -= MathHelper.sin(yaw) * 0.15f;
                        final EntityPlayerSP player4 = ListenerMove.mc.player;
                        player4.motionZ += MathHelper.cos(yaw) * 0.15f;
                        break;
                    }
                    break;
                }
                case Control: {
                    if (!ListenerMove.mc.player.isElytraFlying()) {
                        break;
                    }
                    if (!ListenerMove.mc.player.movementInput.forwardKeyDown && !ListenerMove.mc.player.movementInput.sneak) {
                        ListenerMove.mc.player.setVelocity(0.0, 0.0, 0.0);
                        break;
                    }
                    if (ListenerMove.mc.player.movementInput.forwardKeyDown && (((ElytraFlight)this.module).vertical.getValue() || ListenerMove.mc.player.prevRotationPitch > 0.0f)) {
                        final float yaw = (float)Math.toRadians(ListenerMove.mc.player.rotationYaw);
                        final double speed = ((ElytraFlight)this.module).hSpeed.getValue() / 10.0;
                        ListenerMove.mc.player.motionX = MathHelper.sin(yaw) * -speed;
                        ListenerMove.mc.player.motionZ = MathHelper.cos(yaw) * speed;
                        break;
                    }
                    break;
                }
                case Normal: {
                    if (ListenerMove.mc.player.isElytraFlying() && ((ElytraFlight)this.module).noWater.getValue() && ListenerMove.mc.player.isInWater()) {
                        return;
                    }
                    if (ListenerMove.mc.player.movementInput.jump || (!ListenerMove.mc.inGameHasFocus && ListenerMove.mc.player.isElytraFlying())) {
                        event.setY(0.0);
                    }
                    if (ListenerMove.mc.inGameHasFocus && ((ElytraFlight)this.module).instant.getValue() && ListenerMove.mc.player.movementInput.jump && !ListenerMove.mc.player.isElytraFlying() && ((ElytraFlight)this.module).timer.passed(1000L)) {
                        ListenerMove.mc.player.setJumping(false);
                        ListenerMove.mc.player.setSprinting(true);
                        ListenerMove.mc.player.jump();
                        ((ElytraFlight)this.module).sendFallPacket();
                        ((ElytraFlight)this.module).timer.reset();
                        return;
                    }
                    break;
                }
            }
        }
    }
}
