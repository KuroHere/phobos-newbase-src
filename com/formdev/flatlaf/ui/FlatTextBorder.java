// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import java.awt.*;

public class FlatTextBorder extends FlatBorder
{
    protected final int arc;
    
    public FlatTextBorder() {
        this.arc = UIManager.getInt("TextComponent.arc");
    }
    
    @Override
    protected int getArc(final Component c) {
        if (this.isCellEditor(c)) {
            return 0;
        }
        final Boolean roundRect = FlatUIUtils.isRoundRect(c);
        return (roundRect != null) ? (roundRect ? 32767 : 0) : this.arc;
    }
}
