// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.misc.chat.util.*;

final class ListenerChatLog extends ModuleListener<Chat, ChatEvent.Log>
{
    public ListenerChatLog(final Chat module) {
        super(module, (Class<? super Object>)ChatEvent.Log.class);
    }
    
    public void invoke(final ChatEvent.Log event) {
        if (((Chat)this.module).log.getValue() != LoggerMode.Normal) {
            event.setCancelled(true);
        }
    }
}
