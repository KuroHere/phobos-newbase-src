//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.noslowdown.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Flight, MotionUpdateEvent>
{
    private static final ModuleCache<NoSlowDown> NO_SLOW_DOWN;
    private static final SettingCache<Boolean, BooleanSetting, NoSlowDown> GUI;
    
    public ListenerMotion(final Flight module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        switch (((Flight)this.module).mode.getValue()) {
            case ConstantiamNew: {
                if (event.getStage() == Stage.PRE) {
                    if (((Flight)this.module).constNewStage > 2) {
                        ListenerMotion.mc.player.motionY = 0.0;
                        ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY - 0.032, ListenerMotion.mc.player.posZ);
                        final Flight flight = (Flight)this.module;
                        ++flight.constNewTicks;
                        switch (((Flight)this.module).constNewTicks) {
                            case 1: {
                                final Flight flight2 = (Flight)this.module;
                                flight2.constY *= -0.949999988079071;
                                break;
                            }
                            case 2:
                            case 3:
                            case 4: {
                                final Flight flight3 = (Flight)this.module;
                                flight3.constY += 3.25E-4;
                                break;
                            }
                            case 5: {
                                final Flight flight4 = (Flight)this.module;
                                flight4.constY += 5.0E-4;
                                ((Flight)this.module).constNewTicks = 0;
                                break;
                            }
                        }
                        event.setY(ListenerMotion.mc.player.posY + ((Flight)this.module).constY);
                    }
                }
                else if (((Flight)this.module).constNewStage > 2) {
                    ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY + 0.032, ListenerMotion.mc.player.posZ);
                }
                if (!ListenerMotion.mc.player.onGround && !ListenerMotion.mc.player.isCollidedVertically && ListenerMotion.mc.player.ticksExisted % 30 == 0) {
                    event.setY(event.getY() - 0.032);
                    break;
                }
                break;
            }
            case ConstoHare:
            case ConstoHareFast: {
                if (event.getStage() != Stage.PRE) {
                    final double xDist = ListenerMotion.mc.player.posX - ListenerMotion.mc.player.prevPosX;
                    final double zDist = ListenerMotion.mc.player.posZ - ListenerMotion.mc.player.prevPosZ;
                    ((Flight)this.module).oHareLastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                    break;
                }
                final Flight flight5 = (Flight)this.module;
                ++flight5.oHareCounter;
                if (ListenerMotion.mc.player.field_191988_bg == 0.0f && ListenerMotion.mc.player.moveStrafing == 0.0f) {
                    ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.posX + 1.0, ListenerMotion.mc.player.posY + 1.0, ListenerMotion.mc.player.posZ + 1.0);
                    ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.prevPosX, ListenerMotion.mc.player.prevPosY, ListenerMotion.mc.player.prevPosZ);
                    ListenerMotion.mc.player.motionX = 0.0;
                    ListenerMotion.mc.player.motionZ = 0.0;
                }
                ListenerMotion.mc.player.motionY = 0.0;
                if (ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final EntityPlayerSP player = ListenerMotion.mc.player;
                    player.motionY += 0.5;
                }
                if (ListenerMotion.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    final EntityPlayerSP player2 = ListenerMotion.mc.player;
                    player2.motionY -= 0.5;
                }
                if (((Flight)this.module).oHareCounter == 2) {
                    ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY + 1.0E-10, ListenerMotion.mc.player.posZ);
                    ((Flight)this.module).oHareCounter = 0;
                    break;
                }
                break;
            }
            case Constantiam:
            case Normal: {
                ListenerMotion.mc.player.motionX = 0.0;
                ListenerMotion.mc.player.motionY = 0.0;
                ListenerMotion.mc.player.motionZ = 0.0;
                if (((Flight)this.module).glide.getValue()) {
                    final EntityPlayerSP player3 = ListenerMotion.mc.player;
                    player3.motionY -= ((Flight)this.module).glideSpeed.getValue();
                }
                if (!ListenerMotion.mc.inGameHasFocus) {
                    if (!ListenerMotion.NO_SLOW_DOWN.isEnabled()) {
                        break;
                    }
                    if (!ListenerMotion.GUI.getValue()) {
                        break;
                    }
                }
                if (ListenerMotion.mc.player.movementInput.jump) {
                    final EntityPlayerSP player4 = ListenerMotion.mc.player;
                    player4.motionY += 0.4000000059604645;
                }
                if (ListenerMotion.mc.player.movementInput.sneak) {
                    final EntityPlayerSP player5 = ListenerMotion.mc.player;
                    player5.motionY -= 0.4000000059604645;
                }
                if (((Flight)this.module).mode.getValue() == FlightMode.Constantiam && !ListenerMotion.mc.player.onGround && !ListenerMotion.mc.player.isCollidedVertically && ListenerMotion.mc.player.ticksExisted % 20 == 0 && ((Flight)this.module).antiKick.getValue()) {
                    ListenerMotion.mc.player.setPosition(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY - 0.032, ListenerMotion.mc.player.posZ);
                    break;
                }
                break;
            }
            case Jump: {
                if (event.getStage() != Stage.PRE || ListenerMotion.mc.player.onGround) {
                    break;
                }
                if (!ListenerMotion.mc.player.movementInput.jump) {
                    if (MovementUtil.noMovementKeys() || ListenerMotion.mc.player.movementInput.sneak) {
                        break;
                    }
                    final Flight flight6 = (Flight)this.module;
                    ++flight6.counter;
                    if (((Flight)this.module).counter >= 11) {
                        ListenerMotion.mc.player.jumpMovementFactor = 0.7f;
                        ListenerMotion.mc.player.jump();
                        ((Flight)this.module).counter = 0;
                        break;
                    }
                    break;
                }
                else {
                    if (ListenerMotion.mc.player.movementInput.sneak) {
                        break;
                    }
                    final Flight flight7 = (Flight)this.module;
                    ++flight7.counter;
                    if (((Flight)this.module).counter >= 4) {
                        ListenerMotion.mc.player.jumpMovementFactor = 0.01f;
                        ListenerMotion.mc.player.jump();
                        ((Flight)this.module).counter = 0;
                        break;
                    }
                    break;
                }
                break;
            }
        }
        if (event.getStage() == Stage.PRE) {
            ((Flight)this.module).constNewOffset = ListenerMotion.mc.player.posX - ListenerMotion.mc.player.prevPosX;
            final double zDif = ListenerMotion.mc.player.posZ - ListenerMotion.mc.player.prevPosZ;
            ((Flight)this.module).lastDist = Math.sqrt(((Flight)this.module).constNewOffset * ((Flight)this.module).constNewOffset + zDif * zDif);
        }
        if (((Flight)this.module).antiKick.getValue() && ((Flight)this.module).mode.getValue() != FlightMode.Constantiam) {
            final Flight flight8 = (Flight)this.module;
            ++flight8.antiCounter;
            if (((Flight)this.module).antiCounter >= 12 && !ListenerMotion.mc.player.isPotionActive(MobEffects.LEVITATION) && !ListenerMotion.mc.player.isElytraFlying() && ListenerMotion.mc.world.getCollisionBoxes((Entity)ListenerMotion.mc.player, ListenerMotion.mc.player.getEntityBoundingBox().expandXyz(0.0625).addCoord(0.0, -0.55, 0.0)).isEmpty()) {
                event.setY(event.getY() - 0.03126);
                if (((Flight)this.module).antiCounter >= 22) {
                    ((Flight)this.module).antiCounter = 0;
                }
            }
        }
    }
    
    static {
        NO_SLOW_DOWN = Caches.getModule(NoSlowDown.class);
        GUI = Caches.getSetting(NoSlowDown.class, BooleanSetting.class, "GuiMove", true);
    }
}
