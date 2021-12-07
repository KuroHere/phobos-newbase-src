// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.array;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;

public class IntArrayArgument extends AbstractArgument<int[]>
{
    private static final PacketArgument<Integer> PARSER;
    
    public IntArrayArgument() {
        super(int[].class);
    }
    
    @Override
    public int[] fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split("]");
        final int[] array = new int[split.length];
        for (int i = 0; i < split.length; ++i) {
            array[i] = IntArrayArgument.PARSER.fromString(split[i]);
        }
        return array;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return PossibleInputs.empty().setRest("<Integer]Integer...>");
        }
        return PossibleInputs.empty();
    }
    
    static {
        PARSER = new IntArgument();
    }
}
