// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.formdev.flatlaf.util.*;
import java.util.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;

public class FlatScrollBarUI extends BasicScrollBarUI
{
    protected Insets trackInsets;
    protected Insets thumbInsets;
    protected int trackArc;
    protected int thumbArc;
    protected Color hoverTrackColor;
    protected Color hoverThumbColor;
    protected boolean hoverThumbWithTrack;
    protected Color pressedTrackColor;
    protected Color pressedThumbColor;
    protected boolean pressedThumbWithTrack;
    protected boolean showButtons;
    protected String arrowType;
    protected Color buttonArrowColor;
    protected Color buttonDisabledArrowColor;
    protected Color hoverButtonBackground;
    protected Color pressedButtonBackground;
    private MouseAdapter hoverListener;
    protected boolean hoverTrack;
    protected boolean hoverThumb;
    private static boolean isPressed;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatScrollBarUI();
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.hoverListener = new ScrollBarHoverListener();
        this.scrollbar.addMouseListener(this.hoverListener);
        this.scrollbar.addMouseMotionListener(this.hoverListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.scrollbar.removeMouseListener(this.hoverListener);
        this.scrollbar.removeMouseMotionListener(this.hoverListener);
        this.hoverListener = null;
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        this.trackInsets = UIManager.getInsets("ScrollBar.trackInsets");
        this.thumbInsets = UIManager.getInsets("ScrollBar.thumbInsets");
        this.trackArc = UIManager.getInt("ScrollBar.trackArc");
        this.thumbArc = UIManager.getInt("ScrollBar.thumbArc");
        this.hoverTrackColor = UIManager.getColor("ScrollBar.hoverTrackColor");
        this.hoverThumbColor = UIManager.getColor("ScrollBar.hoverThumbColor");
        this.hoverThumbWithTrack = UIManager.getBoolean("ScrollBar.hoverThumbWithTrack");
        this.pressedTrackColor = UIManager.getColor("ScrollBar.pressedTrackColor");
        this.pressedThumbColor = UIManager.getColor("ScrollBar.pressedThumbColor");
        this.pressedThumbWithTrack = UIManager.getBoolean("ScrollBar.pressedThumbWithTrack");
        this.showButtons = UIManager.getBoolean("ScrollBar.showButtons");
        this.arrowType = UIManager.getString("Component.arrowType");
        this.buttonArrowColor = UIManager.getColor("ScrollBar.buttonArrowColor");
        this.buttonDisabledArrowColor = UIManager.getColor("ScrollBar.buttonDisabledArrowColor");
        this.hoverButtonBackground = UIManager.getColor("ScrollBar.hoverButtonBackground");
        this.pressedButtonBackground = UIManager.getColor("ScrollBar.pressedButtonBackground");
        if (this.trackInsets == null) {
            this.trackInsets = new Insets(0, 0, 0, 0);
        }
        if (this.thumbInsets == null) {
            this.thumbInsets = new Insets(0, 0, 0, 0);
        }
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.trackInsets = null;
        this.thumbInsets = null;
        this.hoverTrackColor = null;
        this.hoverThumbColor = null;
        this.pressedTrackColor = null;
        this.pressedThumbColor = null;
        this.buttonArrowColor = null;
        this.buttonDisabledArrowColor = null;
        this.hoverButtonBackground = null;
        this.pressedButtonBackground = null;
    }
    
    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   javax/swing/plaf/basic/BasicScrollBarUI.createPropertyChangeListener:()Ljava/beans/PropertyChangeListener;
        //     4: astore_1        /* superListener */
        //     5: aload_0         /* this */
        //     6: aload_1         /* superListener */
        //     7: invokedynamic   BootstrapMethod #0, propertyChange:(Lcom/formdev/flatlaf/ui/FlatScrollBarUI;Ljava/beans/PropertyChangeListener;)Ljava/beans/PropertyChangeListener;
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
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return UIScale.scale(super.getPreferredSize(c));
    }
    
    @Override
    protected JButton createDecreaseButton(final int orientation) {
        return new FlatScrollBarButton(orientation);
    }
    
    @Override
    protected JButton createIncreaseButton(final int orientation) {
        return new FlatScrollBarButton(orientation);
    }
    
    protected boolean isShowButtons() {
        Object showButtons = this.scrollbar.getClientProperty("JScrollBar.showButtons");
        if (showButtons == null && this.scrollbar.getParent() instanceof JScrollPane) {
            showButtons = ((JScrollPane)this.scrollbar.getParent()).getClientProperty("JScrollBar.showButtons");
        }
        return (showButtons != null) ? Objects.equals(showButtons, true) : this.showButtons;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        super.paint(g, c);
        FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
    }
    
    @Override
    protected void paintTrack(final Graphics g, final JComponent c, final Rectangle trackBounds) {
        g.setColor(this.getTrackColor(c, this.hoverTrack, FlatScrollBarUI.isPressed && this.hoverTrack && !this.hoverThumb));
        this.paintTrackOrThumb(g, c, trackBounds, this.trackInsets, this.trackArc);
    }
    
    @Override
    protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !this.scrollbar.isEnabled()) {
            return;
        }
        g.setColor(this.getThumbColor(c, this.hoverThumb || (this.hoverThumbWithTrack && this.hoverTrack), FlatScrollBarUI.isPressed && (this.hoverThumb || (this.pressedThumbWithTrack && this.hoverTrack))));
        this.paintTrackOrThumb(g, c, thumbBounds, this.thumbInsets, this.thumbArc);
    }
    
    protected void paintTrackOrThumb(final Graphics g, final JComponent c, Rectangle bounds, Insets insets, int arc) {
        if (this.scrollbar.getOrientation() == 0) {
            insets = new Insets(insets.right, insets.top, insets.left, insets.bottom);
        }
        bounds = FlatUIUtils.subtractInsets(bounds, UIScale.scale(insets));
        if (arc <= 0) {
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        else {
            arc = Math.min(UIScale.scale(arc), Math.min(bounds.width, bounds.height));
            g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arc, arc);
        }
    }
    
    @Override
    protected void paintDecreaseHighlight(final Graphics g) {
    }
    
    @Override
    protected void paintIncreaseHighlight(final Graphics g) {
    }
    
    protected Color getTrackColor(final JComponent c, final boolean hover, final boolean pressed) {
        final Color trackColor = FlatUIUtils.deriveColor(this.trackColor, c.getBackground());
        return (pressed && this.pressedTrackColor != null) ? FlatUIUtils.deriveColor(this.pressedTrackColor, trackColor) : ((hover && this.hoverTrackColor != null) ? FlatUIUtils.deriveColor(this.hoverTrackColor, trackColor) : trackColor);
    }
    
    protected Color getThumbColor(final JComponent c, final boolean hover, final boolean pressed) {
        final Color trackColor = FlatUIUtils.deriveColor(this.trackColor, c.getBackground());
        final Color thumbColor = FlatUIUtils.deriveColor(this.thumbColor, trackColor);
        return (pressed && this.pressedThumbColor != null) ? FlatUIUtils.deriveColor(this.pressedThumbColor, thumbColor) : ((hover && this.hoverThumbColor != null) ? FlatUIUtils.deriveColor(this.hoverThumbColor, thumbColor) : thumbColor);
    }
    
    @Override
    protected Dimension getMinimumThumbSize() {
        return UIScale.scale(FlatUIUtils.addInsets(super.getMinimumThumbSize(), this.thumbInsets));
    }
    
    @Override
    protected Dimension getMaximumThumbSize() {
        return UIScale.scale(FlatUIUtils.addInsets(super.getMaximumThumbSize(), this.thumbInsets));
    }
    
    private class ScrollBarHoverListener extends MouseAdapter
    {
        @Override
        public void mouseExited(final MouseEvent e) {
            if (!FlatScrollBarUI.isPressed) {
                final FlatScrollBarUI this$0 = FlatScrollBarUI.this;
                final FlatScrollBarUI this$2 = FlatScrollBarUI.this;
                final boolean b = false;
                this$2.hoverThumb = b;
                this$0.hoverTrack = b;
                this.repaint();
            }
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
            if (!FlatScrollBarUI.isPressed) {
                this.update(e.getX(), e.getY());
            }
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            FlatScrollBarUI.isPressed = true;
            this.repaint();
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
            FlatScrollBarUI.isPressed = false;
            this.repaint();
            this.update(e.getX(), e.getY());
        }
        
        private void update(final int x, final int y) {
            final boolean inTrack = BasicScrollBarUI.this.getTrackBounds().contains(x, y);
            final boolean inThumb = BasicScrollBarUI.this.getThumbBounds().contains(x, y);
            if (inTrack != FlatScrollBarUI.this.hoverTrack || inThumb != FlatScrollBarUI.this.hoverThumb) {
                FlatScrollBarUI.this.hoverTrack = inTrack;
                FlatScrollBarUI.this.hoverThumb = inThumb;
                this.repaint();
            }
        }
        
        private void repaint() {
            if (FlatScrollBarUI.this.scrollbar.isEnabled()) {
                FlatScrollBarUI.this.scrollbar.repaint();
            }
        }
    }
    
    protected class FlatScrollBarButton extends FlatArrowButton
    {
        protected FlatScrollBarButton(final FlatScrollBarUI this$0, final int direction) {
            this(this$0, direction, this$0.arrowType, this$0.buttonArrowColor, this$0.buttonDisabledArrowColor, null, this$0.hoverButtonBackground, null, this$0.pressedButtonBackground);
        }
        
        protected FlatScrollBarButton(final int direction, final String type, final Color foreground, final Color disabledForeground, final Color hoverForeground, final Color hoverBackground, final Color pressedForeground, final Color pressedBackground) {
            super(direction, type, foreground, disabledForeground, hoverForeground, hoverBackground, pressedForeground, pressedBackground);
            this.setArrowWidth(6);
            this.setFocusable(false);
            this.setRequestFocusEnabled(false);
        }
        
        @Override
        protected Color deriveBackground(final Color background) {
            return FlatUIUtils.deriveColor(background, FlatScrollBarUI.this.scrollbar.getBackground());
        }
        
        @Override
        public Dimension getPreferredSize() {
            if (FlatScrollBarUI.this.isShowButtons()) {
                final int w = UIScale.scale(FlatScrollBarUI.this.scrollBarWidth);
                return new Dimension(w, w);
            }
            return new Dimension();
        }
        
        @Override
        public Dimension getMinimumSize() {
            return FlatScrollBarUI.this.isShowButtons() ? super.getMinimumSize() : new Dimension();
        }
        
        @Override
        public Dimension getMaximumSize() {
            return FlatScrollBarUI.this.isShowButtons() ? super.getMaximumSize() : new Dimension();
        }
    }
}
