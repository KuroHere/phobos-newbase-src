// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ServerStatusResponseArgument extends AbstractArgument<ServerStatusResponse>
{
    public ServerStatusResponseArgument() {
        super(ServerStatusResponse.class);
    }
    
    @Override
    public ServerStatusResponse fromString(final String argument) throws ArgParseException {
        return new DummyServerStatusResponse();
    }
}
