// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.*;

public class ReachEvent extends Event
{
    private float reach;
    private float hitBox;
    
    public ReachEvent(final float reach, final float hitBox) {
        this.reach = reach;
        this.hitBox = hitBox;
    }
    
    public float getReach() {
        return this.reach;
    }
    
    public void setReach(final float reach) {
        this.reach = reach;
    }
    
    public float getHitBox() {
        return this.hitBox;
    }
    
    public void setHitBox(final float hitBox) {
        this.hitBox = hitBox;
    }
}
