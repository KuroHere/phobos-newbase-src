// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.icons;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.ui.*;

public abstract class FlatInternalFrameAbstractIcon extends FlatAbstractIcon
{
    private final Color hoverBackground;
    private final Color pressedBackground;
    
    public FlatInternalFrameAbstractIcon() {
        this(UIManager.getDimension("InternalFrame.buttonSize"), UIManager.getColor("InternalFrame.buttonHoverBackground"), UIManager.getColor("InternalFrame.buttonPressedBackground"));
    }
    
    public FlatInternalFrameAbstractIcon(final Dimension size, final Color hoverBackground, final Color pressedBackground) {
        super(size.width, size.height, null);
        this.hoverBackground = hoverBackground;
        this.pressedBackground = pressedBackground;
    }
    
    protected void paintBackground(final Component c, final Graphics2D g) {
        final Color background = FlatButtonUI.buttonStateColor(c, null, null, null, this.hoverBackground, this.pressedBackground);
        if (background != null) {
            g.setColor(FlatUIUtils.deriveColor(background, c.getBackground()));
            g.fillRect(0, 0, this.width, this.height);
        }
    }
}
