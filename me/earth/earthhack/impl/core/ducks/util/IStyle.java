// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.util;

import net.minecraft.util.text.event.*;
import java.util.function.*;

public interface IStyle
{
    void setRightClickEvent(final ClickEvent p0);
    
    void setMiddleClickEvent(final ClickEvent p0);
    
    ClickEvent getRightClickEvent();
    
    ClickEvent getMiddleClickEvent();
    
    void setSuppliedInsertion(final Supplier<String> p0);
    
    void setRightInsertion(final Supplier<String> p0);
    
    void setMiddleInsertion(final Supplier<String> p0);
    
    String getRightInsertion();
    
    String getMiddleInsertion();
}
