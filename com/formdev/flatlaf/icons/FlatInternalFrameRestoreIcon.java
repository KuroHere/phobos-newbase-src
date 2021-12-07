// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import com.formdev.flatlaf.ui.*;
import java.awt.*;
import java.awt.geom.*;

public class FlatInternalFrameRestoreIcon extends FlatInternalFrameAbstractIcon
{
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        this.paintBackground(c, g);
        g.setColor(c.getForeground());
        final int x = this.width / 2 - 4;
        final int y = this.height / 2 - 4;
        final Path2D r1 = FlatUIUtils.createRectangle((float)(x + 1), (float)(y - 1), 8.0f, 8.0f, 1.0f);
        final Path2D r2 = FlatUIUtils.createRectangle((float)(x - 1), (float)(y + 1), 8.0f, 8.0f, 1.0f);
        final Area area = new Area(r1);
        area.subtract(new Area(new Rectangle2D.Float((float)(x - 1), (float)(y + 1), 8.0f, 8.0f)));
        g.fill(area);
        g.fill(r2);
    }
}
