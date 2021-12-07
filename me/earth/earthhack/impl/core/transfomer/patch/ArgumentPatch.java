// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch;

import org.objectweb.asm.tree.*;
import me.earth.earthhack.tweaker.launch.*;

public abstract class ArgumentPatch extends FinishingPatch
{
    private final String argument;
    
    public ArgumentPatch(final String name, final String transformed, final String argument) {
        super(name, transformed);
        this.argument = argument;
    }
    
    protected abstract void applyPatch(final ClassNode p0);
    
    @Override
    public void apply(final ClassNode node) {
        final ArgumentManager dev = DevArguments.getInstance();
        final Argument<Boolean> arg = dev.getArgument(this.argument);
        if (arg != null && !arg.getValue()) {
            this.setFinished(true);
            return;
        }
        this.applyPatch(node);
    }
}
