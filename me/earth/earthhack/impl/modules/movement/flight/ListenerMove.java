//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.client.entity.*;

final class ListenerMove extends ModuleListener<Flight, MoveEvent>
{
    public ListenerMove(final Flight module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        final float forward = ListenerMove.mc.player.movementInput.field_192832_b;
        final float strafe = ListenerMove.mc.player.movementInput.moveStrafe;
        switch (((Flight)this.module).mode.getValue()) {
            case ConstantiamNew: {
                if (MovementUtil.isMoving()) {
                    switch (((Flight)this.module).constNewStage) {
                        case 0: {
                            if (ListenerMove.mc.player.onGround && ListenerMove.mc.player.isCollidedVertically) {
                                ((Flight)this.module).constMovementSpeed = 0.5 * ((Flight)this.module).speed.getValue();
                                break;
                            }
                            break;
                        }
                        case 1: {
                            if (ListenerMove.mc.player.onGround && ListenerMove.mc.player.isCollidedVertically) {
                                final double y = 0.4;
                                event.setY(ListenerMove.mc.player.motionY = y);
                            }
                            final Flight flight = (Flight)this.module;
                            flight.constMovementSpeed *= 2.149;
                            break;
                        }
                        case 2: {
                            ((Flight)this.module).constMovementSpeed = 1.3 * ((Flight)this.module).speed.getValue();
                            break;
                        }
                        default: {
                            ((Flight)this.module).constMovementSpeed = ((Flight)this.module).lastDist - ((Flight)this.module).lastDist / 159.0;
                            break;
                        }
                    }
                    MovementUtil.strafe(event, Math.max(((Flight)this.module).constMovementSpeed, MovementUtil.getSpeed()));
                    final Flight flight2 = (Flight)this.module;
                    ++flight2.constNewStage;
                    break;
                }
                break;
            }
            case ConstoHareFast: {
                if (forward == 0.0f && strafe == 0.0f) {
                    event.setX(0.0);
                    event.setZ(0.0);
                }
                if (((Flight)this.module).oHareLevel != 1 || (ListenerMove.mc.player.field_191988_bg == 0.0f && ListenerMove.mc.player.moveStrafing == 0.0f)) {
                    if (((Flight)this.module).oHareLevel == 2) {
                        ((Flight)this.module).oHareLevel = 3;
                        final Flight flight3 = (Flight)this.module;
                        flight3.oHareMoveSpeed *= 2.1499999;
                    }
                    else if (((Flight)this.module).oHareLevel == 3) {
                        ((Flight)this.module).oHareLevel = 4;
                        final double difference = ((ListenerMove.mc.player.ticksExisted % 2 == 0) ? 0.0103 : 0.0123) * (((Flight)this.module).oHareLastDist - MovementUtil.getSpeed());
                        ((Flight)this.module).oHareMoveSpeed = ((Flight)this.module).oHareLastDist - difference;
                    }
                    else {
                        if (ListenerMove.mc.world.getCollisionBoxes((Entity)ListenerMove.mc.player, ListenerMove.mc.player.getEntityBoundingBox().offset(0.0, ListenerMove.mc.player.motionY, 0.0)).size() > 0 || ListenerMove.mc.player.isCollidedVertically) {
                            ((Flight)this.module).oHareLevel = 1;
                        }
                        ((Flight)this.module).oHareMoveSpeed = ((Flight)this.module).oHareLastDist - ((Flight)this.module).oHareLastDist / 159.0;
                    }
                }
                else {
                    ((Flight)this.module).oHareLevel = 2;
                    final double boost = ListenerMove.mc.player.isPotionActive(MobEffects.SPEED) ? 1.56 : 2.034;
                    ((Flight)this.module).oHareMoveSpeed = boost * MovementUtil.getSpeed();
                }
                ((Flight)this.module).oHareMoveSpeed = Math.max(((Flight)this.module).oHareMoveSpeed, MovementUtil.getSpeed());
                MovementUtil.strafe(event, Math.max(((Flight)this.module).oHareMoveSpeed, MovementUtil.getSpeed()));
                break;
            }
            case Constantiam: {
                event.setX(event.getX() * ((Flight)this.module).speed.getValue());
                event.setZ(event.getZ() * ((Flight)this.module).speed.getValue());
                if (ListenerMove.mc.player.ticksExisted % 2 == 0) {
                    event.setY(0.00118212);
                }
                else {
                    event.setY(-0.00118212);
                }
                final Flight flight4 = (Flight)this.module;
                ++flight4.constantiamStage;
                break;
            }
            case Normal: {
                event.setX(event.getX() * ((Flight)this.module).speed.getValue());
                event.setZ(event.getZ() * ((Flight)this.module).speed.getValue());
                break;
            }
            case AAC: {
                if (!ListenerMove.mc.player.onGround && !PositionUtil.inLiquid()) {
                    MovementUtil.strafe(event, 0.4521096646785736);
                    break;
                }
                break;
            }
            case Creative: {
                final double speed = ((Flight)this.module).speed.getValue() / 10.0;
                if (ListenerMove.mc.player.movementInput.jump) {
                    event.setY(speed);
                    ListenerMove.mc.player.motionY = speed;
                }
                else if (ListenerMove.mc.player.movementInput.sneak) {
                    event.setY(-speed);
                    ListenerMove.mc.player.motionY = -speed;
                }
                else {
                    event.setY(0.0);
                    ListenerMove.mc.player.motionY = 0.0;
                    if (!ListenerMove.mc.player.isCollidedVertically && ((Flight)this.module).glide.getValue()) {
                        final EntityPlayerSP player = ListenerMove.mc.player;
                        player.motionY -= ((Flight)this.module).glideSpeed.getValue();
                        event.setY(ListenerMove.mc.player.motionY);
                    }
                }
                MovementUtil.strafe(event, speed);
                break;
            }
        }
    }
}
