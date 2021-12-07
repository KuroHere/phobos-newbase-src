// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockRendererDispatcher.class })
public abstract class MixinBlockRendererDispatcher
{
    @Inject(method = { "renderBlock" }, at = { @At("HEAD") })
    private void renderBlockHook(final IBlockState state, final BlockPos pos, final IBlockAccess blockAccess, final BufferBuilder bufferBuilderIn, final CallbackInfoReturnable<Boolean> info) {
        Bus.EVENT_BUS.post(new BlockRenderEvent(pos, state));
    }
}
