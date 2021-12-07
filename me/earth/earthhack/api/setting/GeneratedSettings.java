// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting;

import java.util.*;

public class GeneratedSettings
{
    private static final Map<SettingContainer, Set<Setting<?>>> GENERATED;
    
    private GeneratedSettings() {
        throw new AssertionError();
    }
    
    public static void add(final SettingContainer container, final Setting<?> setting) {
        GeneratedSettings.GENERATED.computeIfAbsent(container, v -> new HashSet()).add(setting);
    }
    
    public static Set<Setting<?>> getGenerated(final SettingContainer container) {
        return GeneratedSettings.GENERATED.getOrDefault(container, new HashSet<Setting<?>>());
    }
    
    public static void clear(final SettingContainer container) {
        GeneratedSettings.GENERATED.remove(container);
    }
    
    static {
        GENERATED = new HashMap<SettingContainer, Set<Setting<?>>>();
    }
}
