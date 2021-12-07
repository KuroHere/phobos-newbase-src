// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.network;

public interface IPlayerControllerMP
{
    void syncItem();
    
    int getItem();
    
    void setBlockHitDelay(final int p0);
    
    int getBlockHitDelay();
    
    float getCurBlockDamageMP();
    
    void setCurBlockDamageMP(final float p0);
    
    void setIsHittingBlock(final boolean p0);
    
    boolean getIsHittingBlock();
}
