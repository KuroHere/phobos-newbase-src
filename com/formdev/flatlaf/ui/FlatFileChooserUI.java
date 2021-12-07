// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.metal.*;
import javax.swing.plaf.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.basic.*;
import java.io.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;

public class FlatFileChooserUI extends MetalFileChooserUI
{
    private final FlatFileView fileView;
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatFileChooserUI((JFileChooser)c);
    }
    
    public FlatFileChooserUI(final JFileChooser filechooser) {
        super(filechooser);
        this.fileView = new FlatFileView();
    }
    
    @Override
    public void installComponents(final JFileChooser fc) {
        super.installComponents(fc);
        this.patchUI(fc);
    }
    
    private void patchUI(final JFileChooser fc) {
        final Component topPanel = fc.getComponent(0);
        if (topPanel instanceof JPanel && ((JPanel)topPanel).getLayout() instanceof BorderLayout) {
            final Component topButtonPanel = ((JPanel)topPanel).getComponent(0);
            if (topButtonPanel instanceof JPanel && ((JPanel)topButtonPanel).getLayout() instanceof BoxLayout) {
                final Insets margin = UIManager.getInsets("Button.margin");
                final Component[] comps = ((JPanel)topButtonPanel).getComponents();
                for (int i = comps.length - 1; i >= 0; --i) {
                    final Component c = comps[i];
                    if (c instanceof JButton || c instanceof JToggleButton) {
                        final AbstractButton b = (AbstractButton)c;
                        b.putClientProperty("JButton.buttonType", "toolBarButton");
                        b.setMargin(margin);
                        b.setFocusable(false);
                    }
                    else if (c instanceof Box.Filler) {
                        ((JPanel)topButtonPanel).remove(i);
                    }
                }
            }
        }
        try {
            final Component directoryComboBox = ((JPanel)topPanel).getComponent(2);
            if (directoryComboBox instanceof JComboBox) {
                final int maximumRowCount = UIManager.getInt("ComboBox.maximumRowCount");
                if (maximumRowCount > 0) {
                    ((JComboBox)directoryComboBox).setMaximumRowCount(maximumRowCount);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
    }
    
    @Override
    protected JPanel createDetailsView(final JFileChooser fc) {
        final JPanel p = super.createDetailsView(fc);
        if (!SystemInfo.isWindows) {
            return p;
        }
        JScrollPane scrollPane = null;
        for (final Component c : p.getComponents()) {
            if (c instanceof JScrollPane) {
                scrollPane = (JScrollPane)c;
                break;
            }
        }
        if (scrollPane == null) {
            return p;
        }
        final Component view = scrollPane.getViewport().getView();
        if (!(view instanceof JTable)) {
            return p;
        }
        final JTable table = (JTable)view;
        final TableCellRenderer defaultRenderer = table.getDefaultRenderer(Object.class);
        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(final JTable table, Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
                if (value instanceof String && ((String)value).startsWith("\u200e")) {
                    final String str = (String)value;
                    final char[] buf = new char[str.length()];
                    int j = 0;
                    for (int i = 0; i < buf.length; ++i) {
                        final char ch = str.charAt(i);
                        if (ch != '\u200e' && ch != '\u200f') {
                            buf[j++] = ch;
                        }
                    }
                    value = new String(buf, 0, j);
                }
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        return p;
    }
    
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return UIScale.scale(super.getPreferredSize(c));
    }
    
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return UIScale.scale(super.getMinimumSize(c));
    }
    
    @Override
    public FileView getFileView(final JFileChooser fc) {
        return this.fileView;
    }
    
    @Override
    public void clearIconCache() {
        this.fileView.clearIconCache();
    }
    
    private class FlatFileView extends BasicFileView
    {
        @Override
        public Icon getIcon(final File f) {
            Icon icon = this.getCachedIcon(f);
            if (icon != null) {
                return icon;
            }
            if (f != null) {
                icon = FlatFileChooserUI.this.getFileChooser().getFileSystemView().getSystemIcon(f);
                if (icon != null) {
                    if (icon instanceof ImageIcon) {
                        icon = new ScaledImageIcon((ImageIcon)icon);
                    }
                    this.cacheIcon(f, icon);
                    return icon;
                }
            }
            icon = super.getIcon(f);
            if (icon instanceof ImageIcon) {
                icon = new ScaledImageIcon((ImageIcon)icon);
                this.cacheIcon(f, icon);
            }
            return icon;
        }
    }
}
