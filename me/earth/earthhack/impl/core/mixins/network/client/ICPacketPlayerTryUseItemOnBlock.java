// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.*;

@Mixin({ CPacketPlayerTryUseItemOnBlock.class })
public interface ICPacketPlayerTryUseItemOnBlock
{
    @Accessor("placedBlockDirection")
    void setFacing(final EnumFacing p0);
    
    @Accessor("hand")
    void setHand(final EnumHand p0);
}
