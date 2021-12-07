// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.client;

import me.earth.earthhack.impl.util.thread.*;
import java.io.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.api.*;

public final class Client extends AbstractConnection implements SafeRunnable, IClient
{
    private final IPacketManager manager;
    private final IServerList serverList;
    
    public Client(final IPacketManager manager, final IServerList serverList, final String ip, final int port) throws IOException {
        super(ip, port);
        this.manager = manager;
        this.serverList = serverList;
    }
    
    @Override
    public void runSafely() throws Throwable {
        try (final DataInputStream in = new DataInputStream(this.getInputStream())) {
            while (this.isOpen()) {
                final IPacket packet = ProtocolUtil.readPacket(in);
                this.manager.handle(this, packet.getId(), packet.getBuffer());
            }
        }
    }
    
    @Override
    public void handle(final Throwable t) {
        t.printStackTrace();
        this.close();
    }
    
    @Override
    public IServerList getServerList() {
        return this.serverList;
    }
}
