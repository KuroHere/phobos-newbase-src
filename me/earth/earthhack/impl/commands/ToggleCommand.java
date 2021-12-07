// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.commands.util.*;

public class ToggleCommand extends AbstractModuleCommand
{
    public ToggleCommand() {
        super(new String[][] { { "toggle" }, { "module" }, { "times" } }, 1);
        CommandDescriptions.register(this, "Toggle the specified module. If you specify a number you can toggle it multiple times. This can be useful to set the FakePlayer to another position for example (t Fakeplayer 2).");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length > 1) {
            final Module module = Managers.MODULES.getObject(args[1]);
            if (module != null) {
                int times = 1;
                if (args.length > 2) {
                    try {
                        times = Integer.parseInt(args[2]);
                    }
                    catch (NumberFormatException ignored) {
                        ChatUtil.sendMessage("<ToggleCommand> §c" + args[2] + " is not a valid number.");
                        return;
                    }
                }
                final String color = ((module.isEnabled() && times % 2 == 0) || (!module.isEnabled() && times % 2 != 0)) ? "§a" : "§c";
                Managers.CHAT.sendDeleteMessage(color + "Toggling " + "§f" + "§l" + module.getDisplayName() + color + ((times > 1) ? (" " + times + "x.") : "."), module.getName(), 2000);
                final int finalTimes = times;
                Scheduler.getInstance().schedule(() -> {
                    for (int i = 0; i < finalTimes; ++i) {
                        module.toggle();
                    }
                });
            }
            else {
                ChatUtil.sendMessage("<ToggleCommand> §cCouldn't find " + args[1] + ".");
            }
            return;
        }
        ChatUtil.sendMessage("<ToggleCommand> §cUsage is: " + this.getFullUsage());
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length <= 1) {
            return inputs;
        }
        final Module module = this.getModule(args, 1);
        if (module != null) {
            final String enabled = module.isEnabled() ? " <Currently: Enabled>" : " <Currently: Disabled>";
            if (args.length > 2) {
                try {
                    final int times = Integer.parseInt(args[2]);
                    return inputs.setCompletion("").setRest(enabled + " <Will be:" + (((module.isEnabled() && times % 2 == 0) || (!module.isEnabled() && times % 2 != 0)) ? "§a Enabled" : "§c Disabled") + "§f" + ">");
                }
                catch (NumberFormatException e) {
                    return inputs.setCompletion("").setRest("§c <" + args[2] + " is not a number>");
                }
            }
            return inputs.setCompletion(TextUtil.substring(module.getName(), args[1].length())).setRest(" <times> " + enabled);
        }
        return inputs.setCompletion("").setRest("§c not found");
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.getArgs().length == 1) {
            completer.setResult(Commands.getPrefix() + "toggle");
            return completer;
        }
        if (completer.getArgs().length == 2) {
            final Module module = CommandUtil.getNameableStartingWith(completer.getArgs()[1], Managers.MODULES.getRegistered());
            if (module != null) {
                completer.setResult(Commands.getPrefix() + completer.getArgs()[0] + " " + module.getName());
            }
        }
        return completer;
    }
}
