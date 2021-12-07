//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.newchunks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.render.newchunks.util.*;

final class ListenerChunkData extends ModuleListener<NewChunks, PacketEvent.Receive<SPacketChunkData>>
{
    public ListenerChunkData(final NewChunks module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketChunkData.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketChunkData> event) {
        final SPacketChunkData p = event.getPacket();
        if (!p.doChunkLoad()) {
            final ChunkData data = new ChunkData(p.getChunkX(), p.getChunkZ());
            ((NewChunks)this.module).data.add(data);
        }
    }
}
