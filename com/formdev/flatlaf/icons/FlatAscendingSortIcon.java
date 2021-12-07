// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;
import java.awt.geom.*;

public class FlatAscendingSortIcon extends FlatAbstractIcon
{
    protected final boolean chevron;
    protected final Color sortIconColor;
    
    public FlatAscendingSortIcon() {
        super(10, 5, null);
        this.chevron = FlatUIUtils.isChevron(UIManager.getString("Component.arrowType"));
        this.sortIconColor = UIManager.getColor("Table.sortIconColor");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.setColor(this.sortIconColor);
        if (this.chevron) {
            final Path2D path = FlatUIUtils.createPath(false, 1.0, 4.0, 5.0, 0.0, 9.0, 4.0);
            g.setStroke(new BasicStroke(1.0f));
            g.draw(path);
        }
        else {
            g.fill(FlatUIUtils.createPath(0.5, 5.0, 5.0, 0.0, 9.5, 5.0));
        }
    }
}
