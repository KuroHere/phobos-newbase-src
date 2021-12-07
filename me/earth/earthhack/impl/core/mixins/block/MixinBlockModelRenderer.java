//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BlockModelRenderer.class })
public abstract class MixinBlockModelRenderer
{
    private static final ModuleCache<XRay> XRAY;
    
    @Inject(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Z)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void renderModelHook(final IBlockAccess blockAccess, final IBakedModel bakedModel, final IBlockState blockState, final BlockPos blockPos, final BufferBuilder bufferBuilder, final boolean b, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlockModelRenderer.XRAY.isEnabled() && MixinBlockModelRenderer.XRAY.get().getMode() == XrayMode.Simple && !MixinBlockModelRenderer.XRAY.get().shouldRender(blockState.getBlock())) {
            info.setReturnValue(false);
        }
    }
    
    @ModifyArg(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelFlat(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
    private boolean renderModelFlatHook(final boolean result) {
        return (!MixinBlockModelRenderer.XRAY.isEnabled() || MixinBlockModelRenderer.XRAY.get().getMode() != XrayMode.Simple) && result;
    }
    
    @ModifyArg(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelSmooth(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
    private boolean renderModelSmoothHook(final boolean result) {
        return (!MixinBlockModelRenderer.XRAY.isEnabled() || MixinBlockModelRenderer.XRAY.get().getMode() != XrayMode.Simple) && result;
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
