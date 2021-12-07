// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.host;

import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import me.earth.earthhack.impl.modules.client.server.api.*;

public final class Host implements SafeRunnable, Globals, IHost
{
    private final IConnectionManager manager;
    private final ExecutorService service;
    private final IShutDownHandler module;
    private final ServerSocket socket;
    private final boolean receive;
    private Future<?> future;
    
    private Host(final IConnectionManager connectionManager, final ExecutorService service, final IShutDownHandler module, final int port, final boolean receive) throws IOException {
        this.socket = new ServerSocket(port);
        this.service = service;
        this.manager = connectionManager;
        this.module = module;
        this.receive = receive;
    }
    
    @Override
    public void runSafely() throws Throwable {
        while (!this.future.isCancelled()) {
            final Socket client = this.socket.accept();
            final Connection connection = new Connection(this.manager, client);
            if (!this.manager.accept(connection)) {
                client.close();
            }
            else {
                if (!this.receive) {
                    continue;
                }
                this.service.submit(connection);
            }
        }
    }
    
    @Override
    public void handle(final Throwable t) {
        this.module.disable(t.getMessage());
    }
    
    @Override
    public int getPort() {
        return this.socket.getLocalPort();
    }
    
    @Override
    public IConnectionManager getConnectionManager() {
        return this.manager;
    }
    
    @Override
    public void close() {
        if (this.future != null) {
            this.future.cancel(true);
        }
        if (this.isOpen()) {
            try {
                this.socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.manager.getConnections().forEach(ICloseable::close);
        this.manager.getConnections().clear();
    }
    
    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }
    
    public void setFuture(final Future<?> future) {
        this.future = future;
    }
    
    public static Host createAndStart(final ExecutorService service, final IConnectionManager manager, final IShutDownHandler module, final int port, final boolean receive) throws IOException {
        final Host host = new Host(manager, service, module, port, receive);
        host.setFuture(service.submit(host));
        return host;
    }
}
