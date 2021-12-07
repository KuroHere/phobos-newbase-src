//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.plugin.*;
import me.earth.earthhack.impl.managers.client.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import me.earth.earthhack.impl.util.text.*;
import java.util.*;

public class PluginCommand extends Command
{
    public PluginCommand() {
        super(new String[][] { { "plugin" } });
        CommandDescriptions.register(this, "Get a list of the currently active plugins.");
    }
    
    @Override
    public boolean fits(final String[] args) {
        return args[0].length() > 0 && TextUtil.startsWith("plugins", args[0]);
    }
    
    @Override
    public void execute(final String[] args) {
        final ITextComponent component = (ITextComponent)new TextComponentString("Active Plugins: ");
        final Iterator<PluginConfig> itr = PluginManager.getInstance().getPlugins().keySet().iterator();
        while (itr.hasNext()) {
            final PluginConfig config = itr.next();
            final Plugin plugin = PluginManager.getInstance().getPlugins().get(config);
            if (plugin == null) {
                continue;
            }
            String description = PluginDescriptions.getDescription(plugin);
            if (description == null) {
                description = "A Plugin.";
            }
            component.appendSibling(new TextComponentString("§b" + config.getName() + (itr.hasNext() ? "§f, " : "")).setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(description))))));
        }
        ChatUtil.sendComponent(component);
    }
}
