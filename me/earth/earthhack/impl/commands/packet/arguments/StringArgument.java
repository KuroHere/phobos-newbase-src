// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class StringArgument extends AbstractArgument<String>
{
    public StringArgument() {
        super(String.class);
    }
    
    @Override
    public String fromString(final String argument) throws ArgParseException {
        return argument;
    }
}
