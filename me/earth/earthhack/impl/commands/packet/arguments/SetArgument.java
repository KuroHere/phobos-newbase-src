// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class SetArgument extends AbstractArgument<Set>
{
    public SetArgument() {
        super(Set.class);
    }
    
    @Override
    public Set fromString(final String argument) throws ArgParseException {
        return Collections.EMPTY_SET;
    }
}
