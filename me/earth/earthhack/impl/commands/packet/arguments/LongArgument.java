// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class LongArgument extends AbstractArgument<Long>
{
    public LongArgument() {
        super(Long.class);
    }
    
    @Override
    public Long fromString(final String argument) throws ArgParseException {
        try {
            return Long.parseLong(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to Long!");
        }
    }
}
