//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class WorldArgument extends AbstractArgument<World> implements Globals
{
    public WorldArgument() {
        super(World.class);
    }
    
    @Override
    public World fromString(final String argument) throws ArgParseException {
        return (World)WorldArgument.mc.world;
    }
}
