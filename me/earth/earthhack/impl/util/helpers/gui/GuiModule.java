//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.gui;

import me.earth.earthhack.api.module.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.*;

public abstract class GuiModule extends Module
{
    protected GuiScreen screen;
    protected boolean fromEvent;
    
    public GuiModule(final String name, final Category category) {
        super(name, category);
        this.listeners.add(new EventListener<GuiScreenEvent<?>>(GuiScreenEvent.class) {
            @Override
            public void invoke(final GuiScreenEvent<?> event) {
                GuiModule.this.onOtherGuiDisplayed();
            }
        });
    }
    
    @Override
    protected void onEnable() {
        this.screen = GuiModule.mc.currentScreen;
        this.display();
    }
    
    @Override
    protected void onDisable() {
        if (!this.fromEvent) {
            GuiModule.mc.displayGuiScreen(this.screen);
        }
        this.screen = null;
        this.fromEvent = false;
    }
    
    protected void onOtherGuiDisplayed() {
        this.fromEvent = true;
        this.disable();
    }
    
    protected void display() {
        GuiModule.mc.displayGuiScreen(this.provideScreen());
    }
    
    protected abstract GuiScreen provideScreen();
}
