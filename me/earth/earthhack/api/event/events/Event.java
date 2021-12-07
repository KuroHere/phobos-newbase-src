// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.events;

import me.earth.earthhack.api.event.bus.api.*;

public class Event implements ICancellable
{
    private boolean cancelled;
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
