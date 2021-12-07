//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.util;

import java.util.function.*;
import net.minecraft.util.text.*;
import java.util.*;

public class SimpleTextFormatHook implements Supplier<String>
{
    private final TextComponentBase base;
    
    public SimpleTextFormatHook(final TextComponentBase base) {
        this.base = base;
    }
    
    @Override
    public String get() {
        final StringBuilder sb = new StringBuilder();
        for (final ITextComponent component : this.base) {
            sb.append(component.getUnformattedComponentText());
        }
        return sb.toString();
    }
}
