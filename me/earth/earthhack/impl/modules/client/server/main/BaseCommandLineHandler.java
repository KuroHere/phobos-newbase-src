// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.modules.client.server.main.command.handlers.*;
import java.util.*;
import me.earth.earthhack.impl.modules.client.server.main.command.*;

public class BaseCommandLineHandler extends CommandLineManager
{
    private final ICloseable closeable;
    
    public BaseCommandLineHandler(final ICloseable closeable) {
        this.closeable = closeable;
        this.add("exit", new ExitCommand(closeable));
        this.add("stop", new ExitCommand(closeable));
        this.add("bye", new ExitCommand(closeable));
    }
    
    public void startListening() {
        final Thread thread = Thread.currentThread();
        try (final Scanner scanner = new Scanner(System.in)) {
            while (!thread.isInterrupted() && this.closeable.isOpen()) {
                final String input = scanner.nextLine();
                try {
                    this.handle(input);
                }
                catch (CommandException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
