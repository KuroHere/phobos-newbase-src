// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlatDesktopPaneUI extends BasicDesktopPaneUI
{
    private LayoutDockListener layoutDockListener;
    private boolean layoutDockPending;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatDesktopPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        this.layoutDockLaterOnce();
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.layoutDockListener = new LayoutDockListener();
        this.desktop.addContainerListener(this.layoutDockListener);
        this.desktop.addComponentListener(this.layoutDockListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.desktop.removeContainerListener(this.layoutDockListener);
        this.desktop.removeComponentListener(this.layoutDockListener);
        this.layoutDockListener = null;
    }
    
    private void layoutDockLaterOnce() {
        if (this.layoutDockPending) {
            return;
        }
        this.layoutDockPending = true;
        EventQueue.invokeLater(() -> {
            this.layoutDockPending = false;
            if (this.desktop != null) {
                this.layoutDock();
            }
        });
    }
    
    protected void layoutDock() {
        final Dimension desktopSize = this.desktop.getSize();
        int x = 0;
        int y = desktopSize.height;
        int rowHeight = 0;
        for (final Component c : this.desktop.getComponents()) {
            if (c instanceof JInternalFrame.JDesktopIcon) {
                final JInternalFrame.JDesktopIcon icon = (JInternalFrame.JDesktopIcon)c;
                final Dimension iconSize = icon.getPreferredSize();
                if (x + iconSize.width > desktopSize.width) {
                    x = 0;
                    y -= rowHeight;
                    rowHeight = 0;
                }
                icon.setLocation(x, y - iconSize.height);
                x += iconSize.width;
                rowHeight = Math.max(iconSize.height, rowHeight);
            }
        }
    }
    
    private class LayoutDockListener extends ComponentAdapter implements ContainerListener
    {
        @Override
        public void componentAdded(final ContainerEvent e) {
            FlatDesktopPaneUI.this.layoutDockLaterOnce();
        }
        
        @Override
        public void componentRemoved(final ContainerEvent e) {
            FlatDesktopPaneUI.this.layoutDockLaterOnce();
        }
        
        @Override
        public void componentResized(final ComponentEvent e) {
            FlatDesktopPaneUI.this.layoutDockLaterOnce();
        }
    }
}
