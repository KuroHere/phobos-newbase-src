// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch.arguments;

import me.earth.earthhack.tweaker.launch.*;

public abstract class AbstractArgument<T> implements Argument<T>
{
    protected T value;
    
    public AbstractArgument(final T value) {
        this.value = value;
    }
    
    @Override
    public T getValue() {
        return this.value;
    }
}
