//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable.setting.component;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.text.*;

public class SimpleRemovingComponent extends SettingComponent<Boolean, SimpleRemovingSetting>
{
    public SimpleRemovingComponent(final SimpleRemovingSetting setting) {
        super(setting);
        final SimpleComponent value = new SimpleComponent("Remove");
        value.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        if (setting.getContainer() instanceof Module) {
            value.getStyle().setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" remove";
                }
            });
        }
        this.appendSibling((ITextComponent)value);
    }
    
    @Override
    public String getText() {
        return super.getText() + "§c";
    }
    
    @Override
    public String getUnformattedComponentText() {
        return this.getText();
    }
    
    @Override
    public TextComponentString createCopy() {
        return ComponentFactory.create(this.setting);
    }
}
