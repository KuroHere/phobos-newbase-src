//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import io.netty.buffer.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;
import net.minecraft.client.entity.*;

public class PacketHandler implements IPacketHandler, Globals
{
    private final ILogger logger;
    
    public PacketHandler(final ILogger logger) {
        this.logger = logger;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) {
        final EntityPlayerSP playerSP = PacketHandler.mc.player;
        if (playerSP == null) {
            this.logger.log("Received a packet, but we are not ingame!");
            return;
        }
        PacketBuffer buffer = null;
        try {
            buffer = new PacketBuffer(Unpooled.wrappedBuffer(bytes));
            final int id = buffer.readVarIntFromBuffer();
            this.logger.log("Received Packet with ID: " + id);
            final Packet<?> packet = (Packet<?>)new CopyPacket(id, EnumConnectionState.PLAY.ordinal(), bytes, buffer.readerIndex());
            playerSP.connection.sendPacket((Packet)packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (buffer != null) {
                BufferUtil.releaseBuffer((ReferenceCounted)buffer);
            }
        }
    }
}
