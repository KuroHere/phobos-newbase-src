// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main.command.handlers;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.modules.client.server.main.command.*;

public class ExitCommand implements ICommandHandler
{
    private final ICloseable closeable;
    
    public ExitCommand(final ICloseable closeable) {
        this.closeable = closeable;
    }
    
    @Override
    public void handle(final String command) throws CommandException {
        System.out.println("Bye!");
        this.closeable.close();
        System.exit(0);
    }
}
