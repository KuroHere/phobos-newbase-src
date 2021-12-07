// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting;

import java.util.*;

public class SettingContainer
{
    private Map<String, Setting<?>> settings;
    
    public SettingContainer() {
        this.settings = new LinkedHashMap<String, Setting<?>>();
    }
    
    public <T, S extends Setting<T>> S register(final S setting) {
        if (setting != null) {
            setting.setContainer(this);
            this.settings.put(setting.getName().toLowerCase(), setting);
            return setting;
        }
        return null;
    }
    
    public Setting<?> unregister(final Setting<?> setting) {
        return (setting == null) ? null : this.settings.remove(setting.getName().toLowerCase());
    }
    
    public final Setting<?> getSetting(final String name) {
        return this.settings.get(name.toLowerCase());
    }
    
    public <T, S extends Setting<T>> S getSetting(final String name, final Class<?> clazz) {
        final Setting<?> setting = this.settings.get(name.toLowerCase());
        if (clazz.isInstance(setting)) {
            return (S)setting;
        }
        return null;
    }
    
    public <T, S extends Setting<T>> S getSetting(final String name, final Class<S> clazz, final Class<T> type) {
        final Setting<?> setting = this.settings.get(name.toLowerCase());
        if (clazz.isInstance(setting) && setting.getInitial().getClass() == type) {
            return (S)setting;
        }
        return null;
    }
    
    public Setting<?> getSettingConfig(final String name) {
        return this.settings.get(name.toLowerCase());
    }
    
    public Collection<Setting<?>> getSettings() {
        return Collections.unmodifiableCollection((Collection<? extends Setting<?>>)this.settings.values());
    }
    
    public <T, S extends Setting<T>> S registerBefore(final S setting, final Setting<?> before) {
        return this.registerAt(setting, before, true);
    }
    
    public <T, S extends Setting<T>> S registerAfter(final S setting, final Setting<?> after) {
        return this.registerAt(setting, after, false);
    }
    
    private <T, S extends Setting<T>> S registerAt(final S setting, final Setting<?> target, final boolean before) {
        if (setting != null) {
            setting.setContainer(this);
            final Map<String, Setting<?>> newSettings = new LinkedHashMap<String, Setting<?>>();
            for (final Map.Entry<String, Setting<?>> entry : this.settings.entrySet()) {
                final boolean found = entry.getValue().equals(target);
                if (found && before) {
                    newSettings.put(setting.getName().toLowerCase(), setting);
                }
                newSettings.put(entry.getKey(), entry.getValue());
                if (found && !before) {
                    newSettings.put(setting.getName().toLowerCase(), setting);
                }
            }
            this.settings = newSettings;
            return setting;
        }
        return null;
    }
}
