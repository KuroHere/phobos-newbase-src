// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import java.awt.event.*;
import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;

public class FlatTableUI extends BasicTableUI
{
    protected boolean showHorizontalLines;
    protected boolean showVerticalLines;
    protected Dimension intercellSpacing;
    protected Color selectionBackground;
    protected Color selectionForeground;
    protected Color selectionInactiveBackground;
    protected Color selectionInactiveForeground;
    private boolean oldShowHorizontalLines;
    private boolean oldShowVerticalLines;
    private Dimension oldIntercellSpacing;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTableUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.showHorizontalLines = UIManager.getBoolean("Table.showHorizontalLines");
        this.showVerticalLines = UIManager.getBoolean("Table.showVerticalLines");
        this.intercellSpacing = UIManager.getDimension("Table.intercellSpacing");
        this.selectionBackground = UIManager.getColor("Table.selectionBackground");
        this.selectionForeground = UIManager.getColor("Table.selectionForeground");
        this.selectionInactiveBackground = UIManager.getColor("Table.selectionInactiveBackground");
        this.selectionInactiveForeground = UIManager.getColor("Table.selectionInactiveForeground");
        this.toggleSelectionColors();
        final int rowHeight = FlatUIUtils.getUIInt("Table.rowHeight", 16);
        if (rowHeight > 0) {
            LookAndFeel.installProperty(this.table, "rowHeight", UIScale.scale(rowHeight));
        }
        if (!this.showHorizontalLines) {
            this.oldShowHorizontalLines = this.table.getShowHorizontalLines();
            this.table.setShowHorizontalLines(false);
        }
        if (!this.showVerticalLines) {
            this.oldShowVerticalLines = this.table.getShowVerticalLines();
            this.table.setShowVerticalLines(false);
        }
        if (this.intercellSpacing != null) {
            this.oldIntercellSpacing = this.table.getIntercellSpacing();
            this.table.setIntercellSpacing(this.intercellSpacing);
        }
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.selectionBackground = null;
        this.selectionForeground = null;
        this.selectionInactiveBackground = null;
        this.selectionInactiveForeground = null;
        if (!this.showHorizontalLines && this.oldShowHorizontalLines && !this.table.getShowHorizontalLines()) {
            this.table.setShowHorizontalLines(true);
        }
        if (!this.showVerticalLines && this.oldShowVerticalLines && !this.table.getShowVerticalLines()) {
            this.table.setShowVerticalLines(true);
        }
        if (this.intercellSpacing != null && this.table.getIntercellSpacing().equals(this.intercellSpacing)) {
            this.table.setIntercellSpacing(this.oldIntercellSpacing);
        }
    }
    
    @Override
    protected FocusListener createFocusListener() {
        return new FocusHandler() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                FlatTableUI.this.toggleSelectionColors();
            }
            
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                EventQueue.invokeLater(() -> FlatTableUI.this.toggleSelectionColors());
            }
        };
    }
    
    private void toggleSelectionColors() {
        if (this.table == null) {
            return;
        }
        if (FlatUIUtils.isPermanentFocusOwner(this.table)) {
            if (this.table.getSelectionBackground() == this.selectionInactiveBackground) {
                this.table.setSelectionBackground(this.selectionBackground);
            }
            if (this.table.getSelectionForeground() == this.selectionInactiveForeground) {
                this.table.setSelectionForeground(this.selectionForeground);
            }
        }
        else {
            if (this.table.getSelectionBackground() == this.selectionBackground) {
                this.table.setSelectionBackground(this.selectionInactiveBackground);
            }
            if (this.table.getSelectionForeground() == this.selectionForeground) {
                this.table.setSelectionForeground(this.selectionInactiveForeground);
            }
        }
    }
    
    @Override
    public void paint(Graphics g, final JComponent c) {
        final boolean horizontalLines = this.table.getShowHorizontalLines();
        final boolean verticalLines = this.table.getShowVerticalLines();
        if (horizontalLines || verticalLines) {
            final boolean hideLastVerticalLine = this.hideLastVerticalLine();
            final int tableWidth = this.table.getWidth();
            final JTableHeader header = this.table.getTableHeader();
            final boolean isDragging = header != null && header.getDraggedColumn() != null;
            final double systemScaleFactor = UIScale.getSystemScaleFactor((Graphics2D)g);
            final double lineThickness = 1.0 / systemScaleFactor * (int)systemScaleFactor;
            g = new Graphics2DProxy((Graphics2D)g) {
                @Override
                public void drawLine(final int x1, final int y1, final int x2, final int y2) {
                    if (hideLastVerticalLine && verticalLines && x1 == x2 && y1 == 0 && x1 == tableWidth - 1 && this.wasInvokedFromPaintGrid()) {
                        return;
                    }
                    if (isDragging && SystemInfo.isJava_9_orLater && ((horizontalLines && y1 == y2) || (verticalLines && x1 == x2)) && this.wasInvokedFromMethod("paintDraggedArea")) {
                        if (y1 == y2) {
                            super.fill(new Rectangle2D.Double(x1, y1, x2 - x1 + 1, lineThickness));
                        }
                        else if (x1 == x2) {
                            super.fill(new Rectangle2D.Double(x1, y1, lineThickness, y2 - y1 + 1));
                        }
                        return;
                    }
                    super.drawLine(x1, y1, x2, y2);
                }
                
                @Override
                public void fillRect(final int x, final int y, final int width, final int height) {
                    if (hideLastVerticalLine && verticalLines && width == 1 && y == 0 && x == tableWidth - 1 && this.wasInvokedFromPaintGrid()) {
                        return;
                    }
                    if (lineThickness != 1.0) {
                        if (horizontalLines && height == 1 && this.wasInvokedFromPaintGrid()) {
                            super.fill(new Rectangle2D.Double(x, y, width, lineThickness));
                            return;
                        }
                        if (verticalLines && width == 1 && y == 0 && this.wasInvokedFromPaintGrid()) {
                            super.fill(new Rectangle2D.Double(x, y, lineThickness, height));
                            return;
                        }
                    }
                    super.fillRect(x, y, width, height);
                }
                
                private boolean wasInvokedFromPaintGrid() {
                    return this.wasInvokedFromMethod("paintGrid");
                }
                
                private boolean wasInvokedFromMethod(final String methodName) {
                    return StackUtils.wasInvokedFrom(BasicTableUI.class.getName(), methodName, 8);
                }
            };
        }
        super.paint(g, c);
    }
    
    protected boolean hideLastVerticalLine() {
        final Container viewport = SwingUtilities.getUnwrappedParent(this.table);
        final Container viewportParent = (viewport != null) ? viewport.getParent() : null;
        if (!(viewportParent instanceof JScrollPane)) {
            return false;
        }
        if (this.table.getX() + this.table.getWidth() < viewport.getWidth()) {
            return false;
        }
        final JScrollPane scrollPane = (JScrollPane)viewportParent;
        final JViewport rowHeader = scrollPane.getRowHeader();
        return scrollPane.getComponentOrientation().isLeftToRight() ? (viewport != rowHeader) : (viewport == rowHeader || rowHeader == null);
    }
}
