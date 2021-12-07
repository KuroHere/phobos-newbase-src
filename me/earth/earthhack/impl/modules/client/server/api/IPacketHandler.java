// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.io.*;

public interface IPacketHandler
{
    void handle(final IConnection p0, final byte[] p1) throws IOException;
}
