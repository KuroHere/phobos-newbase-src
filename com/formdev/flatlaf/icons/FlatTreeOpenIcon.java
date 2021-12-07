// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatTreeOpenIcon extends FlatAbstractIcon
{
    public FlatTreeOpenIcon() {
        super(16, 16, UIManager.getColor("Tree.icon.openColor"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.fill(FlatUIUtils.createPath(1.0, 2.0, 6.0, 2.0, 8.0, 4.0, 14.0, 4.0, 14.0, 6.0, 3.5, 6.0, 1.0, 11.0));
        g.fill(FlatUIUtils.createPath(4.0, 7.0, 16.0, 7.0, 13.0, 13.0, 1.0, 13.0));
    }
}
