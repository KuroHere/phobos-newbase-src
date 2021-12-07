//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.geocache;

import net.minecraft.util.math.*;
import java.util.*;

public class SphereCache extends AbstractSphere
{
    private static final SphereCache INSTANCE;
    
    private SphereCache() {
        super(4187707, 101, 100.0);
    }
    
    public static SphereCache getInstance() {
        return SphereCache.INSTANCE;
    }
    
    @Override
    protected Collection<BlockPos> sorter(final BlockPos middle) {
        return new TreeSet<BlockPos>((o, p) -> {
            if (o.equals((Object)p)) {
                return 0;
            }
            else {
                int compare = Double.compare(middle.distanceSq((Vec3i)o), middle.distanceSq((Vec3i)p));
                if (compare == 0) {
                    compare = Integer.compare(Math.abs(o.getX()) + Math.abs(o.getY()) + Math.abs(o.getZ()), Math.abs(p.getX()) + Math.abs(p.getY()) + Math.abs(p.getZ()));
                }
                return (compare == 0) ? 1 : compare;
            }
        });
    }
    
    static {
        (INSTANCE = new SphereCache()).cache();
    }
}
