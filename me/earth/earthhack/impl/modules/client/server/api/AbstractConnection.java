// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.net.*;
import java.io.*;

public abstract class AbstractConnection implements IConnection
{
    protected final Socket socket;
    protected String name;
    
    public AbstractConnection(final String ip, final int port) throws IOException {
        this(new Socket(ip, port));
    }
    
    public AbstractConnection(final Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public void send(final byte[] packet) throws IOException {
        this.socket.getOutputStream().write(packet);
    }
    
    @Override
    public void close() {
        try {
            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }
    
    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }
}
