// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.util.*;
import javax.swing.text.*;
import java.awt.geom.*;
import javax.swing.plaf.*;
import java.beans.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;

public class FlatComboBoxUI extends BasicComboBoxUI
{
    protected int minimumWidth;
    protected int editorColumns;
    protected String buttonStyle;
    protected String arrowType;
    protected boolean isIntelliJTheme;
    protected Color borderColor;
    protected Color disabledBorderColor;
    protected Color editableBackground;
    protected Color focusedBackground;
    protected Color disabledBackground;
    protected Color disabledForeground;
    protected Color buttonBackground;
    protected Color buttonEditableBackground;
    protected Color buttonFocusedBackground;
    protected Color buttonArrowColor;
    protected Color buttonDisabledArrowColor;
    protected Color buttonHoverArrowColor;
    protected Color buttonPressedArrowColor;
    protected Color popupBackground;
    private MouseListener hoverListener;
    protected boolean hover;
    protected boolean pressed;
    private CellPaddingBorder paddingBorder;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatComboBoxUI();
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.hoverListener = new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                FlatComboBoxUI.this.hover = true;
                this.repaintArrowButton();
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                FlatComboBoxUI.this.hover = false;
                this.repaintArrowButton();
            }
            
            @Override
            public void mousePressed(final MouseEvent e) {
                FlatComboBoxUI.this.pressed = true;
                this.repaintArrowButton();
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
                FlatComboBoxUI.this.pressed = false;
                this.repaintArrowButton();
            }
            
            private void repaintArrowButton() {
                if (FlatComboBoxUI.this.arrowButton != null && !FlatComboBoxUI.this.comboBox.isEditable()) {
                    FlatComboBoxUI.this.arrowButton.repaint();
                }
            }
        };
        this.comboBox.addMouseListener(this.hoverListener);
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.comboBox.removeMouseListener(this.hoverListener);
        this.hoverListener = null;
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.comboBox, "opaque", false);
        this.minimumWidth = UIManager.getInt("ComboBox.minimumWidth");
        this.editorColumns = UIManager.getInt("ComboBox.editorColumns");
        this.buttonStyle = UIManager.getString("ComboBox.buttonStyle");
        this.arrowType = UIManager.getString("Component.arrowType");
        this.isIntelliJTheme = UIManager.getBoolean("Component.isIntelliJTheme");
        this.borderColor = UIManager.getColor("Component.borderColor");
        this.disabledBorderColor = UIManager.getColor("Component.disabledBorderColor");
        this.editableBackground = UIManager.getColor("ComboBox.editableBackground");
        this.focusedBackground = UIManager.getColor("ComboBox.focusedBackground");
        this.disabledBackground = UIManager.getColor("ComboBox.disabledBackground");
        this.disabledForeground = UIManager.getColor("ComboBox.disabledForeground");
        this.buttonBackground = UIManager.getColor("ComboBox.buttonBackground");
        this.buttonFocusedBackground = UIManager.getColor("ComboBox.buttonFocusedBackground");
        this.buttonEditableBackground = UIManager.getColor("ComboBox.buttonEditableBackground");
        this.buttonArrowColor = UIManager.getColor("ComboBox.buttonArrowColor");
        this.buttonDisabledArrowColor = UIManager.getColor("ComboBox.buttonDisabledArrowColor");
        this.buttonHoverArrowColor = UIManager.getColor("ComboBox.buttonHoverArrowColor");
        this.buttonPressedArrowColor = UIManager.getColor("ComboBox.buttonPressedArrowColor");
        this.popupBackground = UIManager.getColor("ComboBox.popupBackground");
        final int maximumRowCount = UIManager.getInt("ComboBox.maximumRowCount");
        if (maximumRowCount > 0 && maximumRowCount != 8 && this.comboBox.getMaximumRowCount() == 8) {
            this.comboBox.setMaximumRowCount(maximumRowCount);
        }
        this.paddingBorder = new CellPaddingBorder(this.padding);
        MigLayoutVisualPadding.install(this.comboBox);
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.borderColor = null;
        this.disabledBorderColor = null;
        this.editableBackground = null;
        this.focusedBackground = null;
        this.disabledBackground = null;
        this.disabledForeground = null;
        this.buttonBackground = null;
        this.buttonEditableBackground = null;
        this.buttonFocusedBackground = null;
        this.buttonArrowColor = null;
        this.buttonDisabledArrowColor = null;
        this.buttonHoverArrowColor = null;
        this.buttonPressedArrowColor = null;
        this.popupBackground = null;
        this.paddingBorder.uninstall();
        MigLayoutVisualPadding.uninstall(this.comboBox);
    }
    
    @Override
    protected LayoutManager createLayoutManager() {
        return new ComboBoxLayoutManager() {
            @Override
            public void layoutContainer(final Container parent) {
                super.layoutContainer(parent);
                if (FlatComboBoxUI.this.arrowButton != null) {
                    final FontMetrics fm = FlatComboBoxUI.this.comboBox.getFontMetrics(FlatComboBoxUI.this.comboBox.getFont());
                    final int maxButtonWidth = fm.getHeight() + UIScale.scale(FlatComboBoxUI.this.padding.top) + UIScale.scale(FlatComboBoxUI.this.padding.bottom);
                    final Insets insets = BasicComboBoxUI.this.getInsets();
                    final int buttonWidth = Math.min(parent.getPreferredSize().height - insets.top - insets.bottom, maxButtonWidth);
                    if (buttonWidth != FlatComboBoxUI.this.arrowButton.getWidth()) {
                        final int xOffset = FlatComboBoxUI.this.comboBox.getComponentOrientation().isLeftToRight() ? (FlatComboBoxUI.this.arrowButton.getWidth() - buttonWidth) : 0;
                        FlatComboBoxUI.this.arrowButton.setBounds(FlatComboBoxUI.this.arrowButton.getX() + xOffset, FlatComboBoxUI.this.arrowButton.getY(), buttonWidth, FlatComboBoxUI.this.arrowButton.getHeight());
                        if (FlatComboBoxUI.this.editor != null) {
                            FlatComboBoxUI.this.editor.setBounds(BasicComboBoxUI.this.rectangleForCurrentValue());
                        }
                    }
                }
            }
        };
    }
    
    @Override
    protected FocusListener createFocusListener() {
        return new FocusHandler() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                if (FlatComboBoxUI.this.comboBox != null && FlatComboBoxUI.this.comboBox.isEditable()) {
                    FlatComboBoxUI.this.comboBox.repaint();
                }
            }
            
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                if (FlatComboBoxUI.this.comboBox != null && FlatComboBoxUI.this.comboBox.isEditable()) {
                    FlatComboBoxUI.this.comboBox.repaint();
                }
            }
        };
    }
    
    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        final PropertyChangeListener superListener = super.createPropertyChangeListener();
        return e -> {
            superListener.propertyChange(e);
            final Object source = e.getSource();
            final String propertyName = e.getPropertyName();
            if (this.editor != null && ((source == this.comboBox && propertyName == "foreground") || (source == this.editor && propertyName == "enabled"))) {
                this.updateEditorColors();
            }
            else if (this.editor != null && source == this.comboBox && propertyName == "componentOrientation") {
                final ComponentOrientation o = (ComponentOrientation)e.getNewValue();
                this.editor.applyComponentOrientation(o);
            }
            else if (this.editor != null && "JTextField.placeholderText".equals(propertyName)) {
                this.editor.repaint();
            }
            else if ("JComponent.roundRect".equals(propertyName)) {
                this.comboBox.repaint();
            }
            else if ("JComponent.minimumWidth".equals(propertyName)) {
                this.comboBox.revalidate();
            }
        };
    }
    
    @Override
    protected ComboPopup createPopup() {
        return new FlatComboPopup(this.comboBox);
    }
    
    @Override
    protected ComboBoxEditor createEditor() {
        final ComboBoxEditor comboBoxEditor = super.createEditor();
        final Component editor = comboBoxEditor.getEditorComponent();
        if (editor instanceof JTextField) {
            final JTextField textField = (JTextField)editor;
            textField.setColumns(this.editorColumns);
            textField.setBorder(BorderFactory.createEmptyBorder());
        }
        return comboBoxEditor;
    }
    
    @Override
    protected void configureEditor() {
        super.configureEditor();
        if (this.editor instanceof JTextField && ((JTextField)this.editor).getBorder() instanceof FlatTextBorder) {
            ((JTextField)this.editor).setBorder(BorderFactory.createEmptyBorder());
        }
        if (this.editor instanceof JComponent) {
            ((JComponent)this.editor).setOpaque(false);
        }
        this.editor.applyComponentOrientation(this.comboBox.getComponentOrientation());
        this.updateEditorPadding();
        this.updateEditorColors();
        if (SystemInfo.isMacOS && this.editor instanceof JTextComponent) {
            final InputMap inputMap = ((JTextComponent)this.editor).getInputMap();
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("UP"));
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("KP_UP"));
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("DOWN"));
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("KP_DOWN"));
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("HOME"));
            new EditorDelegateAction(inputMap, KeyStroke.getKeyStroke("END"));
        }
    }
    
    private void updateEditorPadding() {
        if (!(this.editor instanceof JTextField)) {
            return;
        }
        final JTextField textField = (JTextField)this.editor;
        final Insets insets = textField.getInsets();
        Insets pad = this.padding;
        if (insets.top != 0 || insets.left != 0 || insets.bottom != 0 || insets.right != 0) {
            pad = new Insets(UIScale.unscale(Math.max(UIScale.scale(this.padding.top) - insets.top, 0)), UIScale.unscale(Math.max(UIScale.scale(this.padding.left) - insets.left, 0)), UIScale.unscale(Math.max(UIScale.scale(this.padding.bottom) - insets.bottom, 0)), UIScale.unscale(Math.max(UIScale.scale(this.padding.right) - insets.right, 0)));
        }
        textField.putClientProperty("JTextField.padding", pad);
    }
    
    private void updateEditorColors() {
        final boolean isTextComponent = this.editor instanceof JTextComponent;
        this.editor.setForeground(FlatUIUtils.nonUIResource(this.getForeground(isTextComponent || this.editor.isEnabled())));
        if (isTextComponent) {
            ((JTextComponent)this.editor).setDisabledTextColor(FlatUIUtils.nonUIResource(this.getForeground(false)));
        }
    }
    
    @Override
    protected JButton createArrowButton() {
        return new FlatComboBoxButton();
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        float focusWidth = FlatUIUtils.getBorderFocusWidth(c);
        float arc = FlatUIUtils.getBorderArc(c);
        boolean paintBackground = true;
        final boolean isCellRenderer = c.getParent() instanceof CellRendererPane;
        if (isCellRenderer) {
            focusWidth = 0.0f;
            arc = 0.0f;
            paintBackground = this.isCellRendererBackgroundChanged();
        }
        if (c.isOpaque() && (focusWidth > 0.0f || arc > 0.0f)) {
            FlatUIUtils.paintParentBackground(g, c);
        }
        final Graphics2D g2 = (Graphics2D)g;
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g2);
        final int width = c.getWidth();
        final int height = c.getHeight();
        final int arrowX = this.arrowButton.getX();
        final int arrowWidth = this.arrowButton.getWidth();
        final boolean paintButton = (this.comboBox.isEditable() || "button".equals(this.buttonStyle)) && !"none".equals(this.buttonStyle);
        final boolean enabled = this.comboBox.isEnabled();
        final boolean isLeftToRight = this.comboBox.getComponentOrientation().isLeftToRight();
        if (paintBackground || c.isOpaque()) {
            g2.setColor(this.getBackground(enabled));
            FlatUIUtils.paintComponentBackground(g2, 0, 0, width, height, focusWidth, arc);
            if (enabled && !isCellRenderer) {
                g2.setColor(paintButton ? this.buttonEditableBackground : (((this.buttonFocusedBackground != null || this.focusedBackground != null) && isPermanentFocusOwner(this.comboBox)) ? ((this.buttonFocusedBackground != null) ? this.buttonFocusedBackground : this.focusedBackground) : this.buttonBackground));
                final Shape oldClip = g2.getClip();
                if (isLeftToRight) {
                    g2.clipRect(arrowX, 0, width - arrowX, height);
                }
                else {
                    g2.clipRect(0, 0, arrowX + arrowWidth, height);
                }
                FlatUIUtils.paintComponentBackground(g2, 0, 0, width, height, focusWidth, arc);
                g2.setClip(oldClip);
            }
            if (paintButton) {
                g2.setColor(enabled ? this.borderColor : this.disabledBorderColor);
                final float lw = UIScale.scale(1.0f);
                final float lx = isLeftToRight ? ((float)arrowX) : (arrowX + arrowWidth - lw);
                g2.fill(new Rectangle2D.Float(lx, focusWidth, lw, height - 1 - focusWidth * 2.0f));
            }
        }
        FlatUIUtils.resetRenderingHints(g2, oldRenderingHints);
        this.paint(g, c);
    }
    
    @Override
    public void paintCurrentValue(final Graphics g, final Rectangle bounds, final boolean hasFocus) {
        this.paddingBorder.uninstall();
        ListCellRenderer<Object> renderer = this.comboBox.getRenderer();
        if (renderer == null) {
            renderer = new DefaultListCellRenderer();
        }
        final Component c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, false, false);
        c.setFont(this.comboBox.getFont());
        c.applyComponentOrientation(this.comboBox.getComponentOrientation());
        final boolean enabled = this.comboBox.isEnabled();
        c.setBackground(this.getBackground(enabled));
        c.setForeground(this.getForeground(enabled));
        final boolean shouldValidate = c instanceof JPanel;
        this.paddingBorder.install(c);
        this.currentValuePane.paintComponent(g, c, this.comboBox, bounds.x, bounds.y, bounds.width, bounds.height, shouldValidate);
        this.paddingBorder.uninstall();
    }
    
    @Override
    public void paintCurrentValueBackground(final Graphics g, final Rectangle bounds, final boolean hasFocus) {
    }
    
    protected Color getBackground(final boolean enabled) {
        if (!enabled) {
            return this.isIntelliJTheme ? FlatUIUtils.getParentBackground(this.comboBox) : this.disabledBackground;
        }
        final Color background = this.comboBox.getBackground();
        if (!(background instanceof UIResource)) {
            return background;
        }
        if (this.focusedBackground != null && isPermanentFocusOwner(this.comboBox)) {
            return this.focusedBackground;
        }
        return (this.editableBackground != null && this.comboBox.isEditable()) ? this.editableBackground : background;
    }
    
    protected Color getForeground(final boolean enabled) {
        return enabled ? this.comboBox.getForeground() : this.disabledForeground;
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        final Dimension minimumSize = super.getMinimumSize(c);
        final int fw = Math.round(FlatUIUtils.getBorderFocusWidth(c) * 2.0f);
        minimumSize.width = Math.max(minimumSize.width, UIScale.scale(FlatUIUtils.minimumWidth(c, this.minimumWidth)) + fw);
        return minimumSize;
    }
    
    @Override
    protected Dimension getDefaultSize() {
        this.paddingBorder.uninstall();
        final Dimension size = super.getDefaultSize();
        this.paddingBorder.uninstall();
        return size;
    }
    
    @Override
    protected Dimension getDisplaySize() {
        this.paddingBorder.uninstall();
        final Dimension displaySize = super.getDisplaySize();
        this.paddingBorder.uninstall();
        int displayWidth = displaySize.width - this.padding.left - this.padding.right;
        final int displayHeight = displaySize.height - this.padding.top - this.padding.bottom;
        if (displayWidth == 100 && this.comboBox.isEditable() && this.comboBox.getItemCount() == 0 && this.comboBox.getPrototypeDisplayValue() == null) {
            displayWidth = Math.max(this.getDefaultSize().width, this.editor.getPreferredSize().width);
        }
        return new Dimension(displayWidth, displayHeight);
    }
    
    @Override
    protected Dimension getSizeForComponent(final Component comp) {
        this.paddingBorder.install(comp);
        final Dimension size = super.getSizeForComponent(comp);
        this.paddingBorder.uninstall();
        return size;
    }
    
    private boolean isCellRenderer() {
        return this.comboBox.getParent() instanceof CellRendererPane;
    }
    
    private boolean isCellRendererBackgroundChanged() {
        final Container parentParent = this.comboBox.getParent().getParent();
        return parentParent != null && !this.comboBox.getBackground().equals(parentParent.getBackground());
    }
    
    public static boolean isPermanentFocusOwner(final JComboBox<?> comboBox) {
        if (comboBox.isEditable()) {
            final Component editorComponent = comboBox.getEditor().getEditorComponent();
            return editorComponent != null && FlatUIUtils.isPermanentFocusOwner(editorComponent);
        }
        return FlatUIUtils.isPermanentFocusOwner(comboBox);
    }
    
    protected class FlatComboBoxButton extends FlatArrowButton
    {
        protected FlatComboBoxButton(final FlatComboBoxUI this$0) {
            this(this$0, 5, this$0.arrowType, this$0.buttonArrowColor, this$0.buttonDisabledArrowColor, this$0.buttonHoverArrowColor, null, this$0.buttonPressedArrowColor, null);
        }
        
        protected FlatComboBoxButton(final int direction, final String type, final Color foreground, final Color disabledForeground, final Color hoverForeground, final Color hoverBackground, final Color pressedForeground, final Color pressedBackground) {
            super(direction, type, foreground, disabledForeground, hoverForeground, hoverBackground, pressedForeground, pressedBackground);
        }
        
        @Override
        protected boolean isHover() {
            return super.isHover() || (!FlatComboBoxUI.this.comboBox.isEditable() && FlatComboBoxUI.this.hover);
        }
        
        @Override
        protected boolean isPressed() {
            return super.isPressed() || (!FlatComboBoxUI.this.comboBox.isEditable() && FlatComboBoxUI.this.pressed);
        }
        
        @Override
        protected Color getArrowColor() {
            if (FlatComboBoxUI.this.isCellRenderer() && FlatComboBoxUI.this.isCellRendererBackgroundChanged()) {
                return FlatComboBoxUI.this.comboBox.getForeground();
            }
            return super.getArrowColor();
        }
    }
    
    protected class FlatComboPopup extends BasicComboPopup
    {
        protected FlatComboPopup(final JComboBox combo) {
            super(combo);
            final ComponentOrientation o = this.comboBox.getComponentOrientation();
            this.list.setComponentOrientation(o);
            this.scroller.setComponentOrientation(o);
            this.setComponentOrientation(o);
        }
        
        @Override
        protected Rectangle computePopupBounds(int px, final int py, int pw, final int ph) {
            int displayWidth = FlatComboBoxUI.this.getDisplaySize().width;
            for (final Border border : new Border[] { this.scroller.getViewportBorder(), this.scroller.getBorder() }) {
                if (border != null) {
                    final Insets borderInsets = border.getBorderInsets(null);
                    displayWidth += borderInsets.left + borderInsets.right;
                }
            }
            final JScrollBar verticalScrollBar = this.scroller.getVerticalScrollBar();
            if (verticalScrollBar != null) {
                displayWidth += verticalScrollBar.getPreferredSize().width;
            }
            if (displayWidth > pw) {
                final GraphicsConfiguration gc = this.comboBox.getGraphicsConfiguration();
                if (gc != null) {
                    final Rectangle screenBounds = gc.getBounds();
                    final Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
                    displayWidth = Math.min(displayWidth, screenBounds.width - screenInsets.left - screenInsets.right);
                }
                else {
                    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    displayWidth = Math.min(displayWidth, screenSize.width);
                }
                final int diff = displayWidth - pw;
                pw = displayWidth;
                if (!this.comboBox.getComponentOrientation().isLeftToRight()) {
                    px -= diff;
                }
            }
            return super.computePopupBounds(px, py, pw, ph);
        }
        
        @Override
        protected void configurePopup() {
            super.configurePopup();
            final Border border = UIManager.getBorder("PopupMenu.border");
            if (border != null) {
                this.setBorder(border);
            }
        }
        
        @Override
        protected void configureList() {
            super.configureList();
            this.list.setCellRenderer(new PopupListCellRenderer());
            if (FlatComboBoxUI.this.popupBackground != null) {
                this.list.setBackground(FlatComboBoxUI.this.popupBackground);
            }
        }
        
        @Override
        protected PropertyChangeListener createPropertyChangeListener() {
            final PropertyChangeListener superListener = super.createPropertyChangeListener();
            return e -> {
                superListener.propertyChange(e);
                if (e.getPropertyName() == "renderer") {
                    this.list.setCellRenderer(new PopupListCellRenderer());
                }
            };
        }
        
        @Override
        protected int getPopupHeightForRowCount(final int maxRowCount) {
            final int height = super.getPopupHeightForRowCount(maxRowCount);
            FlatComboBoxUI.this.paddingBorder.uninstall();
            return height;
        }
        
        @Override
        protected void paintChildren(final Graphics g) {
            super.paintChildren(g);
            FlatComboBoxUI.this.paddingBorder.uninstall();
        }
        
        private class PopupListCellRenderer implements ListCellRenderer
        {
            @Override
            public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                FlatComboBoxUI.this.paddingBorder.uninstall();
                ListCellRenderer renderer = FlatComboPopup.this.comboBox.getRenderer();
                if (renderer == null) {
                    renderer = new DefaultListCellRenderer();
                }
                final Component c = renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.applyComponentOrientation(FlatComboPopup.this.comboBox.getComponentOrientation());
                FlatComboBoxUI.this.paddingBorder.install(c);
                return c;
            }
        }
    }
    
    private static class CellPaddingBorder extends AbstractBorder
    {
        private final Insets padding;
        private JComponent rendererComponent;
        private Border rendererBorder;
        
        CellPaddingBorder(final Insets padding) {
            this.padding = padding;
        }
        
        void install(final Component c) {
            if (!(c instanceof JComponent)) {
                return;
            }
            final JComponent jc = (JComponent)c;
            final Border oldBorder = jc.getBorder();
            if (oldBorder == this) {
                return;
            }
            if (oldBorder instanceof CellPaddingBorder) {
                ((CellPaddingBorder)oldBorder).uninstall();
            }
            this.uninstall();
            this.rendererComponent = jc;
            this.rendererBorder = jc.getBorder();
            this.rendererComponent.setBorder(this);
        }
        
        void uninstall() {
            if (this.rendererComponent == null) {
                return;
            }
            if (this.rendererComponent.getBorder() == this) {
                this.rendererComponent.setBorder(this.rendererBorder);
            }
            this.rendererComponent = null;
            this.rendererBorder = null;
        }
        
        @Override
        public Insets getBorderInsets(final Component c, final Insets insets) {
            final Insets padding = UIScale.scale(this.padding);
            if (this.rendererBorder != null) {
                final Insets insideInsets = this.rendererBorder.getBorderInsets(c);
                insets.top = Math.max(padding.top, insideInsets.top);
                insets.left = Math.max(padding.left, insideInsets.left);
                insets.bottom = Math.max(padding.bottom, insideInsets.bottom);
                insets.right = Math.max(padding.right, insideInsets.right);
            }
            else {
                insets.top = padding.top;
                insets.left = padding.left;
                insets.bottom = padding.bottom;
                insets.right = padding.right;
            }
            return insets;
        }
        
        @Override
        public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
            if (this.rendererBorder != null) {
                this.rendererBorder.paintBorder(c, g, x, y, width, height);
            }
        }
    }
    
    private class EditorDelegateAction extends AbstractAction
    {
        private final KeyStroke keyStroke;
        
        EditorDelegateAction(final InputMap inputMap, final KeyStroke keyStroke) {
            inputMap.put(this.keyStroke = keyStroke, this);
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            final ActionListener action = FlatComboBoxUI.this.comboBox.getActionForKeyStroke(this.keyStroke);
            if (action != null) {
                action.actionPerformed(new ActionEvent(FlatComboBoxUI.this.comboBox, e.getID(), e.getActionCommand(), e.getWhen(), e.getModifiers()));
            }
        }
    }
}
