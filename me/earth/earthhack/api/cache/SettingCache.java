// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.cache;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.*;

public class SettingCache<T, S extends Setting<T>, E extends SettingContainer> extends Cache<S>
{
    private final Cache<E> container;
    private T defaultValue;
    
    private SettingCache(final Cache<E> container) {
        this.container = container;
    }
    
    public void setDefaultValue(final T value) {
        this.defaultValue = value;
    }
    
    public T getValue() {
        return this.returnIfPresent(Setting::getValue, this.defaultValue);
    }
    
    public E getContainer() {
        return this.container.get();
    }
    
    public void setContainer(final E container) {
        this.container.set(container);
    }
    
    public static <T, S extends Setting<T>, E extends Module> SettingCache<T, S, E> newModuleSettingCache(final String name, final Class<?> type, final Cache<E> module, final T defaultValue) {
        final Class<S> converted = (Class<S>)type;
        final SettingCache<T, S, E> result = new SettingCache<T, S, E>(module);
        result.setDefaultValue(defaultValue);
        result.getter = (() -> result.container.returnIfPresent(c -> c.getSetting(name, converted), null));
        return result;
    }
}
