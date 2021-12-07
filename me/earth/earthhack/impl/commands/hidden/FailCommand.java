// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.*;
import java.util.*;
import me.earth.earthhack.api.command.*;

public class FailCommand extends Command implements Registrable, Globals
{
    private final StopWatch indexTimer;
    private int index;
    
    public FailCommand() {
        super(new String[][] { { "fail" } }, true);
        this.indexTimer = new StopWatch();
    }
    
    @Override
    public void execute(final String[] args) {
        if (args != null && args.length != 0) {
            Command closest = null;
            int closestDistance = Integer.MAX_VALUE;
            for (final Command command : Managers.COMMANDS.getRegistered()) {
                final int levenshtein = CommandUtil.levenshtein(command.getName(), args[0]);
                if (levenshtein < closestDistance) {
                    closest = command;
                    closestDistance = levenshtein;
                }
            }
            if (closest != null) {
                ChatUtil.sendMessage("§cCommand not found, did you mean " + closest.getName() + "?. Type " + Commands.getPrefix() + "help to get a list of commands.");
                Earthhack.getLogger().info("FailCommand for args: " + Arrays.toString(args));
                return;
            }
        }
        ChatUtil.sendMessage("§cCommand not found. Type " + Commands.getPrefix() + "help to get a list of commands.");
        Earthhack.getLogger().info("FailCommand for args: " + Arrays.toString(args));
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final String conc = Managers.COMMANDS.getConcatenatedCommands();
        if (conc == null || conc.isEmpty()) {
            return PossibleInputs.empty().setRest("§cerror");
        }
        if (this.indexTimer.passed(750L)) {
            this.index += 10;
            this.indexTimer.reset();
        }
        if (this.index >= conc.length()) {
            this.index = 0;
        }
        return PossibleInputs.empty().setRest("§c" + conc.substring(this.index) + ", " + conc);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        completer.setMcComplete(true);
        return completer;
    }
}
