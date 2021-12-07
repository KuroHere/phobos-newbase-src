// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.*;
import java.lang.reflect.*;
import com.formdev.flatlaf.util.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;

public class FlatPopupFactory extends PopupFactory
{
    private Method java8getPopupMethod;
    private Method java9getPopupMethod;
    
    @Override
    public Popup getPopup(final Component owner, final Component contents, int x, int y) throws IllegalArgumentException {
        final Point pt = this.fixToolTipLocation(owner, contents, x, y);
        if (pt != null) {
            x = pt.x;
            y = pt.y;
        }
        final boolean forceHeavyWeight = this.isOptionEnabled(owner, contents, "Popup.forceHeavyWeight", "Popup.forceHeavyWeight");
        if (!this.isOptionEnabled(owner, contents, "Popup.dropShadowPainted", "Popup.dropShadowPainted") || SystemInfo.isProjector || SystemInfo.isWebswing) {
            return new NonFlashingPopup(this.getPopupForScreenOfOwner(owner, contents, x, y, forceHeavyWeight), contents);
        }
        if (SystemInfo.isMacOS || SystemInfo.isLinux) {
            return new NonFlashingPopup(this.getPopupForScreenOfOwner(owner, contents, x, y, true), contents);
        }
        return new DropShadowPopup(this.getPopupForScreenOfOwner(owner, contents, x, y, forceHeavyWeight), owner, contents);
    }
    
    private Popup getPopupForScreenOfOwner(final Component owner, final Component contents, final int x, final int y, final boolean forceHeavyWeight) throws IllegalArgumentException {
        int count = 0;
        while (true) {
            final Popup popup = forceHeavyWeight ? this.getHeavyWeightPopup(owner, contents, x, y) : super.getPopup(owner, contents, x, y);
            final Window popupWindow = SwingUtilities.windowForComponent(contents);
            if (popupWindow == null || owner == null || popupWindow.getGraphicsConfiguration() == owner.getGraphicsConfiguration()) {
                return popup;
            }
            if (popupWindow instanceof JWindow) {
                ((JWindow)popupWindow).getContentPane().removeAll();
            }
            popupWindow.dispose();
            if (++count > 10) {
                return popup;
            }
        }
    }
    
    private static void showPopupAndFixLocation(final Popup popup, final Window popupWindow) {
        if (popupWindow != null) {
            final int x = popupWindow.getX();
            final int y = popupWindow.getY();
            popup.show();
            if (popupWindow.getX() != x || popupWindow.getY() != y) {
                popupWindow.setLocation(x, y);
            }
        }
        else {
            popup.show();
        }
    }
    
    private boolean isOptionEnabled(final Component owner, final Component contents, final String clientKey, final String uiKey) {
        if (owner instanceof JComponent) {
            final Boolean b = FlatClientProperties.clientPropertyBooleanStrict((JComponent)owner, clientKey, null);
            if (b != null) {
                return b;
            }
        }
        if (contents instanceof JComponent) {
            final Boolean b = FlatClientProperties.clientPropertyBooleanStrict((JComponent)contents, clientKey, null);
            if (b != null) {
                return b;
            }
        }
        return UIManager.getBoolean(uiKey);
    }
    
    private Popup getHeavyWeightPopup(final Component owner, final Component contents, final int x, final int y) throws IllegalArgumentException {
        try {
            if (SystemInfo.isJava_9_orLater) {
                if (this.java9getPopupMethod == null) {
                    this.java9getPopupMethod = PopupFactory.class.getDeclaredMethod("getPopup", Component.class, Component.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE);
                }
                return (Popup)this.java9getPopupMethod.invoke(this, owner, contents, x, y, true);
            }
            try {
                if (this.java8getPopupMethod == null) {
                    (this.java8getPopupMethod = PopupFactory.class.getDeclaredMethod("getPopup", Component.class, Component.class, Integer.TYPE, Integer.TYPE, Integer.TYPE)).setAccessible(true);
                }
                return (Popup)this.java8getPopupMethod.invoke(this, owner, contents, x, y, 2);
            }
            catch (SecurityException | IllegalAccessException ex2) {
                final Exception ex3;
                final Exception ex = ex3;
                return null;
            }
        }
        catch (NoSuchMethodException ex4) {}
        catch (SecurityException ex5) {}
        catch (IllegalAccessException ex6) {}
        catch (InvocationTargetException ex7) {}
    }
    
    private Point fixToolTipLocation(final Component owner, final Component contents, final int x, final int y) {
        if (!(contents instanceof JToolTip) || !this.wasInvokedFromToolTipManager()) {
            return null;
        }
        final PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            return null;
        }
        final Point mouseLocation = pointerInfo.getLocation();
        final Dimension tipSize = contents.getPreferredSize();
        final Rectangle tipBounds = new Rectangle(x, y, tipSize.width, tipSize.height);
        if (!tipBounds.contains(mouseLocation)) {
            return null;
        }
        GraphicsConfiguration gc = null;
        for (final GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            final GraphicsConfiguration dgc = device.getDefaultConfiguration();
            if (dgc.getBounds().contains(mouseLocation)) {
                gc = dgc;
                break;
            }
        }
        if (gc == null) {
            gc = owner.getGraphicsConfiguration();
        }
        if (gc == null) {
            return null;
        }
        final Rectangle screenBounds = gc.getBounds();
        final Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
        final int screenTop = screenBounds.y + screenInsets.top;
        final int newY = mouseLocation.y - tipSize.height - UIScale.scale(20);
        if (newY < screenTop) {
            return null;
        }
        return new Point(x, newY);
    }
    
    private boolean wasInvokedFromToolTipManager() {
        return StackUtils.wasInvokedFrom(ToolTipManager.class.getName(), "showTipWindow", 8);
    }
    
    private class NonFlashingPopup extends Popup
    {
        private Popup delegate;
        private Component contents;
        protected Window popupWindow;
        private Color oldPopupWindowBackground;
        
        NonFlashingPopup(final Popup delegate, final Component contents) {
            this.delegate = delegate;
            this.contents = contents;
            this.popupWindow = SwingUtilities.windowForComponent(contents);
            if (this.popupWindow != null) {
                this.oldPopupWindowBackground = this.popupWindow.getBackground();
                this.popupWindow.setBackground(contents.getBackground());
            }
        }
        
        @Override
        public void show() {
            if (this.delegate != null) {
                showPopupAndFixLocation(this.delegate, this.popupWindow);
                if (this.contents instanceof JToolTip && this.popupWindow == null) {
                    final Container parent = this.contents.getParent();
                    if (parent instanceof JPanel) {
                        final Dimension prefSize = parent.getPreferredSize();
                        if (!prefSize.equals(parent.getSize())) {
                            final Container mediumWeightPanel = SwingUtilities.getAncestorOfClass(Panel.class, parent);
                            final Container c = (mediumWeightPanel != null) ? mediumWeightPanel : parent;
                            c.setSize(prefSize);
                            c.validate();
                        }
                    }
                }
            }
        }
        
        @Override
        public void hide() {
            if (this.delegate != null) {
                this.delegate.hide();
                this.delegate = null;
                this.contents = null;
            }
            if (this.popupWindow != null) {
                this.popupWindow.setBackground(this.oldPopupWindowBackground);
                this.popupWindow = null;
            }
        }
    }
    
    private class DropShadowPopup extends NonFlashingPopup
    {
        private final Component owner;
        private JComponent lightComp;
        private Border oldBorder;
        private boolean oldOpaque;
        private boolean mediumWeightShown;
        private Panel mediumWeightPanel;
        private JPanel dropShadowPanel;
        private ComponentListener mediumPanelListener;
        private Popup dropShadowDelegate;
        private Window dropShadowWindow;
        private Color oldDropShadowWindowBackground;
        
        DropShadowPopup(final Popup delegate, final Component owner, final Component contents) {
            super(delegate, contents);
            this.owner = owner;
            final Dimension size = contents.getPreferredSize();
            if (size.width <= 0 || size.height <= 0) {
                return;
            }
            if (this.popupWindow != null) {
                final JPanel dropShadowPanel = new JPanel();
                dropShadowPanel.setBorder(this.createDropShadowBorder());
                dropShadowPanel.setOpaque(false);
                final Dimension prefSize = this.popupWindow.getPreferredSize();
                final Insets insets = dropShadowPanel.getInsets();
                dropShadowPanel.setPreferredSize(new Dimension(prefSize.width + insets.left + insets.right, prefSize.height + insets.top + insets.bottom));
                final int x = this.popupWindow.getX() - insets.left;
                final int y = this.popupWindow.getY() - insets.top;
                this.dropShadowDelegate = FlatPopupFactory.this.getPopupForScreenOfOwner(owner, dropShadowPanel, x, y, true);
                this.dropShadowWindow = SwingUtilities.windowForComponent(dropShadowPanel);
                if (this.dropShadowWindow != null) {
                    this.oldDropShadowWindowBackground = this.dropShadowWindow.getBackground();
                    this.dropShadowWindow.setBackground(new Color(0, true));
                }
            }
            else {
                this.mediumWeightPanel = (Panel)SwingUtilities.getAncestorOfClass(Panel.class, contents);
                if (this.mediumWeightPanel != null) {
                    (this.dropShadowPanel = new JPanel()).setBorder(this.createDropShadowBorder());
                    this.dropShadowPanel.setOpaque(false);
                    this.dropShadowPanel.setSize(FlatUIUtils.addInsets(this.mediumWeightPanel.getSize(), this.dropShadowPanel.getInsets()));
                }
                else {
                    final Container p = contents.getParent();
                    if (!(p instanceof JComponent)) {
                        return;
                    }
                    this.lightComp = (JComponent)p;
                    this.oldBorder = this.lightComp.getBorder();
                    this.oldOpaque = this.lightComp.isOpaque();
                    this.lightComp.setBorder(this.createDropShadowBorder());
                    this.lightComp.setOpaque(false);
                    this.lightComp.setSize(this.lightComp.getPreferredSize());
                }
            }
        }
        
        private Border createDropShadowBorder() {
            return new FlatDropShadowBorder(UIManager.getColor("Popup.dropShadowColor"), UIManager.getInsets("Popup.dropShadowInsets"), FlatUIUtils.getUIFloat("Popup.dropShadowOpacity", 0.5f));
        }
        
        @Override
        public void show() {
            if (this.dropShadowDelegate != null) {
                showPopupAndFixLocation(this.dropShadowDelegate, this.dropShadowWindow);
            }
            if (this.mediumWeightPanel != null) {
                this.showMediumWeightDropShadow();
            }
            super.show();
            if (this.lightComp != null) {
                final Insets insets = this.lightComp.getInsets();
                if (insets.left != 0 || insets.top != 0) {
                    this.lightComp.setLocation(this.lightComp.getX() - insets.left, this.lightComp.getY() - insets.top);
                }
            }
        }
        
        @Override
        public void hide() {
            if (this.dropShadowDelegate != null) {
                this.dropShadowDelegate.hide();
                this.dropShadowDelegate = null;
            }
            if (this.mediumWeightPanel != null) {
                this.hideMediumWeightDropShadow();
                this.dropShadowPanel = null;
                this.mediumWeightPanel = null;
            }
            super.hide();
            if (this.dropShadowWindow != null) {
                this.dropShadowWindow.setBackground(this.oldDropShadowWindowBackground);
                this.dropShadowWindow = null;
            }
            if (this.lightComp != null) {
                this.lightComp.setBorder(this.oldBorder);
                this.lightComp.setOpaque(this.oldOpaque);
                this.lightComp = null;
            }
        }
        
        private void showMediumWeightDropShadow() {
            if (this.mediumWeightShown) {
                return;
            }
            this.mediumWeightShown = true;
            if (this.owner == null) {
                return;
            }
            final Window window = SwingUtilities.windowForComponent(this.owner);
            if (!(window instanceof RootPaneContainer)) {
                return;
            }
            this.dropShadowPanel.setVisible(false);
            final JLayeredPane layeredPane = ((RootPaneContainer)window).getLayeredPane();
            layeredPane.add(this.dropShadowPanel, JLayeredPane.POPUP_LAYER, 0);
            this.moveMediumWeightDropShadow();
            this.resizeMediumWeightDropShadow();
            this.mediumPanelListener = new ComponentListener() {
                @Override
                public void componentShown(final ComponentEvent e) {
                    if (DropShadowPopup.this.dropShadowPanel != null) {
                        DropShadowPopup.this.dropShadowPanel.setVisible(true);
                    }
                }
                
                @Override
                public void componentHidden(final ComponentEvent e) {
                    if (DropShadowPopup.this.dropShadowPanel != null) {
                        DropShadowPopup.this.dropShadowPanel.setVisible(false);
                    }
                }
                
                @Override
                public void componentMoved(final ComponentEvent e) {
                    DropShadowPopup.this.moveMediumWeightDropShadow();
                }
                
                @Override
                public void componentResized(final ComponentEvent e) {
                    DropShadowPopup.this.resizeMediumWeightDropShadow();
                }
            };
            this.mediumWeightPanel.addComponentListener(this.mediumPanelListener);
        }
        
        private void hideMediumWeightDropShadow() {
            this.mediumWeightPanel.removeComponentListener(this.mediumPanelListener);
            final Container parent = this.dropShadowPanel.getParent();
            if (parent != null) {
                final Rectangle bounds = this.dropShadowPanel.getBounds();
                parent.remove(this.dropShadowPanel);
                parent.repaint(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
        
        private void moveMediumWeightDropShadow() {
            if (this.dropShadowPanel != null && this.mediumWeightPanel != null) {
                final Point location = this.mediumWeightPanel.getLocation();
                final Insets insets = this.dropShadowPanel.getInsets();
                this.dropShadowPanel.setLocation(location.x - insets.left, location.y - insets.top);
            }
        }
        
        private void resizeMediumWeightDropShadow() {
            if (this.dropShadowPanel != null && this.mediumWeightPanel != null) {
                this.dropShadowPanel.setSize(FlatUIUtils.addInsets(this.mediumWeightPanel.getSize(), this.dropShadowPanel.getInsets()));
            }
        }
    }
}
