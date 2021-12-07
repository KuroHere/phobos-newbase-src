// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketCustomPayload.class })
public interface ICPacketCustomPayload
{
    @Accessor("data")
    void setData(final PacketBuffer p0);
}
