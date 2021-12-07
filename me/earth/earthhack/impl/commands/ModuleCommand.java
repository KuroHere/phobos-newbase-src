// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.hidden.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.command.*;
import java.util.*;
import me.earth.earthhack.api.register.*;

public class ModuleCommand extends Command implements Registrable
{
    public ModuleCommand() {
        super(new String[][] { { "module" }, { "setting" }, { "value" } });
        CommandDescriptions.register(this, "Type only the name of the module to open the chatgui with its settings. You can also specify one of the modules settings and set it to a value.");
    }
    
    @Override
    public void onUnRegister() throws CantUnregisterException {
        throw new CantUnregisterException(this);
    }
    
    @Override
    public boolean fits(final String[] args) {
        return args[0].length() > 0 && this.getModule(args[0]) != null;
    }
    
    @Override
    public void execute(final String[] args) {
        if (args == null || args.length < 1) {
            return;
        }
        Module module = Managers.MODULES.getObject(args[0]);
        if (module == null) {
            module = ((IterationRegister<Module>)Managers.ELEMENTS).getObject(args[0]);
            if (module == null) {
                module = this.getModule(args[0].toLowerCase());
                if (module == null) {
                    ChatUtil.sendMessage("§cCould not find §f" + args[0] + "§c" + ". Try " + Commands.getPrefix() + "modules.");
                }
                else {
                    ChatUtil.sendMessage("§cDid you mean §f" + module.getName() + "§c" + "?");
                }
                return;
            }
        }
        if (module instanceof CustomCommandModule && ((CustomCommandModule)module).execute(args)) {
            return;
        }
        if (args.length == 1) {
            final Module finalModule = module;
            Scheduler.getInstance().schedule(() -> Managers.COMMANDS.applyCommand(HListSettingCommand.create(finalModule)));
            return;
        }
        final Setting<?> setting = module.getSetting(args[1]);
        if (setting == null) {
            ChatUtil.sendMessage("§cCouldn't find setting §b" + args[1] + "§c" + " in " + "§f" + module.getName() + "§c" + ".");
            return;
        }
        if (args.length == 2) {
            ChatUtil.sendMessage("§cPlease specify a value for §f" + args[1] + "§c" + " in " + "§f" + module.getName() + "§c" + ".");
        }
        else {
            if (setting.getName().equals("Enabled")) {
                if (args[2].equalsIgnoreCase("true")) {
                    module.enable();
                }
                else {
                    if (!args[2].equalsIgnoreCase("false")) {
                        ChatUtil.sendMessage("§cPossible values: true or false!");
                        return;
                    }
                    module.disable();
                }
                Managers.CHAT.sendDeleteMessage("§l" + module.getName() + (module.isEnabled() ? "§a enabled." : "§c disabled."), module.getName(), 2000);
                return;
            }
            final SettingResult result = setting.fromString(args[2]);
            if (!result.wasSuccessful()) {
                ChatUtil.sendMessage("§c" + result.getMessage());
            }
            else {
                final String message = "<" + module.getDisplayName() + "> " + "§b" + setting.getName() + "§f" + " set to " + ((setting.getValue() instanceof Boolean) ? (setting.getValue() ? "§a" : "§c") : "§b") + setting.toJson() + "§f" + ".";
                Managers.CHAT.sendDeleteMessage(message, setting.getName(), 3000);
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final Module module = this.getModule(args[0]);
        if (module == null) {
            return new PossibleInputs("", "§c not found");
        }
        if (module instanceof CustomCommandModule) {
            final PossibleInputs inputs = PossibleInputs.empty();
            if (((CustomCommandModule)module).getInput(args, inputs)) {
                return inputs;
            }
        }
        if (args.length == 1) {
            return new PossibleInputs(TextUtil.substring(module.getName(), args[0].length()), " <setting> <value>");
        }
        if (!args[0].equalsIgnoreCase(module.getName())) {
            return new PossibleInputs("", "§c not found");
        }
        final Setting<?> setting = CommandUtil.getNameableStartingWith(args[1], module.getSettings());
        if (setting == null) {
            return new PossibleInputs("", "§c no such setting!");
        }
        if (args.length == 2) {
            return new PossibleInputs(TextUtil.substring(setting.getName(), args[1].length()), " " + setting.getInputs(null) + " <Current: " + setting.toJson() + ">" + (setting.getName().contains(" ") ? " <Surround setting with \"...\">" : ""));
        }
        if (args.length != 3) {
            return PossibleInputs.empty();
        }
        return new PossibleInputs(TextUtil.substring(setting.getInputs(args[2]), args[2].length()), " <Current: " + setting.toJson() + ">" + (setting.getName().contains(" ") ? " <Surround setting with \"...\">" : ""));
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        final String[] args = completer.getArgs();
        if (args.length > 0) {
            final Module module = this.getModule(args[0]);
            if (module instanceof CustomCommandModule) {
                switch (((CustomCommandModule)module).complete(completer)) {
                    case RETURN: {
                        return completer;
                    }
                    case SUPER: {
                        return super.onTabComplete(completer);
                    }
                }
            }
        }
        if (completer.isSame()) {
            final Module module = this.getModule(args[0]);
            if (module == null) {
                return super.onTabComplete(completer);
            }
            if (args.length == 1) {
                return completer;
            }
            if (args.length == 2) {
                final Optional<Setting<?>> first = module.getSettings().stream().findFirst();
                if (!first.isPresent()) {
                    return completer;
                }
                if (module instanceof CustomCommandModule) {
                    final String[] custom = ((CustomCommandModule)module).getArgs();
                    if (custom != null && custom.length > 0) {
                        boolean found = false;
                        for (final String s : custom) {
                            if (found) {
                                completer.setResult(Commands.getPrefix() + args[0] + " " + s);
                                return completer;
                            }
                            if (args[1].equalsIgnoreCase(s)) {
                                found = true;
                            }
                        }
                    }
                }
                final Setting<?> setting = module.getSetting(args[1]);
                if (setting == null) {
                    completer.setResult(Commands.getPrefix() + args[0] + " " + this.getEscapedName(first.get().getName()));
                    return completer;
                }
                boolean found = false;
                for (final Setting<?> s2 : module.getSettings()) {
                    if (found) {
                        completer.setResult(Commands.getPrefix() + args[0] + " " + this.getEscapedName(s2.getName()));
                        return completer;
                    }
                    if (!s2.equals(setting)) {
                        continue;
                    }
                    found = true;
                }
                if (module instanceof CustomCommandModule) {
                    final String[] custom2 = ((CustomCommandModule)module).getArgs();
                    if (custom2 != null && custom2.length > 0) {
                        completer.setResult(Commands.getPrefix() + args[0] + " " + custom2[0]);
                        return completer;
                    }
                }
                completer.setResult(Commands.getPrefix() + args[0] + " " + this.getEscapedName(first.get().getName()));
            }
            else {
                if (module instanceof CustomCommandModule) {
                    final String[] custom3 = ((CustomCommandModule)module).getArgs();
                    if (custom3 != null && custom3.length > 0) {
                        for (final String s3 : custom3) {
                            if (args[1].equalsIgnoreCase(s3)) {
                                return completer;
                            }
                        }
                    }
                }
                final Setting<?> setting2 = module.getSetting(args[2]);
                if (setting2 != null) {
                    completer.setResult(Commands.getPrefix() + args[0] + " " + Completer.nextValueInSetting(setting2, args[args.length - 1]));
                }
            }
            return completer;
        }
        else {
            if (args.length != 2) {
                return super.onTabComplete(completer);
            }
            final Module module = this.getModule(args[0]);
            if (module == null) {
                return completer;
            }
            final Setting<?> setting2 = CommandUtil.getNameableStartingWith(args[1], module.getSettings());
            if (setting2 == null) {
                return completer;
            }
            return completer.setResult(Commands.getPrefix() + args[0] + " " + this.getEscapedName(setting2.getName()));
        }
    }
    
    private String getEscapedName(final String name) {
        return name.contains(" ") ? ("\"" + name + "\"") : name;
    }
    
    private Module getModule(final String name) {
        final Module module = CommandUtil.getNameableStartingWith(name, (Register<Module>)Managers.MODULES);
        if (module != null) {
            return module;
        }
        return CommandUtil.getNameableStartingWith(name, (Register<Module>)Managers.ELEMENTS);
    }
}
