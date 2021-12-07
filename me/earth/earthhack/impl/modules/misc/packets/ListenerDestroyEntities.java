//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import java.util.*;

final class ListenerDestroyEntities extends ModuleListener<Packets, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerDestroyEntities(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        if (((Packets)this.module).fastDestroyEntities.getValue()) {
            final List<Entity> entities = Managers.ENTITIES.getEntities();
            if (entities == null) {
                return;
            }
            for (final int id : event.getPacket().getEntityIDs()) {
                for (final Entity entity : entities) {
                    if (entity != null && entity.getEntityId() == id) {
                        entity.setDead();
                    }
                }
            }
        }
    }
}
