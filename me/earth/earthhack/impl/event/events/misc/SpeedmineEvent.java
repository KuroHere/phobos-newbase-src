// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import net.minecraft.util.math.*;

public class SpeedmineEvent
{
    private final BlockPos pos;
    
    public SpeedmineEvent(final BlockPos pos) {
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
