// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.settings;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public abstract class MixinKeyBinding
{
    @Inject(method = { "unpressKey" }, at = { @At("HEAD") })
    private void onUnpress(final CallbackInfo info) {
    }
}
