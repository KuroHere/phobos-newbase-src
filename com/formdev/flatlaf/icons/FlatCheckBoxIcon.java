// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import com.formdev.flatlaf.ui.*;
import java.awt.*;
import java.awt.geom.*;
import com.formdev.flatlaf.*;
import javax.swing.*;

public class FlatCheckBoxIcon extends FlatAbstractIcon
{
    protected final String style;
    public final int focusWidth;
    protected final Color focusColor;
    protected final int arc;
    protected final Color borderColor;
    protected final Color background;
    protected final Color selectedBorderColor;
    protected final Color selectedBackground;
    protected final Color checkmarkColor;
    protected final Color disabledBorderColor;
    protected final Color disabledBackground;
    protected final Color disabledCheckmarkColor;
    protected final Color focusedBorderColor;
    protected final Color focusedBackground;
    protected final Color selectedFocusedBorderColor;
    protected final Color selectedFocusedBackground;
    protected final Color selectedFocusedCheckmarkColor;
    protected final Color hoverBorderColor;
    protected final Color hoverBackground;
    protected final Color selectedHoverBackground;
    protected final Color pressedBackground;
    protected final Color selectedPressedBackground;
    static final int ICON_SIZE = 15;
    
    protected static Color getUIColor(final String key, final String style) {
        if (style != null) {
            final Color color = UIManager.getColor(styleKey(key, style));
            if (color != null) {
                return color;
            }
        }
        return UIManager.getColor(key);
    }
    
    protected static int getUIInt(final String key, final int defaultValue, final String style) {
        if (style != null) {
            final Object value = UIManager.get(styleKey(key, style));
            if (value instanceof Integer) {
                return (int)value;
            }
        }
        return FlatUIUtils.getUIInt(key, defaultValue);
    }
    
    private static String styleKey(final String key, final String style) {
        return key.replace(".icon.", ".icon[" + style + "].");
    }
    
    public FlatCheckBoxIcon() {
        super(15, 15, null);
        this.style = UIManager.getString("CheckBox.icon.style");
        this.focusWidth = getUIInt("CheckBox.icon.focusWidth", UIManager.getInt("Component.focusWidth"), this.style);
        this.focusColor = FlatUIUtils.getUIColor("CheckBox.icon.focusColor", UIManager.getColor("Component.focusColor"));
        this.arc = FlatUIUtils.getUIInt("CheckBox.arc", 2);
        this.borderColor = getUIColor("CheckBox.icon.borderColor", this.style);
        this.background = getUIColor("CheckBox.icon.background", this.style);
        this.selectedBorderColor = getUIColor("CheckBox.icon.selectedBorderColor", this.style);
        this.selectedBackground = getUIColor("CheckBox.icon.selectedBackground", this.style);
        this.checkmarkColor = getUIColor("CheckBox.icon.checkmarkColor", this.style);
        this.disabledBorderColor = getUIColor("CheckBox.icon.disabledBorderColor", this.style);
        this.disabledBackground = getUIColor("CheckBox.icon.disabledBackground", this.style);
        this.disabledCheckmarkColor = getUIColor("CheckBox.icon.disabledCheckmarkColor", this.style);
        this.focusedBorderColor = getUIColor("CheckBox.icon.focusedBorderColor", this.style);
        this.focusedBackground = getUIColor("CheckBox.icon.focusedBackground", this.style);
        this.selectedFocusedBorderColor = getUIColor("CheckBox.icon.selectedFocusedBorderColor", this.style);
        this.selectedFocusedBackground = getUIColor("CheckBox.icon.selectedFocusedBackground", this.style);
        this.selectedFocusedCheckmarkColor = getUIColor("CheckBox.icon.selectedFocusedCheckmarkColor", this.style);
        this.hoverBorderColor = getUIColor("CheckBox.icon.hoverBorderColor", this.style);
        this.hoverBackground = getUIColor("CheckBox.icon.hoverBackground", this.style);
        this.selectedHoverBackground = getUIColor("CheckBox.icon.selectedHoverBackground", this.style);
        this.pressedBackground = getUIColor("CheckBox.icon.pressedBackground", this.style);
        this.selectedPressedBackground = getUIColor("CheckBox.icon.selectedPressedBackground", this.style);
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g) {
        final boolean indeterminate = this.isIndeterminate(c);
        final boolean selected = indeterminate || this.isSelected(c);
        final boolean isFocused = FlatUIUtils.isPermanentFocusOwner(c);
        if (isFocused && this.focusWidth > 0 && FlatButtonUI.isFocusPainted(c)) {
            g.setColor(this.getFocusColor(c));
            this.paintFocusBorder(c, g);
        }
        g.setColor(this.getBorderColor(c, selected));
        this.paintBorder(c, g);
        final Color bg = FlatUIUtils.deriveColor(this.getBackground(c, selected), selected ? this.selectedBackground : this.background);
        if (bg.getAlpha() < 255) {
            g.setColor(selected ? this.selectedBackground : this.background);
            this.paintBackground(c, g);
        }
        g.setColor(bg);
        this.paintBackground(c, g);
        if (selected || indeterminate) {
            g.setColor(this.getCheckmarkColor(c, selected, isFocused));
            if (indeterminate) {
                this.paintIndeterminate(c, g);
            }
            else {
                this.paintCheckmark(c, g);
            }
        }
    }
    
    protected void paintFocusBorder(final Component c, final Graphics2D g) {
        final int wh = 14 + this.focusWidth * 2;
        final int arcwh = this.arc + this.focusWidth * 2;
        g.fillRoundRect(-this.focusWidth + 1, -this.focusWidth, wh, wh, arcwh, arcwh);
    }
    
    protected void paintBorder(final Component c, final Graphics2D g) {
        final int arcwh = this.arc;
        g.fillRoundRect(1, 0, 14, 14, arcwh, arcwh);
    }
    
    protected void paintBackground(final Component c, final Graphics2D g) {
        final int arcwh = this.arc - 1;
        g.fillRoundRect(2, 1, 12, 12, arcwh, arcwh);
    }
    
    protected void paintCheckmark(final Component c, final Graphics2D g) {
        final Path2D.Float path = new Path2D.Float();
        path.moveTo(4.5f, 7.5f);
        path.lineTo(6.6f, 10.0f);
        path.lineTo(11.25f, 3.5f);
        g.setStroke(new BasicStroke(1.9f, 1, 1));
        g.draw(path);
    }
    
    protected void paintIndeterminate(final Component c, final Graphics2D g) {
        g.fill(new RoundRectangle2D.Float(3.75f, 5.75f, 8.5f, 2.5f, 2.0f, 2.0f));
    }
    
    protected boolean isIndeterminate(final Component c) {
        return c instanceof JComponent && FlatClientProperties.clientPropertyEquals((JComponent)c, "JButton.selectedState", "indeterminate");
    }
    
    protected boolean isSelected(final Component c) {
        return c instanceof AbstractButton && ((AbstractButton)c).isSelected();
    }
    
    protected Color getFocusColor(final Component c) {
        return this.focusColor;
    }
    
    protected Color getBorderColor(final Component c, final boolean selected) {
        return FlatButtonUI.buttonStateColor(c, selected ? this.selectedBorderColor : this.borderColor, this.disabledBorderColor, (selected && this.selectedFocusedBorderColor != null) ? this.selectedFocusedBorderColor : this.focusedBorderColor, this.hoverBorderColor, null);
    }
    
    protected Color getBackground(final Component c, final boolean selected) {
        return FlatButtonUI.buttonStateColor(c, selected ? this.selectedBackground : this.background, this.disabledBackground, (selected && this.selectedFocusedBackground != null) ? this.selectedFocusedBackground : this.focusedBackground, (selected && this.selectedHoverBackground != null) ? this.selectedHoverBackground : this.hoverBackground, (selected && this.selectedPressedBackground != null) ? this.selectedPressedBackground : this.pressedBackground);
    }
    
    protected Color getCheckmarkColor(final Component c, final boolean selected, final boolean isFocused) {
        return c.isEnabled() ? ((selected && isFocused && this.selectedFocusedCheckmarkColor != null) ? this.selectedFocusedCheckmarkColor : this.checkmarkColor) : this.disabledCheckmarkColor;
    }
}
