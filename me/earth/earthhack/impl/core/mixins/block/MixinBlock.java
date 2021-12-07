//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import me.earth.earthhack.impl.core.ducks.block.*;
import net.minecraft.block.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import me.earth.earthhack.impl.modules.movement.jesus.*;
import me.earth.earthhack.impl.modules.movement.phase.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.material.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.event.events.misc.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ Block.class })
public abstract class MixinBlock implements IBlock
{
    private static final ModuleCache<XRay> XRAY;
    private static final ModuleCache<Jesus> JESUS;
    private static final ModuleCache<Phase> PHASE;
    private static final Minecraft MC;
    private final String[] harvestToolNonForge;
    private final int[] harvestLevelNonForge;
    @Shadow
    @Final
    protected Material blockMaterial;
    
    public MixinBlock() {
        this.harvestToolNonForge = new String[16];
        this.harvestLevelNonForge = new int[16];
    }
    
    @Shadow
    protected static void addCollisionBoxToList(final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> cBoxes, final AxisAlignedBB blockBox) {
        throw new IllegalStateException("MixinBlock.addCollisionBoxToList has not been shadowed");
    }
    
    @Shadow
    public abstract BlockStateContainer getBlockState();
    
    @Shadow
    public abstract int getMetaFromState(final IBlockState p0);
    
    @Unique
    @Override
    public void setHarvestLevelNonForge(final String toolClass, final int level) {
        for (final IBlockState state : this.getBlockState().getValidStates()) {
            final int idx = this.getMetaFromState(state);
            this.harvestToolNonForge[idx] = toolClass;
            this.harvestLevelNonForge[idx] = level;
        }
    }
    
    @Unique
    @Override
    public String getHarvestToolNonForge(final IBlockState state) {
        return this.harvestToolNonForge[this.getMetaFromState(state)];
    }
    
    @Unique
    @Override
    public int getHarvestLevelNonForge(final IBlockState state) {
        return this.harvestLevelNonForge[this.getMetaFromState(state)];
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/block/material/Material;Lnet/minecraft/block/material/MapColor;)V" }, at = { @At("RETURN") })
    private void ctrHook(final Material blockMaterialIn, final MapColor blockMapColorIn, final CallbackInfo ci) {
        Arrays.fill(this.harvestLevelNonForge, -1);
    }
    
    @Deprecated
    @Inject(method = { "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V" }, at = { @At("HEAD") }, cancellable = true)
    private void addCollisionBoxToListHook_Pre(final IBlockState state, final World world, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> cBoxes, final Entity entity, final boolean isActualState, final CallbackInfo info) {
        if (!MixinBlock.JESUS.isEnabled() && !MixinBlock.PHASE.isEnabled()) {
            return;
        }
        final Block block = Block.class.cast(this);
        AxisAlignedBB bb = block.getCollisionBoundingBox(state, (IBlockAccess)world, pos);
        final CollisionEvent event = new CollisionEvent(pos, bb, entity, block);
        MixinBlock.JESUS.get().onCollision(event);
        MixinBlock.PHASE.get().onCollision(event);
        if (bb != event.getBB()) {
            bb = event.getBB();
        }
        if (bb != null && entityBox.intersectsWith(bb)) {
            cBoxes.add(bb);
        }
        addCollisionBoxToList(pos, entityBox, cBoxes, bb);
        info.cancel();
    }
    
    @Inject(method = { "addCollisionBoxToList(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/util/math/AxisAlignedBB;)V" }, at = { @At("HEAD") }, cancellable = true)
    private static void addCollisionBoxToListHook(final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> cBoxes, final AxisAlignedBB blockBox, final CallbackInfo info) {
        if (blockBox != Block.NULL_AABB && (MixinBlock.JESUS.isEnabled() || MixinBlock.PHASE.isEnabled())) {
            AxisAlignedBB bb = blockBox.offset(pos);
            final CollisionEvent event = new CollisionEvent(pos, bb, null, (MixinBlock.MC.world != null) ? MixinBlock.MC.world.getBlockState(pos).getBlock() : null);
            MixinBlock.JESUS.get().onCollision(event);
            MixinBlock.PHASE.get().onCollision(event);
            if (bb != event.getBB()) {
                bb = event.getBB();
            }
            if (bb != null && entityBox.intersectsWith(bb)) {
                cBoxes.add(bb);
            }
            info.cancel();
        }
    }
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void isFullCubeHook(final IBlockState blockState, final CallbackInfoReturnable<Boolean> info) {
        if (MixinBlock.XRAY.isEnabled() && MixinBlock.XRAY.get().getMode() == XrayMode.Simple) {
            info.setReturnValue(MixinBlock.XRAY.get().shouldRender(Block.class.cast(this)));
        }
    }
    
    @Inject(method = { "getAmbientOcclusionLightValue" }, at = { @At("HEAD") }, cancellable = true)
    private void ambientValueHook(final CallbackInfoReturnable<Float> info) {
        if (MixinBlock.XRAY.isEnabled() && MixinBlock.XRAY.get().getMode() == XrayMode.Opacity) {
            info.setReturnValue(1.0f);
        }
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
        JESUS = Caches.getModule(Jesus.class);
        PHASE = Caches.getModule(Phase.class);
        MC = Minecraft.getMinecraft();
    }
}
