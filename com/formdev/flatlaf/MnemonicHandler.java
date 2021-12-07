// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import java.lang.ref.*;
import com.formdev.flatlaf.util.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class MnemonicHandler implements KeyEventPostProcessor, ChangeListener
{
    private static boolean showMnemonics;
    private static WeakReference<Window> lastShowMnemonicWindow;
    private static WindowListener windowListener;
    private static int altPressedEventCount;
    private static boolean selectMenuOnAltReleased;
    
    static boolean isShowMnemonics() {
        return MnemonicHandler.showMnemonics || !UIManager.getBoolean("Component.hideMnemonics");
    }
    
    void install() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
        MenuSelectionManager.defaultManager().addChangeListener(this);
    }
    
    void uninstall() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);
        MenuSelectionManager.defaultManager().removeChangeListener(this);
    }
    
    @Override
    public boolean postProcessKeyEvent(final KeyEvent e) {
        final int keyCode = e.getKeyCode();
        if (SystemInfo.isMacOS) {
            if (keyCode == 17 || keyCode == 18) {
                showMnemonics(this.shouldShowMnemonics(e) && e.isControlDown() && e.isAltDown(), e.getComponent());
            }
        }
        else {
            if (SystemInfo.isWindows) {
                return this.processKeyEventOnWindows(e);
            }
            if (keyCode == 18) {
                showMnemonics(this.shouldShowMnemonics(e), e.getComponent());
            }
        }
        return false;
    }
    
    private boolean shouldShowMnemonics(final KeyEvent e) {
        return e.getID() == 401 || MenuSelectionManager.defaultManager().getSelectedPath().length > 0;
    }
    
    private boolean processKeyEventOnWindows(final KeyEvent e) {
        if (e.getKeyCode() != 18) {
            return MnemonicHandler.selectMenuOnAltReleased = false;
        }
        if (e.getID() == 401) {
            ++MnemonicHandler.altPressedEventCount;
            if (MnemonicHandler.altPressedEventCount == 1 && !e.isConsumed()) {
                final MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
                if (!(MnemonicHandler.selectMenuOnAltReleased = (menuSelectionManager.getSelectedPath().length == 0))) {
                    menuSelectionManager.clearSelectedPath();
                }
            }
            showMnemonics(this.shouldShowMnemonics(e), e.getComponent());
            e.consume();
            return true;
        }
        if (e.getID() == 402) {
            MnemonicHandler.altPressedEventCount = 0;
            boolean mnemonicsShown = false;
            if (MnemonicHandler.selectMenuOnAltReleased && !e.isConsumed()) {
                final MenuSelectionManager menuSelectionManager2 = MenuSelectionManager.defaultManager();
                if (menuSelectionManager2.getSelectedPath().length == 0) {
                    final Component c = e.getComponent();
                    final JRootPane rootPane = SwingUtilities.getRootPane(c);
                    JMenuBar menuBar = (rootPane != null) ? rootPane.getJMenuBar() : null;
                    if (menuBar == null) {
                        final Window window = SwingUtilities.getWindowAncestor(c);
                        if (window instanceof JFrame) {
                            menuBar = ((JFrame)window).getJMenuBar();
                        }
                        else if (window instanceof JDialog) {
                            menuBar = ((JDialog)window).getJMenuBar();
                        }
                    }
                    final JMenu firstMenu = (menuBar != null) ? menuBar.getMenu(0) : null;
                    if (firstMenu != null) {
                        menuSelectionManager2.setSelectedPath(new MenuElement[] { menuBar, firstMenu });
                        showMnemonics(true, c);
                        mnemonicsShown = true;
                    }
                }
            }
            MnemonicHandler.selectMenuOnAltReleased = false;
            if (!mnemonicsShown) {
                showMnemonics(this.shouldShowMnemonics(e), e.getComponent());
            }
        }
        return false;
    }
    
    @Override
    public void stateChanged(final ChangeEvent e) {
        final MenuElement[] selectedPath = MenuSelectionManager.defaultManager().getSelectedPath();
        if (selectedPath.length == 0 && MnemonicHandler.altPressedEventCount == 0) {
            showMnemonics(false, null);
        }
    }
    
    static void showMnemonics(final boolean show, final Component c) {
        if (show == MnemonicHandler.showMnemonics) {
            return;
        }
        MnemonicHandler.showMnemonics = show;
        if (!UIManager.getBoolean("Component.hideMnemonics")) {
            return;
        }
        if (show) {
            final JRootPane rootPane = SwingUtilities.getRootPane(c);
            if (rootPane == null) {
                return;
            }
            final Window window = SwingUtilities.getWindowAncestor(rootPane);
            if (window == null) {
                return;
            }
            repaintMnemonics(window);
            window.addWindowListener(MnemonicHandler.windowListener = new WindowAdapter() {
                @Override
                public void windowDeactivated(final WindowEvent e) {
                    MnemonicHandler.altPressedEventCount = 0;
                    MnemonicHandler.selectMenuOnAltReleased = false;
                    EventQueue.invokeLater(() -> MnemonicHandler.showMnemonics(false, null));
                }
            });
            MnemonicHandler.lastShowMnemonicWindow = new WeakReference<Window>(window);
        }
        else if (MnemonicHandler.lastShowMnemonicWindow != null) {
            final Window window2 = MnemonicHandler.lastShowMnemonicWindow.get();
            if (window2 != null) {
                repaintMnemonics(window2);
                if (MnemonicHandler.windowListener != null) {
                    window2.removeWindowListener(MnemonicHandler.windowListener);
                    MnemonicHandler.windowListener = null;
                }
            }
            MnemonicHandler.lastShowMnemonicWindow = null;
        }
    }
    
    private static void repaintMnemonics(final Container container) {
        for (final Component c : container.getComponents()) {
            if (c.isVisible()) {
                if (hasMnemonic(c)) {
                    c.repaint();
                }
                if (c instanceof Container) {
                    repaintMnemonics((Container)c);
                }
            }
        }
    }
    
    private static boolean hasMnemonic(final Component c) {
        if (c instanceof JLabel && ((JLabel)c).getDisplayedMnemonicIndex() >= 0) {
            return true;
        }
        if (c instanceof AbstractButton && ((AbstractButton)c).getDisplayedMnemonicIndex() >= 0) {
            return true;
        }
        if (c instanceof JTabbedPane) {
            final JTabbedPane tabPane = (JTabbedPane)c;
            for (int tabCount = tabPane.getTabCount(), i = 0; i < tabCount; ++i) {
                if (tabPane.getDisplayedMnemonicIndexAt(i) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
