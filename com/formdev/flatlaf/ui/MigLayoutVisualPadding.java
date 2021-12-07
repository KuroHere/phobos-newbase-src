// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;

public class MigLayoutVisualPadding
{
    public static String VISUAL_PADDING_PROPERTY;
    private static final FlatMigInsets ZERO;
    private static final boolean migLayoutAvailable;
    
    public static void install(final JComponent c, final Insets insets) {
        if (!MigLayoutVisualPadding.migLayoutAvailable) {
            return;
        }
        setVisualPadding(c, insets);
    }
    
    public static void install(final JComponent c) {
        if (!MigLayoutVisualPadding.migLayoutAvailable) {
            return;
        }
        install(c, c2 -> {
            final FlatBorder border = FlatUIUtils.getOutsideFlatBorder(c2);
            if (border != null) {
                final int focusWidth = border.getFocusWidth(c2);
                return new Insets(focusWidth, focusWidth, focusWidth, focusWidth);
            }
            else {
                return null;
            }
        }, new String[] { "border" });
    }
    
    public static void install(final JComponent c, final Function<JComponent, Insets> getPaddingFunction, final String... propertyNames) {
        if (!MigLayoutVisualPadding.migLayoutAvailable) {
            return;
        }
        setVisualPadding(c, getPaddingFunction.apply(c));
        c.addPropertyChangeListener(e -> {
            final String propertyName = e.getPropertyName();
            final int length = propertyNames.length;
            int i = 0;
            while (i < length) {
                final String name = propertyNames[i];
                if (name == propertyName) {
                    setVisualPadding(c, getPaddingFunction.apply(c));
                    break;
                }
                else {
                    ++i;
                }
            }
        });
    }
    
    private static void setVisualPadding(final JComponent c, final Insets visualPadding) {
        final Object oldPadding = c.getClientProperty(MigLayoutVisualPadding.VISUAL_PADDING_PROPERTY);
        if (oldPadding == null || oldPadding instanceof FlatMigInsets) {
            final FlatMigInsets flatVisualPadding = (visualPadding != null) ? new FlatMigInsets(UIScale.scale2(visualPadding.top), UIScale.scale2(visualPadding.left), UIScale.scale2(visualPadding.bottom), UIScale.scale2(visualPadding.right)) : MigLayoutVisualPadding.ZERO;
            c.putClientProperty(MigLayoutVisualPadding.VISUAL_PADDING_PROPERTY, flatVisualPadding);
        }
    }
    
    public static void uninstall(final JComponent c) {
        if (!MigLayoutVisualPadding.migLayoutAvailable) {
            return;
        }
        for (final PropertyChangeListener l : c.getPropertyChangeListeners()) {
            if (l instanceof FlatMigListener) {
                c.removePropertyChangeListener(l);
                break;
            }
        }
        if (c.getClientProperty(MigLayoutVisualPadding.VISUAL_PADDING_PROPERTY) instanceof FlatMigInsets) {
            c.putClientProperty(MigLayoutVisualPadding.VISUAL_PADDING_PROPERTY, null);
        }
    }
    
    static {
        MigLayoutVisualPadding.VISUAL_PADDING_PROPERTY = "visualPadding";
        ZERO = new FlatMigInsets(0, 0, 0, 0);
        boolean available = false;
        try {
            Class.forName("net.miginfocom.swing.MigLayout");
            available = true;
        }
        catch (ClassNotFoundException ex) {}
        migLayoutAvailable = available;
    }
    
    private static class FlatMigInsets extends Insets
    {
        FlatMigInsets(final int top, final int left, final int bottom, final int right) {
            super(top, left, bottom, right);
        }
    }
    
    private interface FlatMigListener extends PropertyChangeListener
    {
    }
}
