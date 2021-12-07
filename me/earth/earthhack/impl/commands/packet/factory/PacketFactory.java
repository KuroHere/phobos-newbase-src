// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.factory;

import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public interface PacketFactory
{
    Packet<?> create(final Class<? extends Packet<?>> p0, final String[] p1) throws ArgParseException;
    
    PossibleInputs getInputs(final Class<? extends Packet<?>> p0, final String[] p1);
    
    CustomCompleterResult onTabComplete(final Completer p0);
}
