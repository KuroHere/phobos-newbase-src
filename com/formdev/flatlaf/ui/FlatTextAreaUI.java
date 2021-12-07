// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.plaf.*;
import javax.swing.text.*;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatTextAreaUI extends BasicTextAreaUI
{
    protected int minimumWidth;
    protected boolean isIntelliJTheme;
    protected Color background;
    protected Color disabledBackground;
    protected Color inactiveBackground;
    protected Color focusedBackground;
    private Insets defaultMargin;
    private FocusListener focusListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTextAreaUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        this.updateBackground();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.minimumWidth = UIManager.getInt("Component.minimumWidth");
        this.isIntelliJTheme = UIManager.getBoolean("Component.isIntelliJTheme");
        this.background = UIManager.getColor("TextArea.background");
        this.disabledBackground = UIManager.getColor("TextArea.disabledBackground");
        this.inactiveBackground = UIManager.getColor("TextArea.inactiveBackground");
        this.focusedBackground = UIManager.getColor("TextArea.focusedBackground");
        this.defaultMargin = UIManager.getInsets("TextArea.margin");
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.background = null;
        this.disabledBackground = null;
        this.inactiveBackground = null;
        this.focusedBackground = null;
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.focusListener = new FlatUIUtils.RepaintFocusListener(this.getComponent(), c -> this.focusedBackground != null);
        this.getComponent().addFocusListener(this.focusListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.getComponent().removeFocusListener(this.focusListener);
        this.focusListener = null;
    }
    
    @Override
    protected void propertyChange(final PropertyChangeEvent e) {
        super.propertyChange(e);
        FlatEditorPaneUI.propertyChange(this.getComponent(), e);
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "editable":
            case "enabled": {
                this.updateBackground();
                break;
            }
        }
    }
    
    private void updateBackground() {
        final JTextComponent c = this.getComponent();
        final Color background = c.getBackground();
        if (!(background instanceof UIResource)) {
            return;
        }
        if (background != this.background && background != this.disabledBackground && background != this.inactiveBackground) {
            return;
        }
        final Color newBackground = c.isEnabled() ? (c.isEditable() ? this.background : this.inactiveBackground) : this.disabledBackground;
        if (newBackground != background) {
            c.setBackground(newBackground);
        }
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return this.applyMinimumWidth(c, super.getPreferredSize(c));
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return this.applyMinimumWidth(c, super.getMinimumSize(c));
    }
    
    private Dimension applyMinimumWidth(final JComponent c, final Dimension size) {
        if (c instanceof JTextArea && ((JTextArea)c).getColumns() > 0) {
            return size;
        }
        return FlatEditorPaneUI.applyMinimumWidth(c, size, this.minimumWidth, this.defaultMargin);
    }
    
    @Override
    protected void paintSafely(final Graphics g) {
        super.paintSafely(HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g));
    }
    
    @Override
    protected void paintBackground(final Graphics g) {
        FlatEditorPaneUI.paintBackground(g, this.getComponent(), this.isIntelliJTheme, this.focusedBackground);
    }
}
