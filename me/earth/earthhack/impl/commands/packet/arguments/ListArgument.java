// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ListArgument extends AbstractArgument<List>
{
    public ListArgument() {
        super(List.class);
    }
    
    @Override
    public List fromString(final String argument) throws ArgParseException {
        return Collections.EMPTY_LIST;
    }
}
