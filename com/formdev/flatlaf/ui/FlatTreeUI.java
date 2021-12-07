// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.formdev.flatlaf.util.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.beans.*;

public class FlatTreeUI extends BasicTreeUI
{
    protected Color selectionBackground;
    protected Color selectionForeground;
    protected Color selectionInactiveBackground;
    protected Color selectionInactiveForeground;
    protected Color selectionBorderColor;
    protected boolean wideSelection;
    protected boolean showCellFocusIndicator;
    private Color defaultCellNonSelectionBackground;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTreeUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installBorder(this.tree, "Tree.border");
        this.selectionBackground = UIManager.getColor("Tree.selectionBackground");
        this.selectionForeground = UIManager.getColor("Tree.selectionForeground");
        this.selectionInactiveBackground = UIManager.getColor("Tree.selectionInactiveBackground");
        this.selectionInactiveForeground = UIManager.getColor("Tree.selectionInactiveForeground");
        this.selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        this.wideSelection = UIManager.getBoolean("Tree.wideSelection");
        this.showCellFocusIndicator = UIManager.getBoolean("Tree.showCellFocusIndicator");
        this.defaultCellNonSelectionBackground = UIManager.getColor("Tree.textBackground");
        final int rowHeight = FlatUIUtils.getUIInt("Tree.rowHeight", 16);
        if (rowHeight > 0) {
            LookAndFeel.installProperty(this.tree, "rowHeight", UIScale.scale(rowHeight));
        }
        this.setLeftChildIndent(UIScale.scale(this.getLeftChildIndent()));
        this.setRightChildIndent(UIScale.scale(this.getRightChildIndent()));
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        LookAndFeel.uninstallBorder(this.tree);
        this.selectionBackground = null;
        this.selectionForeground = null;
        this.selectionInactiveBackground = null;
        this.selectionInactiveForeground = null;
        this.selectionBorderColor = null;
        this.defaultCellNonSelectionBackground = null;
    }
    
    @Override
    protected MouseListener createMouseListener() {
        return new MouseHandler() {
            @Override
            public void mousePressed(final MouseEvent e) {
                super.mousePressed(this.handleWideMouseEvent(e));
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
                super.mouseReleased(this.handleWideMouseEvent(e));
            }
            
            @Override
            public void mouseDragged(final MouseEvent e) {
                super.mouseDragged(this.handleWideMouseEvent(e));
            }
            
            private MouseEvent handleWideMouseEvent(final MouseEvent e) {
                if (!FlatTreeUI.this.isWideSelection() || !FlatTreeUI.this.tree.isEnabled() || !SwingUtilities.isLeftMouseButton(e) || e.isConsumed()) {
                    return e;
                }
                final int x = e.getX();
                final int y = e.getY();
                final TreePath path = FlatTreeUI.this.getClosestPathForLocation(FlatTreeUI.this.tree, x, y);
                if (path == null || BasicTreeUI.this.isLocationInExpandControl(path, x, y)) {
                    return e;
                }
                final Rectangle bounds = FlatTreeUI.this.getPathBounds(FlatTreeUI.this.tree, path);
                if (bounds == null || y < bounds.y || y >= bounds.y + bounds.height) {
                    return e;
                }
                final int newX = Math.max(bounds.x, Math.min(x, bounds.x + bounds.width - 1));
                if (newX == x) {
                    return e;
                }
                return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers() | e.getModifiersEx(), newX, e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
            }
        };
    }
    
    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   javax/swing/plaf/basic/BasicTreeUI.createPropertyChangeListener:()Ljava/beans/PropertyChangeListener;
        //     4: astore_1        /* superListener */
        //     5: aload_0         /* this */
        //     6: aload_1         /* superListener */
        //     7: invokedynamic   BootstrapMethod #0, propertyChange:(Lcom/formdev/flatlaf/ui/FlatTreeUI;Ljava/beans/PropertyChangeListener;)Ljava/beans/PropertyChangeListener;
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
    
    private void repaintWideDropLocation(final JTree.DropLocation loc) {
        if (loc == null || this.isDropLine(loc)) {
            return;
        }
        final Rectangle r = this.tree.getPathBounds(loc.getPath());
        if (r != null) {
            this.tree.repaint(0, r.y, this.tree.getWidth(), r.height);
        }
    }
    
    @Override
    protected void paintRow(final Graphics g, final Rectangle clipBounds, final Insets insets, final Rectangle bounds, final TreePath path, final int row, final boolean isExpanded, final boolean hasBeenExpanded, final boolean isLeaf) {
        final boolean isEditing = this.editingComponent != null && this.editingRow == row;
        final boolean isSelected = this.tree.isRowSelected(row);
        final boolean isDropRow = this.isDropRow(row);
        final boolean needsSelectionPainting = (isSelected || isDropRow) && this.isPaintSelection();
        if (isEditing && !needsSelectionPainting) {
            return;
        }
        boolean hasFocus = FlatUIUtils.isPermanentFocusOwner(this.tree);
        final boolean cellHasFocus = hasFocus && row == this.getLeadSelectionRow();
        if (!hasFocus && isSelected && this.tree.getParent() instanceof CellRendererPane) {
            hasFocus = FlatUIUtils.isPermanentFocusOwner(this.tree.getParent().getParent());
        }
        final Component rendererComponent = this.currentCellRenderer.getTreeCellRendererComponent(this.tree, path.getLastPathComponent(), isSelected, isExpanded, isLeaf, row, cellHasFocus);
        Color oldBackgroundSelectionColor = null;
        if (isSelected && !hasFocus && !isDropRow) {
            if (rendererComponent instanceof DefaultTreeCellRenderer) {
                final DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)rendererComponent;
                if (renderer.getBackgroundSelectionColor() == this.selectionBackground) {
                    oldBackgroundSelectionColor = renderer.getBackgroundSelectionColor();
                    renderer.setBackgroundSelectionColor(this.selectionInactiveBackground);
                }
            }
            else if (rendererComponent.getBackground() == this.selectionBackground) {
                rendererComponent.setBackground(this.selectionInactiveBackground);
            }
            if (rendererComponent.getForeground() == this.selectionForeground) {
                rendererComponent.setForeground(this.selectionInactiveForeground);
            }
        }
        Color oldBorderSelectionColor = null;
        if (isSelected && hasFocus && (!this.showCellFocusIndicator || this.tree.getMinSelectionRow() == this.tree.getMaxSelectionRow()) && rendererComponent instanceof DefaultTreeCellRenderer) {
            final DefaultTreeCellRenderer renderer2 = (DefaultTreeCellRenderer)rendererComponent;
            if (renderer2.getBorderSelectionColor() == this.selectionBorderColor) {
                oldBorderSelectionColor = renderer2.getBorderSelectionColor();
                renderer2.setBorderSelectionColor(null);
            }
        }
        if (needsSelectionPainting) {
            final Color oldColor = g.getColor();
            g.setColor(isDropRow ? UIManager.getColor("Tree.dropCellBackground") : ((rendererComponent instanceof DefaultTreeCellRenderer) ? ((DefaultTreeCellRenderer)rendererComponent).getBackgroundSelectionColor() : (hasFocus ? this.selectionBackground : this.selectionInactiveBackground)));
            if (this.isWideSelection()) {
                g.fillRect(0, bounds.y, this.tree.getWidth(), bounds.height);
                if (this.shouldPaintExpandControl(path, row, isExpanded, hasBeenExpanded, isLeaf)) {
                    this.paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
                }
            }
            else {
                this.paintCellBackground(g, rendererComponent, bounds);
            }
            g.setColor(oldColor);
        }
        else if (rendererComponent instanceof DefaultTreeCellRenderer) {
            final DefaultTreeCellRenderer renderer2 = (DefaultTreeCellRenderer)rendererComponent;
            final Color bg = renderer2.getBackgroundNonSelectionColor();
            if (bg != null && !bg.equals(this.defaultCellNonSelectionBackground)) {
                final Color oldColor2 = g.getColor();
                g.setColor(bg);
                this.paintCellBackground(g, rendererComponent, bounds);
                g.setColor(oldColor2);
            }
        }
        if (!isEditing) {
            this.rendererPane.paintComponent(g, rendererComponent, this.tree, bounds.x, bounds.y, bounds.width, bounds.height, true);
        }
        if (oldBackgroundSelectionColor != null) {
            ((DefaultTreeCellRenderer)rendererComponent).setBackgroundSelectionColor(oldBackgroundSelectionColor);
        }
        if (oldBorderSelectionColor != null) {
            ((DefaultTreeCellRenderer)rendererComponent).setBorderSelectionColor(oldBorderSelectionColor);
        }
    }
    
    private void paintCellBackground(final Graphics g, final Component rendererComponent, final Rectangle bounds) {
        int xOffset = 0;
        int imageOffset = 0;
        if (rendererComponent instanceof JLabel) {
            final JLabel label = (JLabel)rendererComponent;
            final Icon icon = label.getIcon();
            imageOffset = ((icon != null && label.getText() != null) ? (icon.getIconWidth() + Math.max(label.getIconTextGap() - 1, 0)) : 0);
            xOffset = (label.getComponentOrientation().isLeftToRight() ? imageOffset : 0);
        }
        g.fillRect(bounds.x + xOffset, bounds.y, bounds.width - imageOffset, bounds.height);
    }
    
    private boolean isDropRow(final int row) {
        final JTree.DropLocation dropLocation = this.tree.getDropLocation();
        return dropLocation != null && dropLocation.getChildIndex() == -1 && this.tree.getRowForPath(dropLocation.getPath()) == row;
    }
    
    @Override
    protected Rectangle getDropLineRect(final JTree.DropLocation loc) {
        final Rectangle r = super.getDropLineRect(loc);
        return this.isWideSelection() ? new Rectangle(0, r.y, this.tree.getWidth(), r.height) : r;
    }
    
    protected boolean isWideSelection() {
        return FlatClientProperties.clientPropertyBoolean(this.tree, "JTree.wideSelection", this.wideSelection);
    }
    
    protected boolean isPaintSelection() {
        return FlatClientProperties.clientPropertyBoolean(this.tree, "JTree.paintSelection", true);
    }
}
