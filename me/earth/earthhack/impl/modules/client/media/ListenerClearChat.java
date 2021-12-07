// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.media;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerClearChat extends ModuleListener<Media, ChatEvent.Clear>
{
    public ListenerClearChat(final Media module) {
        super(module, (Class<? super Object>)ChatEvent.Clear.class);
    }
    
    public void invoke(final ChatEvent.Clear event) {
        ((Media)this.module).cache.clear();
    }
}
