// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus;

import me.earth.earthhack.api.event.bus.api.*;

public abstract class EventListener<T> implements Listener<T>
{
    private final Class<? super T> target;
    private final Class<?> type;
    private final int priority;
    
    public EventListener(final Class<? super T> target) {
        this(target, 10, null);
    }
    
    public EventListener(final Class<? super T> target, final Class<?> type) {
        this(target, 10, type);
    }
    
    public EventListener(final Class<? super T> target, final int priority) {
        this(target, priority, null);
    }
    
    public EventListener(final Class<? super T> target, final int priority, final Class<?> type) {
        this.priority = priority;
        this.target = target;
        this.type = type;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public Class<? super T> getTarget() {
        return this.target;
    }
    
    @Override
    public Class<?> getType() {
        return this.type;
    }
}
