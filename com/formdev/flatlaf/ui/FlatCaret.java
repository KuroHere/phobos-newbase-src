// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class FlatCaret extends DefaultCaret implements UIResource
{
    private final String selectAllOnFocusPolicy;
    private final boolean selectAllOnMouseClick;
    private boolean wasFocused;
    private boolean wasTemporaryLost;
    private boolean isMousePressed;
    
    public FlatCaret(final String selectAllOnFocusPolicy, final boolean selectAllOnMouseClick) {
        this.selectAllOnFocusPolicy = selectAllOnFocusPolicy;
        this.selectAllOnMouseClick = selectAllOnMouseClick;
    }
    
    @Override
    public void install(final JTextComponent c) {
        super.install(c);
        final Document doc = c.getDocument();
        if (doc != null && this.getDot() == 0 && this.getMark() == 0) {
            final int length = doc.getLength();
            if (length > 0) {
                this.setDot(length);
            }
        }
    }
    
    @Override
    protected void adjustVisibility(final Rectangle nloc) {
        final JTextComponent c = this.getComponent();
        if (c != null && c.getUI() instanceof FlatTextFieldUI) {
            final Insets padding = ((FlatTextFieldUI)c.getUI()).getPadding();
            if (padding != null) {
                nloc.x -= padding.left;
                nloc.y -= padding.top;
            }
        }
        super.adjustVisibility(nloc);
    }
    
    @Override
    public void focusGained(final FocusEvent e) {
        if (!this.wasTemporaryLost && (!this.isMousePressed || this.selectAllOnMouseClick)) {
            this.selectAllOnFocusGained();
        }
        this.wasTemporaryLost = false;
        this.wasFocused = true;
        super.focusGained(e);
    }
    
    @Override
    public void focusLost(final FocusEvent e) {
        this.wasTemporaryLost = e.isTemporary();
        super.focusLost(e);
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        this.isMousePressed = true;
        super.mousePressed(e);
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        this.isMousePressed = false;
        super.mouseReleased(e);
    }
    
    protected void selectAllOnFocusGained() {
        final JTextComponent c = this.getComponent();
        final Document doc = c.getDocument();
        if (doc == null || !c.isEnabled() || !c.isEditable()) {
            return;
        }
        Object selectAllOnFocusPolicy = c.getClientProperty("JTextField.selectAllOnFocusPolicy");
        if (selectAllOnFocusPolicy == null) {
            selectAllOnFocusPolicy = this.selectAllOnFocusPolicy;
        }
        if ("never".equals(selectAllOnFocusPolicy)) {
            return;
        }
        if (!"always".equals(selectAllOnFocusPolicy)) {
            if (this.wasFocused) {
                return;
            }
            final int dot = this.getDot();
            final int mark = this.getMark();
            if (dot != mark || dot != doc.getLength()) {
                return;
            }
        }
        if (c instanceof JFormattedTextField) {
            EventQueue.invokeLater(() -> {
                this.setDot(0);
                this.moveDot(doc.getLength());
            });
        }
        else {
            this.setDot(0);
            this.moveDot(doc.getLength());
        }
    }
    
    public void scrollCaretToVisible() {
        final JTextComponent c = this.getComponent();
        if (c == null || c.getUI() == null) {
            return;
        }
        try {
            final Rectangle loc = c.getUI().modelToView(c, this.getDot(), this.getDotBias());
            if (loc != null) {
                this.adjustVisibility(loc);
                this.damage(loc);
            }
        }
        catch (BadLocationException ex) {}
    }
}
