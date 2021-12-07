//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

final class ListenerConfirmTransaction extends ModuleListener<Packets, PacketEvent.Receive<SPacketConfirmTransaction>>
{
    public ListenerConfirmTransaction(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -1000, SPacketConfirmTransaction.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketConfirmTransaction> event) {
        if (!event.isCancelled() && ((Packets)this.module).fastTransactions.getValue() && ListenerConfirmTransaction.mc.player != null) {
            final SPacketConfirmTransaction packet = event.getPacket();
            if (!packet.wasAccepted() && ((packet.getWindowId() == 0) ? ListenerConfirmTransaction.mc.player.inventoryContainer : ListenerConfirmTransaction.mc.player.openContainer) != null) {
                event.setCancelled(true);
                ListenerConfirmTransaction.mc.player.connection.sendPacket((Packet)new CPacketConfirmTransaction(packet.getWindowId(), packet.getActionNumber(), true));
            }
        }
    }
}
