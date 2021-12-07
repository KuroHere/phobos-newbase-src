// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatMarginBorder extends BasicBorders.MarginBorder
{
    private final int left;
    private final int right;
    private final int top;
    private final int bottom;
    
    public FlatMarginBorder() {
        final int n = 0;
        this.bottom = n;
        this.top = n;
        this.right = n;
        this.left = n;
    }
    
    public FlatMarginBorder(final Insets insets) {
        this.left = insets.left;
        this.top = insets.top;
        this.right = insets.right;
        this.bottom = insets.bottom;
    }
    
    @Override
    public Insets getBorderInsets(final Component c, Insets insets) {
        insets = super.getBorderInsets(c, insets);
        insets.top = UIScale.scale(insets.top + this.top);
        insets.left = UIScale.scale(insets.left + this.left);
        insets.bottom = UIScale.scale(insets.bottom + this.bottom);
        insets.right = UIScale.scale(insets.right + this.right);
        return insets;
    }
}
