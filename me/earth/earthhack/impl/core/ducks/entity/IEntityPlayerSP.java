// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

public interface IEntityPlayerSP
{
    double getLastReportedX();
    
    double getLastReportedY();
    
    double getLastReportedZ();
    
    float getLastReportedYaw();
    
    float getLastReportedPitch();
    
    boolean getLastOnGround();
    
    void setLastReportedYaw(final float p0);
    
    void setLastReportedPitch(final float p0);
    
    int getPositionUpdateTicks();
    
    void superUpdate();
    
    void invokeUpdateWalkingPlayer();
    
    void setHorseJumpPower(final float p0);
}
