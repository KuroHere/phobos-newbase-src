// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.generic;

import me.earth.earthhack.impl.commands.packet.*;
import java.lang.reflect.*;

public abstract class GenericArgument<T> extends AbstractArgument<T>
{
    private final Constructor<?> ctr;
    private final int argIndex;
    
    public GenericArgument(final Class<? super T> type, final Constructor<?> ctr, final int argIndex) {
        super(type);
        this.ctr = ctr;
        this.argIndex = argIndex;
    }
    
    public Constructor<?> getConstructor() {
        return this.ctr;
    }
    
    public int getArgIndex() {
        return this.argIndex;
    }
}
