//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

final class ListenerHeldItemChange extends ModuleListener<Packets, PacketEvent.Receive<SPacketHeldItemChange>>
{
    public ListenerHeldItemChange(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketHeldItemChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketHeldItemChange> event) {
        if (((Packets)this.module).noHandChange.getValue() && ListenerHeldItemChange.mc.player != null) {
            event.setCancelled(true);
            ListenerHeldItemChange.mc.addScheduledTask(Locks.wrap(Locks.PLACE_SWITCH_LOCK, () -> {
                if (ListenerHeldItemChange.mc.player != null && ListenerHeldItemChange.mc.getConnection() != null) {
                    final int l = ListenerHeldItemChange.mc.player.inventory.currentItem;
                    if (l != ((SPacketHeldItemChange)event.getPacket()).getHeldItemHotbarIndex()) {
                        ListenerHeldItemChange.mc.player.inventory.currentItem = l;
                        ListenerHeldItemChange.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(l));
                    }
                }
            }));
        }
    }
}
