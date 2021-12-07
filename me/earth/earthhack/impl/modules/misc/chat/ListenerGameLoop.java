//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.core.ducks.gui.*;

final class ListenerGameLoop extends ModuleListener<Chat, GameLoopEvent>
{
    public ListenerGameLoop(final Chat module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (!((Chat)this.module).cleared && ListenerGameLoop.mc.ingameGUI != null) {
            final IGuiNewChat chat = (IGuiNewChat)ListenerGameLoop.mc.ingameGUI.getChatGUI();
            if (chat.getScrollPos() == 0) {
                ((Chat)this.module).clearNoScroll();
            }
        }
    }
}
