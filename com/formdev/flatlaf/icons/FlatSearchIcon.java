// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import com.formdev.flatlaf.ui.*;

public class FlatSearchIcon extends FlatAbstractIcon
{
    protected Color searchIconColor;
    protected Color searchIconHoverColor;
    protected Color searchIconPressedColor;
    
    public FlatSearchIcon() {
        super(16, 16, null);
        this.searchIconColor = UIManager.getColor("SearchField.searchIconColor");
        this.searchIconHoverColor = UIManager.getColor("SearchField.searchIconHoverColor");
        this.searchIconPressedColor = UIManager.getColor("SearchField.searchIconPressedColor");
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.setColor(FlatButtonUI.buttonStateColor(c, this.searchIconColor, this.searchIconColor, null, this.searchIconHoverColor, this.searchIconPressedColor));
        final Area area = new Area(new Ellipse2D.Float(2.0f, 2.0f, 10.0f, 10.0f));
        area.subtract(new Area(new Ellipse2D.Float(3.0f, 3.0f, 8.0f, 8.0f)));
        area.add(new Area(FlatUIUtils.createPath(10.813, 9.75, 14.0, 12.938, 12.938, 14.0, 9.75, 10.813)));
        g.fill(area);
    }
}
