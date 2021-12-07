// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class BlockRenderEvent
{
    private final BlockPos pos;
    private final IBlockState state;
    
    public BlockRenderEvent(final BlockPos pos, final IBlockState state) {
        this.pos = pos;
        this.state = state;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public IBlockState getState() {
        return this.state;
    }
}
