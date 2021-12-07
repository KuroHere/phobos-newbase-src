//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public abstract class MixinKeyBinding implements IKeyBinding
{
    @Shadow
    private boolean pressed;
    
    @Accessor("pressed")
    @Override
    public abstract void setPressed(final boolean p0);
    
    @Inject(method = { "isKeyDown" }, at = { @At("RETURN") }, cancellable = true)
    private void isKeyDownHook(final CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValue()) {
            info.setReturnValue(this.pressed);
        }
    }
}
