// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.util.function.*;
import javax.swing.*;
import java.awt.*;

public class FlatViewportUI extends BasicViewportUI
{
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatViewportUI.class, (Supplier<ComponentUI>)FlatViewportUI::new);
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        final Component view = ((JViewport)c).getView();
        if (c.isOpaque() && view instanceof JTable) {
            g.setColor(view.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
            this.paint(g, c);
        }
        else {
            super.update(g, c);
        }
    }
}
