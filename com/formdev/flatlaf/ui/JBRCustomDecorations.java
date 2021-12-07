// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.lang.reflect.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.plaf.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;

public class JBRCustomDecorations
{
    private static Boolean supported;
    private static Method Window_hasCustomDecoration;
    private static Method Window_setHasCustomDecoration;
    private static Method WWindowPeer_setCustomDecorationTitleBarHeight;
    private static Method WWindowPeer_setCustomDecorationHitTestSpots;
    private static Method AWTAccessor_getComponentAccessor;
    private static Method AWTAccessor_ComponentAccessor_getPeer;
    
    public static boolean isSupported() {
        initialize();
        return JBRCustomDecorations.supported;
    }
    
    static Object install(final JRootPane rootPane) {
        if (!isSupported()) {
            return null;
        }
        final Window window = SwingUtilities.windowForComponent(rootPane);
        if (window != null) {
            FlatNativeWindowBorder.install(window);
            return null;
        }
        final HierarchyListener addListener = new HierarchyListener() {
            @Override
            public void hierarchyChanged(final HierarchyEvent e) {
                if (e.getChanged() != rootPane || (e.getChangeFlags() & 0x1L) == 0x0L) {
                    return;
                }
                final Container parent = e.getChangedParent();
                if (parent instanceof Window) {
                    FlatNativeWindowBorder.install((Window)parent);
                }
                EventQueue.invokeLater(() -> {
                    final Object val$rootPane = rootPane;
                    rootPane.removeHierarchyListener(this);
                });
            }
        };
        rootPane.addHierarchyListener(addListener);
        return addListener;
    }
    
    static void uninstall(final JRootPane rootPane, final Object data) {
        if (data instanceof HierarchyListener) {
            rootPane.removeHierarchyListener((HierarchyListener)data);
        }
        final Window window = SwingUtilities.windowForComponent(rootPane);
        if (window != null) {
            setHasCustomDecoration(window, false);
        }
    }
    
    static boolean hasCustomDecoration(final Window window) {
        if (!isSupported()) {
            return false;
        }
        try {
            return (boolean)JBRCustomDecorations.Window_hasCustomDecoration.invoke(window, new Object[0]);
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            return false;
        }
    }
    
    static void setHasCustomDecoration(final Window window, final boolean hasCustomDecoration) {
        if (!isSupported()) {
            return;
        }
        try {
            if (hasCustomDecoration) {
                JBRCustomDecorations.Window_setHasCustomDecoration.invoke(window, new Object[0]);
            }
            else {
                setTitleBarHeightAndHitTestSpots(window, 4, Collections.emptyList());
            }
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }
    
    static void setTitleBarHeightAndHitTestSpots(final Window window, final int titleBarHeight, final List<Rectangle> hitTestSpots) {
        if (!isSupported()) {
            return;
        }
        try {
            final Object compAccessor = JBRCustomDecorations.AWTAccessor_getComponentAccessor.invoke(null, new Object[0]);
            final Object peer = JBRCustomDecorations.AWTAccessor_ComponentAccessor_getPeer.invoke(compAccessor, window);
            JBRCustomDecorations.WWindowPeer_setCustomDecorationTitleBarHeight.invoke(peer, titleBarHeight);
            JBRCustomDecorations.WWindowPeer_setCustomDecorationHitTestSpots.invoke(peer, hitTestSpots);
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }
    
    private static void initialize() {
        if (JBRCustomDecorations.supported != null) {
            return;
        }
        JBRCustomDecorations.supported = false;
        if (!SystemInfo.isJetBrainsJVM_11_orLater || !SystemInfo.isWindows_10_orLater) {
            return;
        }
        try {
            final Class<?> awtAcessorClass = Class.forName("sun.awt.AWTAccessor");
            final Class<?> compAccessorClass = Class.forName("sun.awt.AWTAccessor$ComponentAccessor");
            JBRCustomDecorations.AWTAccessor_getComponentAccessor = awtAcessorClass.getDeclaredMethod("getComponentAccessor", (Class<?>[])new Class[0]);
            JBRCustomDecorations.AWTAccessor_ComponentAccessor_getPeer = compAccessorClass.getDeclaredMethod("getPeer", Component.class);
            final Class<?> peerClass = Class.forName("sun.awt.windows.WWindowPeer");
            JBRCustomDecorations.WWindowPeer_setCustomDecorationTitleBarHeight = peerClass.getDeclaredMethod("setCustomDecorationTitleBarHeight", Integer.TYPE);
            JBRCustomDecorations.WWindowPeer_setCustomDecorationHitTestSpots = peerClass.getDeclaredMethod("setCustomDecorationHitTestSpots", List.class);
            JBRCustomDecorations.WWindowPeer_setCustomDecorationTitleBarHeight.setAccessible(true);
            JBRCustomDecorations.WWindowPeer_setCustomDecorationHitTestSpots.setAccessible(true);
            JBRCustomDecorations.Window_hasCustomDecoration = Window.class.getDeclaredMethod("hasCustomDecoration", (Class<?>[])new Class[0]);
            JBRCustomDecorations.Window_setHasCustomDecoration = Window.class.getDeclaredMethod("setHasCustomDecoration", (Class<?>[])new Class[0]);
            JBRCustomDecorations.Window_hasCustomDecoration.setAccessible(true);
            JBRCustomDecorations.Window_setHasCustomDecoration.setAccessible(true);
            JBRCustomDecorations.supported = true;
        }
        catch (Exception ex) {}
    }
    
    static class JBRWindowTopBorder extends BorderUIResource.EmptyBorderUIResource
    {
        private static JBRWindowTopBorder instance;
        private final Color defaultActiveBorder;
        private final Color inactiveLightColor;
        private boolean colorizationAffectsBorders;
        private Color activeColor;
        
        static JBRWindowTopBorder getInstance() {
            if (JBRWindowTopBorder.instance == null) {
                JBRWindowTopBorder.instance = new JBRWindowTopBorder();
            }
            return JBRWindowTopBorder.instance;
        }
        
        JBRWindowTopBorder() {
            super(1, 0, 0, 0);
            this.defaultActiveBorder = new Color(7368816);
            this.inactiveLightColor = new Color(11184810);
            this.activeColor = this.defaultActiveBorder;
            this.update();
            this.installListeners();
        }
        
        void update() {
            this.colorizationAffectsBorders = this.isColorizationColorAffectsBorders();
            this.activeColor = this.calculateActiveBorderColor();
        }
        
        void installListeners() {
            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.addPropertyChangeListener("win.dwm.colorizationColor.affects.borders", e -> {
                this.colorizationAffectsBorders = this.isColorizationColorAffectsBorders();
                this.activeColor = this.calculateActiveBorderColor();
                return;
            });
            final PropertyChangeListener l = e -> this.activeColor = this.calculateActiveBorderColor();
            toolkit.addPropertyChangeListener("win.dwm.colorizationColor", l);
            toolkit.addPropertyChangeListener("win.dwm.colorizationColorBalance", l);
            toolkit.addPropertyChangeListener("win.frame.activeBorderColor", l);
        }
        
        boolean isColorizationColorAffectsBorders() {
            final Object value = Toolkit.getDefaultToolkit().getDesktopProperty("win.dwm.colorizationColor.affects.borders");
            return !(value instanceof Boolean) || (boolean)value;
        }
        
        Color getColorizationColor() {
            return (Color)Toolkit.getDefaultToolkit().getDesktopProperty("win.dwm.colorizationColor");
        }
        
        int getColorizationColorBalance() {
            final Object value = Toolkit.getDefaultToolkit().getDesktopProperty("win.dwm.colorizationColorBalance");
            return (int)((value instanceof Integer) ? value : -1);
        }
        
        private Color calculateActiveBorderColor() {
            if (!this.colorizationAffectsBorders) {
                return this.defaultActiveBorder;
            }
            final Color colorizationColor = this.getColorizationColor();
            if (colorizationColor == null) {
                final Color activeBorderColor = (Color)Toolkit.getDefaultToolkit().getDesktopProperty("win.frame.activeBorderColor");
                return (activeBorderColor != null) ? activeBorderColor : UIManager.getColor("MenuBar.borderColor");
            }
            int colorizationColorBalance = this.getColorizationColorBalance();
            if (colorizationColorBalance < 0 || colorizationColorBalance > 100) {
                colorizationColorBalance = 100;
            }
            if (colorizationColorBalance == 0) {
                return new Color(14277081);
            }
            if (colorizationColorBalance == 100) {
                return colorizationColor;
            }
            final float alpha = colorizationColorBalance / 100.0f;
            final float remainder = 1.0f - alpha;
            int r = Math.round(colorizationColor.getRed() * alpha + 217.0f * remainder);
            int g = Math.round(colorizationColor.getGreen() * alpha + 217.0f * remainder);
            int b = Math.round(colorizationColor.getBlue() * alpha + 217.0f * remainder);
            r = Math.min(Math.max(r, 0), 255);
            g = Math.min(Math.max(g, 0), 255);
            b = Math.min(Math.max(b, 0), 255);
            return new Color(r, g, b);
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            final Window window = SwingUtilities.windowForComponent(c);
            final boolean active = window != null && window.isActive();
            final boolean paintTopBorder = !FlatLaf.isLafDark() || (active && this.colorizationAffectsBorders);
            if (!paintTopBorder) {
                return;
            }
            g.setColor(active ? this.activeColor : this.inactiveLightColor);
            HiDPIUtils.paintAtScale1x((Graphics2D)g, x, y, width, height, this::paintImpl);
        }
        
        private void paintImpl(final Graphics2D g, final int x, final int y, final int width, final int height, final double scaleFactor) {
            g.drawRect(x, y, width - 1, 0);
        }
        
        void repaintBorder(final Component c) {
            c.repaint(0, 0, c.getWidth(), 1);
        }
    }
}
