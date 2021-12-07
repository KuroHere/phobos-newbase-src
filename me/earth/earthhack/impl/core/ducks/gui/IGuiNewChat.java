// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.gui;

import net.minecraft.util.text.*;

public interface IGuiNewChat
{
    boolean replace(final ITextComponent p0, final int p1, final boolean p2, final boolean p3);
    
    int getScrollPos();
    
    void setScrollPos(final int p0);
    
    boolean getScrolled();
    
    void setScrolled(final boolean p0);
    
    void invokeSetChatLine(final ITextComponent p0, final int p1, final int p2, final boolean p3);
    
    void invokeClearChat(final boolean p0);
}
