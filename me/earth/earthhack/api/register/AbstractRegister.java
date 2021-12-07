// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.register.exception.*;
import java.util.*;

public abstract class AbstractRegister<T extends Nameable> implements Register<T>
{
    protected final Map<String, T> registered;
    
    public AbstractRegister() {
        this((Map)new ConcurrentHashMap());
    }
    
    public AbstractRegister(final Map<String, T> map) {
        this.registered = map;
    }
    
    @Override
    public void register(final T object) throws AlreadyRegisteredException {
        final T alreadyRegistered = this.getObject(object.getName());
        if (alreadyRegistered != null) {
            throw new AlreadyRegisteredException(object, alreadyRegistered);
        }
        if (object instanceof Registrable) {
            ((Registrable)object).onRegister();
        }
        this.registered.put(object.getName().toLowerCase(), object);
    }
    
    @Override
    public void unregister(final T object) throws CantUnregisterException {
        if (object instanceof Registrable) {
            ((Registrable)object).onUnRegister();
        }
        for (final Map.Entry<String, T> entry : this.registered.entrySet()) {
            if (object.equals(entry.getValue())) {
                this.registered.remove(entry.getKey());
            }
        }
    }
    
    @Override
    public T getObject(final String name) {
        return this.registered.get(name.toLowerCase());
    }
    
    @Override
    public <C extends T> C getByClass(final Class<C> clazz) {
        for (final Map.Entry<String, T> entry : this.registered.entrySet()) {
            if (clazz.isInstance(entry.getValue())) {
                return (C)entry.getValue();
            }
        }
        return null;
    }
    
    @Override
    public Collection<T> getRegistered() {
        return this.registered.values();
    }
}
