// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;

public class ServerListHandler implements IPacketHandler
{
    private final IServerList serverList;
    
    public ServerListHandler(final IServerList serverList) {
        this.serverList = serverList;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        final IConnectionEntry[] list = ProtocolUtil.deserializeServerList(bytes);
        this.serverList.set(list);
    }
}
