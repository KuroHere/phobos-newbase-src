// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import java.nio.charset.*;

public class NameHandler implements IPacketHandler
{
    private final ILogger logger;
    
    public NameHandler(final ILogger logger) {
        this.logger = logger;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) {
        final String name = new String(bytes, StandardCharsets.UTF_8);
        this.logger.log("Connection: " + connection.getId() + " previously (" + connection.getName() + ") set it's name to: " + name + ".");
        connection.setName(name);
    }
}
