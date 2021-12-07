//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.holetp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.movement.blocklag.*;
import me.earth.earthhack.impl.modules.movement.longjump.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.speed.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

final class ListenerMotion extends ModuleListener<HoleTP, MotionUpdateEvent>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    private static final ModuleCache<BlockLag> BLOCK_LAG;
    private static final ModuleCache<Speed> SPEED;
    private static final ModuleCache<LongJump> LONGJUMP;
    private static final SettingCache<SpeedMode, EnumSetting<SpeedMode>, Speed> SPEED_MODE;
    
    public ListenerMotion(final HoleTP module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST) {
            if (PositionUtil.inLiquid(true) || PositionUtil.inLiquid(false) || ListenerMotion.PACKET_FLY.isEnabled() || ListenerMotion.BLOCK_LAG.isEnabled() || ListenerMotion.LONGJUMP.isEnabled() || (ListenerMotion.SPEED.isEnabled() && ListenerMotion.SPEED_MODE.getValue() != SpeedMode.Instant)) {
                return;
            }
            if (!ListenerMotion.mc.player.onGround) {
                if (ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown()) {
                    ((HoleTP)this.module).jumped = true;
                }
            }
            else {
                ((HoleTP)this.module).jumped = false;
            }
            if (!((HoleTP)this.module).jumped && ListenerMotion.mc.player.fallDistance < 0.5 && ((HoleTP)this.module).isInHole() && ListenerMotion.mc.player.posY - ((HoleTP)this.module).getNearestBlockBelow() > 0.8 && ListenerMotion.mc.player.posY - ((HoleTP)this.module).getNearestBlockBelow() <= 1.125) {
                if (!ListenerMotion.mc.player.onGround) {
                    final HoleTP holeTP = (HoleTP)this.module;
                    ++holeTP.packets;
                }
                if (!ListenerMotion.mc.player.onGround && !ListenerMotion.mc.player.isOnLadder() && !ListenerMotion.mc.player.isEntityInsideOpaqueBlock() && !ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown() && ((HoleTP)this.module).packets > 0) {
                    final BlockPos pos = new BlockPos(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ);
                    for (final double position : HoleTP.OFFSETS) {
                        ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)(pos.getX() + 0.5f), ListenerMotion.mc.player.posY - position, (double)(pos.getZ() + 0.5f), true));
                    }
                    ListenerMotion.mc.player.setPosition((double)(pos.getX() + 0.5f), ((HoleTP)this.module).getNearestBlockBelow() + 0.1, (double)(pos.getZ() + 0.5f));
                    ((HoleTP)this.module).packets = 0;
                }
            }
        }
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
        BLOCK_LAG = Caches.getModule(BlockLag.class);
        SPEED = Caches.getModule(Speed.class);
        LONGJUMP = Caches.getModule(LongJump.class);
        SPEED_MODE = Caches.getSetting(Speed.class, Setting.class, "Mode", SpeedMode.Instant);
    }
}
