// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatTreeLeafIcon extends FlatAbstractIcon
{
    public FlatTreeLeafIcon() {
        super(16, 16, UIManager.getColor("Tree.icon.leafColor"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.fill(FlatUIUtils.createPath(8.0, 6.0, 8.0, 1.0, 13.0, 1.0, 13.0, 15.0, 3.0, 15.0, 3.0, 6.0));
        g.fill(FlatUIUtils.createPath(3.0, 5.0, 7.0, 5.0, 7.0, 1.0));
    }
}
