// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.newchunks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerUnload extends ModuleListener<NewChunks, PacketEvent.Receive<SPacketUnloadChunk>>
{
    public ListenerUnload(final NewChunks module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketUnloadChunk.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketUnloadChunk> event) {
    }
}
