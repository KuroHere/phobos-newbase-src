// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ShortArgument extends AbstractArgument<Short>
{
    public ShortArgument() {
        super(Short.class);
    }
    
    @Override
    public Short fromString(final String argument) throws ArgParseException {
        try {
            return (short)Long.parseLong(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to short!");
        }
    }
}
