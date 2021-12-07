// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch;

import org.objectweb.asm.tree.*;

public class FinishingPatch extends AbstractPatch
{
    private boolean finished;
    
    public FinishingPatch(final String name, final String transformed) {
        super(name, transformed);
    }
    
    @Override
    public void apply(final ClassNode node) {
        this.setFinished(true);
    }
    
    @Override
    public boolean isFinished() {
        return this.finished;
    }
    
    protected void setFinished(final boolean finished) {
        this.finished = finished;
    }
}
