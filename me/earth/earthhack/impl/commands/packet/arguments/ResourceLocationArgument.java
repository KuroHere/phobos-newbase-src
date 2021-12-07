// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ResourceLocationArgument extends AbstractArgument<ResourceLocation>
{
    public ResourceLocationArgument() {
        super(ResourceLocation.class);
    }
    
    @Override
    public ResourceLocation fromString(final String argument) throws ArgParseException {
        return new ResourceLocation(argument);
    }
}
