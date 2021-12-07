// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import com.formdev.flatlaf.ui.*;

public abstract class FlatWindowAbstractIcon extends FlatAbstractIcon
{
    private final Color hoverBackground;
    private final Color pressedBackground;
    
    public FlatWindowAbstractIcon() {
        this(UIManager.getDimension("TitlePane.buttonSize"), UIManager.getColor("TitlePane.buttonHoverBackground"), UIManager.getColor("TitlePane.buttonPressedBackground"));
    }
    
    public FlatWindowAbstractIcon(final Dimension size, final Color hoverBackground, final Color pressedBackground) {
        super(size.width, size.height, null);
        this.hoverBackground = hoverBackground;
        this.pressedBackground = pressedBackground;
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        this.paintBackground(c, g);
        g.setColor(this.getForeground(c));
        HiDPIUtils.paintAtScale1x(g, 0, 0, this.width, this.height, this::paintIconAt1x);
    }
    
    protected abstract void paintIconAt1x(final Graphics2D p0, final int p1, final int p2, final int p3, final int p4, final double p5);
    
    protected void paintBackground(final Component c, final Graphics2D g) {
        final Color background = FlatButtonUI.buttonStateColor(c, null, null, null, this.hoverBackground, this.pressedBackground);
        if (background != null) {
            g.setColor(FlatUIUtils.deriveColor(background, c.getBackground()));
            g.fillRect(0, 0, this.width, this.height);
        }
    }
    
    protected Color getForeground(final Component c) {
        return c.getForeground();
    }
}
