// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;

public class CUnsupportedHandler implements IPacketHandler
{
    private final ILogger logger;
    private final int id;
    
    public CUnsupportedHandler(final ILogger logger, final int id) {
        this.logger = logger;
        this.id = id;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        this.logger.log("Received packet with unsupported id: " + this.id);
    }
}
