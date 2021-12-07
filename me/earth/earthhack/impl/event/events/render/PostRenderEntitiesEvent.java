// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

public class PostRenderEntitiesEvent
{
    private final float partialTicks;
    private final int pass;
    
    public PostRenderEntitiesEvent(final float partialTicks, final int pass) {
        this.partialTicks = partialTicks;
        this.pass = pass;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public int getPass() {
        return this.pass;
    }
}
