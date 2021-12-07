//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.tab;

import me.earth.earthhack.impl.util.helpers.gui.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

public class TabModule extends GuiModule
{
    protected final Setting<Boolean> silent;
    protected final Setting<Boolean> pause;
    protected boolean isSilent;
    
    public TabModule() {
        super("Tab", Category.Client);
        this.silent = this.register(new BooleanSetting("Silent", true));
        this.pause = this.register(new BooleanSetting("Pause", true));
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                if (Globals.mc.currentScreen == null && TabModule.this.isSilent) {
                    Mouse.setGrabbed(false);
                }
            }
        });
    }
    
    @Override
    protected void onEnable() {
        if (!(this.isSilent = this.silent.getValue())) {
            super.onEnable();
        }
    }
    
    @Override
    protected void onDisable() {
        if (!this.isSilent) {
            super.onDisable();
        }
    }
    
    @Override
    protected void onOtherGuiDisplayed() {
        if (!this.isSilent) {
            super.onOtherGuiDisplayed();
        }
    }
    
    @Override
    protected GuiScreen provideScreen() {
        return new GuiScreenTab(this);
    }
}
