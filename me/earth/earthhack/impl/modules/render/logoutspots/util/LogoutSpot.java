//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;

public class LogoutSpot implements Globals
{
    private final String name;
    private final AxisAlignedBB boundingBox;
    private final double x;
    private final double y;
    private final double z;
    
    public LogoutSpot(final EntityPlayer player) {
        this.name = player.getName();
        this.boundingBox = player.getEntityBoundingBox();
        this.x = player.posX;
        this.y = player.posY;
        this.z = player.posZ;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public double getDistance() {
        return LogoutSpot.mc.player.getDistance(this.x, this.y, this.z);
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public Vec3d rounded() {
        return new Vec3d(MathUtil.round(this.x, 1), MathUtil.round(this.y, 1), MathUtil.round(this.z, 1));
    }
}
