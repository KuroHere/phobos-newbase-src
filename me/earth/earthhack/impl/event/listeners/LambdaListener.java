// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.bus.api.*;

public class LambdaListener<E> extends EventListener<E>
{
    private final Invoker<E> invoker;
    
    public LambdaListener(final Class<? super E> target, final Invoker<E> invoker) {
        this(target, 10, invoker);
    }
    
    public LambdaListener(final Class<? super E> target, final Class<?> type, final Invoker<E> invoker) {
        this(target, 10, type, invoker);
    }
    
    public LambdaListener(final Class<? super E> target, final int priority, final Invoker<E> invoker) {
        this(target, priority, null, invoker);
    }
    
    public LambdaListener(final Class<? super E> target, final int priority, final Class<?> type, final Invoker<E> invoker) {
        super(target, priority, type);
        this.invoker = invoker;
    }
    
    @Override
    public void invoke(final E event) {
        this.invoker.invoke(event);
    }
}
