//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.xcarry;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerCloseWindow extends ModuleListener<XCarry, PacketEvent.Send<CPacketCloseWindow>>
{
    public ListenerCloseWindow(final XCarry module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketCloseWindow.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketCloseWindow> event) {
        final ICPacketCloseWindow packet = event.getPacket();
        if (packet.getWindowId() == ListenerCloseWindow.mc.player.inventoryContainer.windowId) {
            event.setCancelled(true);
        }
    }
}
