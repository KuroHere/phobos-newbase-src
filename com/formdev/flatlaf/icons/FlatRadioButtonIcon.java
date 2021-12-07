// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import java.awt.geom.*;
import java.awt.*;

public class FlatRadioButtonIcon extends FlatCheckBoxIcon
{
    protected final int centerDiameter;
    
    public FlatRadioButtonIcon() {
        this.centerDiameter = FlatCheckBoxIcon.getUIInt("RadioButton.icon.centerDiameter", 8, this.style);
    }
    
    @Override
    protected void paintFocusBorder(final Component c, final Graphics2D g) {
        final int wh = 15 + this.focusWidth * 2;
        g.fillOval(-this.focusWidth, -this.focusWidth, wh, wh);
    }
    
    @Override
    protected void paintBorder(final Component c, final Graphics2D g) {
        g.fillOval(0, 0, 15, 15);
    }
    
    @Override
    protected void paintBackground(final Component c, final Graphics2D g) {
        g.fillOval(1, 1, 13, 13);
    }
    
    @Override
    protected void paintCheckmark(final Component c, final Graphics2D g) {
        final float xy = (15 - this.centerDiameter) / 2.0f;
        g.fill(new Ellipse2D.Float(xy, xy, (float)this.centerDiameter, (float)this.centerDiameter));
    }
}
