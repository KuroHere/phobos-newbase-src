//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.criticals;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.killaura.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.criticals.mode.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerUseEntity extends ModuleListener<Criticals, PacketEvent.Send<CPacketUseEntity>>
{
    private static final ModuleCache<KillAura> KILL_AURA;
    
    public ListenerUseEntity(final Criticals module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketUseEntity.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketUseEntity> event) {
        if (event.getPacket().getAction() == CPacketUseEntity.Action.ATTACK && ListenerUseEntity.mc.player.onGround && !ListenerUseEntity.mc.gameSettings.keyBindJump.isKeyDown() && !ListenerUseEntity.mc.player.isInWater() && !ListenerUseEntity.mc.player.isInLava() && ((Criticals)this.module).timer.passed(((Criticals)this.module).delay.getValue())) {
            final CPacketUseEntity packet = event.getPacket();
            final Entity entity = ((ICPacketUseEntity)packet).getAttackedEntity();
            if (!((Criticals)this.module).noDesync.getValue() || entity instanceof EntityLivingBase) {
                final Vec3d vec = RotationUtil.getRotationPlayer().getPositionVector();
                final Vec3d pos = ListenerUseEntity.KILL_AURA.returnIfPresent(k -> k.criticalCallback(vec), vec);
                switch (((Criticals)this.module).mode.getValue()) {
                    case Packet: {
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord + 0.0625101, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord + 1.1E-5, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord, pos.zCoord, false));
                        break;
                    }
                    case Bypass: {
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord + 0.062600301692775, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord + 0.07260029960661, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord, pos.zCoord, false));
                        ListenerUseEntity.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.xCoord, pos.yCoord, pos.zCoord, false));
                        break;
                    }
                    case Jump: {
                        ListenerUseEntity.mc.player.jump();
                        break;
                    }
                    case MiniJump: {
                        ListenerUseEntity.mc.player.jump();
                        final EntityPlayerSP player = ListenerUseEntity.mc.player;
                        player.motionY /= 2.0;
                        break;
                    }
                }
                ((Criticals)this.module).timer.reset();
            }
        }
    }
    
    static {
        KILL_AURA = Caches.getModule(KillAura.class);
    }
}
