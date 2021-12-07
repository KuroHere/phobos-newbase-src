// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

public class MutableWrapper<T>
{
    protected T value;
    
    public MutableWrapper() {
        this(null);
    }
    
    public MutableWrapper(final T value) {
        this.value = value;
    }
    
    public T get() {
        return this.value;
    }
    
    public void set(final T value) {
        this.value = value;
    }
}
