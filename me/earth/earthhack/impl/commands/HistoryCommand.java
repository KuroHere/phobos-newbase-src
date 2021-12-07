//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import java.text.*;
import java.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class HistoryCommand extends Command implements Globals
{
    public HistoryCommand() {
        super(new String[][] { { "history" }, { "name" } });
        CommandDescriptions.register(this, "Gets the Namehistory of players.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cPlease specify a name.");
        }
        else if (args.length > 1) {
            Managers.CHAT.sendDeleteMessage("§bLooking up §f" + args[1] + "'s " + "§b" + "name history.", args[1], 3000);
            Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.HISTORY, args[1]) {
                @Override
                public void onSuccess() {
                    Globals.mc.addScheduledTask(() -> {
                        final Object val$args = args;
                        boolean first = true;
                        ChatUtil.sendMessage("");
                        this.names.entrySet().iterator();
                        final Iterator iterator;
                        while (iterator.hasNext()) {
                            final Map.Entry<Date, String> entry = iterator.next();
                            String format;
                            if (entry.getKey().getTime() == 0L) {
                                format = "";
                            }
                            else {
                                format = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(entry.getKey());
                            }
                            final String dateString = format;
                            if (first) {
                                Managers.CHAT.sendDeleteMessage("§l" + entry.getValue() + "§7" + " - " + "§6" + dateString, args[1], 3000);
                                first = false;
                            }
                            else {
                                ChatUtil.sendMessage(entry.getValue() + "§7" + " - " + "§6" + dateString);
                            }
                        }
                        ChatUtil.sendMessage("");
                    });
                }
                
                @Override
                public void onFailure() {
                    Managers.CHAT.sendDeleteMessage("§cFailed to lookup §f" + args[1], args[1], 3000);
                }
            });
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length == 1) {
            return inputs;
        }
        if (args.length == 2) {
            final String player = LookUpUtil.findNextPlayerName(args[1]);
            return inputs.setCompletion((player == null) ? "" : TextUtil.substring(player, args[1].length())).setRest("");
        }
        return inputs;
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.getArgs().length == 1) {
            if (completer.getArgs()[0].equalsIgnoreCase("history")) {
                completer.setMcComplete(true);
            }
            else {
                completer.setResult(Commands.getPrefix() + "history");
            }
        }
        else if (completer.getArgs().length == 2) {
            final String player = LookUpUtil.findNextPlayerName(completer.getArgs()[1]);
            if (player == null || player.equalsIgnoreCase(completer.getArgs()[1])) {
                completer.setMcComplete(true);
            }
            else {
                completer.setResult(Commands.getPrefix() + "history " + player);
            }
        }
        return completer;
    }
}
