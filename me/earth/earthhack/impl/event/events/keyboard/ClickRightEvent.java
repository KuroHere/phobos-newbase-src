// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.keyboard;

import me.earth.earthhack.api.event.events.*;

public class ClickRightEvent extends Event
{
    private int delay;
    
    public ClickRightEvent(final int delay) {
        this.delay = delay;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public void setDelay(final int delay) {
        this.delay = delay;
    }
}
