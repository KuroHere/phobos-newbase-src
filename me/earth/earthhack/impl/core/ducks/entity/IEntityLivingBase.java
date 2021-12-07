// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

public interface IEntityLivingBase
{
    int armSwingAnimationEnd();
    
    int getTicksSinceLastSwing();
    
    int getActiveItemStackUseCount();
    
    void setTicksSinceLastSwing(final int p0);
    
    void setActiveItemStackUseCount(final int p0);
    
    boolean getElytraFlag();
    
    void setLowestDura(final float p0);
    
    float getLowestDurability();
}
