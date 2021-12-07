// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;

public class FlatRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI
{
    private FlatMenuItemRenderer renderer;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatRadioButtonMenuItemUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.menuItem, "iconTextGap", FlatUIUtils.getUIInt("MenuItem.iconTextGap", 4));
        this.renderer = this.createRenderer();
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.renderer = null;
    }
    
    protected FlatMenuItemRenderer createRenderer() {
        return new FlatMenuItemRenderer(this.menuItem, this.checkIcon, this.arrowIcon, this.acceleratorFont, this.acceleratorDelimiter);
    }
    
    @Override
    protected Dimension getPreferredMenuItemSize(final JComponent c, final Icon checkIcon, final Icon arrowIcon, final int defaultTextIconGap) {
        return this.renderer.getPreferredMenuItemSize();
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        this.renderer.paintMenuItem(g, this.selectionBackground, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
    }
}
