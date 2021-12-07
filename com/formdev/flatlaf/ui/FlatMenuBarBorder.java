// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import java.awt.*;
import javax.swing.*;

public class FlatMenuBarBorder extends FlatMarginBorder
{
    private final Color borderColor;
    
    public FlatMenuBarBorder() {
        this.borderColor = UIManager.getColor("MenuBar.borderColor");
    }
    
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final float lineHeight = UIScale.scale(1.0f);
        FlatUIUtils.paintFilledRectangle(g, this.borderColor, (float)x, y + height - lineHeight, (float)width, lineHeight);
    }
    
    @Override
    public Insets getBorderInsets(final Component c, final Insets insets) {
        final Insets margin = (c instanceof JMenuBar) ? ((JMenuBar)c).getMargin() : new Insets(0, 0, 0, 0);
        insets.top = UIScale.scale(margin.top);
        insets.left = UIScale.scale(margin.left);
        insets.bottom = UIScale.scale(margin.bottom + 1);
        insets.right = UIScale.scale(margin.right);
        return insets;
    }
}
