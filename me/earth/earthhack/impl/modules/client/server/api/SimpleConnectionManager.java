// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class SimpleConnectionManager implements IConnectionManager
{
    private final IPacketManager packetManager;
    private final List<IConnectionListener> listeners;
    private final List<IConnection> connections;
    private final int maxConnections;
    
    public SimpleConnectionManager(final IPacketManager packetManager, final int maxConnections) {
        this.packetManager = packetManager;
        this.maxConnections = maxConnections;
        this.connections = new CopyOnWriteArrayList<IConnection>();
        this.listeners = new CopyOnWriteArrayList<IConnectionListener>();
    }
    
    @Override
    public IPacketManager getHandler() {
        return this.packetManager;
    }
    
    @Override
    public boolean accept(final IConnection client) {
        if (this.connections.size() >= this.maxConnections) {
            return false;
        }
        this.connections.add(client);
        for (final IConnectionListener listener : this.listeners) {
            if (listener != null) {
                listener.onJoin(this, client);
            }
        }
        return true;
    }
    
    @Override
    public void remove(final IConnection connection) {
        if (connection.isOpen()) {
            connection.close();
        }
        this.connections.remove(connection);
        for (final IConnectionListener listener : this.listeners) {
            if (listener != null) {
                listener.onLeave(this, connection);
            }
        }
    }
    
    @Override
    public List<IConnection> getConnections() {
        return this.connections;
    }
    
    @Override
    public void addListener(final IConnectionListener listener) {
        this.listeners.add(listener);
    }
    
    @Override
    public void removeListener(final IConnectionListener listener) {
        this.listeners.remove(listener);
    }
    
    @Override
    public void send(final byte[] packet) throws IOException {
        for (final IConnection connection : this.connections) {
            try {
                connection.send(packet);
            }
            catch (IOException e) {
                this.remove(connection);
                e.printStackTrace();
            }
        }
    }
}
