// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerSPacketEntityMetaData extends ModuleListener<Packets, PacketEvent.Receive<SPacketEntityMetadata>>
{
    public ListenerSPacketEntityMetaData(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityMetadata.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityMetadata> event) {
    }
}
