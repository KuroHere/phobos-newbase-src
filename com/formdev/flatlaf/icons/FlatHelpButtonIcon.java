// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import com.formdev.flatlaf.ui.*;
import java.awt.*;
import java.awt.geom.*;
import com.formdev.flatlaf.util.*;

public class FlatHelpButtonIcon extends FlatAbstractIcon
{
    protected final int focusWidth;
    protected final Color focusColor;
    protected final float innerFocusWidth;
    protected final int borderWidth;
    protected final Color borderColor;
    protected final Color disabledBorderColor;
    protected final Color focusedBorderColor;
    protected final Color hoverBorderColor;
    protected final Color background;
    protected final Color disabledBackground;
    protected final Color focusedBackground;
    protected final Color hoverBackground;
    protected final Color pressedBackground;
    protected final Color questionMarkColor;
    protected final Color disabledQuestionMarkColor;
    protected final int iconSize;
    
    public FlatHelpButtonIcon() {
        super(0, 0, null);
        this.focusWidth = UIManager.getInt("Component.focusWidth");
        this.focusColor = UIManager.getColor("Component.focusColor");
        this.innerFocusWidth = FlatUIUtils.getUIFloat("HelpButton.innerFocusWidth", FlatUIUtils.getUIFloat("Component.innerFocusWidth", 0.0f));
        this.borderWidth = FlatUIUtils.getUIInt("HelpButton.borderWidth", 1);
        this.borderColor = UIManager.getColor("HelpButton.borderColor");
        this.disabledBorderColor = UIManager.getColor("HelpButton.disabledBorderColor");
        this.focusedBorderColor = UIManager.getColor("HelpButton.focusedBorderColor");
        this.hoverBorderColor = UIManager.getColor("HelpButton.hoverBorderColor");
        this.background = UIManager.getColor("HelpButton.background");
        this.disabledBackground = UIManager.getColor("HelpButton.disabledBackground");
        this.focusedBackground = UIManager.getColor("HelpButton.focusedBackground");
        this.hoverBackground = UIManager.getColor("HelpButton.hoverBackground");
        this.pressedBackground = UIManager.getColor("HelpButton.pressedBackground");
        this.questionMarkColor = UIManager.getColor("HelpButton.questionMarkColor");
        this.disabledQuestionMarkColor = UIManager.getColor("HelpButton.disabledQuestionMarkColor");
        this.iconSize = 22 + this.focusWidth * 2;
    }
    
    @Override
    protected void paintIcon(final Component c, final Graphics2D g2) {
        final boolean enabled = c.isEnabled();
        final boolean focused = FlatUIUtils.isPermanentFocusOwner(c);
        float xy = 0.5f;
        float wh = (float)(this.iconSize - 1);
        if (focused && FlatButtonUI.isFocusPainted(c)) {
            g2.setColor(this.focusColor);
            g2.fill(new Ellipse2D.Float(xy, xy, wh, wh));
        }
        xy += this.focusWidth;
        wh -= this.focusWidth * 2;
        g2.setColor(FlatButtonUI.buttonStateColor(c, this.borderColor, this.disabledBorderColor, this.focusedBorderColor, this.hoverBorderColor, null));
        g2.fill(new Ellipse2D.Float(xy, xy, wh, wh));
        xy += this.borderWidth;
        wh -= this.borderWidth * 2;
        if (this.innerFocusWidth > 0.0f && focused && FlatButtonUI.isFocusPainted(c)) {
            g2.setColor(this.focusColor);
            g2.fill(new Ellipse2D.Float(xy, xy, wh, wh));
            xy += this.innerFocusWidth;
            wh -= this.innerFocusWidth * 2.0f;
        }
        g2.setColor(FlatUIUtils.deriveColor(FlatButtonUI.buttonStateColor(c, this.background, this.disabledBackground, this.focusedBackground, this.hoverBackground, this.pressedBackground), this.background));
        g2.fill(new Ellipse2D.Float(xy, xy, wh, wh));
        final Path2D q = new Path2D.Float();
        q.moveTo(11.0, 5.0);
        q.curveTo(8.8, 5.0, 7.0, 6.8, 7.0, 9.0);
        q.lineTo(9.0, 9.0);
        q.curveTo(9.0, 7.9, 9.9, 7.0, 11.0, 7.0);
        q.curveTo(12.1, 7.0, 13.0, 7.9, 13.0, 9.0);
        q.curveTo(13.0, 11.0, 10.0, 10.75, 10.0, 14.0);
        q.lineTo(12.0, 14.0);
        q.curveTo(12.0, 11.75, 15.0, 11.5, 15.0, 9.0);
        q.curveTo(15.0, 6.8, 13.2, 5.0, 11.0, 5.0);
        q.closePath();
        g2.translate(this.focusWidth, this.focusWidth);
        g2.setColor(enabled ? this.questionMarkColor : this.disabledQuestionMarkColor);
        g2.fill(q);
        g2.fillRect(10, 15, 2, 2);
    }
    
    @Override
    public int getIconWidth() {
        return UIScale.scale(this.iconSize);
    }
    
    @Override
    public int getIconHeight() {
        return UIScale.scale(this.iconSize);
    }
}
