//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BlockModelRenderer.class })
public abstract class MixinBlockModelRenderer
{
    private static final ModuleCache<XRay> XRAY;
    
    @Shadow
    public abstract boolean renderModelSmooth(final IBlockAccess p0, final IBakedModel p1, final IBlockState p2, final BlockPos p3, final BufferBuilder p4, final boolean p5, final long p6);
    
    @Inject(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void renderModelHook(final IBlockAccess access, final IBakedModel model, final IBlockState state, final BlockPos pos, final BufferBuilder bufferBuilder, final boolean checkSides, final long rand, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlockModelRenderer.XRAY.isEnabled() && MixinBlockModelRenderer.XRAY.get().getMode() == XrayMode.Opacity) {
            info.setReturnValue(this.renderModelSmooth(access, model, state, pos, bufferBuilder, !MixinBlockModelRenderer.XRAY.get().isValid(state.getBlock().getLocalizedName()), rand));
        }
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
