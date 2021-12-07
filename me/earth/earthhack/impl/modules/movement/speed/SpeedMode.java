//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.client.entity.*;

public enum SpeedMode implements Globals
{
    Instant {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (SpeedMode$1.mc.player.isElytraFlying()) {
                return;
            }
            if (module.LONG_JUMP.isEnabled()) {
                return;
            }
            if (!module.noWaterInstant.getValue() || (!SpeedMode$1.mc.player.isInWater() && !SpeedMode$1.mc.player.isInLava())) {
                MovementUtil.strafe(event, MovementUtil.getSpeed(module.slow.getValue()));
            }
        }
    }, 
    OldGround {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            SpeedMode$2.OnGround.move(event, module);
        }
    }, 
    OnGround {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (SpeedMode$3.mc.player.onGround || module.onGroundStage == 3) {
                if ((!SpeedMode$3.mc.player.isCollidedHorizontally && SpeedMode$3.mc.player.field_191988_bg != 0.0f) || SpeedMode$3.mc.player.moveStrafing != 0.0f) {
                    if (module.onGroundStage == 2) {
                        module.speed *= 2.149;
                        module.onGroundStage = 3;
                    }
                    else if (module.onGroundStage == 3) {
                        module.onGroundStage = 2;
                        module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
                    }
                    else if (PositionUtil.isBoxColliding() || SpeedMode$3.mc.player.isCollidedVertically) {
                        module.onGroundStage = 1;
                    }
                }
                module.speed = Math.min(module.speed, module.getCap());
                MovementUtil.strafe(event, module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue())));
            }
        }
    }, 
    Vanilla {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            MovementUtil.strafe(event, module.speedSet.getValue() / 10.0);
        }
    }, 
    NCP {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (SpeedMode$5.mc.player.isElytraFlying()) {
                return;
            }
            if (module.LONG_JUMP.isEnabled()) {
                return;
            }
            switch (module.ncpStage) {
                case 0: {
                    ++module.ncpStage;
                    module.lastDist = 0.0;
                    break;
                }
                case 2: {
                    if ((SpeedMode$5.mc.player.field_191988_bg != 0.0f || SpeedMode$5.mc.player.moveStrafing != 0.0f) && SpeedMode$5.mc.player.onGround) {
                        event.setY(SpeedMode$5.mc.player.motionY = (PositionUtil.isBoxColliding() ? 0.2 : 0.3999) + MovementUtil.getJumpSpeed());
                        module.speed *= 2.149;
                        break;
                    }
                    break;
                }
                case 3: {
                    module.speed = module.lastDist - 0.7095 * (module.lastDist - MovementUtil.getSpeed(module.slow.getValue()));
                    break;
                }
                default: {
                    if ((SpeedMode$5.mc.world.getCollisionBoxes((Entity)null, SpeedMode$5.mc.player.getEntityBoundingBox().offset(0.0, SpeedMode$5.mc.player.motionY, 0.0)).size() > 0 || SpeedMode$5.mc.player.isCollidedVertically) && module.ncpStage > 0) {
                        module.ncpStage = ((SpeedMode$5.mc.player.field_191988_bg != 0.0f || SpeedMode$5.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    module.speed = module.lastDist - module.lastDist / 159.0;
                    break;
                }
            }
            module.speed = Math.min(module.speed, module.getCap());
            MovementUtil.strafe(event, module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue())));
            ++module.ncpStage;
        }
    }, 
    Strafe {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!MovementUtil.isMoving()) {
                return;
            }
            if (SpeedMode$6.mc.player.isElytraFlying()) {
                return;
            }
            if (module.LONG_JUMP.isEnabled()) {
                return;
            }
            if (!Managers.NCP.passed(module.lagTime.getValue())) {
                return;
            }
            if (module.useTimer.getValue() && Managers.NCP.passed(250)) {
                Managers.TIMER.setTimer(1.0888f);
            }
            if (module.stage == 1 && MovementUtil.isMoving()) {
                module.speed = 1.35 * MovementUtil.getSpeed(module.slow.getValue(), module.strafeSpeed.getValue()) - 0.01;
            }
            else if (module.stage == 2 && MovementUtil.isMoving()) {
                final double yMotion = 0.3999 + MovementUtil.getJumpSpeed();
                event.setY(SpeedMode$6.mc.player.motionY = yMotion);
                module.speed *= (module.boost ? 1.6835 : 1.395);
            }
            else if (module.stage == 3) {
                module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue(), module.strafeSpeed.getValue()));
                module.boost = !module.boost;
            }
            else {
                if ((SpeedMode$6.mc.world.getCollisionBoxes((Entity)null, SpeedMode$6.mc.player.getEntityBoundingBox().offset(0.0, SpeedMode$6.mc.player.motionY, 0.0)).size() > 0 || SpeedMode$6.mc.player.isCollidedVertically) && module.stage > 0) {
                    module.stage = (MovementUtil.isMoving() ? 1 : 0);
                }
                module.speed = module.distance - module.distance / 159.0;
            }
            module.speed = Math.min(module.speed, module.getCap());
            MovementUtil.strafe(event, module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue(), module.strafeSpeed.getValue())));
            if (MovementUtil.isMoving()) {
                ++module.stage;
            }
        }
    }, 
    GayHop {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!Managers.NCP.passed(100)) {
                module.gayStage = 1;
                return;
            }
            if (!MovementUtil.isMoving()) {
                module.speed = MovementUtil.getSpeed(module.slow.getValue());
            }
            if (module.gayStage == 1 && SpeedMode$7.mc.player.isCollidedVertically && MovementUtil.isMoving()) {
                module.speed = 0.25 + MovementUtil.getSpeed(module.slow.getValue()) - 0.01;
            }
            else if (module.gayStage == 2 && SpeedMode$7.mc.player.isCollidedVertically && MovementUtil.isMoving()) {
                final double yMotion = (PositionUtil.isBoxColliding() ? 0.2 : 0.4) + MovementUtil.getJumpSpeed();
                event.setY(SpeedMode$7.mc.player.motionY = yMotion);
                module.speed *= 2.149;
            }
            else if (module.gayStage == 3) {
                module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
            }
            else {
                if (SpeedMode$7.mc.player.onGround && module.gayStage > 0) {
                    if (1.35 * MovementUtil.getSpeed(module.slow.getValue()) - 0.01 > module.speed) {
                        module.gayStage = 0;
                    }
                    else {
                        module.gayStage = (MovementUtil.isMoving() ? 1 : 0);
                    }
                }
                module.speed = module.distance - module.distance / 159.0;
            }
            module.speed = Math.min(module.speed, module.getCap());
            module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue()));
            if (module.gayStage > 0) {
                MovementUtil.strafe(event, module.speed);
            }
            if (MovementUtil.isMoving()) {
                ++module.gayStage;
            }
        }
    }, 
    Bhop {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!Managers.NCP.passed(100)) {
                module.bhopStage = 4;
                return;
            }
            if (MathUtil.round(SpeedMode$8.mc.player.posY - (int)SpeedMode$8.mc.player.posY, 3) == MathUtil.round(0.138, 3)) {
                final EntityPlayerSP player = SpeedMode$8.mc.player;
                player.motionY -= 0.08 + MovementUtil.getJumpSpeed();
                event.setY(event.getY() - (0.0931 + MovementUtil.getJumpSpeed()));
                final EntityPlayerSP player2 = SpeedMode$8.mc.player;
                player2.posY -= 0.0931 + MovementUtil.getJumpSpeed();
            }
            if (module.bhopStage != 2.0 || !MovementUtil.isMoving()) {
                if (module.bhopStage == 3.0) {
                    module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
                }
                else {
                    if (SpeedMode$8.mc.player.onGround) {
                        module.bhopStage = 1;
                    }
                    module.speed = module.distance - module.distance / 159.0;
                }
            }
            else {
                final double yMotion = (PositionUtil.isBoxColliding() ? 0.2 : 0.4) + MovementUtil.getJumpSpeed();
                event.setY(SpeedMode$8.mc.player.motionY = yMotion);
                module.speed *= 2.149;
            }
            module.speed = Math.min(module.speed, module.getCap());
            MovementUtil.strafe(event, module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue())));
            ++module.bhopStage;
        }
    }, 
    VHop {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!Managers.NCP.passed(100)) {
                module.vStage = 1;
                return;
            }
            if (!MovementUtil.isMoving()) {
                module.speed = MovementUtil.getSpeed(module.slow.getValue());
            }
            if (MathUtil.round(SpeedMode$9.mc.player.posY - (int)SpeedMode$9.mc.player.posY, 3) == MathUtil.round(0.4, 3)) {
                event.setY(SpeedMode$9.mc.player.motionY = 0.31 + MovementUtil.getJumpSpeed());
            }
            else if (MathUtil.round(SpeedMode$9.mc.player.posY - (int)SpeedMode$9.mc.player.posY, 3) == MathUtil.round(0.71, 3)) {
                event.setY(SpeedMode$9.mc.player.motionY = 0.04 + MovementUtil.getJumpSpeed());
            }
            else if (MathUtil.round(SpeedMode$9.mc.player.posY - (int)SpeedMode$9.mc.player.posY, 3) == MathUtil.round(0.75, 3)) {
                event.setY(SpeedMode$9.mc.player.motionY = -0.2 + MovementUtil.getJumpSpeed());
            }
            if (SpeedMode$9.mc.world.getCollisionBoxes((Entity)null, SpeedMode$9.mc.player.getEntityBoundingBox().offset(0.0, -0.56, 0.0)).size() > 0 && MathUtil.round(SpeedMode$9.mc.player.posY - (int)SpeedMode$9.mc.player.posY, 3) == MathUtil.round(0.55, 3)) {
                event.setY(SpeedMode$9.mc.player.motionY = -0.14 + MovementUtil.getJumpSpeed());
            }
            if (module.vStage != 1 || !SpeedMode$9.mc.player.isCollidedVertically || !MovementUtil.isMoving()) {
                if (module.vStage != 2 || !SpeedMode$9.mc.player.isCollidedVertically || !MovementUtil.isMoving()) {
                    if (module.vStage == 3) {
                        module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
                    }
                    else {
                        if (SpeedMode$9.mc.player.onGround && module.vStage > 0) {
                            if (1.35 * MovementUtil.getSpeed(module.slow.getValue()) - 0.01 > module.speed) {
                                module.vStage = 0;
                            }
                            else {
                                module.vStage = (MovementUtil.isMoving() ? 1 : 0);
                            }
                        }
                        module.speed = module.distance - module.distance / 159.0;
                    }
                }
                else {
                    event.setY(SpeedMode$9.mc.player.motionY = (PositionUtil.isBoxColliding() ? 0.2 : 0.4) + MovementUtil.getJumpSpeed());
                    module.speed *= 2.149;
                }
            }
            else {
                module.speed = 2.0 * MovementUtil.getSpeed(module.slow.getValue()) - 0.01;
            }
            if (module.vStage > 8) {
                module.speed = MovementUtil.getSpeed(module.slow.getValue());
            }
            module.speed = Math.min(module.speed, module.getCap());
            module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue()));
            if (module.vStage > 0) {
                MovementUtil.strafe(event, module.speed);
            }
            if (MovementUtil.isMoving()) {
                ++module.vStage;
            }
        }
    }, 
    LowHop {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!Managers.NCP.passed(100)) {
                return;
            }
            if (module.useTimer.getValue() && Managers.NCP.passed(250)) {
                Managers.TIMER.setTimer(1.0888f);
            }
            if (!SpeedMode$10.mc.player.isCollidedHorizontally) {
                if (MathUtil.round(SpeedMode$10.mc.player.posY - (int)SpeedMode$10.mc.player.posY, 3) == MathUtil.round(0.4, 3)) {
                    event.setY(SpeedMode$10.mc.player.motionY = 0.31 + MovementUtil.getJumpSpeed());
                }
                else if (MathUtil.round(SpeedMode$10.mc.player.posY - (int)SpeedMode$10.mc.player.posY, 3) == MathUtil.round(0.71, 3)) {
                    event.setY(SpeedMode$10.mc.player.motionY = 0.04 + MovementUtil.getJumpSpeed());
                }
                else if (MathUtil.round(SpeedMode$10.mc.player.posY - (int)SpeedMode$10.mc.player.posY, 3) == MathUtil.round(0.75, 3)) {
                    event.setY(SpeedMode$10.mc.player.motionY = -0.2 - MovementUtil.getJumpSpeed());
                }
                else if (MathUtil.round(SpeedMode$10.mc.player.posY - (int)SpeedMode$10.mc.player.posY, 3) == MathUtil.round(0.55, 3)) {
                    event.setY(SpeedMode$10.mc.player.motionY = -0.14 + MovementUtil.getJumpSpeed());
                }
                else if (MathUtil.round(SpeedMode$10.mc.player.posY - (int)SpeedMode$10.mc.player.posY, 3) == MathUtil.round(0.41, 3)) {
                    event.setY(SpeedMode$10.mc.player.motionY = -0.2 + MovementUtil.getJumpSpeed());
                }
            }
            if (module.lowStage == 1 && MovementUtil.isMoving()) {
                module.speed = 1.35 * MovementUtil.getSpeed(module.slow.getValue()) - 0.01;
            }
            else if (module.lowStage == 2 && MovementUtil.isMoving()) {
                event.setY(SpeedMode$10.mc.player.motionY = (PositionUtil.isBoxColliding() ? 0.2 : 0.3999) + MovementUtil.getJumpSpeed());
                module.speed *= (module.boost ? 1.5685 : 1.3445);
            }
            else if (module.lowStage == 3) {
                module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
                module.boost = !module.boost;
            }
            else {
                if (SpeedMode$10.mc.player.onGround && module.lowStage > 0) {
                    module.lowStage = (MovementUtil.isMoving() ? 1 : 0);
                }
                module.speed = module.distance - module.distance / 159.0;
            }
            module.speed = Math.min(module.speed, module.getCap());
            MovementUtil.strafe(event, module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue())));
            if (MovementUtil.isMoving()) {
                ++module.lowStage;
            }
        }
    }, 
    Constantiam {
        @Override
        public void move(final MoveEvent event, final Speed module) {
            if (!Managers.NCP.passed(100)) {
                module.constStage = 0;
                return;
            }
            if (!MovementUtil.isMoving()) {
                module.speed = MovementUtil.getSpeed(module.slow.getValue());
            }
            if (module.constStage == 0 && MovementUtil.isMoving() && SpeedMode$11.mc.player.onGround) {
                module.speed = 0.08;
            }
            else if (module.constStage == 1 && SpeedMode$11.mc.player.isCollidedVertically && MovementUtil.isMoving()) {
                module.speed = 0.25 + MovementUtil.getSpeed(module.slow.getValue()) - 0.01;
            }
            else if (module.constStage == 2 && SpeedMode$11.mc.player.isCollidedVertically && MovementUtil.isMoving()) {
                final double yMotion = (PositionUtil.isBoxColliding() ? 0.2 : 0.4) + MovementUtil.getJumpSpeed();
                event.setY(SpeedMode$11.mc.player.motionY = yMotion);
                module.speed *= module.constFactor.getValue();
            }
            else if (module.constStage == 3) {
                module.speed = module.distance - 0.66 * (module.distance - MovementUtil.getSpeed(module.slow.getValue()));
            }
            else {
                if (SpeedMode$11.mc.player.onGround && module.constStage > 0) {
                    module.constStage = 0;
                }
                if (!SpeedMode$11.mc.player.onGround && module.constStage > module.constOff.getValue() && module.constStage < module.constTicks.getValue()) {
                    if (SpeedMode$11.mc.player.ticksExisted % 2 == 0) {
                        event.setY(0.00118212);
                    }
                    else {
                        event.setY(-0.00118212);
                    }
                }
                module.speed = module.distance - module.distance / 159.0;
            }
            module.speed = Math.min(module.speed, module.getCap());
            if (module.constStage != 0) {
                module.speed = Math.max(module.speed, MovementUtil.getSpeed(module.slow.getValue()));
            }
            MovementUtil.strafe(event, module.speed);
            if (MovementUtil.isMoving()) {
                ++module.constStage;
            }
        }
    };
    
    public abstract void move(final MoveEvent p0, final Speed p1);
}
