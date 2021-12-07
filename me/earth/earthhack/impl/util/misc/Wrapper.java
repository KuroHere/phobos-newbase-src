// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

public class Wrapper<T>
{
    protected final T value;
    
    public Wrapper(final T value) {
        this.value = value;
    }
    
    public T get() {
        return this.value;
    }
}
