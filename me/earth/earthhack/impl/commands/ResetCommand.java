//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.*;

public class ResetCommand extends AbstractModuleCommand implements Globals
{
    public ResetCommand() {
        super(new String[][] { { "reset" }, { "module" }, { "setting" } }, 1);
        CommandDescriptions.register(this, "Resets all settings of the module to their default values.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            ChatUtil.sendMessage("Use this command to reset Modules and Settings.");
            return;
        }
        final Module module = Managers.MODULES.getObject(args[1]);
        if (module == null) {
            ChatUtil.sendMessage("§cModule §f" + args[1] + "§c" + " not found!");
            return;
        }
        if (args.length == 2) {
            Scheduler.getInstance().schedule(() -> {
                final GuiScreen previous = ResetCommand.mc.currentScreen;
                final Minecraft mc = ResetCommand.mc;
                new YesNoNonPausing((result, id) -> {
                    ResetCommand.mc.displayGuiScreen(previous);
                    if (!result) {
                        return;
                    }
                    for (final Setting<?> setting : module.getSettings()) {
                        setting.reset();
                    }
                    ChatUtil.sendMessage("§aModule §f" + module.getName() + "§a" + " has been reset.");
                }, "Do you really want to reset the Module " + module.getName() + "?", "", 1337);
                final YesNoNonPausing yesNoNonPausing;
                mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
            });
        }
        else {
            final List<Setting<?>> settings = new ArrayList<Setting<?>>(args.length - 2);
            for (int i = 2; i < args.length; ++i) {
                final Setting<?> setting = module.getSetting(args[i]);
                if (setting != null) {
                    settings.add(setting);
                }
                else {
                    ChatUtil.sendMessage("§cCould not find Setting §f" + args[i] + "§c" + " in module " + "§f" + module.getName() + "§c" + ".");
                }
            }
            if (settings.isEmpty()) {
                return;
            }
            final StringBuilder settingString = new StringBuilder("§c");
            settingString.append("Do you really want to reset the Setting");
            if (settings.size() > 1) {
                settingString.append("s ");
            }
            else {
                settingString.append(" ");
            }
            settingString.append("§f");
            final Iterator<Setting<?>> itr = settings.iterator();
            while (itr.hasNext()) {
                final Setting<?> s = itr.next();
                settingString.append(s.getName());
                if (itr.hasNext()) {
                    settingString.append("§c").append(", ").append("§f");
                }
            }
            settingString.append("§c").append(" in the module ").append("§f").append(module.getName()).append("§c").append("?");
            Scheduler.getInstance().schedule(() -> {
                final GuiScreen previous2 = ResetCommand.mc.currentScreen;
                ResetCommand.mc.displayGuiScreen((GuiScreen)new YesNoNonPausing((result, id) -> {
                    ResetCommand.mc.displayGuiScreen(previous2);
                    if (!result) {
                        return;
                    }
                    for (final Setting<?> setting : settings) {
                        setting.reset();
                        ChatUtil.sendMessage("§cReset " + module.getName() + "§c" + " - " + "§f" + setting.getName() + "§c" + ".");
                    }
                }, settingString.toString(), "", 1337));
            });
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length <= 2) {
            return inputs;
        }
        final Module module = Managers.MODULES.getObject(args[1]);
        if (module == null) {
            return inputs.setCompletion("").setRest("§c not found.");
        }
        final Setting<?> s = CommandUtil.getNameableStartingWith(args[args.length - 1], module.getSettings());
        if (s == null) {
            return inputs.setCompletion("").setRest("§c not found.");
        }
        return inputs.setCompletion(TextUtil.substring(s.getName(), args[args.length - 1].length()));
    }
}
