//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.chat.components.values.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.gui.chat.util.*;

public class NumberComponent<N extends Number, E extends NumberSetting<N>> extends SettingComponent<N, NumberSetting<N>>
{
    public static final IComponentFactory<?, ?> FACTORY;
    
    public NumberComponent(final E setting) {
        super(setting);
        if (!(setting.getContainer() instanceof Module)) {
            this.appendSibling((ITextComponent)new ValueComponent(setting));
            return;
        }
        final Module module = (Module)setting.getContainer();
        final HoverEvent numberHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> setting.getName() + " <" + setting.getValue().toString() + "> " + setting.getInputs(null)));
        HoverEvent plus;
        HoverEvent minus;
        if (setting.isFloating()) {
            plus = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> "Increment " + setting.getName() + " to " + "§b" + this.getNewValue(true) + "§f" + " by 0.1. Hold: " + "§c" + "ALT " + "§f" + ": 1.0, " + "§c" + "RCTRL " + "§f" + ": Max, " + "§c" + "LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%"));
            minus = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> "Decrement " + setting.getName() + " to " + "§b" + this.getNewValue(false) + "§f" + " by 0.1. Hold: " + "§c" + "ALT " + "§f" + ": 1.0, " + "§c" + "RCTRL " + "§f" + ": Min, " + "§c" + "LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%"));
        }
        else {
            plus = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> "Increment " + setting.getName() + " to " + "§b" + this.getNewValue(true) + "§f" + " by 1. Hold: " + "§c" + "ALT " + "§f" + ": 10, " + "§c" + "RCTRL " + "§f" + ": Max, " + "§c" + "LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%"));
            minus = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> "Decrement " + setting.getName() + " to " + "§b" + this.getNewValue(false) + "§f" + " by 1. Hold: " + "§c" + "ALT " + "§f" + ": 10, " + "§c" + "RCTRL " + "§f" + ": Min, " + "§c" + "LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%"));
        }
        this.appendSibling(new TextComponentString("§7 + §f").setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(plus)).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
            @Override
            public String getValue() {
                return Commands.getPrefix() + "hiddensetting " + module.getName() + " \"" + setting.getName() + "\" " + NumberComponent.this.getNewValue(true);
            }
        })));
        this.appendSibling(new ValueComponent(setting).setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(numberHover)).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, Commands.getPrefix() + "hiddensetting " + module.getName() + " \"" + setting.getName() + "\""))));
        this.appendSibling(new TextComponentString("§7 - §r").setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(minus)).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
            @Override
            public String getValue() {
                return Commands.getPrefix() + "hiddensetting " + module.getName() + " \"" + setting.getName() + "\" " + NumberComponent.this.getNewValue(false);
            }
        })));
    }
    
    private String getNewValue(final boolean plus) {
        String value;
        if (this.setting.isFloating()) {
            value = IncrementationUtil.crD(((Setting<Object>)this.setting).getValue() + "", ((NumberSetting<Object>)this.setting).getMin() + "", ((NumberSetting<Object>)this.setting).getMax() + "", !plus);
        }
        else {
            value = IncrementationUtil.crL(((Setting<Number>)this.setting).getValue().longValue(), ((NumberSetting<Number>)this.setting).getMin().longValue(), ((NumberSetting<Number>)this.setting).getMax().longValue(), !plus) + "";
        }
        return value;
    }
    
    static {
        FACTORY = new NumberComponentFactory<Object>();
    }
    
    private static final class NumberComponentFactory<F extends Number> implements IComponentFactory<F, NumberSetting<F>>
    {
        @Override
        public SettingComponent<F, NumberSetting<F>> create(final NumberSetting<F> setting) {
            return new NumberComponent<F, Object>((Object)setting);
        }
    }
}
