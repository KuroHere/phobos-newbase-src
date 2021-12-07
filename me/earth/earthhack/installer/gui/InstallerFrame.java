// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.gui;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

public class InstallerFrame
{
    private final JFrame frame;
    
    public InstallerFrame() {
        FlatLaf.setup(new FlatDarculaLaf());
        (this.frame = new JFrame("3arthh4ck-Installer")).setDefaultCloseOperation(3);
        final JPanel panel = new JPanel();
        panel.setSize(550, 400);
        panel.setPreferredSize(new Dimension(550, 400));
        panel.setLayout(null);
        this.frame.setSize(550, 400);
        this.frame.setResizable(false);
        this.frame.getContentPane().add(panel);
        this.frame.pack();
    }
    
    public void display() {
        this.frame.setVisible(true);
    }
    
    public void schedule(final JPanel panel) {
        SwingUtilities.invokeLater(() -> this.setPanel(panel));
    }
    
    public void setPanel(final JPanel panel) {
        this.frame.setContentPane(panel);
        this.frame.invalidate();
        this.frame.validate();
    }
}
