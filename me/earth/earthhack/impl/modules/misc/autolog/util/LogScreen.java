//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autolog.util;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.misc.autolog.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.modules.client.pingbypass.guis.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import java.io.*;
import me.earth.earthhack.impl.modules.*;

public class LogScreen extends GuiScreen
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private final AutoLog autoLog;
    private final ServerData data;
    private final String message;
    private GuiButton autoLogButton;
    private final int textHeight;
    
    public LogScreen(final AutoLog autoLog, final String message, final ServerData data) {
        this.autoLog = autoLog;
        this.mc = Minecraft.getMinecraft();
        this.message = message;
        this.data = data;
        this.textHeight = this.mc.fontRendererObj.FONT_HEIGHT;
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.mc.fontRendererObj.FONT_HEIGHT, this.height - 30), ((this.data == null) ? "§c" : "§f") + "Reconnect"));
        this.autoLogButton = new GuiButton(1, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.mc.fontRendererObj.FONT_HEIGHT, this.height - 30) + 23, this.getButtonString());
        this.buttonList.add(this.autoLogButton);
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.mc.fontRendererObj.FONT_HEIGHT, this.height - 30) + 46, "Back to server list"));
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0 && this.data != null) {
            if (LogScreen.PINGBYPASS.isEnabled()) {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnectingPingBypass((GuiScreen)new GuiMainMenu(), this.mc, this.data));
            }
            else {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this.mc, this.data));
            }
        }
        else if (button.id == 1) {
            this.autoLog.toggle();
            this.autoLogButton.displayString = this.getButtonString();
        }
        else if (button.id == 2) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.message, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private String getButtonString() {
        return "AutoLog: " + (this.autoLog.isEnabled() ? "§aOn" : "§cOff");
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
