//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.client.server.util.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.*;
import java.io.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;
import java.util.*;

final class ListenerCPacket extends CPacketPlayerListener
{
    private final ServerModule module;
    
    public ListenerCPacket(final ServerModule module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.onEvent(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.onEvent((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.onEvent((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.onEvent((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    private void onEvent(final PacketEvent.Send<? extends CPacketPlayer> event) {
        if (event.isCancelled() || !this.module.sync.getValue()) {
            return;
        }
        if (this.module.currentMode == ServerMode.Client) {
            event.setCancelled(true);
            return;
        }
        final Packet<?> p = event.getPacket();
        PacketBuffer buffer = null;
        try {
            buffer = new PacketBuffer(Unpooled.buffer());
            final int id = EnumConnectionState.getFromPacket((Packet)p).getPacketId(EnumPacketDirection.SERVERBOUND, (Packet)p);
            buffer.writeInt(3);
            final int index = buffer.writerIndex();
            buffer.writeInt(-1);
            int size = buffer.writerIndex();
            buffer.writeVarIntToBuffer(id);
            event.getPacket().writePacketData(buffer);
            final int lastIndex = buffer.writerIndex();
            size = buffer.writerIndex() - size;
            buffer.writerIndex(index);
            buffer.writeInt(size);
            buffer.writerIndex(lastIndex);
            final byte[] packets = ProtocolPlayUtil.velocityAndPosition(RotationUtil.getRotationPlayer());
            for (final IConnection connection : this.module.connectionManager.getConnections()) {
                try {
                    buffer.getBytes(0, connection.getOutputStream(), buffer.readableBytes());
                    connection.send(packets);
                }
                catch (IOException e) {
                    this.module.connectionManager.remove(connection);
                    Earthhack.getLogger().warn("Error with Connection: " + connection.getName());
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        finally {
            if (buffer != null) {
                BufferUtil.releaseBuffer((ReferenceCounted)buffer);
            }
        }
    }
}
