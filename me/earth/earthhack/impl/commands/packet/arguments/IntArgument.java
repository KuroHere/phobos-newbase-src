// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class IntArgument extends AbstractArgument<Integer>
{
    public IntArgument() {
        super(Integer.class);
    }
    
    @Override
    public Integer fromString(final String argument) throws ArgParseException {
        try {
            return (int)Long.parseLong(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to Integer!");
        }
    }
}
