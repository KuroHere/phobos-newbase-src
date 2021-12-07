// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class EnumArgument<T extends Enum<?>> extends AbstractArgument<T>
{
    private final Class<T> directType;
    
    public EnumArgument(final Class<T> type) {
        super(type);
        this.directType = type;
    }
    
    @Override
    public T fromString(final String argument) throws ArgParseException {
        final T e = EnumHelper.fromString(this.directType, argument);
        if (e == null) {
            throw new ArgParseException("Could not find Enum: " + argument + " in " + this.getSimpleName() + ".");
        }
        return e;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return super.getPossibleInputs(argument);
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final Enum<?> starting = EnumHelper.getEnumStartingWith(argument, this.directType);
        if (starting != null) {
            return inputs.setCompletion(TextUtil.substring(starting.name(), argument.length()));
        }
        return PossibleInputs.empty();
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        if (completer.isSame()) {
            final String[] args = completer.getArgs();
            Enum<?> e;
            try {
                e = this.fromString(args[args.length - 1]);
            }
            catch (ArgParseException exception) {
                return CustomCompleterResult.RETURN;
            }
            final String r = CommandUtil.concatenate(args, args.length - 1);
            final Enum<?> next = EnumHelper.next(e);
            completer.setResult(Commands.getPrefix() + r + " " + next.name());
            return CustomCompleterResult.RETURN;
        }
        return CustomCompleterResult.PASS;
    }
}
