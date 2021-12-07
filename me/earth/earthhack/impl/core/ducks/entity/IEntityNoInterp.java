// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

public interface IEntityNoInterp
{
    double getNoInterpX();
    
    double getNoInterpY();
    
    double getNoInterpZ();
    
    void setNoInterpX(final double p0);
    
    void setNoInterpY(final double p0);
    
    void setNoInterpZ(final double p0);
    
    int getPosIncrements();
    
    void setPosIncrements(final int p0);
    
    float getNoInterpSwingAmount();
    
    float getNoInterpSwing();
    
    float getNoInterpPrevSwing();
    
    void setNoInterpSwingAmount(final float p0);
    
    void setNoInterpSwing(final float p0);
    
    void setNoInterpPrevSwing(final float p0);
    
    boolean isNoInterping();
    
    void setNoInterping(final boolean p0);
}
