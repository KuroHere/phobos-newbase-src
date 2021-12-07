// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.gui;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ErrorPanel extends JPanel
{
    public ErrorPanel(final Throwable throwable) {
        final JTextArea text = new JTextArea();
        text.setEditable(false);
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        text.setText(sw.toString().replace("\t", "   "));
        text.setCaretPosition(0);
        final JScrollPane scroller = new JScrollPane(text);
        final JButton button = new JButton("Close");
        button.addActionListener(e -> System.exit(0));
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        this.setLayout(new BoxLayout(this, 1));
        this.add(scroller);
        this.add(buttonPanel);
    }
}
