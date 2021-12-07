// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class FlatPasswordFieldUI extends FlatTextFieldUI
{
    protected boolean showCapsLock;
    protected Icon capsLockIcon;
    private KeyListener capsLockListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatPasswordFieldUI();
    }
    
    @Override
    protected String getPropertyPrefix() {
        return "PasswordField";
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        final String prefix = this.getPropertyPrefix();
        final Character echoChar = (Character)UIManager.get(prefix + ".echoChar");
        if (echoChar != null) {
            LookAndFeel.installProperty(this.getComponent(), "echoChar", echoChar);
        }
        this.showCapsLock = UIManager.getBoolean("PasswordField.showCapsLock");
        this.capsLockIcon = UIManager.getIcon("PasswordField.capsLockIcon");
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.capsLockIcon = null;
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.capsLockListener = new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                this.repaint(e);
            }
            
            @Override
            public void keyReleased(final KeyEvent e) {
                this.repaint(e);
            }
            
            private void repaint(final KeyEvent e) {
                if (e.getKeyCode() == 20) {
                    e.getComponent().repaint();
                    FlatPasswordFieldUI.this.scrollCaretToVisible();
                }
            }
        };
        this.getComponent().addKeyListener(this.capsLockListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.getComponent().removeKeyListener(this.capsLockListener);
        this.capsLockListener = null;
    }
    
    @Override
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        final ActionMap map = SwingUtilities.getUIActionMap(this.getComponent());
        if (map != null && map.get("select-word") != null) {
            final Action selectLineAction = map.get("select-line");
            if (selectLineAction != null) {
                map.put("select-word", selectLineAction);
            }
        }
    }
    
    @Override
    public View create(final Element elem) {
        return new PasswordView(elem);
    }
    
    @Override
    protected void paintSafely(final Graphics g) {
        final Shape oldClip = g.getClip();
        super.paintSafely(g);
        g.setClip(oldClip);
        this.paintCapsLock(g);
    }
    
    protected void paintCapsLock(final Graphics g) {
        if (!this.isCapsLockVisible()) {
            return;
        }
        final JTextComponent c = this.getComponent();
        final int y = (c.getHeight() - this.capsLockIcon.getIconHeight()) / 2;
        final int x = c.getComponentOrientation().isLeftToRight() ? (c.getWidth() - this.capsLockIcon.getIconWidth() - y) : y;
        this.capsLockIcon.paintIcon(c, g, x, y);
    }
    
    protected boolean isCapsLockVisible() {
        if (!this.showCapsLock) {
            return false;
        }
        final JTextComponent c = this.getComponent();
        return FlatUIUtils.isPermanentFocusOwner(c) && Toolkit.getDefaultToolkit().getLockingKeyState(20);
    }
    
    @Override
    protected Insets getPadding() {
        final Insets padding = super.getPadding();
        if (!this.isCapsLockVisible()) {
            return padding;
        }
        final boolean ltr = this.getComponent().getComponentOrientation().isLeftToRight();
        final int iconWidth = this.capsLockIcon.getIconWidth();
        return FlatUIUtils.addInsets(padding, new Insets(0, ltr ? 0 : iconWidth, 0, ltr ? iconWidth : 0));
    }
}
