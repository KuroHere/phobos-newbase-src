//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautototem;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import net.minecraft.inventory.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerSetSlot extends ModuleListener<ServerAutoTotem, PacketEvent.Receive<SPacketSetSlot>>
{
    private static final ModuleCache<Offhand> OFFHAND;
    
    public ListenerSetSlot(final ServerAutoTotem module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSetSlot.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSetSlot> event) {
        if (((ServerAutoTotem)this.module).getParent().isEnabled() && ListenerSetSlot.mc.player != null) {
            final SPacketSetSlot packet = event.getPacket();
            if (packet.getSlot() == -1337) {
                ((IContainer)ListenerSetSlot.mc.player.openContainer).setTransactionID((short)packet.getWindowId());
                ((ISPacketSetSlot)packet).setWindowId(-1);
            }
            else if (packet.getWindowId() == -128) {
                event.setCancelled(true);
                ListenerSetSlot.mc.addScheduledTask(() -> {
                    final OffhandMode recovery = ListenerSetSlot.OFFHAND.returnIfPresent(Offhand::getMode, null);
                    ListenerSetSlot.OFFHAND.computeIfPresent(offhand -> {
                        offhand.setMode(OffhandMode.TOTEM);
                        offhand.postWindowClick();
                        return;
                    });
                    ListenerSetSlot.OFFHAND.computeIfPresent(offhand -> offhand.setRecovery(recovery));
                    final Slot slot = ListenerSetSlot.mc.player.inventoryContainer.inventorySlots.get(packet.getSlot());
                    slot.putStack(packet.getStack());
                });
            }
        }
    }
    
    static {
        OFFHAND = Caches.getModule(Offhand.class);
    }
}
