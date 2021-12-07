// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.path;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import java.util.*;

public class BasePath implements Pathable
{
    private final List<BlockingEntity> blocking;
    private final int maxLength;
    private final BlockPos pos;
    private final Entity from;
    private boolean valid;
    private Ray[] path;
    
    public BasePath(final Entity from, final BlockPos pos, final int maxLength) {
        this.blocking = new ArrayList<BlockingEntity>();
        this.from = from;
        this.pos = pos;
        this.maxLength = maxLength;
    }
    
    @Override
    public BlockPos getPos() {
        return this.pos;
    }
    
    @Override
    public Entity getFrom() {
        return this.from;
    }
    
    @Override
    public Ray[] getPath() {
        return this.path;
    }
    
    @Override
    public void setPath(final Ray... path) {
        this.path = path;
    }
    
    @Override
    public int getMaxLength() {
        return this.maxLength;
    }
    
    @Override
    public boolean isValid() {
        return this.valid;
    }
    
    @Override
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
    
    @Override
    public List<BlockingEntity> getBlockingEntities() {
        return this.blocking;
    }
}
