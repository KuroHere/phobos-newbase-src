// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.awt.event.*;
import java.util.function.*;
import java.beans.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;
import java.util.*;

public class FlatTextFieldUI extends BasicTextFieldUI
{
    protected int minimumWidth;
    protected boolean isIntelliJTheme;
    protected Color placeholderForeground;
    protected Color focusedBackground;
    private Insets defaultMargin;
    private FocusListener focusListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTextFieldUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        final String prefix = this.getPropertyPrefix();
        this.minimumWidth = UIManager.getInt("Component.minimumWidth");
        this.isIntelliJTheme = UIManager.getBoolean("Component.isIntelliJTheme");
        this.placeholderForeground = UIManager.getColor(prefix + ".placeholderForeground");
        this.focusedBackground = UIManager.getColor(prefix + ".focusedBackground");
        this.defaultMargin = UIManager.getInsets(prefix + ".margin");
        LookAndFeel.installProperty(this.getComponent(), "opaque", false);
        MigLayoutVisualPadding.install(this.getComponent());
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.placeholderForeground = null;
        this.focusedBackground = null;
        MigLayoutVisualPadding.uninstall(this.getComponent());
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.focusListener = new FlatUIUtils.RepaintFocusListener(this.getComponent(), null);
        this.getComponent().addFocusListener(this.focusListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.getComponent().removeFocusListener(this.focusListener);
        this.focusListener = null;
    }
    
    @Override
    protected Caret createCaret() {
        return new FlatCaret(UIManager.getString("TextComponent.selectAllOnFocusPolicy"), UIManager.getBoolean("TextComponent.selectAllOnMouseClick"));
    }
    
    @Override
    protected void propertyChange(final PropertyChangeEvent e) {
        super.propertyChange(e);
        propertyChange(this.getComponent(), e);
    }
    
    static void propertyChange(final JTextComponent c, final PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "JTextField.placeholderText":
            case "JComponent.roundRect":
            case "JTextField.padding": {
                c.repaint();
                break;
            }
            case "JComponent.minimumWidth": {
                c.revalidate();
                break;
            }
        }
    }
    
    @Override
    protected void paintSafely(final Graphics g) {
        paintBackground(g, this.getComponent(), this.isIntelliJTheme, this.focusedBackground);
        this.paintPlaceholder(g);
        super.paintSafely(HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g));
    }
    
    @Override
    protected void paintBackground(final Graphics g) {
    }
    
    static void paintBackground(final Graphics g, final JTextComponent c, final boolean isIntelliJTheme, final Color focusedBackground) {
        if (!c.isOpaque() && FlatUIUtils.getOutsideFlatBorder(c) == null && FlatUIUtils.hasOpaqueBeenExplicitlySet(c)) {
            return;
        }
        final float focusWidth = FlatUIUtils.getBorderFocusWidth(c);
        final float arc = FlatUIUtils.getBorderArc(c);
        if (c.isOpaque() && (focusWidth > 0.0f || arc > 0.0f)) {
            FlatUIUtils.paintParentBackground(g, c);
        }
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            g2.setColor(getBackground(c, isIntelliJTheme, focusedBackground));
            FlatUIUtils.paintComponentBackground(g2, 0, 0, c.getWidth(), c.getHeight(), focusWidth, arc);
        }
        finally {
            g2.dispose();
        }
    }
    
    static Color getBackground(final JTextComponent c, final boolean isIntelliJTheme, final Color focusedBackground) {
        final Color background = c.getBackground();
        if (!(background instanceof UIResource)) {
            return background;
        }
        if (focusedBackground != null && FlatUIUtils.isPermanentFocusOwner(c)) {
            return focusedBackground;
        }
        if (isIntelliJTheme && (!c.isEnabled() || !c.isEditable())) {
            return FlatUIUtils.getParentBackground(c);
        }
        return background;
    }
    
    protected void paintPlaceholder(final Graphics g) {
        final JTextComponent c = this.getComponent();
        if (c.getDocument().getLength() > 0) {
            return;
        }
        final Container parent = c.getParent();
        final JComponent jc = (JComponent)((parent instanceof JComboBox) ? parent : c);
        final Object placeholder = jc.getClientProperty("JTextField.placeholderText");
        if (!(placeholder instanceof String)) {
            return;
        }
        final Rectangle r = this.getVisibleEditorRect();
        final FontMetrics fm = c.getFontMetrics(c.getFont());
        final int y = r.y + fm.getAscent() + (r.height - fm.getHeight()) / 2;
        g.setColor(this.placeholderForeground);
        final String clippedPlaceholder = JavaCompatibility.getClippedString(c, fm, (String)placeholder, r.width);
        FlatUIUtils.drawString(c, g, clippedPlaceholder, r.x, y);
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return this.applyMinimumWidth(c, super.getPreferredSize(c), this.minimumWidth);
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return this.applyMinimumWidth(c, super.getMinimumSize(c), this.minimumWidth);
    }
    
    private Dimension applyMinimumWidth(final JComponent c, final Dimension size, int minimumWidth) {
        if (c instanceof JTextField && ((JTextField)c).getColumns() > 0) {
            return size;
        }
        if (!hasDefaultMargins(c, this.defaultMargin)) {
            return size;
        }
        final Container parent = c.getParent();
        if (parent instanceof JComboBox || parent instanceof JSpinner || (parent != null && parent.getParent() instanceof JSpinner)) {
            return size;
        }
        minimumWidth = FlatUIUtils.minimumWidth(c, minimumWidth);
        final float focusWidth = FlatUIUtils.getBorderFocusWidth(c);
        size.width = Math.max(size.width, UIScale.scale(minimumWidth) + Math.round(focusWidth * 2.0f));
        return size;
    }
    
    static boolean hasDefaultMargins(final JComponent c, final Insets defaultMargin) {
        final Insets margin = ((JTextComponent)c).getMargin();
        return margin instanceof UIResource && Objects.equals(margin, defaultMargin);
    }
    
    @Override
    protected Rectangle getVisibleEditorRect() {
        Rectangle r = super.getVisibleEditorRect();
        if (r != null) {
            final Insets padding = this.getPadding();
            if (padding != null) {
                r = FlatUIUtils.subtractInsets(r, padding);
                r.width = Math.max(r.width, 0);
                r.height = Math.max(r.height, 0);
            }
        }
        return r;
    }
    
    protected Insets getPadding() {
        final Object padding = this.getComponent().getClientProperty("JTextField.padding");
        return (padding instanceof Insets) ? UIScale.scale((Insets)padding) : null;
    }
    
    protected void scrollCaretToVisible() {
        final Caret caret = this.getComponent().getCaret();
        if (caret instanceof FlatCaret) {
            ((FlatCaret)caret).scrollCaretToVisible();
        }
    }
}
