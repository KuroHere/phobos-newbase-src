// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.plaf.*;
import com.formdev.flatlaf.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

public class FlatSpinnerUI extends BasicSpinnerUI
{
    private Handler handler;
    protected int minimumWidth;
    protected String buttonStyle;
    protected String arrowType;
    protected boolean isIntelliJTheme;
    protected Color borderColor;
    protected Color disabledBorderColor;
    protected Color disabledBackground;
    protected Color disabledForeground;
    protected Color focusedBackground;
    protected Color buttonBackground;
    protected Color buttonArrowColor;
    protected Color buttonDisabledArrowColor;
    protected Color buttonHoverArrowColor;
    protected Color buttonPressedArrowColor;
    protected Insets padding;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatSpinnerUI();
    }
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.installProperty(this.spinner, "opaque", false);
        this.minimumWidth = UIManager.getInt("Component.minimumWidth");
        this.buttonStyle = UIManager.getString("Spinner.buttonStyle");
        this.arrowType = UIManager.getString("Component.arrowType");
        this.isIntelliJTheme = UIManager.getBoolean("Component.isIntelliJTheme");
        this.borderColor = UIManager.getColor("Component.borderColor");
        this.disabledBorderColor = UIManager.getColor("Component.disabledBorderColor");
        this.disabledBackground = UIManager.getColor("Spinner.disabledBackground");
        this.disabledForeground = UIManager.getColor("Spinner.disabledForeground");
        this.focusedBackground = UIManager.getColor("Spinner.focusedBackground");
        this.buttonBackground = UIManager.getColor("Spinner.buttonBackground");
        this.buttonArrowColor = UIManager.getColor("Spinner.buttonArrowColor");
        this.buttonDisabledArrowColor = UIManager.getColor("Spinner.buttonDisabledArrowColor");
        this.buttonHoverArrowColor = UIManager.getColor("Spinner.buttonHoverArrowColor");
        this.buttonPressedArrowColor = UIManager.getColor("Spinner.buttonPressedArrowColor");
        this.padding = UIManager.getInsets("Spinner.padding");
        MigLayoutVisualPadding.install(this.spinner);
    }
    
    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        this.borderColor = null;
        this.disabledBorderColor = null;
        this.disabledBackground = null;
        this.disabledForeground = null;
        this.focusedBackground = null;
        this.buttonBackground = null;
        this.buttonArrowColor = null;
        this.buttonDisabledArrowColor = null;
        this.buttonHoverArrowColor = null;
        this.buttonPressedArrowColor = null;
        this.padding = null;
        MigLayoutVisualPadding.uninstall(this.spinner);
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.addEditorFocusListener(this.spinner.getEditor());
        this.spinner.addFocusListener(this.getHandler());
        this.spinner.addPropertyChangeListener(this.getHandler());
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        this.removeEditorFocusListener(this.spinner.getEditor());
        this.spinner.removeFocusListener(this.getHandler());
        this.spinner.removePropertyChangeListener(this.getHandler());
        this.handler = null;
    }
    
    private Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler();
        }
        return this.handler;
    }
    
    @Override
    protected JComponent createEditor() {
        final JComponent editor = super.createEditor();
        editor.setOpaque(false);
        final JTextField textField = getEditorTextField(editor);
        if (textField != null) {
            textField.setOpaque(false);
        }
        this.updateEditorPadding();
        this.updateEditorColors();
        return editor;
    }
    
    @Override
    protected void replaceEditor(final JComponent oldEditor, final JComponent newEditor) {
        super.replaceEditor(oldEditor, newEditor);
        this.removeEditorFocusListener(oldEditor);
        this.addEditorFocusListener(newEditor);
        this.updateEditorPadding();
        this.updateEditorColors();
    }
    
    private void addEditorFocusListener(final JComponent editor) {
        final JTextField textField = getEditorTextField(editor);
        if (textField != null) {
            textField.addFocusListener(this.getHandler());
        }
    }
    
    private void removeEditorFocusListener(final JComponent editor) {
        final JTextField textField = getEditorTextField(editor);
        if (textField != null) {
            textField.removeFocusListener(this.getHandler());
        }
    }
    
    private void updateEditorPadding() {
        final JTextField textField = getEditorTextField(this.spinner.getEditor());
        if (textField != null) {
            textField.putClientProperty("JTextField.padding", this.padding);
        }
    }
    
    private void updateEditorColors() {
        final JTextField textField = getEditorTextField(this.spinner.getEditor());
        if (textField != null) {
            textField.setForeground(FlatUIUtils.nonUIResource(this.getForeground(true)));
            textField.setDisabledTextColor(FlatUIUtils.nonUIResource(this.getForeground(false)));
        }
    }
    
    private static JTextField getEditorTextField(final JComponent editor) {
        return (editor instanceof JSpinner.DefaultEditor) ? ((JSpinner.DefaultEditor)editor).getTextField() : null;
    }
    
    public static boolean isPermanentFocusOwner(final JSpinner spinner) {
        if (FlatUIUtils.isPermanentFocusOwner(spinner)) {
            return true;
        }
        final JTextField textField = getEditorTextField(spinner.getEditor());
        return textField != null && FlatUIUtils.isPermanentFocusOwner(textField);
    }
    
    protected Color getBackground(final boolean enabled) {
        if (!enabled) {
            return this.isIntelliJTheme ? FlatUIUtils.getParentBackground(this.spinner) : this.disabledBackground;
        }
        final Color background = this.spinner.getBackground();
        if (!(background instanceof UIResource)) {
            return background;
        }
        if (this.focusedBackground != null && isPermanentFocusOwner(this.spinner)) {
            return this.focusedBackground;
        }
        return background;
    }
    
    protected Color getForeground(final boolean enabled) {
        return enabled ? this.spinner.getForeground() : this.disabledForeground;
    }
    
    @Override
    protected LayoutManager createLayout() {
        return this.getHandler();
    }
    
    @Override
    protected Component createNextButton() {
        return this.createArrowButton(1, "Spinner.nextButton");
    }
    
    @Override
    protected Component createPreviousButton() {
        return this.createArrowButton(5, "Spinner.previousButton");
    }
    
    private Component createArrowButton(final int direction, final String name) {
        final FlatArrowButton button = new FlatArrowButton(direction, this.arrowType, this.buttonArrowColor, this.buttonDisabledArrowColor, this.buttonHoverArrowColor, null, this.buttonPressedArrowColor, null);
        button.setName(name);
        button.setYOffset((direction == 1) ? 1.25f : -1.25f);
        if (direction == 1) {
            this.installNextButtonListeners(button);
        }
        else {
            this.installPreviousButtonListeners(button);
        }
        return button;
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        final float focusWidth = FlatUIUtils.getBorderFocusWidth(c);
        final float arc = FlatUIUtils.getBorderArc(c);
        if (c.isOpaque() && (focusWidth > 0.0f || arc > 0.0f)) {
            FlatUIUtils.paintParentBackground(g, c);
        }
        final Graphics2D g2 = (Graphics2D)g;
        final Object[] oldRenderingHints = FlatUIUtils.setRenderingHints(g2);
        final int width = c.getWidth();
        final int height = c.getHeight();
        final boolean enabled = this.spinner.isEnabled();
        g2.setColor(this.getBackground(enabled));
        FlatUIUtils.paintComponentBackground(g2, 0, 0, width, height, focusWidth, arc);
        final boolean paintButton = !"none".equals(this.buttonStyle);
        final Handler handler = this.getHandler();
        if (paintButton && (handler.nextButton != null || handler.previousButton != null)) {
            final Component button = (handler.nextButton != null) ? handler.nextButton : handler.previousButton;
            final int arrowX = button.getX();
            final int arrowWidth = button.getWidth();
            final boolean isLeftToRight = this.spinner.getComponentOrientation().isLeftToRight();
            if (enabled) {
                g2.setColor(this.buttonBackground);
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
            g2.setColor(enabled ? this.borderColor : this.disabledBorderColor);
            final float lw = UIScale.scale(1.0f);
            final float lx = isLeftToRight ? ((float)arrowX) : (arrowX + arrowWidth - lw);
            g2.fill(new Rectangle2D.Float(lx, focusWidth, lw, height - 1 - focusWidth * 2.0f));
        }
        this.paint(g, c);
        FlatUIUtils.resetRenderingHints(g, oldRenderingHints);
    }
    
    private class Handler implements LayoutManager, FocusListener, PropertyChangeListener
    {
        private Component editor;
        private Component nextButton;
        private Component previousButton;
        
        private Handler() {
            this.editor = null;
        }
        
        @Override
        public void addLayoutComponent(final String name, final Component c) {
            switch (name) {
                case "Editor": {
                    this.editor = c;
                    break;
                }
                case "Next": {
                    this.nextButton = c;
                    break;
                }
                case "Previous": {
                    this.previousButton = c;
                    break;
                }
            }
        }
        
        @Override
        public void removeLayoutComponent(final Component c) {
            if (c == this.editor) {
                this.editor = null;
            }
            else if (c == this.nextButton) {
                this.nextButton = null;
            }
            else if (c == this.previousButton) {
                this.previousButton = null;
            }
        }
        
        @Override
        public Dimension preferredLayoutSize(final Container parent) {
            final Insets insets = parent.getInsets();
            final Insets padding = UIScale.scale(FlatSpinnerUI.this.padding);
            final Dimension editorSize = (this.editor != null) ? this.editor.getPreferredSize() : new Dimension(0, 0);
            final int minimumWidth = FlatUIUtils.minimumWidth(FlatSpinnerUI.this.spinner, FlatSpinnerUI.this.minimumWidth);
            final int innerHeight = editorSize.height + padding.top + padding.bottom;
            final float focusWidth = FlatUIUtils.getBorderFocusWidth(FlatSpinnerUI.this.spinner);
            return new Dimension(Math.max(insets.left + insets.right + editorSize.width + padding.left + padding.right + innerHeight, UIScale.scale(minimumWidth) + Math.round(focusWidth * 2.0f)), insets.top + insets.bottom + innerHeight);
        }
        
        @Override
        public Dimension minimumLayoutSize(final Container parent) {
            return this.preferredLayoutSize(parent);
        }
        
        @Override
        public void layoutContainer(final Container parent) {
            final Dimension size = parent.getSize();
            final Insets insets = parent.getInsets();
            final Rectangle r = FlatUIUtils.subtractInsets(new Rectangle(size), insets);
            if (this.nextButton == null && this.previousButton == null) {
                if (this.editor != null) {
                    this.editor.setBounds(r);
                }
                return;
            }
            final Rectangle editorRect = new Rectangle(r);
            final Rectangle buttonsRect = new Rectangle(r);
            final FontMetrics fm = FlatSpinnerUI.this.spinner.getFontMetrics(FlatSpinnerUI.this.spinner.getFont());
            final int maxButtonWidth = fm.getHeight() + UIScale.scale(FlatSpinnerUI.this.padding.top) + UIScale.scale(FlatSpinnerUI.this.padding.bottom);
            final int buttonsWidth = Math.min(parent.getPreferredSize().height - insets.top - insets.bottom, maxButtonWidth);
            buttonsRect.width = buttonsWidth;
            if (parent.getComponentOrientation().isLeftToRight()) {
                final Rectangle rectangle = editorRect;
                rectangle.width -= buttonsWidth;
                final Rectangle rectangle2 = buttonsRect;
                rectangle2.x += editorRect.width;
            }
            else {
                final Rectangle rectangle3 = editorRect;
                rectangle3.x += buttonsWidth;
                final Rectangle rectangle4 = editorRect;
                rectangle4.width -= buttonsWidth;
            }
            if (this.editor != null) {
                this.editor.setBounds(editorRect);
            }
            final int nextHeight = buttonsRect.height / 2 + buttonsRect.height % 2;
            if (this.nextButton != null) {
                this.nextButton.setBounds(buttonsRect.x, buttonsRect.y, buttonsRect.width, nextHeight);
            }
            if (this.previousButton != null) {
                final int previousY = buttonsRect.y + buttonsRect.height - nextHeight;
                this.previousButton.setBounds(buttonsRect.x, previousY, buttonsRect.width, nextHeight);
            }
        }
        
        @Override
        public void focusGained(final FocusEvent e) {
            FlatSpinnerUI.this.spinner.repaint();
            if (e.getComponent() == FlatSpinnerUI.this.spinner) {
                final JTextField textField = getEditorTextField(FlatSpinnerUI.this.spinner.getEditor());
                if (textField != null) {
                    textField.requestFocusInWindow();
                }
            }
        }
        
        @Override
        public void focusLost(final FocusEvent e) {
            FlatSpinnerUI.this.spinner.repaint();
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            final String propertyName = e.getPropertyName();
            switch (propertyName) {
                case "foreground":
                case "enabled": {
                    FlatSpinnerUI.this.updateEditorColors();
                    break;
                }
                case "JComponent.roundRect": {
                    FlatSpinnerUI.this.spinner.repaint();
                    break;
                }
                case "JComponent.minimumWidth": {
                    FlatSpinnerUI.this.spinner.revalidate();
                    break;
                }
            }
        }
    }
}
