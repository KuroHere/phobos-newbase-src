//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityEnderCrystal.class })
public abstract class MixinEntityEnderCrystal extends MixinEntity
{
    @Inject(method = { "<init>(Lnet/minecraft/world/World;DDD)V" }, at = { @At("RETURN") })
    private void initHook(final World worldIn, final double x, final double y, final double z, final CallbackInfo ci) {
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.lastTickPosX = x;
        this.lastTickPosY = y;
        this.lastTickPosZ = z;
    }
}
