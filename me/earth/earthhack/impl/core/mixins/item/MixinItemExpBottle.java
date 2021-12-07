// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.exptweaks.*;
import me.earth.earthhack.impl.modules.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemExpBottle.class })
public abstract class MixinItemExpBottle
{
    private final ModuleCache<ExpTweaks> expTweaks;
    
    public MixinItemExpBottle() {
        this.expTweaks = Caches.getModule(ExpTweaks.class);
    }
    
    @Redirect(method = { "onItemRightClick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;shrink(I)V"))
    private void onItemRightClickHook(final ItemStack stack, final int quantity) {
        if (!this.expTweaks.returnIfPresent(ExpTweaks::cancelShrink, false)) {
            stack.func_190918_g(quantity);
        }
    }
}
