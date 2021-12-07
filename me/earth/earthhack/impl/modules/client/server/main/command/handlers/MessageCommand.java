// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main.command.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.main.command.*;
import java.io.*;

public class MessageCommand implements ICommandHandler
{
    private final ISender sender;
    private final int id;
    
    public MessageCommand(final ISender sender, final int id) {
        this.sender = sender;
        this.id = id;
    }
    
    @Override
    public void handle(final String command) throws CommandException {
        try {
            this.sender.send(ProtocolUtil.writeString(this.id, command));
        }
        catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
