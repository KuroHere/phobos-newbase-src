//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import me.earth.earthhack.impl.core.ducks.render.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.rainbowenchant.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.renderer.texture.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderItem.class })
public abstract class MixinRenderItem implements IRenderItem
{
    private static final ResourceLocation RESOURCE;
    private static final ModuleCache<RainbowEnchant> ENCHANT;
    
    @Accessor("notRenderingEffectsInGUI")
    @Override
    public abstract void setNotRenderingEffectsInGUI(final boolean p0);
    
    @Redirect(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    public void bindHook(final TextureManager textureManager, final ResourceLocation resource) {
        if (MixinRenderItem.ENCHANT.isEnabled()) {
            textureManager.bindTexture(MixinRenderItem.RESOURCE);
        }
        else {
            textureManager.bindTexture(resource);
        }
    }
    
    static {
        RESOURCE = new ResourceLocation("textures/rainbow.png");
        ENCHANT = Caches.getModule(RainbowEnchant.class);
    }
}
