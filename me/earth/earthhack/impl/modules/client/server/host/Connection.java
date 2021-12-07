// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.host;

import me.earth.earthhack.impl.util.thread.*;
import java.util.concurrent.atomic.*;
import java.net.*;
import java.io.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.api.*;

public final class Connection extends AbstractConnection implements SafeRunnable
{
    private static final AtomicInteger ID;
    private final IConnectionManager manager;
    private final int id;
    
    public Connection(final IConnectionManager manager, final Socket socket) {
        super(socket);
        this.manager = manager;
        this.name = "unknown";
        this.id = Connection.ID.incrementAndGet();
    }
    
    @Override
    public void runSafely() throws Throwable {
        try (final DataInputStream in = new DataInputStream(this.getInputStream())) {
            while (this.isOpen()) {
                final IPacket p = ProtocolUtil.readPacket(in);
                this.manager.getHandler().handle(this, p.getId(), p.getBuffer());
            }
        }
    }
    
    @Override
    public void handle(final Throwable t) {
        this.manager.remove(this);
    }
    
    @Override
    public String getName() {
        return (this.name == null) ? (this.id + "") : this.name;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    static {
        ID = new AtomicInteger();
    }
}
