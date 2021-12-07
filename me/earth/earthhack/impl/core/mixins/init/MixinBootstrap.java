// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.init;

import org.spongepowered.asm.mixin.*;
import net.minecraft.init.*;
import java.io.*;
import net.minecraft.advancements.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Bootstrap.class })
public abstract class MixinBootstrap
{
    @Redirect(method = { "register" }, at = @At(value = "NEW", target = "net/minecraft/advancements/AdvancementManager"))
    private static AdvancementManager advancementManagerHook(final File file) {
        return AdvancementArgument.MANAGER;
    }
}
