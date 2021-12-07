// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.generic;

import net.minecraft.util.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.commands.packet.*;

public class GenericNonNullListArgument<T> extends AbstractIterableArgument<T, NonNullList<T>>
{
    public GenericNonNullListArgument(final Constructor<?> ctr, final int argIndex, final PacketArgument<T> parser) {
        super(Iterable.class, ctr, argIndex, parser);
    }
    
    @Override
    protected NonNullList<T> create(final T[] array) {
        final NonNullList<T> list = (NonNullList<T>)NonNullList.func_191196_a();
        for (final T t : array) {
            if (t != null) {
                list.add((Object)t);
            }
        }
        return list;
    }
}
