// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import java.util.function.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.nio.charset.*;
import java.io.*;

public class MessageHandler implements IPacketHandler
{
    private final Function<String, String> format;
    private final ILogger logger;
    
    public MessageHandler(final ILogger logger) {
        this(logger, null);
    }
    
    public MessageHandler(final ILogger logger, final Function<String, String> format) {
        this.logger = logger;
        this.format = format;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        final String message = new String(bytes, StandardCharsets.UTF_8);
        if (this.format != null) {
            this.logger.log(this.format.apply(message));
        }
        else {
            this.logger.log(new String(bytes, StandardCharsets.UTF_8));
        }
    }
}
