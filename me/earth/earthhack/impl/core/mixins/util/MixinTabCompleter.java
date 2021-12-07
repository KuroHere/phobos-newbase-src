//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TabCompleter.class })
public abstract class MixinTabCompleter
{
    @Inject(method = { "requestCompletions" }, at = { @At("HEAD") }, cancellable = true)
    private void requestCompletionsHook(final String prefix, final CallbackInfo ci) {
        if (Minecraft.getMinecraft().player == null) {
            ci.cancel();
        }
    }
}
