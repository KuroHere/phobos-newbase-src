//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.suicide.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.entity.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTotem extends ModuleListener<Offhand, PacketEvent.Receive<SPacketEntityStatus>>
{
    private static final ModuleCache<Suicide> SUICIDE;
    
    public ListenerTotem(final Offhand module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityStatus.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityStatus> event) {
        final EntityPlayerSP player = ListenerTotem.mc.player;
        if (player == null || !InventoryUtil.validScreen() || !((Offhand)this.module).async.getValue() || event.getPacket().getOpCode() != 35 || !((Offhand)this.module).timer.passed(((Offhand)this.module).delay.getValue()) || player.getEntityId() != event.getPacket().getEntityId() || ListenerTotem.SUICIDE.returnIfPresent(Suicide::deactivateOffhand, false)) {
            return;
        }
        int currentItem;
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            currentItem = ListenerTotem.mc.player.inventory.currentItem;
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
        int slot = InventoryUtil.hotbarToInventory(currentItem);
        ItemStack stack = ListenerTotem.mc.player.inventory.getStackInSlot(currentItem);
        if (stack.getItem() != Items.field_190929_cY) {
            slot = 45;
            stack = ListenerTotem.mc.player.getHeldItemOffhand();
            if (stack.getItem() != Items.field_190929_cY) {
                return;
            }
        }
        if (stack.func_190916_E() - 1 > 0) {
            return;
        }
        final Set<Integer> ignore = new HashSet<Integer>();
        ignore.add(slot);
        final int t = InventoryUtil.findItem(Items.field_190929_cY, true, ignore);
        if (t == -1) {
            return;
        }
        final int finalSlot = slot;
        Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
            if (InventoryUtil.get(t).getItem() == Items.field_190929_cY) {
                InventoryUtil.put(finalSlot, ItemStack.field_190927_a);
                if (t != -2) {
                    Managers.NCP.startMultiClick();
                    InventoryUtil.click(t);
                }
                InventoryUtil.click(finalSlot);
                if (t != 2) {
                    Managers.NCP.releaseMultiClick();
                }
            }
            return;
        });
        ((Offhand)this.module).asyncSlot = slot;
        ((Offhand)this.module).asyncTimer.reset();
        ((Offhand)this.module).postWindowClick();
    }
    
    static {
        SUICIDE = Caches.getModule(Suicide.class);
    }
}
