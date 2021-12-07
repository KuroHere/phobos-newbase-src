// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.setting.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.*;
import java.util.*;

public class Caches
{
    private static final Map<Class<? extends Module>, ModuleCache<? extends Module>> MODULES;
    private static final Map<Class<?>, Map<SettingType<?>, SettingCache<?, ?, ?>>> SETTINGS;
    private static Register<Module> moduleManager;
    
    public static <M extends Module> ModuleCache<M> getModule(final Class<M> clazz) {
        return (ModuleCache)Caches.MODULES.computeIfAbsent(clazz, v -> new ModuleCache(Caches.moduleManager, clazz));
    }
    
    public static <T, S extends Setting<T>, E extends Module> SettingCache<T, S, E> getSetting(final Class<E> module, final Class<?> settingType, final String setting, final T defaultValue) {
        final Class<S> converted = (Class<S>)settingType;
        final Map<SettingType<?>, SettingCache<?, ?, ?>> inner = Caches.SETTINGS.computeIfAbsent((Class<?>)module, v -> new ConcurrentHashMap());
        final SettingType<S> type = new SettingType<S>(setting, converted);
        return (SettingCache)inner.computeIfAbsent(type, v -> {
            final Cache<Object> moduleCache = (Cache<Object>)getModule((Class<Module>)module);
            return SettingCache.newModuleSettingCache(setting, converted, moduleCache, defaultValue);
        });
    }
    
    public static void setManager(final Register<Module> moduleManagerIn) {
        Caches.moduleManager = moduleManagerIn;
        for (final Map.Entry<Class<? extends Module>, ModuleCache<?>> entry : Caches.MODULES.entrySet()) {
            entry.getValue().setModuleManager(moduleManagerIn);
            entry.getValue().setFrozen(false);
            if (!entry.getValue().isPresent()) {
                Earthhack.getLogger().error("Module-Caches: couldn't make " + entry.getKey().getName() + " present.");
                entry.getValue().setFrozen(true);
            }
        }
        for (final Map.Entry<Class<?>, Map<SettingType<?>, SettingCache<?, ?, ?>>> entry2 : Caches.SETTINGS.entrySet()) {
            if (entry2.getValue() != null) {
                for (final Map.Entry<SettingType<?>, SettingCache<?, ?, ?>> entry3 : entry2.getValue().entrySet()) {
                    entry3.getValue().setFrozen(false);
                    if (!entry3.getValue().isPresent()) {
                        Earthhack.getLogger().error("Setting-Caches: couldn't make " + entry2.getKey().getName() + " - " + entry3.getKey().getName() + " (" + entry3.getKey().getType().getName() + ") present.");
                        entry3.getValue().setFrozen(true);
                    }
                }
            }
        }
    }
    
    static {
        MODULES = new ConcurrentHashMap<Class<? extends Module>, ModuleCache<? extends Module>>();
        SETTINGS = new ConcurrentHashMap<Class<?>, Map<SettingType<?>, SettingCache<?, ?, ?>>>();
    }
    
    private static final class SettingType<S extends Setting<?>>
    {
        private final Class<S> type;
        private final String name;
        
        public SettingType(final String name, final Class<S> type) {
            this.name = name;
            this.type = type;
        }
        
        public Class<S> getType() {
            return this.type;
        }
        
        public String getName() {
            return this.name;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof SettingType && ((SettingType)o).type == this.type && this.name.equals(((SettingType)o).name);
        }
        
        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
