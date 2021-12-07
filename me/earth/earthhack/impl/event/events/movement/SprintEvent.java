// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import me.earth.earthhack.api.event.events.*;

public class SprintEvent extends Event
{
    private boolean sprinting;
    
    public SprintEvent(final boolean sprinting) {
        this.sprinting = sprinting;
    }
    
    public void setSprinting(final boolean sprinting) {
        this.sprinting = sprinting;
    }
    
    public boolean isSprinting() {
        return this.sprinting;
    }
}
