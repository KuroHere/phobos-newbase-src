// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.io.*;

public interface IPacketManager
{
    void handle(final IConnection p0, final int p1, final byte[] p2) throws UnknownProtocolException, IOException;
    
    void add(final int p0, final IPacketHandler p1);
    
    IPacketHandler getHandlerFor(final int p0);
}
