//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import java.awt.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import java.util.function.*;
import me.earth.earthhack.impl.gui.chat.components.*;

public class ColorComponent extends SettingComponent<Color, ColorSetting>
{
    private int otherSettings;
    
    public ColorComponent(final ColorSetting setting) {
        super(setting);
        if (!(setting.getContainer() instanceof Module)) {
            return;
        }
        final Module m = (Module)setting.getContainer();
        for (final ColorEnum e : ColorEnum.values()) {
            this.appendSibling(this.supply(() -> "§7 +" + e.getTextColor(), 0).setStyle(new Style().setHoverEvent(this.getHoverEvent(e.name(), true)).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> e.getCommand(setting, true, m)))));
            this.appendSibling(this.supply(() -> e.getValue(setting) + "", 0).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(e.name() + " <0 - 255>"))).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, Commands.getPrefix() + "hiddensetting " + m.getName() + " \"" + setting.getName() + "\""))));
            this.appendSibling(this.supply(() -> "§7- §r", 0).setStyle(new Style().setHoverEvent(this.getHoverEvent(e.name(), false)).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> e.getCommand(setting, false, m)))));
        }
        this.appendSibling(this.supply(() -> (setting.isSync() ? "§a" : "§c") + " Sync", 1).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Un/Sync this color."))).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> () -> setting.setSync(!setting.isSync())))));
        this.appendSibling(this.supply(() -> (setting.isRainbow() ? "§a" : "§c") + " Rainbow", 1).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Make this color rainbow."))).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> () -> setting.setRainbow(!setting.isRainbow())))));
        this.appendSibling(this.supply(() -> (setting.isStaticRainbow() ? "§a" : "§c") + " Static", 1).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Make this color a static rainbow."))).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> () -> setting.setStaticRainbow(!setting.isStaticRainbow())))));
        for (final RainbowEnum r : RainbowEnum.values()) {
            this.appendSibling(this.supply(() -> "§7 +" + r.getColor(), 2).setStyle(new Style().setHoverEvent(this.getFloatEvent(r.name(), true)).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> r.getCommand(setting, true, m)))));
            this.appendSibling(this.supply(() -> r.getValue(setting) + "", 2).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(r.name() + " " + r.getRange()))).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, Commands.getPrefix() + "hiddensetting " + m.getName() + " \"" + setting.getName() + "\""))));
            this.appendSibling(this.supply(() -> "§7- §r", 2).setStyle(new Style().setHoverEvent(this.getFloatEvent(r.name(), false)).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> r.getCommand(setting, false, m)))));
        }
        this.appendSibling(new TextComponentString("§7 \u2699").setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new SuppliedComponent(() -> {
            switch (this.otherSettings) {
                case 0: {
                    return "Show more settings";
                }
                case 1: {
                    return "Show rainbow settings.";
                }
                case 2: {
                    return "Show r,g,b settings.";
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }))).setClickEvent((ClickEvent)new SuppliedRunnableClickEvent(() -> () -> this.otherSettings = ++this.otherSettings % 3))));
    }
    
    @Override
    public String getText() {
        if (((ColorSetting)this.setting).isRainbow() || ((ColorSetting)this.setting).isStaticRainbow()) {
            return super.getText() + "§y" + "\u2588";
        }
        return super.getText() + "§z" + TextUtil.get32BitString(((ColorSetting)this.setting).getValue().getRGB()) + "\u2588";
    }
    
    private HoverEvent getHoverEvent(final String color, final boolean incr) {
        return ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString((incr ? "In" : "De") + "crement " + color.toLowerCase() + " value by 1. Hold: " + "§c" + "ALT " + "§f" + ": 10," + "§c" + " RCTRL" + "§f" + " : " + (incr ? "Max" : "Min") + "§c" + " LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%")));
    }
    
    private HoverEvent getFloatEvent(final String color, final boolean incr) {
        return ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString((incr ? "In" : "De") + "crement " + color.toLowerCase() + " value by 0.1. Hold: " + "§c" + "ALT " + "§f" + ": 1.0, " + "§c" + "RCTRL " + "§f" + ": Max, " + "§c" + "LCTRL " + "§f" + ": 5%, " + "§c" + "LCTRL + ALT " + "§f" + ": 10%")));
    }
    
    private SuppliedComponent supply(final Supplier<String> s, final int isOtherSettings) {
        return new SuppliedHoverableComponent(() -> {
            if (this.otherSettings == isOtherSettings) {
                return (String)s.get();
            }
            else {
                return "";
            }
        }, () -> this.otherSettings == isOtherSettings);
    }
    
    @Override
    public TextComponentString createCopy() {
        final ColorComponent component = new ColorComponent((ColorSetting)this.setting);
        component.otherSettings = this.otherSettings;
        return component;
    }
}
