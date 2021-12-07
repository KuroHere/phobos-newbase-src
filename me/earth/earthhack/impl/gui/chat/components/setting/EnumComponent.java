//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.components.values.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class EnumComponent<A extends Enum<A>> extends SettingComponent<A, EnumSetting<A>>
{
    public static final IComponentFactory<?, ?> FACTORY;
    
    public EnumComponent(final EnumSetting<A> setting) {
        super(setting);
        if (!(setting.getContainer() instanceof Module)) {
            this.appendSibling(new ValueComponent(setting).setStyle(this.getStyle()));
            return;
        }
        this.appendSibling(new ValueComponent(setting).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new EnumHoverComponent((EnumSetting<Enum>)setting))).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
            @Override
            public String getValue() {
                final Enum<?> next = EnumHelper.next((Enum<?>)setting.getValue());
                return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" " + next.name();
            }
        })));
    }
    
    @Override
    public String getText() {
        return super.getText() + "§b";
    }
    
    static {
        FACTORY = new EnumComponentFactory<Object>();
    }
    
    private static final class EnumComponentFactory<F extends Enum<F>> implements IComponentFactory<F, EnumSetting<F>>
    {
        @Override
        public SettingComponent<F, EnumSetting<F>> create(final EnumSetting<F> setting) {
            return (SettingComponent<F, EnumSetting<F>>)new EnumComponent((EnumSetting<Enum>)setting);
        }
    }
}
