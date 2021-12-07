//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly.util;

import net.minecraft.util.math.*;

public class TimeVec extends Vec3d
{
    private final long time;
    
    public TimeVec(final Vec3d vec3d) {
        this(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, System.currentTimeMillis());
    }
    
    public TimeVec(final double xIn, final double yIn, final double zIn, final long time) {
        super(xIn, yIn, zIn);
        this.time = time;
    }
    
    public long getTime() {
        return this.time;
    }
}
