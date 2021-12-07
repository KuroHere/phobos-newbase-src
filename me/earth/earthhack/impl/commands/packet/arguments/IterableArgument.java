// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class IterableArgument extends AbstractArgument<Iterable>
{
    public IterableArgument() {
        super(Iterable.class);
    }
    
    @Override
    public Iterable fromString(final String argument) throws ArgParseException {
        return Collections.EMPTY_LIST;
    }
}
