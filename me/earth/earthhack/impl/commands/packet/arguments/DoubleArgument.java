// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class DoubleArgument extends AbstractArgument<Double>
{
    public DoubleArgument() {
        super(Double.class);
    }
    
    @Override
    public Double fromString(final String argument) throws ArgParseException {
        try {
            return Double.parseDouble(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to double!");
        }
    }
}
