//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoeat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.core.mixins.item.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.client.settings.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.item.*;

final class ListenerTick extends ModuleListener<AutoEat, TickEvent>
{
    public ListenerTick(final AutoEat module) {
        super(module, (Class<? super Object>)TickEvent.class, 11);
    }
    
    public void invoke(final TickEvent event) {
        if (!event.isSafe()) {
            ((AutoEat)this.module).lastSlot = -1;
            ((AutoEat)this.module).isEating = false;
            ((AutoEat)this.module).force = false;
            ((AutoEat)this.module).server = false;
            return;
        }
        ((AutoEat)this.module).force = (((AutoEat)this.module).always.getValue() || ((AutoEat)this.module).server);
        if (ListenerTick.mc.player.getFoodStats().getFoodLevel() > ((AutoEat)this.module).hunger.getValue() && (!((AutoEat)this.module).health.getValue() || !this.enemyCheck() || (Managers.SAFETY.isSafe() && EntityUtil.getHealth((EntityLivingBase)ListenerTick.mc.player, ((AutoEat)this.module).calcWithAbsorption.getValue()) > ((AutoEat)this.module).safeHealth.getValue()) || (!Managers.SAFETY.isSafe() && EntityUtil.getHealth((EntityLivingBase)ListenerTick.mc.player, ((AutoEat)this.module).calcWithAbsorption.getValue()) > ((AutoEat)this.module).unsafeHealth.getValue())) && (!((AutoEat)this.module).absorption.getValue() || ListenerTick.mc.player.getAbsorptionAmount() > ((AutoEat)this.module).absorptionAmount.getValue()) && !((AutoEat)this.module).force) {
            if (((AutoEat)this.module).isEating) {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(((AutoEat)this.module).lastSlot));
                ((AutoEat)this.module).reset();
            }
            return;
        }
        final int slot = InventoryUtil.findInHotbar(s -> s.getItem() instanceof ItemFood && !this.hasBadEffect((IITemFood)s.getItem()) && (!(s.getItem() instanceof ItemFishFood) || ItemFishFood.FishType.byItemStack(s) != ItemFishFood.FishType.PUFFERFISH));
        if (slot == -1) {
            ModuleUtil.sendMessage((Module)this.module, "§cNo food found in your hotbar!");
            return;
        }
        if (((AutoEat)this.module).lastSlot == -1) {
            ((AutoEat)this.module).lastSlot = ListenerTick.mc.player.inventory.currentItem;
        }
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(slot));
        KeyBinding.setKeyBindState(ListenerTick.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        ((AutoEat)this.module).isEating = true;
    }
    
    private boolean enemyCheck() {
        if (((AutoEat)this.module).enemyRange.getValue() != 0.0f) {
            final EntityPlayer entity = EntityUtil.getClosestEnemy();
            return entity != null && entity.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer()) < MathUtil.square(((AutoEat)this.module).enemyRange.getValue());
        }
        return false;
    }
    
    private boolean hasBadEffect(final IITemFood itemFood) {
        final PotionEffect effect = itemFood.getPotionId();
        if (effect != null) {
            for (final Potion p : Potion.REGISTRY) {
                if (p.isBadEffect() && p.equals(effect.getPotion())) {
                    return true;
                }
            }
        }
        return false;
    }
}
