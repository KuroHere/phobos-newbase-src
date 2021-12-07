// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.impl.util.mcp.*;

public abstract class AbstractArgument<T> implements PacketArgument<T>
{
    protected final Class<? super T> type;
    
    public AbstractArgument(final Class<? super T> type) {
        this.type = type;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<" + this.getSimpleName() + ">");
        }
        if (TextUtil.startsWith(this.getSimpleName(), argument)) {
            return PossibleInputs.empty().setCompletion(TextUtil.substring(this.getSimpleName(), argument.length()));
        }
        return PossibleInputs.empty();
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.RETURN;
    }
    
    @Override
    public Class<? super T> getType() {
        return this.type;
    }
    
    @Override
    public String getSimpleName() {
        return MappingProvider.simpleName(this.type);
    }
}
