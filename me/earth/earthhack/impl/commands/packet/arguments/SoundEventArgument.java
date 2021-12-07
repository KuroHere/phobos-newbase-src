//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;

public class SoundEventArgument extends AbstractArgument<SoundEvent>
{
    public SoundEventArgument() {
        super(SoundEvent.class);
    }
    
    @Override
    public SoundEvent fromString(final String argument) throws ArgParseException {
        SoundEvent event = getSoundStartingWith(argument);
        if (event == null) {
            try {
                final int id = Integer.parseInt(argument);
                event = (SoundEvent)SoundEvent.REGISTRY.getObjectById(id);
            }
            catch (NumberFormatException e) {
                event = null;
            }
            if (event == null) {
                throw new ArgParseException("Could not parse SoundEvent from name or id: " + argument + "!");
            }
        }
        return event;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String arg) {
        if (arg == null || arg.isEmpty()) {
            return PossibleInputs.empty().setRest("<SoundEvent>");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final SoundEvent event = getSoundStartingWith(arg);
        if (event != null) {
            return inputs.setCompletion(TextUtil.substring(event.getSoundName().toString(), arg.length()));
        }
        return inputs;
    }
    
    public static SoundEvent getSoundStartingWith(final String argument) {
        for (final SoundEvent event : SoundEvent.REGISTRY) {
            final String name = event.getSoundName().toString();
            if (TextUtil.startsWith(name, argument)) {
                return event;
            }
        }
        return null;
    }
}
