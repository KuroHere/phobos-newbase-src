// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.generic;

import me.earth.earthhack.impl.commands.packet.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.commands.packet.array.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public abstract class AbstractIterableArgument<T, S extends Iterable<T>> extends GenericArgument<S>
{
    private final PacketArgument<T> parser;
    
    public AbstractIterableArgument(final Class<? super S> type, final Constructor<?> ctr, final int argIndex, final PacketArgument<T> parser) {
        super(type, ctr, argIndex);
        this.parser = parser;
    }
    
    protected abstract S create(final T[] p0);
    
    @Override
    public S fromString(final String argument) throws ArgParseException {
        final T[] array = SimpleArrayArgument.toArray(argument, this.parser);
        return this.create(array);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return PossibleInputs.empty().setRest("<" + this.parser.getPossibleInputs(null).getRest() + "]" + this.parser.getPossibleInputs(null).getRest() + "...>");
        }
        final String[] split = argument.split("]");
        return this.parser.getPossibleInputs(split[split.length - 1]);
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return super.onTabComplete(completer);
    }
}
