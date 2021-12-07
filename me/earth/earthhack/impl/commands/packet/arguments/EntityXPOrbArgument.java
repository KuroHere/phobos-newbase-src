//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.world.*;
import me.earth.earthhack.api.command.*;

public class EntityXPOrbArgument extends AbstractArgument<EntityXPOrb> implements Globals
{
    public EntityXPOrbArgument() {
        super(EntityXPOrb.class);
    }
    
    @Override
    public EntityXPOrb fromString(final String argument) throws ArgParseException {
        if (EntityXPOrbArgument.mc.world == null || EntityXPOrbArgument.mc.player == null) {
            throw new ArgParseException("Minecraft.World was null!");
        }
        final String[] split = argument.split(",");
        if (split.length == 0) {
            throw new ArgParseException("XP-Orb was empty!");
        }
        int id;
        try {
            id = Integer.parseInt(split[0]);
        }
        catch (NumberFormatException e) {
            throw new ArgParseException("Could not parse XP-ID from " + split[0] + "!");
        }
        int amount = 1;
        if (split.length > 1) {
            try {
                amount = Integer.parseInt(split[1]);
            }
            catch (NumberFormatException e2) {
                throw new ArgParseException("Could not parse XP-Amount from " + split[1] + "!");
            }
        }
        final double x = (split.length > 2) ? this.tryParse(split[2], "x") : EntityXPOrbArgument.mc.player.posX;
        final double y = (split.length > 3) ? this.tryParse(split[3], "y") : EntityXPOrbArgument.mc.player.posY;
        final double z = (split.length > 4) ? this.tryParse(split[4], "z") : EntityXPOrbArgument.mc.player.posZ;
        final EntityXPOrb entity = new EntityXPOrb((World)EntityXPOrbArgument.mc.world, x, y, z, amount);
        entity.setEntityId(id);
        return entity;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<XP-Orb:id,amount,x,y,z>");
        }
        final String[] split = argument.split(",");
        switch (split.length) {
            case 0: {
                return inputs.setRest("<XP-Orb:id,amount,x,y,z>");
            }
            case 1: {
                return inputs.setCompletion(",").setRest("amount,x,y,z>");
            }
            case 2: {
                return inputs.setCompletion(",").setRest("x,y,z>");
            }
            case 3: {
                return inputs.setCompletion(",").setRest("y,z>");
            }
            case 4: {
                return inputs.setCompletion(",").setRest("z>");
            }
            default: {
                return inputs;
            }
        }
    }
    
    private double tryParse(final String string, final String message) throws ArgParseException {
        try {
            return Double.parseDouble(string);
        }
        catch (NumberFormatException e) {
            throw new ArgParseException("Could not parse " + message + " from " + string);
        }
    }
}
