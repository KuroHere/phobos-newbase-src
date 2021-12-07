//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.network;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.network.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.network.play.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.instance.*;
import net.minecraft.network.*;
import java.util.*;

public class NetworkUtil implements Globals
{
    public static void send(final Packet<?> packet) {
        final NetHandlerPlayClient connection = NetworkUtil.mc.getConnection();
        if (connection != null) {
            connection.sendPacket((Packet)packet);
        }
    }
    
    public static Packet<?> sendPacketNoEvent(final Packet<?> packet) {
        return sendPacketNoEvent(packet, true);
    }
    
    public static Packet<?> sendPacketNoEvent(final Packet<?> packet, final boolean post) {
        final NetHandlerPlayClient connection = NetworkUtil.mc.getConnection();
        if (connection != null) {
            final INetworkManager manager = (INetworkManager)connection.getNetworkManager();
            return manager.sendPacketNoEvent(packet, post);
        }
        return null;
    }
    
    public static boolean receive(final Packet<INetHandlerPlayClient> packet) {
        final PacketEvent.Receive<?> e = new PacketEvent.Receive<Object>(packet);
        Bus.EVENT_BUS.post(e, packet.getClass());
        if (e.isCancelled()) {
            return false;
        }
        packet.processPacket((INetHandler)NetworkUtil.mc.player.connection);
        for (final Runnable runnable : e.getPostEvents()) {
            runnable.run();
        }
        return true;
    }
}
