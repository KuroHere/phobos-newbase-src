//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.path;

import me.earth.earthhack.impl.util.math.geocache.*;
import net.minecraft.util.math.*;
import java.util.*;

public class PathCache extends AbstractSphere
{
    public PathCache(final int expectedSize, final int indicesSize, final double radius) {
        super(expectedSize, indicesSize, radius);
    }
    
    @Override
    protected Collection<BlockPos> sorter(final BlockPos middle) {
        return new TreeSet<BlockPos>((o, p) -> {
            if (o.equals((Object)p)) {
                return 0;
            }
            else {
                final int xpDiff = middle.getX() - p.getX();
                final int ypDiff = middle.getY() - p.getY();
                final int zpDiff = middle.getZ() - p.getZ();
                final int xoDiff = middle.getX() - o.getX();
                final int yoDiff = middle.getY() - o.getY();
                final int zoDiff = middle.getZ() - o.getZ();
                final int compare = Integer.compare(PathFinder.produceOffsets(false, false, xoDiff, yoDiff, zoDiff).length, PathFinder.produceOffsets(false, false, xpDiff, ypDiff, zpDiff).length);
                if (compare != 0) {
                    return compare;
                }
                else {
                    int compare2 = Double.compare(middle.distanceSq((Vec3i)o), middle.distanceSq((Vec3i)p));
                    if (compare2 == 0) {
                        compare2 = Integer.compare(Math.abs(o.getX()) + Math.abs(o.getY()) + Math.abs(o.getZ()), Math.abs(p.getX()) + Math.abs(p.getY()) + Math.abs(p.getZ()));
                    }
                    return (compare2 == 0) ? 1 : compare2;
                }
            }
        });
    }
}
