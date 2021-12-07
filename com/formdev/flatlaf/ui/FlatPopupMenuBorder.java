// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;

public class FlatPopupMenuBorder extends FlatLineBorder
{
    public FlatPopupMenuBorder() {
        super(UIManager.getInsets("PopupMenu.borderInsets"), UIManager.getColor("PopupMenu.borderColor"));
    }
    
    @Override
    public Insets getBorderInsets(final Component c, final Insets insets) {
        if (c instanceof Container && ((Container)c).getComponentCount() > 0 && ((Container)c).getComponent(0) instanceof JScrollPane) {
            final int scale = UIScale.scale(1);
            insets.bottom = scale;
            insets.right = scale;
            insets.top = scale;
            insets.left = scale;
            return insets;
        }
        return super.getBorderInsets(c, insets);
    }
}
