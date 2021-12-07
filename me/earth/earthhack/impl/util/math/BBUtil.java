//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

import net.minecraft.util.math.*;

public class BBUtil
{
    public static boolean intersects(final AxisAlignedBB bb, final Vec3i vec3i) {
        return bb.minX < vec3i.getX() + 1 && bb.maxX > vec3i.getX() && bb.minY < vec3i.getY() + 1 && bb.maxY > vec3i.getY() && bb.minZ < vec3i.getZ() + 1 && bb.maxZ > vec3i.getZ();
    }
}
