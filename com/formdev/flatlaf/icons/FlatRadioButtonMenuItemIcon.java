// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import java.awt.*;

public class FlatRadioButtonMenuItemIcon extends FlatCheckBoxMenuItemIcon
{
    @Override
    protected void paintCheckmark(final Graphics2D g2) {
        g2.fillOval(4, 4, 7, 7);
    }
}
