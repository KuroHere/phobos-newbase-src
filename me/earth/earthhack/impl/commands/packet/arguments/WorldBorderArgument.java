// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.border.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class WorldBorderArgument extends AbstractArgument<WorldBorder>
{
    public WorldBorderArgument() {
        super(WorldBorder.class);
    }
    
    @Override
    public WorldBorder fromString(final String argument) throws ArgParseException {
        return new WorldBorder();
    }
}
