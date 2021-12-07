// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.network.*;

public class DisconnectCommand extends Command
{
    public DisconnectCommand() {
        super(new String[][] { { "disconnect" } });
    }
    
    @Override
    public void execute(final String[] args) {
        ServerUtil.disconnectFromMC("Disconnected.");
    }
}
