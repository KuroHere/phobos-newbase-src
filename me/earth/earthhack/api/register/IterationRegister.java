// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.register.exception.*;
import java.util.*;

public abstract class IterationRegister<T extends Nameable> implements Register<T>
{
    protected final List<T> registered;
    
    public IterationRegister() {
        this.registered = new ArrayList<T>();
    }
    
    @Override
    public void register(final T object) throws AlreadyRegisteredException {
        final T alreadyRegistered = this.getObject(object.getName());
        if (alreadyRegistered != null) {
            throw new AlreadyRegisteredException(object, alreadyRegistered);
        }
        this.registered.add(object);
        if (object instanceof Registrable) {
            ((Registrable)object).onRegister();
        }
    }
    
    @Override
    public void unregister(final T object) throws CantUnregisterException {
        if (object instanceof Registrable) {
            ((Registrable)object).onUnRegister();
        }
        this.registered.remove(object);
    }
    
    @Override
    public T getObject(String name) {
        name = name.toLowerCase();
        for (final T t : this.registered) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public <C extends T> C getByClass(final Class<C> clazz) {
        for (final T t : this.registered) {
            if (clazz == t.getClass()) {
                return (C)t;
            }
        }
        return null;
    }
    
    @Override
    public Collection<T> getRegistered() {
        return this.registered;
    }
}
