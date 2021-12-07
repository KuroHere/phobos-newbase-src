// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatSearchWithHistoryIcon extends FlatSearchIcon
{
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.translate(-2, 0);
        super.paintIcon(c, g);
        g.translate(2, 0);
        g.fill(FlatUIUtils.createPath(11.0, 7.0, 16.0, 7.0, 13.5, 10.0));
    }
}
