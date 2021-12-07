// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import com.mojang.authlib.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import java.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public class GameProfileArgument extends AbstractArgument<GameProfile>
{
    private static final UUIDArgument UUID_ARGUMENT;
    
    public GameProfileArgument() {
        super(GameProfile.class);
    }
    
    @Override
    public GameProfile fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split(",");
        if (split.length != 2) {
            throw new ArgParseException("GameProfile takes 2 arguments: UUID and String!");
        }
        final UUID uuid = GameProfileArgument.UUID_ARGUMENT.fromString(split[0]);
        return new GameProfile(uuid, split[1]);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<" + this.getSimpleName() + ">");
        }
        final String[] split = argument.split(",");
        if (split.length > 2) {
            return PossibleInputs.empty();
        }
        if (split.length == 1) {
            final PossibleInputs inputs = GameProfileArgument.UUID_ARGUMENT.getPossibleInputs(argument);
            return inputs.setRest(",name");
        }
        return PossibleInputs.empty();
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    static {
        UUID_ARGUMENT = new UUIDArgument();
    }
}
