// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.awt.event.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.beans.*;
import javax.swing.text.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatEditorPaneUI extends BasicEditorPaneUI
{
    protected int minimumWidth;
    protected boolean isIntelliJTheme;
    protected Color focusedBackground;
    private Insets defaultMargin;
    private Object oldHonorDisplayProperties;
    private FocusListener focusListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatEditorPaneUI();
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
        propertyChange(this.getComponent(), e);
    }
    
    static void propertyChange(final JTextComponent c, final PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "JComponent.minimumWidth": {
                c.revalidate();
                break;
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return applyMinimumWidth(c, super.getPreferredSize(c), this.minimumWidth, this.defaultMargin);
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return applyMinimumWidth(c, super.getMinimumSize(c), this.minimumWidth, this.defaultMargin);
    }
    
    static Dimension applyMinimumWidth(final JComponent c, final Dimension size, int minimumWidth, final Insets defaultMargin) {
        if (!FlatTextFieldUI.hasDefaultMargins(c, defaultMargin)) {
            return size;
        }
        minimumWidth = FlatUIUtils.minimumWidth(c, minimumWidth);
        size.width = Math.max(size.width, UIScale.scale(minimumWidth) - UIScale.scale(1) * 2);
        return size;
    }
    
    @Override
    protected void paintSafely(final Graphics g) {
        super.paintSafely(HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g));
    }
    
    @Override
    protected void paintBackground(final Graphics g) {
        paintBackground(g, this.getComponent(), this.isIntelliJTheme, this.focusedBackground);
    }
    
    static void paintBackground(final Graphics g, final JTextComponent c, final boolean isIntelliJTheme, final Color focusedBackground) {
        g.setColor(FlatTextFieldUI.getBackground(c, isIntelliJTheme, focusedBackground));
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
