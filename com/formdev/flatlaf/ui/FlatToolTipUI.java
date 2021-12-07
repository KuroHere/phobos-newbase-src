// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.util.function.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;

public class FlatToolTipUI extends BasicToolTipUI
{
    private static PropertyChangeListener sharedPropertyChangedListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatToolTipUI.class, (Supplier<ComponentUI>)FlatToolTipUI::new);
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        FlatLabelUI.updateHTMLRenderer(c, ((JToolTip)c).getTipText(), false);
    }
    
    @Override
    protected void installListeners(final JComponent c) {
        super.installListeners(c);
        if (FlatToolTipUI.sharedPropertyChangedListener == null) {
            FlatToolTipUI.sharedPropertyChangedListener = (e -> {
                final String name = e.getPropertyName();
                if (name == "tiptext" || name == "font" || name == "foreground") {
                    final JToolTip toolTip = (JToolTip)e.getSource();
                    FlatLabelUI.updateHTMLRenderer(toolTip, toolTip.getTipText(), false);
                }
                return;
            });
        }
        c.addPropertyChangeListener(FlatToolTipUI.sharedPropertyChangedListener);
    }
    
    @Override
    protected void uninstallListeners(final JComponent c) {
        super.uninstallListeners(c);
        c.removePropertyChangeListener(FlatToolTipUI.sharedPropertyChangedListener);
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final String text = ((JToolTip)c).getTipText();
        if (text == null || text.isEmpty()) {
            return new Dimension();
        }
        if (this.isMultiLine(c)) {
            final FontMetrics fm = c.getFontMetrics(c.getFont());
            final Insets insets = c.getInsets();
            final List<String> lines = StringUtils.split(((JToolTip)c).getTipText(), '\n');
            int width = 0;
            final int height = fm.getHeight() * Math.max(lines.size(), 1);
            for (final String line : lines) {
                width = Math.max(width, SwingUtilities.computeStringWidth(fm, line));
            }
            return new Dimension(insets.left + width + insets.right + 6, insets.top + height + insets.bottom);
        }
        return super.getPreferredSize(c);
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (this.isMultiLine(c)) {
            final FontMetrics fm = c.getFontMetrics(c.getFont());
            final Insets insets = c.getInsets();
            g.setColor(c.getForeground());
            final List<String> lines = StringUtils.split(((JToolTip)c).getTipText(), '\n');
            final int x = insets.left + 3;
            final int x2 = c.getWidth() - insets.right - 3;
            int y = insets.top - fm.getDescent();
            final int lineHeight = fm.getHeight();
            final JComponent comp = ((JToolTip)c).getComponent();
            final boolean leftToRight = ((comp != null) ? comp : c).getComponentOrientation().isLeftToRight();
            for (final String line : lines) {
                y += lineHeight;
                FlatUIUtils.drawString(c, g, line, leftToRight ? x : (x2 - SwingUtilities.computeStringWidth(fm, line)), y);
            }
        }
        else {
            super.paint(HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g), c);
        }
    }
    
    private boolean isMultiLine(final JComponent c) {
        final String text = ((JToolTip)c).getTipText();
        return c.getClientProperty("html") == null && text != null && text.indexOf(10) >= 0;
    }
}
