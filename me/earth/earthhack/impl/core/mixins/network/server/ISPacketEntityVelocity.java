// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketEntityVelocity.class })
public interface ISPacketEntityVelocity
{
    @Accessor("entityID")
    int getEntityID();
    
    @Accessor("motionX")
    int getX();
    
    @Accessor("motionX")
    void setX(final int p0);
    
    @Accessor("motionY")
    int getY();
    
    @Accessor("motionY")
    void setY(final int p0);
    
    @Accessor("motionZ")
    int getZ();
    
    @Accessor("motionZ")
    void setZ(final int p0);
}
