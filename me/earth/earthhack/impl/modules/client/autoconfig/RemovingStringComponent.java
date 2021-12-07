//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.autoconfig;

import me.earth.earthhack.impl.gui.chat.components.setting.*;
import me.earth.earthhack.api.module.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class RemovingStringComponent extends DefaultComponent<String, RemovingString>
{
    public RemovingStringComponent(final RemovingString setting) {
        super(setting);
        if (setting.getContainer() instanceof Module) {
            final Module module = (Module)setting.getContainer();
            final HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Removes this Setting"));
            this.appendSibling(new TextComponentString("§c Remove ").setStyle(new Style().setHoverEvent(event).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + module.getName() + " \"" + setting.getName() + "\" remove";
                }
            })));
        }
    }
    
    @Override
    public String getText() {
        return ((RemovingString)this.setting).getName() + "§7" + " : " + "§6";
    }
}
