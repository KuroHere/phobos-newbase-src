//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import me.earth.earthhack.impl.core.util.*;
import java.util.function.*;
import me.earth.earthhack.api.module.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.text.*;

public abstract class SettingComponent<T, S extends Setting<T>> extends AbstractTextComponent
{
    protected final S setting;
    
    public SettingComponent(final S setting) {
        super(setting.getName());
        this.setting = setting;
        this.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        ((ITextComponentBase)this).setFormattingHook(new SimpleTextFormatHook((TextComponentBase)this));
        ((ITextComponentBase)this).setUnFormattedHook(new SimpleTextFormatHook((TextComponentBase)this));
        if (setting.getContainer() instanceof Module) {
            this.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\""));
        }
    }
    
    @Override
    public String getText() {
        return this.setting.getName() + "§7" + " : " + "§f";
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
