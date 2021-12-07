// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.array;

import java.util.function.*;
import me.earth.earthhack.impl.commands.packet.*;

public class FunctionArrayArgument<T> extends AbstractArrayArgument<T>
{
    private final Function<Integer, T[]> function;
    
    public FunctionArrayArgument(final Class<T[]> type, final PacketArgument<T> parser, final Function<Integer, T[]> function) {
        super(type, parser);
        this.function = function;
    }
    
    @Override
    protected T[] create(final int size) {
        return this.function.apply(size);
    }
}
