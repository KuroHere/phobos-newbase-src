// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.*;
import java.util.function.*;
import java.beans.*;
import javax.swing.plaf.basic.*;
import java.util.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import javax.swing.*;

public class FlatLabelUI extends BasicLabelUI
{
    private Color disabledForeground;
    private boolean defaults_initialized;
    private static Set<String> tagsUseFontSizeSet;
    
    public FlatLabelUI() {
        this.defaults_initialized = false;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatLabelUI.class, (Supplier<ComponentUI>)FlatLabelUI::new);
    }
    
    @Override
    protected void installDefaults(final JLabel c) {
        super.installDefaults(c);
        if (!this.defaults_initialized) {
            this.disabledForeground = UIManager.getColor("Label.disabledForeground");
            this.defaults_initialized = true;
        }
    }
    
    @Override
    protected void uninstallDefaults(final JLabel c) {
        super.uninstallDefaults(c);
        this.defaults_initialized = false;
    }
    
    @Override
    protected void installComponents(final JLabel c) {
        super.installComponents(c);
        updateHTMLRenderer(c, c.getText(), false);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        final String name = e.getPropertyName();
        if (name == "text" || name == "font" || name == "foreground") {
            final JLabel label = (JLabel)e.getSource();
            updateHTMLRenderer(label, label.getText(), true);
        }
        else {
            super.propertyChange(e);
        }
    }
    
    static void updateHTMLRenderer(final JComponent c, String text, final boolean always) {
        if (BasicHTML.isHTMLString(text) && c.getClientProperty("html.disable") != Boolean.TRUE && needsFontBaseSize(text)) {
            String style = "<style>BASE_SIZE " + c.getFont().getSize() + "</style>";
            final String lowerText = text.toLowerCase();
            final int headIndex;
            int insertIndex;
            if ((headIndex = lowerText.indexOf("<head>")) >= 0) {
                insertIndex = headIndex + "<head>".length();
            }
            else {
                final int styleIndex;
                if ((styleIndex = lowerText.indexOf("<style>")) >= 0) {
                    insertIndex = styleIndex;
                }
                else {
                    style = "<head>" + style + "</head>";
                    insertIndex = "<html>".length();
                }
            }
            text = text.substring(0, insertIndex) + style + text.substring(insertIndex);
        }
        else if (!always) {
            return;
        }
        BasicHTML.updateRenderer(c, text);
    }
    
    private static boolean needsFontBaseSize(final String text) {
        if (FlatLabelUI.tagsUseFontSizeSet == null) {
            FlatLabelUI.tagsUseFontSizeSet = new HashSet<String>(Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6", "code", "kbd", "big", "small", "samp"));
        }
        for (int textLength = text.length(), i = 6; i < textLength - 1; ++i) {
            if (text.charAt(i) == '<') {
                switch (text.charAt(i + 1)) {
                    case 'B':
                    case 'C':
                    case 'H':
                    case 'K':
                    case 'S':
                    case 'b':
                    case 'c':
                    case 'h':
                    case 'k':
                    case 's': {
                        final int tagBegin = i + 1;
                        i += 2;
                        while (i < textLength) {
                            if (!Character.isLetterOrDigit(text.charAt(i))) {
                                final String tag = text.substring(tagBegin, i).toLowerCase();
                                if (FlatLabelUI.tagsUseFontSizeSet.contains(tag)) {
                                    return true;
                                }
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }
    
    static Graphics createGraphicsHTMLTextYCorrection(final Graphics g, final JComponent c) {
        return (c.getClientProperty("html") != null) ? HiDPIUtils.createGraphicsTextYCorrection((Graphics2D)g) : g;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        super.paint(createGraphicsHTMLTextYCorrection(g, c), c);
    }
    
    @Override
    protected void paintEnabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
        final int mnemIndex = FlatLaf.isShowMnemonics() ? l.getDisplayedMnemonicIndex() : -1;
        g.setColor(l.getForeground());
        FlatUIUtils.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
    }
    
    @Override
    protected void paintDisabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
        final int mnemIndex = FlatLaf.isShowMnemonics() ? l.getDisplayedMnemonicIndex() : -1;
        g.setColor(this.disabledForeground);
        FlatUIUtils.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
    }
    
    @Override
    protected String layoutCL(final JLabel label, final FontMetrics fontMetrics, final String text, final Icon icon, final Rectangle viewR, final Rectangle iconR, final Rectangle textR) {
        return SwingUtilities.layoutCompoundLabel(label, fontMetrics, text, icon, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), viewR, iconR, textR, UIScale.scale(label.getIconTextGap()));
    }
}
