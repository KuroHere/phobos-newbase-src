//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.gui;

import net.minecraft.client.gui.*;

public class YesNoNonPausing extends GuiYesNo
{
    public YesNoNonPausing(final GuiYesNoCallback parentScreenIn, final String messageLine1In, final String messageLine2In, final int parentButtonClickedIdIn) {
        super(parentScreenIn, messageLine1In, messageLine2In, parentButtonClickedIdIn);
    }
    
    public YesNoNonPausing(final GuiYesNoCallback parentScreenIn, final String messageLine1In, final String messageLine2In, final String confirmButtonTextIn, final String cancelButtonTextIn, final int parentButtonClickedIdIn) {
        super(parentScreenIn, messageLine1In, messageLine2In, confirmButtonTextIn, cancelButtonTextIn, parentButtonClickedIdIn);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
}
