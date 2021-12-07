//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.tab;

import java.io.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;

public class GuiScreenTab extends GuiScreen
{
    private final TabModule module;
    
    public GuiScreenTab(final TabModule module) {
        this.module = module;
    }
    
    public boolean doesGuiPauseGame() {
        return this.module.pause.getValue();
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == this.module.getBind().getKey()) {
            this.module.disable();
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        super.setWorldAndResolution(mc, width, height);
        this.buttonList.clear();
        this.buttonList.add(new ExitButton(0, this.width - 24, 5));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.mc.world == null) {
            this.drawDefaultBackground();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            this.module.disable();
        }
    }
}
