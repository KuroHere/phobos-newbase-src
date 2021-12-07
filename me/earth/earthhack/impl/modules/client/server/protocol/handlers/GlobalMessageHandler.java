// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import java.nio.charset.*;
import java.nio.*;
import java.util.*;
import java.io.*;

public class GlobalMessageHandler implements IPacketHandler
{
    private final IConnectionManager manager;
    private final ILogger logger;
    
    public GlobalMessageHandler(final ILogger logger, final IConnectionManager manager) {
        this.logger = logger;
        this.manager = manager;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        final String message = new String(bytes, StandardCharsets.UTF_8);
        this.logger.log(message);
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 8);
        buffer.putInt(2);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        final byte[] globalMessage = buffer.array();
        for (final IConnection conn : this.manager.getConnections()) {
            if (conn != null && !conn.equals(connection)) {
                conn.send(globalMessage);
            }
        }
    }
}
