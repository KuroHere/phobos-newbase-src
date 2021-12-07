//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.gui;

import net.minecraft.client.gui.*;
import java.io.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.managers.*;

public class CommandChatGui extends GuiChat
{
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            return;
        }
        if (keyCode == 28 || keyCode == 156) {
            final String s = this.inputField.getText().trim();
            if (!s.isEmpty()) {
                this.sendChatMessage(s);
            }
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (!this.inputField.getText().startsWith(Commands.getPrefix())) {
            this.inputField.setText(Commands.getPrefix() + this.inputField.getText());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void sendChatMessage(final String msg, final boolean addToChat) {
        this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
        this.setText(Commands.getPrefix());
        Managers.COMMANDS.applyCommand(msg);
    }
    
    public void setText(final String text) {
        this.inputField.setText(text);
    }
}
