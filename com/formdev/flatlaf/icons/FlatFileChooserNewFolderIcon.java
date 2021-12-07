// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;

public class FlatFileChooserNewFolderIcon extends FlatAbstractIcon
{
    public FlatFileChooserNewFolderIcon() {
        super(16, 16, UIManager.getColor("Actions.Grey"));
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        g.fill(FlatUIUtils.createPath(2.0, 3.0, 5.5, 3.0, 7.0, 5.0, 14.0, 5.0, 14.0, 8.0, 11.0, 8.0, 11.0, 10.0, 9.0, 10.0, 9.0, 13.0, 2.0, 13.0));
        g.fill(FlatUIUtils.createPath(14.0, 11.0, 16.0, 11.0, 16.0, 13.0, 14.0, 13.0, 14.0, 15.0, 12.0, 15.0, 12.0, 13.0, 10.0, 13.0, 10.0, 11.0, 12.0, 11.0, 12.0, 9.0, 14.0, 9.0, 14.0, 11.0));
    }
}
