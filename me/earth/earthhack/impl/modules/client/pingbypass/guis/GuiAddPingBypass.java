//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.guis;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.io.*;
import me.earth.earthhack.impl.modules.*;

public class GuiAddPingBypass extends GuiScreen
{
    private static final SettingCache<String, StringSetting, PingBypass> IP;
    private static final SettingCache<String, StringSetting, PingBypass> PORT;
    private final GuiScreen parentScreen;
    private GuiTextField serverPortField;
    private GuiTextField serverIPField;
    
    public GuiAddPingBypass(final GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }
    
    public void updateScreen() {
        this.serverIPField.updateCursorCounter();
        this.serverPortField.updateCursorCounter();
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Done"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        (this.serverIPField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20)).setFocused(true);
        this.serverIPField.setText((String)GuiAddPingBypass.IP.getValue());
        (this.serverPortField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20)).setMaxStringLength(128);
        this.serverPortField.setText((String)GuiAddPingBypass.PORT.getValue());
        this.buttonList.get(0).enabled = (!this.serverPortField.getText().isEmpty() && this.serverPortField.getText().split(":").length > 0 && !this.serverIPField.getText().isEmpty());
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                this.parentScreen.confirmClicked(false, 1337);
            }
            else if (button.id == 0) {
                GuiAddPingBypass.IP.computeIfPresent(s -> s.setValue(this.serverIPField.getText()));
                GuiAddPingBypass.PORT.computeIfPresent(s -> s.setValue(this.serverPortField.getText()));
                this.parentScreen.confirmClicked(true, 1337);
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.serverIPField.textboxKeyTyped(typedChar, keyCode);
        this.serverPortField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 15) {
            this.serverIPField.setFocused(!this.serverIPField.isFocused());
            this.serverPortField.setFocused(!this.serverPortField.isFocused());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        this.buttonList.get(0).enabled = (!this.serverPortField.getText().isEmpty() && this.serverPortField.getText().split(":").length > 0 && !this.serverIPField.getText().isEmpty());
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.serverPortField.mouseClicked(mouseX, mouseY, mouseButton);
        this.serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Edit PingBypass", this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, "Proxy-IP", this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, "Proxy-Port", this.width / 2 - 100, 94, 10526880);
        this.serverIPField.drawTextBox();
        this.serverPortField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        IP = Caches.getSetting(PingBypass.class, StringSetting.class, "IP", "Proxy-IP");
        PORT = Caches.getSetting(PingBypass.class, StringSetting.class, "Port", "0");
    }
}
