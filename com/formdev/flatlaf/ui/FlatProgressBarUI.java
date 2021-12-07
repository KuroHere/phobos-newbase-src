// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import java.awt.geom.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;

public class FlatProgressBarUI extends BasicProgressBarUI
{
    protected int arc;
    protected Dimension horizontalSize;
    protected Dimension verticalSize;
    private PropertyChangeListener propertyChangeListener;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatProgressBarUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.progressBar, "opaque", false);
        this.arc = UIManager.getInt("ProgressBar.arc");
        this.horizontalSize = UIManager.getDimension("ProgressBar.horizontalSize");
        this.verticalSize = UIManager.getDimension("ProgressBar.verticalSize");
    }
    
    @Override
    protected void installListeners() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   javax/swing/plaf/basic/BasicProgressBarUI.installListeners:()V
        //     4: aload_0         /* this */
        //     5: aload_0         /* this */
        //     6: invokedynamic   BootstrapMethod #0, propertyChange:(Lcom/formdev/flatlaf/ui/FlatProgressBarUI;)Ljava/beans/PropertyChangeListener;
        //    11: putfield        com/formdev/flatlaf/ui/FlatProgressBarUI.propertyChangeListener:Ljava/beans/PropertyChangeListener;
        //    14: aload_0         /* this */
        //    15: getfield        com/formdev/flatlaf/ui/FlatProgressBarUI.progressBar:Ljavax/swing/JProgressBar;
        //    18: aload_0         /* this */
        //    19: getfield        com/formdev/flatlaf/ui/FlatProgressBarUI.propertyChangeListener:Ljava/beans/PropertyChangeListener;
        //    22: invokevirtual   javax/swing/JProgressBar.addPropertyChangeListener:(Ljava/beans/PropertyChangeListener;)V
        //    25: return         
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
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.progressBar.removePropertyChangeListener(this.propertyChangeListener);
        this.propertyChangeListener = null;
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final Dimension size = super.getPreferredSize(c);
        if (this.progressBar.isStringPainted() || FlatClientProperties.clientPropertyBoolean(c, "JProgressBar.largeHeight", false)) {
            final Insets insets = this.progressBar.getInsets();
            final FontMetrics fm = this.progressBar.getFontMetrics(this.progressBar.getFont());
            if (this.progressBar.getOrientation() == 0) {
                size.height = Math.max(fm.getHeight() + insets.top + insets.bottom, this.getPreferredInnerHorizontal().height);
            }
            else {
                size.width = Math.max(fm.getHeight() + insets.left + insets.right, this.getPreferredInnerVertical().width);
            }
        }
        return size;
    }
    
    @Override
    protected Dimension getPreferredInnerHorizontal() {
        return UIScale.scale(this.horizontalSize);
    }
    
    @Override
    protected Dimension getPreferredInnerVertical() {
        return UIScale.scale(this.verticalSize);
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        if (c.isOpaque()) {
            FlatUIUtils.paintParentBackground(g, c);
        }
        this.paint(g, c);
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final Insets insets = this.progressBar.getInsets();
        final int x = insets.left;
        final int y = insets.top;
        final int width = this.progressBar.getWidth() - (insets.right + insets.left);
        final int height = this.progressBar.getHeight() - (insets.top + insets.bottom);
        if (width <= 0 || height <= 0) {
            return;
        }
        final boolean horizontal = this.progressBar.getOrientation() == 0;
        final int arc = FlatClientProperties.clientPropertyBoolean(c, "JProgressBar.square", false) ? 0 : Math.min(UIScale.scale(this.arc), horizontal ? height : width);
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float((float)x, (float)y, (float)width, (float)height, (float)arc, (float)arc);
        g.setColor(this.progressBar.getBackground());
        ((Graphics2D)g).fill(trackShape);
        int amountFull = 0;
        if (this.progressBar.isIndeterminate()) {
            this.boxRect = this.getBox(this.boxRect);
            if (this.boxRect != null) {
                g.setColor(this.progressBar.getForeground());
                ((Graphics2D)g).fill(new RoundRectangle2D.Float((float)this.boxRect.x, (float)this.boxRect.y, (float)this.boxRect.width, (float)this.boxRect.height, (float)arc, (float)arc));
            }
        }
        else {
            amountFull = this.getAmountFull(insets, width, height);
            final RoundRectangle2D.Float progressShape = horizontal ? new RoundRectangle2D.Float(c.getComponentOrientation().isLeftToRight() ? ((float)x) : ((float)(x + (width - amountFull))), (float)y, (float)amountFull, (float)height, (float)arc, (float)arc) : new RoundRectangle2D.Float((float)x, (float)(y + (height - amountFull)), (float)width, (float)amountFull, (float)arc, (float)arc);
            g.setColor(this.progressBar.getForeground());
            if (amountFull < (horizontal ? height : width)) {
                final Area area = new Area(trackShape);
                area.intersect(new Area(progressShape));
                ((Graphics2D)g).fill(area);
            }
            else {
                ((Graphics2D)g).fill(progressShape);
            }
        }
        FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
        if (this.progressBar.isStringPainted()) {
            this.paintString(g, x, y, width, height, amountFull, insets);
        }
    }
    
    @Override
    protected void paintString(final Graphics g, final int x, final int y, final int width, final int height, final int amountFull, final Insets b) {
        super.paintString(HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g), x, y, width, height, amountFull, b);
    }
    
    @Override
    protected void setAnimationIndex(final int newValue) {
        super.setAnimationIndex(newValue);
        final double systemScaleFactor = UIScale.getSystemScaleFactor(this.progressBar.getGraphicsConfiguration());
        if ((int)systemScaleFactor != systemScaleFactor) {
            this.progressBar.repaint();
        }
    }
}
