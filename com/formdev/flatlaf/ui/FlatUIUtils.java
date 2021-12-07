// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.util.*;
import com.formdev.flatlaf.*;
import javax.swing.plaf.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import java.util.function.*;
import java.awt.event.*;

public class FlatUIUtils
{
    public static final boolean MAC_USE_QUARTZ;
    private static WeakHashMap<LookAndFeel, IdentityHashMap<Object, ComponentUI>> sharedUIinstances;
    
    public static Rectangle addInsets(final Rectangle r, final Insets insets) {
        return new Rectangle(r.x - insets.left, r.y - insets.top, r.width + insets.left + insets.right, r.height + insets.top + insets.bottom);
    }
    
    public static Rectangle subtractInsets(final Rectangle r, final Insets insets) {
        return new Rectangle(r.x + insets.left, r.y + insets.top, r.width - insets.left - insets.right, r.height - insets.top - insets.bottom);
    }
    
    public static Dimension addInsets(final Dimension dim, final Insets insets) {
        return new Dimension(dim.width + insets.left + insets.right, dim.height + insets.top + insets.bottom);
    }
    
    public static Insets addInsets(final Insets insets1, final Insets insets2) {
        if (insets1 == null) {
            return insets2;
        }
        if (insets2 == null) {
            return insets1;
        }
        return new Insets(insets1.top + insets2.top, insets1.left + insets2.left, insets1.bottom + insets2.bottom, insets1.right + insets2.right);
    }
    
    public static void setInsets(final Insets dest, final Insets src) {
        dest.top = src.top;
        dest.left = src.left;
        dest.bottom = src.bottom;
        dest.right = src.right;
    }
    
    public static Color getUIColor(final String key, final int defaultColorRGB) {
        final Color color = UIManager.getColor(key);
        return (color != null) ? color : new Color(defaultColorRGB);
    }
    
    public static Color getUIColor(final String key, final Color defaultColor) {
        final Color color = UIManager.getColor(key);
        return (color != null) ? color : defaultColor;
    }
    
    public static Color getUIColor(final String key, final String defaultKey) {
        final Color color = UIManager.getColor(key);
        return (color != null) ? color : UIManager.getColor(defaultKey);
    }
    
    public static boolean getUIBoolean(final String key, final boolean defaultValue) {
        final Object value = UIManager.get(key);
        return (boolean)((value instanceof Boolean) ? value : defaultValue);
    }
    
    public static int getUIInt(final String key, final int defaultValue) {
        final Object value = UIManager.get(key);
        return (int)((value instanceof Integer) ? value : defaultValue);
    }
    
    public static float getUIFloat(final String key, final float defaultValue) {
        final Object value = UIManager.get(key);
        return (value instanceof Number) ? ((Number)value).floatValue() : defaultValue;
    }
    
    public static boolean getBoolean(final JComponent c, final String systemPropertyKey, final String clientPropertyKey, final String uiKey, final boolean defaultValue) {
        Boolean value = FlatSystemProperties.getBooleanStrict(systemPropertyKey, null);
        if (value != null) {
            return value;
        }
        value = FlatClientProperties.clientPropertyBooleanStrict(c, clientPropertyKey, null);
        if (value != null) {
            return value;
        }
        return getUIBoolean(uiKey, defaultValue);
    }
    
    public static boolean isChevron(final String arrowType) {
        return !"triangle".equals(arrowType);
    }
    
    public static Color nonUIResource(final Color c) {
        return (c instanceof UIResource) ? new Color(c.getRGB(), true) : c;
    }
    
    public static Font nonUIResource(final Font font) {
        return (font instanceof UIResource) ? font.deriveFont(font.getStyle()) : font;
    }
    
    public static int minimumWidth(final JComponent c, final int minimumWidth) {
        return FlatClientProperties.clientPropertyInt(c, "JComponent.minimumWidth", minimumWidth);
    }
    
    public static int minimumHeight(final JComponent c, final int minimumHeight) {
        return FlatClientProperties.clientPropertyInt(c, "JComponent.minimumHeight", minimumHeight);
    }
    
    public static boolean isCellEditor(final Component c) {
        Component c2 = c;
        Container parent;
        for (int i = 0; i <= 2 && c2 != null; c2 = parent, ++i) {
            parent = c2.getParent();
            if (parent instanceof JTable && ((JTable)parent).getEditorComponent() == c2) {
                return true;
            }
        }
        final String name = c.getName();
        return "Table.editor".equals(name) || "Tree.cellEditor".equals(name) || (c instanceof JComponent && Boolean.TRUE.equals(((JComponent)c).getClientProperty("JComboBox.isTableCellEditor")));
    }
    
    public static boolean isPermanentFocusOwner(final Component c) {
        final KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        if (c instanceof JComponent) {
            final Object value = ((JComponent)c).getClientProperty("JComponent.focusOwner");
            if (value instanceof Predicate) {
                return ((Predicate)value).test(c) && isInActiveWindow(c, keyboardFocusManager.getActiveWindow());
            }
        }
        return keyboardFocusManager.getPermanentFocusOwner() == c && isInActiveWindow(c, keyboardFocusManager.getActiveWindow());
    }
    
    private static boolean isInActiveWindow(final Component c, final Window activeWindow) {
        final Window window = SwingUtilities.windowForComponent(c);
        return window == activeWindow || (window != null && window.getType() == Window.Type.POPUP && window.getOwner() == activeWindow);
    }
    
    public static boolean isFullScreen(final Component c) {
        final GraphicsConfiguration gc = c.getGraphicsConfiguration();
        final GraphicsDevice gd = (gc != null) ? gc.getDevice() : null;
        final Window fullScreenWindow = (gd != null) ? gd.getFullScreenWindow() : null;
        return fullScreenWindow != null && fullScreenWindow == SwingUtilities.windowForComponent(c);
    }
    
    public static Boolean isRoundRect(final Component c) {
        return (c instanceof JComponent) ? FlatClientProperties.clientPropertyBooleanStrict((JComponent)c, "JComponent.roundRect", null) : null;
    }
    
    public static float getBorderFocusWidth(final JComponent c) {
        final FlatBorder border = getOutsideFlatBorder(c);
        return (border != null) ? UIScale.scale((float)border.getFocusWidth(c)) : 0.0f;
    }
    
    public static float getBorderArc(final JComponent c) {
        final FlatBorder border = getOutsideFlatBorder(c);
        return (border != null) ? UIScale.scale((float)border.getArc(c)) : 0.0f;
    }
    
    public static boolean hasRoundBorder(final JComponent c) {
        return getBorderArc(c) >= c.getHeight();
    }
    
    public static FlatBorder getOutsideFlatBorder(final JComponent c) {
        Border border;
        for (border = c.getBorder(); !(border instanceof FlatBorder); border = ((CompoundBorder)border).getOutsideBorder()) {
            if (!(border instanceof CompoundBorder)) {
                return null;
            }
        }
        return (FlatBorder)border;
    }
    
    public static Object[] setRenderingHints(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g;
        final Object[] oldRenderingHints = { g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING), g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL) };
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, FlatUIUtils.MAC_USE_QUARTZ ? RenderingHints.VALUE_STROKE_PURE : RenderingHints.VALUE_STROKE_NORMALIZE);
        return oldRenderingHints;
    }
    
    public static void resetRenderingHints(final Graphics g, final Object[] oldRenderingHints) {
        final Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldRenderingHints[0]);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, oldRenderingHints[1]);
    }
    
    public static void runWithoutRenderingHints(final Graphics g, final Object[] oldRenderingHints, final Runnable runnable) {
        if (oldRenderingHints == null) {
            runnable.run();
            return;
        }
        final Graphics2D g2 = (Graphics2D)g;
        final Object[] oldRenderingHints2 = { g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING), g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL) };
        resetRenderingHints(g2, oldRenderingHints);
        runnable.run();
        resetRenderingHints(g2, oldRenderingHints2);
    }
    
    public static Color deriveColor(final Color color, final Color baseColor) {
        return (color instanceof DerivedColor) ? ((DerivedColor)color).derive(baseColor) : color;
    }
    
    public static void paintComponentOuterBorder(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float lineWidth, final float arc) {
        if (focusWidth + lineWidth == 0.0f) {
            return;
        }
        final double systemScaleFactor = UIScale.getSystemScaleFactor(g);
        if (systemScaleFactor != 1.0 && systemScaleFactor != 2.0) {
            HiDPIUtils.paintAtScale1x(g, x, y, width, height, (g2d, x2, y2, width2, height2, scaleFactor) -> paintComponentOuterBorderImpl(g2d, x2, y2, width2, height2, (float)(focusWidth * scaleFactor), (float)(lineWidth * scaleFactor), (float)(arc * scaleFactor)));
            return;
        }
        paintComponentOuterBorderImpl(g, x, y, width, height, focusWidth, lineWidth, arc);
    }
    
    private static void paintComponentOuterBorderImpl(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float lineWidth, final float arc) {
        final float ow = focusWidth + lineWidth;
        float outerArc = arc + focusWidth * 2.0f;
        final float innerArc = arc - lineWidth * 2.0f;
        if (focusWidth > 0.0f && arc > 0.0f && arc < UIScale.scale(10)) {
            outerArc -= UIScale.scale(2.0f);
        }
        final Path2D path = new Path2D.Float(0);
        path.append(createComponentRectangle((float)x, (float)y, (float)width, (float)height, outerArc), false);
        path.append(createComponentRectangle(x + ow, y + ow, width - ow * 2.0f, height - ow * 2.0f, innerArc), false);
        g.fill(path);
    }
    
    public static void paintComponentBorder(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float lineWidth, final float arc) {
        if (lineWidth == 0.0f) {
            return;
        }
        final double systemScaleFactor = UIScale.getSystemScaleFactor(g);
        if (systemScaleFactor != 1.0 && systemScaleFactor != 2.0) {
            HiDPIUtils.paintAtScale1x(g, x, y, width, height, (g2d, x2, y2, width2, height2, scaleFactor) -> paintComponentBorderImpl(g2d, x2, y2, width2, height2, (float)(focusWidth * scaleFactor), (float)(lineWidth * scaleFactor), (float)(arc * scaleFactor)));
            return;
        }
        paintComponentBorderImpl(g, x, y, width, height, focusWidth, lineWidth, arc);
    }
    
    private static void paintComponentBorderImpl(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float lineWidth, final float arc) {
        final float x2 = x + focusWidth;
        final float y2 = y + focusWidth;
        final float width2 = width - focusWidth * 2.0f;
        final float height2 = height - focusWidth * 2.0f;
        final float arc2 = arc - lineWidth * 2.0f;
        final Shape r1 = createComponentRectangle(x2, y2, width2, height2, arc);
        final Shape r2 = createComponentRectangle(x2 + lineWidth, y2 + lineWidth, width2 - lineWidth * 2.0f, height2 - lineWidth * 2.0f, arc2);
        final Path2D border = new Path2D.Float(0);
        border.append(r1, false);
        border.append(r2, false);
        g.fill(border);
    }
    
    public static void paintComponentBackground(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float arc) {
        final double systemScaleFactor = UIScale.getSystemScaleFactor(g);
        if (systemScaleFactor != 1.0 && systemScaleFactor != 2.0) {
            HiDPIUtils.paintAtScale1x(g, x, y, width, height, (g2d, x2, y2, width2, height2, scaleFactor) -> paintComponentBackgroundImpl(g2d, x2, y2, width2, height2, (float)(focusWidth * scaleFactor), (float)(arc * scaleFactor)));
            return;
        }
        paintComponentBackgroundImpl(g, x, y, width, height, focusWidth, arc);
    }
    
    private static void paintComponentBackgroundImpl(final Graphics2D g, final int x, final int y, final int width, final int height, final float focusWidth, final float arc) {
        g.fill(createComponentRectangle(x + focusWidth, y + focusWidth, width - focusWidth * 2.0f, height - focusWidth * 2.0f, arc));
    }
    
    public static Shape createComponentRectangle(final float x, final float y, final float w, final float h, float arc) {
        if (arc <= 0.0f) {
            return new Rectangle2D.Float(x, y, w, h);
        }
        arc = Math.min(arc, Math.min(w, h));
        return new RoundRectangle2D.Float(x, y, w, h, arc, arc);
    }
    
    static void paintFilledRectangle(final Graphics g, final Color color, final float x, final float y, final float w, final float h) {
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            setRenderingHints(g2);
            g2.setColor(color);
            g2.fill(new Rectangle2D.Float(x, y, w, h));
        }
        finally {
            g2.dispose();
        }
    }
    
    public static void paintGrip(final Graphics g, final int x, final int y, final int width, final int height, final boolean horizontal, final int dotCount, int dotSize, int gap, final boolean centerPrecise) {
        dotSize = UIScale.scale(dotSize);
        gap = UIScale.scale(gap);
        final int gripSize = dotSize * dotCount + gap * (dotCount - 1);
        float gx;
        float gy;
        if (horizontal) {
            gx = (float)(x + Math.round((width - gripSize) / 2.0f));
            gy = y + (height - dotSize) / 2.0f;
            if (!centerPrecise) {
                gy = (float)Math.round(gy);
            }
        }
        else {
            gx = x + (width - dotSize) / 2.0f;
            gy = (float)(y + Math.round((height - gripSize) / 2.0f));
            if (!centerPrecise) {
                gx = (float)Math.round(gx);
            }
        }
        for (int i = 0; i < dotCount; ++i) {
            ((Graphics2D)g).fill(new Ellipse2D.Float(gx, gy, (float)dotSize, (float)dotSize));
            if (horizontal) {
                gx += dotSize + gap;
            }
            else {
                gy += dotSize + gap;
            }
        }
    }
    
    public static void paintParentBackground(final Graphics g, final JComponent c) {
        final Container parent = findOpaqueParent(c);
        if (parent != null) {
            g.setColor(parent.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }
    
    public static Color getParentBackground(final JComponent c) {
        final Container parent = findOpaqueParent(c);
        return (parent != null) ? parent.getBackground() : UIManager.getColor("Panel.background");
    }
    
    private static Container findOpaqueParent(Container c) {
        while ((c = c.getParent()) != null) {
            if (c.isOpaque()) {
                return c;
            }
        }
        return null;
    }
    
    public static Path2D createRectangle(final float x, final float y, final float width, final float height, final float lineWidth) {
        final Path2D path = new Path2D.Float(0);
        path.append(new Rectangle2D.Float(x, y, width, height), false);
        path.append(new Rectangle2D.Float(x + lineWidth, y + lineWidth, width - lineWidth * 2.0f, height - lineWidth * 2.0f), false);
        return path;
    }
    
    public static Path2D createRoundRectangle(final float x, final float y, final float width, final float height, final float lineWidth, final float arcTopLeft, final float arcTopRight, final float arcBottomLeft, final float arcBottomRight) {
        final Path2D path = new Path2D.Float(0);
        path.append(createRoundRectanglePath(x, y, width, height, arcTopLeft, arcTopRight, arcBottomLeft, arcBottomRight), false);
        path.append(createRoundRectanglePath(x + lineWidth, y + lineWidth, width - lineWidth * 2.0f, height - lineWidth * 2.0f, arcTopLeft - lineWidth, arcTopRight - lineWidth, arcBottomLeft - lineWidth, arcBottomRight - lineWidth), false);
        return path;
    }
    
    public static Shape createRoundRectanglePath(final float x, final float y, final float width, final float height, float arcTopLeft, float arcTopRight, float arcBottomLeft, float arcBottomRight) {
        if (arcTopLeft <= 0.0f && arcTopRight <= 0.0f && arcBottomLeft <= 0.0f && arcBottomRight <= 0.0f) {
            return new Rectangle2D.Float(x, y, width, height);
        }
        final float maxArc = Math.min(width, height) / 2.0f;
        arcTopLeft = ((arcTopLeft > 0.0f) ? Math.min(arcTopLeft, maxArc) : 0.0f);
        arcTopRight = ((arcTopRight > 0.0f) ? Math.min(arcTopRight, maxArc) : 0.0f);
        arcBottomLeft = ((arcBottomLeft > 0.0f) ? Math.min(arcBottomLeft, maxArc) : 0.0f);
        arcBottomRight = ((arcBottomRight > 0.0f) ? Math.min(arcBottomRight, maxArc) : 0.0f);
        final float x2 = x + width;
        final float y2 = y + height;
        final double c = 0.5522847498307933;
        final double ci = 1.0 - c;
        final double ciTopLeft = arcTopLeft * ci;
        final double ciTopRight = arcTopRight * ci;
        final double ciBottomLeft = arcBottomLeft * ci;
        final double ciBottomRight = arcBottomRight * ci;
        final Path2D rect = new Path2D.Float();
        rect.moveTo(x2 - arcTopRight, y);
        rect.curveTo(x2 - ciTopRight, y, x2, y + ciTopRight, x2, y + arcTopRight);
        rect.lineTo(x2, y2 - arcBottomRight);
        rect.curveTo(x2, y2 - ciBottomRight, x2 - ciBottomRight, y2, x2 - arcBottomRight, y2);
        rect.lineTo(x + arcBottomLeft, y2);
        rect.curveTo(x + ciBottomLeft, y2, x, y2 - ciBottomLeft, x, y2 - arcBottomLeft);
        rect.lineTo(x, y + arcTopLeft);
        rect.curveTo(x, y + ciTopLeft, x + ciTopLeft, y, x + arcTopLeft, y);
        rect.closePath();
        return rect;
    }
    
    public static void paintArrow(final Graphics2D g, final int x, final int y, final int width, final int height, final int direction, final boolean chevron, final int arrowSize, final float xOffset, final float yOffset) {
        int aw = UIScale.scale(arrowSize + (chevron ? 0 : 1));
        int ah = UIScale.scale(arrowSize / 2 + (chevron ? 0 : 1));
        final boolean vert = direction == 1 || direction == 5;
        if (!vert) {
            final int temp = aw;
            aw = ah;
            ah = temp;
        }
        final int extra = chevron ? 1 : 0;
        final float ox = (width - (aw + extra)) / 2.0f + UIScale.scale(xOffset);
        final float oy = (height - (ah + extra)) / 2.0f + UIScale.scale(yOffset);
        final int ax = x + ((direction == 7) ? (-Math.round(-ox)) : Math.round(ox));
        final int ay = y + ((direction == 1) ? (-Math.round(-oy)) : Math.round(oy));
        g.translate(ax, ay);
        final Shape arrowShape = createArrowShape(direction, chevron, (float)aw, (float)ah);
        if (chevron) {
            final Stroke oldStroke = g.getStroke();
            g.setStroke(new BasicStroke(UIScale.scale(1.0f)));
            g.draw(arrowShape);
            g.setStroke(oldStroke);
        }
        else {
            g.fill(arrowShape);
        }
        g.translate(-ax, -ay);
    }
    
    public static Shape createArrowShape(final int direction, final boolean chevron, final float w, final float h) {
        switch (direction) {
            case 1: {
                return createPath(!chevron, 0.0, h, w / 2.0f, 0.0, w, h);
            }
            case 5: {
                return createPath(!chevron, 0.0, 0.0, w / 2.0f, h, w, 0.0);
            }
            case 7: {
                return createPath(!chevron, w, 0.0, 0.0, h / 2.0f, w, h);
            }
            case 3: {
                return createPath(!chevron, 0.0, 0.0, w, h / 2.0f, 0.0, h);
            }
            default: {
                return new Path2D.Float();
            }
        }
    }
    
    public static Path2D createPath(final double... points) {
        return createPath(true, points);
    }
    
    public static Path2D createPath(final boolean close, final double... points) {
        final Path2D path = new Path2D.Float();
        path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            path.lineTo(points[i], points[i + 1]);
        }
        if (close) {
            path.closePath();
        }
        return path;
    }
    
    public static void drawString(final JComponent c, final Graphics g, final String text, final int x, final int y) {
        HiDPIUtils.drawStringWithYCorrection(c, (Graphics2D)g, text, x, y);
    }
    
    public static void drawStringUnderlineCharAt(final JComponent c, Graphics g, final String text, final int underlinedIndex, final int x, final int y) {
        if (underlinedIndex >= 0 && UIScale.getUserScaleFactor() > 1.0f) {
            g = new Graphics2DProxy((Graphics2D)g) {
                @Override
                public void fillRect(final int x, int y, final int width, int height) {
                    if (height == 1) {
                        height = Math.round(UIScale.scale(0.9f));
                        y += height - 1;
                    }
                    super.fillRect(x, y, width, height);
                }
            };
        }
        HiDPIUtils.drawStringUnderlineCharAtWithYCorrection(c, (Graphics2D)g, text, underlinedIndex, x, y);
    }
    
    public static boolean hasOpaqueBeenExplicitlySet(final JComponent c) {
        final boolean oldOpaque = c.isOpaque();
        LookAndFeel.installProperty(c, "opaque", !oldOpaque);
        final boolean explicitlySet = c.isOpaque() == oldOpaque;
        LookAndFeel.installProperty(c, "opaque", oldOpaque);
        return explicitlySet;
    }
    
    public static ComponentUI createSharedUI(final Object key, final Supplier<ComponentUI> newInstanceSupplier) {
        return FlatUIUtils.sharedUIinstances.computeIfAbsent(UIManager.getLookAndFeel(), k -> new IdentityHashMap()).computeIfAbsent(key, k -> newInstanceSupplier.get());
    }
    
    static {
        MAC_USE_QUARTZ = Boolean.getBoolean("apple.awt.graphics.UseQuartz");
        FlatUIUtils.sharedUIinstances = new WeakHashMap<LookAndFeel, IdentityHashMap<Object, ComponentUI>>();
    }
    
    public static class RepaintFocusListener implements FocusListener
    {
        private final Component repaintComponent;
        private final Predicate<Component> repaintCondition;
        
        public RepaintFocusListener(final Component repaintComponent, final Predicate<Component> repaintCondition) {
            this.repaintComponent = repaintComponent;
            this.repaintCondition = repaintCondition;
        }
        
        @Override
        public void focusGained(final FocusEvent e) {
            if (this.repaintCondition == null || this.repaintCondition.test(this.repaintComponent)) {
                this.repaintComponent.repaint();
            }
        }
        
        @Override
        public void focusLost(final FocusEvent e) {
            if (this.repaintCondition == null || this.repaintCondition.test(this.repaintComponent)) {
                this.repaintComponent.repaint();
            }
        }
    }
}
