// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class CollisionEvent extends Event
{
    private final Entity entity;
    private final BlockPos pos;
    private final Block block;
    private AxisAlignedBB bb;
    
    public CollisionEvent(final BlockPos pos, final AxisAlignedBB bb, final Entity entity, final Block block) {
        this.pos = pos;
        this.bb = bb;
        this.entity = entity;
        this.block = block;
    }
    
    public AxisAlignedBB getBB() {
        return this.bb;
    }
    
    public void setBB(final AxisAlignedBB bb) {
        this.bb = bb;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public interface Listener
    {
        void onCollision(final CollisionEvent p0);
    }
}
