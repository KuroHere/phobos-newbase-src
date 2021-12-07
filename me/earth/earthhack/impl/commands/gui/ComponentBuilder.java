//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.gui;

import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public class ComponentBuilder
{
    private final ITextComponent base;
    private ITextComponent current;
    
    public ComponentBuilder(final String start) {
        this((ITextComponent)new TextComponentString(start));
    }
    
    public ComponentBuilder(final ITextComponent base) {
        this.base = base;
        this.current = base;
    }
    
    public ComponentBuilder append() {
        this.base.appendSibling(this.current);
        return this;
    }
    
    public ComponentBuilder sibling(final String siblingText) {
        return this.sibling((ITextComponent)new TextComponentString(siblingText));
    }
    
    public ComponentBuilder sibling(final ITextComponent sibling) {
        this.current = sibling;
        return this;
    }
    
    public ComponentBuilder addHover(final String hoverText) {
        return this.addHover(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(hoverText));
    }
    
    public ComponentBuilder addHover(final HoverEvent.Action actionIn, final ITextComponent valueIn) {
        return this.addHover(new HoverEvent(actionIn, valueIn));
    }
    
    public ComponentBuilder addHover(final HoverEvent event) {
        this.current.getStyle().setHoverEvent(event);
        return this;
    }
    
    public ComponentBuilder addSmartClickEvent(final String command) {
        return this.addClickEvent(new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
            @Override
            public String getValue() {
                return Commands.getPrefix() + command;
            }
        });
    }
    
    public ComponentBuilder addClickEvent(final ClickEvent event) {
        this.current.getStyle().setClickEvent(event);
        return this;
    }
    
    public ITextComponent build() {
        return this.base;
    }
}
