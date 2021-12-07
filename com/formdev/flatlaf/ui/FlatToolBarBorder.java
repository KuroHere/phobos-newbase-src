// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatToolBarBorder extends FlatMarginBorder
{
    private static final int DOT_COUNT = 4;
    private static final int DOT_SIZE = 2;
    private static final int GRIP_SIZE = 6;
    protected final Color gripColor;
    
    public FlatToolBarBorder() {
        super(UIManager.getInsets("ToolBar.borderMargins"));
        this.gripColor = UIManager.getColor("ToolBar.gripColor");
    }
    
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        if (c instanceof JToolBar && ((JToolBar)c).isFloatable()) {
            final Graphics2D g2 = (Graphics2D)g.create();
            try {
                FlatUIUtils.setRenderingHints(g2);
                g2.setColor(this.gripColor);
                this.paintGrip(c, g2, x, y, width, height);
            }
            finally {
                g2.dispose();
            }
        }
    }
    
    protected void paintGrip(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final Rectangle r = this.calculateGripBounds(c, x, y, width, height);
        FlatUIUtils.paintGrip(g, r.x, r.y, r.width, r.height, ((JToolBar)c).getOrientation() == 1, 4, 2, 2, false);
    }
    
    protected Rectangle calculateGripBounds(final Component c, final int x, final int y, final int width, final int height) {
        final Insets insets = super.getBorderInsets(c, new Insets(0, 0, 0, 0));
        final Rectangle r = FlatUIUtils.subtractInsets(new Rectangle(x, y, width, height), insets);
        final int gripSize = UIScale.scale(6);
        if (((JToolBar)c).getOrientation() == 0) {
            if (!c.getComponentOrientation().isLeftToRight()) {
                r.x = r.x + r.width - gripSize;
            }
            r.width = gripSize;
        }
        else {
            r.height = gripSize;
        }
        return r;
    }
    
    @Override
    public Insets getBorderInsets(final Component c, Insets insets) {
        insets = super.getBorderInsets(c, insets);
        if (c instanceof JToolBar && ((JToolBar)c).isFloatable()) {
            final int gripInset = UIScale.scale(6);
            if (((JToolBar)c).getOrientation() == 0) {
                if (c.getComponentOrientation().isLeftToRight()) {
                    final Insets insets2 = insets;
                    insets2.left += gripInset;
                }
                else {
                    final Insets insets3 = insets;
                    insets3.right += gripInset;
                }
            }
            else {
                final Insets insets4 = insets;
                insets4.top += gripInset;
            }
        }
        return insets;
    }
}
