// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class SimplePacketManager implements IPacketManager
{
    private final Map<Integer, IPacketHandler> handlers;
    
    public SimplePacketManager() {
        this.handlers = new ConcurrentHashMap<Integer, IPacketHandler>();
    }
    
    @Override
    public void handle(final IConnection connection, final int id, final byte[] bytes) throws UnknownProtocolException, IOException {
        final IPacketHandler handler = this.handlers.get(id);
        if (handler == null) {
            throw new UnknownProtocolException(id);
        }
        handler.handle(connection, bytes);
    }
    
    @Override
    public void add(final int id, final IPacketHandler handler) {
        this.handlers.put(id, handler);
    }
    
    @Override
    public IPacketHandler getHandlerFor(final int id) {
        return this.handlers.get(id);
    }
}
