//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.impl.gui.chat.components.setting.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.module.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.api.setting.*;

public class RemovingIntegerComponent extends NumberComponent<Integer, RemovingInteger>
{
    protected static final IComponentFactory<Integer, RemovingInteger> FACTORY;
    
    public RemovingIntegerComponent(final RemovingInteger setting) {
        super(setting);
        if (setting.getContainer() instanceof Module) {
            final Module module = (Module)setting.getContainer();
            final HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("Removes this Setting"));
            this.appendSibling(new TextComponentString("§cRemove ").setStyle(new Style().setHoverEvent(event).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + module.getName() + " \"" + setting.getName() + "\" remove";
                }
            })));
        }
    }
    
    static {
        FACTORY = new RemovingIntegerFactory();
    }
    
    private static final class RemovingIntegerFactory implements IComponentFactory<Integer, RemovingInteger>
    {
        @Override
        public SettingComponent<Integer, RemovingInteger> create(final RemovingInteger setting) {
            return (SettingComponent<Integer, RemovingInteger>)new RemovingIntegerComponent(setting);
        }
    }
}
