// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.util.*;
import javax.swing.table.*;
import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;

public class FlatTableHeaderUI extends BasicTableHeaderUI
{
    protected Color bottomSeparatorColor;
    protected int height;
    protected int sortIconPosition;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTableHeaderUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.bottomSeparatorColor = UIManager.getColor("TableHeader.bottomSeparatorColor");
        this.height = UIManager.getInt("TableHeader.height");
        final String string = Objects.toString(UIManager.getString("TableHeader.sortIconPosition"), "right");
        switch (string) {
            default: {
                this.sortIconPosition = 4;
                break;
            }
            case "left": {
                this.sortIconPosition = 2;
                break;
            }
            case "top": {
                this.sortIconPosition = 1;
                break;
            }
            case "bottom": {
                this.sortIconPosition = 3;
                break;
            }
        }
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.bottomSeparatorColor = null;
    }
    
    public int getRolloverColumn() {
        return super.getRolloverColumn();
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final TableColumnModel columnModel = this.header.getColumnModel();
        if (columnModel.getColumnCount() <= 0) {
            return;
        }
        final int columnCount = columnModel.getColumnCount();
        int totalWidth = 0;
        for (int i = 0; i < columnCount; ++i) {
            totalWidth += columnModel.getColumn(i).getWidth();
        }
        if (totalWidth < this.header.getWidth()) {
            final TableCellRenderer defaultRenderer = this.header.getDefaultRenderer();
            boolean paintBottomSeparator = this.isSystemDefaultRenderer(defaultRenderer);
            if (!paintBottomSeparator && this.header.getTable() != null) {
                final Component rendererComponent = defaultRenderer.getTableCellRendererComponent(this.header.getTable(), "", false, false, -1, 0);
                paintBottomSeparator = this.isSystemDefaultRenderer(rendererComponent);
            }
            if (paintBottomSeparator) {
                final int w = c.getWidth() - totalWidth;
                final int x = this.header.getComponentOrientation().isLeftToRight() ? (c.getWidth() - w) : 0;
                this.paintBottomSeparator(g, c, x, w);
            }
        }
        FlatTableCellHeaderRenderer sortIconRenderer = null;
        if (this.sortIconPosition != 4) {
            sortIconRenderer = new FlatTableCellHeaderRenderer(this.header.getDefaultRenderer());
            this.header.setDefaultRenderer(sortIconRenderer);
        }
        super.paint(g, c);
        if (sortIconRenderer != null) {
            sortIconRenderer.reset();
            this.header.setDefaultRenderer(sortIconRenderer.delegate);
        }
    }
    
    private boolean isSystemDefaultRenderer(final Object headerRenderer) {
        final String rendererClassName = headerRenderer.getClass().getName();
        return rendererClassName.equals("sun.swing.table.DefaultTableCellHeaderRenderer") || rendererClassName.equals("sun.swing.FilePane$AlignableTableHeaderRenderer");
    }
    
    protected void paintBottomSeparator(final Graphics g, final JComponent c, final int x, final int w) {
        final float lineWidth = UIScale.scale(1.0f);
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            g2.setColor(this.bottomSeparatorColor);
            g2.fill(new Rectangle2D.Float((float)x, c.getHeight() - lineWidth, (float)w, lineWidth));
        }
        finally {
            g2.dispose();
        }
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final Dimension size = super.getPreferredSize(c);
        if (size.height > 0) {
            size.height = Math.max(size.height, UIScale.scale(this.height));
        }
        return size;
    }
    
    private class FlatTableCellHeaderRenderer implements TableCellRenderer, Border, UIResource
    {
        private final TableCellRenderer delegate;
        private JLabel l;
        private int oldHorizontalTextPosition;
        private Border origBorder;
        private Icon sortIcon;
        
        FlatTableCellHeaderRenderer(final TableCellRenderer delegate) {
            this.oldHorizontalTextPosition = -1;
            this.delegate = delegate;
        }
        
        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            final Component c = this.delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!(c instanceof JLabel)) {
                return c;
            }
            this.l = (JLabel)c;
            if (FlatTableHeaderUI.this.sortIconPosition == 2) {
                if (this.oldHorizontalTextPosition < 0) {
                    this.oldHorizontalTextPosition = this.l.getHorizontalTextPosition();
                }
                this.l.setHorizontalTextPosition(4);
            }
            else {
                this.sortIcon = this.l.getIcon();
                this.origBorder = this.l.getBorder();
                this.l.setIcon(null);
                this.l.setBorder(this);
            }
            return this.l;
        }
        
        void reset() {
            if (this.l != null && FlatTableHeaderUI.this.sortIconPosition == 2 && this.oldHorizontalTextPosition >= 0) {
                this.l.setHorizontalTextPosition(this.oldHorizontalTextPosition);
            }
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            if (this.origBorder != null) {
                this.origBorder.paintBorder(c, g, x, y, width, height);
            }
            if (this.sortIcon != null) {
                final int xi = x + (width - this.sortIcon.getIconWidth()) / 2;
                final int yi = (FlatTableHeaderUI.this.sortIconPosition == 1) ? (y + UIScale.scale(1)) : (y + height - this.sortIcon.getIconHeight() - 1 - (int)(1.0f * UIScale.getUserScaleFactor()));
                this.sortIcon.paintIcon(c, g, xi, yi);
            }
        }
        
        @Override
        public Insets getBorderInsets(final Component c) {
            return (this.origBorder != null) ? this.origBorder.getBorderInsets(c) : new Insets(0, 0, 0, 0);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return this.origBorder != null && this.origBorder.isBorderOpaque();
        }
    }
}
