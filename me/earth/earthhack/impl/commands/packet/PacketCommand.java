// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet;

import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.generic.*;
import me.earth.earthhack.impl.commands.packet.factory.*;
import java.util.*;

public interface PacketCommand
{
    Class<? extends Packet<?>> getPacket(final String p0);
    
    Map<Class<? extends Packet<?>>, List<GenericArgument<?>>> getGenerics();
    
    Map<Class<? extends Packet<?>>, PacketFactory> getCustom();
    
    Set<Class<? extends Packet<?>>> getPackets();
    
    Map<Class<?>, PacketArgument<?>> getArguments();
    
    String getName(final Class<? extends Packet<?>> p0);
}
