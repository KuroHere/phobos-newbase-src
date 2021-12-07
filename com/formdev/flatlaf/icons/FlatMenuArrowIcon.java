// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import com.formdev.flatlaf.ui.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class FlatMenuArrowIcon extends FlatAbstractIcon
{
    protected final boolean chevron;
    protected final Color arrowColor;
    protected final Color disabledArrowColor;
    protected final Color selectionForeground;
    
    public FlatMenuArrowIcon() {
        super(6, 10, null);
        this.chevron = FlatUIUtils.isChevron(UIManager.getString("Component.arrowType"));
        this.arrowColor = UIManager.getColor("Menu.icon.arrowColor");
        this.disabledArrowColor = UIManager.getColor("Menu.icon.disabledArrowColor");
        this.selectionForeground = UIManager.getColor("Menu.selectionForeground");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        if (!c.getComponentOrientation().isLeftToRight()) {
            g.rotate(Math.toRadians(180.0), this.width / 2.0, this.height / 2.0);
        }
        g.setColor(this.getArrowColor(c));
        if (this.chevron) {
            final Path2D path = FlatUIUtils.createPath(false, 1.0, 1.0, 5.0, 5.0, 1.0, 9.0);
            g.setStroke(new BasicStroke(1.0f));
            g.draw(path);
        }
        else {
            g.fill(FlatUIUtils.createPath(0.0, 0.5, 5.0, 5.0, 0.0, 9.5));
        }
    }
    
    protected Color getArrowColor(final Component c) {
        if (c instanceof JMenu && ((JMenu)c).isSelected() && !this.isUnderlineSelection()) {
            return this.selectionForeground;
        }
        return c.isEnabled() ? this.arrowColor : this.disabledArrowColor;
    }
    
    protected boolean isUnderlineSelection() {
        return "underline".equals(UIManager.getString("MenuItem.selectionType"));
    }
}
