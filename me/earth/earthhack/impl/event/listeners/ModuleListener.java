// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;

public abstract class ModuleListener<M, E> extends EventListener<E> implements Globals
{
    protected final M module;
    
    public ModuleListener(final M module, final Class<? super E> target) {
        this(module, target, 10);
    }
    
    public ModuleListener(final M module, final Class<? super E> target, final int priority) {
        this(module, target, priority, null);
    }
    
    public ModuleListener(final M module, final Class<? super E> target, final Class<?> type) {
        this(module, target, 10, type);
    }
    
    public ModuleListener(final M module, final Class<? super E> target, final int priority, final Class<?> type) {
        super(target, priority, type);
        this.module = module;
    }
}
