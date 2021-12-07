//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.util;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.module.*;

public class EarthhackJsBridge implements Globals
{
    public void command(final String command) {
        EarthhackJsBridge.mc.addScheduledTask(() -> Managers.COMMANDS.applyCommand(Commands.getPrefix() + command));
    }
    
    public boolean isEnabled(final String module) {
        return Managers.MODULES.getObject(module).isEnabled();
    }
}
