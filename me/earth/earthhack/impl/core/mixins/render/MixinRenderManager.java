//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.impl.core.ducks.render.*;
import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderManager.class })
public abstract class MixinRenderManager implements IRenderManager
{
    private static final ModuleCache<NoInterp> NOINTERP;
    
    @Accessor("renderPosX")
    @Override
    public abstract double getRenderPosX();
    
    @Accessor("renderPosY")
    @Override
    public abstract double getRenderPosY();
    
    @Accessor("renderPosZ")
    @Override
    public abstract double getRenderPosZ();
    
    @Redirect(method = { "renderEntityStatic" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D", ordinal = 1))
    private double posXHook0(final Entity entity) {
        return NoInterp.noInterpX(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderEntityStatic" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D", ordinal = 1))
    private double posYHook0(final Entity entity) {
        return NoInterp.noInterpY(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderEntityStatic" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D", ordinal = 1))
    private double posZHook0(final Entity entity) {
        return NoInterp.noInterpZ(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "cacheActiveRenderInfo" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D", ordinal = 1))
    private double posXHook1(final Entity entity) {
        return NoInterp.noInterpX(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "cacheActiveRenderInfo" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D", ordinal = 1))
    private double posYHook1(final Entity entity) {
        return NoInterp.noInterpY(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "cacheActiveRenderInfo" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D", ordinal = 1))
    private double posZHook1(final Entity entity) {
        return NoInterp.noInterpZ(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderMultipass" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D", ordinal = 1))
    private double posXHook2(final Entity entity) {
        return NoInterp.noInterpX(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderMultipass" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D", ordinal = 1))
    private double posYHook2(final Entity entity) {
        return NoInterp.noInterpY(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderMultipass" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D", ordinal = 1))
    private double posZHook2(final Entity entity) {
        return NoInterp.noInterpZ(MixinRenderManager.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderEntity" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Render;doRender(Lnet/minecraft/entity/Entity;DDDFF)V"))
    public void renderEntity(final Render<Entity> render, final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final RenderEntityEvent pre = new RenderEntityEvent.Pre(render, entity, x, y, z, entityYaw, partialTicks);
        Bus.EVENT_BUS.post(pre);
        if (!pre.isCancelled()) {
            render.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        final RenderEntityEvent post = new RenderEntityEvent.Post(render, entity);
        Bus.EVENT_BUS.post(post);
    }
    
    static {
        NOINTERP = Caches.getModule(NoInterp.class);
    }
}
