// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc.intintmap;

public class Tools
{
    private static final int INT_PHI = -1640531527;
    
    public static long nextPowerOfTwo(long x) {
        if (x == 0L) {
            return 1L;
        }
        --x;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return (x | x >> 32) + 1L;
    }
    
    public static int arraySize(final int expected, final float f) {
        final long s = Math.max(2L, nextPowerOfTwo((long)Math.ceil(expected / f)));
        if (s > 1073741824L) {
            throw new IllegalArgumentException("Too large (" + expected + " expected elements with load factor " + f + ")");
        }
        return (int)s;
    }
    
    public static int phiMix(final int x) {
        final int h = x * -1640531527;
        return h ^ h >> 16;
    }
}
