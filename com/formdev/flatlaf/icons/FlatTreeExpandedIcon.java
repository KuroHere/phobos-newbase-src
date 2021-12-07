// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import java.awt.*;

public class FlatTreeExpandedIcon extends FlatTreeCollapsedIcon
{
    public FlatTreeExpandedIcon() {
        super(UIManager.getColor("Tree.icon.expandedColor"));
    }
    
    @Override
    void rotate(final Component c, final Graphics2D g) {
        g.rotate(Math.toRadians(90.0), this.width / 2.0, this.height / 2.0);
    }
}
