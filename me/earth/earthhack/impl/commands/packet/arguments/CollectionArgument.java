// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class CollectionArgument extends AbstractArgument<Collection>
{
    public CollectionArgument() {
        super(Collection.class);
    }
    
    @Override
    public Collection fromString(final String argument) throws ArgParseException {
        return Collections.EMPTY_LIST;
    }
}
