// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FlatSplitPaneUI extends BasicSplitPaneUI
{
    protected String arrowType;
    protected Color oneTouchArrowColor;
    protected Color oneTouchHoverArrowColor;
    protected Color oneTouchPressedArrowColor;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatSplitPaneUI();
    }
    
    @Override
    protected void installDefaults() {
        this.arrowType = UIManager.getString("Component.arrowType");
        this.oneTouchArrowColor = UIManager.getColor("SplitPaneDivider.oneTouchArrowColor");
        this.oneTouchHoverArrowColor = UIManager.getColor("SplitPaneDivider.oneTouchHoverArrowColor");
        this.oneTouchPressedArrowColor = UIManager.getColor("SplitPaneDivider.oneTouchPressedArrowColor");
        super.installDefaults();
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.oneTouchArrowColor = null;
        this.oneTouchHoverArrowColor = null;
        this.oneTouchPressedArrowColor = null;
    }
    
    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new FlatSplitPaneDivider(this);
    }
    
    protected class FlatSplitPaneDivider extends BasicSplitPaneDivider
    {
        protected final String style;
        protected final Color gripColor;
        protected final int gripDotCount;
        protected final int gripDotSize;
        protected final int gripGap;
        final /* synthetic */ FlatSplitPaneUI this$0;
        
        protected FlatSplitPaneDivider(final BasicSplitPaneUI ui) {
            super(ui);
            this.style = UIManager.getString("SplitPaneDivider.style");
            this.gripColor = UIManager.getColor("SplitPaneDivider.gripColor");
            this.gripDotCount = FlatUIUtils.getUIInt("SplitPaneDivider.gripDotCount", 3);
            this.gripDotSize = FlatUIUtils.getUIInt("SplitPaneDivider.gripDotSize", 3);
            this.gripGap = FlatUIUtils.getUIInt("SplitPaneDivider.gripGap", 2);
            this.setLayout(new FlatDividerLayout());
        }
        
        @Override
        public void setDividerSize(final int newSize) {
            super.setDividerSize(UIScale.scale(newSize));
        }
        
        @Override
        protected JButton createLeftOneTouchButton() {
            return new FlatOneTouchButton(true);
        }
        
        @Override
        protected JButton createRightOneTouchButton() {
            return new FlatOneTouchButton(false);
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            super.propertyChange(e);
            final String propertyName = e.getPropertyName();
            switch (propertyName) {
                case "dividerLocation": {
                    this.revalidate();
                    break;
                }
            }
        }
        
        @Override
        public void paint(final Graphics g) {
            super.paint(g);
            if ("plain".equals(this.style)) {
                return;
            }
            final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g);
            g.setColor(this.gripColor);
            this.paintGrip(g, 0, 0, this.getWidth(), this.getHeight());
            FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
        }
        
        protected void paintGrip(final Graphics g, final int x, final int y, final int width, final int height) {
            FlatUIUtils.paintGrip(g, x, y, width, height, this.splitPane.getOrientation() == 0, this.gripDotCount, this.gripDotSize, this.gripGap, true);
        }
        
        protected boolean isLeftCollapsed() {
            final int location = this.splitPane.getDividerLocation();
            final Insets insets = this.splitPane.getInsets();
            return (this.orientation == 0) ? (location == insets.top) : (location == insets.left);
        }
        
        protected boolean isRightCollapsed() {
            final int location = this.splitPane.getDividerLocation();
            final Insets insets = this.splitPane.getInsets();
            return (this.orientation == 0) ? (location == this.splitPane.getHeight() - this.getHeight() - insets.bottom) : (location == this.splitPane.getWidth() - this.getWidth() - insets.right);
        }
        
        protected class FlatOneTouchButton extends FlatArrowButton
        {
            protected final boolean left;
            
            protected FlatOneTouchButton(final boolean left) {
                super(1, FlatSplitPaneDivider.this.this$0.arrowType, FlatSplitPaneDivider.this.this$0.oneTouchArrowColor, null, FlatSplitPaneDivider.this.this$0.oneTouchHoverArrowColor, null, FlatSplitPaneDivider.this.this$0.oneTouchPressedArrowColor, null);
                this.setCursor(Cursor.getPredefinedCursor(0));
                ToolTipManager.sharedInstance().registerComponent(this);
                this.left = left;
            }
            
            @Override
            public int getDirection() {
                return (FlatSplitPaneDivider.this.orientation == 0) ? (this.left ? 1 : 5) : (this.left ? 7 : 3);
            }
            
            @Override
            public String getToolTipText(final MouseEvent e) {
                final String key = (FlatSplitPaneDivider.this.orientation == 0) ? (this.left ? (FlatSplitPaneDivider.this.isRightCollapsed() ? "SplitPaneDivider.expandBottomToolTipText" : "SplitPaneDivider.collapseTopToolTipText") : (FlatSplitPaneDivider.this.isLeftCollapsed() ? "SplitPaneDivider.expandTopToolTipText" : "SplitPaneDivider.collapseBottomToolTipText")) : (this.left ? (FlatSplitPaneDivider.this.isRightCollapsed() ? "SplitPaneDivider.expandRightToolTipText" : "SplitPaneDivider.collapseLeftToolTipText") : (FlatSplitPaneDivider.this.isLeftCollapsed() ? "SplitPaneDivider.expandLeftToolTipText" : "SplitPaneDivider.collapseRightToolTipText"));
                final Object value = FlatSplitPaneDivider.this.splitPane.getClientProperty(key);
                if (value instanceof String) {
                    return (String)value;
                }
                return UIManager.getString(key, this.getLocale());
            }
        }
        
        protected class FlatDividerLayout extends DividerLayout
        {
            @Override
            public void layoutContainer(final Container c) {
                super.layoutContainer(c);
                if (FlatSplitPaneDivider.this.leftButton == null || FlatSplitPaneDivider.this.rightButton == null || !FlatSplitPaneDivider.this.splitPane.isOneTouchExpandable()) {
                    return;
                }
                final int extraSize = UIScale.scale(4);
                if (FlatSplitPaneDivider.this.orientation == 0) {
                    FlatSplitPaneDivider.this.leftButton.setSize(FlatSplitPaneDivider.this.leftButton.getWidth() + extraSize, FlatSplitPaneDivider.this.leftButton.getHeight());
                    FlatSplitPaneDivider.this.rightButton.setBounds(FlatSplitPaneDivider.this.leftButton.getX() + FlatSplitPaneDivider.this.leftButton.getWidth(), FlatSplitPaneDivider.this.rightButton.getY(), FlatSplitPaneDivider.this.rightButton.getWidth() + extraSize, FlatSplitPaneDivider.this.rightButton.getHeight());
                }
                else {
                    FlatSplitPaneDivider.this.leftButton.setSize(FlatSplitPaneDivider.this.leftButton.getWidth(), FlatSplitPaneDivider.this.leftButton.getHeight() + extraSize);
                    FlatSplitPaneDivider.this.rightButton.setBounds(FlatSplitPaneDivider.this.rightButton.getX(), FlatSplitPaneDivider.this.leftButton.getY() + FlatSplitPaneDivider.this.leftButton.getHeight(), FlatSplitPaneDivider.this.rightButton.getWidth(), FlatSplitPaneDivider.this.rightButton.getHeight() + extraSize);
                }
                final boolean leftCollapsed = FlatSplitPaneDivider.this.isLeftCollapsed();
                if (leftCollapsed) {
                    FlatSplitPaneDivider.this.rightButton.setLocation(FlatSplitPaneDivider.this.leftButton.getLocation());
                }
                FlatSplitPaneDivider.this.leftButton.setVisible(!leftCollapsed);
                FlatSplitPaneDivider.this.rightButton.setVisible(!FlatSplitPaneDivider.this.isRightCollapsed());
            }
        }
    }
}
