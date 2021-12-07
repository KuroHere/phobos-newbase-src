// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketExplosion.class })
public interface ISPacketExplosion
{
    @Accessor("motionX")
    void setX(final float p0);
    
    @Accessor("motionY")
    void setY(final float p0);
    
    @Accessor("motionZ")
    void setZ(final float p0);
    
    @Accessor("motionX")
    float getX();
    
    @Accessor("motionY")
    float getY();
    
    @Accessor("motionZ")
    float getZ();
}
