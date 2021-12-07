// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.awt.event.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.beans.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatTextPaneUI extends BasicTextPaneUI
{
    protected int minimumWidth;
    protected boolean isIntelliJTheme;
    protected Color focusedBackground;
    private Insets defaultMargin;
    private Object oldHonorDisplayProperties;
    private FocusListener focusListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTextPaneUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        final String prefix = this.getPropertyPrefix();
        this.minimumWidth = UIManager.getInt("Component.minimumWidth");
        this.isIntelliJTheme = UIManager.getBoolean("Component.isIntelliJTheme");
        this.focusedBackground = UIManager.getColor(prefix + ".focusedBackground");
        this.defaultMargin = UIManager.getInsets(prefix + ".margin");
        this.oldHonorDisplayProperties = this.getComponent().getClientProperty("JEditorPane.honorDisplayProperties");
        this.getComponent().putClientProperty("JEditorPane.honorDisplayProperties", true);
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.focusedBackground = null;
        this.getComponent().putClientProperty("JEditorPane.honorDisplayProperties", this.oldHonorDisplayProperties);
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
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return FlatEditorPaneUI.applyMinimumWidth(c, super.getPreferredSize(c), this.minimumWidth, this.defaultMargin);
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return FlatEditorPaneUI.applyMinimumWidth(c, super.getMinimumSize(c), this.minimumWidth, this.defaultMargin);
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
