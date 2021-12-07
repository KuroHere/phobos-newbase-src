// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import java.awt.*;
import com.formdev.flatlaf.ui.*;
import java.awt.geom.*;
import javax.swing.*;

public class FlatClearIcon extends FlatAbstractIcon
{
    protected Color clearIconColor;
    protected Color clearIconHoverColor;
    protected Color clearIconPressedColor;
    
    public FlatClearIcon() {
        super(16, 16, null);
        this.clearIconColor = UIManager.getColor("SearchField.clearIconColor");
        this.clearIconHoverColor = UIManager.getColor("SearchField.clearIconHoverColor");
        this.clearIconPressedColor = UIManager.getColor("SearchField.clearIconPressedColor");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        if (c instanceof AbstractButton) {
            final ButtonModel model = ((AbstractButton)c).getModel();
            if (model.isPressed() || model.isRollover()) {
                g.setColor(model.isPressed() ? this.clearIconPressedColor : this.clearIconHoverColor);
                final Path2D path = new Path2D.Float(0);
                path.append(new Ellipse2D.Float(1.75f, 1.75f, 12.5f, 12.5f), false);
                path.append(FlatUIUtils.createPath(4.5, 5.5, 5.5, 4.5, 8.0, 7.0, 10.5, 4.5, 11.5, 5.5, 9.0, 8.0, 11.5, 10.5, 10.5, 11.5, 8.0, 9.0, 5.5, 11.5, 4.5, 10.5, 7.0, 8.0), false);
                g.fill(path);
                return;
            }
        }
        g.setColor(this.clearIconColor);
        final Path2D path2 = new Path2D.Float(0);
        path2.append(new Line2D.Float(5.0f, 5.0f, 11.0f, 11.0f), false);
        path2.append(new Line2D.Float(5.0f, 11.0f, 11.0f, 5.0f), false);
        g.draw(path2);
    }
}
