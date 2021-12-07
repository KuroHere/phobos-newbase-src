// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.border.*;
import com.formdev.flatlaf.*;
import java.beans.*;
import java.awt.event.*;
import java.util.function.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;

public class FlatRootPaneUI extends BasicRootPaneUI
{
    protected final Color borderColor;
    protected JRootPane rootPane;
    protected FlatTitlePane titlePane;
    protected FlatWindowResizer windowResizer;
    private Object nativeWindowBorderData;
    private LayoutManager oldLayout;
    private HierarchyListener hierarchyListener;
    protected static final Integer TITLE_PANE_LAYER;
    
    public FlatRootPaneUI() {
        this.borderColor = UIManager.getColor("TitlePane.borderColor");
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatRootPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        this.rootPane = (JRootPane)c;
        if (this.rootPane.getWindowDecorationStyle() != 0) {
            this.installClientDecorations();
        }
        else {
            this.installBorder();
        }
        this.installNativeWindowBorder();
    }
    
    protected void installBorder() {
        if (this.borderColor != null) {
            final Border b = this.rootPane.getBorder();
            if (b == null || b instanceof UIResource) {
                this.rootPane.setBorder(new FlatWindowTitleBorder(this.borderColor));
            }
        }
    }
    
    @Override
    public void uninstallUI(final JComponent c) {
        super.uninstallUI(c);
        this.uninstallNativeWindowBorder();
        this.uninstallClientDecorations();
        this.rootPane = null;
    }
    
    @Override
    protected void installDefaults(final JRootPane c) {
        super.installDefaults(c);
        final Container parent = c.getParent();
        if (parent instanceof JFrame || parent instanceof JDialog) {
            final Color background = parent.getBackground();
            if (background == null || background instanceof UIResource) {
                parent.setBackground(UIManager.getColor("control"));
            }
        }
        if (SystemInfo.isJetBrainsJVM && SystemInfo.isMacOS_10_14_Mojave_orLater) {
            c.putClientProperty("jetbrains.awt.windowDarkAppearance", FlatLaf.isLafDark());
        }
    }
    
    @Override
    protected void installListeners(final JRootPane root) {
        super.installListeners(root);
        if (SystemInfo.isJava_9_orLater) {
            root.addHierarchyListener(this.hierarchyListener = (e -> {
                if ((e.getChangeFlags() & 0x4L) != 0x0L && this.rootPane.getParent() instanceof Window) {
                    this.rootPane.getParent().repaint(this.rootPane.getX(), this.rootPane.getY(), this.rootPane.getWidth(), this.rootPane.getHeight());
                }
            }));
        }
    }
    
    @Override
    protected void uninstallListeners(final JRootPane root) {
        super.uninstallListeners(root);
        if (SystemInfo.isJava_9_orLater) {
            root.removeHierarchyListener(this.hierarchyListener);
            this.hierarchyListener = null;
        }
    }
    
    protected void installNativeWindowBorder() {
        this.nativeWindowBorderData = FlatNativeWindowBorder.install(this.rootPane);
    }
    
    protected void uninstallNativeWindowBorder() {
        FlatNativeWindowBorder.uninstall(this.rootPane, this.nativeWindowBorderData);
        this.nativeWindowBorderData = null;
    }
    
    public static void updateNativeWindowBorder(final JRootPane rootPane) {
        final RootPaneUI rui = rootPane.getUI();
        if (!(rui instanceof FlatRootPaneUI)) {
            return;
        }
        final FlatRootPaneUI ui = (FlatRootPaneUI)rui;
        ui.uninstallNativeWindowBorder();
        ui.installNativeWindowBorder();
    }
    
    protected void installClientDecorations() {
        final boolean isNativeWindowBorderSupported = FlatNativeWindowBorder.isSupported();
        if (this.rootPane.getWindowDecorationStyle() != 0 && !isNativeWindowBorderSupported) {
            LookAndFeel.installBorder(this.rootPane, "RootPane.border");
        }
        else {
            LookAndFeel.uninstallBorder(this.rootPane);
        }
        this.setTitlePane(this.createTitlePane());
        this.oldLayout = this.rootPane.getLayout();
        this.rootPane.setLayout(this.createRootLayout());
        if (!isNativeWindowBorderSupported) {
            this.windowResizer = this.createWindowResizer();
        }
    }
    
    protected void uninstallClientDecorations() {
        LookAndFeel.uninstallBorder(this.rootPane);
        this.setTitlePane(null);
        if (this.windowResizer != null) {
            this.windowResizer.uninstall();
            this.windowResizer = null;
        }
        if (this.oldLayout != null) {
            this.rootPane.setLayout(this.oldLayout);
            this.oldLayout = null;
        }
        if (this.rootPane.getWindowDecorationStyle() == 0) {
            this.rootPane.revalidate();
            this.rootPane.repaint();
        }
    }
    
    protected FlatRootLayout createRootLayout() {
        return new FlatRootLayout();
    }
    
    protected FlatWindowResizer createWindowResizer() {
        return new FlatWindowResizer.WindowResizer(this.rootPane);
    }
    
    protected FlatTitlePane createTitlePane() {
        return new FlatTitlePane(this.rootPane);
    }
    
    protected void setTitlePane(final FlatTitlePane newTitlePane) {
        final JLayeredPane layeredPane = this.rootPane.getLayeredPane();
        if (this.titlePane != null) {
            layeredPane.remove(this.titlePane);
        }
        if (newTitlePane != null) {
            layeredPane.add(newTitlePane, FlatRootPaneUI.TITLE_PANE_LAYER);
        }
        this.titlePane = newTitlePane;
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        super.propertyChange(e);
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "windowDecorationStyle": {
                this.uninstallClientDecorations();
                if (this.rootPane.getWindowDecorationStyle() != 0) {
                    this.installClientDecorations();
                    break;
                }
                this.installBorder();
                break;
            }
            case "JRootPane.useWindowDecorations": {
                updateNativeWindowBorder(this.rootPane);
                break;
            }
            case "JRootPane.menuBarEmbedded": {
                if (this.titlePane != null) {
                    this.titlePane.menuBarChanged();
                    this.rootPane.revalidate();
                    this.rootPane.repaint();
                    break;
                }
                break;
            }
            case "JRootPane.titleBarBackground":
            case "JRootPane.titleBarForeground": {
                if (this.titlePane != null) {
                    this.titlePane.titleBarColorsChanged();
                    break;
                }
                break;
            }
        }
    }
    
    protected static boolean isMenuBarEmbedded(final JRootPane rootPane) {
        final RootPaneUI ui = rootPane.getUI();
        return ui instanceof FlatRootPaneUI && ((FlatRootPaneUI)ui).titlePane != null && ((FlatRootPaneUI)ui).titlePane.isMenuBarEmbedded();
    }
    
    static {
        TITLE_PANE_LAYER = JLayeredPane.FRAME_CONTENT_LAYER - 1;
    }
    
    protected class FlatRootLayout implements LayoutManager2
    {
        @Override
        public void addLayoutComponent(final String name, final Component comp) {
        }
        
        @Override
        public void addLayoutComponent(final Component comp, final Object constraints) {
        }
        
        @Override
        public void removeLayoutComponent(final Component comp) {
        }
        
        @Override
        public Dimension preferredLayoutSize(final Container parent) {
            return this.computeLayoutSize(parent, c -> c.getPreferredSize());
        }
        
        @Override
        public Dimension minimumLayoutSize(final Container parent) {
            return this.computeLayoutSize(parent, c -> c.getMinimumSize());
        }
        
        @Override
        public Dimension maximumLayoutSize(final Container parent) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        
        private Dimension computeLayoutSize(final Container parent, final Function<Component, Dimension> getSizeFunc) {
            final JRootPane rootPane = (JRootPane)parent;
            final Dimension titlePaneSize = (FlatRootPaneUI.this.titlePane != null) ? getSizeFunc.apply(FlatRootPaneUI.this.titlePane) : new Dimension();
            final Dimension contentSize = (rootPane.getContentPane() != null) ? getSizeFunc.apply(rootPane.getContentPane()) : rootPane.getSize();
            int width = contentSize.width;
            int height = titlePaneSize.height + contentSize.height;
            if (FlatRootPaneUI.this.titlePane == null || !FlatRootPaneUI.this.titlePane.isMenuBarEmbedded()) {
                final JMenuBar menuBar = rootPane.getJMenuBar();
                final Dimension menuBarSize = (menuBar != null && menuBar.isVisible()) ? getSizeFunc.apply(menuBar) : new Dimension();
                width = Math.max(width, menuBarSize.width);
                height += menuBarSize.height;
            }
            final Insets insets = rootPane.getInsets();
            return new Dimension(width + insets.left + insets.right, height + insets.top + insets.bottom);
        }
        
        @Override
        public void layoutContainer(final Container parent) {
            final JRootPane rootPane = (JRootPane)parent;
            final boolean isFullScreen = FlatUIUtils.isFullScreen(rootPane);
            final Insets insets = rootPane.getInsets();
            final int x = insets.left;
            final int y = insets.top;
            final int width = rootPane.getWidth() - insets.left - insets.right;
            final int height = rootPane.getHeight() - insets.top - insets.bottom;
            if (rootPane.getLayeredPane() != null) {
                rootPane.getLayeredPane().setBounds(x, y, width, height);
            }
            if (rootPane.getGlassPane() != null) {
                rootPane.getGlassPane().setBounds(x, y, width, height);
            }
            int nextY = 0;
            if (FlatRootPaneUI.this.titlePane != null) {
                final int prefHeight = isFullScreen ? 0 : FlatRootPaneUI.this.titlePane.getPreferredSize().height;
                FlatRootPaneUI.this.titlePane.setBounds(0, 0, width, prefHeight);
                nextY += prefHeight;
            }
            final JMenuBar menuBar = rootPane.getJMenuBar();
            if (menuBar != null && menuBar.isVisible()) {
                final boolean embedded = !isFullScreen && FlatRootPaneUI.this.titlePane != null && FlatRootPaneUI.this.titlePane.isMenuBarEmbedded();
                if (embedded) {
                    FlatRootPaneUI.this.titlePane.validate();
                    menuBar.setBounds(FlatRootPaneUI.this.titlePane.getMenuBarBounds());
                }
                else {
                    final Dimension prefSize = menuBar.getPreferredSize();
                    menuBar.setBounds(0, nextY, width, prefSize.height);
                    nextY += prefSize.height;
                }
            }
            final Container contentPane = rootPane.getContentPane();
            if (contentPane != null) {
                contentPane.setBounds(0, nextY, width, Math.max(height - nextY, 0));
            }
            if (FlatRootPaneUI.this.titlePane != null) {
                FlatRootPaneUI.this.titlePane.menuBarLayouted();
            }
        }
        
        @Override
        public void invalidateLayout(final Container parent) {
            if (FlatRootPaneUI.this.titlePane != null) {
                FlatRootPaneUI.this.titlePane.menuBarChanged();
            }
        }
        
        @Override
        public float getLayoutAlignmentX(final Container target) {
            return 0.0f;
        }
        
        @Override
        public float getLayoutAlignmentY(final Container target) {
            return 0.0f;
        }
    }
    
    public static class FlatWindowBorder extends BorderUIResource.EmptyBorderUIResource
    {
        protected final Color activeBorderColor;
        protected final Color inactiveBorderColor;
        protected final Color baseBorderColor;
        
        public FlatWindowBorder() {
            super(1, 1, 1, 1);
            this.activeBorderColor = UIManager.getColor("RootPane.activeBorderColor");
            this.inactiveBorderColor = UIManager.getColor("RootPane.inactiveBorderColor");
            this.baseBorderColor = UIManager.getColor("Panel.background");
        }
        
        @Override
        public Insets getBorderInsets(final Component c, final Insets insets) {
            if (this.isWindowMaximized(c) || FlatUIUtils.isFullScreen(c)) {
                final int n = 0;
                insets.right = n;
                insets.bottom = n;
                insets.left = n;
                insets.top = n;
                return insets;
            }
            return super.getBorderInsets(c, insets);
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            if (this.isWindowMaximized(c) || FlatUIUtils.isFullScreen(c)) {
                return;
            }
            final Container parent = c.getParent();
            final boolean active = parent instanceof Window && ((Window)parent).isActive();
            g.setColor(FlatUIUtils.deriveColor(active ? this.activeBorderColor : this.inactiveBorderColor, this.baseBorderColor));
            HiDPIUtils.paintAtScale1x((Graphics2D)g, x, y, width, height, this::paintImpl);
        }
        
        private void paintImpl(final Graphics2D g, final int x, final int y, final int width, final int height, final double scaleFactor) {
            g.drawRect(x, y, width - 1, height - 1);
        }
        
        protected boolean isWindowMaximized(final Component c) {
            final Container parent = c.getParent();
            return parent instanceof Frame && (((Frame)parent).getExtendedState() & 0x6) != 0x0;
        }
    }
    
    private static class FlatWindowTitleBorder extends BorderUIResource.EmptyBorderUIResource
    {
        private final Color borderColor;
        
        FlatWindowTitleBorder(final Color borderColor) {
            super(0, 0, 0, 0);
            this.borderColor = borderColor;
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            if (this.showBorder(c)) {
                final float lineHeight = UIScale.scale(1.0f);
                FlatUIUtils.paintFilledRectangle(g, this.borderColor, (float)x, (float)y, (float)width, lineHeight);
            }
        }
        
        @Override
        public Insets getBorderInsets(final Component c, final Insets insets) {
            insets.set(this.showBorder(c) ? 1 : 0, 0, 0, 0);
            return insets;
        }
        
        private boolean showBorder(final Component c) {
            final Container parent = c.getParent();
            return (parent instanceof JFrame && (((JFrame)parent).getJMenuBar() == null || !((JFrame)parent).getJMenuBar().isVisible())) || (parent instanceof JDialog && (((JDialog)parent).getJMenuBar() == null || !((JDialog)parent).getJMenuBar().isVisible()));
        }
    }
}
