//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.clickgui;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.gui.click.*;

final class ListenerScreen extends ModuleListener<ClickGui, GuiScreenEvent<?>>
{
    public ListenerScreen(final ClickGui module) {
        super(module, (Class<? super Object>)GuiScreenEvent.class);
    }
    
    public void invoke(final GuiScreenEvent<?> event) {
        if (ListenerScreen.mc.currentScreen instanceof Click) {
            ((ClickGui)this.module).fromEvent = true;
            ((ClickGui)this.module).disable();
        }
    }
}
