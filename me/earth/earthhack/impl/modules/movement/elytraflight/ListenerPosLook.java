//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.elytraflight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;

final class ListenerPosLook extends ModuleListener<ElytraFlight, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final ElytraFlight module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (((ElytraFlight)this.module).mode.getValue() == ElytraMode.Packet && ListenerPosLook.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            ((ElytraFlight)this.module).lag = true;
        }
    }
}
