// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.array;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public abstract class AbstractArrayArgument<T> extends AbstractArgument<T[]>
{
    protected final PacketArgument<T> parser;
    
    public AbstractArrayArgument(final Class<T[]> type, final PacketArgument<T> parser) {
        super(type);
        this.parser = parser;
    }
    
    protected abstract T[] create(final int p0);
    
    @Override
    public T[] fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split("]");
        final T[] array = this.create(split.length);
        for (int i = 0; i < split.length; ++i) {
            array[i] = this.parser.fromString(split[i]);
        }
        return array;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return PossibleInputs.empty().setRest("<" + this.parser.getPossibleInputs(null).getRest() + "]" + this.parser.getPossibleInputs(null).getRest() + "]...>");
        }
        final String[] split = argument.split("]");
        return this.parser.getPossibleInputs(split[split.length - 1]);
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
}
