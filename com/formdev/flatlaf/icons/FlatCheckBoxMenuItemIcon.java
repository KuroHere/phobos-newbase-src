// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import java.awt.geom.*;
import java.awt.*;
import javax.swing.*;

public class FlatCheckBoxMenuItemIcon extends FlatAbstractIcon
{
    protected final Color checkmarkColor;
    protected final Color disabledCheckmarkColor;
    protected final Color selectionForeground;
    
    public FlatCheckBoxMenuItemIcon() {
        super(15, 15, null);
        this.checkmarkColor = UIManager.getColor("MenuItemCheckBox.icon.checkmarkColor");
        this.disabledCheckmarkColor = UIManager.getColor("MenuItemCheckBox.icon.disabledCheckmarkColor");
        this.selectionForeground = UIManager.getColor("MenuItem.selectionForeground");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g2) {
        final boolean selected = c instanceof AbstractButton && ((AbstractButton)c).isSelected();
        if (selected) {
            g2.setColor(this.getCheckmarkColor(c));
            this.paintCheckmark(g2);
        }
    }
    
    protected void paintCheckmark(final Graphics2D g2) {
        final Path2D.Float path = new Path2D.Float();
        path.moveTo(4.5f, 7.5f);
        path.lineTo(6.6f, 10.0f);
        path.lineTo(11.25f, 3.5f);
        g2.setStroke(new BasicStroke(1.9f, 1, 1));
        g2.draw(path);
    }
    
    protected Color getCheckmarkColor(final Component c) {
        if (c instanceof JMenuItem && ((JMenuItem)c).isArmed() && !this.isUnderlineSelection()) {
            return this.selectionForeground;
        }
        return c.isEnabled() ? this.checkmarkColor : this.disabledCheckmarkColor;
    }
    
    protected boolean isUnderlineSelection() {
        return "underline".equals(UIManager.getString("MenuItem.selectionType"));
    }
}
