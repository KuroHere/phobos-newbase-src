// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.settingspoof;

import net.minecraft.util.*;

public enum HandTranslator
{
    Left(EnumHandSide.LEFT), 
    Right(EnumHandSide.RIGHT);
    
    private final EnumHandSide handSide;
    
    private HandTranslator(final EnumHandSide visibility) {
        this.handSide = visibility;
    }
    
    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}
