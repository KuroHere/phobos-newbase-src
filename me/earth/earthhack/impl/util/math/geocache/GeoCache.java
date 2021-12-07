// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.geocache;

import net.minecraft.util.math.*;

public interface GeoCache
{
    void cache();
    
    int getRadius(final double p0);
    
    Vec3i get(final int p0);
    
    Vec3i[] array();
}
