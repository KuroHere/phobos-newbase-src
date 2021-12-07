// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class FlatMenuUI extends BasicMenuUI
{
    private Color hoverBackground;
    private FlatMenuItemRenderer renderer;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatMenuUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.menuItem, "iconTextGap", FlatUIUtils.getUIInt("MenuItem.iconTextGap", 4));
        this.menuItem.setRolloverEnabled(true);
        this.hoverBackground = UIManager.getColor("MenuBar.hoverBackground");
        this.renderer = this.createRenderer();
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.hoverBackground = null;
        this.renderer = null;
    }
    
    protected FlatMenuItemRenderer createRenderer() {
        return new FlatMenuRenderer(this.menuItem, this.checkIcon, this.arrowIcon, this.acceleratorFont, this.acceleratorDelimiter);
    }
    
    @Override
    protected MouseInputListener createMouseInputListener(final JComponent c) {
        return new MouseInputHandler() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                super.mouseEntered(e);
                this.rollover(e, true);
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                super.mouseExited(e);
                this.rollover(e, false);
            }
            
            private void rollover(final MouseEvent e, final boolean rollover) {
                final JMenu menu = (JMenu)e.getSource();
                if (menu.isTopLevelMenu() && menu.isRolloverEnabled()) {
                    menu.getModel().setRollover(rollover);
                    menu.repaint();
                }
            }
        };
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return ((JMenu)this.menuItem).isTopLevelMenu() ? c.getPreferredSize() : null;
    }
    
    @Override
    protected Dimension getPreferredMenuItemSize(final JComponent c, final Icon checkIcon, final Icon arrowIcon, final int defaultTextIconGap) {
        return this.renderer.getPreferredMenuItemSize();
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        this.renderer.paintMenuItem(g, this.selectionBackground, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
    }
    
    protected class FlatMenuRenderer extends FlatMenuItemRenderer
    {
        protected final Color menuBarUnderlineSelectionBackground;
        protected final Color menuBarUnderlineSelectionColor;
        protected final int menuBarUnderlineSelectionHeight;
        
        protected FlatMenuRenderer(final JMenuItem menuItem, final Icon checkIcon, final Icon arrowIcon, final Font acceleratorFont, final String acceleratorDelimiter) {
            super(menuItem, checkIcon, arrowIcon, acceleratorFont, acceleratorDelimiter);
            this.menuBarUnderlineSelectionBackground = FlatUIUtils.getUIColor("MenuBar.underlineSelectionBackground", this.underlineSelectionBackground);
            this.menuBarUnderlineSelectionColor = FlatUIUtils.getUIColor("MenuBar.underlineSelectionColor", this.underlineSelectionColor);
            this.menuBarUnderlineSelectionHeight = FlatUIUtils.getUIInt("MenuBar.underlineSelectionHeight", this.underlineSelectionHeight);
        }
        
        @Override
        protected void paintBackground(final Graphics g, Color selectionBackground) {
            if (this.isUnderlineSelection() && ((JMenu)this.menuItem).isTopLevelMenu()) {
                selectionBackground = this.menuBarUnderlineSelectionBackground;
            }
            final ButtonModel model = this.menuItem.getModel();
            if (model.isRollover() && !model.isArmed() && !model.isSelected() && model.isEnabled() && ((JMenu)this.menuItem).isTopLevelMenu()) {
                g.setColor(this.deriveBackground(FlatMenuUI.this.hoverBackground));
                g.fillRect(0, 0, this.menuItem.getWidth(), this.menuItem.getHeight());
            }
            else {
                super.paintBackground(g, selectionBackground);
            }
        }
        
        @Override
        protected void paintUnderlineSelection(final Graphics g, Color underlineSelectionColor, int underlineSelectionHeight) {
            if (((JMenu)this.menuItem).isTopLevelMenu()) {
                underlineSelectionColor = this.menuBarUnderlineSelectionColor;
                underlineSelectionHeight = this.menuBarUnderlineSelectionHeight;
            }
            super.paintUnderlineSelection(g, underlineSelectionColor, underlineSelectionHeight);
        }
    }
}
