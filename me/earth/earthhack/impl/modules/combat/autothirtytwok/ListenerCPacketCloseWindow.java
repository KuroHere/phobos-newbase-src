// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autothirtytwok;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerCPacketCloseWindow extends ModuleListener<Auto32k, PacketEvent.Send<CPacketCloseWindow>>
{
    public ListenerCPacketCloseWindow(final Auto32k module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketCloseWindow.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketCloseWindow> event) {
        ((Auto32k)this.module).onCPacketCloseWindow(event);
    }
}
