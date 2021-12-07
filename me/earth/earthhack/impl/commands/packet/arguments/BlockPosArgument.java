//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.util.math.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class BlockPosArgument extends AbstractPositionArgument<BlockPos>
{
    public BlockPosArgument() {
        super("BlockPos", BlockPos.class);
    }
    
    @Override
    public BlockPos fromString(final String argument) throws ArgParseException {
        if (argument.equalsIgnoreCase("ORIGIN")) {
            return BlockPos.ORIGIN;
        }
        final String[] split = argument.split(",");
        if (split.length != 3) {
            throw new ArgParseException("BlockPos takes 3 arguments!");
        }
        try {
            final int x = (int)Long.parseLong(split[0]);
            final int y = (int)Long.parseLong(split[1]);
            final int z = (int)Long.parseLong(split[2]);
            return new BlockPos(x, y, z);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to BlockPos!");
        }
    }
}
