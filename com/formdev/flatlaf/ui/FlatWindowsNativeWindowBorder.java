// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.beans.*;
import java.awt.*;
import java.awt.geom.*;

class FlatWindowsNativeWindowBorder implements FlatNativeWindowBorder.Provider
{
    private final Map<Window, WndProc> windowsMap;
    private final EventListenerList listenerList;
    private Timer fireStateChangedTimer;
    private boolean colorizationUpToDate;
    private boolean colorizationColorAffectsBorders;
    private Color colorizationColor;
    private int colorizationColorBalance;
    private static NativeLibrary nativeLibrary;
    private static FlatWindowsNativeWindowBorder instance;
    
    static FlatNativeWindowBorder.Provider getInstance() {
        if (!SystemInfo.isWindows_10_orLater) {
            return null;
        }
        if (FlatWindowsNativeWindowBorder.nativeLibrary == null) {
            if (!SystemInfo.isJava_9_orLater) {
                try {
                    System.loadLibrary("jawt");
                }
                catch (Exception ex) {
                    LoggingFacade.INSTANCE.logSevere(null, ex);
                }
            }
            String libraryName = "com/formdev/flatlaf/natives/flatlaf-windows-x86";
            if (SystemInfo.isX86_64) {
                libraryName += "_64";
            }
            FlatWindowsNativeWindowBorder.nativeLibrary = new NativeLibrary(libraryName, null, true);
        }
        if (!FlatWindowsNativeWindowBorder.nativeLibrary.isLoaded()) {
            return null;
        }
        if (FlatWindowsNativeWindowBorder.instance == null) {
            FlatWindowsNativeWindowBorder.instance = new FlatWindowsNativeWindowBorder();
        }
        return FlatWindowsNativeWindowBorder.instance;
    }
    
    private FlatWindowsNativeWindowBorder() {
        this.windowsMap = Collections.synchronizedMap(new IdentityHashMap<Window, WndProc>());
        this.listenerList = new EventListenerList();
    }
    
    @Override
    public boolean hasCustomDecoration(final Window window) {
        return this.windowsMap.containsKey(window);
    }
    
    @Override
    public void setHasCustomDecoration(final Window window, final boolean hasCustomDecoration) {
        if (hasCustomDecoration) {
            this.install(window);
        }
        else {
            this.uninstall(window);
        }
    }
    
    private void install(final Window window) {
        if (!SystemInfo.isWindows_10_orLater) {
            return;
        }
        if (!(window instanceof JFrame) && !(window instanceof JDialog)) {
            return;
        }
        if ((window instanceof Frame && ((Frame)window).isUndecorated()) || (window instanceof Dialog && ((Dialog)window).isUndecorated())) {
            return;
        }
        if (this.windowsMap.containsKey(window)) {
            return;
        }
        final WndProc wndProc = new WndProc(window);
        if (wndProc.hwnd == 0L) {
            return;
        }
        this.windowsMap.put(window, wndProc);
    }
    
    private void uninstall(final Window window) {
        final WndProc wndProc = this.windowsMap.remove(window);
        if (wndProc != null) {
            wndProc.uninstall();
        }
    }
    
    @Override
    public void setTitleBarHeight(final Window window, final int titleBarHeight) {
        final WndProc wndProc = this.windowsMap.get(window);
        if (wndProc == null) {
            return;
        }
        wndProc.titleBarHeight = titleBarHeight;
    }
    
    @Override
    public void setTitleBarHitTestSpots(final Window window, final List<Rectangle> hitTestSpots) {
        final WndProc wndProc = this.windowsMap.get(window);
        if (wndProc == null) {
            return;
        }
        wndProc.hitTestSpots = hitTestSpots.toArray(new Rectangle[hitTestSpots.size()]);
    }
    
    @Override
    public void setTitleBarAppIconBounds(final Window window, final Rectangle appIconBounds) {
        final WndProc wndProc = this.windowsMap.get(window);
        if (wndProc == null) {
            return;
        }
        wndProc.appIconBounds = ((appIconBounds != null) ? new Rectangle(appIconBounds) : null);
    }
    
    @Override
    public boolean showWindow(final Window window, final int cmd) {
        final WndProc wndProc = this.windowsMap.get(window);
        if (wndProc == null) {
            return false;
        }
        wndProc.showWindow(wndProc.hwnd, cmd);
        return true;
    }
    
    @Override
    public boolean isColorizationColorAffectsBorders() {
        this.updateColorization();
        return this.colorizationColorAffectsBorders;
    }
    
    @Override
    public Color getColorizationColor() {
        this.updateColorization();
        return this.colorizationColor;
    }
    
    @Override
    public int getColorizationColorBalance() {
        this.updateColorization();
        return this.colorizationColorBalance;
    }
    
    private void updateColorization() {
        if (this.colorizationUpToDate) {
            return;
        }
        this.colorizationUpToDate = true;
        final String subKey = "SOFTWARE\\Microsoft\\Windows\\DWM";
        int value = registryGetIntValue(subKey, "ColorPrevalence", -1);
        this.colorizationColorAffectsBorders = (value > 0);
        value = registryGetIntValue(subKey, "ColorizationColor", -1);
        this.colorizationColor = ((value != -1) ? new Color(value) : null);
        this.colorizationColorBalance = registryGetIntValue(subKey, "ColorizationColorBalance", -1);
    }
    
    private static native int registryGetIntValue(final String p0, final String p1, final int p2);
    
    @Override
    public void addChangeListener(final ChangeListener l) {
        this.listenerList.add(ChangeListener.class, l);
    }
    
    @Override
    public void removeChangeListener(final ChangeListener l) {
        this.listenerList.remove(ChangeListener.class, l);
    }
    
    private void fireStateChanged() {
        final Object[] listeners = this.listenerList.getListenerList();
        if (listeners.length == 0) {
            return;
        }
        final ChangeEvent e = new ChangeEvent(this);
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener)listeners[i + 1]).stateChanged(e);
            }
        }
    }
    
    void fireStateChangedLaterOnce() {
        EventQueue.invokeLater(() -> {
            if (this.fireStateChangedTimer != null) {
                this.fireStateChangedTimer.restart();
            }
            else {
                (this.fireStateChangedTimer = new Timer(300, e -> {
                    this.fireStateChangedTimer = null;
                    this.colorizationUpToDate = false;
                    this.fireStateChanged();
                    return;
                })).setRepeats(false);
                this.fireStateChangedTimer.start();
            }
        });
    }
    
    private class WndProc implements PropertyChangeListener
    {
        private static final int HTCLIENT = 1;
        private static final int HTCAPTION = 2;
        private static final int HTSYSMENU = 3;
        private static final int HTTOP = 12;
        private Window window;
        private final long hwnd;
        private int titleBarHeight;
        private Rectangle[] hitTestSpots;
        private Rectangle appIconBounds;
        
        WndProc(final Window window) {
            this.window = window;
            this.hwnd = this.installImpl(window);
            if (this.hwnd == 0L) {
                return;
            }
            this.updateFrame(this.hwnd, (window instanceof JFrame) ? ((JFrame)window).getExtendedState() : 0);
            this.updateWindowBackground();
            window.addPropertyChangeListener("background", this);
        }
        
        void uninstall() {
            this.window.removePropertyChangeListener("background", this);
            this.uninstallImpl(this.hwnd);
            this.window = null;
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            this.updateWindowBackground();
        }
        
        private void updateWindowBackground() {
            final Color bg = this.window.getBackground();
            if (bg != null) {
                this.setWindowBackground(this.hwnd, bg.getRed(), bg.getGreen(), bg.getBlue());
            }
        }
        
        private native long installImpl(final Window p0);
        
        private native void uninstallImpl(final long p0);
        
        private native void updateFrame(final long p0, final int p1);
        
        private native void setWindowBackground(final long p0, final int p1, final int p2, final int p3);
        
        private native void showWindow(final long p0, final int p1);
        
        private int onNcHitTest(final int x, final int y, final boolean isOnResizeBorder) {
            final Point pt = this.scaleDown(x, y);
            final int sx = pt.x;
            final int sy = pt.y;
            if (this.appIconBounds != null && this.appIconBounds.contains(sx, sy)) {
                return 3;
            }
            final boolean isOnTitleBar = sy < this.titleBarHeight;
            if (isOnTitleBar) {
                final Rectangle[] hitTestSpots3;
                final Rectangle[] hitTestSpots2 = hitTestSpots3 = this.hitTestSpots;
                for (final Rectangle spot : hitTestSpots3) {
                    if (spot.contains(sx, sy)) {
                        return 1;
                    }
                }
                return isOnResizeBorder ? 12 : 2;
            }
            return isOnResizeBorder ? 12 : 1;
        }
        
        private Point scaleDown(final int x, final int y) {
            final GraphicsConfiguration gc = this.window.getGraphicsConfiguration();
            if (gc == null) {
                return new Point(x, y);
            }
            final AffineTransform t = gc.getDefaultTransform();
            return new Point(this.clipRound(x / t.getScaleX()), this.clipRound(y / t.getScaleY()));
        }
        
        private int clipRound(double value) {
            value -= 0.5;
            if (value < -2.147483648E9) {
                return Integer.MIN_VALUE;
            }
            if (value > 2.147483647E9) {
                return Integer.MAX_VALUE;
            }
            return (int)Math.ceil(value);
        }
        
        private boolean isFullscreen() {
            final GraphicsConfiguration gc = this.window.getGraphicsConfiguration();
            return gc != null && gc.getDevice().getFullScreenWindow() == this.window;
        }
        
        private void fireStateChangedLaterOnce() {
            FlatWindowsNativeWindowBorder.this.fireStateChangedLaterOnce();
        }
    }
}
