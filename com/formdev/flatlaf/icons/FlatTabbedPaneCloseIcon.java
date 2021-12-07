// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.geom.*;
import java.awt.*;

public class FlatTabbedPaneCloseIcon extends FlatAbstractIcon
{
    protected final Dimension size;
    protected final int arc;
    protected final float crossPlainSize;
    protected final float crossFilledSize;
    protected final float closeCrossLineWidth;
    protected final Color background;
    protected final Color foreground;
    protected final Color hoverBackground;
    protected final Color hoverForeground;
    protected final Color pressedBackground;
    protected final Color pressedForeground;
    
    public FlatTabbedPaneCloseIcon() {
        super(16, 16, null);
        this.size = UIManager.getDimension("TabbedPane.closeSize");
        this.arc = UIManager.getInt("TabbedPane.closeArc");
        this.crossPlainSize = FlatUIUtils.getUIFloat("TabbedPane.closeCrossPlainSize", 7.5f);
        this.crossFilledSize = FlatUIUtils.getUIFloat("TabbedPane.closeCrossFilledSize", this.crossPlainSize);
        this.closeCrossLineWidth = FlatUIUtils.getUIFloat("TabbedPane.closeCrossLineWidth", 1.0f);
        this.background = UIManager.getColor("TabbedPane.closeBackground");
        this.foreground = UIManager.getColor("TabbedPane.closeForeground");
        this.hoverBackground = UIManager.getColor("TabbedPane.closeHoverBackground");
        this.hoverForeground = UIManager.getColor("TabbedPane.closeHoverForeground");
        this.pressedBackground = UIManager.getColor("TabbedPane.closePressedBackground");
        this.pressedForeground = UIManager.getColor("TabbedPane.closePressedForeground");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        final Color bg = FlatButtonUI.buttonStateColor(c, this.background, null, null, this.hoverBackground, this.pressedBackground);
        if (bg != null) {
            g.setColor(FlatUIUtils.deriveColor(bg, c.getBackground()));
            g.fillRoundRect((this.width - this.size.width) / 2, (this.height - this.size.height) / 2, this.size.width, this.size.height, this.arc, this.arc);
        }
        final Color fg = FlatButtonUI.buttonStateColor(c, this.foreground, null, null, this.hoverForeground, this.pressedForeground);
        g.setColor(FlatUIUtils.deriveColor(fg, c.getForeground()));
        final float mx = (float)(this.width / 2);
        final float my = (float)(this.height / 2);
        final float r = ((bg != null) ? this.crossFilledSize : this.crossPlainSize) / 2.0f;
        final Path2D path = new Path2D.Float(0);
        path.append(new Line2D.Float(mx - r, my - r, mx + r, my + r), false);
        path.append(new Line2D.Float(mx - r, my + r, mx + r, my - r), false);
        g.setStroke(new BasicStroke(this.closeCrossLineWidth));
        g.draw(path);
    }
}
