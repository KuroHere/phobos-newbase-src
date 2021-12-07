//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.util.text.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;

public class TimesCommand extends Command implements CommandScheduler, Globals
{
    private final Map<String, TimesProcess> ids;
    private final AtomicLong id;
    
    public TimesCommand() {
        super(new String[][] { { "times" }, { "amount", "cancel" }, { "delay", "id" }, { "command" } });
        this.ids = new ConcurrentHashMap<String, TimesProcess>();
        this.id = new AtomicLong();
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("Use this command to execute a command x times with a given delay.");
            return;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("cancel")) {
                ChatUtil.sendMessage("§cNo id specified, available: " + this.ids.keySet() + ".");
            }
            else {
                ChatUtil.sendMessage("§cPlease specify a command.");
            }
            return;
        }
        if (args[1].equalsIgnoreCase("cancel")) {
            final TimesProcess process = this.ids.get(args[2]);
            if (process == null) {
                ChatUtil.sendMessage("§cNo process found for id §f" + args[2] + "§c" + "!");
                return;
            }
            ChatUtil.sendMessage("§bCancelling process §f" + args[2] + "§b" + "...");
            process.setValid(false);
            process.clear();
        }
        else {
            if (args.length < 4) {
                ChatUtil.sendMessage("§cPlease specify a command.");
                return;
            }
            int amount;
            try {
                amount = (int)Long.parseLong(args[1]);
                if (amount <= 0) {
                    ChatUtil.sendMessage("§cAmount §f" + args[1] + "§c" + " was smaller than or equal to 0!");
                    return;
                }
            }
            catch (NumberFormatException e) {
                ChatUtil.sendMessage("§cCouldn't parse §f" + args[1] + "§c" + " to amount.");
                return;
            }
            long delay;
            try {
                delay = Long.parseLong(args[2]);
                if (delay < 0L) {
                    ChatUtil.sendMessage("§cDelay §f" + args[2] + "§c" + " was smaller than 0!");
                    return;
                }
            }
            catch (NumberFormatException e2) {
                ChatUtil.sendMessage("§cCouldn't parse §f" + args[2] + "§c" + " to delay.");
                return;
            }
            final String[] arguments = Arrays.copyOfRange(args, 3, args.length);
            final Command command = Managers.COMMANDS.getCommandForMessage(arguments);
            if (delay == 0L) {
                TimesCommand.mc.addScheduledTask(() -> {
                    try {
                        for (int j = 0; j < amount; ++j) {
                            command.execute(arguments);
                        }
                    }
                    catch (Throwable t) {
                        ChatUtil.sendMessage("§cAn error occurred while executing command §f" + arguments[0] + "§c" + ": " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
            else {
                Runnable last = null;
                final String processId = this.id.incrementAndGet() + "";
                final TimesProcess process2 = new TimesProcess(amount);
                for (int i = 0; i < amount; ++i) {
                    if (last != null) {
                        process2.addFuture(TimesCommand.SCHEDULER.schedule(last, delay * (i - 1), TimeUnit.MILLISECONDS));
                    }
                    final long time = delay * i;
                    if (time < 0L) {
                        ChatUtil.sendMessage("§cYour delay * amount overflowed!");
                        process2.setValid(false);
                        process2.clear();
                        return;
                    }
                    final TimesProcess process;
                    last = (() -> TimesCommand.mc.addScheduledTask(() -> {
                        if (!(!process.isValid())) {
                            try {
                                command.execute(arguments);
                            }
                            catch (Throwable t2) {
                                ChatUtil.sendMessage("§cAn error occurred while executing command §f" + arguments[0] + "§c" + ": " + t2.getMessage());
                                t2.printStackTrace();
                            }
                        }
                    }));
                }
                final Runnable finalLast = last;
                this.ids.put(processId, process2);
                process2.addFuture(TimesCommand.SCHEDULER.schedule(() -> {
                    finalLast.run();
                    this.ids.remove(processId);
                    return;
                }, delay * (amount - 1), TimeUnit.MILLISECONDS));
                ChatUtil.sendMessage("§aStarted process with id §b" + processId + "§a" + ".");
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length == 1) {
            return super.getPossibleInputs(args);
        }
        if (args.length == 2) {
            if (TextUtil.startsWith("cancel", args[1])) {
                return super.getPossibleInputs(args);
            }
            return new PossibleInputs("", " <delay> <command>");
        }
        else {
            if (TextUtil.startsWith("cancel", args[1])) {
                return PossibleInputs.empty();
            }
            if (args.length == 3) {
                return new PossibleInputs("", " <command>");
            }
            final String[] arguments = Arrays.copyOfRange(args, 3, args.length);
            final Command command = Managers.COMMANDS.getCommandForMessage(arguments);
            return command.getPossibleInputs(arguments);
        }
    }
}
