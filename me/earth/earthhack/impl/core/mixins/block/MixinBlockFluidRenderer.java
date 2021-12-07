//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BlockFluidRenderer.class })
public abstract class MixinBlockFluidRenderer
{
    private static final ModuleCache<XRay> XRAY;
    
    @Inject(method = { "renderFluid" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFluidHook(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BufferBuilder bufferBuilder, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlockFluidRenderer.XRAY.isEnabled() && MixinBlockFluidRenderer.XRAY.get().getMode() == XrayMode.Simple && !MixinBlockFluidRenderer.XRAY.get().shouldRender(blockState.getBlock())) {
            info.setReturnValue(false);
        }
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
