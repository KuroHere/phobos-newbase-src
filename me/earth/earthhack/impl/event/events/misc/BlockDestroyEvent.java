// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import net.minecraft.util.math.*;
import me.earth.earthhack.api.event.events.*;

public class BlockDestroyEvent extends StageEvent
{
    private final BlockPos pos;
    private boolean used;
    
    public BlockDestroyEvent(final Stage stage, final BlockPos pos) {
        super(stage);
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public boolean isUsed() {
        return this.used;
    }
    
    public void setUsed(final boolean used) {
        this.used = used;
    }
}
