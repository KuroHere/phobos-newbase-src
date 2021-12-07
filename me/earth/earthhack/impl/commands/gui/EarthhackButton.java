//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.gui;

import me.earth.earthhack.impl.gui.buttons.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.gui.*;

public class EarthhackButton extends SimpleButton implements Globals
{
    public EarthhackButton(final int buttonID, final int xPos, final int yPos) {
        super(buttonID, xPos, yPos, 0, 40, 0, 60);
    }
    
    @Override
    public void onClick(final GuiScreen parent, final int id) {
        EarthhackButton.mc.displayGuiScreen((GuiScreen)new CommandGui(parent, id));
    }
}
