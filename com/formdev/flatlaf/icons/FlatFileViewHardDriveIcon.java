// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;

public class FlatFileViewHardDriveIcon extends FlatAbstractIcon
{
    public FlatFileViewHardDriveIcon() {
        super(16, 16, UIManager.getColor("Objects.Grey"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        final Path2D path = new Path2D.Float(0);
        path.append(new Rectangle2D.Float(2.0f, 6.0f, 12.0f, 4.0f), false);
        path.append(new Rectangle2D.Float(12.0f, 8.0f, 1.0f, 1.0f), false);
        path.append(new Rectangle2D.Float(10.0f, 8.0f, 1.0f, 1.0f), false);
        g.fill(path);
    }
}
