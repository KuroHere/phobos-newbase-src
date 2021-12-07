//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoreconnect.util;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.core.mixins.gui.util.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.client.pingbypass.guis.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.*;

public class ReconnectScreen extends GuiDisconnected
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private final StopWatch timer;
    private final IGuiDisconnected parent;
    private final ServerData data;
    private final int delay;
    private GuiButton reconnectButton;
    private boolean noData;
    private boolean reconnect;
    private long time;
    
    public ReconnectScreen(final IGuiDisconnected parent, final ServerData serverData, final int delay) {
        super(parent.getParentScreen(), parent.getReason(), parent.getMessage());
        this.timer = new StopWatch();
        this.parent = parent;
        this.data = serverData;
        this.delay = delay;
        this.reconnect = true;
        this.time = System.currentTimeMillis();
        this.mc = Minecraft.getMinecraft();
        this.timer.reset();
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        final int textHeight = ((IGuiDisconnected)this).getMultilineMessage().size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, Math.min(this.height / 2 + textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, this.height - 30), ((this.data == null) ? "§c" : "§f") + "Reconnect"));
        this.reconnectButton = new GuiButton(2, this.width / 2 - 100, Math.min(this.height / 2 + textHeight / 2 + this.mc.fontRendererObj.FONT_HEIGHT, this.height - 30) + 23, this.getButtonString());
        this.buttonList.add(this.reconnectButton);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + textHeight / 2 + this.mc.fontRendererObj.FONT_HEIGHT, this.height - 30) + 46, I18n.format("gui.toMenu", new Object[0])));
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 1) {
            this.connect();
        }
        else if (button.id == 2) {
            this.reconnect = !this.reconnect;
            this.time = this.timer.getTime();
            this.reconnectButton.displayString = this.getButtonString();
        }
    }
    
    public void updateScreen() {
        if (!this.reconnect) {
            this.timer.setTime(System.currentTimeMillis() - this.time);
        }
        if (this.noData) {
            if (this.timer.passed(3000L)) {
                this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
            }
        }
        else if (this.timer.passed(this.delay) && this.reconnect) {
            this.connect();
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final String text = this.getReconnectString();
        Managers.TEXT.drawStringWithShadow(text, this.width / 2.0f - Managers.TEXT.getStringWidth(text) / 2.0f, 16.0f, -1);
    }
    
    private void connect() {
        final ServerData serverData = (this.data == null) ? this.mc.getCurrentServerData() : this.data;
        if (serverData != null) {
            if (ReconnectScreen.PINGBYPASS.isEnabled()) {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnectingPingBypass(this.parent.getParentScreen(), this.mc, serverData));
            }
            else {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this.parent.getParentScreen(), this.mc, serverData));
            }
        }
        else {
            this.noData = true;
            this.timer.reset();
        }
    }
    
    private String getButtonString() {
        return "AutoReconnect: " + (this.reconnect ? "§aOn" : "§cOff");
    }
    
    private String getReconnectString() {
        final float time = MathUtil.round((this.delay - (this.reconnect ? this.timer.getTime() : this.time)) / 1000.0f, 1);
        return this.noData ? "§cNo ServerData found!" : ("Reconnecting in " + ((time <= 0.0f) ? "0.0" : Float.valueOf(time)) + "s.");
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
