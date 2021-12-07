// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.generic;

import me.earth.earthhack.impl.commands.packet.*;
import java.lang.reflect.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;

public class GenericMapArgument<K, V, M extends Map<K, V>> extends GenericArgument<M>
{
    protected final PacketArgument<K> key;
    protected final PacketArgument<V> value;
    
    public GenericMapArgument(final Class<? super M> type, final Constructor<?> ctr, final int argIndex, final PacketArgument<K> key, final PacketArgument<V> value) {
        super(type, ctr, argIndex);
        this.key = key;
        this.value = value;
    }
    
    protected M create() {
        return (M)new HashMap();
    }
    
    @Override
    public M fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split("]");
        if (split.length == 0) {
            return (M)Collections.EMPTY_MAP;
        }
        final M map = this.create();
        for (final String entry : split) {
            if (entry != null) {
                final String[] keyValue = entry.split("\\)");
                if (keyValue.length != 2) {
                    throw new ArgParseException("Couldn't parse " + entry + " to MapEntry!");
                }
                final K k = this.key.fromString(keyValue[0]);
                final V v = this.value.fromString(keyValue[1]);
                map.put(k, v);
            }
        }
        return map;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<Map:" + this.key.getSimpleName() + ")" + this.value.getSimpleName() + "]>");
        }
        final String[] split = argument.split("]");
        if (split.length == 0) {
            return inputs.setRest("<Map:" + this.key.getSimpleName() + ")" + this.value.getSimpleName() + "]>");
        }
        final String[] last = split[split.length - 1].split("\\)");
        if (last.length == 0) {
            return inputs.setRest(this.key.getSimpleName() + ")" + this.value.getSimpleName() + "]");
        }
        if (last.length == 1) {
            final PossibleInputs keyInputs = this.key.getPossibleInputs(last[0]);
            return inputs.setCompletion(keyInputs.getCompletion() + ")");
        }
        if (last.length == 2) {
            final PossibleInputs valueInputs = this.key.getPossibleInputs(last[1]);
            return inputs.setCompletion(valueInputs.getCompletion() + "]");
        }
        return inputs;
    }
}
