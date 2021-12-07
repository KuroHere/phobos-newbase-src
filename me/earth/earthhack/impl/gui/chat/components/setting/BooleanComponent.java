//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.gui.chat.components.values.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.module.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.text.*;

public class BooleanComponent extends SettingComponent<Boolean, BooleanSetting>
{
    public BooleanComponent(final BooleanSetting setting) {
        super(setting);
        final ValueComponent value = new ValueComponent(setting);
        value.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        if (setting.getContainer() instanceof Module) {
            value.getStyle().setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" " + !setting.getValue();
                }
            });
        }
        this.appendSibling((ITextComponent)value);
    }
    
    @Override
    public String getText() {
        return super.getText() + (((BooleanSetting)this.setting).getValue() ? "§a" : "§c");
    }
}
