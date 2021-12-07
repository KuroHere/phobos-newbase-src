// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.gui;

import javax.swing.*;
import java.awt.event.*;

public class VersionMouseAdapter extends MouseAdapter
{
    private final JTable table;
    private final JButton install;
    private final JButton uninstall;
    private final Object[][] data;
    
    public VersionMouseAdapter(final JTable table, final JButton install, final JButton uninstall, final Object[][] data) {
        this.table = table;
        this.install = install;
        this.uninstall = uninstall;
        this.data = data;
    }
    
    @Override
    public void mouseClicked(final MouseEvent evt) {
        final int row = this.table.rowAtPoint(evt.getPoint());
        if (row >= 0) {
            final Object[] o = this.data[row];
            if (!(boolean)o[3]) {
                this.install.setEnabled(false);
                this.uninstall.setEnabled(false);
            }
            else if (o[2]) {
                this.install.setEnabled(false);
                this.uninstall.setEnabled(true);
            }
            else {
                this.install.setEnabled(true);
                this.uninstall.setEnabled(false);
            }
        }
        else {
            this.install.setEnabled(false);
            this.uninstall.setEnabled(false);
        }
    }
}
