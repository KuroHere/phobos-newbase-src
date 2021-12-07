//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.command.*;

public class ShrugCommand extends Command implements Globals
{
    public static final String SHRUG = "¯\\_(\u30c4)_/¯";
    
    public ShrugCommand() {
        super(new String[][] { { "shrug" }, { "message" } });
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            this.sendMessage("¯\\_(\u30c4)_/¯");
            return;
        }
        final String message = CommandUtil.concatenate(args, 1);
        if (!message.contains(":shrug:")) {
            ChatUtil.sendMessage("§cUse :shrug: to specify parts of the message that should be replaced with the shrug emoji!");
            return;
        }
        this.sendMessage(message.replace(":shrug:", "¯\\_(\u30c4)_/¯"));
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 1) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
    
    private void sendMessage(final String message) {
        if (ShrugCommand.mc.player == null) {
            ChatUtil.sendMessage(message);
        }
        else {
            ShrugCommand.mc.player.sendChatMessage(message);
        }
    }
}
