//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.util.text.*;
import java.util.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;

public class HelpCommand extends Command implements Globals
{
    public HelpCommand() {
        super(new String[][] { { "help" } });
        CommandDescriptions.register(this, "Get a list and help for all commands.");
    }
    
    @Override
    public void execute(final String[] args) {
        final ITextComponent component = (ITextComponent)new TextComponentString("Following commands are available: ");
        final Iterator<Command> it = Managers.COMMANDS.getRegistered().iterator();
        while (it.hasNext()) {
            final Command command = it.next();
            if (command != null) {
                final ITextComponent sibling = (ITextComponent)new TextComponentString("§b" + command.getName() + "§f" + (it.hasNext() ? ", " : ""));
                final String descr = CommandDescriptions.getDescription(command);
                final HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString((descr == null) ? "A command." : descr));
                ChatComponentUtil.setOffset(event);
                final Style style = new Style().setHoverEvent(event);
                if (command instanceof ModuleCommand) {
                    style.setClickEvent((ClickEvent)new RunnableClickEvent(() -> this.setText(Commands.getPrefix() + "AutoCrystal")));
                }
                else {
                    style.setClickEvent((ClickEvent)new RunnableClickEvent(() -> this.setText(Commands.getPrefix() + command.getName())));
                }
                sibling.setStyle(style);
                component.appendSibling(sibling);
            }
        }
        ChatUtil.sendComponent(component);
    }
    
    private void setText(final String text) {
        final GuiScreen current = HelpCommand.mc.currentScreen;
        if (current instanceof CommandGui) {
            ((CommandGui)current).setText(text);
        }
        else {
            HelpCommand.mc.displayGuiScreen((GuiScreen)new GuiChat(text));
        }
    }
}
