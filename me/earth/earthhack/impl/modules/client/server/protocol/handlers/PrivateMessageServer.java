// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import java.nio.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import java.io.*;

public class PrivateMessageServer implements IPacketHandler
{
    private final IConnectionManager manager;
    
    public PrivateMessageServer(final IConnectionManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);
        final int id = buffer.getInt();
        final IConnection target = this.manager.getConnections().stream().filter(c -> c != null && c.getId() == id).findFirst().orElse(null);
        if (target != null) {
            final byte[] packet = new byte[bytes.length + 12];
            ProtocolUtil.addInt(5, packet);
            ProtocolUtil.addInt(bytes.length + 4, packet, 4);
            ProtocolUtil.addInt(connection.getId(), bytes, 8);
            ProtocolUtil.addAllBytes(bytes, packet, 12);
            target.send(packet);
        }
    }
}
