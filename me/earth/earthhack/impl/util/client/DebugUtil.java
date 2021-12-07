//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.client;

import me.earth.earthhack.impl.util.text.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;

public class DebugUtil
{
    public static void debug(final BlockPos pos, final String message) {
        ChatUtil.sendMessage(pos.getX() + "x, " + pos.getY() + "y, " + pos.getZ() + "z : " + message);
    }
    
    public static void debug(final Vec3d vec3d, final String message) {
        ChatUtil.sendMessage(MathUtil.round(vec3d.xCoord, 2) + "x, " + MathUtil.round(vec3d.yCoord, 2) + "y, " + MathUtil.round(vec3d.zCoord, 2) + "z : " + message);
    }
}
