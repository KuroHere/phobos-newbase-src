// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.util;

import java.util.*;
import me.earth.earthhack.api.command.*;
import java.util.concurrent.*;

public class CommandDescriptions
{
    private static final Map<String, String> descriptions;
    
    public static void register(final Command command, final String description) {
        CommandDescriptions.descriptions.put(command.getName(), description);
    }
    
    public static String getDescription(final Command command) {
        return CommandDescriptions.descriptions.get(command.getName());
    }
    
    static {
        descriptions = new ConcurrentHashMap<String, String>();
    }
}
