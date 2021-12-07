// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import net.minecraft.util.*;
import net.minecraft.block.*;

public class BlockLayerEvent
{
    private BlockRenderLayer layer;
    private final Block block;
    
    public BlockLayerEvent(final Block block) {
        this.layer = null;
        this.block = block;
    }
    
    public void setLayer(final BlockRenderLayer layer) {
        this.layer = layer;
    }
    
    public BlockRenderLayer getLayer() {
        return this.layer;
    }
    
    public Block getBlock() {
        return this.block;
    }
}
