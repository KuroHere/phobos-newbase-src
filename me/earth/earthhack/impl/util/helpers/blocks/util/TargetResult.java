// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.util;

import net.minecraft.util.math.*;
import java.util.*;

public class TargetResult
{
    private List<BlockPos> targets;
    private boolean valid;
    
    public TargetResult() {
        this.targets = new ArrayList<BlockPos>();
        this.valid = true;
    }
    
    public List<BlockPos> getTargets() {
        return this.targets;
    }
    
    public TargetResult setTargets(final List<BlockPos> targets) {
        this.targets = targets;
        return this;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public TargetResult setValid(final boolean valid) {
        this.valid = valid;
        return this;
    }
}
