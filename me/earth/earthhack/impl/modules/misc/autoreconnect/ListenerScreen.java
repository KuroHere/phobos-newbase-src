// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoreconnect;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.gui.*;

final class ListenerScreen extends ModuleListener<AutoReconnect, GuiScreenEvent<GuiDisconnected>>
{
    public ListenerScreen(final AutoReconnect module) {
        super(module, (Class<? super Object>)GuiScreenEvent.class, -1000, GuiDisconnected.class);
    }
    
    public void invoke(final GuiScreenEvent<GuiDisconnected> event) {
        if (!event.isCancelled()) {
            ((AutoReconnect)this.module).onGuiDisconnected(event.getScreen());
            event.setCancelled(true);
        }
    }
}
