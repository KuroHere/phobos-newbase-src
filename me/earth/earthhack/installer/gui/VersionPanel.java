// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.gui;

import me.earth.earthhack.installer.*;
import me.earth.earthhack.installer.version.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class VersionPanel extends JPanel
{
    public VersionPanel(final Installer handler, final List<Version> versions) {
        final String[] columns = { "name", "forge", "3arthh4ck", "valid" };
        final List<Object[]> data = new ArrayList<Object[]>(versions.size());
        for (final Version version : versions) {
            final boolean earth = VersionUtil.hasEarthhack(version);
            final boolean forge = VersionUtil.hasForge(version);
            final boolean valid = VersionUtil.hasLaunchWrapper(version);
            data.add(new Object[] { version.getName(), forge, earth, valid });
        }
        final JButton install = new JButton("Install");
        install.setEnabled(false);
        final JButton uninstall = new JButton("Uninstall");
        uninstall.setEnabled(false);
        final JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> handler.refreshVersions());
        final Object[][] t = data.toArray(new Object[0][]);
        final JTable jt = new JTable(t, columns);
        jt.setSelectionMode(0);
        jt.addMouseListener(new VersionMouseAdapter(jt, install, uninstall, t));
        install.addActionListener(e -> {
            final int row = jt.getSelectedRow();
            if (row >= 0) {
                final Version version2 = versions.get(row);
                handler.install(version2);
            }
            return;
        });
        uninstall.addActionListener(e -> {
            final int row2 = jt.getSelectedRow();
            if (row2 >= 0) {
                final Version version3 = versions.get(row2);
                handler.uninstall(version3);
            }
            return;
        });
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(install);
        buttonPanel.add(uninstall);
        buttonPanel.add(refresh);
        final JButton installAll = new JButton("Install-All");
        installAll.addActionListener(e -> {
            versions.iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final Version version4 = iterator2.next();
                if (VersionUtil.hasLaunchWrapper(version4) && !VersionUtil.hasEarthhack(version4) && handler.install(version4)) {
                    return;
                }
            }
            return;
        });
        final JButton uninstallAll = new JButton("Uninstall-All");
        uninstallAll.addActionListener(e -> {
            versions.iterator();
            final Iterator iterator3;
            while (iterator3.hasNext()) {
                final Version version5 = iterator3.next();
                if (VersionUtil.hasEarthhack(version5) && handler.uninstall(version5)) {
                    return;
                }
            }
            return;
        });
        final JButton updateForge = new JButton("Update-Forge");
        updateForge.addActionListener(e -> handler.update(true));
        final JButton updateVanilla = new JButton("Update-Vanilla");
        updateVanilla.addActionListener(e -> handler.update(false));
        final JPanel allPanel = new JPanel();
        allPanel.add(installAll);
        allPanel.add(uninstallAll);
        allPanel.add(updateForge);
        allPanel.add(updateVanilla);
        final JButton exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));
        final JPanel exitPanel = new JPanel();
        exitPanel.add(exit);
        this.setLayout(new BoxLayout(this, 1));
        this.add(new JScrollPane(jt));
        this.add(buttonPanel);
        this.add(allPanel);
        this.add(exitPanel);
    }
}
