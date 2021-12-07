//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.commands.util.*;

public class SayCommand extends AbstractTextCommand implements Globals
{
    public SayCommand() {
        super("say");
        CommandDescriptions.register(this, "Use this command to say a message. This can be useful for macros.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cUse this command to send a chat message.§f (Useful for Macros)");
        }
        else {
            final String message = CommandUtil.concatenate(args, 1);
            if (SayCommand.mc.player != null) {
                SayCommand.mc.player.sendChatMessage(message);
            }
            else {
                ChatUtil.sendMessage(message);
            }
        }
    }
}
