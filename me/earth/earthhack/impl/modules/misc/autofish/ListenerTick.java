//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autofish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.autoeat.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.projectile.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTick extends ModuleListener<AutoFish, TickEvent>
{
    private static final ModuleCache<AutoEat> AUTOEAT;
    
    public ListenerTick(final AutoFish module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (!event.isSafe() || ListenerTick.AUTOEAT.returnIfPresent(AutoEat::isEating, false)) {
            return;
        }
        final int slot = InventoryUtil.findHotbarItem((Item)Items.FISHING_ROD, new Item[0]);
        if (slot == -1) {
            ModuleUtil.disableRed((Module)this.module, "No fishing rod found in your hotbar.");
        }
        if (ListenerTick.mc.player.inventory.currentItem != slot) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(slot));
        }
        else if (((AutoFish)this.module).delayCounter > 0) {
            final AutoFish autoFish = (AutoFish)this.module;
            --autoFish.delayCounter;
        }
        else {
            final EntityFishHook fish = ListenerTick.mc.player.fishEntity;
            if (fish == null) {
                ((AutoFish)this.module).click();
                return;
            }
            if (++((AutoFish)this.module).timeout > 720) {
                ((AutoFish)this.module).click();
            }
            if (ListenerTick.mc.player.fishEntity.caughtEntity != null) {
                ((AutoFish)this.module).click();
            }
            if (((AutoFish)this.module).splash) {
                if (++((AutoFish)this.module).splashTicks >= 4) {
                    ((AutoFish)this.module).click();
                    ((AutoFish)this.module).splash = false;
                }
            }
            else if (((AutoFish)this.module).splashTicks != 0) {
                final AutoFish autoFish2 = (AutoFish)this.module;
                --autoFish2.splashTicks;
            }
        }
    }
    
    static {
        AUTOEAT = Caches.getModule(AutoEat.class);
    }
}
