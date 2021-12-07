// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

public class AspectRatioEvent
{
    private float aspectRatio;
    
    public AspectRatioEvent(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
    
    public float getAspectRatio() {
        return this.aspectRatio;
    }
    
    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}
