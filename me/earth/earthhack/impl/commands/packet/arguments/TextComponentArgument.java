// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class TextComponentArgument extends AbstractArgument<ITextComponent>
{
    public TextComponentArgument() {
        super(ITextComponent.class);
    }
    
    @Override
    public ITextComponent fromString(final String argument) throws ArgParseException {
        return (ITextComponent)new TextComponentString(argument);
    }
}
