// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus.api;

public interface Listener<T> extends Invoker<T>
{
    int getPriority();
    
    Class<? super T> getTarget();
    
    Class<?> getType();
}
