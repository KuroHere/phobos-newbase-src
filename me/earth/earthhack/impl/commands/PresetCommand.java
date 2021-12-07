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
import me.earth.earthhack.api.config.preset.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.data.*;
import java.util.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.*;

public class PresetCommand extends AbstractModuleCommand implements Globals
{
    public PresetCommand() {
        super(new String[][] { { "preset" }, { "module" }, { "preset" } }, 1);
        CommandDescriptions.register(this, "Apply only the best, carefully handpicked configs from the devs to modules.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cUse this command to apply a preset to a module.");
            return;
        }
        final Module module = Managers.MODULES.getObject(args[1]);
        if (module == null) {
            ChatUtil.sendMessage("§cCould not find module §f" + args[1] + "§c" + ".");
            return;
        }
        final ModuleData data = module.getData();
        if (data == null) {
            ChatUtil.sendMessage("§cThe module §f" + args[1] + "§c" + " has no Module-Data!");
            return;
        }
        if (args.length == 2) {
            boolean first = true;
            final ComponentBuilder builder = new ComponentBuilder(args[1] + "-Presets: ");
            for (final ModulePreset preset : data.getPresets()) {
                if (!first) {
                    builder.sibling("§f, §b").append();
                }
                first = false;
                builder.sibling("§b" + preset.getName()).addHover(preset.getDescription()).addSmartClickEvent("preset " + args[1] + " " + preset.getName()).append();
            }
            if (first) {
                ChatUtil.sendMessage("§cThe module §f" + args[1] + "§c" + " has no Presets.");
                return;
            }
            ChatUtil.sendComponent(builder.build());
        }
        else {
            ModulePreset result = null;
            for (final ModulePreset preset2 : data.getPresets()) {
                if (preset2.getName().equalsIgnoreCase(args[2])) {
                    result = preset2;
                    break;
                }
            }
            if (result == null) {
                ChatUtil.sendMessage("§cThe module §f" + args[1] + "§c" + " doesn't have a " + "§b" + args[2] + "§c" + " preset.");
                return;
            }
            final ModulePreset finalResult = result;
            final GuiScreen before = PresetCommand.mc.currentScreen;
            Scheduler.getInstance().schedule(() -> {
                final Minecraft mc = PresetCommand.mc;
                new YesNoNonPausing((r, id) -> {
                    PresetCommand.mc.displayGuiScreen(before);
                    if (!r) {
                        return;
                    }
                    ChatUtil.sendMessage("§aApplying preset §b" + finalResult.getName() + "§a" + " to " + "§f" + module.getName() + "§a" + ".");
                    finalResult.apply();
                }, "§cApply preset §f" + finalResult.getName() + "§c" + " to module " + "§f" + module.getName() + "§c" + "?", "This will override your current settings for " + module.getName() + ".", 1337);
                final YesNoNonPausing yesNoNonPausing;
                mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
            });
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 2) {
            if (args.length == 3) {
                final Module module = Managers.MODULES.getObject(args[1]);
                if (module == null) {
                    return PossibleInputs.empty();
                }
                final ModuleData data = module.getData();
                if (data == null) {
                    return PossibleInputs.empty();
                }
                final ModulePreset preset = CommandUtil.getNameableStartingWith(args[2], data.getPresets());
                if (preset != null) {
                    return new PossibleInputs(TextUtil.substring(preset.getName(), args[2].length()), "");
                }
            }
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
}
