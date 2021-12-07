// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.entity;

import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "net/minecraft/block/Block.removedByPlayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/EntityPlayer;Z)Z", remap = false) }, cancellable = true)
    @Dynamic
    private void onPlayerDestroyBlockHook(final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        final BlockDestroyEvent event = new BlockDestroyEvent(Stage.PRE, pos);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(false);
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onPlayerDestroy(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V", shift = At.Shift.BEFORE) })
    private void onPlayerDestroyHook(final BlockPos pos, final CallbackInfoReturnable<Boolean> cir) {
        Bus.EVENT_BUS.post(new BlockDestroyEvent(Stage.POST, pos));
    }
}
