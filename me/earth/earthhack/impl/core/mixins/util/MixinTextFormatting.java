// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.*;
import java.util.regex.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TextFormatting.class })
public abstract class MixinTextFormatting
{
    private static final Pattern NEW_PATTERN;
    
    @Redirect(method = { "getTextWithoutFormattingCodes" }, at = @At(value = "INVOKE", target = "Ljava/util/regex/Pattern;matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", remap = false))
    private static Matcher patternHook(final Pattern pattern, final CharSequence s) {
        return MixinTextFormatting.NEW_PATTERN.matcher(s);
    }
    
    static {
        NEW_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORY+-]|§Z[0-9A-F]{8}");
    }
}
