//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.misc.chat.util.*;
import org.apache.logging.log4j.*;

final class ListenerPacket extends ModuleListener<Chat, PacketEvent.Receive<SPacketChat>>
{
    private static final Logger LOGGER;
    
    public ListenerPacket(final Chat module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketChat.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketChat> event) {
        if (((Chat)this.module).log.getValue() == LoggerMode.Async) {
            ListenerPacket.LOGGER.info("[CHAT] {}", (Object)event.getPacket().getChatComponent().getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
