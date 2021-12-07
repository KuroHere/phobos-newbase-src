// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import java.io.*;

public class SUnsupportedHandler implements IPacketHandler
{
    private final String message;
    
    public SUnsupportedHandler(final String message) {
        this.message = message;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        ProtocolUtil.sendMessage(connection, 2, this.message);
    }
}
