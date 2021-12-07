//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.nametags.*;
import me.earth.earthhack.impl.modules.render.handchams.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.handchams.modes.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderPlayer.class })
public abstract class MixinRenderPlayer
{
    private static final ModuleCache<Nametags> NAMETAGS;
    private static final ModuleCache<HandChams> HAND_CHAMS;
    
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    public void renderEntityNameHook(final AbstractClientPlayer entityIn, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo info) {
        if (MixinRenderPlayer.NAMETAGS.isEnabled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "renderRightArm" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFF)V"))
    public void renderRightArmHook(final float colorRed, final float colorGreen, final float colorBlue) {
        if (MixinRenderPlayer.HAND_CHAMS.isEnabled() && MixinRenderPlayer.HAND_CHAMS.get().mode.getValue() == ChamsMode.Gradient) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, MixinRenderPlayer.HAND_CHAMS.get().color.getValue().getAlpha() / 255.0f);
        }
    }
    
    @Redirect(method = { "renderLeftArm" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFF)V"))
    public void renderLeftArmHook(final float colorRed, final float colorGreen, final float colorBlue) {
        if (MixinRenderPlayer.HAND_CHAMS.isEnabled() && MixinRenderPlayer.HAND_CHAMS.get().mode.getValue() == ChamsMode.Gradient) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, MixinRenderPlayer.HAND_CHAMS.get().color.getValue().getAlpha() / 255.0f);
        }
    }
    
    static {
        NAMETAGS = Caches.getModule(Nametags.class);
        HAND_CHAMS = Caches.getModule(HandChams.class);
    }
}
