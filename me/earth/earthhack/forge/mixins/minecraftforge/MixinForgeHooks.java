//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.minecraftforge;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.thread.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ForgeHooks.class })
public abstract class MixinForgeHooks
{
    @Redirect(method = { "onPickBlock" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", ordinal = 1))
    private static void onPickBlockHook(final InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.currentItem = value);
    }
}
