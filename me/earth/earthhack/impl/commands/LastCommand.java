//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.client.gui.*;

public class LastCommand extends Command implements Globals
{
    public LastCommand() {
        super(new String[][] { { "last" }, { "execute" } });
    }
    
    @Override
    public void execute(final String[] args) {
        final String last = Managers.COMMANDS.getLastCommand();
        if (last == null) {
            ChatUtil.sendMessage("§cThere's no last command!");
            return;
        }
        if (args.length > 1 && "execute".equalsIgnoreCase(args[1])) {
            ChatUtil.sendMessage("§aExecuting last Command: §b" + last + "§a" + "!");
            Managers.COMMANDS.applyCommand(last);
            return;
        }
        Scheduler.getInstance().schedule(() -> LastCommand.mc.displayGuiScreen((GuiScreen)new GuiChat(last)));
    }
}
