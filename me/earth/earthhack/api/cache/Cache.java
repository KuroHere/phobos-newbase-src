// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.cache;

import java.util.function.*;

public class Cache<T>
{
    protected Supplier<T> getter;
    protected boolean frozen;
    protected T cached;
    
    protected Cache() {
        this.getter = (Supplier<T>)(() -> null);
    }
    
    public Cache(final T value) {
        this.getter = (Supplier<T>)(() -> value);
        this.cached = value;
    }
    
    public Cache(final Supplier<T> getter) {
        this.getter = getter;
    }
    
    public Cache<T> set(final T value) {
        this.cached = value;
        return this;
    }
    
    public T get() {
        if (this.isPresent()) {
            return this.cached;
        }
        return null;
    }
    
    public boolean isPresent() {
        if (this.cached == null && !this.frozen) {
            this.cached = this.getter.get();
        }
        return this.cached != null;
    }
    
    public boolean computeIfPresent(final Consumer<T> consumer) {
        if (this.isPresent()) {
            consumer.accept(this.cached);
            return true;
        }
        return false;
    }
    
    public <E> E returnIfPresent(final Function<T, E> function, final E defaultValue) {
        if (this.isPresent()) {
            return function.apply(this.cached);
        }
        return defaultValue;
    }
    
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
}
