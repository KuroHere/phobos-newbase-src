//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.noafk;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.text.*;

final class ListenerChat extends ModuleListener<NoAFK, PacketEvent.Receive<SPacketChat>>
{
    public ListenerChat(final NoAFK module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketChat.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketChat> event) {
        if (((NoAFK)this.module).autoReply.getValue()) {
            final String m = event.getPacket().getChatComponent().getFormattedText();
            if ((m.contains(((NoAFK)this.module).color.getValue().getColor()) || ((NoAFK)this.module).color.getValue() == TextColor.Reset) && m.contains(((NoAFK)this.module).indicator.getValue())) {
                ListenerChat.mc.player.sendChatMessage(((NoAFK)this.module).reply.getValue() + ((NoAFK)this.module).message.getValue());
            }
        }
    }
}
