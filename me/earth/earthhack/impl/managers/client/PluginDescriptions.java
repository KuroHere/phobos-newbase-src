// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import java.util.*;
import me.earth.earthhack.api.plugin.*;
import java.util.concurrent.*;

public class PluginDescriptions
{
    private static final Map<Plugin, String> DESCRIPTIONS;
    
    public static void register(final Plugin plugin, final String description) {
        PluginDescriptions.DESCRIPTIONS.put(plugin, description);
    }
    
    public static String getDescription(final Plugin plugin) {
        return PluginDescriptions.DESCRIPTIONS.get(plugin);
    }
    
    static {
        DESCRIPTIONS = new ConcurrentHashMap<Plugin, String>();
    }
}
