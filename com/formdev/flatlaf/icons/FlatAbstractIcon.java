// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import javax.swing.plaf.*;
import java.awt.*;
import com.formdev.flatlaf.ui.*;
import com.formdev.flatlaf.util.*;

public abstract class FlatAbstractIcon implements Icon, UIResource
{
    protected final int width;
    protected final int height;
    protected final Color color;
    
    public FlatAbstractIcon(final int width, final int height, final Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    @Override
    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            g2.translate(x, y);
            UIScale.scaleGraphics(g2);
            if (this.color != null) {
                g2.setColor(this.color);
            }
            this.paintIcon(c, g2);
        }
        finally {
            g2.dispose();
        }
    }
    
    protected abstract void paintIcon(final Component p0, final Graphics2D p1);
    
    @Override
    public int getIconWidth() {
        return UIScale.scale(this.width);
    }
    
    @Override
    public int getIconHeight() {
        return UIScale.scale(this.height);
    }
}
