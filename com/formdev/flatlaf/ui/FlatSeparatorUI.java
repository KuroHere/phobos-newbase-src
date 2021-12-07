// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.util.function.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import java.awt.*;

public class FlatSeparatorUI extends BasicSeparatorUI
{
    protected int height;
    protected int stripeWidth;
    protected int stripeIndent;
    private boolean defaults_initialized;
    
    public FlatSeparatorUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatSeparatorUI.class, (Supplier<ComponentUI>)FlatSeparatorUI::new);
    }
    
    @Override
    protected void installDefaults(final JSeparator s) {
        super.installDefaults(s);
        if (!this.defaults_initialized) {
            final String prefix = this.getPropertyPrefix();
            this.height = UIManager.getInt(prefix + ".height");
            this.stripeWidth = UIManager.getInt(prefix + ".stripeWidth");
            this.stripeIndent = UIManager.getInt(prefix + ".stripeIndent");
            this.defaults_initialized = true;
        }
    }
    
    @Override
    protected void uninstallDefaults(final JSeparator s) {
        super.uninstallDefaults(s);
        this.defaults_initialized = false;
    }
    
    protected String getPropertyPrefix() {
        return "Separator";
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            g2.setColor(c.getForeground());
            final float width = UIScale.scale((float)this.stripeWidth);
            final float indent = UIScale.scale((float)this.stripeIndent);
            if (((JSeparator)c).getOrientation() == 1) {
                g2.fill(new Rectangle2D.Float(indent, 0.0f, width, (float)c.getHeight()));
            }
            else {
                g2.fill(new Rectangle2D.Float(0.0f, indent, (float)c.getWidth(), width));
            }
        }
        finally {
            g2.dispose();
        }
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        if (((JSeparator)c).getOrientation() == 1) {
            return new Dimension(UIScale.scale(this.height), 0);
        }
        return new Dimension(0, UIScale.scale(this.height));
    }
}
