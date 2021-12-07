// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.geom.*;
import com.formdev.flatlaf.util.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;
import java.beans.*;
import java.awt.event.*;

public class FlatTitlePane extends JComponent
{
    protected final Color activeBackground;
    protected final Color inactiveBackground;
    protected final Color activeForeground;
    protected final Color inactiveForeground;
    protected final Color embeddedForeground;
    protected final Color borderColor;
    protected final Dimension iconSize;
    protected final int buttonMaximizedHeight;
    protected final boolean centerTitle;
    protected final boolean centerTitleIfMenuBarEmbedded;
    protected final int menuBarTitleGap;
    protected final JRootPane rootPane;
    protected JPanel leftPanel;
    protected JLabel iconLabel;
    protected JComponent menuBarPlaceholder;
    protected JLabel titleLabel;
    protected JPanel buttonPanel;
    protected JButton iconifyButton;
    protected JButton maximizeButton;
    protected JButton restoreButton;
    protected JButton closeButton;
    protected Window window;
    private final Handler handler;
    private static final int HIT_TEST_SPOT_GROW = 2;
    
    public FlatTitlePane(final JRootPane rootPane) {
        this.activeBackground = UIManager.getColor("TitlePane.background");
        this.inactiveBackground = UIManager.getColor("TitlePane.inactiveBackground");
        this.activeForeground = UIManager.getColor("TitlePane.foreground");
        this.inactiveForeground = UIManager.getColor("TitlePane.inactiveForeground");
        this.embeddedForeground = UIManager.getColor("TitlePane.embeddedForeground");
        this.borderColor = UIManager.getColor("TitlePane.borderColor");
        this.iconSize = UIManager.getDimension("TitlePane.iconSize");
        this.buttonMaximizedHeight = UIManager.getInt("TitlePane.buttonMaximizedHeight");
        this.centerTitle = UIManager.getBoolean("TitlePane.centerTitle");
        this.centerTitleIfMenuBarEmbedded = FlatUIUtils.getUIBoolean("TitlePane.centerTitleIfMenuBarEmbedded", true);
        this.menuBarTitleGap = FlatUIUtils.getUIInt("TitlePane.menuBarTitleGap", 20);
        this.rootPane = rootPane;
        this.handler = this.createHandler();
        this.setBorder(this.createTitlePaneBorder());
        this.addSubComponents();
        this.activeChanged(true);
        this.addMouseListener(this.handler);
        this.addMouseMotionListener(this.handler);
        this.iconLabel.addMouseListener(this.handler);
    }
    
    protected FlatTitlePaneBorder createTitlePaneBorder() {
        return new FlatTitlePaneBorder();
    }
    
    protected Handler createHandler() {
        return new Handler();
    }
    
    protected void addSubComponents() {
        this.leftPanel = new JPanel();
        this.iconLabel = new JLabel();
        this.titleLabel = new JLabel() {
            @Override
            public void updateUI() {
                this.setUI(new FlatTitleLabelUI());
            }
        };
        this.iconLabel.setBorder(new FlatEmptyBorder(UIManager.getInsets("TitlePane.iconMargins")));
        this.titleLabel.setBorder(new FlatEmptyBorder(UIManager.getInsets("TitlePane.titleMargins")));
        this.titleLabel.setHorizontalAlignment(0);
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, 2));
        this.leftPanel.setOpaque(false);
        this.leftPanel.add(this.iconLabel);
        this.menuBarPlaceholder = new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                final JMenuBar menuBar = FlatTitlePane.this.rootPane.getJMenuBar();
                return FlatTitlePane.this.hasVisibleEmbeddedMenuBar(menuBar) ? menuBar.getPreferredSize() : new Dimension();
            }
        };
        this.leftPanel.add(this.menuBarPlaceholder);
        this.createButtons();
        this.setLayout(new BorderLayout() {
            @Override
            public void layoutContainer(final Container target) {
                super.layoutContainer(target);
                final Insets insets = target.getInsets();
                final int width = target.getWidth() - insets.left - insets.right;
                if (FlatTitlePane.this.leftPanel.getWidth() + FlatTitlePane.this.buttonPanel.getWidth() > width) {
                    final int oldWidth = FlatTitlePane.this.leftPanel.getWidth();
                    final int newWidth = Math.max(width - FlatTitlePane.this.buttonPanel.getWidth(), 0);
                    FlatTitlePane.this.leftPanel.setSize(newWidth, FlatTitlePane.this.leftPanel.getHeight());
                    if (!FlatTitlePane.this.getComponentOrientation().isLeftToRight()) {
                        FlatTitlePane.this.leftPanel.setLocation(FlatTitlePane.this.leftPanel.getX() + (oldWidth - newWidth), FlatTitlePane.this.leftPanel.getY());
                    }
                }
                final JMenuBar menuBar = FlatTitlePane.this.rootPane.getJMenuBar();
                if (FlatTitlePane.this.hasVisibleEmbeddedMenuBar(menuBar)) {
                    final Component horizontalGlue = FlatTitlePane.this.findHorizontalGlue(menuBar);
                    if (horizontalGlue != null) {
                        final Point glueLocation = SwingUtilities.convertPoint(horizontalGlue, 0, 0, FlatTitlePane.this.titleLabel);
                        FlatTitlePane.this.titleLabel.setBounds(FlatTitlePane.this.titleLabel.getX() + glueLocation.x, FlatTitlePane.this.titleLabel.getY(), horizontalGlue.getWidth(), FlatTitlePane.this.titleLabel.getHeight());
                    }
                }
            }
        });
        this.add(this.leftPanel, "Before");
        this.add(this.titleLabel, "Center");
        this.add(this.buttonPanel, "After");
    }
    
    protected void createButtons() {
        this.iconifyButton = this.createButton("TitlePane.iconifyIcon", "Iconify", e -> this.iconify());
        this.maximizeButton = this.createButton("TitlePane.maximizeIcon", "Maximize", e -> this.maximize());
        this.restoreButton = this.createButton("TitlePane.restoreIcon", "Restore", e -> this.restore());
        this.closeButton = this.createButton("TitlePane.closeIcon", "Close", e -> this.close());
        (this.buttonPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                if (FlatTitlePane.this.buttonMaximizedHeight > 0 && FlatTitlePane.this.window instanceof Frame && (((Frame)FlatTitlePane.this.window).getExtendedState() & 0x6) != 0x0) {
                    size = new Dimension(size.width, Math.min(size.height, UIScale.scale(FlatTitlePane.this.buttonMaximizedHeight)));
                }
                return size;
            }
        }).setOpaque(false);
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, 2));
        if (this.rootPane.getWindowDecorationStyle() == 1) {
            this.restoreButton.setVisible(false);
            this.buttonPanel.add(this.iconifyButton);
            this.buttonPanel.add(this.maximizeButton);
            this.buttonPanel.add(this.restoreButton);
        }
        this.buttonPanel.add(this.closeButton);
    }
    
    protected JButton createButton(final String iconKey, final String accessibleName, final ActionListener action) {
        final JButton button = new JButton(UIManager.getIcon(iconKey));
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.putClientProperty("AccessibleName", accessibleName);
        button.addActionListener(action);
        return button;
    }
    
    protected void activeChanged(final boolean active) {
        Color background = FlatClientProperties.clientPropertyColor(this.rootPane, "JRootPane.titleBarBackground", null);
        Color titleForeground;
        Color foreground = titleForeground = FlatClientProperties.clientPropertyColor(this.rootPane, "JRootPane.titleBarForeground", null);
        if (background == null) {
            background = FlatUIUtils.nonUIResource(active ? this.activeBackground : this.inactiveBackground);
        }
        if (foreground == null) {
            foreground = FlatUIUtils.nonUIResource(active ? this.activeForeground : this.inactiveForeground);
            titleForeground = ((active && this.hasVisibleEmbeddedMenuBar(this.rootPane.getJMenuBar())) ? FlatUIUtils.nonUIResource(this.embeddedForeground) : foreground);
        }
        this.setBackground(background);
        this.titleLabel.setForeground(titleForeground);
        this.iconifyButton.setForeground(foreground);
        this.maximizeButton.setForeground(foreground);
        this.restoreButton.setForeground(foreground);
        this.closeButton.setForeground(foreground);
        this.iconifyButton.setBackground(background);
        this.maximizeButton.setBackground(background);
        this.restoreButton.setBackground(background);
        this.closeButton.setBackground(background);
    }
    
    protected void frameStateChanged() {
        if (this.window == null || this.rootPane.getWindowDecorationStyle() != 1) {
            return;
        }
        if (this.window instanceof Frame) {
            final Frame frame = (Frame)this.window;
            final boolean resizable = frame.isResizable();
            final boolean maximized = (frame.getExtendedState() & 0x6) != 0x0;
            this.iconifyButton.setVisible(true);
            this.maximizeButton.setVisible(resizable && !maximized);
            this.restoreButton.setVisible(resizable && maximized);
            if (maximized && this.rootPane.getClientProperty("_flatlaf.maximizedBoundsUpToDate") == null) {
                this.rootPane.putClientProperty("_flatlaf.maximizedBoundsUpToDate", null);
                final Rectangle oldMaximizedBounds = frame.getMaximizedBounds();
                this.updateMaximizedBounds();
                final Rectangle newMaximizedBounds = frame.getMaximizedBounds();
                if (newMaximizedBounds != null && !newMaximizedBounds.equals(oldMaximizedBounds)) {
                    final int oldExtendedState = frame.getExtendedState();
                    frame.setExtendedState(oldExtendedState & 0xFFFFFFF9);
                    frame.setExtendedState(oldExtendedState);
                }
            }
        }
        else {
            this.iconifyButton.setVisible(false);
            this.maximizeButton.setVisible(false);
            this.restoreButton.setVisible(false);
            this.revalidate();
            this.repaint();
        }
    }
    
    protected void updateIcon() {
        List<Image> images = this.window.getIconImages();
        if (images.isEmpty()) {
            for (Window owner = this.window.getOwner(); owner != null; owner = owner.getOwner()) {
                images = owner.getIconImages();
                if (!images.isEmpty()) {
                    break;
                }
            }
        }
        boolean hasIcon = true;
        if (!images.isEmpty()) {
            this.iconLabel.setIcon(new FlatTitlePaneIcon(images, this.iconSize));
        }
        else {
            Icon defaultIcon = UIManager.getIcon("TitlePane.icon");
            if (defaultIcon != null && (defaultIcon.getIconWidth() == 0 || defaultIcon.getIconHeight() == 0)) {
                defaultIcon = null;
            }
            if (defaultIcon != null) {
                if (defaultIcon instanceof ImageIcon) {
                    defaultIcon = new ScaledImageIcon((ImageIcon)defaultIcon, this.iconSize.width, this.iconSize.height);
                }
                this.iconLabel.setIcon(defaultIcon);
            }
            else {
                hasIcon = false;
            }
        }
        this.iconLabel.setVisible(hasIcon);
        this.updateNativeTitleBarHeightAndHitTestSpotsLater();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        this.uninstallWindowListeners();
        this.window = SwingUtilities.getWindowAncestor(this);
        if (this.window != null) {
            this.frameStateChanged();
            this.activeChanged(this.window.isActive());
            this.updateIcon();
            this.titleLabel.setText(this.getWindowTitle());
            this.installWindowListeners();
        }
        this.updateNativeTitleBarHeightAndHitTestSpotsLater();
    }
    
    @Override
    public void removeNotify() {
        super.removeNotify();
        this.uninstallWindowListeners();
        this.window = null;
    }
    
    protected String getWindowTitle() {
        if (this.window instanceof Frame) {
            return ((Frame)this.window).getTitle();
        }
        if (this.window instanceof Dialog) {
            return ((Dialog)this.window).getTitle();
        }
        return null;
    }
    
    protected void installWindowListeners() {
        if (this.window == null) {
            return;
        }
        this.window.addPropertyChangeListener(this.handler);
        this.window.addWindowListener(this.handler);
        this.window.addWindowStateListener(this.handler);
        this.window.addComponentListener(this.handler);
    }
    
    protected void uninstallWindowListeners() {
        if (this.window == null) {
            return;
        }
        this.window.removePropertyChangeListener(this.handler);
        this.window.removeWindowListener(this.handler);
        this.window.removeWindowStateListener(this.handler);
        this.window.removeComponentListener(this.handler);
    }
    
    protected boolean hasVisibleEmbeddedMenuBar(final JMenuBar menuBar) {
        return menuBar != null && menuBar.isVisible() && this.isMenuBarEmbedded();
    }
    
    protected boolean isMenuBarEmbedded() {
        return FlatUIUtils.getBoolean(this.rootPane, "flatlaf.menuBarEmbedded", "JRootPane.menuBarEmbedded", "TitlePane.menuBarEmbedded", false);
    }
    
    protected Rectangle getMenuBarBounds() {
        final Insets insets = this.rootPane.getInsets();
        final Rectangle bounds = new Rectangle(SwingUtilities.convertPoint(this.menuBarPlaceholder, -insets.left, -insets.top, this.rootPane), this.menuBarPlaceholder.getSize());
        final Insets borderInsets = this.getBorder().getBorderInsets(this);
        final Rectangle rectangle = bounds;
        rectangle.height += borderInsets.bottom;
        final Component horizontalGlue = this.findHorizontalGlue(this.rootPane.getJMenuBar());
        if (horizontalGlue != null) {
            final boolean leftToRight = this.getComponentOrientation().isLeftToRight();
            int titleWidth = leftToRight ? (this.buttonPanel.getX() - (this.leftPanel.getX() + this.leftPanel.getWidth())) : (this.leftPanel.getX() - (this.buttonPanel.getX() + this.buttonPanel.getWidth()));
            titleWidth = Math.max(titleWidth, 0);
            final Rectangle rectangle2 = bounds;
            rectangle2.width += titleWidth;
            if (!leftToRight) {
                final Rectangle rectangle3 = bounds;
                rectangle3.x -= titleWidth;
            }
        }
        return bounds;
    }
    
    protected Component findHorizontalGlue(final JMenuBar menuBar) {
        if (menuBar == null) {
            return null;
        }
        final int count = menuBar.getComponentCount();
        for (int i = count - 1; i >= 0; --i) {
            final Component c = menuBar.getComponent(i);
            if (c instanceof Box.Filler && c.getMaximumSize().width >= 32767) {
                return c;
            }
        }
        return null;
    }
    
    protected void titleBarColorsChanged() {
        this.activeChanged(this.window == null || this.window.isActive());
        this.repaint();
    }
    
    protected void menuBarChanged() {
        this.menuBarPlaceholder.invalidate();
        this.repaint();
        EventQueue.invokeLater(() -> this.activeChanged(this.window == null || this.window.isActive()));
    }
    
    protected void menuBarLayouted() {
        this.updateNativeTitleBarHeightAndHitTestSpotsLater();
        this.revalidate();
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        g.setColor((UIManager.getBoolean("TitlePane.unifiedBackground") && FlatClientProperties.clientPropertyColor(this.rootPane, "JRootPane.titleBarBackground", null) == null) ? FlatUIUtils.getParentBackground(this) : this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    protected void repaintWindowBorder() {
        final int width = this.rootPane.getWidth();
        final int height = this.rootPane.getHeight();
        final Insets insets = this.rootPane.getInsets();
        this.rootPane.repaint(0, 0, width, insets.top);
        this.rootPane.repaint(0, 0, insets.left, height);
        this.rootPane.repaint(0, height - insets.bottom, width, insets.bottom);
        this.rootPane.repaint(width - insets.right, 0, insets.right, height);
    }
    
    protected void iconify() {
        if (!(this.window instanceof Frame)) {
            return;
        }
        final Frame frame = (Frame)this.window;
        if (!FlatNativeWindowBorder.showWindow(this.window, 6)) {
            frame.setExtendedState(frame.getExtendedState() | 0x1);
        }
    }
    
    protected void maximize() {
        if (!(this.window instanceof Frame)) {
            return;
        }
        final Frame frame = (Frame)this.window;
        this.updateMaximizedBounds();
        this.rootPane.putClientProperty("_flatlaf.maximizedBoundsUpToDate", true);
        if (!FlatNativeWindowBorder.showWindow(frame, 3)) {
            frame.setExtendedState(frame.getExtendedState() | 0x6);
        }
    }
    
    protected void updateMaximizedBounds() {
        final Frame frame = (Frame)this.window;
        final Rectangle oldMaximizedBounds = frame.getMaximizedBounds();
        if (!this.hasNativeCustomDecoration() && (oldMaximizedBounds == null || Objects.equals(oldMaximizedBounds, this.rootPane.getClientProperty("_flatlaf.maximizedBounds")))) {
            final GraphicsConfiguration gc = this.window.getGraphicsConfiguration();
            final Rectangle screenBounds = gc.getBounds();
            int maximizedX = screenBounds.x;
            int maximizedY = screenBounds.y;
            int maximizedWidth = screenBounds.width;
            int maximizedHeight = screenBounds.height;
            if (!this.isMaximizedBoundsFixed()) {
                maximizedX = 0;
                maximizedY = 0;
                final AffineTransform defaultTransform = gc.getDefaultTransform();
                maximizedWidth *= (int)defaultTransform.getScaleX();
                maximizedHeight *= (int)defaultTransform.getScaleY();
            }
            final Insets screenInsets = this.window.getToolkit().getScreenInsets(gc);
            final Rectangle newMaximizedBounds = new Rectangle(maximizedX + screenInsets.left, maximizedY + screenInsets.top, maximizedWidth - screenInsets.left - screenInsets.right, maximizedHeight - screenInsets.top - screenInsets.bottom);
            if (!Objects.equals(oldMaximizedBounds, newMaximizedBounds)) {
                frame.setMaximizedBounds(newMaximizedBounds);
                this.rootPane.putClientProperty("_flatlaf.maximizedBounds", newMaximizedBounds);
            }
        }
    }
    
    private boolean isMaximizedBoundsFixed() {
        return SystemInfo.isJava_15_orLater || (SystemInfo.javaVersion >= SystemInfo.toVersion(11, 0, 8, 0) && SystemInfo.javaVersion < SystemInfo.toVersion(12, 0, 0, 0)) || (SystemInfo.javaVersion >= SystemInfo.toVersion(13, 0, 4, 0) && SystemInfo.javaVersion < SystemInfo.toVersion(14, 0, 0, 0));
    }
    
    protected void restore() {
        if (!(this.window instanceof Frame)) {
            return;
        }
        final Frame frame = (Frame)this.window;
        if (!FlatNativeWindowBorder.showWindow(this.window, 9)) {
            final int state = frame.getExtendedState();
            frame.setExtendedState(((state & 0x1) != 0x0) ? (state & 0xFFFFFFFE) : (state & 0xFFFFFFF9));
        }
    }
    
    protected void close() {
        if (this.window != null) {
            this.window.dispatchEvent(new WindowEvent(this.window, 201));
        }
    }
    
    private boolean hasJBRCustomDecoration() {
        return this.window != null && JBRCustomDecorations.hasCustomDecoration(this.window);
    }
    
    protected boolean hasNativeCustomDecoration() {
        return this.window != null && FlatNativeWindowBorder.hasCustomDecoration(this.window);
    }
    
    protected void updateNativeTitleBarHeightAndHitTestSpotsLater() {
        EventQueue.invokeLater(() -> this.updateNativeTitleBarHeightAndHitTestSpots());
    }
    
    protected void updateNativeTitleBarHeightAndHitTestSpots() {
        if (!this.isDisplayable()) {
            return;
        }
        if (!this.hasNativeCustomDecoration()) {
            return;
        }
        int titleBarHeight = this.getHeight();
        if (titleBarHeight > 0) {
            --titleBarHeight;
        }
        final List<Rectangle> hitTestSpots = new ArrayList<Rectangle>();
        Rectangle appIconBounds = null;
        if (this.iconLabel.isVisible()) {
            final Point location = SwingUtilities.convertPoint(this.iconLabel, 0, 0, this.window);
            final Insets iconInsets = this.iconLabel.getInsets();
            final Rectangle iconBounds = new Rectangle(location.x + iconInsets.left - 1, location.y + iconInsets.top - 1, this.iconLabel.getWidth() - iconInsets.left - iconInsets.right + 2, this.iconLabel.getHeight() - iconInsets.top - iconInsets.bottom + 2);
            if (this.window instanceof Frame && (((Frame)this.window).getExtendedState() & 0x6) != 0x0) {
                final Rectangle rectangle = iconBounds;
                rectangle.height += iconBounds.y;
                iconBounds.y = 0;
                if (this.window.getComponentOrientation().isLeftToRight()) {
                    final Rectangle rectangle2 = iconBounds;
                    rectangle2.width += iconBounds.x;
                    iconBounds.x = 0;
                }
                else {
                    final Rectangle rectangle3 = iconBounds;
                    rectangle3.width += iconInsets.right;
                }
            }
            if (this.hasJBRCustomDecoration()) {
                hitTestSpots.add(iconBounds);
            }
            else {
                appIconBounds = iconBounds;
            }
        }
        Rectangle r = this.getNativeHitTestSpot(this.buttonPanel);
        if (r != null) {
            hitTestSpots.add(r);
        }
        final JMenuBar menuBar = this.rootPane.getJMenuBar();
        if (this.hasVisibleEmbeddedMenuBar(menuBar)) {
            r = this.getNativeHitTestSpot(this.menuBarPlaceholder);
            if (r != null) {
                final Component horizontalGlue = this.findHorizontalGlue(menuBar);
                if (horizontalGlue != null) {
                    final Point glueLocation = SwingUtilities.convertPoint(horizontalGlue, 0, 0, this.window);
                    Rectangle r2;
                    if (this.getComponentOrientation().isLeftToRight()) {
                        final int trailingWidth = r.x + r.width - 2 - glueLocation.x;
                        final Rectangle rectangle4 = r;
                        rectangle4.width -= trailingWidth;
                        r2 = new Rectangle(glueLocation.x + horizontalGlue.getWidth(), r.y, trailingWidth, r.height);
                    }
                    else {
                        final int leadingWidth = glueLocation.x + horizontalGlue.getWidth() - (r.x + 2);
                        final Rectangle rectangle5 = r;
                        rectangle5.x += leadingWidth;
                        final Rectangle rectangle6 = r;
                        rectangle6.width -= leadingWidth;
                        r2 = new Rectangle(glueLocation.x - leadingWidth, r.y, leadingWidth, r.height);
                    }
                    r2.grow(2, 2);
                    hitTestSpots.add(r2);
                }
                hitTestSpots.add(r);
            }
        }
        FlatNativeWindowBorder.setTitleBarHeightAndHitTestSpots(this.window, titleBarHeight, hitTestSpots, appIconBounds);
    }
    
    protected Rectangle getNativeHitTestSpot(final JComponent c) {
        final Dimension size = c.getSize();
        if (size.width <= 0 || size.height <= 0) {
            return null;
        }
        final Point location = SwingUtilities.convertPoint(c, 0, 0, this.window);
        final Rectangle r = new Rectangle(location, size);
        r.grow(2, 2);
        return r;
    }
    
    protected class FlatTitlePaneBorder extends AbstractBorder
    {
        @Override
        public Insets getBorderInsets(final Component c, Insets insets) {
            super.getBorderInsets(c, insets);
            final Border menuBarBorder = this.getMenuBarBorder();
            if (menuBarBorder != null) {
                final Insets menuBarInsets = menuBarBorder.getBorderInsets(c);
                final Insets insets2 = insets;
                insets2.bottom += menuBarInsets.bottom;
            }
            else if (FlatTitlePane.this.borderColor != null && (FlatTitlePane.this.rootPane.getJMenuBar() == null || !FlatTitlePane.this.rootPane.getJMenuBar().isVisible())) {
                final Insets insets3 = insets;
                insets3.bottom += UIScale.scale(1);
            }
            if (FlatTitlePane.this.hasNativeCustomDecoration() && !this.isWindowMaximized(c)) {
                insets = FlatUIUtils.addInsets(insets, FlatNativeWindowBorder.WindowTopBorder.getInstance().getBorderInsets());
            }
            return insets;
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            final Border menuBarBorder = this.getMenuBarBorder();
            if (menuBarBorder != null) {
                menuBarBorder.paintBorder(c, g, x, y, width, height);
            }
            else if (FlatTitlePane.this.borderColor != null && (FlatTitlePane.this.rootPane.getJMenuBar() == null || !FlatTitlePane.this.rootPane.getJMenuBar().isVisible())) {
                final float lineHeight = UIScale.scale(1.0f);
                FlatUIUtils.paintFilledRectangle(g, FlatTitlePane.this.borderColor, (float)x, y + height - lineHeight, (float)width, lineHeight);
            }
            if (FlatTitlePane.this.hasNativeCustomDecoration() && !this.isWindowMaximized(c)) {
                FlatNativeWindowBorder.WindowTopBorder.getInstance().paintBorder(c, g, x, y, width, height);
            }
        }
        
        protected Border getMenuBarBorder() {
            final JMenuBar menuBar = FlatTitlePane.this.rootPane.getJMenuBar();
            return FlatTitlePane.this.hasVisibleEmbeddedMenuBar(menuBar) ? menuBar.getBorder() : null;
        }
        
        protected boolean isWindowMaximized(final Component c) {
            return FlatTitlePane.this.window instanceof Frame && (((Frame)FlatTitlePane.this.window).getExtendedState() & 0x6) != 0x0;
        }
    }
    
    protected class FlatTitleLabelUI extends FlatLabelUI
    {
        @Override
        protected void paintEnabledText(final JLabel l, final Graphics g, final String s, int textX, final int textY) {
            final boolean hasEmbeddedMenuBar = FlatTitlePane.this.hasVisibleEmbeddedMenuBar(FlatTitlePane.this.rootPane.getJMenuBar());
            final int labelWidth = l.getWidth();
            final int textWidth = labelWidth - textX * 2;
            final int gap = UIScale.scale(FlatTitlePane.this.menuBarTitleGap);
            final boolean center = hasEmbeddedMenuBar ? FlatTitlePane.this.centerTitleIfMenuBarEmbedded : FlatTitlePane.this.centerTitle;
            Label_0215: {
                if (center) {
                    final int centeredTextX = (l.getParent().getWidth() - textWidth) / 2 - l.getX();
                    if (centeredTextX >= gap && centeredTextX + textWidth <= labelWidth - gap) {
                        textX = centeredTextX;
                    }
                }
                else {
                    final boolean leftToRight = FlatTitlePane.this.getComponentOrientation().isLeftToRight();
                    final Insets insets = l.getInsets();
                    final int leadingInset = hasEmbeddedMenuBar ? gap : (leftToRight ? insets.left : insets.right);
                    final int leadingTextX = leftToRight ? leadingInset : (labelWidth - leadingInset - textWidth);
                    if (leftToRight) {
                        if (leadingTextX >= textX) {
                            break Label_0215;
                        }
                    }
                    else if (leadingTextX <= textX) {
                        break Label_0215;
                    }
                    textX = leadingTextX;
                }
            }
            super.paintEnabledText(l, g, s, textX, textY);
        }
    }
    
    protected class Handler extends WindowAdapter implements PropertyChangeListener, MouseListener, MouseMotionListener, ComponentListener
    {
        private Point dragOffset;
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            final String propertyName = e.getPropertyName();
            switch (propertyName) {
                case "title": {
                    FlatTitlePane.this.titleLabel.setText(FlatTitlePane.this.getWindowTitle());
                    break;
                }
                case "resizable": {
                    if (FlatTitlePane.this.window instanceof Frame) {
                        FlatTitlePane.this.frameStateChanged();
                        break;
                    }
                    break;
                }
                case "iconImage": {
                    FlatTitlePane.this.updateIcon();
                    break;
                }
                case "componentOrientation": {
                    FlatTitlePane.this.updateNativeTitleBarHeightAndHitTestSpotsLater();
                    break;
                }
            }
        }
        
        @Override
        public void windowActivated(final WindowEvent e) {
            FlatTitlePane.this.activeChanged(true);
            FlatTitlePane.this.updateNativeTitleBarHeightAndHitTestSpots();
            if (FlatTitlePane.this.hasNativeCustomDecoration()) {
                FlatNativeWindowBorder.WindowTopBorder.getInstance().repaintBorder(FlatTitlePane.this);
            }
            FlatTitlePane.this.repaintWindowBorder();
        }
        
        @Override
        public void windowDeactivated(final WindowEvent e) {
            FlatTitlePane.this.activeChanged(false);
            FlatTitlePane.this.updateNativeTitleBarHeightAndHitTestSpots();
            if (FlatTitlePane.this.hasNativeCustomDecoration()) {
                FlatNativeWindowBorder.WindowTopBorder.getInstance().repaintBorder(FlatTitlePane.this);
            }
            FlatTitlePane.this.repaintWindowBorder();
        }
        
        @Override
        public void windowStateChanged(final WindowEvent e) {
            FlatTitlePane.this.frameStateChanged();
            FlatTitlePane.this.updateNativeTitleBarHeightAndHitTestSpots();
        }
        
        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                if (e.getSource() == FlatTitlePane.this.iconLabel) {
                    FlatTitlePane.this.close();
                }
                else if (!FlatTitlePane.this.hasNativeCustomDecoration() && FlatTitlePane.this.window instanceof Frame && ((Frame)FlatTitlePane.this.window).isResizable()) {
                    final Frame frame = (Frame)FlatTitlePane.this.window;
                    if ((frame.getExtendedState() & 0x6) != 0x0) {
                        FlatTitlePane.this.restore();
                    }
                    else {
                        FlatTitlePane.this.maximize();
                    }
                }
            }
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            if (FlatTitlePane.this.window == null) {
                return;
            }
            this.dragOffset = SwingUtilities.convertPoint(FlatTitlePane.this, e.getPoint(), FlatTitlePane.this.window);
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(final MouseEvent e) {
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
        }
        
        @Override
        public void mouseDragged(final MouseEvent e) {
            if (FlatTitlePane.this.window == null) {
                return;
            }
            if (FlatTitlePane.this.hasNativeCustomDecoration()) {
                return;
            }
            if (FlatTitlePane.this.window instanceof Frame) {
                final Frame frame = (Frame)FlatTitlePane.this.window;
                final int state = frame.getExtendedState();
                if ((state & 0x6) != 0x0) {
                    final int maximizedWidth = FlatTitlePane.this.window.getWidth();
                    frame.setExtendedState(state & 0xFFFFFFF9);
                    final int restoredWidth = FlatTitlePane.this.window.getWidth();
                    final int center = restoredWidth / 2;
                    if (this.dragOffset.x > center) {
                        if (this.dragOffset.x > maximizedWidth - center) {
                            this.dragOffset.x = restoredWidth - (maximizedWidth - this.dragOffset.x);
                        }
                        else {
                            this.dragOffset.x = center;
                        }
                    }
                }
            }
            final int newX = e.getXOnScreen() - this.dragOffset.x;
            final int newY = e.getYOnScreen() - this.dragOffset.y;
            if (newX == FlatTitlePane.this.window.getX() && newY == FlatTitlePane.this.window.getY()) {
                return;
            }
            FlatTitlePane.this.window.setLocation(newX, newY);
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
        }
        
        @Override
        public void componentResized(final ComponentEvent e) {
            FlatTitlePane.this.updateNativeTitleBarHeightAndHitTestSpotsLater();
        }
        
        @Override
        public void componentShown(final ComponentEvent e) {
            FlatTitlePane.this.frameStateChanged();
        }
        
        @Override
        public void componentMoved(final ComponentEvent e) {
        }
        
        @Override
        public void componentHidden(final ComponentEvent e) {
        }
    }
}
