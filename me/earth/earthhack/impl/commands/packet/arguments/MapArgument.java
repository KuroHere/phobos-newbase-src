// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class MapArgument extends AbstractArgument<Map>
{
    public MapArgument() {
        super(Map.class);
    }
    
    @Override
    public Map fromString(final String argument) throws ArgParseException {
        return Collections.EMPTY_MAP;
    }
}
