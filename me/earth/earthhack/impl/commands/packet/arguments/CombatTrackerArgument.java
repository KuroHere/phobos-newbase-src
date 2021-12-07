//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.entity.*;

public class CombatTrackerArgument extends AbstractArgument<CombatTracker> implements Globals
{
    public CombatTrackerArgument() {
        super(CombatTracker.class);
    }
    
    @Override
    public CombatTracker fromString(final String argument) throws ArgParseException {
        if (CombatTrackerArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.World was null!");
        }
        final EntityLivingBase entity = AbstractEntityArgument.getEntity(argument, EntityLivingBase.class);
        if (entity == null) {
            throw new ArgParseException("Could not parse " + argument + " to EntityLivingBase!");
        }
        return entity.getCombatTracker();
    }
}
