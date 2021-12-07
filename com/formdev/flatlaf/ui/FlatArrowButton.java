// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.formdev.flatlaf.util.*;
import javax.swing.*;
import java.awt.*;

public class FlatArrowButton extends BasicArrowButton implements UIResource
{
    public static final int DEFAULT_ARROW_WIDTH = 8;
    protected final boolean chevron;
    protected final Color foreground;
    protected final Color disabledForeground;
    protected final Color hoverForeground;
    protected final Color hoverBackground;
    protected final Color pressedForeground;
    protected final Color pressedBackground;
    private int arrowWidth;
    private float xOffset;
    private float yOffset;
    private boolean hover;
    private boolean pressed;
    
    public FlatArrowButton(final int direction, final String type, final Color foreground, final Color disabledForeground, final Color hoverForeground, final Color hoverBackground, final Color pressedForeground, final Color pressedBackground) {
        super(direction, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        this.arrowWidth = 8;
        this.xOffset = 0.0f;
        this.yOffset = 0.0f;
        this.chevron = FlatUIUtils.isChevron(type);
        this.foreground = foreground;
        this.disabledForeground = disabledForeground;
        this.hoverForeground = hoverForeground;
        this.hoverBackground = hoverBackground;
        this.pressedForeground = pressedForeground;
        this.pressedBackground = pressedBackground;
        this.setOpaque(false);
        this.setBorder(null);
        if (hoverForeground != null || hoverBackground != null || pressedForeground != null || pressedBackground != null) {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(final MouseEvent e) {
                    FlatArrowButton.this.hover = true;
                    FlatArrowButton.this.repaint();
                }
                
                @Override
                public void mouseExited(final MouseEvent e) {
                    FlatArrowButton.this.hover = false;
                    FlatArrowButton.this.repaint();
                }
                
                @Override
                public void mousePressed(final MouseEvent e) {
                    FlatArrowButton.this.pressed = true;
                    FlatArrowButton.this.repaint();
                }
                
                @Override
                public void mouseReleased(final MouseEvent e) {
                    FlatArrowButton.this.pressed = false;
                    FlatArrowButton.this.repaint();
                }
            });
        }
    }
    
    public int getArrowWidth() {
        return this.arrowWidth;
    }
    
    public void setArrowWidth(final int arrowWidth) {
        this.arrowWidth = arrowWidth;
    }
    
    protected boolean isHover() {
        return this.hover;
    }
    
    protected boolean isPressed() {
        return this.pressed;
    }
    
    public float getXOffset() {
        return this.xOffset;
    }
    
    public void setXOffset(final float xOffset) {
        this.xOffset = xOffset;
    }
    
    public float getYOffset() {
        return this.yOffset;
    }
    
    public void setYOffset(final float yOffset) {
        this.yOffset = yOffset;
    }
    
    protected Color deriveBackground(final Color background) {
        return background;
    }
    
    protected Color deriveForeground(final Color foreground) {
        return FlatUIUtils.deriveColor(foreground, this.foreground);
    }
    
    protected Color getArrowColor() {
        return this.isEnabled() ? ((this.pressedForeground != null && this.isPressed()) ? this.pressedForeground : ((this.hoverForeground != null && this.isHover()) ? this.hoverForeground : this.foreground)) : this.disabledForeground;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return UIScale.scale(super.getPreferredSize());
    }
    
    @Override
    public Dimension getMinimumSize() {
        return UIScale.scale(super.getMinimumSize());
    }
    
    @Override
    public void paint(final Graphics g) {
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        if (this.isEnabled()) {
            final Color background = (this.pressedBackground != null && this.isPressed()) ? this.pressedBackground : ((this.hoverBackground != null && this.isHover()) ? this.hoverBackground : null);
            if (background != null) {
                g.setColor(this.deriveBackground(background));
                this.paintBackground((Graphics2D)g);
            }
        }
        g.setColor(this.deriveForeground(this.getArrowColor()));
        this.paintArrow((Graphics2D)g);
        FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
    }
    
    protected void paintBackground(final Graphics2D g) {
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    protected void paintArrow(final Graphics2D g) {
        final boolean vert = this.direction == 1 || this.direction == 5;
        int x = 0;
        final Container parent = this.getParent();
        if (vert && parent instanceof JComponent && FlatUIUtils.hasRoundBorder((JComponent)parent)) {
            x -= UIScale.scale(parent.getComponentOrientation().isLeftToRight() ? 1 : -1);
        }
        FlatUIUtils.paintArrow(g, x, 0, this.getWidth(), this.getHeight(), this.getDirection(), this.chevron, this.arrowWidth, this.xOffset, this.yOffset);
    }
}
