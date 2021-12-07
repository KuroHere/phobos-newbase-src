//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import net.minecraft.network.play.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;

final class ListenerSetSlot extends ModuleListener<AutoArmor, PacketEvent.Receive<SPacketSetSlot>>
{
    public ListenerSetSlot(final AutoArmor module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSetSlot.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSetSlot> event) {
        final SPacketSetSlot packet = event.getPacket();
        if (!((AutoArmor)this.module).noDuraDesync.getValue() || event.isCancelled() || packet.getWindowId() != 0 || packet.getSlot() < 5 || packet.getSlot() > 8) {
            return;
        }
        event.setCancelled(true);
        ListenerSetSlot.mc.addScheduledTask(() -> {
            if (ListenerSetSlot.mc.player != null) {
                final DesyncClick click = ((AutoArmor)this.module).desyncMap.get(packet.getSlot());
                if (click == null || System.currentTimeMillis() - click.getTimeStamp() > ((AutoArmor)this.module).removeTime.getValue()) {
                    packet.processPacket((INetHandlerPlayClient)ListenerSetSlot.mc.player.connection);
                }
                else {
                    final ItemStack stack = InventoryUtil.get(packet.getSlot());
                    if (InventoryUtil.equals(stack, packet.getStack())) {
                        packet.processPacket((INetHandlerPlayClient)ListenerSetSlot.mc.player.connection);
                    }
                    else {
                        final ItemStack drag = ListenerSetSlot.mc.player.inventory.getItemStack();
                        if (InventoryUtil.equals(drag, packet.getStack())) {
                            ListenerSetSlot.mc.player.inventory.setItemStack(packet.getStack());
                        }
                        else {
                            final int slot = click.getClick().getTarget();
                            if (slot > 0 && slot < 45) {
                                final ItemStack stack2 = InventoryUtil.get(slot);
                                if (InventoryUtil.equals(stack2, packet.getStack())) {
                                    InventoryUtil.put(slot, packet.getStack());
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
