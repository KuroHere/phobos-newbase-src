//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import me.earth.earthhack.impl.gui.chat.*;

public class ModuleListCommand extends Command
{
    public ModuleListCommand() {
        super(new String[][] { { "modules" } });
        CommandDescriptions.register(this, "List all modules in the client. Leftclick a module to toggle it. Middleclick a module to open the chatgui and get a list of its settings.");
    }
    
    @Override
    public void execute(final String[] args) {
        Managers.CHAT.sendDeleteComponent(getComponent(), "moduleListCommand", 2000);
    }
    
    public static ITextComponent getComponent() {
        final AbstractTextComponent component = new SimpleComponent("Modules: ");
        component.setWrap(true);
        final List<Module> moduleList = Managers.MODULES.getRegistered().stream().sorted(Comparator.comparing((Function<? super Module, ? extends Comparable>)Displayable::getDisplayName)).collect((Collector<? super Module, ?, List<Module>>)Collectors.toList());
        for (int i = 0; i < moduleList.size(); ++i) {
            final Module module = moduleList.get(i);
            if (module != null) {
                final int finalI = i;
                final ITextComponent sibling = (ITextComponent)new SuppliedComponent(() -> (module.isEnabled() ? "§a" : "§c") + module.getName() + ((finalI == moduleList.size() - 1) ? "" : ", ")).setWrap(true);
                final Style style = new Style().setHoverEvent(ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(module.getData().getDescription())))).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                    @Override
                    public String getValue() {
                        return Commands.getPrefix() + "toggle " + module.getName();
                    }
                });
                ((IStyle)style).setSuppliedInsertion(() -> Commands.getPrefix() + module.getName());
                ((IStyle)style).setMiddleClickEvent(new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                    @Override
                    public String getValue() {
                        return Commands.getPrefix() + module.getName();
                    }
                });
                sibling.setStyle(style);
                component.appendSibling(sibling);
            }
        }
        return (ITextComponent)component;
    }
}
