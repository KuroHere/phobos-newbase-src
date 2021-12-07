//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trails;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.animation.*;

final class ListenerDestroyEntities extends ModuleListener<Trails, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerDestroyEntities(final Trails module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        for (final int id : event.getPacket().getEntityIDs()) {
            if (((Trails)this.module).ids.containsKey(id)) {
                ((Trails)this.module).ids.get(id).play();
            }
        }
    }
}
