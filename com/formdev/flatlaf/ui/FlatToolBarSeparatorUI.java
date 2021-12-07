// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.util.function.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import java.awt.*;

public class FlatToolBarSeparatorUI extends BasicToolBarSeparatorUI
{
    private static final int LINE_WIDTH = 1;
    protected int separatorWidth;
    protected Color separatorColor;
    private boolean defaults_initialized;
    
    public FlatToolBarSeparatorUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatToolBarSeparatorUI.class, (Supplier<ComponentUI>)FlatToolBarSeparatorUI::new);
    }
    
    @Override
    protected void installDefaults(final JSeparator c) {
        super.installDefaults(c);
        if (!this.defaults_initialized) {
            this.separatorWidth = UIManager.getInt("ToolBar.separatorWidth");
            this.separatorColor = UIManager.getColor("ToolBar.separatorColor");
            this.defaults_initialized = true;
        }
        c.setAlignmentX(0.0f);
    }
    
    @Override
    protected void uninstallDefaults(final JSeparator s) {
        super.uninstallDefaults(s);
        this.defaults_initialized = false;
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final Dimension size = ((JToolBar.Separator)c).getSeparatorSize();
        if (size != null) {
            return UIScale.scale(size);
        }
        final int sepWidth = UIScale.scale((this.separatorWidth - 1) / 2) * 2 + UIScale.scale(1);
        final boolean vertical = this.isVertical(c);
        return new Dimension(vertical ? sepWidth : 0, vertical ? 0 : sepWidth);
    }
    
    @Override
    public Dimension getMaximumSize(final JComponent c) {
        final Dimension size = this.getPreferredSize(c);
        if (this.isVertical(c)) {
            return new Dimension(size.width, 32767);
        }
        return new Dimension(32767, size.height);
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final int width = c.getWidth();
        final int height = c.getHeight();
        final float lineWidth = UIScale.scale(1.0f);
        final float offset = UIScale.scale(2.0f);
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        g.setColor(this.separatorColor);
        if (this.isVertical(c)) {
            ((Graphics2D)g).fill(new Rectangle2D.Float((float)Math.round((width - lineWidth) / 2.0f), offset, lineWidth, height - offset * 2.0f));
        }
        else {
            ((Graphics2D)g).fill(new Rectangle2D.Float(offset, (float)Math.round((height - lineWidth) / 2.0f), width - offset * 2.0f, lineWidth));
        }
        FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
    }
    
    private boolean isVertical(final JComponent c) {
        return ((JToolBar.Separator)c).getOrientation() == 1;
    }
}
