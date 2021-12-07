// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketPlayerPosLook.class })
public interface ISPacketPlayerPosLook
{
    @Accessor("teleportId")
    int getTeleportId();
    
    @Accessor("x")
    double getX();
    
    @Accessor("y")
    double getY();
    
    @Accessor("z")
    double getZ();
    
    @Accessor("yaw")
    void setYaw(final float p0);
    
    @Accessor("pitch")
    void setPitch(final float p0);
}
