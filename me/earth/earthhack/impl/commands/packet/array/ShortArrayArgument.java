// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.array;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;

public class ShortArrayArgument extends AbstractArgument<short[]>
{
    private static final PacketArgument<Short> PARSER;
    
    public ShortArrayArgument() {
        super(short[].class);
    }
    
    @Override
    public short[] fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split("]");
        final short[] array = new short[split.length];
        for (int i = 0; i < split.length; ++i) {
            array[i] = ShortArrayArgument.PARSER.fromString(split[i]);
        }
        return array;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return PossibleInputs.empty().setRest("<Short]Short...>");
        }
        return PossibleInputs.empty();
    }
    
    static {
        PARSER = new ShortArgument();
    }
}
