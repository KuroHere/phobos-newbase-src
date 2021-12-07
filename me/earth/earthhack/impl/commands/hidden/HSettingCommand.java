//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;

public class HSettingCommand extends Command implements Globals
{
    public HSettingCommand() {
        super(new String[][] { { "hiddensetting" }, { "module" }, { "setting" } }, true);
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length > 2) {
            final Module module = Managers.MODULES.getObject(args[1]);
            if (module != null) {
                final Setting<?> setting = module.getSetting(args[2]);
                if (setting != null) {
                    if (args.length == 3) {
                        final String command = getCommand(setting, module);
                        Scheduler.getInstance().schedule(() -> HSettingCommand.mc.displayGuiScreen((GuiScreen)new GuiChat(command)));
                    }
                    else {
                        update(setting, module, args, false);
                    }
                }
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        return PossibleInputs.empty();
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        completer.setMcComplete(true);
        return completer;
    }
    
    public static String getCommand(final Setting<?> setting, final Module module) {
        return Commands.getPrefix() + module.getName() + " \"" + setting.getName() + "\" ";
    }
    
    public static void update(final Setting<?> setting, final Module module, final String[] args, final boolean ignoreArgs) {
        if (!setting.getName().equals("Enabled") || !(setting instanceof BooleanSetting)) {
            final List<String> settingNames = new ArrayList<String>(3 + module.getSettings().size());
            settingNames.add(module.getName() + "1");
            settingNames.add(module.getName() + "2");
            settingNames.add(module.getName() + "3");
            for (final Setting<?> s : module.getSettings()) {
                settingNames.add(s.getName() + module.getName());
            }
            if (!ignoreArgs) {
                setting.fromString(args[3]);
            }
            if (module.getSettings().size() != settingNames.size() - 3) {
                settingNames.forEach(n -> Managers.CHAT.deleteMessage(n, 7000));
                Scheduler.getInstance().schedule(() -> Managers.COMMANDS.applyCommand(HListSettingCommand.create(module)));
            }
            return;
        }
        if (ignoreArgs) {
            return;
        }
        final boolean bool = Boolean.parseBoolean(args[3]);
        if (bool) {
            module.enable();
        }
        else {
            module.disable();
        }
    }
}
