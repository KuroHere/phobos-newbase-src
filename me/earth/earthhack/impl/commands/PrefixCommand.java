// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.command.*;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super(new String[][] { { "prefix" }, { "symbol" } });
        CommandDescriptions.register(this, "Manage the clients prefix.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length > 1) {
            final String prefix = args[1];
            Commands.setPrefix(prefix);
            Managers.CHAT.sendDeleteMessage("Prefix has been set to: §b" + prefix + "§f" + ".", "Prefix", 3000);
        }
        else {
            ChatUtil.sendMessage("§cPlease specify a prefix.");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 1) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.getArgs().length > 1 || completer.getArgs()[0].equalsIgnoreCase("prefix")) {
            return completer;
        }
        return super.onTabComplete(completer);
    }
}
