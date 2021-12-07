// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import java.beans.*;
import java.awt.*;

public class FlatOptionPaneUI extends BasicOptionPaneUI
{
    protected int iconMessageGap;
    protected int messagePadding;
    protected int maxCharactersPerLine;
    private int focusWidth;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatOptionPaneUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.iconMessageGap = UIManager.getInt("OptionPane.iconMessageGap");
        this.messagePadding = UIManager.getInt("OptionPane.messagePadding");
        this.maxCharactersPerLine = UIManager.getInt("OptionPane.maxCharactersPerLine");
        this.focusWidth = UIManager.getInt("Component.focusWidth");
    }
    
    @Override
    protected void installComponents() {
        super.installComponents();
        this.updateChildPanels(this.optionPane);
    }
    
    @Override
    public Dimension getMinimumOptionPaneSize() {
        return UIScale.scale(super.getMinimumOptionPaneSize());
    }
    
    @Override
    protected int getMaxCharactersPerLineCount() {
        final int max = super.getMaxCharactersPerLineCount();
        return (this.maxCharactersPerLine > 0 && max == Integer.MAX_VALUE) ? this.maxCharactersPerLine : max;
    }
    
    @Override
    protected Container createMessageArea() {
        final Container messageArea = super.createMessageArea();
        if (this.iconMessageGap > 0) {
            final Component iconMessageSeparator = this.findByName(messageArea, "OptionPane.separator");
            if (iconMessageSeparator != null) {
                iconMessageSeparator.setPreferredSize(new Dimension(UIScale.scale(this.iconMessageGap), 1));
            }
        }
        return messageArea;
    }
    
    @Override
    protected Container createButtonArea() {
        final Container buttonArea = super.createButtonArea();
        if (buttonArea.getLayout() instanceof ButtonAreaLayout) {
            final ButtonAreaLayout layout = (ButtonAreaLayout)buttonArea.getLayout();
            layout.setPadding(UIScale.scale(layout.getPadding() - this.focusWidth * 2));
        }
        return buttonArea;
    }
    
    @Override
    protected void addMessageComponents(final Container container, final GridBagConstraints cons, final Object msg, int maxll, final boolean internallyCreated) {
        if (this.messagePadding > 0) {
            cons.insets.bottom = UIScale.scale(this.messagePadding);
        }
        if (msg instanceof String && BasicHTML.isHTMLString((String)msg)) {
            maxll = Integer.MAX_VALUE;
        }
        if (msg instanceof Box) {
            final Box box = (Box)msg;
            if ("OptionPane.verticalBox".equals(box.getName()) && box.getLayout() instanceof BoxLayout && ((BoxLayout)box.getLayout()).getAxis() == 1) {
                box.addPropertyChangeListener("componentOrientation", e -> {
                    final float alignX = box.getComponentOrientation().isLeftToRight() ? 0.0f : 1.0f;
                    box.getComponents();
                    final Component[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final Component c = array[i];
                        if (c instanceof JLabel && "OptionPane.label".equals(c.getName())) {
                            ((JLabel)c).setAlignmentX(alignX);
                        }
                    }
                    return;
                });
            }
        }
        super.addMessageComponents(container, cons, msg, maxll, internallyCreated);
    }
    
    private void updateChildPanels(final Container c) {
        for (final Component child : c.getComponents()) {
            if (child.getClass() == JPanel.class) {
                final JPanel panel = (JPanel)child;
                panel.setOpaque(false);
                final Border border = panel.getBorder();
                if (border instanceof UIResource) {
                    panel.setBorder(new NonUIResourceBorder(border));
                }
            }
            if (child instanceof Container) {
                this.updateChildPanels((Container)child);
            }
        }
    }
    
    private Component findByName(final Container c, final String name) {
        for (final Component child : c.getComponents()) {
            if (name.equals(child.getName())) {
                return child;
            }
            if (child instanceof Container) {
                final Component c2 = this.findByName((Container)child, name);
                if (c2 != null) {
                    return c2;
                }
            }
        }
        return null;
    }
    
    private static class NonUIResourceBorder implements Border
    {
        private final Border delegate;
        
        NonUIResourceBorder(final Border delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            this.delegate.paintBorder(c, g, x, y, width, height);
        }
        
        @Override
        public Insets getBorderInsets(final Component c) {
            return this.delegate.getBorderInsets(c);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return this.delegate.isBorderOpaque();
        }
    }
}
