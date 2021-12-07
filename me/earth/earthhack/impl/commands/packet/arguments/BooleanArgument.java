// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public class BooleanArgument extends AbstractArgument<Boolean>
{
    public BooleanArgument() {
        super(Boolean.class);
    }
    
    @Override
    public Boolean fromString(final String argument) throws ArgParseException {
        return Boolean.parseBoolean(argument);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(String arg) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (arg == null || arg.isEmpty()) {
            return inputs.setRest("<Boolean>");
        }
        arg = arg.toLowerCase();
        if ("true".startsWith(arg)) {
            inputs.setCompletion(TextUtil.substring("true", arg.length()));
        }
        else if ("false".startsWith(arg)) {
            inputs.setCompletion(TextUtil.substring("false", arg.length()));
        }
        return inputs;
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
}
