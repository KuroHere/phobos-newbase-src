// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.array;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class SimpleArrayArgument extends AbstractArrayArgument<Object>
{
    public SimpleArrayArgument(final PacketArgument<Object> parser) {
        super(Object[].class, parser);
    }
    
    @Override
    protected Object[] create(final int size) {
        return new Object[size];
    }
    
    @Override
    public Object[] fromString(final String argument) throws ArgParseException {
        return toArray(argument, this.parser);
    }
    
    public static <T> T[] toArray(final String argument, final PacketArgument<T> parser) throws ArgParseException {
        final String[] split = argument.split("]");
        final T[] result = (T[])new Object[split.length];
        for (int i = 0; i < split.length; ++i) {
            result[i] = parser.fromString(split[i]);
        }
        return result;
    }
}
