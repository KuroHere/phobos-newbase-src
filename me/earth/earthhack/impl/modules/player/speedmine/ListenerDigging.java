//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.misc.nuker.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerDigging extends ModuleListener<Speedmine, PacketEvent.Send<CPacketPlayerDigging>>
{
    private static final ModuleCache<Nuker> NUKER;
    private static final SettingCache<Boolean, BooleanSetting, Nuker> NUKE;
    private static final ModuleCache<AntiSurround> ANTISURROUND;
    
    public ListenerDigging(final Speedmine module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerDigging> event) {
        if (!PlayerUtil.isCreative((EntityPlayer)ListenerDigging.mc.player) && !ListenerDigging.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false) && (!ListenerDigging.NUKER.isEnabled() || !ListenerDigging.NUKE.getValue()) && (((Speedmine)this.module).mode.getValue() == MineMode.Packet || ((Speedmine)this.module).mode.getValue() == MineMode.Smart || ((Speedmine)this.module).mode.getValue() == MineMode.Instant)) {
            final CPacketPlayerDigging packet = event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK || packet.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                final BlockPos pos = packet.getPosition();
                if (!MineUtil.canBreak(pos)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    static {
        NUKER = Caches.getModule(Nuker.class);
        NUKE = Caches.getSetting(Nuker.class, BooleanSetting.class, "Nuke", false);
        ANTISURROUND = Caches.getModule(AntiSurround.class);
    }
}
