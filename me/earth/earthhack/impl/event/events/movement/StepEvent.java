// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import net.minecraft.util.math.*;
import me.earth.earthhack.api.event.events.*;

public class StepEvent extends StageEvent
{
    private final AxisAlignedBB bb;
    private float height;
    
    public StepEvent(final Stage stage, final AxisAlignedBB bb, final float height) {
        super(stage);
        this.height = height;
        this.bb = bb;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        if (this.getStage() == Stage.PRE) {
            this.height = height;
        }
    }
    
    public AxisAlignedBB getBB() {
        return this.bb;
    }
}
