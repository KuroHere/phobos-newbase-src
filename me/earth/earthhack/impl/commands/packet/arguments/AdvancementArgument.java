// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.advancements.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;
import java.io.*;

public class AdvancementArgument extends AbstractArgument<Advancement>
{
    public static final AdvancementManager MANAGER;
    
    public AdvancementArgument() {
        super(Advancement.class);
    }
    
    @Override
    public Advancement fromString(final String argument) throws ArgParseException {
        final Advancement advancement = this.getAdvancementStartingWith(argument);
        if (advancement == null) {
            throw new ArgParseException("Couldn't parse Advancement from " + argument + "!");
        }
        return advancement;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<Advancement>");
        }
        final Advancement advancement = this.getAdvancementStartingWith(argument);
        if (advancement != null) {
            inputs.setCompletion(TextUtil.substring(advancement.func_192067_g().toString(), argument.length()));
        }
        return inputs;
    }
    
    private Advancement getAdvancementStartingWith(final String name) {
        for (final Advancement advancement : AdvancementArgument.MANAGER.func_192780_b()) {
            if (TextUtil.startsWith(advancement.func_192067_g().toString(), name)) {
                return advancement;
            }
        }
        return null;
    }
    
    static {
        MANAGER = new AdvancementManager((File)null);
    }
}
