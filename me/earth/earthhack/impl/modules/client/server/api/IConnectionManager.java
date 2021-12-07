// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.util.*;

public interface IConnectionManager extends ISender
{
    IPacketManager getHandler();
    
    boolean accept(final IConnection p0);
    
    void remove(final IConnection p0);
    
    List<IConnection> getConnections();
    
    void addListener(final IConnectionListener p0);
    
    void removeListener(final IConnectionListener p0);
}
