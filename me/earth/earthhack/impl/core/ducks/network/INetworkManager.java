// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.network;

import net.minecraft.network.*;

public interface INetworkManager
{
    Packet<?> sendPacketNoEvent(final Packet<?> p0);
    
    Packet<?> sendPacketNoEvent(final Packet<?> p0, final boolean p1);
}
