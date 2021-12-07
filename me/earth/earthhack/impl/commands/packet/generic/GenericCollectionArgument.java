// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.generic;

import java.util.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.commands.packet.*;
import com.google.common.collect.*;

public class GenericCollectionArgument<T> extends AbstractIterableArgument<T, Collection<T>>
{
    public GenericCollectionArgument(final Constructor<?> ctr, final int argIndex, final PacketArgument<T> parser) {
        super(Iterable.class, ctr, argIndex, parser);
    }
    
    @Override
    protected Collection<T> create(final T[] array) {
        return Lists.newArrayList((Object[])array);
    }
}
