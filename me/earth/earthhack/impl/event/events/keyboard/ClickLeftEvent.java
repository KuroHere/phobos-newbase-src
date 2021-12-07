// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.keyboard;

import me.earth.earthhack.api.event.events.*;

public class ClickLeftEvent extends Event
{
    private int leftClickCounter;
    
    public ClickLeftEvent(final int leftClickCounter) {
        this.leftClickCounter = leftClickCounter;
    }
    
    public int getLeftClickCounter() {
        return this.leftClickCounter;
    }
    
    public void setLeftClickCounter(final int leftClickCounter) {
        this.leftClickCounter = leftClickCounter;
    }
}
