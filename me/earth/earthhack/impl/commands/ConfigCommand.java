//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.config.helpers.*;
import me.earth.earthhack.api.config.*;
import java.io.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.*;
import java.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.misc.io.*;

public class ConfigCommand extends Command implements Globals
{
    public ConfigCommand() {
        super(new String[][] { { "config" }, { "config" }, { "save", "delete", "load", "refresh" }, { "name..." } });
        CommandDescriptions.register(this, "Manage your configs.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            Managers.CHAT.sendDeleteMessage("Use this command to save/load your config.", "config", 3000);
            return;
        }
        final ConfigHelper<?> helper = Managers.CONFIG.getObject(args[1]);
        if (helper == null) {
            Managers.CHAT.sendDeleteMessage("§b" + args[1] + "§c" + " unknown. Use: " + this.getConcatenatedHelpers(), "config1", 3000);
            return;
        }
        Label_1325: {
            switch (args.length) {
                case 2: {
                    final StringBuilder message = new StringBuilder("Use this command").append(" to save/delete/load the ").append("§b").append(helper.getName()).append("§f").append(" config. Currently active: ").append("§a").append(CurrentConfig.getInstance().get(helper)).append("§f").append(". Available: ");
                    final Iterator<? extends Config> itr = (Iterator<? extends Config>)helper.getConfigs().iterator();
                    while (itr.hasNext()) {
                        final Config config = (Config)itr.next();
                        message.append("§b").append(config.getName()).append("§f");
                        if (itr.hasNext()) {
                            message.append(", ");
                        }
                    }
                    message.append(".");
                    Managers.CHAT.sendDeleteMessage(message.toString(), "config2", 3000);
                    break;
                }
                case 3: {
                    final String lowerCase = args[2].toLowerCase();
                    switch (lowerCase) {
                        case "save": {
                            try {
                                Managers.CONFIG.save(helper, new String[0]);
                                Managers.CHAT.sendDeleteMessage("§aSaved the " + helper.getName() + " config.", "config5", 3000);
                            }
                            catch (IOException e) {
                                Managers.CHAT.sendDeleteMessage("§cAn error occurred while saving " + helper.getName() + ": " + "§f" + e.getMessage() + "§c" + ".", "config6", 3000);
                                e.printStackTrace();
                            }
                            return;
                        }
                        case "delete": {
                            Managers.CHAT.sendDeleteMessage("§cPlease specify a §f" + helper.getName() + "§c" + " config to delete!", "config6", 3000);
                            return;
                        }
                        case "load": {
                            Managers.CHAT.sendDeleteMessage("§cPlease specify a config to load.", "config6", 3000);
                            return;
                        }
                        case "refresh": {
                            final GuiScreen before = ConfigCommand.mc.currentScreen;
                            Scheduler.getInstance().schedule(() -> {
                                final Minecraft mc = ConfigCommand.mc;
                                new YesNoNonPausing((result, id) -> {
                                    ConfigCommand.mc.displayGuiScreen(before);
                                    if (!result) {
                                        return;
                                    }
                                    try {
                                        helper.refresh();
                                        Managers.CHAT.sendDeleteMessage("§aRefreshed the " + helper.getName() + " config.", "config7", 3000);
                                    }
                                    catch (IOException e) {
                                        Managers.CHAT.sendDeleteMessage("§cAn error occurred while saving " + helper.getName() + ": " + "§f" + e.getMessage() + "§c" + ".", "config6", 3000);
                                        e.printStackTrace();
                                    }
                                }, "§cReload the " + helper.getName() + " config from the disk.", "This action will override your current " + helper.getName() + " configs. Continue?", 1337);
                                final YesNoNonPausing yesNoNonPausing;
                                mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
                            });
                            return;
                        }
                        default: {
                            Managers.CHAT.sendDeleteMessage("§cCan't recognize option " + args[2] + ".", "config4", 3000);
                            break Label_1325;
                        }
                    }
                    break;
                }
                default: {
                    final String[] configs = Arrays.copyOfRange(args, 3, args.length);
                    final String cString = "config" + ((configs.length > 1) ? "s" : "");
                    final String lowerCase2 = args[2].toLowerCase();
                    switch (lowerCase2) {
                        case "save": {
                            this.displayYesNo("Sav", "Save the  " + helper.getName() + " - " + Arrays.toString(configs) + " " + cString + "?", helper, () -> {
                                Managers.CONFIG.save(helper, configs);
                                ChatUtil.sendMessage("§aSaved the §f" + helper.getName() + "§a" + " : " + "§f" + Arrays.toString(configs) + "§a" + " " + cString + ".");
                                return;
                            });
                            break Label_1325;
                        }
                        case "delete": {
                            try {
                                helper.delete(args[3]);
                                ChatUtil.sendMessage("§aDeleted §c" + args[3] + "§a" + " from the " + helper.getName() + "s config.");
                            }
                            catch (Exception e2) {
                                ChatUtil.sendMessage("§cCan't delete §f" + args[3] + "§c" + ": " + e2.getMessage());
                                e2.printStackTrace();
                            }
                            break Label_1325;
                        }
                        case "load": {
                            try {
                                Managers.CONFIG.load(helper, args[3]);
                                ChatUtil.sendMessage("§aLoaded the §f" + helper.getName() + "§a" + " : " + "§f" + args[3] + "§a" + " config.");
                            }
                            catch (IOException e3) {
                                ChatUtil.sendMessage("§cAn error occurred while loading the §f" + helper.getName() + "§c" + " : " + "§f" + args[3] + "§c" + " config.");
                                e3.printStackTrace();
                            }
                            break Label_1325;
                        }
                        case "refresh": {
                            this.displayYesNo("Refresh", "This action will override your current " + helper.getName() + " - " + Arrays.toString(configs) + " " + cString + ". Continue?", helper, () -> {
                                Managers.CONFIG.load(helper, configs);
                                ChatUtil.sendMessage("§aRefreshed the §f" + helper.getName() + "§a" + " : " + "§f" + Arrays.toString(configs) + "§a" + " " + cString + ".");
                                return;
                            });
                            break Label_1325;
                        }
                        default: {
                            ChatUtil.sendMessage("§cCan't recognize option §f" + args[2] + "§c" + ".");
                            break Label_1325;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length == 1) {
            return inputs.setRest(" <" + this.getConcatenatedHelpers() + "> <save/delete/load/refresh> <name>");
        }
        final ConfigHelper<?> helper = CommandUtil.getNameableStartingWith(args[1], Managers.CONFIG.getRegistered());
        if (helper == null) {
            return inputs.setCompletion("").setRest("§c config type not found");
        }
        switch (args.length) {
            case 2: {
                return inputs.setCompletion(TextUtil.substring(helper.getName(), args[1].length())).setRest(" <save/delete/load/refresh> <name>");
            }
            case 3: {
                return inputs;
            }
            default: {
                final Nameable nameable = CommandUtil.getNameableStartingWith(args[args.length - 1], helper.getConfigs());
                if (nameable != null) {
                    return inputs.setRest("").setCompletion(TextUtil.substring(nameable.getName(), args[args.length - 1].length()));
                }
                return inputs;
            }
        }
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        return super.onTabComplete(completer);
    }
    
    private String getConcatenatedHelpers() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<ConfigHelper<?>> it = Managers.CONFIG.getRegistered().iterator();
        while (it.hasNext()) {
            final ConfigHelper<?> helper = it.next();
            builder.append(helper.getName());
            if (it.hasNext()) {
                builder.append("/");
            }
        }
        return builder.toString();
    }
    
    private void displayYesNo(final String action, final String message2, final ConfigHelper<?> helper, final IORunnable runnable) {
        final GuiScreen before = ConfigCommand.mc.currentScreen;
        Scheduler.getInstance().schedule(() -> {
            final Minecraft mc = ConfigCommand.mc;
            new YesNoNonPausing((result, id) -> {
                ConfigCommand.mc.displayGuiScreen(before);
                if (!result) {
                    return;
                }
                try {
                    runnable.run();
                }
                catch (IOException e) {
                    Managers.CHAT.sendDeleteMessage("§cAn error occurred while " + action.toLowerCase() + "ing " + helper.getName() + ": " + "§f" + e.getMessage() + "§c" + ".", "config6", 3000);
                    e.printStackTrace();
                }
            }, "§c" + action + "ing the " + "§f" + helper.getName() + "§c" + " config.", message2, 1337);
            final YesNoNonPausing yesNoNonPausing;
            mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
        });
    }
}
