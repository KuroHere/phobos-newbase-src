//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.chunk.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.world.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public class ChunkArgument extends AbstractArgument<Chunk> implements Globals
{
    protected static final String[] REST;
    
    public ChunkArgument() {
        super(Chunk.class);
    }
    
    @Override
    public Chunk fromString(final String argument) throws ArgParseException {
        if (ChunkArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.world is null!");
        }
        if (argument.equalsIgnoreCase("ORIGIN")) {
            return new Chunk((World)ChunkArgument.mc.world, 0, 0);
        }
        final String[] split = argument.split(",");
        if (split.length != 2) {
            throw new ArgParseException("Chunk takes 2 arguments!");
        }
        try {
            final int x = (int)Long.parseLong(split[0]);
            final int z = (int)Long.parseLong(split[1]);
            return new Chunk((World)ChunkArgument.mc.world, x, z);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to Chunk!");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<Chunk>");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument.toLowerCase().startsWith("origin")) {
            return inputs.setCompletion(TextUtil.substring("ORIGIN", argument.length()));
        }
        final String[] split = argument.split(",");
        if (split.length > 2) {
            return inputs;
        }
        if (split.length < 2) {
            inputs.setCompletion(",");
        }
        return inputs.setRest(ChunkArgument.REST[split.length]);
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    static {
        REST = new String[] { "z", "z", "" };
    }
}
