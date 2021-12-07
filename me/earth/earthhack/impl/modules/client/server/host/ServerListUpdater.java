// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.host;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import java.io.*;
import java.util.*;

public final class ServerListUpdater implements IConnectionListener
{
    @Override
    public void onJoin(final IConnectionManager manager, final IConnection connection) {
        this.updateServerList(manager);
    }
    
    @Override
    public void onLeave(final IConnectionManager manager, final IConnection connection) {
        this.updateServerList(manager);
    }
    
    private void updateServerList(final IConnectionManager manager) {
        final byte[] serverList = ProtocolUtil.serializeServerList(manager);
        for (final IConnection connection : manager.getConnections()) {
            try {
                connection.send(serverList);
            }
            catch (IOException e) {
                manager.remove(connection);
            }
        }
    }
}
