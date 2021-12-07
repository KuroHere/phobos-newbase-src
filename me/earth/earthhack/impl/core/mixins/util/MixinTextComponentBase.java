//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.*;
import java.util.function.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TextComponentBase.class })
public abstract class MixinTextComponentBase implements ITextComponentBase, ITextComponent
{
    private Supplier<String> hookFormat;
    private Supplier<String> hookUnFormat;
    
    @Override
    public void setFormattingHook(final Supplier<String> hook) {
        this.hookFormat = hook;
    }
    
    @Override
    public void setUnFormattedHook(final Supplier<String> hook) {
        this.hookUnFormat = hook;
    }
    
    @Override
    public ITextComponent copyNoSiblings() {
        final ITextComponent copy = this.createCopy();
        copy.getSiblings().clear();
        return copy;
    }
    
    @Inject(method = { "getFormattedText" }, at = { @At("HEAD") }, cancellable = true)
    public void getFormattedTextHook(final CallbackInfoReturnable<String> info) {
        if (this.hookFormat != null) {
            info.setReturnValue(this.hookFormat.get());
        }
    }
    
    @Inject(method = { "getUnformattedText" }, at = { @At("HEAD") }, cancellable = true)
    public void getUnformattedTextHook(final CallbackInfoReturnable<String> info) {
        if (this.hookUnFormat != null) {
            info.setReturnValue(this.hookUnFormat.get());
        }
    }
}
