// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.block;

import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Block.class })
public abstract class MixinBlock
{
    @Inject(method = { "canRenderInLayer" }, at = { @At("RETURN") }, cancellable = true, remap = false)
    @Dynamic
    private void canRenderInLayerHook(final IBlockState state, final BlockRenderLayer layer, final CallbackInfoReturnable<Boolean> info) {
        final Block block = Block.class.cast(this);
        final BlockLayerEvent event = new BlockLayerEvent(block);
        Bus.EVENT_BUS.post(event);
        if (event.getLayer() != null) {
            info.setReturnValue(event.getLayer() == layer);
        }
    }
}
