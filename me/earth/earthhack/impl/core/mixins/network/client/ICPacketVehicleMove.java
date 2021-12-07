// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketVehicleMove.class })
public interface ICPacketVehicleMove
{
    @Accessor("y")
    void setY(final double p0);
    
    @Accessor("x")
    void setX(final double p0);
    
    @Accessor("z")
    void setZ(final double p0);
}
