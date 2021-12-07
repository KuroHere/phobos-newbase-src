// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerDestroyEntities extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerDestroyEntities(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        if (((AutoCrystal)this.module).destroyThread.getValue()) {
            ((AutoCrystal)this.module).threadHelper.schedulePacket(event);
        }
    }
}
