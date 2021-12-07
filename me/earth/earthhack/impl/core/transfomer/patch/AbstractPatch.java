// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch;

import me.earth.earthhack.impl.core.transfomer.*;

public abstract class AbstractPatch implements Patch
{
    private final String name;
    private final String transformed;
    
    public AbstractPatch(final String name, final String transformed) {
        this.name = name;
        this.transformed = transformed;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getTransformedName() {
        return this.transformed;
    }
}
