// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus.api;

public interface ICancellable
{
    void setCancelled(final boolean p0);
    
    boolean isCancelled();
}
