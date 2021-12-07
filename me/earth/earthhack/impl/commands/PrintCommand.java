// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.commands.util.*;

public class PrintCommand extends AbstractTextCommand
{
    public PrintCommand() {
        super("print");
        CommandDescriptions.register(this, "Prints a message in chat, without sending it.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("");
            return;
        }
        ChatUtil.sendMessage(CommandUtil.concatenate(args, 1));
    }
}
