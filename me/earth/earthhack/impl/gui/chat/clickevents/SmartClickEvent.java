//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.clickevents;

import net.minecraft.util.text.event.*;

public abstract class SmartClickEvent extends ClickEvent
{
    public SmartClickEvent(final ClickEvent.Action theAction) {
        super(theAction, "$smart_click_value$");
    }
    
    public abstract String getValue();
    
    public boolean equals(final Object o) {
        return o instanceof SmartClickEvent && super.equals(o);
    }
    
    public int hashCode() {
        return super.hashCode() + 1;
    }
}
