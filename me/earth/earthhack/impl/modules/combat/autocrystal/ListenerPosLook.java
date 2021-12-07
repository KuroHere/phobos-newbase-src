// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerPosLook extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        ((AutoCrystal)this.module).rotationCanceller.drop();
    }
}
