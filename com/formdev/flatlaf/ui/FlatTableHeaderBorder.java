// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;

public class FlatTableHeaderBorder extends FlatEmptyBorder
{
    protected Color separatorColor;
    protected Color bottomSeparatorColor;
    
    public FlatTableHeaderBorder() {
        super(UIManager.getInsets("TableHeader.cellMargins"));
        this.separatorColor = UIManager.getColor("TableHeader.separatorColor");
        this.bottomSeparatorColor = UIManager.getColor("TableHeader.bottomSeparatorColor");
    }
    
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final JTableHeader header = (JTableHeader)SwingUtilities.getAncestorOfClass(JTableHeader.class, c);
        final boolean leftToRight = ((header != null) ? header : c).getComponentOrientation().isLeftToRight();
        boolean paintLeft = !leftToRight;
        boolean paintRight = leftToRight;
        if (header != null) {
            final int hx = SwingUtilities.convertPoint(c, x, y, header).x;
            if (this.isDraggedColumn(header, hx)) {
                paintRight = (paintLeft = true);
            }
            else {
                if (hx <= 0 && !leftToRight && this.hideTrailingVerticalLine(header)) {
                    paintLeft = false;
                }
                if (hx + width >= header.getWidth() && leftToRight && this.hideTrailingVerticalLine(header)) {
                    paintRight = false;
                }
            }
        }
        final float lineWidth = UIScale.scale(1.0f);
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            g2.setColor(this.separatorColor);
            if (paintLeft) {
                g2.fill(new Rectangle2D.Float((float)x, (float)y, lineWidth, height - lineWidth));
            }
            if (paintRight) {
                g2.fill(new Rectangle2D.Float(x + width - lineWidth, (float)y, lineWidth, height - lineWidth));
            }
            g2.setColor(this.bottomSeparatorColor);
            g2.fill(new Rectangle2D.Float((float)x, y + height - lineWidth, (float)width, lineWidth));
        }
        finally {
            g2.dispose();
        }
    }
    
    protected boolean isDraggedColumn(final JTableHeader header, final int x) {
        final TableColumn draggedColumn = header.getDraggedColumn();
        if (draggedColumn == null) {
            return false;
        }
        final int draggedDistance = header.getDraggedDistance();
        if (draggedDistance == 0) {
            return false;
        }
        for (int columnCount = header.getColumnModel().getColumnCount(), i = 0; i < columnCount; ++i) {
            if (header.getHeaderRect(i).x + draggedDistance == x) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean hideTrailingVerticalLine(final JTableHeader header) {
        final Container viewport = header.getParent();
        final Container viewportParent = (viewport != null) ? viewport.getParent() : null;
        if (!(viewportParent instanceof JScrollPane)) {
            return true;
        }
        final JScrollBar vsb = ((JScrollPane)viewportParent).getVerticalScrollBar();
        return vsb == null || !vsb.isVisible() || vsb.getY() == viewport.getY();
    }
}
