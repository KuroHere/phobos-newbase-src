//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

import net.minecraft.util.math.*;

public class MutableBB extends AxisAlignedBB
{
    protected double minX;
    protected double minY;
    protected double minZ;
    protected double maxX;
    protected double maxY;
    protected double maxZ;
    
    public MutableBB() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public MutableBB(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
}
