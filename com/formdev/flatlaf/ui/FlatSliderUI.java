// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.formdev.flatlaf.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class FlatSliderUI extends BasicSliderUI
{
    protected int trackWidth;
    protected Dimension thumbSize;
    protected int focusWidth;
    protected Color trackValueColor;
    protected Color trackColor;
    protected Color thumbColor;
    protected Color thumbBorderColor;
    protected Color focusBaseColor;
    protected Color focusedColor;
    protected Color focusedThumbBorderColor;
    protected Color hoverThumbColor;
    protected Color pressedThumbColor;
    protected Color disabledTrackColor;
    protected Color disabledThumbColor;
    protected Color disabledThumbBorderColor;
    private Color defaultBackground;
    private Color defaultForeground;
    protected boolean thumbHover;
    protected boolean thumbPressed;
    private Object[] oldRenderingHints;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatSliderUI();
    }
    
    public FlatSliderUI() {
        super(null);
    }
    
    @Override
    protected void installDefaults(final JSlider slider) {
        super.installDefaults(slider);
        LookAndFeel.installProperty(slider, "opaque", false);
        this.trackWidth = UIManager.getInt("Slider.trackWidth");
        this.thumbSize = UIManager.getDimension("Slider.thumbSize");
        if (this.thumbSize == null) {
            final int thumbWidth = UIManager.getInt("Slider.thumbWidth");
            this.thumbSize = new Dimension(thumbWidth, thumbWidth);
        }
        this.focusWidth = FlatUIUtils.getUIInt("Slider.focusWidth", 4);
        this.trackValueColor = FlatUIUtils.getUIColor("Slider.trackValueColor", "Slider.thumbColor");
        this.trackColor = UIManager.getColor("Slider.trackColor");
        this.thumbColor = UIManager.getColor("Slider.thumbColor");
        this.thumbBorderColor = UIManager.getColor("Slider.thumbBorderColor");
        this.focusBaseColor = UIManager.getColor("Component.focusColor");
        this.focusedColor = FlatUIUtils.getUIColor("Slider.focusedColor", this.focusBaseColor);
        this.focusedThumbBorderColor = FlatUIUtils.getUIColor("Slider.focusedThumbBorderColor", "Component.focusedBorderColor");
        this.hoverThumbColor = UIManager.getColor("Slider.hoverThumbColor");
        this.pressedThumbColor = UIManager.getColor("Slider.pressedThumbColor");
        this.disabledTrackColor = UIManager.getColor("Slider.disabledTrackColor");
        this.disabledThumbColor = UIManager.getColor("Slider.disabledThumbColor");
        this.disabledThumbBorderColor = FlatUIUtils.getUIColor("Slider.disabledThumbBorderColor", "Component.disabledBorderColor");
        this.defaultBackground = UIManager.getColor("Slider.background");
        this.defaultForeground = UIManager.getColor("Slider.foreground");
    }
    
    @Override
    protected void uninstallDefaults(final JSlider slider) {
        super.uninstallDefaults(slider);
        this.trackValueColor = null;
        this.trackColor = null;
        this.thumbColor = null;
        this.thumbBorderColor = null;
        this.focusBaseColor = null;
        this.focusedColor = null;
        this.focusedThumbBorderColor = null;
        this.hoverThumbColor = null;
        this.pressedThumbColor = null;
        this.disabledTrackColor = null;
        this.disabledThumbColor = null;
        this.disabledThumbBorderColor = null;
        this.defaultBackground = null;
        this.defaultForeground = null;
    }
    
    @Override
    protected TrackListener createTrackListener(final JSlider slider) {
        return new FlatTrackListener();
    }
    
    @Override
    public int getBaseline(final JComponent c, final int width, final int height) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException();
        }
        if (this.slider.getOrientation() == 1) {
            return -1;
        }
        final FontMetrics fm = this.slider.getFontMetrics(this.slider.getFont());
        return this.trackRect.y + Math.round((this.trackRect.height - fm.getHeight()) / 2.0f) + fm.getAscent() - 1;
    }
    
    @Override
    public Dimension getPreferredHorizontalSize() {
        return UIScale.scale(super.getPreferredHorizontalSize());
    }
    
    @Override
    public Dimension getPreferredVerticalSize() {
        return UIScale.scale(super.getPreferredVerticalSize());
    }
    
    @Override
    public Dimension getMinimumHorizontalSize() {
        return UIScale.scale(super.getMinimumHorizontalSize());
    }
    
    @Override
    public Dimension getMinimumVerticalSize() {
        return UIScale.scale(super.getMinimumVerticalSize());
    }
    
    @Override
    protected int getTickLength() {
        return UIScale.scale(super.getTickLength());
    }
    
    @Override
    protected Dimension getThumbSize() {
        return calcThumbSize(this.slider, this.thumbSize, this.focusWidth);
    }
    
    public static Dimension calcThumbSize(final JSlider slider, final Dimension thumbSize, final int focusWidth) {
        final int fw = UIScale.scale(focusWidth);
        final int w = UIScale.scale(thumbSize.width) + fw + fw;
        final int h = UIScale.scale(thumbSize.height) + fw + fw;
        return (slider.getOrientation() == 0) ? new Dimension(w, h) : new Dimension(h, w);
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        this.oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        super.paint(g, c);
        FlatUIUtils.resetRenderingHints(g, this.oldRenderingHints);
        this.oldRenderingHints = null;
    }
    
    @Override
    public void paintLabels(final Graphics g) {
        FlatUIUtils.runWithoutRenderingHints(g, this.oldRenderingHints, () -> super.paintLabels(g));
    }
    
    @Override
    public void paintFocus(final Graphics g) {
    }
    
    @Override
    public void paintTrack(final Graphics g) {
        final boolean enabled = this.slider.isEnabled();
        final float arc;
        final float tw = arc = UIScale.scale((float)this.trackWidth);
        RoundRectangle2D coloredTrack = null;
        RoundRectangle2D track;
        if (this.slider.getOrientation() == 0) {
            final float y = this.trackRect.y + (this.trackRect.height - tw) / 2.0f;
            if (enabled && this.isRoundThumb()) {
                if (this.slider.getComponentOrientation().isLeftToRight()) {
                    final int cw = this.thumbRect.x + this.thumbRect.width / 2 - this.trackRect.x;
                    coloredTrack = new RoundRectangle2D.Float((float)this.trackRect.x, y, (float)cw, tw, arc, arc);
                    track = new RoundRectangle2D.Float((float)(this.trackRect.x + cw), y, (float)(this.trackRect.width - cw), tw, arc, arc);
                }
                else {
                    final int cw = this.trackRect.x + this.trackRect.width - this.thumbRect.x - this.thumbRect.width / 2;
                    coloredTrack = new RoundRectangle2D.Float((float)(this.trackRect.x + this.trackRect.width - cw), y, (float)cw, tw, arc, arc);
                    track = new RoundRectangle2D.Float((float)this.trackRect.x, y, (float)(this.trackRect.width - cw), tw, arc, arc);
                }
            }
            else {
                track = new RoundRectangle2D.Float((float)this.trackRect.x, y, (float)this.trackRect.width, tw, arc, arc);
            }
        }
        else {
            final float x = this.trackRect.x + (this.trackRect.width - tw) / 2.0f;
            if (enabled && this.isRoundThumb()) {
                final int ch = this.thumbRect.y + this.thumbRect.height / 2 - this.trackRect.y;
                track = new RoundRectangle2D.Float(x, (float)this.trackRect.y, tw, (float)ch, arc, arc);
                coloredTrack = new RoundRectangle2D.Float(x, (float)(this.trackRect.y + ch), tw, (float)(this.trackRect.height - ch), arc, arc);
            }
            else {
                track = new RoundRectangle2D.Float(x, (float)this.trackRect.y, tw, (float)this.trackRect.height, arc, arc);
            }
        }
        if (coloredTrack != null) {
            if (this.slider.getInverted()) {
                final RoundRectangle2D temp = track;
                track = coloredTrack;
                coloredTrack = temp;
            }
            g.setColor(this.getTrackValueColor());
            ((Graphics2D)g).fill(coloredTrack);
        }
        g.setColor(enabled ? this.getTrackColor() : this.disabledTrackColor);
        ((Graphics2D)g).fill(track);
    }
    
    @Override
    public void paintThumb(final Graphics g) {
        final Color thumbColor = this.getThumbColor();
        Color color = stateColor(this.slider, this.thumbHover, this.thumbPressed, thumbColor, this.disabledThumbColor, null, this.hoverThumbColor, this.pressedThumbColor);
        color = FlatUIUtils.deriveColor(color, thumbColor);
        final Color foreground = this.slider.getForeground();
        final Color borderColor = (this.thumbBorderColor != null && foreground == this.defaultForeground) ? stateColor(this.slider, false, false, this.thumbBorderColor, this.disabledThumbBorderColor, this.focusedThumbBorderColor, null, null) : null;
        final Color focusedColor = FlatUIUtils.deriveColor(this.focusedColor, (foreground != this.defaultForeground) ? foreground : this.focusBaseColor);
        paintThumb(g, this.slider, this.thumbRect, this.isRoundThumb(), color, borderColor, focusedColor, this.focusWidth);
    }
    
    public static void paintThumb(final Graphics g, final JSlider slider, final Rectangle thumbRect, final boolean roundThumb, final Color thumbColor, final Color thumbBorderColor, final Color focusedColor, final int focusWidth) {
        final double systemScaleFactor = UIScale.getSystemScaleFactor((Graphics2D)g);
        if (systemScaleFactor != 1.0 && systemScaleFactor != 2.0) {
            HiDPIUtils.paintAtScale1x((Graphics2D)g, thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, (g2d, x2, y2, width2, height2, scaleFactor) -> paintThumbImpl(g, slider, x2, y2, width2, height2, roundThumb, thumbColor, thumbBorderColor, focusedColor, (float)(focusWidth * scaleFactor)));
            return;
        }
        paintThumbImpl(g, slider, thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height, roundThumb, thumbColor, thumbBorderColor, focusedColor, (float)focusWidth);
    }
    
    private static void paintThumbImpl(final Graphics g, final JSlider slider, final int x, final int y, final int width, final int height, final boolean roundThumb, final Color thumbColor, final Color thumbBorderColor, final Color focusedColor, final float focusWidth) {
        final int fw = Math.round(UIScale.scale(focusWidth));
        final int tx = x + fw;
        final int ty = y + fw;
        int tw = width - fw - fw;
        int th = height - fw - fw;
        final boolean focused = FlatUIUtils.isPermanentFocusOwner(slider);
        if (roundThumb) {
            if (focused) {
                g.setColor(focusedColor);
                ((Graphics2D)g).fill(createRoundThumbShape((float)x, (float)y, (float)width, (float)height));
            }
            if (thumbBorderColor != null) {
                g.setColor(thumbBorderColor);
                ((Graphics2D)g).fill(createRoundThumbShape((float)tx, (float)ty, (float)tw, (float)th));
                final float lw = UIScale.scale(1.0f);
                g.setColor(thumbColor);
                ((Graphics2D)g).fill(createRoundThumbShape(tx + lw, ty + lw, tw - lw - lw, th - lw - lw));
            }
            else {
                g.setColor(thumbColor);
                ((Graphics2D)g).fill(createRoundThumbShape((float)tx, (float)ty, (float)tw, (float)th));
            }
        }
        else {
            final Graphics2D g2 = (Graphics2D)g.create();
            try {
                g2.translate(x, y);
                if (slider.getOrientation() == 1) {
                    if (slider.getComponentOrientation().isLeftToRight()) {
                        g2.translate(0, height);
                        g2.rotate(Math.toRadians(270.0));
                    }
                    else {
                        g2.translate(width, 0);
                        g2.rotate(Math.toRadians(90.0));
                    }
                    final int temp = tw;
                    tw = th;
                    th = temp;
                }
                if (focused) {
                    g2.setColor(focusedColor);
                    g2.fill(createDirectionalThumbShape(0.0f, 0.0f, (float)(tw + fw + fw), th + fw + fw + fw * 0.4142f, (float)fw));
                }
                if (thumbBorderColor != null) {
                    g2.setColor(thumbBorderColor);
                    g2.fill(createDirectionalThumbShape((float)fw, (float)fw, (float)tw, (float)th, 0.0f));
                    final float lw2 = UIScale.scale(1.0f);
                    g2.setColor(thumbColor);
                    g2.fill(createDirectionalThumbShape(fw + lw2, fw + lw2, tw - lw2 - lw2, th - lw2 - lw2 - lw2 * 0.4142f, 0.0f));
                }
                else {
                    g2.setColor(thumbColor);
                    g2.fill(createDirectionalThumbShape((float)fw, (float)fw, (float)tw, (float)th, 0.0f));
                }
            }
            finally {
                g2.dispose();
            }
        }
    }
    
    public static Shape createRoundThumbShape(final float x, final float y, final float w, final float h) {
        if (w == h) {
            return new Ellipse2D.Float(x, y, w, h);
        }
        final float arc = Math.min(w, h);
        return new RoundRectangle2D.Float(x, y, w, h, arc, arc);
    }
    
    public static Shape createDirectionalThumbShape(final float x, final float y, final float w, final float h, final float arc) {
        final float wh = w / 2.0f;
        final Path2D path = new Path2D.Float();
        path.moveTo(x + wh, y + h);
        path.lineTo(x, y + (h - wh));
        path.lineTo(x, y + arc);
        path.quadTo(x, y, x + arc, y);
        path.lineTo(x + (w - arc), y);
        path.quadTo(x + w, y, x + w, y + arc);
        path.lineTo(x + w, y + (h - wh));
        path.closePath();
        return path;
    }
    
    protected Color getTrackValueColor() {
        final Color foreground = this.slider.getForeground();
        return (foreground != this.defaultForeground) ? foreground : this.trackValueColor;
    }
    
    protected Color getTrackColor() {
        final Color backround = this.slider.getBackground();
        return (backround != this.defaultBackground) ? backround : this.trackColor;
    }
    
    protected Color getThumbColor() {
        final Color foreground = this.slider.getForeground();
        return (foreground != this.defaultForeground) ? foreground : this.thumbColor;
    }
    
    public static Color stateColor(final JSlider slider, final boolean hover, final boolean pressed, final Color enabledColor, final Color disabledColor, final Color focusedColor, final Color hoverColor, final Color pressedColor) {
        if (disabledColor != null && !slider.isEnabled()) {
            return disabledColor;
        }
        if (pressedColor != null && pressed) {
            return pressedColor;
        }
        if (hoverColor != null && hover) {
            return hoverColor;
        }
        if (focusedColor != null && FlatUIUtils.isPermanentFocusOwner(slider)) {
            return focusedColor;
        }
        return enabledColor;
    }
    
    protected boolean isRoundThumb() {
        return !this.slider.getPaintTicks() && !this.slider.getPaintLabels();
    }
    
    @Override
    public void setThumbLocation(final int x, final int y) {
        if (!this.isRoundThumb()) {
            final Rectangle r = new Rectangle(this.thumbRect);
            this.thumbRect.setLocation(x, y);
            SwingUtilities.computeUnion(this.thumbRect.x, this.thumbRect.y, this.thumbRect.width, this.thumbRect.height, r);
            final int extra = (int)Math.ceil(UIScale.scale(this.focusWidth) * 0.4142f);
            if (this.slider.getOrientation() == 0) {
                final Rectangle rectangle = r;
                rectangle.height += extra;
            }
            else {
                final Rectangle rectangle2 = r;
                rectangle2.width += extra;
                if (!this.slider.getComponentOrientation().isLeftToRight()) {
                    final Rectangle rectangle3 = r;
                    rectangle3.x -= extra;
                }
            }
            this.slider.repaint(r);
        }
        else {
            super.setThumbLocation(x, y);
        }
    }
    
    protected class FlatTrackListener extends TrackListener
    {
        @Override
        public void mouseEntered(final MouseEvent e) {
            this.setThumbHover(this.isOverThumb(e));
            super.mouseEntered(e);
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
            this.setThumbHover(false);
            super.mouseExited(e);
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
            this.setThumbHover(this.isOverThumb(e));
            super.mouseMoved(e);
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            this.setThumbPressed(this.isOverThumb(e));
            if (!FlatSliderUI.this.slider.isEnabled()) {
                return;
            }
            if (UIManager.getBoolean("Slider.scrollOnTrackClick")) {
                super.mousePressed(e);
                return;
            }
            final int x = e.getX();
            final int y = e.getY();
            BasicSliderUI.this.calculateGeometry();
            if (FlatSliderUI.this.thumbRect.contains(x, y)) {
                super.mousePressed(e);
                return;
            }
            if (UIManager.getBoolean("Slider.onlyLeftMouseButtonDrag") && !SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            final int tx = FlatSliderUI.this.thumbRect.x + FlatSliderUI.this.thumbRect.width / 2 - x;
            final int ty = FlatSliderUI.this.thumbRect.y + FlatSliderUI.this.thumbRect.height / 2 - y;
            e.translatePoint(tx, ty);
            super.mousePressed(e);
            e.translatePoint(-tx, -ty);
            this.mouseDragged(e);
            this.setThumbPressed(true);
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
            this.setThumbPressed(false);
            super.mouseReleased(e);
        }
        
        @Override
        public void mouseDragged(final MouseEvent e) {
            super.mouseDragged(e);
            if (BasicSliderUI.this.isDragging() && FlatSliderUI.this.slider.getSnapToTicks() && FlatSliderUI.this.slider.isEnabled() && !UIManager.getBoolean("Slider.snapToTicksOnReleased")) {
                BasicSliderUI.this.calculateThumbLocation();
                FlatSliderUI.this.slider.repaint();
            }
        }
        
        protected void setThumbHover(final boolean hover) {
            if (hover != FlatSliderUI.this.thumbHover) {
                FlatSliderUI.this.thumbHover = hover;
                FlatSliderUI.this.slider.repaint(FlatSliderUI.this.thumbRect);
            }
        }
        
        protected void setThumbPressed(final boolean pressed) {
            if (pressed != FlatSliderUI.this.thumbPressed) {
                FlatSliderUI.this.thumbPressed = pressed;
                FlatSliderUI.this.slider.repaint(FlatSliderUI.this.thumbRect);
            }
        }
        
        protected boolean isOverThumb(final MouseEvent e) {
            return e != null && FlatSliderUI.this.slider.isEnabled() && FlatSliderUI.this.thumbRect.contains(e.getX(), e.getY());
        }
    }
}
