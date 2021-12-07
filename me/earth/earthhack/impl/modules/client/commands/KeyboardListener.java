//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.commands;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.client.gui.*;

final class KeyboardListener extends ModuleListener<Commands, KeyboardEvent>
{
    public KeyboardListener(final Commands module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (((Commands)this.module).prefixBind.getValue() && event.getEventState() && event.getCharacter() == ((Commands)this.module).prefixChar) {
            Scheduler.getInstance().schedule(() -> KeyboardListener.mc.displayGuiScreen((GuiScreen)new GuiChat(Commands.getPrefix())));
        }
    }
}
