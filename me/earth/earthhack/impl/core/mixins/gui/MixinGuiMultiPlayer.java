//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.client.autoconfig.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.impl.modules.client.pingbypass.guis.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiMultiplayer.class })
public abstract class MixinGuiMultiPlayer extends GuiScreen
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private static final ModuleCache<AutoConfig> CONFIG;
    private GuiButton pingBypassButton;
    
    @Inject(method = { "createButtons" }, at = { @At("HEAD") })
    public void createButtonsHook(final CallbackInfo info) {
        this.buttonList.add(new GuiButtonPingBypassOptions(1339, this.width - 24, 5));
        this.pingBypassButton = this.addButton(new GuiButton(1336, this.width - 126, 5, 100, 20, this.getDisplayString()));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    protected void actionPerformed(final GuiButton button, final CallbackInfo info) {
        if (button.enabled) {
            if (button.id == this.pingBypassButton.id) {
                MixinGuiMultiPlayer.PINGBYPASS.toggle();
                this.pingBypassButton.displayString = this.getDisplayString();
                info.cancel();
            }
            else if (button.id == 1339) {
                this.mc.displayGuiScreen((GuiScreen)new GuiAddPingBypass(this));
                info.cancel();
            }
        }
    }
    
    @Inject(method = { "confirmClicked" }, at = { @At("HEAD") }, cancellable = true)
    public void confirmClickedHook(final boolean result, final int id, final CallbackInfo info) {
        if (id == this.pingBypassButton.id) {
            this.mc.displayGuiScreen((GuiScreen)this);
            info.cancel();
        }
    }
    
    @Inject(method = { "connectToServer" }, at = { @At("HEAD") }, cancellable = true)
    public void connectToServerHook(final ServerData data, final CallbackInfo info) {
        if (MixinGuiMultiPlayer.CONFIG.isEnabled()) {
            MixinGuiMultiPlayer.CONFIG.get().onConnect(data.serverIP);
        }
        if (MixinGuiMultiPlayer.PINGBYPASS.isEnabled()) {
            this.mc.displayGuiScreen((GuiScreen)new GuiConnectingPingBypass(this, this.mc, data));
            info.cancel();
        }
    }
    
    private String getDisplayString() {
        return "PingBypass: " + (MixinGuiMultiPlayer.PINGBYPASS.isEnabled() ? "§aOn" : "§cOff");
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
        CONFIG = Caches.getModule(AutoConfig.class);
    }
}
