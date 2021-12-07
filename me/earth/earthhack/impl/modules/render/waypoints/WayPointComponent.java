//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.waypoints;

import net.minecraft.util.math.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;

public class WayPointComponent extends SettingComponent<BlockPos, WayPointSetting>
{
    public WayPointComponent(final WayPointSetting setting) {
        super(setting);
        final SimpleComponent remove = new SimpleComponent("§cRemove");
        remove.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        if (setting.getContainer() instanceof Module) {
            remove.getStyle().setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" remove";
                }
            });
        }
        SimpleComponent value;
        if (setting.isCorrupted()) {
            value = new SimpleComponent("§cCorrupted ");
            value.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("This settings config got corrupted!"))));
        }
        else {
            final BlockPos pos = setting.getValue();
            final String t = setting.getType().toString().substring(0, 1);
            value = new SimpleComponent("(" + t + "," + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ") ");
            value.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        }
        this.appendSibling((ITextComponent)value);
        this.appendSibling((ITextComponent)remove);
    }
    
    @Override
    public String getText() {
        return super.getText() + "§f";
    }
}
