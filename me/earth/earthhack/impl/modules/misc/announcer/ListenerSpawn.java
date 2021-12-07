//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import java.util.stream.*;

public class ListenerSpawn extends ModuleListener<Announcer, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawn(final Announcer module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if ((event.getPacket().getType() == 60 || event.getPacket().getType() == 91) && (Math.abs(event.getPacket().getSpeedX() / 8000) > 0.001 || Math.abs(event.getPacket().getSpeedY() / 8000) > 0.001 || Math.abs(event.getPacket().getSpeedZ() / 8000) > 0.001) && ((Announcer)this.module).miss.getValue()) {
            final EntityPlayer closestPlayer = Managers.ENTITIES.getPlayers().stream().filter(player -> player != ListenerSpawn.mc.player && !Managers.FRIENDS.contains(player)).sorted(Comparator.comparing(player -> player.getDistanceSq(((SPacketSpawnObject)event.getPacket()).getX(), ((SPacketSpawnObject)event.getPacket()).getY(), ((SPacketSpawnObject)event.getPacket()).getZ()))).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).get(0);
            if (closestPlayer != null) {
                ((Announcer)this.module).arrowMap.put(event.getPacket().getEntityID(), closestPlayer);
            }
        }
    }
}
