//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.core.ducks.gui.*;

final class ListenerChat extends ModuleListener<Chat, ChatEvent.Send>
{
    public ListenerChat(final Chat module) {
        super(module, (Class<? super Object>)ChatEvent.Send.class);
    }
    
    public void invoke(final ChatEvent.Send event) {
        if (((Chat)this.module).noScroll.getValue() && ListenerChat.mc.ingameGUI != null) {
            final IGuiNewChat chat = (IGuiNewChat)ListenerChat.mc.ingameGUI.getChatGUI();
            if (chat.getScrollPos() != 0) {
                ((Chat)this.module).events.add(event);
                ((Chat)this.module).cleared = false;
                event.setCancelled(true);
            }
        }
    }
}
