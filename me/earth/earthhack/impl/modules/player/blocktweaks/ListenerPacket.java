//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blocktweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.misc.nuker.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerPacket extends ModuleListener<BlockTweaks, PacketEvent.Post<CPacketPlayerDigging>>
{
    private static final ModuleCache<Nuker> NUKER;
    private static final SettingCache<Boolean, BooleanSetting, Nuker> NUKE;
    
    public ListenerPacket(final BlockTweaks module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerDigging> event) {
        if (((BlockTweaks)this.module).noBreakAnim.getValue() && !PlayerUtil.isCreative((EntityPlayer)ListenerPacket.mc.player) && (!ListenerPacket.NUKER.isEnabled() || !ListenerPacket.NUKE.getValue())) {
            final CPacketPlayerDigging packet = event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                final BlockPos pos = packet.getPosition();
                if (MineUtil.canBreak(pos)) {
                    ListenerPacket.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, packet.getPosition(), packet.getFacing()));
                }
            }
        }
    }
    
    static {
        NUKER = Caches.getModule(Nuker.class);
        NUKE = Caches.getSetting(Nuker.class, BooleanSetting.class, "Nuke", false);
    }
}
