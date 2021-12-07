//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ItemBlock.class })
public abstract class MixinItemBlock
{
    private static final ModuleCache<Freecam> FREECAM;
    
    @Redirect(method = { "onItemUse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;mayPlace(Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/util/EnumFacing;Lnet/minecraft/entity/Entity;)Z"))
    private boolean mayPlaceHook1(final World world, final Block blockIn, final BlockPos pos, final boolean skip, final EnumFacing sidePlacedOn, final Entity placer) {
        if (MixinItemBlock.FREECAM.isEnabled()) {
            return this.mayPlace(world, blockIn, pos, skip, sidePlacedOn, placer);
        }
        return world.func_190527_a(blockIn, pos, skip, sidePlacedOn, placer);
    }
    
    @Redirect(method = { "canPlaceBlockOnSide" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;mayPlace(Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/util/EnumFacing;Lnet/minecraft/entity/Entity;)Z"))
    private boolean mayPlaceHook2(final World world, final Block blockIn, final BlockPos pos, final boolean skip, final EnumFacing sidePlacedOn, final Entity placer) {
        if (MixinItemBlock.FREECAM.isEnabled()) {
            return this.mayPlace(world, blockIn, pos, skip, sidePlacedOn, placer);
        }
        return world.func_190527_a(blockIn, pos, skip, sidePlacedOn, placer);
    }
    
    private boolean mayPlace(final World world, final Block blockIn, final BlockPos pos, final boolean skip, final EnumFacing sidePlacedOn, final Entity placer) {
        final IBlockState state = world.getBlockState(pos);
        final AxisAlignedBB bb = skip ? null : blockIn.getDefaultState().getCollisionBoundingBox((IBlockAccess)world, pos);
        return (bb == Block.NULL_AABB || bb == null || this.checkCollision(world, bb.offset(pos), placer)) && ((state.getMaterial() == Material.CIRCUITS && blockIn == Blocks.ANVIL) || (state.getBlock().isReplaceable((IBlockAccess)world, pos) && blockIn.canPlaceBlockOnSide(world, pos, sidePlacedOn)));
    }
    
    private boolean checkCollision(final World world, final AxisAlignedBB bb, final Entity entityIn) {
        for (final Entity entity : world.getEntitiesWithinAABBExcludingEntity((Entity)null, bb)) {
            if (entity != null && !entity.isDead && entity.preventEntitySpawning && !entity.equals((Object)entityIn) && !entity.equals((Object)Minecraft.getMinecraft().player) && (entityIn == null || !entity.isRidingSameEntity(entityIn))) {
                return false;
            }
        }
        return true;
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
}
