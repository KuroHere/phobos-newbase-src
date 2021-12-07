// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;

public class FlatMenuItemBorder extends FlatMarginBorder
{
    private final Insets menuBarItemMargins;
    
    public FlatMenuItemBorder() {
        this.menuBarItemMargins = UIManager.getInsets("MenuBar.itemMargins");
    }
    
    @Override
    public Insets getBorderInsets(final Component c, final Insets insets) {
        if (c.getParent() instanceof JMenuBar) {
            insets.top = UIScale.scale(this.menuBarItemMargins.top);
            insets.left = UIScale.scale(this.menuBarItemMargins.left);
            insets.bottom = UIScale.scale(this.menuBarItemMargins.bottom);
            insets.right = UIScale.scale(this.menuBarItemMargins.right);
            return insets;
        }
        return super.getBorderInsets(c, insets);
    }
}
