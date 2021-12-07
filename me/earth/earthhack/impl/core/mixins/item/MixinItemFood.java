// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.misc.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemFood.class })
public abstract class MixinItemFood
{
    @Inject(method = { "onItemUseFinish" }, at = { @At("HEAD") })
    private void onItemUseFinishHook(final ItemStack stack, final World world, final EntityLivingBase entity, final CallbackInfoReturnable<ItemStack> info) {
        if (entity instanceof EntityPlayer) {
            Bus.EVENT_BUS.post(new EatEvent(stack, entity));
        }
    }
}
