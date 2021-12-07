//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.sorter.*;
import me.earth.earthhack.impl.util.thread.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ InventoryPlayer.class })
public abstract class MixinInventoryPlayer
{
    private static final ModuleCache<Sorter> SORTER;
    
    @Redirect(method = { "setPickedItemStack" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = 181))
    private void setPickedItemStackHook(final InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.currentItem = value);
    }
    
    @Redirect(method = { "pickItem" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = 181))
    private void pickItemHook(final InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.currentItem = value);
    }
    
    @Inject(method = { "changeCurrentItem" }, at = { @At("HEAD") }, cancellable = true)
    private void changeCurrentItemHook(final int direction, final CallbackInfo ci) {
        if (MixinInventoryPlayer.SORTER.returnIfPresent(s -> s.scroll(direction), Boolean.valueOf(false))) {
            ci.cancel();
        }
    }
    
    static {
        SORTER = Caches.getModule(Sorter.class);
    }
}
