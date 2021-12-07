// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatFileChooserHomeFolderIcon extends FlatAbstractIcon
{
    public FlatFileChooserHomeFolderIcon() {
        super(16, 16, UIManager.getColor("Actions.Grey"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.fill(FlatUIUtils.createPath(2.0, 8.0, 8.0, 2.0, 14.0, 8.0, 12.0, 8.0, 12.0, 13.0, 9.0, 13.0, 9.0, 10.0, 7.0, 10.0, 7.0, 13.0, 4.0, 13.0, 4.0, 8.0));
    }
}
