//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.potion.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;

public class PotionArgument extends AbstractArgument<Potion>
{
    public PotionArgument() {
        super(Potion.class);
    }
    
    @Override
    public Potion fromString(final String argument) throws ArgParseException {
        Potion potion = getPotionStartingWith(argument);
        if (potion == null) {
            try {
                final int id = Integer.parseInt(argument);
                potion = Potion.getPotionById(id);
            }
            catch (NumberFormatException e) {
                potion = null;
            }
            if (potion == null) {
                throw new ArgParseException("Could not parse potion from name or id: " + argument + "!");
            }
        }
        return potion;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String arg) {
        if (arg == null || arg.isEmpty()) {
            return PossibleInputs.empty().setRest("<potion>");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final Potion potion = getPotionStartingWith(arg);
        if (potion != null) {
            return inputs.setCompletion(TextUtil.substring(potion.getName(), arg.length()));
        }
        return inputs;
    }
    
    public static Potion getPotionStartingWith(final String argument) {
        for (final Potion potion : Potion.REGISTRY) {
            final String name = potion.getName();
            if (TextUtil.startsWith(name, argument)) {
                return potion;
            }
        }
        return null;
    }
}
