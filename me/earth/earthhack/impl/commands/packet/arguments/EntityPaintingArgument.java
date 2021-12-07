//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.entity.item.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import me.earth.earthhack.api.command.*;

public class EntityPaintingArgument extends AbstractArgument<EntityPainting> implements Globals
{
    private static final PacketArgument<EnumFacing> FACING_ARGUMENT;
    
    public EntityPaintingArgument() {
        super(EntityPainting.class);
    }
    
    @Override
    public EntityPainting fromString(final String argument) throws ArgParseException {
        if (EntityPaintingArgument.mc.world == null || EntityPaintingArgument.mc.player == null) {
            throw new ArgParseException("Minecraft.World was null!");
        }
        final String[] split = argument.split(",");
        if (split.length < 1) {
            throw new ArgParseException("At least define an ID for the Painting!");
        }
        final int id = (int)ArgParseException.tryLong(split[0], "id");
        final double x = (split.length > 1) ? ArgParseException.tryDouble(split[1], "x") : EntityPaintingArgument.mc.player.posX;
        final double y = (split.length > 2) ? ArgParseException.tryDouble(split[2], "y") : EntityPaintingArgument.mc.player.posY;
        final double z = (split.length > 3) ? ArgParseException.tryDouble(split[3], "z") : EntityPaintingArgument.mc.player.posZ;
        final EnumFacing facing = (split.length > 4) ? EntityPaintingArgument.FACING_ARGUMENT.fromString(split[4]) : EnumFacing.UP;
        final BlockPos pos = new BlockPos(x, y, z);
        final EntityPainting painting = new EntityPainting((World)EntityPaintingArgument.mc.world, pos, facing);
        painting.setEntityId(id);
        return painting;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<Painting:id,x,y,z,facing>");
        }
        final String[] split = argument.split(",");
        switch (split.length) {
            case 0: {
                return inputs.setRest("<Painting:id,x,y,z,facing>");
            }
            case 1: {
                return inputs.setCompletion(",").setRest("x,y,z,facing>");
            }
            case 2: {
                return inputs.setCompletion(",").setRest("y,z,facing>");
            }
            case 3: {
                return inputs.setCompletion(",").setRest("z,facing>");
            }
            case 4: {
                return inputs.setCompletion(",").setRest("facing>");
            }
            case 5: {
                return EntityPaintingArgument.FACING_ARGUMENT.getPossibleInputs(argument);
            }
            default: {
                return inputs;
            }
        }
    }
    
    static {
        FACING_ARGUMENT = new EnumArgument<EnumFacing>(EnumFacing.class);
    }
}
