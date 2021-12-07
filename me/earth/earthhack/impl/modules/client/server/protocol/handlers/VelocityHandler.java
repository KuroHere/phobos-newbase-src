// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import java.nio.*;
import java.io.*;

public class VelocityHandler implements IPacketHandler
{
    private final IVelocityHandler handler;
    
    public VelocityHandler(final IVelocityHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        final ByteBuffer buf = ByteBuffer.wrap(bytes);
        this.handler.onVelocity(buf.getDouble(), buf.getDouble(), buf.getDouble());
    }
}
