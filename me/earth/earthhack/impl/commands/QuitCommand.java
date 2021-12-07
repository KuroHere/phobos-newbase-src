//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;

public class QuitCommand extends Command implements Globals
{
    public QuitCommand() {
        super(new String[][] { { "quit" } });
    }
    
    @Override
    public void execute(final String[] args) {
        QuitCommand.mc.shutdown();
    }
}
