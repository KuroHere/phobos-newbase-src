//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autolog;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.misc.autolog.util.*;
import net.minecraft.client.gui.*;

final class ListenerScreen extends ModuleListener<AutoLog, GuiScreenEvent<GuiDisconnected>>
{
    public ListenerScreen(final AutoLog module) {
        super(module, (Class<? super Object>)GuiScreenEvent.class, GuiDisconnected.class);
    }
    
    public void invoke(final GuiScreenEvent<GuiDisconnected> event) {
        if (((AutoLog)this.module).awaitScreen) {
            ((AutoLog)this.module).awaitScreen = false;
            ListenerScreen.mc.displayGuiScreen((GuiScreen)new LogScreen((AutoLog)this.module, ((AutoLog)this.module).message, ((AutoLog)this.module).serverData));
            event.setCancelled(true);
        }
    }
}
