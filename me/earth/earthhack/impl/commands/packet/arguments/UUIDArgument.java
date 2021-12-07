// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public class UUIDArgument extends AbstractArgument<UUID>
{
    public UUIDArgument() {
        super(UUID.class);
    }
    
    @Override
    public UUID fromString(final String argument) throws ArgParseException {
        try {
            return UUID.fromString(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse UUID from " + argument + "!");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<" + this.getSimpleName() + ">");
        }
        final String[] split = argument.split("-");
        final StringBuilder builder = new StringBuilder("*****");
        for (int i = split.length + 1; i < 5; ++i) {
            builder.append("-*****");
        }
        final String s = builder.toString();
        if (s.isEmpty()) {
            return PossibleInputs.empty();
        }
        final String compl = (split.length < 5) ? "-" : "";
        return PossibleInputs.empty().setCompletion(compl).setRest(s);
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
}
