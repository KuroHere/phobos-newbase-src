// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.util.*;
import com.formdev.flatlaf.*;
import javax.swing.*;

public class FlatMenuBarUI extends BasicMenuBarUI
{
    public static ComponentUI createUI(final JComponent c) {
        return new FlatMenuBarUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.menuBar, "opaque", false);
    }
    
    @Override
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        ActionMap map = SwingUtilities.getUIActionMap(this.menuBar);
        if (map == null) {
            map = new ActionMapUIResource();
            SwingUtilities.replaceUIActionMap(this.menuBar, map);
        }
        map.put("takeFocus", new TakeFocus());
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        final Color background = this.getBackground(c);
        if (background != null) {
            g.setColor(background);
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        this.paint(g, c);
    }
    
    protected Color getBackground(final JComponent c) {
        Color background = c.getBackground();
        if (c.isOpaque() || !(background instanceof UIResource)) {
            return background;
        }
        final JRootPane rootPane = SwingUtilities.getRootPane(c);
        if (rootPane == null || !(rootPane.getParent() instanceof Window) || rootPane.getJMenuBar() != c) {
            return background;
        }
        if (UIManager.getBoolean("TitlePane.unifiedBackground") && FlatNativeWindowBorder.hasCustomDecoration((Window)rootPane.getParent())) {
            background = FlatUIUtils.getParentBackground(c);
        }
        if (FlatUIUtils.isFullScreen(rootPane)) {
            return background;
        }
        return FlatRootPaneUI.isMenuBarEmbedded(rootPane) ? null : background;
    }
    
    private static class TakeFocus extends AbstractAction
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final JMenuBar menuBar = (JMenuBar)e.getSource();
            final JMenu menu = menuBar.getMenu(0);
            if (menu != null) {
                MenuSelectionManager.defaultManager().setSelectedPath(SystemInfo.isWindows ? new MenuElement[] { menuBar, menu } : new MenuElement[] { menuBar, menu, menu.getPopupMenu() });
                FlatLaf.showMnemonics(menuBar);
            }
        }
    }
}
