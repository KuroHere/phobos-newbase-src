//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.abstracts;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.*;
import java.util.*;
import me.earth.earthhack.impl.managers.client.macro.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.api.register.*;

public abstract class AbstractMultiMacroCommand<T extends Macro> extends Command implements Globals
{
    private final String ifSmallArgs;
    private final String macroName;
    
    public AbstractMultiMacroCommand(final String[][] usage, final String macroName, final String ifSmallArgs) {
        super(usage, true);
        this.macroName = macroName;
        this.ifSmallArgs = ifSmallArgs;
    }
    
    protected abstract T getMacro(final String p0, final Bind p1, final Macro... p2);
    
    @Override
    public void execute(final String[] args) {
        if (args.length <= 5) {
            ChatUtil.sendMessage("§c" + this.ifSmallArgs);
            return;
        }
        final Macro[] macros = new Macro[args.length - 5];
        final Macro[] realMacros = new Macro[args.length - 5];
        Macro macro = null;
        for (int i = 5; i < args.length; ++i) {
            macro = Managers.MACRO.getObject(args[i]);
            if (macro == null) {
                ChatUtil.sendMessage("§cCouldn't find macro: §f" + args[i] + "§c" + ".");
                return;
            }
            realMacros[i - 5] = macro;
            if (macro.getType() == MacroType.COMBINED || macro.getType() == MacroType.FLOW) {
                Earthhack.getLogger().info("Creating Delegate for Macro: " + macro.getName() + " : " + Arrays.toString(macro.getCommands()));
                String name;
                for (name = "CopyOf-" + macro.getName(); Managers.MACRO.getObject(name) != null; name += "I") {}
                final DelegateMacro extraDelegate = DelegateMacro.delegate(name, macro);
                String name2;
                for (name2 = "Delegate-" + macro.getName(); Managers.MACRO.getObject(name2) != null; name2 += "I") {}
                final DelegateMacro delegate = new DelegateMacro(name2, extraDelegate.getName());
                try {
                    ((IterationRegister<DelegateMacro>)Managers.MACRO).register(extraDelegate);
                }
                catch (AlreadyRegisteredException e) {
                    ChatUtil.sendMessage("§cAn error occurred while delegating your macro: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                try {
                    ((IterationRegister<DelegateMacro>)Managers.MACRO).register(delegate);
                }
                catch (AlreadyRegisteredException e) {
                    ChatUtil.sendMessage("§cAn error occurred while delegating your macro: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                macros[i - 5] = delegate;
            }
            else {
                macros[i - 5] = macro;
            }
        }
        final String name3 = args[2];
        final String bind = args[3];
        final Bind parsed = Bind.fromString(bind);
        final T macro2 = this.getMacro(name3, parsed, macros);
        final StringBuilder conc = new StringBuilder();
        for (int j = 0; j < realMacros.length; ++j) {
            conc.append("§c").append(realMacros[j].getName());
            if (j != realMacros.length - 1) {
                conc.append("§f").append(", ");
            }
        }
        final String concatenated = conc.append("§f").toString();
        final GuiScreen before = AbstractMultiMacroCommand.mc.currentScreen;
        Scheduler.getInstance().schedule(() -> {
            final Minecraft mc = AbstractMultiMacroCommand.mc;
            new YesNoNonPausing((result, id) -> {
                AbstractMultiMacroCommand.mc.displayGuiScreen(before);
                if (!result) {
                    this.registerMacro(macro, parsed, concatenated);
                    return;
                }
                for (int i = 0; i < realMacros.length; ++i) {
                    try {
                        Managers.MACRO.unregister(realMacros[i]);
                    }
                    catch (CantUnregisterException e) {
                        ChatUtil.sendMessage("§cA critical error occurred: §f" + realMacros[i].getName() + "§c" + " can't be deleted (" + e.getMessage() + ").");
                        e.printStackTrace();
                    }
                }
                this.registerMacro(macro, parsed, concatenated);
            }, "", "Do you want to delete the macros " + concatenated + " ?", 1337);
            final YesNoNonPausing yesNoNonPausing;
            mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
        });
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length == 1) {
            return inputs;
        }
        inputs.setRest(" <macro> <macro> <...>");
        final Macro macro = CommandUtil.getNameableStartingWith(args[args.length - 1], Managers.MACRO.getRegistered());
        if (macro == null) {
            return inputs.setCompletion("").setRest("§c not found");
        }
        return inputs.setCompletion(TextUtil.substring(macro.getName(), args[args.length - 1].length()));
    }
    
    private void registerMacro(final Macro macro, final Bind parsed, final String concatenated) {
        try {
            Managers.MACRO.register(macro);
            ChatUtil.sendMessage("§aAdded new " + this.macroName + ": " + "§f" + macro.getName() + " : " + "§b" + parsed.toString() + "§f" + " : " + "§c" + Commands.getPrefix() + concatenated + ".");
        }
        catch (AlreadyRegisteredException e) {
            ChatUtil.sendMessage("§cCouldn't add Macro §f" + macro.getName() + "§c" + ", a Macro with that name already exists.");
        }
    }
}
