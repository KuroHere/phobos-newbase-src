// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class NonNullListArgument extends AbstractArgument<NonNullList>
{
    public NonNullListArgument() {
        super(NonNullList.class);
    }
    
    @Override
    public NonNullList fromString(final String argument) throws ArgParseException {
        return NonNullList.func_191196_a();
    }
}
