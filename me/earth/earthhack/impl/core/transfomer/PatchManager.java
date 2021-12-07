// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer;

public interface PatchManager
{
    void addPatch(final Patch p0);
    
    byte[] transform(final String p0, final String p1, final byte[] p2);
}
