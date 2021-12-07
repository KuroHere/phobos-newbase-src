//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.liquids.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BlockLiquid.class })
public abstract class MixinBlockLiquid extends MixinBlock
{
    private static final ModuleCache<LiquidInteract> LIQUID_INTERACT;
    private static final ModuleCache<XRay> XRAY;
    
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideCheckHook(final IBlockState blockState, final boolean hitIfLiquid, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlockLiquid.LIQUID_INTERACT.isEnabled()) {
            info.setReturnValue(true);
        }
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    private void shouldSideBeRenderedHook(final IBlockState state, final IBlockAccess access, final BlockPos pos, final EnumFacing facing, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlockLiquid.XRAY.isEnabled() && MixinBlockLiquid.XRAY.get().getMode() == XrayMode.Opacity) {
            info.setReturnValue(access.getBlockState(pos.offset(facing)).getMaterial() != this.blockMaterial);
        }
    }
    
    static {
        LIQUID_INTERACT = Caches.getModule(LiquidInteract.class);
        XRAY = Caches.getModule(XRay.class);
    }
}
