// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.util;

import java.util.function.*;
import net.minecraft.util.text.*;

public interface ITextComponentBase
{
    void setFormattingHook(final Supplier<String> p0);
    
    void setUnFormattedHook(final Supplier<String> p0);
    
    ITextComponent copyNoSiblings();
}
