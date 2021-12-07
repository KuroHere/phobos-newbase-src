// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.potion.*;
import me.earth.earthhack.api.command.*;

public class PotionEffectArgument extends AbstractArgument<PotionEffect>
{
    private static final PotionArgument POTION_ARGUMENT;
    
    public PotionEffectArgument() {
        super(PotionEffect.class);
    }
    
    @Override
    public PotionEffect fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split(",");
        if (split.length == 0) {
            throw new ArgParseException("PotionEffect was empty!");
        }
        final Potion potion = PotionEffectArgument.POTION_ARGUMENT.fromString(split[0]);
        int time = 100;
        int lvl = 1;
        if (split.length > 1) {
            try {
                time = Integer.parseInt(split[1]);
            }
            catch (NumberFormatException e) {
                throw new ArgParseException("Couldn't parse PotionEffect-Time from: " + argument + "!");
            }
            if (split.length > 2) {
                try {
                    lvl = Integer.parseInt(split[1]);
                }
                catch (NumberFormatException e) {
                    throw new ArgParseException("Couldn't parse PotionEffect-LvL from: " + argument + "!");
                }
            }
        }
        return new PotionEffect(potion, time, lvl);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<PotionEffect:Potion,time,lvl");
        }
        final String[] split = argument.split(",");
        if (split.length == 1 && !split[0].isEmpty()) {
            inputs = PotionEffectArgument.POTION_ARGUMENT.getPossibleInputs(split[0]);
            inputs.setCompletion(inputs.getCompletion() + ",");
            inputs.setRest("time,lvl");
            return inputs;
        }
        if (split.length == 2) {
            return inputs.setCompletion(",").setRest("lvl");
        }
        return inputs;
    }
    
    static {
        POTION_ARGUMENT = new PotionArgument();
    }
}
