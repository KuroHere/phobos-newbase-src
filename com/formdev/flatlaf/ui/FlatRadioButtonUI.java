// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.util.function.*;
import com.formdev.flatlaf.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.icons.*;

public class FlatRadioButtonUI extends BasicRadioButtonUI
{
    protected int iconTextGap;
    protected Color disabledText;
    private Color defaultBackground;
    private boolean defaults_initialized;
    private static Insets tempInsets;
    
    public FlatRadioButtonUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatRadioButtonUI.class, (Supplier<ComponentUI>)FlatRadioButtonUI::new);
    }
    
    public void installDefaults(final AbstractButton b) {
        super.installDefaults(b);
        if (!this.defaults_initialized) {
            final String prefix = this.getPropertyPrefix();
            this.iconTextGap = FlatUIUtils.getUIInt(prefix + "iconTextGap", 4);
            this.disabledText = UIManager.getColor(prefix + "disabledText");
            this.defaultBackground = UIManager.getColor(prefix + "background");
            this.defaults_initialized = true;
        }
        LookAndFeel.installProperty(b, "opaque", false);
        LookAndFeel.installProperty(b, "iconTextGap", UIScale.scale(this.iconTextGap));
        MigLayoutVisualPadding.install(b, null);
    }
    
    @Override
    protected void uninstallDefaults(final AbstractButton b) {
        super.uninstallDefaults(b);
        MigLayoutVisualPadding.uninstall(b);
        this.defaults_initialized = false;
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final Dimension size = super.getPreferredSize(c);
        if (size == null) {
            return null;
        }
        final int focusWidth = this.getIconFocusWidth(c);
        if (focusWidth > 0) {
            final Insets insets = c.getInsets(FlatRadioButtonUI.tempInsets);
            final Dimension dimension = size;
            dimension.width += Math.max(focusWidth - insets.left, 0) + Math.max(focusWidth - insets.right, 0);
            final Dimension dimension2 = size;
            dimension2.height += Math.max(focusWidth - insets.top, 0) + Math.max(focusWidth - insets.bottom, 0);
        }
        return size;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (!c.isOpaque() && ((AbstractButton)c).isContentAreaFilled() && !Objects.equals(c.getBackground(), this.getDefaultBackground(c))) {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        final int focusWidth = this.getIconFocusWidth(c);
        if (focusWidth > 0) {
            final boolean ltr = c.getComponentOrientation().isLeftToRight();
            final Insets insets = c.getInsets(FlatRadioButtonUI.tempInsets);
            final int leftOrRightInset = ltr ? insets.left : insets.right;
            if (focusWidth > leftOrRightInset) {
                int offset = focusWidth - leftOrRightInset;
                if (!ltr) {
                    offset = -offset;
                }
                g.translate(offset, 0);
                super.paint(g, c);
                g.translate(-offset, 0);
                return;
            }
        }
        super.paint(FlatLabelUI.createGraphicsHTMLTextYCorrection(g, c), c);
    }
    
    @Override
    protected void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text) {
        FlatButtonUI.paintText(g, b, textRect, text, b.isEnabled() ? b.getForeground() : this.disabledText);
    }
    
    private Color getDefaultBackground(final JComponent c) {
        final Container parent = c.getParent();
        return (parent instanceof CellRendererPane && parent.getParent() != null) ? parent.getParent().getBackground() : this.defaultBackground;
    }
    
    private int getIconFocusWidth(final JComponent c) {
        final AbstractButton b = (AbstractButton)c;
        return (b.getIcon() == null && this.getDefaultIcon() instanceof FlatCheckBoxIcon) ? UIScale.scale(((FlatCheckBoxIcon)this.getDefaultIcon()).focusWidth) : 0;
    }
    
    static {
        FlatRadioButtonUI.tempInsets = new Insets(0, 0, 0, 0);
    }
}
