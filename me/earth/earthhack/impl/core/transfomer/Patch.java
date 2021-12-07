// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer;

import org.objectweb.asm.tree.*;

public interface Patch
{
    String getName();
    
    String getTransformedName();
    
    void apply(final ClassNode p0);
    
    boolean isFinished();
}
