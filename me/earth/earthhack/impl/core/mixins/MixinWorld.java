//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins;

import me.earth.earthhack.impl.core.ducks.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import me.earth.earthhack.impl.modules.misc.packets.*;
import me.earth.earthhack.impl.modules.player.blocktweaks.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.api.event.bus.instance.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ World.class })
public abstract class MixinWorld implements IWorld
{
    private static final ModuleCache<NoRender> NO_RENDER;
    private static final ModuleCache<Packets> PACKETS;
    private static final ModuleCache<BlockTweaks> BLOCK_TWEAKS;
    
    @Shadow
    public abstract List<Entity> getEntitiesWithinAABBExcludingEntity(@Nullable final Entity p0, final AxisAlignedBB p1);
    
    @Shadow
    public abstract World init();
    
    @Invoker("isChunkLoaded")
    @Override
    public abstract boolean isChunkLoaded(final int p0, final int p1, final boolean p2);
    
    @Redirect(method = { "handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
    public boolean isPushedByWaterHook(final Entity entity) {
        final WaterPushEvent event = new WaterPushEvent(entity);
        Bus.EVENT_BUS.post(event);
        return entity.isPushedByWater() && !event.isCancelled();
    }
    
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void checkLightForHook(final EnumSkyBlock skyBlock, final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        if (MixinWorld.NO_RENDER.returnIfPresent(NoRender::noSkyLight, false) && skyBlock == EnumSkyBlock.SKY) {
            info.setReturnValue(false);
        }
    }
    
    @Inject(method = { "getRainStrength" }, at = { @At("HEAD") }, cancellable = true)
    private void getRainStrengthHook(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (MixinWorld.NO_RENDER.returnIfPresent(NoRender::noWeather, false)) {
            callbackInfoReturnable.setReturnValue(0.0f);
        }
    }
    
    @Inject(method = { "spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V" }, at = { @At("HEAD") }, cancellable = true)
    private void spawnParticleHook(final EnumParticleTypes particleType, final double xCoord, final double yCoord, final double zCoord, final double xSpeed, final double ySpeed, final double zSpeed, final int[] parameters, final CallbackInfo ci) {
        if (MixinWorld.NO_RENDER.isEnabled() && MixinWorld.NO_RENDER.get().explosions.getValue()) {
            switch (particleType) {
                case EXPLOSION_NORMAL:
                case EXPLOSION_LARGE:
                case EXPLOSION_HUGE:
                case FIREWORKS_SPARK: {
                    ci.cancel();
                    break;
                }
            }
        }
    }
    
    @Redirect(method = { "getEntityByID" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/IntHashMap;lookup(I)Ljava/lang/Object;"))
    private Object getEntityByIDHook(final IntHashMap<Entity> intHashMap, final int id) {
        Entity entity = (Entity)intHashMap.lookup(id);
        if (entity == null) {
            entity = Managers.SET_DEAD.getEntity(id);
            if (entity != null) {
                entity.lastTickPosX = entity.posX;
                entity.lastTickPosY = entity.posY;
                entity.lastTickPosZ = entity.posZ;
                entity.prevPosX = entity.posX;
                entity.prevPosY = entity.posY;
                entity.prevPosZ = entity.posZ;
            }
        }
        return entity;
    }
    
    @Inject(method = { "updateEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2) })
    private void updateEntitiesHook(final CallbackInfo ci) {
        final UpdateEntitiesEvent event = new UpdateEntitiesEvent();
        Bus.EVENT_BUS.post(event);
    }
    
    @Inject(method = { "getBlockState" }, at = { @At("HEAD") }, cancellable = true)
    private void getBlockStateHook(final BlockPos pos, final CallbackInfoReturnable<IBlockState> cir) {
        if (MixinWorld.PACKETS.isEnabled()) {
            final IBlockState state = MixinWorld.PACKETS.get().getStateMap().get(pos);
            if (state != null) {
                cir.setReturnValue(state);
            }
        }
    }
    
    @Redirect(method = { "mayPlace" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;checkNoEntityCollision(Lnet/minecraft/util/math/AxisAlignedBB;Lnet/minecraft/entity/Entity;)Z"))
    private boolean checkNoEntityCollisionHook(final World world, final AxisAlignedBB bb, final Entity entityIn) {
        if (MixinWorld.BLOCK_TWEAKS.returnIfPresent(BlockTweaks::isIgnoreFallingActive, false)) {
            for (final Entity entity : world.getEntitiesWithinAABBExcludingEntity((Entity)null, bb)) {
                if (!entity.isDead && !(entity instanceof EntityFallingBlock) && entity.preventEntitySpawning && entity != entityIn && (entityIn == null || !entity.isRidingSameEntity(entityIn))) {
                    return false;
                }
            }
            return true;
        }
        return world.checkNoEntityCollision(bb, entityIn);
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
        PACKETS = Caches.getModule(Packets.class);
        BLOCK_TWEAKS = Caches.getModule(BlockTweaks.class);
    }
}
