// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus.api;

public interface EventBus
{
    public static final int DEFAULT_PRIORITY = 10;
    
    void post(final Object p0);
    
    void post(final Object p0, final Class<?> p1);
    
    boolean postCancellable(final ICancellable p0);
    
    boolean postCancellable(final ICancellable p0, final Class<?> p1);
    
    void postReversed(final Object p0, final Class<?> p1);
    
    void subscribe(final Object p0);
    
    void unsubscribe(final Object p0);
    
    void register(final Listener<?> p0);
    
    void unregister(final Listener<?> p0);
    
    boolean isSubscribed(final Object p0);
    
    boolean hasSubscribers(final Class<?> p0);
    
    boolean hasSubscribers(final Class<?> p0, final Class<?> p1);
}
