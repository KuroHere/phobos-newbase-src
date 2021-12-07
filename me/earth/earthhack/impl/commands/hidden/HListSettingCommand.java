//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.command.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.client.gui.*;
import java.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class HListSettingCommand extends Command implements Globals, CommandScheduler
{
    public HListSettingCommand() {
        super(new String[][] { { "hiddenlistsetting" }, { "module" } }, true);
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length > 1) {
            final Module module = Managers.MODULES.getObject(args[1]);
            if (module != null) {
                sendSettings(module);
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
    
    private static void sendSettings(final Module module) {
        Managers.CHAT.sendDeleteMessage(" ", module.getName() + "1", 7000);
        Managers.CHAT.sendDeleteComponent(new TextComponentString(module.getName() + " : " + "§7" + module.getCategory().toString()).setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(module.getData().getDescription()))))), module.getName() + "2", 7000);
        final int i = 2;
        for (final Setting<?> setting : module.getSettings()) {
            final ITextComponent component = (ITextComponent)ComponentFactory.create(setting);
            Managers.CHAT.sendDeleteComponent(component, setting.getName() + module.getName(), 7000);
        }
        Managers.CHAT.sendDeleteMessage(" ", module.getName() + "3", 7000);
        Scheduler.getInstance().schedule(() -> HListSettingCommand.mc.displayGuiScreen((GuiScreen)new GuiChat()));
        HListSettingCommand.SCHEDULER.submit(() -> HListSettingCommand.mc.addScheduledTask(() -> {
            if (HListSettingCommand.mc.ingameGUI != null) {
                HListSettingCommand.mc.ingameGUI.getChatGUI().scroll(1);
            }
        }), 100);
    }
    
    public static String create(final Module module) {
        return Commands.getPrefix() + "hiddenlistsetting " + module.getName();
    }
}
