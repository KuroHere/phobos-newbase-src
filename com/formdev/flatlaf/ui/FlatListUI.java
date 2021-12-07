// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FlatListUI extends BasicListUI
{
    protected Color selectionBackground;
    protected Color selectionForeground;
    protected Color selectionInactiveBackground;
    protected Color selectionInactiveForeground;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatListUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.selectionBackground = UIManager.getColor("List.selectionBackground");
        this.selectionForeground = UIManager.getColor("List.selectionForeground");
        this.selectionInactiveBackground = UIManager.getColor("List.selectionInactiveBackground");
        this.selectionInactiveForeground = UIManager.getColor("List.selectionInactiveForeground");
        this.toggleSelectionColors();
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.selectionBackground = null;
        this.selectionForeground = null;
        this.selectionInactiveBackground = null;
        this.selectionInactiveForeground = null;
    }
    
    @Override
    protected FocusListener createFocusListener() {
        return new FocusHandler() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                FlatListUI.this.toggleSelectionColors();
            }
            
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                EventQueue.invokeLater(() -> FlatListUI.this.toggleSelectionColors());
            }
        };
    }
    
    private void toggleSelectionColors() {
        if (this.list == null) {
            return;
        }
        if (FlatUIUtils.isPermanentFocusOwner(this.list)) {
            if (this.list.getSelectionBackground() == this.selectionInactiveBackground) {
                this.list.setSelectionBackground(this.selectionBackground);
            }
            if (this.list.getSelectionForeground() == this.selectionInactiveForeground) {
                this.list.setSelectionForeground(this.selectionForeground);
            }
        }
        else {
            if (this.list.getSelectionBackground() == this.selectionBackground) {
                this.list.setSelectionBackground(this.selectionInactiveBackground);
            }
            if (this.list.getSelectionForeground() == this.selectionForeground) {
                this.list.setSelectionForeground(this.selectionInactiveForeground);
            }
        }
    }
}
