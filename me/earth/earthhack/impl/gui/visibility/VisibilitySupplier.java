// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.visibility;

public interface VisibilitySupplier
{
    boolean isVisible();
    
    default VisibilitySupplier compose(final VisibilitySupplier other) {
        return this;
    }
}
