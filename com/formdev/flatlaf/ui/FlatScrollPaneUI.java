// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FlatScrollPaneUI extends BasicScrollPaneUI
{
    private Handler handler;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatScrollPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        final int focusWidth = UIManager.getInt("Component.focusWidth");
        LookAndFeel.installProperty(c, "opaque", focusWidth == 0);
        MigLayoutVisualPadding.install(this.scrollpane);
    }
    
    @Override
    public void uninstallUI(final JComponent c) {
        MigLayoutVisualPadding.uninstall(this.scrollpane);
        super.uninstallUI(c);
    }
    
    @Override
    protected void installListeners(final JScrollPane c) {
        super.installListeners(c);
        this.addViewportListeners(this.scrollpane.getViewport());
    }
    
    @Override
    protected void uninstallListeners(final JComponent c) {
        super.uninstallListeners(c);
        this.removeViewportListeners(this.scrollpane.getViewport());
        this.handler = null;
    }
    
    @Override
    protected MouseWheelListener createMouseWheelListener() {
        final MouseWheelListener superListener = super.createMouseWheelListener();
        return e -> {
            if (this.isSmoothScrollingEnabled() && this.scrollpane.isWheelScrollingEnabled() && e.getScrollType() == 0 && e.getPreciseWheelRotation() != 0.0 && e.getPreciseWheelRotation() != e.getWheelRotation()) {
                this.mouseWheelMovedSmooth(e);
            }
            else {
                superListener.mouseWheelMoved(e);
            }
        };
    }
    
    protected boolean isSmoothScrollingEnabled() {
        final Object smoothScrolling = this.scrollpane.getClientProperty("JScrollPane.smoothScrolling");
        if (smoothScrolling instanceof Boolean) {
            return (boolean)smoothScrolling;
        }
        return UIManager.getBoolean("ScrollPane.smoothScrolling");
    }
    
    private void mouseWheelMovedSmooth(final MouseWheelEvent e) {
        final JViewport viewport = this.scrollpane.getViewport();
        if (viewport == null) {
            return;
        }
        JScrollBar scrollbar = this.scrollpane.getVerticalScrollBar();
        if (scrollbar == null || !scrollbar.isVisible() || e.isShiftDown()) {
            scrollbar = this.scrollpane.getHorizontalScrollBar();
            if (scrollbar == null || !scrollbar.isVisible()) {
                return;
            }
        }
        e.consume();
        final double rotation = e.getPreciseWheelRotation();
        final int orientation = scrollbar.getOrientation();
        final Component view = viewport.getView();
        int unitIncrement;
        if (view instanceof Scrollable) {
            final Scrollable scrollable = (Scrollable)view;
            final Rectangle visibleRect = new Rectangle(viewport.getViewSize());
            unitIncrement = scrollable.getScrollableUnitIncrement(visibleRect, orientation, 1);
            if (unitIncrement > 0) {
                if (orientation == 1) {
                    final Rectangle rectangle = visibleRect;
                    rectangle.y += unitIncrement;
                    final Rectangle rectangle2 = visibleRect;
                    rectangle2.height -= unitIncrement;
                }
                else {
                    final Rectangle rectangle3 = visibleRect;
                    rectangle3.x += unitIncrement;
                    final Rectangle rectangle4 = visibleRect;
                    rectangle4.width -= unitIncrement;
                }
                final int unitIncrement2 = scrollable.getScrollableUnitIncrement(visibleRect, orientation, 1);
                if (unitIncrement2 > 0) {
                    unitIncrement = Math.min(unitIncrement, unitIncrement2);
                }
            }
        }
        else {
            final int direction = (rotation < 0.0) ? -1 : 1;
            unitIncrement = scrollbar.getUnitIncrement(direction);
        }
        final int viewportWH = (orientation == 1) ? viewport.getHeight() : viewport.getWidth();
        final int scrollIncrement = Math.min(unitIncrement * e.getScrollAmount(), viewportWH);
        final double delta = rotation * scrollIncrement;
        int idelta = (int)Math.round(delta);
        if (idelta == 0) {
            if (rotation > 0.0) {
                idelta = 1;
            }
            else if (rotation < 0.0) {
                idelta = -1;
            }
        }
        final int value = scrollbar.getValue();
        final int minValue = scrollbar.getMinimum();
        final int maxValue = scrollbar.getMaximum() - scrollbar.getModel().getExtent();
        final int newValue = Math.max(minValue, Math.min(value + idelta, maxValue));
        if (newValue != value) {
            scrollbar.setValue(newValue);
        }
    }
    
    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   javax/swing/plaf/basic/BasicScrollPaneUI.createPropertyChangeListener:()Ljava/beans/PropertyChangeListener;
        //     4: astore_1        /* superListener */
        //     5: aload_0         /* this */
        //     6: aload_1         /* superListener */
        //     7: invokedynamic   BootstrapMethod #1, propertyChange:(Lcom/formdev/flatlaf/ui/FlatScrollPaneUI;Ljava/beans/PropertyChangeListener;)Ljava/beans/PropertyChangeListener;
        //    12: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:252)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:185)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.nameVariables(AstMethodBodyBuilder.java:1473)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.populateVariables(AstMethodBodyBuilder.java:1402)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:213)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:94)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:840)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:733)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:610)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:577)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:193)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:160)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:135)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler();
        }
        return this.handler;
    }
    
    @Override
    protected void updateViewport(final PropertyChangeEvent e) {
        super.updateViewport(e);
        final JViewport oldViewport = (JViewport)e.getOldValue();
        final JViewport newViewport = (JViewport)e.getNewValue();
        this.removeViewportListeners(oldViewport);
        this.addViewportListeners(newViewport);
    }
    
    private void addViewportListeners(final JViewport viewport) {
        if (viewport == null) {
            return;
        }
        viewport.addContainerListener(this.getHandler());
        final Component view = viewport.getView();
        if (view != null) {
            view.addFocusListener(this.getHandler());
        }
    }
    
    private void removeViewportListeners(final JViewport viewport) {
        if (viewport == null) {
            return;
        }
        viewport.removeContainerListener(this.getHandler());
        final Component view = viewport.getView();
        if (view != null) {
            view.removeFocusListener(this.getHandler());
        }
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        if (c.isOpaque()) {
            FlatUIUtils.paintParentBackground(g, c);
            final Insets insets = c.getInsets();
            g.setColor(c.getBackground());
            g.fillRect(insets.left, insets.top, c.getWidth() - insets.left - insets.right, c.getHeight() - insets.top - insets.bottom);
        }
        this.paint(g, c);
    }
    
    public static boolean isPermanentFocusOwner(final JScrollPane scrollPane) {
        final JViewport viewport = scrollPane.getViewport();
        final Component view = (viewport != null) ? viewport.getView() : null;
        if (view == null) {
            return false;
        }
        if (FlatUIUtils.isPermanentFocusOwner(view)) {
            return true;
        }
        if ((view instanceof JTable && ((JTable)view).isEditing()) || (view instanceof JTree && ((JTree)view).isEditing())) {
            final Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            if (focusOwner != null) {
                return SwingUtilities.isDescendingFrom(focusOwner, view);
            }
        }
        return false;
    }
    
    private class Handler implements ContainerListener, FocusListener
    {
        @Override
        public void componentAdded(final ContainerEvent e) {
            e.getChild().addFocusListener(this);
        }
        
        @Override
        public void componentRemoved(final ContainerEvent e) {
            e.getChild().removeFocusListener(this);
        }
        
        @Override
        public void focusGained(final FocusEvent e) {
            FlatScrollPaneUI.this.scrollpane.repaint();
        }
        
        @Override
        public void focusLost(final FocusEvent e) {
            FlatScrollPaneUI.this.scrollpane.repaint();
        }
    }
}
