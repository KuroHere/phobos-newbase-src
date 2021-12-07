// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.keyboard;

import me.earth.earthhack.api.event.events.*;

public class ClickMiddleEvent extends Event
{
    private boolean moduleCancelled;
    
    public void setModuleCancelled(final boolean cancelled) {
        this.moduleCancelled = cancelled;
    }
    
    public boolean isModuleCancelled() {
        return this.moduleCancelled;
    }
}
