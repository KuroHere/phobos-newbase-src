// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class FlatButtonBorder extends FlatBorder
{
    protected final Color borderColor;
    protected final Color endBorderColor;
    protected final Color disabledBorderColor;
    protected final Color focusedBorderColor;
    protected final Color hoverBorderColor;
    protected final Color defaultBorderColor;
    protected final Color defaultEndBorderColor;
    protected final Color defaultHoverBorderColor;
    protected final Color defaultFocusedBorderColor;
    protected final Color defaultFocusColor;
    protected final Color toolbarFocusColor;
    protected final int borderWidth;
    protected final int defaultBorderWidth;
    protected final float buttonInnerFocusWidth;
    protected final Insets toolbarMargin;
    protected final Insets toolbarSpacingInsets;
    protected final float toolbarFocusWidth;
    protected final int arc;
    
    public FlatButtonBorder() {
        this.borderColor = FlatUIUtils.getUIColor("Button.startBorderColor", "Button.borderColor");
        this.endBorderColor = UIManager.getColor("Button.endBorderColor");
        this.disabledBorderColor = UIManager.getColor("Button.disabledBorderColor");
        this.focusedBorderColor = UIManager.getColor("Button.focusedBorderColor");
        this.hoverBorderColor = UIManager.getColor("Button.hoverBorderColor");
        this.defaultBorderColor = FlatUIUtils.getUIColor("Button.default.startBorderColor", "Button.default.borderColor");
        this.defaultEndBorderColor = UIManager.getColor("Button.default.endBorderColor");
        this.defaultHoverBorderColor = UIManager.getColor("Button.default.hoverBorderColor");
        this.defaultFocusedBorderColor = UIManager.getColor("Button.default.focusedBorderColor");
        this.defaultFocusColor = UIManager.getColor("Button.default.focusColor");
        this.toolbarFocusColor = UIManager.getColor("Button.toolbar.focusColor");
        this.borderWidth = UIManager.getInt("Button.borderWidth");
        this.defaultBorderWidth = UIManager.getInt("Button.default.borderWidth");
        this.buttonInnerFocusWidth = FlatUIUtils.getUIFloat("Button.innerFocusWidth", this.innerFocusWidth);
        this.toolbarMargin = UIManager.getInsets("Button.toolbar.margin");
        this.toolbarSpacingInsets = UIManager.getInsets("Button.toolbar.spacingInsets");
        this.toolbarFocusWidth = FlatUIUtils.getUIFloat("Button.toolbar.focusWidth", 1.5f);
        this.arc = UIManager.getInt("Button.arc");
    }
    
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        if (FlatButtonUI.isContentAreaFilled(c) && !FlatButtonUI.isToolBarButton(c) && (!FlatButtonUI.isBorderlessButton(c) || FlatUIUtils.isPermanentFocusOwner(c)) && !FlatButtonUI.isHelpButton(c) && !FlatToggleButtonUI.isTabButton(c)) {
            super.paintBorder(c, g, x, y, width, height);
        }
        else if (FlatButtonUI.isToolBarButton(c) && this.isFocused(c)) {
            this.paintToolBarFocus(c, g, x, y, width, height);
        }
    }
    
    protected void paintToolBarFocus(final Component c, final Graphics g, int x, int y, int width, int height) {
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            final float focusWidth = UIScale.scale(this.toolbarFocusWidth);
            final float arc = UIScale.scale((float)this.getArc(c));
            final Color outlineColor = this.getOutlineColor(c);
            final Insets spacing = UIScale.scale(this.toolbarSpacingInsets);
            x += spacing.left;
            y += spacing.top;
            width -= spacing.left + spacing.right;
            height -= spacing.top + spacing.bottom;
            g2.setColor((outlineColor != null) ? outlineColor : this.getFocusColor(c));
            FlatUIUtils.paintComponentBorder(g2, x, y, width, height, 0.0f, focusWidth, arc);
        }
        finally {
            g2.dispose();
        }
    }
    
    @Override
    protected Color getFocusColor(final Component c) {
        return (this.toolbarFocusColor != null && FlatButtonUI.isToolBarButton(c)) ? this.toolbarFocusColor : (FlatButtonUI.isDefaultButton(c) ? this.defaultFocusColor : super.getFocusColor(c));
    }
    
    @Override
    protected boolean isFocused(final Component c) {
        return FlatButtonUI.isFocusPainted(c) && super.isFocused(c);
    }
    
    @Override
    protected Paint getBorderColor(final Component c) {
        final boolean def = FlatButtonUI.isDefaultButton(c);
        Paint color = FlatButtonUI.buttonStateColor(c, def ? this.defaultBorderColor : this.borderColor, this.disabledBorderColor, def ? this.defaultFocusedBorderColor : this.focusedBorderColor, def ? this.defaultHoverBorderColor : this.hoverBorderColor, null);
        final Color startBg = def ? this.defaultBorderColor : this.borderColor;
        final Color endBg = def ? this.defaultEndBorderColor : this.endBorderColor;
        if (color == startBg && endBg != null && !startBg.equals(endBg)) {
            color = new GradientPaint(0.0f, 0.0f, startBg, 0.0f, (float)c.getHeight(), endBg);
        }
        return color;
    }
    
    @Override
    public Insets getBorderInsets(final Component c, Insets insets) {
        if (FlatButtonUI.isToolBarButton(c)) {
            final Insets margin = (c instanceof AbstractButton) ? ((AbstractButton)c).getMargin() : null;
            FlatUIUtils.setInsets(insets, UIScale.scale(FlatUIUtils.addInsets(this.toolbarSpacingInsets, (margin != null && !(margin instanceof UIResource)) ? margin : this.toolbarMargin)));
        }
        else {
            insets = super.getBorderInsets(c, insets);
            if (FlatButtonUI.isIconOnlyOrSingleCharacterButton(c) && ((AbstractButton)c).getMargin() instanceof UIResource) {
                final Insets insets2 = insets;
                final Insets insets3 = insets;
                final int min = Math.min(insets.top, insets.bottom);
                insets3.right = min;
                insets2.left = min;
            }
        }
        return insets;
    }
    
    @Override
    protected int getFocusWidth(final Component c) {
        return FlatToggleButtonUI.isTabButton(c) ? 0 : super.getFocusWidth(c);
    }
    
    @Override
    protected float getInnerFocusWidth(final Component c) {
        return this.buttonInnerFocusWidth;
    }
    
    @Override
    protected int getBorderWidth(final Component c) {
        return FlatButtonUI.isDefaultButton(c) ? this.defaultBorderWidth : this.borderWidth;
    }
    
    @Override
    protected int getArc(final Component c) {
        if (this.isCellEditor(c)) {
            return 0;
        }
        switch (FlatButtonUI.getButtonType(c)) {
            case 0: {
                return 0;
            }
            case 1: {
                return 32767;
            }
            default: {
                return this.arc;
            }
        }
    }
}
