//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.impl.commands.hidden.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.client.macro.*;
import java.util.stream.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.api.register.exception.*;
import java.util.*;

public class MacroCommand extends Command
{
    private static final BindSetting BIND_INSTANCE;
    private final List<AbstractMultiMacroCommand<?>> custom;
    
    public MacroCommand() {
        super(new String[][] { { "macro" }, { "add", "del", "release", "use" }, { "name" }, { "bind", "release" }, { "flow", "combine", "command" } });
        this.custom = new ArrayList<AbstractMultiMacroCommand<?>>();
        CommandDescriptions.register(this, "Manage your Macros. Use §lflow§r to create a macro that switches between the given macros everytime its used. Use §lcombine§r to combine multiple macros into one. You can also use these features to combine or flow macros even further customizing your macros to the maximum.§l Release§r <true/false> allows you to make macros that toggle when you release a key.");
        this.custom.add(new HMacroCombineCommand());
        this.custom.add(new HMacroFlowCommand());
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            final ITextComponent component = (ITextComponent)new TextComponentString("Macros: ");
            final Macro m;
            final Iterator<Macro> iterator = Managers.MACRO.getRegistered().stream().filter(m -> m.getType() != MacroType.DELEGATE).collect((Collector<? super Macro, ?, List<? super Macro>>)Collectors.toList()).iterator();
            while (iterator.hasNext()) {
                final Macro macro = iterator.next();
                final ITextComponent macroComp = (ITextComponent)new TextComponentString("§b" + macro.getName());
                macroComp.setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Bind: §b" + macro.getBind().toString() + "§f" + ", Command: " + "§c" + Arrays.toString(macro.getCommands()))))));
                component.appendSibling(macroComp);
                if (iterator.hasNext()) {
                    component.appendSibling((ITextComponent)new TextComponentString("§f, "));
                }
            }
            Managers.CHAT.sendDeleteComponent(component, "Macros", 3000);
            return;
        }
        if (args.length == 2) {
            ChatUtil.sendMessage("§cPlease Specify a Macro");
            return;
        }
        if (args.length >= 3) {
            if (args[1].equalsIgnoreCase("use")) {
                this.executeMacro(args[2]);
                return;
            }
            if (args[1].equalsIgnoreCase("release")) {
                final Macro m = Managers.MACRO.getObject(args[2]);
                if (m == null) {
                    ChatUtil.sendMessage("§cMacro §f" + args[2] + "§c" + " doesn't exist.");
                }
                else if (args.length == 3) {
                    final boolean r = m.isRelease();
                    ChatUtil.sendMessage("§aMacro §b" + args[2] + "§a" + (r ? " toggles" : " doesn't toggle") + " on release.");
                }
                else {
                    final boolean r = Boolean.parseBoolean(args[3]);
                    m.setRelease(r);
                    ChatUtil.sendMessage("§aMacro §b" + args[2] + "§a" + " now" + (r ? " toggles " : " doesn't toggle ") + "on releasing the key.");
                }
                return;
            }
        }
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("del")) {
                this.delMacro(args);
            }
            else if (args[1].equalsIgnoreCase("add")) {
                ChatUtil.sendMessage("§cPlease specify a bind and command.");
            }
            else {
                this.onInvalidInput(args);
            }
        }
        else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("del")) {
                this.delMacro(args);
            }
            else if (args[1].equalsIgnoreCase("add")) {
                ChatUtil.sendMessage("§cPlease specify a command.");
            }
            else {
                this.onInvalidInput(args);
            }
        }
        else if (args[1].equalsIgnoreCase("del")) {
            this.delMacro(args);
        }
        else if (args[1].equalsIgnoreCase("add")) {
            for (final Command command : this.custom) {
                if (command.fits(Arrays.copyOfRange(args, 4, args.length))) {
                    command.execute(args);
                    return;
                }
            }
            final String name = args[2];
            final String bind = args[3];
            final String comm = CommandUtil.concatenate(args, 4);
            final Bind parsed = Bind.fromString(bind);
            final Macro macro2 = new Macro(name, parsed, new String[] { comm });
            try {
                Managers.MACRO.register(macro2);
                ChatUtil.sendMessage("§aAdded new Macro: §f" + macro2.getName() + " : " + "§b" + parsed + "§f" + " : " + "§c" + Commands.getPrefix() + comm);
            }
            catch (AlreadyRegisteredException e) {
                ChatUtil.sendMessage("§cCouldn't add Macro §f" + macro2.getName() + "§c" + ", a Macro with that name already exists.");
            }
        }
        else {
            this.onInvalidInput(args);
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length < 3) {
            return inputs;
        }
        if (args.length == 3) {
            final Macro macro = this.getMacroStartingWith(args[2]);
            if ((args[1].equalsIgnoreCase("use") || args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("release")) && macro == null) {
                return inputs.setCompletion("").setRest("§c not found");
            }
            if (args[1].equalsIgnoreCase("add") && macro != null) {
                return inputs.setCompletion(TextUtil.substring(macro.getName(), args[2].length())).setRest("§c <Macro: §f" + macro.getName() + "§c" + "> already exists.");
            }
            if (macro == null) {
                return inputs.setCompletion("").setRest(" <bind> <flow/combine/command>");
            }
            inputs.setCompletion(TextUtil.substring(macro.getName(), args[2].length()));
            if (args[1].equalsIgnoreCase("release")) {
                return inputs.setRest(" <true/false>");
            }
            return inputs.setRest("");
        }
        else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("release")) {
                final String s = CommandUtil.completeBoolean(args[3]);
                if (s == null) {
                    return inputs.setCompletion("").setRest("§c try true/false");
                }
                return inputs.setCompletion(s).setRest("");
            }
            else {
                if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("use")) {
                    return PossibleInputs.empty();
                }
                return inputs.setCompletion(TextUtil.substring(MacroCommand.BIND_INSTANCE.getInputs(args[3]), args[3].length())).setRest(" <flow/combine/command>");
            }
        }
        else {
            if (args[2].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("use") || args[1].equalsIgnoreCase("release")) {
                return PossibleInputs.empty();
            }
            final String[] arguments = Arrays.copyOfRange(args, 4, args.length);
            for (final Command command : this.custom) {
                if (command.fits(arguments)) {
                    return command.getPossibleInputs(arguments);
                }
            }
            final Command target = Managers.COMMANDS.getCommandForMessage(arguments);
            if (target == null) {
                return PossibleInputs.empty();
            }
            return target.getPossibleInputs(arguments);
        }
    }
    
    private void onInvalidInput(final String[] args) {
        final Macro macro = this.getMacroStartingWith(args[2]);
        if (macro == null) {
            Earthhack.getLogger().warn(Arrays.toString(args));
            ChatUtil.sendMessage("§cUsage is <add/del>.");
        }
        else {
            ChatUtil.sendMessage("§cBad Input, info about §f" + macro.getName() + "§c" + ": " + "§f" + "<" + "§b" + "bind: " + macro.getBind().toString() + "§f" + "> <" + "§b" + "commands: " + Arrays.toString(macro.getCommands()) + "§f" + ">");
        }
    }
    
    private void delMacro(final String[] args) {
        final Macro macro = this.getMacroStartingWith(args[2]);
        if (macro == null) {
            ChatUtil.sendMessage("§cCouldn't find macro " + args[2] + ".");
            return;
        }
        if (macro.getName().equalsIgnoreCase(args[2])) {
            try {
                Managers.MACRO.unregister(macro);
                ChatUtil.sendMessage("Removed Macro §c" + args[2] + "§f" + ".");
            }
            catch (CantUnregisterException e) {
                ChatUtil.sendMessage("Could not unregister Macro §c" + args[2] + "§f" + ".");
            }
        }
        else {
            ChatUtil.sendMessage("§cCouldn't find " + args[2] + " did you mean " + "§b" + macro.getName() + "§c" + "?");
        }
    }
    
    private void executeMacro(final String name) {
        final Macro macro = Managers.MACRO.getObject(name);
        if (macro == null) {
            ChatUtil.sendMessage("§cMacro §f" + name + "§c" + " couldn't be found!");
            return;
        }
        if (Managers.MACRO.isSafe()) {
            macro.execute(Managers.COMMANDS);
        }
        else {
            try {
                macro.execute(Managers.COMMANDS);
            }
            catch (Throwable t) {
                ChatUtil.sendMessage("§cAn error occurred while executing macro §f" + name + "§c" + ": " + ((t.getMessage() == null) ? t.getClass().getName() : t.getMessage()));
            }
        }
    }
    
    private Macro getMacroStartingWith(final String name) {
        return CommandUtil.getNameableStartingWith(name, (Collection<Macro>)Managers.MACRO.getRegistered().stream().filter(m -> m.getType() != MacroType.DELEGATE).collect((Collector<? super Macro, ?, Collection<T>>)Collectors.toList()));
    }
    
    static {
        BIND_INSTANCE = new BindSetting("Bind");
    }
}
