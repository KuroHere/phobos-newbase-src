// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatFileViewDirectoryIcon extends FlatAbstractIcon
{
    public FlatFileViewDirectoryIcon() {
        super(16, 16, UIManager.getColor("Objects.Grey"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.fill(FlatUIUtils.createPath(1.0, 2.0, 6.0, 2.0, 8.0, 4.0, 15.0, 4.0, 15.0, 13.0, 1.0, 13.0));
    }
}
