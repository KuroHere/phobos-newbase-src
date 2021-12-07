// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import net.minecraft.entity.*;

public class MoveEvent
{
    private final MoverType type;
    private double x;
    private double y;
    private double z;
    private boolean sneaking;
    
    public MoveEvent(final MoverType type, final double x, final double y, final double z, final boolean sneaking) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sneaking = sneaking;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
    
    public void setSneaking(final boolean sneaking) {
        this.sneaking = sneaking;
    }
}
