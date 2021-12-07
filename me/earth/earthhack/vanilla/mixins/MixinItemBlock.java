//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla.mixins;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.noglitchblocks.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ItemBlock.class })
public abstract class MixinItemBlock
{
    @Shadow
    @Final
    protected Block block;
    private static final ModuleCache<NoGlitchBlocks> NO_GLITCH_BLOCKS;
    
    @Inject(method = { "onItemUse" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z") }, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void setBlockStateHook(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final CallbackInfoReturnable<EnumActionResult> cir, final ItemStack itemStack_1) {
        if (worldIn.isRemote && MixinItemBlock.NO_GLITCH_BLOCKS.returnIfPresent(NoGlitchBlocks::noPlace, false)) {
            final SoundType soundtype = this.block.getSoundType();
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
            itemStack_1.func_190918_g(1);
            cir.setReturnValue(EnumActionResult.SUCCESS);
        }
    }
    
    static {
        NO_GLITCH_BLOCKS = Caches.getModule(NoGlitchBlocks.class);
    }
}
