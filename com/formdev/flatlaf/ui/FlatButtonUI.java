// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.util.function.*;
import javax.swing.plaf.*;
import com.formdev.flatlaf.util.*;
import javax.swing.plaf.basic.*;
import java.beans.*;
import java.awt.geom.*;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FlatButtonUI extends BasicButtonUI
{
    protected int minimumWidth;
    protected int iconTextGap;
    protected Color background;
    protected Color foreground;
    protected Color startBackground;
    protected Color endBackground;
    protected Color focusedBackground;
    protected Color hoverBackground;
    protected Color pressedBackground;
    protected Color selectedBackground;
    protected Color selectedForeground;
    protected Color disabledBackground;
    protected Color disabledText;
    protected Color disabledSelectedBackground;
    protected Color defaultBackground;
    protected Color defaultEndBackground;
    protected Color defaultForeground;
    protected Color defaultFocusedBackground;
    protected Color defaultHoverBackground;
    protected Color defaultPressedBackground;
    protected boolean defaultBoldText;
    protected int shadowWidth;
    protected Color shadowColor;
    protected Color defaultShadowColor;
    protected Insets toolbarSpacingInsets;
    protected Color toolbarHoverBackground;
    protected Color toolbarPressedBackground;
    protected Color toolbarSelectedBackground;
    private Icon helpButtonIcon;
    private Insets defaultMargin;
    private boolean defaults_initialized;
    static final int TYPE_OTHER = -1;
    static final int TYPE_SQUARE = 0;
    static final int TYPE_ROUND_RECT = 1;
    
    public FlatButtonUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatButtonUI.class, (Supplier<ComponentUI>)FlatButtonUI::new);
    }
    
    @Override
    protected void installDefaults(final AbstractButton b) {
        super.installDefaults(b);
        if (!this.defaults_initialized) {
            final String prefix = this.getPropertyPrefix();
            this.minimumWidth = UIManager.getInt(prefix + "minimumWidth");
            this.iconTextGap = FlatUIUtils.getUIInt(prefix + "iconTextGap", 4);
            this.background = UIManager.getColor(prefix + "background");
            this.foreground = UIManager.getColor(prefix + "foreground");
            this.startBackground = UIManager.getColor(prefix + "startBackground");
            this.endBackground = UIManager.getColor(prefix + "endBackground");
            this.focusedBackground = UIManager.getColor(prefix + "focusedBackground");
            this.hoverBackground = UIManager.getColor(prefix + "hoverBackground");
            this.pressedBackground = UIManager.getColor(prefix + "pressedBackground");
            this.selectedBackground = UIManager.getColor(prefix + "selectedBackground");
            this.selectedForeground = UIManager.getColor(prefix + "selectedForeground");
            this.disabledBackground = UIManager.getColor(prefix + "disabledBackground");
            this.disabledText = UIManager.getColor(prefix + "disabledText");
            this.disabledSelectedBackground = UIManager.getColor(prefix + "disabledSelectedBackground");
            if (UIManager.getBoolean("Button.paintShadow")) {
                this.shadowWidth = FlatUIUtils.getUIInt("Button.shadowWidth", 2);
                this.shadowColor = UIManager.getColor("Button.shadowColor");
                this.defaultShadowColor = UIManager.getColor("Button.default.shadowColor");
            }
            else {
                this.shadowWidth = 0;
                this.shadowColor = null;
                this.defaultShadowColor = null;
            }
            this.defaultBackground = FlatUIUtils.getUIColor("Button.default.startBackground", "Button.default.background");
            this.defaultEndBackground = UIManager.getColor("Button.default.endBackground");
            this.defaultForeground = UIManager.getColor("Button.default.foreground");
            this.defaultFocusedBackground = UIManager.getColor("Button.default.focusedBackground");
            this.defaultHoverBackground = UIManager.getColor("Button.default.hoverBackground");
            this.defaultPressedBackground = UIManager.getColor("Button.default.pressedBackground");
            this.defaultBoldText = UIManager.getBoolean("Button.default.boldText");
            this.toolbarSpacingInsets = UIManager.getInsets("Button.toolbar.spacingInsets");
            this.toolbarHoverBackground = UIManager.getColor(prefix + "toolbar.hoverBackground");
            this.toolbarPressedBackground = UIManager.getColor(prefix + "toolbar.pressedBackground");
            this.toolbarSelectedBackground = UIManager.getColor(prefix + "toolbar.selectedBackground");
            this.helpButtonIcon = UIManager.getIcon("HelpButton.icon");
            this.defaultMargin = UIManager.getInsets(prefix + "margin");
            this.defaults_initialized = true;
        }
        if (this.startBackground != null) {
            final Color bg = b.getBackground();
            if (bg == null || bg instanceof UIResource) {
                b.setBackground(this.startBackground);
            }
        }
        LookAndFeel.installProperty(b, "opaque", false);
        LookAndFeel.installProperty(b, "iconTextGap", UIScale.scale(this.iconTextGap));
        MigLayoutVisualPadding.install(b);
    }
    
    @Override
    protected void uninstallDefaults(final AbstractButton b) {
        super.uninstallDefaults(b);
        MigLayoutVisualPadding.uninstall(b);
        this.defaults_initialized = false;
    }
    
    @Override
    protected BasicButtonListener createButtonListener(final AbstractButton b) {
        return new FlatButtonListener(b);
    }
    
    protected void propertyChange(final AbstractButton b, final PropertyChangeEvent e) {
        final String propertyName = e.getPropertyName();
        switch (propertyName) {
            case "JButton.squareSize":
            case "JComponent.minimumWidth":
            case "JComponent.minimumHeight": {
                b.revalidate();
                break;
            }
            case "JButton.buttonType": {
                b.revalidate();
                b.repaint();
                break;
            }
        }
    }
    
    static boolean isContentAreaFilled(final Component c) {
        return !(c instanceof AbstractButton) || ((AbstractButton)c).isContentAreaFilled();
    }
    
    public static boolean isFocusPainted(final Component c) {
        return !(c instanceof AbstractButton) || ((AbstractButton)c).isFocusPainted();
    }
    
    static boolean isDefaultButton(final Component c) {
        return c instanceof JButton && ((JButton)c).isDefaultButton();
    }
    
    static boolean isIconOnlyOrSingleCharacterButton(final Component c) {
        if (!(c instanceof JButton) && !(c instanceof JToggleButton)) {
            return false;
        }
        final Icon icon = ((AbstractButton)c).getIcon();
        final String text = ((AbstractButton)c).getText();
        return (icon != null && (text == null || text.isEmpty())) || (icon == null && text != null && ("...".equals(text) || text.length() == 1 || (text.length() == 2 && Character.isSurrogatePair(text.charAt(0), text.charAt(1)))));
    }
    
    static int getButtonType(final Component c) {
        if (!(c instanceof AbstractButton)) {
            return -1;
        }
        final Object value = ((AbstractButton)c).getClientProperty("JButton.buttonType");
        if (!(value instanceof String)) {
            return -1;
        }
        final String s = (String)value;
        switch (s) {
            case "square": {
                return 0;
            }
            case "roundRect": {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    static boolean isHelpButton(final Component c) {
        return c instanceof JButton && FlatClientProperties.clientPropertyEquals((JComponent)c, "JButton.buttonType", "help");
    }
    
    static boolean isToolBarButton(final Component c) {
        return c.getParent() instanceof JToolBar || (c instanceof AbstractButton && FlatClientProperties.clientPropertyEquals((JComponent)c, "JButton.buttonType", "toolBarButton"));
    }
    
    static boolean isBorderlessButton(final Component c) {
        return c instanceof AbstractButton && FlatClientProperties.clientPropertyEquals((JComponent)c, "JButton.buttonType", "borderless");
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        if (c.isOpaque()) {
            FlatUIUtils.paintParentBackground(g, c);
        }
        if (isHelpButton(c)) {
            this.helpButtonIcon.paintIcon(c, g, 0, 0);
            return;
        }
        if (isContentAreaFilled(c)) {
            this.paintBackground(g, c);
        }
        this.paint(g, c);
    }
    
    protected void paintBackground(final Graphics g, final JComponent c) {
        final Color background = this.getBackground(c);
        if (background == null) {
            return;
        }
        final Graphics2D g2 = (Graphics2D)g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);
            final boolean isToolBarButton = isToolBarButton(c);
            final float focusWidth = isToolBarButton ? 0.0f : FlatUIUtils.getBorderFocusWidth(c);
            final float arc = FlatUIUtils.getBorderArc(c);
            final boolean def = isDefaultButton(c);
            int x = 0;
            int y = 0;
            int width = c.getWidth();
            int height = c.getHeight();
            if (isToolBarButton) {
                final Insets spacing = UIScale.scale(this.toolbarSpacingInsets);
                x += spacing.left;
                y += spacing.top;
                width -= spacing.left + spacing.right;
                height -= spacing.top + spacing.bottom;
            }
            final Color shadowColor = def ? this.defaultShadowColor : this.shadowColor;
            if (shadowColor != null && this.shadowWidth > 0 && focusWidth > 0.0f && c.isEnabled() && !isToolBarButton && !isBorderlessButton(c) && (!isFocusPainted(c) || !FlatUIUtils.isPermanentFocusOwner(c))) {
                g2.setColor(shadowColor);
                g2.fill(new RoundRectangle2D.Float(focusWidth, focusWidth + UIScale.scale((float)this.shadowWidth), width - focusWidth * 2.0f, height - focusWidth * 2.0f, arc, arc));
            }
            final Color startBg = def ? this.defaultBackground : this.startBackground;
            final Color endBg = def ? this.defaultEndBackground : this.endBackground;
            if (background == startBg && endBg != null && !startBg.equals(endBg)) {
                g2.setPaint(new GradientPaint(0.0f, 0.0f, startBg, 0.0f, (float)height, endBg));
            }
            else {
                g2.setColor(FlatUIUtils.deriveColor(background, this.getBackgroundBase(c, def)));
            }
            FlatUIUtils.paintComponentBackground(g2, x, y, width, height, focusWidth, arc);
        }
        finally {
            g2.dispose();
        }
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        super.paint(FlatLabelUI.createGraphicsHTMLTextYCorrection(g, c), c);
    }
    
    @Override
    protected void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text) {
        if (isHelpButton(b)) {
            return;
        }
        if (this.defaultBoldText && isDefaultButton(b) && b.getFont() instanceof UIResource) {
            final Font boldFont = g.getFont().deriveFont(1);
            g.setFont(boldFont);
            final int boldWidth = b.getFontMetrics(boldFont).stringWidth(text);
            if (boldWidth > textRect.width) {
                textRect.x -= (boldWidth - textRect.width) / 2;
                textRect.width = boldWidth;
            }
        }
        paintText(g, b, textRect, text, this.getForeground(b));
    }
    
    public static void paintText(final Graphics g, final AbstractButton b, final Rectangle textRect, final String text, final Color foreground) {
        final FontMetrics fm = b.getFontMetrics(b.getFont());
        final int mnemonicIndex = FlatLaf.isShowMnemonics() ? b.getDisplayedMnemonicIndex() : -1;
        g.setColor(foreground);
        FlatUIUtils.drawStringUnderlineCharAt(b, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
    }
    
    protected Color getBackground(final JComponent c) {
        final boolean toolBarButton = isToolBarButton(c) || isBorderlessButton(c);
        if (((AbstractButton)c).isSelected()) {
            return buttonStateColor(c, toolBarButton ? this.toolbarSelectedBackground : this.selectedBackground, toolBarButton ? this.toolbarSelectedBackground : this.disabledSelectedBackground, null, null, toolBarButton ? this.toolbarPressedBackground : this.pressedBackground);
        }
        if (toolBarButton) {
            final Color bg = c.getBackground();
            return buttonStateColor(c, this.isCustomBackground(bg) ? bg : null, null, null, this.toolbarHoverBackground, this.toolbarPressedBackground);
        }
        final boolean def = isDefaultButton(c);
        return buttonStateColor(c, this.getBackgroundBase(c, def), this.disabledBackground, this.isCustomBackground(c.getBackground()) ? null : (def ? this.defaultFocusedBackground : this.focusedBackground), def ? this.defaultHoverBackground : this.hoverBackground, def ? this.defaultPressedBackground : this.pressedBackground);
    }
    
    protected Color getBackgroundBase(final JComponent c, final boolean def) {
        final Color bg = c.getBackground();
        if (this.isCustomBackground(bg)) {
            return bg;
        }
        return def ? this.defaultBackground : bg;
    }
    
    protected boolean isCustomBackground(final Color bg) {
        return bg != this.background && (this.startBackground == null || bg != this.startBackground);
    }
    
    public static Color buttonStateColor(final Component c, final Color enabledColor, final Color disabledColor, final Color focusedColor, final Color hoverColor, final Color pressedColor) {
        if (!c.isEnabled()) {
            return disabledColor;
        }
        if (c instanceof AbstractButton) {
            final ButtonModel model = ((AbstractButton)c).getModel();
            if (pressedColor != null && model.isPressed()) {
                return pressedColor;
            }
            if (hoverColor != null && model.isRollover()) {
                return hoverColor;
            }
        }
        if (focusedColor != null && isFocusPainted(c) && FlatUIUtils.isPermanentFocusOwner(c)) {
            return focusedColor;
        }
        return enabledColor;
    }
    
    protected Color getForeground(final JComponent c) {
        if (!c.isEnabled()) {
            return this.disabledText;
        }
        if (((AbstractButton)c).isSelected() && !isToolBarButton(c) && !isBorderlessButton(c)) {
            return this.selectedForeground;
        }
        final Color fg = c.getForeground();
        if (this.isCustomForeground(fg)) {
            return fg;
        }
        final boolean def = isDefaultButton(c);
        return def ? this.defaultForeground : fg;
    }
    
    protected boolean isCustomForeground(final Color fg) {
        return fg != this.foreground;
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        if (isHelpButton(c)) {
            return new Dimension(this.helpButtonIcon.getIconWidth(), this.helpButtonIcon.getIconHeight());
        }
        final Dimension prefSize = super.getPreferredSize(c);
        if (prefSize == null) {
            return null;
        }
        final boolean isIconOnlyOrSingleCharacter = isIconOnlyOrSingleCharacterButton(c);
        if (FlatClientProperties.clientPropertyBoolean(c, "JButton.squareSize", false)) {
            final Dimension dimension = prefSize;
            final Dimension dimension2 = prefSize;
            final int max = Math.max(prefSize.width, prefSize.height);
            dimension2.height = max;
            dimension.width = max;
        }
        else if (isIconOnlyOrSingleCharacter && ((AbstractButton)c).getIcon() == null) {
            prefSize.width = Math.max(prefSize.width, prefSize.height);
        }
        else if (!isIconOnlyOrSingleCharacter && !isToolBarButton(c) && c.getBorder() instanceof FlatButtonBorder && this.hasDefaultMargins(c)) {
            final int fw = Math.round(FlatUIUtils.getBorderFocusWidth(c) * 2.0f);
            prefSize.width = Math.max(prefSize.width, UIScale.scale(FlatUIUtils.minimumWidth(c, this.minimumWidth)) + fw);
            prefSize.height = Math.max(prefSize.height, UIScale.scale(FlatUIUtils.minimumHeight(c, 0)) + fw);
        }
        return prefSize;
    }
    
    private boolean hasDefaultMargins(final JComponent c) {
        final Insets margin = ((AbstractButton)c).getMargin();
        return margin instanceof UIResource && Objects.equals(margin, this.defaultMargin);
    }
    
    protected class FlatButtonListener extends BasicButtonListener
    {
        private final AbstractButton b;
        
        protected FlatButtonListener(final AbstractButton b) {
            super(b);
            this.b = b;
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            super.propertyChange(e);
            FlatButtonUI.this.propertyChange(this.b, e);
        }
    }
}
