// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import com.formdev.flatlaf.util.*;
import javax.swing.*;
import java.awt.*;

public class FlatBorder extends BasicBorders.MarginBorder
{
    protected final int focusWidth;
    protected final float innerFocusWidth;
    protected final float innerOutlineWidth;
    protected final Color focusColor;
    protected final Color borderColor;
    protected final Color disabledBorderColor;
    protected final Color focusedBorderColor;
    protected final Color errorBorderColor;
    protected final Color errorFocusedBorderColor;
    protected final Color warningBorderColor;
    protected final Color warningFocusedBorderColor;
    protected final Color customBorderColor;
    
    public FlatBorder() {
        this.focusWidth = UIManager.getInt("Component.focusWidth");
        this.innerFocusWidth = FlatUIUtils.getUIFloat("Component.innerFocusWidth", 0.0f);
        this.innerOutlineWidth = FlatUIUtils.getUIFloat("Component.innerOutlineWidth", 0.0f);
        this.focusColor = UIManager.getColor("Component.focusColor");
        this.borderColor = UIManager.getColor("Component.borderColor");
        this.disabledBorderColor = UIManager.getColor("Component.disabledBorderColor");
        this.focusedBorderColor = UIManager.getColor("Component.focusedBorderColor");
        this.errorBorderColor = UIManager.getColor("Component.error.borderColor");
        this.errorFocusedBorderColor = UIManager.getColor("Component.error.focusedBorderColor");
        this.warningBorderColor = UIManager.getColor("Component.warning.borderColor");
        this.warningFocusedBorderColor = UIManager.getColor("Component.warning.focusedBorderColor");
        this.customBorderColor = UIManager.getColor("Component.custom.borderColor");
    }
    
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            final float focusWidth = UIScale.scale((float)this.getFocusWidth(c));
            final float borderWidth = UIScale.scale((float)this.getBorderWidth(c));
            final float arc = UIScale.scale((float)this.getArc(c));
            final Color outlineColor = this.getOutlineColor(c);
            if (outlineColor != null || this.isFocused(c)) {
                final float innerWidth = (!this.isCellEditor(c) && !(c instanceof JScrollPane)) ? ((outlineColor != null) ? this.innerOutlineWidth : this.getInnerFocusWidth(c)) : 0.0f;
                if (focusWidth > 0.0f || innerWidth > 0.0f) {
                    g2.setColor((outlineColor != null) ? outlineColor : this.getFocusColor(c));
                    FlatUIUtils.paintComponentOuterBorder(g2, x, y, width, height, focusWidth, borderWidth + UIScale.scale(innerWidth), arc);
                }
            }
            g2.setPaint((outlineColor != null) ? outlineColor : this.getBorderColor(c));
            FlatUIUtils.paintComponentBorder(g2, x, y, width, height, focusWidth, borderWidth, arc);
        }
        finally {
            g2.dispose();
        }
    }
    
    protected Color getOutlineColor(final Component c) {
        if (!(c instanceof JComponent)) {
            return null;
        }
        final Object outline = ((JComponent)c).getClientProperty("JComponent.outline");
        if (outline instanceof String) {
            final String s = (String)outline;
            switch (s) {
                case "error": {
                    return this.isFocused(c) ? this.errorFocusedBorderColor : this.errorBorderColor;
                }
                case "warning": {
                    return this.isFocused(c) ? this.warningFocusedBorderColor : this.warningBorderColor;
                }
            }
        }
        else {
            if (outline instanceof Color) {
                Color color = (Color)outline;
                if (!this.isFocused(c) && this.customBorderColor instanceof DerivedColor) {
                    color = ((DerivedColor)this.customBorderColor).derive(color);
                }
                return color;
            }
            if (outline instanceof Color[] && ((Color[])outline).length >= 2) {
                return ((Color[])outline)[!this.isFocused(c)];
            }
        }
        return null;
    }
    
    protected Color getFocusColor(final Component c) {
        return this.focusColor;
    }
    
    protected Paint getBorderColor(final Component c) {
        return this.isEnabled(c) ? (this.isFocused(c) ? this.focusedBorderColor : this.borderColor) : this.disabledBorderColor;
    }
    
    protected boolean isEnabled(final Component c) {
        if (c instanceof JScrollPane) {
            final JViewport viewport = ((JScrollPane)c).getViewport();
            final Component view = (viewport != null) ? viewport.getView() : null;
            if (view != null && !this.isEnabled(view)) {
                return false;
            }
        }
        return c.isEnabled();
    }
    
    protected boolean isFocused(final Component c) {
        if (c instanceof JScrollPane) {
            return FlatScrollPaneUI.isPermanentFocusOwner((JScrollPane)c);
        }
        if (c instanceof JComboBox) {
            return FlatComboBoxUI.isPermanentFocusOwner((JComboBox<?>)c);
        }
        if (c instanceof JSpinner) {
            return FlatSpinnerUI.isPermanentFocusOwner((JSpinner)c);
        }
        return FlatUIUtils.isPermanentFocusOwner(c);
    }
    
    protected boolean isCellEditor(final Component c) {
        return FlatUIUtils.isCellEditor(c);
    }
    
    @Override
    public Insets getBorderInsets(final Component c, Insets insets) {
        final float focusWidth = UIScale.scale((float)this.getFocusWidth(c));
        final int ow = Math.round(focusWidth + UIScale.scale((float)this.getLineWidth(c)));
        insets = super.getBorderInsets(c, insets);
        insets.top = UIScale.scale(insets.top) + ow;
        insets.left = UIScale.scale(insets.left) + ow;
        insets.bottom = UIScale.scale(insets.bottom) + ow;
        insets.right = UIScale.scale(insets.right) + ow;
        if (this.isCellEditor(c)) {
            final Insets insets2 = insets;
            final Insets insets3 = insets;
            final int n = 0;
            insets3.bottom = n;
            insets2.top = n;
            if (c.getComponentOrientation().isLeftToRight()) {
                insets.right = 0;
            }
            else {
                insets.left = 0;
            }
        }
        return insets;
    }
    
    protected int getFocusWidth(final Component c) {
        if (this.isCellEditor(c)) {
            return 0;
        }
        return this.focusWidth;
    }
    
    protected float getInnerFocusWidth(final Component c) {
        return this.innerFocusWidth;
    }
    
    protected int getLineWidth(final Component c) {
        return 1;
    }
    
    protected int getBorderWidth(final Component c) {
        return this.getLineWidth(c);
    }
    
    protected int getArc(final Component c) {
        return 0;
    }
}
