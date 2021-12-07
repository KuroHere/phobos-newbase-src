// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FlatToolBarUI extends BasicToolBarUI
{
    protected boolean focusableButtons;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatToolBarUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        if (!this.focusableButtons) {
            this.setButtonsFocusable(false);
        }
    }
    
    @Override
    public void uninstallUI(final JComponent c) {
        super.uninstallUI(c);
        if (!this.focusableButtons) {
            this.setButtonsFocusable(true);
        }
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.focusableButtons = UIManager.getBoolean("ToolBar.focusableButtons");
    }
    
    @Override
    protected ContainerListener createToolBarContListener() {
        return new ToolBarContListener() {
            @Override
            public void componentAdded(final ContainerEvent e) {
                super.componentAdded(e);
                if (!FlatToolBarUI.this.focusableButtons) {
                    final Component c = e.getChild();
                    if (c instanceof AbstractButton) {
                        c.setFocusable(false);
                    }
                }
            }
            
            @Override
            public void componentRemoved(final ContainerEvent e) {
                super.componentRemoved(e);
                if (!FlatToolBarUI.this.focusableButtons) {
                    final Component c = e.getChild();
                    if (c instanceof AbstractButton) {
                        c.setFocusable(true);
                    }
                }
            }
        };
    }
    
    protected void setButtonsFocusable(final boolean focusable) {
        for (final Component c : this.toolBar.getComponents()) {
            if (c instanceof AbstractButton) {
                c.setFocusable(focusable);
            }
        }
    }
    
    @Override
    protected void setBorderToRollover(final Component c) {
    }
    
    @Override
    protected void setBorderToNonRollover(final Component c) {
    }
    
    @Override
    protected void setBorderToNormal(final Component c) {
    }
    
    @Override
    protected void installRolloverBorders(final JComponent c) {
    }
    
    @Override
    protected void installNonRolloverBorders(final JComponent c) {
    }
    
    @Override
    protected void installNormalBorders(final JComponent c) {
    }
    
    @Override
    protected Border createRolloverBorder() {
        return null;
    }
    
    @Override
    protected Border createNonRolloverBorder() {
        return null;
    }
    
    @Override
    public void setOrientation(final int orientation) {
        if (orientation != this.toolBar.getOrientation()) {
            final Insets margin = this.toolBar.getMargin();
            final Insets newMargin = new Insets(margin.left, margin.top, margin.right, margin.bottom);
            if (!newMargin.equals(margin)) {
                this.toolBar.setMargin(newMargin);
            }
        }
        super.setOrientation(orientation);
    }
}
