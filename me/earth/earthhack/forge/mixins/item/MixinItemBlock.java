//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.item;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.noglitchblocks.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ItemBlock.class })
public abstract class MixinItemBlock
{
    private static final ModuleCache<NoGlitchBlocks> NO_GLITCH_BLOCKS;
    
    @Shadow(remap = false)
    @Dynamic
    public abstract boolean placeBlockAt(final ItemStack p0, final EntityPlayer p1, final World p2, final BlockPos p3, final EnumFacing p4, final float p5, final float p6, final float p7, final IBlockState p8);
    
    @Redirect(method = { "onItemUse" }, at = @At(value = "INVOKE", target = "net/minecraft/item/ItemBlock.placeBlockAt(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;FFFLnet/minecraft/block/state/IBlockState;)Z", remap = false))
    @Dynamic
    private boolean onItemUseHook(final ItemBlock block, final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final IBlockState state) {
        return (world.isRemote && MixinItemBlock.NO_GLITCH_BLOCKS.isPresent() && MixinItemBlock.NO_GLITCH_BLOCKS.get().noPlace()) || this.placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, state);
    }
    
    static {
        NO_GLITCH_BLOCKS = Caches.getModule(NoGlitchBlocks.class);
    }
}
