// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderGlobal.class })
public abstract class MixinRenderGlobal
{
    @Inject(method = { "renderEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;release()V", shift = At.Shift.BEFORE) })
    private void renderEntitiesHook(final Entity renderViewEntity, final ICamera camera, final float partialTicks, final CallbackInfo ci) {
        Bus.EVENT_BUS.post(new PostRenderEntitiesEvent(partialTicks, 0));
    }
}
