//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.killaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.item.*;

final class ListenerEntityEquipment extends ModuleListener<KillAura, PacketEvent.Receive<SPacketEntityEquipment>>
{
    public ListenerEntityEquipment(final KillAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityEquipment.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityEquipment> event) {
        final SPacketEntityEquipment packet = event.getPacket();
        if (packet.getEquipmentSlot().getIndex() == 1 && ((KillAura)this.module).cancelEntityEquip.getValue() && packet.getItemStack().getItem() instanceof ItemAir && ListenerEntityEquipment.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
            event.setCancelled(true);
        }
    }
}
