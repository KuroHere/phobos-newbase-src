// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.stats.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class StatBaseArgument extends AbstractArgument<StatBase>
{
    public StatBaseArgument() {
        super(StatBase.class);
    }
    
    @Override
    public StatBase fromString(final String argument) throws ArgParseException {
        return new StatBase(argument, (ITextComponent)new TextComponentString("dummy-statbase-component"));
    }
}
