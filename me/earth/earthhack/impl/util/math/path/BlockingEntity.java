// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.path;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class BlockingEntity
{
    private final Entity entity;
    private final BlockPos pos;
    
    public BlockingEntity(final Entity entity, final BlockPos pos) {
        this.entity = entity;
        this.pos = pos;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public BlockPos getBlockedPos() {
        return this.pos;
    }
}
