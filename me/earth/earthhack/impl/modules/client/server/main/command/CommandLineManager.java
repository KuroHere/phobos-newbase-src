// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main.command;

import java.util.*;

public class CommandLineManager implements ICommandLineHandler
{
    private final Map<String, ICommandHandler> handlers;
    
    public CommandLineManager() {
        this.handlers = new HashMap<String, ICommandHandler>();
    }
    
    @Override
    public void handle(final String line) throws CommandException {
        final String[] command = line.split(" ", 2);
        if (command.length < 1) {
            throw new CommandException("Your command was empty...");
        }
        final ICommandHandler handler = this.handlers.get(command[0].toLowerCase());
        if (handler == null) {
            throw new CommandException("Unknown command: " + command[0] + ".");
        }
        handler.handle((command.length == 1) ? "" : command[1]);
    }
    
    @Override
    public void add(final String command, final ICommandHandler handler) {
        this.handlers.put(command.toLowerCase(), handler);
    }
}
