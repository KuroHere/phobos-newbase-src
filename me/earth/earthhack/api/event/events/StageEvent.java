// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.events;

public class StageEvent extends Event
{
    private final Stage stage;
    
    public StageEvent(final Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return this.stage;
    }
}
