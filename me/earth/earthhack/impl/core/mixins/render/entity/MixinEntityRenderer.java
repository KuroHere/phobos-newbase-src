//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import me.earth.earthhack.impl.modules.render.blockhighlight.*;
import me.earth.earthhack.impl.modules.player.blocktweaks.*;
import me.earth.earthhack.impl.modules.render.viewclip.*;
import me.earth.earthhack.impl.modules.render.weather.*;
import me.earth.earthhack.impl.modules.player.raytrace.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.lwjgl.util.glu.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.gui.*;
import java.nio.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import me.earth.earthhack.impl.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.impl.event.events.misc.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.misc.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer implements IEntityRenderer
{
    private static final ModuleCache<NoRender> NO_RENDER;
    private static final ModuleCache<BlockHighlight> BLOCK_HIGHLIGHT;
    private static final ModuleCache<BlockTweaks> BLOCK_TWEAKS;
    private static final ModuleCache<CameraClip> CAMERA_CLIP;
    private static final ModuleCache<Weather> WEATHER;
    private static final ModuleCache<RayTrace> RAYTRACE;
    private static final ModuleCache<Spectate> SPECTATE;
    private static final SettingCache<Boolean, BooleanSetting, CameraClip> EXTEND;
    private static final SettingCache<Double, NumberSetting<Double>, CameraClip> DISTANCE;
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private ItemStack field_190566_ab;
    private float lastReach;
    
    @Shadow
    protected abstract void orientCamera(final float p0);
    
    @Shadow
    protected abstract void setupCameraTransform(final float p0, final int p1);
    
    @Override
    public void invokeOrientCamera(final float partialTicks) {
        this.orientCamera(partialTicks);
    }
    
    @Override
    public void invokeSetupCameraTransform(final float partialTicks, final int pass) {
        this.setupCameraTransform(partialTicks, pass);
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap = false))
    private void onSetupCameraTransform(final float fovy, final float aspect, final float zNear, final float zFar) {
        final AspectRatioEvent event = new AspectRatioEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective(fovy, event.getAspectRatio(), zNear, zFar);
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap = false))
    private void onRenderWorldPass(final float fovy, final float aspect, final float zNear, final float zFar) {
        final AspectRatioEvent event = new AspectRatioEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective(fovy, event.getAspectRatio(), zNear, zFar);
        final GLUProjection projection = GLUProjection.getInstance();
        final IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projectionPort);
        GL11.glGetInteger(2978, viewPort);
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        projection.updateMatrices(viewPort, modelView, projectionPort, scaledResolution.getScaledWidth() / (double)Minecraft.getMinecraft().displayWidth, scaledResolution.getScaledHeight() / (double)Minecraft.getMinecraft().displayHeight);
    }
    
    @Redirect(method = { "renderCloudsCheck" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap = false))
    private void onRenderCloudsCheck(final float fovy, final float aspect, final float zNear, final float zFar) {
        final AspectRatioEvent event = new AspectRatioEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective(fovy, event.getAspectRatio(), zNear, zFar);
    }
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemActivationHook(final CallbackInfo info) {
        if (this.field_190566_ab != null && MixinEntityRenderer.NO_RENDER.returnIfPresent(NoRender::noTotems, false) && this.field_190566_ab.getItem() == Items.field_190929_cY) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.clear(I)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void renderWorldPassHook(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo info) {
        if (Display.isActive() || Display.isVisible()) {
            Bus.EVENT_BUS.post(new Render3DEvent(partialTicks));
        }
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(final EntityPlayerSP entityPlayerSP) {
        if (MixinEntityRenderer.NO_RENDER.returnIfPresent(NoRender::noNausea, false)) {
            return -3.4028235E38f;
        }
        return entityPlayerSP.prevTimeInPortal;
    }
    
    @Inject(method = { "setupFog" }, at = { @At("RETURN") }, cancellable = true)
    public void setupFogHook(final int startCoords, final float partialTicks, final CallbackInfo info) {
        if (MixinEntityRenderer.NO_RENDER.returnIfPresent(NoRender::noFog, false)) {
            GlStateManager.setFogDensity(0.0f);
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (MixinEntityRenderer.NO_RENDER.returnIfPresent(NoRender::noHurtCam, false) || ESP.isRendering) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcludingHook(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate<? super Entity> predicate) {
        if (MixinEntityRenderer.BLOCK_TWEAKS.isEnabled() && MixinEntityRenderer.BLOCK_TWEAKS.get().noMiningTrace()) {
            return Collections.emptyList();
        }
        try {
            final Predicate<? super Entity> p = (Predicate<? super Entity>)(e -> predicate.test((Object)e) && !e.equals((Object)this.mc.player));
            return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, (Predicate)p);
        }
        catch (Exception e) {
            Earthhack.getLogger().warn("It's that Exception again...");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/PlayerControllerMP.getBlockReachDistance()F"))
    private float getBlockReachDistanceHook(final PlayerControllerMP controller) {
        final ReachEvent event = new ReachEvent(controller.getBlockReachDistance(), 0.0f);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            this.lastReach = event.getReach();
        }
        else {
            this.lastReach = 0.0f;
        }
        return this.mc.playerController.getBlockReachDistance() + this.lastReach;
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "net/minecraft/util/math/Vec3d.distanceTo(Lnet/minecraft/util/math/Vec3d;)D"))
    private double distanceToHook(final Vec3d vec3d, final Vec3d vec3d1) {
        return vec3d.distanceTo(vec3d1) - this.lastReach;
    }
    
    @ModifyVariable(method = { "orientCamera" }, ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double changeCameraDistanceHook(final double range) {
        return (MixinEntityRenderer.CAMERA_CLIP.isEnabled() && MixinEntityRenderer.EXTEND.getValue()) ? MixinEntityRenderer.DISTANCE.getValue() : range;
    }
    
    @ModifyVariable(method = { "orientCamera" }, ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double orientCameraHook(final double range) {
        return MixinEntityRenderer.CAMERA_CLIP.isEnabled() ? (MixinEntityRenderer.EXTEND.getValue() ? MixinEntityRenderer.DISTANCE.getValue() : 4.0) : range;
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;IF)V"))
    public void drawSelectionBoxHook(final RenderGlobal renderGlobal, final EntityPlayer player, final RayTraceResult movingObjectPositionIn, final int execute, final float partialTicks) {
        if (!MixinEntityRenderer.BLOCK_HIGHLIGHT.isEnabled()) {
            renderGlobal.drawSelectionBox(player, movingObjectPositionIn, execute, partialTicks);
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V") })
    public void weatherHook(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        if (MixinEntityRenderer.WEATHER.isEnabled()) {
            MixinEntityRenderer.WEATHER.get().render(partialTicks);
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;rayTrace(DF)Lnet/minecraft/util/math/RayTraceResult;"))
    private RayTraceResult getMouseOverHook(final Entity entity, final double blockReachDistance, final float partialTicks) {
        if (MixinEntityRenderer.RAYTRACE.returnIfPresent(RayTrace::isActive, false)) {
            final Vec3d start = entity.getPositionEyes(partialTicks);
            final Vec3d look = entity.getLook(partialTicks);
            final Vec3d end = start.addVector(look.xCoord * blockReachDistance, look.yCoord * blockReachDistance, look.zCoord * blockReachDistance);
            if (MixinEntityRenderer.RAYTRACE.returnIfPresent(RayTrace::liquidCrystalPlace, false) && (this.mc.player.isInsideOfMaterial(Material.WATER) || this.mc.player.isInsideOfMaterial(Material.LAVA)) && InventoryUtil.isHolding(Blocks.OBSIDIAN)) {
                final MutableWrapper<BlockPos> opposite = new MutableWrapper<BlockPos>();
                RayTraceResult result = this.traceInLiquid(start, end, opposite, true);
                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (result.getBlockPos().equals((Object)opposite.get())) {
                        result.sideHit = result.sideHit.getOpposite();
                    }
                    return result;
                }
                result = this.traceInLiquid(start, end, opposite, false);
                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (result.getBlockPos().equals((Object)opposite.get())) {
                        result.sideHit = result.sideHit.getOpposite();
                    }
                    return result;
                }
            }
            if (MixinEntityRenderer.RAYTRACE.returnIfPresent(RayTrace::phaseCheck, false)) {
                return RayTracer.traceTri((World)this.mc.world, (IBlockAccess)this.mc.world, start, end, false, false, true, (b, p, ef) -> {
                    final AxisAlignedBB bb = this.mc.world.getBlockState(p).getBoundingBox((IBlockAccess)this.mc.world, p).offset(p);
                    if (RotationUtil.getRotationPlayer().getEntityBoundingBox().intersectsWith(bb) && p.getY() > this.mc.player.posY + 0.25) {
                        return false;
                    }
                    else if (ef == null) {
                        return true;
                    }
                    else {
                        this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(p.offset(ef))).iterator();
                        final Iterator iterator;
                        while (iterator.hasNext()) {
                            final Entity e = iterator.next();
                            if (e != null && !e.isDead && !this.mc.player.equals((Object)e)) {
                                if (RotationUtil.getRotationPlayer().equals((Object)e)) {
                                    continue;
                                }
                                else {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        }
        return entity.rayTrace(blockReachDistance, partialTicks);
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void turnHook(final EntityPlayerSP entityPlayerSP, final float yaw, final float pitch) {
        if (MixinEntityRenderer.SPECTATE.isEnabled()) {
            if (MixinEntityRenderer.SPECTATE.get().shouldTurn()) {
                final EntityPlayer spectate = MixinEntityRenderer.SPECTATE.get().getRender();
                if (spectate != null) {
                    spectate.setAngles(yaw, pitch);
                    spectate.rotationYawHead = spectate.rotationYaw;
                }
            }
            return;
        }
        entityPlayerSP.setAngles(yaw, pitch);
    }
    
    private RayTraceResult traceInLiquid(final Vec3d start, final Vec3d end, final MutableWrapper<BlockPos> opposite, final boolean air) {
        return RayTracer.traceTri((World)this.mc.world, (IBlockAccess)this.mc.world, start, end, false, false, true, (b, p, ef) -> {
            if (ef != null) {
                if (!RotationUtil.getRotationPlayer().getEntityBoundingBox().intersectsWith(new AxisAlignedBB(p))) {
                    final BlockPos pos = p.offset(ef);
                    final BlockPos up = pos.up();
                    if (!air || (this.mc.world.getBlockState(up).getBlock() == Blocks.AIR && this.mc.world.getBlockState(up.up()).getBlock() == Blocks.AIR)) {
                        if (this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB((double)up.getX(), (double)up.getY(), (double)up.getZ(), up.getX() + 1.0, up.getY() + 2.0, up.getZ() + 1.0)).isEmpty()) {
                            return true;
                        }
                    }
                    final BlockPos pos2 = p.offset(ef.getOpposite());
                    final BlockPos up2 = pos2.up();
                    if (!air || (this.mc.world.getBlockState(up2).getBlock() == Blocks.AIR && this.mc.world.getBlockState(up2.up()).getBlock() == Blocks.AIR)) {
                        if (this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB((double)up2.getX(), (double)up2.getY(), (double)up2.getZ(), up2.getX() + 1.0, up2.getY() + 2.0, up2.getZ() + 1.0)).isEmpty()) {
                            opposite.set(p);
                            return true;
                        }
                    }
                    return !(b instanceof BlockLiquid) && !(b instanceof BlockAir);
                }
            }
            return false;
        }, (b, p, ef) -> true);
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
        BLOCK_HIGHLIGHT = Caches.getModule(BlockHighlight.class);
        BLOCK_TWEAKS = Caches.getModule(BlockTweaks.class);
        CAMERA_CLIP = Caches.getModule(CameraClip.class);
        WEATHER = Caches.getModule(Weather.class);
        RAYTRACE = Caches.getModule(RayTrace.class);
        SPECTATE = Caches.getModule(Spectate.class);
        EXTEND = Caches.getSetting(CameraClip.class, BooleanSetting.class, "Extend", false);
        DISTANCE = Caches.getSetting(CameraClip.class, Setting.class, "Distance", 10.0);
    }
}
