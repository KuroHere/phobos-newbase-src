//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.raytrace;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

@FunctionalInterface
public interface CollisionFunction
{
    public static final CollisionFunction DEFAULT = IBlockProperties::collisionRayTrace;
    
    RayTraceResult collisionRayTrace(final IBlockState p0, final World p1, final BlockPos p2, final Vec3d p3, final Vec3d p4);
}
