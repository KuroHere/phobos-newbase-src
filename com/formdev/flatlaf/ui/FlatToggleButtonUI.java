// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import java.util.function.*;
import java.beans.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatToggleButtonUI extends FlatButtonUI
{
    protected int tabUnderlineHeight;
    protected Color tabUnderlineColor;
    protected Color tabDisabledUnderlineColor;
    protected Color tabSelectedBackground;
    protected Color tabHoverBackground;
    protected Color tabFocusBackground;
    private boolean defaults_initialized;
    
    public FlatToggleButtonUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatToggleButtonUI.class, (Supplier<ComponentUI>)FlatToggleButtonUI::new);
    }
    
    @Override
    protected String getPropertyPrefix() {
        return "ToggleButton.";
    }
    
    @Override
    protected void installDefaults(final AbstractButton b) {
        super.installDefaults(b);
        if (!this.defaults_initialized) {
            this.tabUnderlineHeight = UIManager.getInt("ToggleButton.tab.underlineHeight");
            this.tabUnderlineColor = UIManager.getColor("ToggleButton.tab.underlineColor");
            this.tabDisabledUnderlineColor = UIManager.getColor("ToggleButton.tab.disabledUnderlineColor");
            this.tabSelectedBackground = UIManager.getColor("ToggleButton.tab.selectedBackground");
            this.tabHoverBackground = UIManager.getColor("ToggleButton.tab.hoverBackground");
            this.tabFocusBackground = UIManager.getColor("ToggleButton.tab.focusBackground");
            this.defaults_initialized = true;
        }
    }
    
    @Override
    protected void uninstallDefaults(final AbstractButton b) {
        super.uninstallDefaults(b);
        this.defaults_initialized = false;
    }
    
    @Override
    protected void propertyChange(final AbstractButton b, final PropertyChangeEvent e) {
        super.propertyChange(b, e);
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "JButton.buttonType": {
                if ("tab".equals(e.getOldValue()) || "tab".equals(e.getNewValue())) {
                    MigLayoutVisualPadding.uninstall(b);
                    MigLayoutVisualPadding.install(b);
                    b.revalidate();
                }
                b.repaint();
                break;
            }
            case "JToggleButton.tab.underlineHeight":
            case "JToggleButton.tab.underlineColor":
            case "JToggleButton.tab.selectedBackground": {
                b.repaint();
                break;
            }
        }
    }
    
    static boolean isTabButton(final Component c) {
        return c instanceof JToggleButton && FlatClientProperties.clientPropertyEquals((JComponent)c, "JButton.buttonType", "tab");
    }
    
    @Override
    protected void paintBackground(final Graphics g, final JComponent c) {
        if (isTabButton(c)) {
            final int height = c.getHeight();
            final int width = c.getWidth();
            final boolean selected = ((AbstractButton)c).isSelected();
            Color enabledColor = selected ? FlatClientProperties.clientPropertyColor(c, "JToggleButton.tab.selectedBackground", this.tabSelectedBackground) : null;
            if (enabledColor == null) {
                final Color bg = c.getBackground();
                if (this.isCustomBackground(bg)) {
                    enabledColor = bg;
                }
            }
            final Color background = FlatButtonUI.buttonStateColor(c, enabledColor, null, this.tabFocusBackground, this.tabHoverBackground, null);
            if (background != null) {
                g.setColor(background);
                g.fillRect(0, 0, width, height);
            }
            if (selected) {
                final int underlineHeight = UIScale.scale(FlatClientProperties.clientPropertyInt(c, "JToggleButton.tab.underlineHeight", this.tabUnderlineHeight));
                g.setColor(c.isEnabled() ? FlatClientProperties.clientPropertyColor(c, "JToggleButton.tab.underlineColor", this.tabUnderlineColor) : this.tabDisabledUnderlineColor);
                g.fillRect(0, height - underlineHeight, width, underlineHeight);
            }
        }
        else {
            super.paintBackground(g, c);
        }
    }
}
