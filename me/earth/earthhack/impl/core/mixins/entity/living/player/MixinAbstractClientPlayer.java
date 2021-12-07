//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import net.minecraft.client.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Shadow
    public abstract boolean isSpectator();
    
    @Inject(method = { "getFovModifier" }, at = { @At("HEAD") }, cancellable = true)
    private void getFovModifierHook(final CallbackInfoReturnable<Float> info) {
        if (MixinAbstractClientPlayer.NO_RENDER.returnIfPresent(NoRender::dynamicFov, false)) {
            info.setReturnValue(1.0f);
        }
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
