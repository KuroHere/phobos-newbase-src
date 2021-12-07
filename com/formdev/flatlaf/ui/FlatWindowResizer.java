// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import java.beans.*;
import java.util.function.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class FlatWindowResizer implements PropertyChangeListener, ComponentListener
{
    protected static final Integer WINDOW_RESIZER_LAYER;
    protected final JComponent resizeComp;
    protected final int borderDragThickness;
    protected final int cornerDragWidth;
    protected final boolean honorFrameMinimumSizeOnResize;
    protected final boolean honorDialogMinimumSizeOnResize;
    protected final DragBorderComponent topDragComp;
    protected final DragBorderComponent bottomDragComp;
    protected final DragBorderComponent leftDragComp;
    protected final DragBorderComponent rightDragComp;
    
    protected FlatWindowResizer(final JComponent resizeComp) {
        this.borderDragThickness = FlatUIUtils.getUIInt("RootPane.borderDragThickness", 5);
        this.cornerDragWidth = FlatUIUtils.getUIInt("RootPane.cornerDragWidth", 16);
        this.honorFrameMinimumSizeOnResize = UIManager.getBoolean("RootPane.honorFrameMinimumSizeOnResize");
        this.honorDialogMinimumSizeOnResize = UIManager.getBoolean("RootPane.honorDialogMinimumSizeOnResize");
        this.resizeComp = resizeComp;
        this.topDragComp = this.createDragBorderComponent(6, 8, 7);
        this.bottomDragComp = this.createDragBorderComponent(4, 9, 5);
        this.leftDragComp = this.createDragBorderComponent(6, 10, 4);
        this.rightDragComp = this.createDragBorderComponent(7, 11, 5);
        final Container cont = (resizeComp instanceof JRootPane) ? ((JRootPane)resizeComp).getLayeredPane() : resizeComp;
        final Object cons = (cont instanceof JLayeredPane) ? FlatWindowResizer.WINDOW_RESIZER_LAYER : null;
        cont.add(this.topDragComp, cons, 0);
        cont.add(this.bottomDragComp, cons, 1);
        cont.add(this.leftDragComp, cons, 2);
        cont.add(this.rightDragComp, cons, 3);
        resizeComp.addComponentListener(this);
        resizeComp.addPropertyChangeListener("ancestor", this);
        if (resizeComp.isDisplayable()) {
            this.addNotify();
        }
    }
    
    protected DragBorderComponent createDragBorderComponent(final int leadingResizeDir, final int centerResizeDir, final int trailingResizeDir) {
        return new DragBorderComponent(leadingResizeDir, centerResizeDir, trailingResizeDir);
    }
    
    public void uninstall() {
        this.removeNotify();
        this.resizeComp.removeComponentListener(this);
        this.resizeComp.removePropertyChangeListener("ancestor", this);
        final Container cont = this.topDragComp.getParent();
        cont.remove(this.topDragComp);
        cont.remove(this.bottomDragComp);
        cont.remove(this.leftDragComp);
        cont.remove(this.rightDragComp);
    }
    
    public void doLayout() {
        if (!this.topDragComp.isVisible()) {
            return;
        }
        final int x = 0;
        final int y = 0;
        final int width = this.resizeComp.getWidth();
        final int height = this.resizeComp.getHeight();
        if (width == 0 || height == 0) {
            return;
        }
        final Insets resizeInsets = this.getResizeInsets();
        final int thickness = UIScale.scale(this.borderDragThickness);
        final int topThickness = Math.max(resizeInsets.top, thickness);
        final int bottomThickness = Math.max(resizeInsets.bottom, thickness);
        final int leftThickness = Math.max(resizeInsets.left, thickness);
        final int rightThickness = Math.max(resizeInsets.right, thickness);
        final int y2 = y + topThickness;
        final int height2 = height - topThickness - bottomThickness;
        this.topDragComp.setBounds(x, y, width, topThickness);
        this.bottomDragComp.setBounds(x, y + height - bottomThickness, width, bottomThickness);
        this.leftDragComp.setBounds(x, y2, leftThickness, height2);
        this.rightDragComp.setBounds(x + width - rightThickness, y2, rightThickness, height2);
        final int cornerDelta = UIScale.scale(this.cornerDragWidth - this.borderDragThickness);
        this.topDragComp.setCornerDragWidths(leftThickness + cornerDelta, rightThickness + cornerDelta);
        this.bottomDragComp.setCornerDragWidths(leftThickness + cornerDelta, rightThickness + cornerDelta);
        this.leftDragComp.setCornerDragWidths(cornerDelta, cornerDelta);
        this.rightDragComp.setCornerDragWidths(cornerDelta, cornerDelta);
    }
    
    protected Insets getResizeInsets() {
        return new Insets(0, 0, 0, 0);
    }
    
    protected void addNotify() {
        this.updateVisibility();
    }
    
    protected void removeNotify() {
        this.updateVisibility();
    }
    
    protected void updateVisibility() {
        final boolean visible = this.isWindowResizable();
        if (visible == this.topDragComp.isVisible()) {
            return;
        }
        this.topDragComp.setVisible(visible);
        this.bottomDragComp.setVisible(visible);
        this.leftDragComp.setVisible(visible);
        this.rightDragComp.setEnabled(visible);
        if (visible) {
            this.rightDragComp.setVisible(true);
            this.doLayout();
        }
        else {
            this.rightDragComp.setBounds(0, 0, 1, 1);
        }
    }
    
    boolean isDialog() {
        return false;
    }
    
    protected abstract boolean isWindowResizable();
    
    protected abstract Rectangle getWindowBounds();
    
    protected abstract void setWindowBounds(final Rectangle p0);
    
    protected abstract boolean limitToParentBounds();
    
    protected abstract Rectangle getParentBounds();
    
    protected abstract boolean honorMinimumSizeOnResize();
    
    protected abstract boolean honorMaximumSizeOnResize();
    
    protected abstract Dimension getWindowMinimumSize();
    
    protected abstract Dimension getWindowMaximumSize();
    
    protected void beginResizing(final int direction) {
    }
    
    protected void endResizing() {
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "ancestor": {
                if (e.getNewValue() != null) {
                    this.addNotify();
                    break;
                }
                this.removeNotify();
                break;
            }
            case "resizable": {
                this.updateVisibility();
                break;
            }
        }
    }
    
    @Override
    public void componentResized(final ComponentEvent e) {
        this.doLayout();
    }
    
    @Override
    public void componentMoved(final ComponentEvent e) {
    }
    
    @Override
    public void componentShown(final ComponentEvent e) {
    }
    
    @Override
    public void componentHidden(final ComponentEvent e) {
    }
    
    static {
        WINDOW_RESIZER_LAYER = JLayeredPane.DRAG_LAYER + 1;
    }
    
    public static class WindowResizer extends FlatWindowResizer implements WindowStateListener
    {
        protected Window window;
        
        public WindowResizer(final JRootPane rootPane) {
            super(rootPane);
        }
        
        @Override
        protected void addNotify() {
            final Container parent = this.resizeComp.getParent();
            this.window = ((parent instanceof Window) ? ((Window)parent) : null);
            if (this.window instanceof Frame) {
                this.window.addPropertyChangeListener("resizable", this);
                this.window.addWindowStateListener(this);
            }
            super.addNotify();
        }
        
        @Override
        protected void removeNotify() {
            if (this.window instanceof Frame) {
                this.window.removePropertyChangeListener("resizable", this);
                this.window.removeWindowStateListener(this);
            }
            this.window = null;
            super.removeNotify();
        }
        
        @Override
        protected boolean isWindowResizable() {
            if (FlatUIUtils.isFullScreen(this.resizeComp)) {
                return false;
            }
            if (this.window instanceof Frame) {
                return ((Frame)this.window).isResizable() && (((Frame)this.window).getExtendedState() & 0x6) == 0x0;
            }
            return this.window instanceof Dialog && ((Dialog)this.window).isResizable();
        }
        
        @Override
        protected Rectangle getWindowBounds() {
            return this.window.getBounds();
        }
        
        @Override
        protected void setWindowBounds(final Rectangle r) {
            this.window.setBounds(r);
            this.doLayout();
            if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
                this.window.validate();
                this.resizeComp.repaint();
            }
        }
        
        @Override
        protected boolean limitToParentBounds() {
            return false;
        }
        
        @Override
        protected Rectangle getParentBounds() {
            return null;
        }
        
        @Override
        protected boolean honorMinimumSizeOnResize() {
            return (this.honorFrameMinimumSizeOnResize && this.window instanceof Frame) || (this.honorDialogMinimumSizeOnResize && this.window instanceof Dialog);
        }
        
        @Override
        protected boolean honorMaximumSizeOnResize() {
            return false;
        }
        
        @Override
        protected Dimension getWindowMinimumSize() {
            return this.window.getMinimumSize();
        }
        
        @Override
        protected Dimension getWindowMaximumSize() {
            return this.window.getMaximumSize();
        }
        
        @Override
        boolean isDialog() {
            return this.window instanceof Dialog;
        }
        
        @Override
        public void windowStateChanged(final WindowEvent e) {
            this.updateVisibility();
        }
    }
    
    public static class InternalFrameResizer extends FlatWindowResizer
    {
        protected final Supplier<DesktopManager> desktopManager;
        
        public InternalFrameResizer(final JInternalFrame frame, final Supplier<DesktopManager> desktopManager) {
            super(frame);
            this.desktopManager = desktopManager;
            frame.addPropertyChangeListener("resizable", this);
        }
        
        @Override
        public void uninstall() {
            this.getFrame().removePropertyChangeListener("resizable", this);
            super.uninstall();
        }
        
        private JInternalFrame getFrame() {
            return (JInternalFrame)this.resizeComp;
        }
        
        @Override
        protected Insets getResizeInsets() {
            return this.getFrame().getInsets();
        }
        
        @Override
        protected boolean isWindowResizable() {
            return this.getFrame().isResizable();
        }
        
        @Override
        protected Rectangle getWindowBounds() {
            return this.getFrame().getBounds();
        }
        
        @Override
        protected void setWindowBounds(final Rectangle r) {
            this.desktopManager.get().resizeFrame(this.getFrame(), r.x, r.y, r.width, r.height);
        }
        
        @Override
        protected boolean limitToParentBounds() {
            return true;
        }
        
        @Override
        protected Rectangle getParentBounds() {
            return this.getFrame().getParent().getBounds();
        }
        
        @Override
        protected boolean honorMinimumSizeOnResize() {
            return true;
        }
        
        @Override
        protected boolean honorMaximumSizeOnResize() {
            return true;
        }
        
        @Override
        protected Dimension getWindowMinimumSize() {
            return this.getFrame().getMinimumSize();
        }
        
        @Override
        protected Dimension getWindowMaximumSize() {
            return this.getFrame().getMaximumSize();
        }
        
        @Override
        protected void beginResizing(final int direction) {
            this.desktopManager.get().beginResizingFrame(this.getFrame(), direction);
        }
        
        @Override
        protected void endResizing() {
            this.desktopManager.get().endResizingFrame(this.getFrame());
        }
    }
    
    protected class DragBorderComponent extends JComponent implements MouseListener, MouseMotionListener
    {
        private final int leadingResizeDir;
        private final int centerResizeDir;
        private final int trailingResizeDir;
        private int resizeDir;
        private int leadingCornerDragWidth;
        private int trailingCornerDragWidth;
        private int dragLeftOffset;
        private int dragRightOffset;
        private int dragTopOffset;
        private int dragBottomOffset;
        
        protected DragBorderComponent(final int leadingResizeDir, final int centerResizeDir, final int trailingResizeDir) {
            this.resizeDir = -1;
            this.leadingResizeDir = leadingResizeDir;
            this.centerResizeDir = centerResizeDir;
            this.trailingResizeDir = trailingResizeDir;
            this.setResizeDir(centerResizeDir);
            this.setVisible(false);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }
        
        void setCornerDragWidths(final int leading, final int trailing) {
            this.leadingCornerDragWidth = leading;
            this.trailingCornerDragWidth = trailing;
        }
        
        protected void setResizeDir(final int resizeDir) {
            if (this.resizeDir == resizeDir) {
                return;
            }
            this.resizeDir = resizeDir;
            this.setCursor(Cursor.getPredefinedCursor(resizeDir));
        }
        
        @Override
        public Dimension getPreferredSize() {
            final int thickness = UIScale.scale(FlatWindowResizer.this.borderDragThickness);
            return new Dimension(thickness, thickness);
        }
        
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintChildren(g);
            FlatWindowResizer.this.updateVisibility();
        }
        
        @Override
        public void mouseClicked(final MouseEvent e) {
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            if (!FlatWindowResizer.this.isWindowResizable()) {
                return;
            }
            final int xOnScreen = e.getXOnScreen();
            final int yOnScreen = e.getYOnScreen();
            final Rectangle windowBounds = FlatWindowResizer.this.getWindowBounds();
            this.dragLeftOffset = xOnScreen - windowBounds.x;
            this.dragTopOffset = yOnScreen - windowBounds.y;
            this.dragRightOffset = windowBounds.x + windowBounds.width - xOnScreen;
            this.dragBottomOffset = windowBounds.y + windowBounds.height - yOnScreen;
            int direction = 0;
            switch (this.resizeDir) {
                case 8: {
                    direction = 1;
                    break;
                }
                case 9: {
                    direction = 5;
                    break;
                }
                case 10: {
                    direction = 7;
                    break;
                }
                case 11: {
                    direction = 3;
                    break;
                }
                case 6: {
                    direction = 8;
                    break;
                }
                case 7: {
                    direction = 2;
                    break;
                }
                case 4: {
                    direction = 6;
                    break;
                }
                case 5: {
                    direction = 4;
                    break;
                }
            }
            FlatWindowResizer.this.beginResizing(direction);
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
            if (!FlatWindowResizer.this.isWindowResizable()) {
                return;
            }
            final int n = 0;
            this.dragBottomOffset = n;
            this.dragTopOffset = n;
            this.dragRightOffset = n;
            this.dragLeftOffset = n;
            FlatWindowResizer.this.endResizing();
        }
        
        @Override
        public void mouseEntered(final MouseEvent e) {
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
            final boolean topOrBottom = this.centerResizeDir == 8 || this.centerResizeDir == 9;
            final int xy = topOrBottom ? e.getX() : e.getY();
            final int wh = topOrBottom ? this.getWidth() : this.getHeight();
            this.setResizeDir((xy <= this.leadingCornerDragWidth) ? this.leadingResizeDir : ((xy >= wh - this.trailingCornerDragWidth) ? this.trailingResizeDir : this.centerResizeDir));
        }
        
        @Override
        public void mouseDragged(final MouseEvent e) {
            if (!FlatWindowResizer.this.isWindowResizable()) {
                return;
            }
            final int xOnScreen = e.getXOnScreen();
            final int yOnScreen = e.getYOnScreen();
            final Rectangle oldBounds = FlatWindowResizer.this.getWindowBounds();
            final Rectangle newBounds = new Rectangle(oldBounds);
            if (this.resizeDir == 8 || this.resizeDir == 6 || this.resizeDir == 7) {
                newBounds.y = yOnScreen - this.dragTopOffset;
                if (FlatWindowResizer.this.limitToParentBounds() && newBounds.y < 0) {
                    newBounds.y = 0;
                }
                final Rectangle rectangle = newBounds;
                rectangle.height += oldBounds.y - newBounds.y;
            }
            if (this.resizeDir == 9 || this.resizeDir == 4 || this.resizeDir == 5) {
                newBounds.height = yOnScreen + this.dragBottomOffset - newBounds.y;
                if (FlatWindowResizer.this.limitToParentBounds()) {
                    final int parentHeight = FlatWindowResizer.this.getParentBounds().height;
                    if (newBounds.y + newBounds.height > parentHeight) {
                        newBounds.height = parentHeight - newBounds.y;
                    }
                }
            }
            if (this.resizeDir == 10 || this.resizeDir == 6 || this.resizeDir == 4) {
                newBounds.x = xOnScreen - this.dragLeftOffset;
                if (FlatWindowResizer.this.limitToParentBounds() && newBounds.x < 0) {
                    newBounds.x = 0;
                }
                final Rectangle rectangle2 = newBounds;
                rectangle2.width += oldBounds.x - newBounds.x;
            }
            if (this.resizeDir == 11 || this.resizeDir == 7 || this.resizeDir == 5) {
                newBounds.width = xOnScreen + this.dragRightOffset - newBounds.x;
                if (FlatWindowResizer.this.limitToParentBounds()) {
                    final int parentWidth = FlatWindowResizer.this.getParentBounds().width;
                    if (newBounds.x + newBounds.width > parentWidth) {
                        newBounds.width = parentWidth - newBounds.x;
                    }
                }
            }
            Dimension minimumSize = FlatWindowResizer.this.honorMinimumSizeOnResize() ? FlatWindowResizer.this.getWindowMinimumSize() : null;
            if (minimumSize == null) {
                minimumSize = UIScale.scale(new Dimension(150, 50));
            }
            if (newBounds.width < minimumSize.width) {
                this.changeWidth(oldBounds, newBounds, minimumSize.width);
            }
            if (newBounds.height < minimumSize.height) {
                this.changeHeight(oldBounds, newBounds, minimumSize.height);
            }
            if (FlatWindowResizer.this.honorMaximumSizeOnResize()) {
                final Dimension maximumSize = FlatWindowResizer.this.getWindowMaximumSize();
                if (newBounds.width > maximumSize.width) {
                    this.changeWidth(oldBounds, newBounds, maximumSize.width);
                }
                if (newBounds.height > maximumSize.height) {
                    this.changeHeight(oldBounds, newBounds, maximumSize.height);
                }
            }
            if (!newBounds.equals(oldBounds)) {
                FlatWindowResizer.this.setWindowBounds(newBounds);
            }
        }
        
        private void changeWidth(final Rectangle oldBounds, final Rectangle newBounds, final int width) {
            if (newBounds.x != oldBounds.x) {
                newBounds.x -= width - newBounds.width;
            }
            newBounds.width = width;
        }
        
        private void changeHeight(final Rectangle oldBounds, final Rectangle newBounds, final int height) {
            if (newBounds.y != oldBounds.y) {
                newBounds.y -= height - newBounds.height;
            }
            newBounds.height = height;
        }
    }
}
