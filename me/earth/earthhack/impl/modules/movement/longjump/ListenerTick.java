//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.longjump.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.entity.*;

final class ListenerTick extends ModuleListener<LongJump, TickEvent>
{
    private static final double[] MOVE;
    
    public ListenerTick(final LongJump module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (ListenerTick.mc.player == null || ListenerTick.mc.world == null || ((LongJump)this.module).mode.getValue() != JumpMode.Cowabunga) {
            return;
        }
        if (KeyBoardUtil.isKeyDown(((LongJump)this.module).invalidBind)) {
            ((LongJump)this.module).invalidPacket();
        }
        if (MovementUtil.noMovementKeys()) {
            return;
        }
        final float direction = ListenerTick.mc.player.rotationYaw + ((ListenerTick.mc.player.field_191988_bg < 0.0f) ? 180 : 0) + ((ListenerTick.mc.player.moveStrafing > 0.0f) ? (-90.0f * ((ListenerTick.mc.player.field_191988_bg < 0.0f) ? -0.5f : ((ListenerTick.mc.player.field_191988_bg > 0.0f) ? 0.5f : 1.0f))) : 0.0f) - ((ListenerTick.mc.player.moveStrafing < 0.0f) ? (-90.0f * ((ListenerTick.mc.player.field_191988_bg < 0.0f) ? -0.5f : ((ListenerTick.mc.player.field_191988_bg > 0.0f) ? 0.5f : 1.0f))) : 0.0f);
        final float x = (float)Math.cos((direction + 90.0f) * 3.141592653589793 / 180.0);
        final float z = (float)Math.sin((direction + 90.0f) * 3.141592653589793 / 180.0);
        if (!ListenerTick.mc.player.isCollidedVertically) {
            final LongJump longJump = (LongJump)this.module;
            ++longJump.airTicks;
            if (ListenerTick.mc.player.movementInput.sneak) {
                ((LongJump)this.module).invalidPacket();
            }
            ((LongJump)this.module).groundTicks = 0;
            if (!ListenerTick.mc.player.isCollidedVertically) {
                if (ListenerTick.mc.player.motionY == -0.07190068807140403) {
                    final EntityPlayerSP player = ListenerTick.mc.player;
                    player.motionY *= 0.3499999940395355;
                }
                if (ListenerTick.mc.player.motionY == -0.10306193759436909) {
                    final EntityPlayerSP player2 = ListenerTick.mc.player;
                    player2.motionY *= 0.550000011920929;
                }
                if (ListenerTick.mc.player.motionY == -0.13395038817442878) {
                    final EntityPlayerSP player3 = ListenerTick.mc.player;
                    player3.motionY *= 0.6700000166893005;
                }
                if (ListenerTick.mc.player.motionY == -0.16635183030382) {
                    final EntityPlayerSP player4 = ListenerTick.mc.player;
                    player4.motionY *= 0.6899999976158142;
                }
                if (ListenerTick.mc.player.motionY == -0.19088711097794803) {
                    final EntityPlayerSP player5 = ListenerTick.mc.player;
                    player5.motionY *= 0.7099999785423279;
                }
                if (ListenerTick.mc.player.motionY == -0.21121925191528862) {
                    final EntityPlayerSP player6 = ListenerTick.mc.player;
                    player6.motionY *= 0.20000000298023224;
                }
                if (ListenerTick.mc.player.motionY == -0.11979897632390576) {
                    final EntityPlayerSP player7 = ListenerTick.mc.player;
                    player7.motionY *= 0.9300000071525574;
                }
                if (ListenerTick.mc.player.motionY == -0.18758479151225355) {
                    final EntityPlayerSP player8 = ListenerTick.mc.player;
                    player8.motionY *= 0.7200000286102295;
                }
                if (ListenerTick.mc.player.motionY == -0.21075983825251726) {
                    final EntityPlayerSP player9 = ListenerTick.mc.player;
                    player9.motionY *= 0.7599999904632568;
                }
                if (((LongJump)this.module).getDistance((EntityPlayer)ListenerTick.mc.player, 69.0) < 0.5) {
                    if (ListenerTick.mc.player.motionY == -0.23537393014173347) {
                        final EntityPlayerSP player10 = ListenerTick.mc.player;
                        player10.motionY *= 0.029999999329447746;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08531999505205401) {
                        final EntityPlayerSP player11 = ListenerTick.mc.player;
                        player11.motionY *= -0.5;
                    }
                    if (ListenerTick.mc.player.motionY == -0.03659320313669756) {
                        final EntityPlayerSP player12 = ListenerTick.mc.player;
                        player12.motionY *= -0.10000000149011612;
                    }
                    if (ListenerTick.mc.player.motionY == -0.07481386749524899) {
                        final EntityPlayerSP player13 = ListenerTick.mc.player;
                        player13.motionY *= -0.07000000029802322;
                    }
                    if (ListenerTick.mc.player.motionY == -0.0732677700939672) {
                        final EntityPlayerSP player14 = ListenerTick.mc.player;
                        player14.motionY *= -0.05000000074505806;
                    }
                    if (ListenerTick.mc.player.motionY == -0.07480988066790395) {
                        final EntityPlayerSP player15 = ListenerTick.mc.player;
                        player15.motionY *= -0.03999999910593033;
                    }
                    if (ListenerTick.mc.player.motionY == -0.0784000015258789) {
                        final EntityPlayerSP player16 = ListenerTick.mc.player;
                        player16.motionY *= 0.10000000149011612;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08608320193943977) {
                        final EntityPlayerSP player17 = ListenerTick.mc.player;
                        player17.motionY *= 0.10000000149011612;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08683615560584318) {
                        final EntityPlayerSP player18 = ListenerTick.mc.player;
                        player18.motionY *= 0.05000000074505806;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08265497329678266) {
                        final EntityPlayerSP player19 = ListenerTick.mc.player;
                        player19.motionY *= 0.05000000074505806;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08245009535659828) {
                        final EntityPlayerSP player20 = ListenerTick.mc.player;
                        player20.motionY *= 0.05000000074505806;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08244005633718426) {
                        ListenerTick.mc.player.motionY = -0.08243956442521608;
                    }
                    if (ListenerTick.mc.player.motionY == -0.08243956442521608) {
                        ListenerTick.mc.player.motionY = -0.08244005590677261;
                    }
                    if (ListenerTick.mc.player.motionY > -0.1 && ListenerTick.mc.player.motionY < -0.08 && !ListenerTick.mc.player.onGround && ListenerTick.mc.player.movementInput.forwardKeyDown) {
                        ListenerTick.mc.player.motionY = -9.999999747378752E-5;
                    }
                }
                else {
                    if (ListenerTick.mc.player.motionY < -0.2 && ListenerTick.mc.player.motionY > -0.24) {
                        final EntityPlayerSP player21 = ListenerTick.mc.player;
                        player21.motionY *= 0.7;
                    }
                    if (ListenerTick.mc.player.motionY < -0.25 && ListenerTick.mc.player.motionY > -0.32) {
                        final EntityPlayerSP player22 = ListenerTick.mc.player;
                        player22.motionY *= 0.8;
                    }
                    if (ListenerTick.mc.player.motionY < -0.35 && ListenerTick.mc.player.motionY > -0.8) {
                        final EntityPlayerSP player23 = ListenerTick.mc.player;
                        player23.motionY *= 0.98;
                    }
                    if (ListenerTick.mc.player.motionY < -0.8 && ListenerTick.mc.player.motionY > -1.6) {
                        final EntityPlayerSP player24 = ListenerTick.mc.player;
                        player24.motionY *= 0.99;
                    }
                }
            }
            Managers.TIMER.setTimer(0.85f);
            if (ListenerTick.mc.player.movementInput.forwardKeyDown) {
                try {
                    ListenerTick.mc.player.motionX = x * ListenerTick.MOVE[((LongJump)this.module).airTicks - 1] * 3.0;
                    ListenerTick.mc.player.motionZ = z * ListenerTick.MOVE[((LongJump)this.module).airTicks - 1] * 3.0;
                    return;
                }
                catch (Exception ex) {
                    return;
                }
            }
            ListenerTick.mc.player.motionX = 0.0;
            ListenerTick.mc.player.motionZ = 0.0;
            return;
        }
        Managers.TIMER.setTimer(1.0f);
        ((LongJump)this.module).airTicks = 0;
        final LongJump longJump2 = (LongJump)this.module;
        ++longJump2.groundTicks;
        final EntityPlayerSP player25 = ListenerTick.mc.player;
        player25.motionX /= 13.0;
        final EntityPlayerSP player26 = ListenerTick.mc.player;
        player26.motionZ /= 13.0;
        if (((LongJump)this.module).groundTicks == 1) {
            ((LongJump)this.module).updatePosition(ListenerTick.mc.player.posX, ListenerTick.mc.player.posY, ListenerTick.mc.player.posZ);
            ((LongJump)this.module).updatePosition(ListenerTick.mc.player.posX + 0.0624, ListenerTick.mc.player.posY, ListenerTick.mc.player.posZ);
            ((LongJump)this.module).updatePosition(ListenerTick.mc.player.posX, ListenerTick.mc.player.posY + 0.419, ListenerTick.mc.player.posZ);
            ((LongJump)this.module).updatePosition(ListenerTick.mc.player.posX + 0.0624, ListenerTick.mc.player.posY, ListenerTick.mc.player.posZ);
            ((LongJump)this.module).updatePosition(ListenerTick.mc.player.posX, ListenerTick.mc.player.posY + 0.419, ListenerTick.mc.player.posZ);
        }
        if (((LongJump)this.module).groundTicks > 2) {
            ((LongJump)this.module).groundTicks = 0;
            ListenerTick.mc.player.motionX = x * 0.3;
            ListenerTick.mc.player.motionY = 0.42399999499320984;
            ListenerTick.mc.player.motionZ = z * 0.3;
        }
    }
    
    static {
        MOVE = new double[] { 0.420606, 0.417924, 0.415258, 0.412609, 0.409977, 0.407361, 0.404761, 0.402178, 0.399611, 0.39706, 0.394525, 0.392, 0.3894, 0.38644, 0.383655, 0.381105, 0.37867, 0.37625, 0.37384, 0.37145, 0.369, 0.3666, 0.3642, 0.3618, 0.35945, 0.357, 0.354, 0.351, 0.348, 0.345, 0.342, 0.339, 0.336, 0.333, 0.33, 0.327, 0.324, 0.321, 0.318, 0.315, 0.312, 0.309, 0.307, 0.305, 0.303, 0.3, 0.297, 0.295, 0.293, 0.291, 0.289, 0.287, 0.285, 0.283, 0.281, 0.279, 0.277, 0.275, 0.273, 0.271, 0.269, 0.267, 0.265, 0.263, 0.261, 0.259, 0.257, 0.255, 0.253, 0.251, 0.249, 0.247, 0.245, 0.243, 0.241, 0.239, 0.237 };
    }
}
