// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public abstract class AbstractPositionArgument<T> extends AbstractArgument<T>
{
    protected static final String[] REST;
    protected final String name;
    
    public AbstractPositionArgument(final String name, final Class<T> type) {
        super(type);
        this.name = name;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<" + this.name + ">");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument.toLowerCase().startsWith("origin")) {
            return inputs.setCompletion(TextUtil.substring("ORIGIN", argument.length()));
        }
        final String[] split = argument.split(",");
        if (split.length > 3) {
            return inputs;
        }
        if (split.length < 3) {
            inputs.setCompletion(",");
        }
        return inputs.setRest(AbstractPositionArgument.REST[split.length]);
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    static {
        REST = new String[] { "y,z", ",z", "", "" };
    }
}
