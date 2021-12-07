// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet;

import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public interface PacketArgument<T>
{
    T fromString(final String p0) throws ArgParseException;
    
    PossibleInputs getPossibleInputs(final String p0);
    
    CustomCompleterResult onTabComplete(final Completer p0);
    
    Class<? super T> getType();
    
    String getSimpleName();
}
