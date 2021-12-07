// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import java.util.*;
import com.formdev.flatlaf.*;
import java.awt.geom.*;
import java.util.function.*;
import javax.swing.plaf.*;
import java.awt.*;
import javax.swing.text.*;
import javax.accessibility.*;
import com.formdev.flatlaf.util.*;
import java.beans.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.*;

public class FlatTabbedPaneUI extends BasicTabbedPaneUI
{
    protected static final int NEVER = 0;
    protected static final int AS_NEEDED = 2;
    protected static final int AS_NEEDED_SINGLE = 3;
    protected static final int BOTH = 100;
    protected static final int FILL = 100;
    protected static final int WIDTH_MODE_PREFERRED = 0;
    protected static final int WIDTH_MODE_EQUAL = 1;
    protected static final int WIDTH_MODE_COMPACT = 2;
    private static Set<KeyStroke> focusForwardTraversalKeys;
    private static Set<KeyStroke> focusBackwardTraversalKeys;
    protected Color foreground;
    protected Color disabledForeground;
    protected Color selectedBackground;
    protected Color selectedForeground;
    protected Color underlineColor;
    protected Color disabledUnderlineColor;
    protected Color hoverColor;
    protected Color focusColor;
    protected Color tabSeparatorColor;
    protected Color contentAreaColor;
    private int textIconGapUnscaled;
    protected int minimumTabWidth;
    protected int maximumTabWidth;
    protected int tabHeight;
    protected int tabSelectionHeight;
    protected int contentSeparatorHeight;
    protected boolean showTabSeparators;
    protected boolean tabSeparatorsFullHeight;
    protected boolean hasFullBorder;
    protected boolean tabsOpaque;
    private int tabsPopupPolicy;
    private int scrollButtonsPolicy;
    private int scrollButtonsPlacement;
    private int tabAreaAlignment;
    private int tabAlignment;
    private int tabWidthMode;
    protected Icon closeIcon;
    protected String arrowType;
    protected Insets buttonInsets;
    protected int buttonArc;
    protected Color buttonHoverBackground;
    protected Color buttonPressedBackground;
    protected String moreTabsButtonToolTipText;
    protected JViewport tabViewport;
    protected FlatWheelTabScroller wheelTabScroller;
    private JButton tabCloseButton;
    private JButton moreTabsButton;
    private Container leadingComponent;
    private Container trailingComponent;
    private Dimension scrollBackwardButtonPrefSize;
    private Handler handler;
    private boolean blockRollover;
    private boolean rolloverTabClose;
    private boolean pressedTabClose;
    private Object[] oldRenderingHints;
    private boolean inCalculateEqual;
    
    public FlatTabbedPaneUI() {
        this.tabsOpaque = true;
    }
    
    public static ComponentUI createUI(final JComponent c) {
        return new FlatTabbedPaneUI();
    }
    
    @Override
    public void installUI(final JComponent c) {
        final String tabLayoutPolicyStr = UIManager.getString("TabbedPane.tabLayoutPolicy");
        if (tabLayoutPolicyStr != null) {
            final String s = tabLayoutPolicyStr;
            int tabLayoutPolicy = 0;
            switch (s) {
                default: {
                    tabLayoutPolicy = 0;
                    break;
                }
                case "scroll": {
                    tabLayoutPolicy = 1;
                    break;
                }
            }
            ((JTabbedPane)c).setTabLayoutPolicy(tabLayoutPolicy);
        }
        this.arrowType = UIManager.getString("TabbedPane.arrowType");
        this.foreground = UIManager.getColor("TabbedPane.foreground");
        this.disabledForeground = UIManager.getColor("TabbedPane.disabledForeground");
        this.buttonHoverBackground = UIManager.getColor("TabbedPane.buttonHoverBackground");
        this.buttonPressedBackground = UIManager.getColor("TabbedPane.buttonPressedBackground");
        super.installUI(c);
    }
    
    @Override
    protected void installDefaults() {
        if (UIManager.getBoolean("TabbedPane.tabsOverlapBorder")) {
            final Object oldValue = UIManager.put("TabbedPane.tabsOverlapBorder", false);
            super.installDefaults();
            UIManager.put("TabbedPane.tabsOverlapBorder", oldValue);
        }
        else {
            super.installDefaults();
        }
        this.selectedBackground = UIManager.getColor("TabbedPane.selectedBackground");
        this.selectedForeground = UIManager.getColor("TabbedPane.selectedForeground");
        this.underlineColor = UIManager.getColor("TabbedPane.underlineColor");
        this.disabledUnderlineColor = UIManager.getColor("TabbedPane.disabledUnderlineColor");
        this.hoverColor = UIManager.getColor("TabbedPane.hoverColor");
        this.focusColor = UIManager.getColor("TabbedPane.focusColor");
        this.tabSeparatorColor = UIManager.getColor("TabbedPane.tabSeparatorColor");
        this.contentAreaColor = UIManager.getColor("TabbedPane.contentAreaColor");
        this.textIconGapUnscaled = UIManager.getInt("TabbedPane.textIconGap");
        this.minimumTabWidth = UIManager.getInt("TabbedPane.minimumTabWidth");
        this.maximumTabWidth = UIManager.getInt("TabbedPane.maximumTabWidth");
        this.tabHeight = UIManager.getInt("TabbedPane.tabHeight");
        this.tabSelectionHeight = UIManager.getInt("TabbedPane.tabSelectionHeight");
        this.contentSeparatorHeight = UIManager.getInt("TabbedPane.contentSeparatorHeight");
        this.showTabSeparators = UIManager.getBoolean("TabbedPane.showTabSeparators");
        this.tabSeparatorsFullHeight = UIManager.getBoolean("TabbedPane.tabSeparatorsFullHeight");
        this.hasFullBorder = UIManager.getBoolean("TabbedPane.hasFullBorder");
        this.tabsOpaque = UIManager.getBoolean("TabbedPane.tabsOpaque");
        this.tabsPopupPolicy = parseTabsPopupPolicy(UIManager.getString("TabbedPane.tabsPopupPolicy"));
        this.scrollButtonsPolicy = parseScrollButtonsPolicy(UIManager.getString("TabbedPane.scrollButtonsPolicy"));
        this.scrollButtonsPlacement = parseScrollButtonsPlacement(UIManager.getString("TabbedPane.scrollButtonsPlacement"));
        this.tabAreaAlignment = parseAlignment(UIManager.getString("TabbedPane.tabAreaAlignment"), 10);
        this.tabAlignment = parseAlignment(UIManager.getString("TabbedPane.tabAlignment"), 0);
        this.tabWidthMode = parseTabWidthMode(UIManager.getString("TabbedPane.tabWidthMode"));
        this.closeIcon = UIManager.getIcon("TabbedPane.closeIcon");
        this.buttonInsets = UIManager.getInsets("TabbedPane.buttonInsets");
        this.buttonArc = UIManager.getInt("TabbedPane.buttonArc");
        final Locale l = this.tabPane.getLocale();
        this.moreTabsButtonToolTipText = UIManager.getString("TabbedPane.moreTabsButtonToolTipText", l);
        this.textIconGap = UIScale.scale(this.textIconGapUnscaled);
        if (FlatTabbedPaneUI.focusForwardTraversalKeys == null) {
            FlatTabbedPaneUI.focusForwardTraversalKeys = Collections.singleton(KeyStroke.getKeyStroke(9, 0));
            FlatTabbedPaneUI.focusBackwardTraversalKeys = Collections.singleton(KeyStroke.getKeyStroke(9, 64));
        }
        this.tabPane.setFocusTraversalKeys(0, FlatTabbedPaneUI.focusForwardTraversalKeys);
        this.tabPane.setFocusTraversalKeys(1, FlatTabbedPaneUI.focusBackwardTraversalKeys);
        MigLayoutVisualPadding.install(this.tabPane, null);
    }
    
    @Override
    protected void uninstallDefaults() {
        this.tabPane.setFocusTraversalKeys(0, null);
        this.tabPane.setFocusTraversalKeys(1, null);
        super.uninstallDefaults();
        this.foreground = null;
        this.disabledForeground = null;
        this.selectedBackground = null;
        this.selectedForeground = null;
        this.underlineColor = null;
        this.disabledUnderlineColor = null;
        this.hoverColor = null;
        this.focusColor = null;
        this.tabSeparatorColor = null;
        this.contentAreaColor = null;
        this.closeIcon = null;
        this.buttonHoverBackground = null;
        this.buttonPressedBackground = null;
        MigLayoutVisualPadding.uninstall(this.tabPane);
    }
    
    @Override
    protected void installComponents() {
        super.installComponents();
        this.tabViewport = null;
        if (this.isScrollTabLayout()) {
            for (final Component c : this.tabPane.getComponents()) {
                if (c instanceof JViewport && c.getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$ScrollableTabViewport")) {
                    this.tabViewport = (JViewport)c;
                    break;
                }
            }
        }
        this.installHiddenTabsNavigation();
        this.installLeadingComponent();
        this.installTrailingComponent();
    }
    
    @Override
    protected void uninstallComponents() {
        this.uninstallHiddenTabsNavigation();
        this.uninstallLeadingComponent();
        this.uninstallTrailingComponent();
        super.uninstallComponents();
        this.tabCloseButton = null;
        this.tabViewport = null;
    }
    
    protected void installHiddenTabsNavigation() {
        if (!this.isScrollTabLayout() || this.tabViewport == null) {
            return;
        }
        this.tabPane.setLayout(this.createScrollLayoutManager((TabbedPaneLayout)this.tabPane.getLayout()));
        this.moreTabsButton = this.createMoreTabsButton();
        this.tabPane.add(this.moreTabsButton);
    }
    
    protected void uninstallHiddenTabsNavigation() {
        if (this.tabPane.getLayout() instanceof FlatTabbedPaneScrollLayout) {
            this.tabPane.setLayout(((FlatTabbedPaneScrollLayout)this.tabPane.getLayout()).delegate);
        }
        if (this.moreTabsButton != null) {
            this.tabPane.remove(this.moreTabsButton);
            this.moreTabsButton = null;
        }
    }
    
    protected void installLeadingComponent() {
        final Object c = this.tabPane.getClientProperty("JTabbedPane.leadingComponent");
        if (c instanceof Component) {
            this.leadingComponent = new ContainerUIResource((Component)c);
            this.tabPane.add(this.leadingComponent);
        }
    }
    
    protected void uninstallLeadingComponent() {
        if (this.leadingComponent != null) {
            this.tabPane.remove(this.leadingComponent);
            this.leadingComponent = null;
        }
    }
    
    protected void installTrailingComponent() {
        final Object c = this.tabPane.getClientProperty("JTabbedPane.trailingComponent");
        if (c instanceof Component) {
            this.trailingComponent = new ContainerUIResource((Component)c);
            this.tabPane.add(this.trailingComponent);
        }
    }
    
    protected void uninstallTrailingComponent() {
        if (this.trailingComponent != null) {
            this.tabPane.remove(this.trailingComponent);
            this.trailingComponent = null;
        }
    }
    
    @Override
    protected void installListeners() {
        super.installListeners();
        this.getHandler().installListeners();
        if (this.tabViewport != null && (this.wheelTabScroller = this.createWheelTabScroller()) != null) {
            this.tabPane.addMouseWheelListener(this.wheelTabScroller);
            this.tabPane.addMouseMotionListener(this.wheelTabScroller);
            this.tabPane.addMouseListener(this.wheelTabScroller);
        }
    }
    
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        if (this.handler != null) {
            this.handler.uninstallListeners();
            this.handler = null;
        }
        if (this.wheelTabScroller != null) {
            this.wheelTabScroller.uninstall();
            this.tabPane.removeMouseWheelListener(this.wheelTabScroller);
            this.tabPane.removeMouseMotionListener(this.wheelTabScroller);
            this.tabPane.removeMouseListener(this.wheelTabScroller);
            this.wheelTabScroller = null;
        }
    }
    
    @Override
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        final ActionMap map = SwingUtilities.getUIActionMap(this.tabPane);
        if (map != null) {
            RunWithOriginalLayoutManagerDelegateAction.install(map, "scrollTabsForwardAction");
            RunWithOriginalLayoutManagerDelegateAction.install(map, "scrollTabsBackwardAction");
        }
    }
    
    private Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler();
        }
        return this.handler;
    }
    
    protected FlatWheelTabScroller createWheelTabScroller() {
        return new FlatWheelTabScroller();
    }
    
    @Override
    protected MouseListener createMouseListener() {
        final Handler handler = this.getHandler();
        handler.mouseDelegate = super.createMouseListener();
        return handler;
    }
    
    @Override
    protected PropertyChangeListener createPropertyChangeListener() {
        final Handler handler = this.getHandler();
        handler.propertyChangeDelegate = super.createPropertyChangeListener();
        return handler;
    }
    
    @Override
    protected ChangeListener createChangeListener() {
        final Handler handler = this.getHandler();
        handler.changeDelegate = super.createChangeListener();
        return handler;
    }
    
    @Override
    protected LayoutManager createLayoutManager() {
        if (this.tabPane.getTabLayoutPolicy() == 0) {
            return new FlatTabbedPaneLayout();
        }
        return super.createLayoutManager();
    }
    
    protected LayoutManager createScrollLayoutManager(final TabbedPaneLayout delegate) {
        return new FlatTabbedPaneScrollLayout(delegate);
    }
    
    protected JButton createMoreTabsButton() {
        return new FlatMoreTabsButton();
    }
    
    @Override
    protected JButton createScrollButton(final int direction) {
        return new FlatScrollableTabButton(direction);
    }
    
    protected void setRolloverTab(final int x, final int y) {
        this.setRolloverTab(this.tabForCoordinate(this.tabPane, x, y));
    }
    
    @Override
    protected void setRolloverTab(final int index) {
        if (this.blockRollover) {
            return;
        }
        final int oldIndex = this.getRolloverTab();
        super.setRolloverTab(index);
        if (index == oldIndex) {
            return;
        }
        this.repaintTab(oldIndex);
        this.repaintTab(index);
    }
    
    protected boolean isRolloverTabClose() {
        return this.rolloverTabClose;
    }
    
    protected void setRolloverTabClose(final boolean rollover) {
        if (this.rolloverTabClose == rollover) {
            return;
        }
        this.rolloverTabClose = rollover;
        this.repaintTab(this.getRolloverTab());
    }
    
    protected boolean isPressedTabClose() {
        return this.pressedTabClose;
    }
    
    protected void setPressedTabClose(final boolean pressed) {
        if (this.pressedTabClose == pressed) {
            return;
        }
        this.pressedTabClose = pressed;
        this.repaintTab(this.getRolloverTab());
    }
    
    private void repaintTab(final int tabIndex) {
        if (tabIndex < 0 || tabIndex >= this.tabPane.getTabCount()) {
            return;
        }
        final Rectangle r = this.getTabBounds(this.tabPane, tabIndex);
        if (r != null) {
            this.tabPane.repaint(r);
        }
    }
    
    @Override
    protected int calculateTabWidth(final int tabPlacement, final int tabIndex, final FontMetrics metrics) {
        final int tabWidthMode = this.getTabWidthMode();
        if (tabWidthMode == 1 && this.isHorizontalTabPlacement() && !this.inCalculateEqual) {
            this.inCalculateEqual = true;
            try {
                return this.calculateMaxTabWidth(tabPlacement);
            }
            finally {
                this.inCalculateEqual = false;
            }
        }
        this.textIconGap = UIScale.scale(this.textIconGapUnscaled);
        Icon icon;
        int tabWidth;
        if (tabWidthMode == 2 && tabIndex != this.tabPane.getSelectedIndex() && this.isHorizontalTabPlacement() && this.tabPane.getTabComponentAt(tabIndex) == null && (icon = this.getIconForTab(tabIndex)) != null) {
            final Insets tabInsets = this.getTabInsets(tabPlacement, tabIndex);
            tabWidth = icon.getIconWidth() + tabInsets.left + tabInsets.right;
        }
        else {
            final int iconPlacement = FlatClientProperties.clientPropertyInt(this.tabPane, "JTabbedPane.tabIconPlacement", 10);
            if ((iconPlacement == 1 || iconPlacement == 3) && this.tabPane.getTabComponentAt(tabIndex) == null && (icon = this.getIconForTab(tabIndex)) != null) {
                tabWidth = icon.getIconWidth();
                final View view = this.getTextViewForTab(tabIndex);
                if (view != null) {
                    tabWidth = Math.max(tabWidth, (int)view.getPreferredSpan(0));
                }
                else {
                    final String title = this.tabPane.getTitleAt(tabIndex);
                    if (title != null) {
                        tabWidth = Math.max(tabWidth, metrics.stringWidth(title));
                    }
                }
                final Insets tabInsets2 = this.getTabInsets(tabPlacement, tabIndex);
                tabWidth += tabInsets2.left + tabInsets2.right;
            }
            else {
                tabWidth = super.calculateTabWidth(tabPlacement, tabIndex, metrics) - 3;
            }
        }
        if (this.isTabClosable(tabIndex)) {
            tabWidth += this.closeIcon.getIconWidth();
        }
        final int min = this.getTabClientPropertyInt(tabIndex, "JTabbedPane.minimumTabWidth", this.minimumTabWidth);
        final int max = this.getTabClientPropertyInt(tabIndex, "JTabbedPane.maximumTabWidth", this.maximumTabWidth);
        if (min > 0) {
            tabWidth = Math.max(tabWidth, UIScale.scale(min));
        }
        if (max > 0 && this.tabPane.getTabComponentAt(tabIndex) == null) {
            tabWidth = Math.min(tabWidth, UIScale.scale(max));
        }
        return tabWidth;
    }
    
    @Override
    protected int calculateTabHeight(final int tabPlacement, final int tabIndex, final int fontHeight) {
        final int iconPlacement = FlatClientProperties.clientPropertyInt(this.tabPane, "JTabbedPane.tabIconPlacement", 10);
        final Icon icon;
        int tabHeight;
        if ((iconPlacement == 1 || iconPlacement == 3) && this.tabPane.getTabComponentAt(tabIndex) == null && (icon = this.getIconForTab(tabIndex)) != null) {
            tabHeight = icon.getIconHeight();
            final View view = this.getTextViewForTab(tabIndex);
            if (view != null) {
                tabHeight += (int)view.getPreferredSpan(1) + UIScale.scale(this.textIconGapUnscaled);
            }
            else if (this.tabPane.getTitleAt(tabIndex) != null) {
                tabHeight += fontHeight + UIScale.scale(this.textIconGapUnscaled);
            }
            final Insets tabInsets = this.getTabInsets(tabPlacement, tabIndex);
            tabHeight += tabInsets.top + tabInsets.bottom;
        }
        else {
            tabHeight = super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) - 2;
        }
        return Math.max(tabHeight, UIScale.scale(FlatClientProperties.clientPropertyInt(this.tabPane, "JTabbedPane.tabHeight", this.tabHeight)));
    }
    
    @Override
    protected int calculateMaxTabWidth(final int tabPlacement) {
        return this.hideTabArea() ? 0 : super.calculateMaxTabWidth(tabPlacement);
    }
    
    @Override
    protected int calculateMaxTabHeight(final int tabPlacement) {
        return this.hideTabArea() ? 0 : super.calculateMaxTabHeight(tabPlacement);
    }
    
    @Override
    protected int calculateTabAreaWidth(final int tabPlacement, final int vertRunCount, final int maxTabWidth) {
        return this.hideTabArea() ? 0 : super.calculateTabAreaWidth(tabPlacement, vertRunCount, maxTabWidth);
    }
    
    @Override
    protected int calculateTabAreaHeight(final int tabPlacement, final int horizRunCount, final int maxTabHeight) {
        return this.hideTabArea() ? 0 : super.calculateTabAreaHeight(tabPlacement, horizRunCount, maxTabHeight);
    }
    
    @Override
    protected Insets getTabInsets(final int tabPlacement, final int tabIndex) {
        final Object value = this.getTabClientProperty(tabIndex, "JTabbedPane.tabInsets");
        return UIScale.scale((value instanceof Insets) ? ((Insets)value) : super.getTabInsets(tabPlacement, tabIndex));
    }
    
    @Override
    protected Insets getSelectedTabPadInsets(final int tabPlacement) {
        return new Insets(0, 0, 0, 0);
    }
    
    protected Insets getRealTabAreaInsets(final int tabPlacement) {
        if (this.tabAreaInsets == null) {
            this.tabAreaInsets = new Insets(0, 0, 0, 0);
        }
        final Insets currentTabAreaInsets = super.getTabAreaInsets(tabPlacement);
        Insets insets = (Insets)currentTabAreaInsets.clone();
        final Object value = this.tabPane.getClientProperty("JTabbedPane.tabAreaInsets");
        if (value instanceof Insets) {
            BasicTabbedPaneUI.rotateInsets((Insets)value, insets, tabPlacement);
        }
        final Insets insets2 = currentTabAreaInsets;
        final Insets insets3 = currentTabAreaInsets;
        final int n = -10000;
        insets3.left = n;
        insets2.top = n;
        insets = UIScale.scale(insets);
        return insets;
    }
    
    @Override
    protected Insets getTabAreaInsets(final int tabPlacement) {
        final Insets insets = this.getRealTabAreaInsets(tabPlacement);
        if (this.tabPane.getTabLayoutPolicy() == 0) {
            if (this.isHorizontalTabPlacement()) {
                final Insets insets2 = insets;
                insets2.left += this.getLeadingPreferredWidth();
                final Insets insets3 = insets;
                insets3.right += this.getTrailingPreferredWidth();
            }
            else {
                final Insets insets4 = insets;
                insets4.top += this.getLeadingPreferredHeight();
                final Insets insets5 = insets;
                insets5.bottom += this.getTrailingPreferredHeight();
            }
        }
        return insets;
    }
    
    @Override
    protected Insets getContentBorderInsets(final int tabPlacement) {
        if (this.hideTabArea() || this.contentSeparatorHeight == 0 || !FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.showContentSeparator", true)) {
            return new Insets(0, 0, 0, 0);
        }
        final boolean hasFullBorder = FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.hasFullBorder", this.hasFullBorder);
        final int sh = UIScale.scale(this.contentSeparatorHeight);
        final Insets insets = hasFullBorder ? new Insets(sh, sh, sh, sh) : new Insets(sh, 0, 0, 0);
        final Insets contentBorderInsets = new Insets(0, 0, 0, 0);
        BasicTabbedPaneUI.rotateInsets(insets, contentBorderInsets, tabPlacement);
        return contentBorderInsets;
    }
    
    @Override
    protected int getTabLabelShiftX(final int tabPlacement, final int tabIndex, final boolean isSelected) {
        if (this.isTabClosable(tabIndex)) {
            final int shift = this.closeIcon.getIconWidth() / 2;
            return this.isLeftToRight() ? (-shift) : shift;
        }
        return 0;
    }
    
    @Override
    protected int getTabLabelShiftY(final int tabPlacement, final int tabIndex, final boolean isSelected) {
        return 0;
    }
    
    @Override
    public void update(final Graphics g, final JComponent c) {
        this.oldRenderingHints = FlatUIUtils.setRenderingHints(g);
        super.update(g, c);
        FlatUIUtils.resetRenderingHints(g, this.oldRenderingHints);
        this.oldRenderingHints = null;
    }
    
    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (this.hideTabArea()) {
            return;
        }
        this.ensureCurrentLayout();
        final int tabPlacement = this.tabPane.getTabPlacement();
        final int selectedIndex = this.tabPane.getSelectedIndex();
        this.paintContentBorder(g, tabPlacement, selectedIndex);
        if (!this.isScrollTabLayout()) {
            this.paintTabArea(g, tabPlacement, selectedIndex);
        }
    }
    
    @Override
    protected void paintTabArea(final Graphics g, final int tabPlacement, final int selectedIndex) {
        final Object[] oldHints = FlatUIUtils.setRenderingHints(g);
        super.paintTabArea(g, tabPlacement, selectedIndex);
        FlatUIUtils.resetRenderingHints(g, oldHints);
    }
    
    @Override
    protected void paintTab(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect, final Rectangle textRect) {
        final Rectangle tabRect = rects[tabIndex];
        final int x = tabRect.x;
        final int y = tabRect.y;
        final int w = tabRect.width;
        final int h = tabRect.height;
        final boolean isSelected = tabIndex == this.tabPane.getSelectedIndex();
        if (this.tabsOpaque || this.tabPane.isOpaque()) {
            this.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
        }
        this.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
        if (this.isTabClosable(tabIndex)) {
            this.paintTabCloseButton(g, tabIndex, x, y, w, h);
        }
        if (isSelected) {
            this.paintTabSelection(g, tabPlacement, x, y, w, h);
        }
        if (this.tabPane.getTabComponentAt(tabIndex) != null) {
            return;
        }
        String title = this.tabPane.getTitleAt(tabIndex);
        final Icon icon = this.getIconForTab(tabIndex);
        final Font font = this.tabPane.getFont();
        final FontMetrics metrics = this.tabPane.getFontMetrics(font);
        final boolean isCompact = icon != null && !isSelected && this.getTabWidthMode() == 2 && this.isHorizontalTabPlacement();
        if (isCompact) {
            title = null;
        }
        String clippedTitle = this.layoutAndClipLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
        if (this.tabViewport != null && (tabPlacement == 1 || tabPlacement == 3)) {
            final Rectangle viewRect2;
            final Rectangle viewRect = viewRect2 = this.tabViewport.getViewRect();
            viewRect2.width -= 4;
            if (!viewRect.contains(textRect)) {
                final Rectangle r = viewRect.intersection(textRect);
                if (r.x > viewRect.x) {
                    clippedTitle = JavaCompatibility.getClippedString(null, metrics, title, r.width);
                }
            }
        }
        if (!isCompact) {
            this.paintText(g, tabPlacement, font, metrics, tabIndex, clippedTitle, textRect, isSelected);
        }
        this.paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
    }
    
    @Override
    protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title, final Rectangle textRect, final boolean isSelected) {
        g.setFont(font);
        FlatUIUtils.runWithoutRenderingHints(g, this.oldRenderingHints, () -> {
            final View view = this.getTextViewForTab(tabIndex);
            if (view != null) {
                view.paint(g, textRect);
            }
            else {
                Color color;
                if (this.tabPane.isEnabled() && this.tabPane.isEnabledAt(tabIndex)) {
                    color = this.tabPane.getForegroundAt(tabIndex);
                    if (isSelected && this.selectedForeground != null && color == this.tabPane.getForeground()) {
                        color = this.selectedForeground;
                    }
                }
                else {
                    color = this.disabledForeground;
                }
                final int mnemIndex = FlatLaf.isShowMnemonics() ? this.tabPane.getDisplayedMnemonicIndexAt(tabIndex) : -1;
                g.setColor(color);
                FlatUIUtils.drawStringUnderlineCharAt(this.tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
            }
        });
    }
    
    @Override
    protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h, final boolean isSelected) {
        final boolean enabled = this.tabPane.isEnabled();
        final Color background = (enabled && this.tabPane.isEnabledAt(tabIndex) && this.getRolloverTab() == tabIndex) ? this.hoverColor : ((enabled && isSelected && FlatUIUtils.isPermanentFocusOwner(this.tabPane)) ? this.focusColor : ((this.selectedBackground != null && enabled && isSelected) ? this.selectedBackground : this.tabPane.getBackgroundAt(tabIndex)));
        g.setColor(FlatUIUtils.deriveColor(background, this.tabPane.getBackground()));
        g.fillRect(x, y, w, h);
    }
    
    @Override
    protected void paintTabBorder(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h, final boolean isSelected) {
        if (FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.showTabSeparators", this.showTabSeparators) && !this.isLastInRun(tabIndex)) {
            this.paintTabSeparator(g, tabPlacement, x, y, w, h);
        }
    }
    
    protected void paintTabCloseButton(final Graphics g, final int tabIndex, final int x, final int y, final int w, final int h) {
        if (this.tabCloseButton == null) {
            (this.tabCloseButton = new TabCloseButton()).setVisible(false);
        }
        final boolean rollover = tabIndex == this.getRolloverTab();
        final ButtonModel bm = this.tabCloseButton.getModel();
        bm.setRollover(rollover && this.isRolloverTabClose());
        bm.setPressed(rollover && this.isPressedTabClose());
        this.tabCloseButton.setBackground(this.tabPane.getBackground());
        this.tabCloseButton.setForeground(this.tabPane.getForeground());
        final Rectangle tabCloseRect = this.getTabCloseBounds(tabIndex, x, y, w, h, this.calcRect);
        this.closeIcon.paintIcon(this.tabCloseButton, g, tabCloseRect.x, tabCloseRect.y);
    }
    
    protected void paintTabSeparator(final Graphics g, final int tabPlacement, final int x, final int y, final int w, final int h) {
        final float sepWidth = UIScale.scale(1.0f);
        final float offset = this.tabSeparatorsFullHeight ? 0.0f : UIScale.scale(5.0f);
        g.setColor((this.tabSeparatorColor != null) ? this.tabSeparatorColor : this.contentAreaColor);
        if (tabPlacement == 2 || tabPlacement == 4) {
            ((Graphics2D)g).fill(new Rectangle2D.Float(x + offset, y + h - sepWidth, w - offset * 2.0f, sepWidth));
        }
        else if (this.isLeftToRight()) {
            ((Graphics2D)g).fill(new Rectangle2D.Float(x + w - sepWidth, y + offset, sepWidth, h - offset * 2.0f));
        }
        else {
            ((Graphics2D)g).fill(new Rectangle2D.Float((float)x, y + offset, sepWidth, h - offset * 2.0f));
        }
    }
    
    protected void paintTabSelection(final Graphics g, final int tabPlacement, final int x, final int y, final int w, final int h) {
        g.setColor(this.tabPane.isEnabled() ? this.underlineColor : this.disabledUnderlineColor);
        final Insets contentInsets = this.getContentBorderInsets(tabPlacement);
        final int tabSelectionHeight = UIScale.scale(this.tabSelectionHeight);
        switch (tabPlacement) {
            default: {
                final int sy = y + h + contentInsets.top - tabSelectionHeight;
                g.fillRect(x, sy, w, tabSelectionHeight);
                break;
            }
            case 3: {
                g.fillRect(x, y - contentInsets.bottom, w, tabSelectionHeight);
                break;
            }
            case 2: {
                final int sx = x + w + contentInsets.left - tabSelectionHeight;
                g.fillRect(sx, y, tabSelectionHeight, h);
                break;
            }
            case 4: {
                g.fillRect(x - contentInsets.right, y, tabSelectionHeight, h);
                break;
            }
        }
    }
    
    @Override
    protected void paintContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex) {
        if (this.tabPane.getTabCount() <= 0 || this.contentSeparatorHeight == 0 || !FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.showContentSeparator", true)) {
            return;
        }
        final Insets insets = this.tabPane.getInsets();
        final Insets tabAreaInsets = this.getTabAreaInsets(tabPlacement);
        int x = insets.left;
        int y = insets.top;
        int w = this.tabPane.getWidth() - insets.right - insets.left;
        int h = this.tabPane.getHeight() - insets.top - insets.bottom;
        switch (tabPlacement) {
            default: {
                y += this.calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
                y -= tabAreaInsets.bottom;
                h -= y - insets.top;
                break;
            }
            case 3: {
                h -= this.calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
                h += tabAreaInsets.top;
                break;
            }
            case 2: {
                x += this.calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
                x -= tabAreaInsets.right;
                w -= x - insets.left;
                break;
            }
            case 4: {
                w -= this.calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
                w += tabAreaInsets.left;
                break;
            }
        }
        final boolean hasFullBorder = FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.hasFullBorder", this.hasFullBorder);
        final int sh = UIScale.scale(this.contentSeparatorHeight * 100);
        final Insets ci = new Insets(0, 0, 0, 0);
        BasicTabbedPaneUI.rotateInsets(hasFullBorder ? new Insets(sh, sh, sh, sh) : new Insets(sh, 0, 0, 0), ci, tabPlacement);
        g.setColor(this.contentAreaColor);
        final Path2D path = new Path2D.Float(0);
        path.append(new Rectangle2D.Float((float)x, (float)y, (float)w, (float)h), false);
        path.append(new Rectangle2D.Float(x + ci.left / 100.0f, y + ci.top / 100.0f, w - ci.left / 100.0f - ci.right / 100.0f, h - ci.top / 100.0f - ci.bottom / 100.0f), false);
        ((Graphics2D)g).fill(path);
        if (this.isScrollTabLayout() && selectedIndex >= 0 && this.tabViewport != null) {
            final Rectangle tabRect = this.getTabBounds(this.tabPane, selectedIndex);
            final Shape oldClip = g.getClip();
            final Rectangle vr = this.tabViewport.getBounds();
            if (this.isHorizontalTabPlacement()) {
                g.clipRect(vr.x, 0, vr.width, this.tabPane.getHeight());
            }
            else {
                g.clipRect(0, vr.y, this.tabPane.getWidth(), vr.height);
            }
            this.paintTabSelection(g, tabPlacement, tabRect.x, tabRect.y, tabRect.width, tabRect.height);
            g.setClip(oldClip);
        }
    }
    
    @Override
    protected void paintFocusIndicator(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
    }
    
    protected String layoutAndClipLabel(final int tabPlacement, final FontMetrics metrics, final int tabIndex, final String title, final Icon icon, Rectangle tabRect, final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
        tabRect = FlatUIUtils.subtractInsets(tabRect, this.getTabInsets(tabPlacement, tabIndex));
        if (this.isTabClosable(tabIndex)) {
            final Rectangle rectangle = tabRect;
            rectangle.width -= this.closeIcon.getIconWidth();
            if (!this.isLeftToRight()) {
                final Rectangle rectangle2 = tabRect;
                rectangle2.x += this.closeIcon.getIconWidth();
            }
        }
        int verticalTextPosition = 0;
        int horizontalTextPosition = 0;
        switch (FlatClientProperties.clientPropertyInt(this.tabPane, "JTabbedPane.tabIconPlacement", 10)) {
            default: {
                verticalTextPosition = 0;
                horizontalTextPosition = 11;
                break;
            }
            case 11: {
                verticalTextPosition = 0;
                horizontalTextPosition = 10;
                break;
            }
            case 1: {
                verticalTextPosition = 3;
                horizontalTextPosition = 0;
                break;
            }
            case 3: {
                verticalTextPosition = 1;
                horizontalTextPosition = 0;
                break;
            }
        }
        textRect.setBounds(0, 0, 0, 0);
        iconRect.setBounds(0, 0, 0, 0);
        final View view = this.getTextViewForTab(tabIndex);
        if (view != null) {
            this.tabPane.putClientProperty("html", view);
        }
        final String clippedTitle = SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, this.getTabAlignment(tabIndex), verticalTextPosition, horizontalTextPosition, tabRect, iconRect, textRect, UIScale.scale(this.textIconGapUnscaled));
        this.tabPane.putClientProperty("html", null);
        return clippedTitle;
    }
    
    @Override
    public int tabForCoordinate(final JTabbedPane pane, int x, int y) {
        if (this.moreTabsButton != null) {
            final Point viewPosition = this.tabViewport.getViewPosition();
            x = x - this.tabViewport.getX() + viewPosition.x;
            y = y - this.tabViewport.getY() + viewPosition.y;
            if (!this.tabViewport.getViewRect().contains(x, y)) {
                return -1;
            }
        }
        return super.tabForCoordinate(pane, x, y);
    }
    
    @Override
    protected Rectangle getTabBounds(final int tabIndex, final Rectangle dest) {
        if (this.moreTabsButton != null) {
            dest.setBounds(this.rects[tabIndex]);
            final Point viewPosition = this.tabViewport.getViewPosition();
            dest.x = dest.x + this.tabViewport.getX() - viewPosition.x;
            dest.y = dest.y + this.tabViewport.getY() - viewPosition.y;
            return dest;
        }
        return super.getTabBounds(tabIndex, dest);
    }
    
    protected Rectangle getTabCloseBounds(final int tabIndex, final int x, final int y, final int w, final int h, final Rectangle dest) {
        final int iconWidth = this.closeIcon.getIconWidth();
        final int iconHeight = this.closeIcon.getIconHeight();
        final Insets tabInsets = this.getTabInsets(this.tabPane.getTabPlacement(), tabIndex);
        dest.x = (this.isLeftToRight() ? (x + w - tabInsets.right / 3 * 2 - iconWidth) : (x + tabInsets.left / 3 * 2));
        dest.y = y + (h - iconHeight) / 2;
        dest.width = iconWidth;
        dest.height = iconHeight;
        return dest;
    }
    
    protected Rectangle getTabCloseHitArea(final int tabIndex) {
        final Rectangle tabRect = this.getTabBounds(this.tabPane, tabIndex);
        final Rectangle tabCloseRect = this.getTabCloseBounds(tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, this.calcRect);
        return new Rectangle(tabCloseRect.x, tabRect.y, tabCloseRect.width, tabRect.height);
    }
    
    protected boolean isTabClosable(final int tabIndex) {
        final Object value = this.getTabClientProperty(tabIndex, "JTabbedPane.tabClosable");
        return value instanceof Boolean && (boolean)value;
    }
    
    protected void closeTab(final int tabIndex) {
        final Object callback = this.getTabClientProperty(tabIndex, "JTabbedPane.tabCloseCallback");
        if (callback instanceof IntConsumer) {
            ((IntConsumer)callback).accept(tabIndex);
        }
        else {
            if (!(callback instanceof BiConsumer)) {
                throw new RuntimeException("Missing tab close callback. Set client property 'JTabbedPane.tabCloseCallback' to a 'java.util.function.IntConsumer' or 'java.util.function.BiConsumer<JTabbedPane, Integer>'");
            }
            ((BiConsumer)callback).accept(this.tabPane, tabIndex);
        }
    }
    
    protected Object getTabClientProperty(final int tabIndex, final String key) {
        if (tabIndex < 0) {
            return null;
        }
        final Component c = this.tabPane.getComponentAt(tabIndex);
        if (c instanceof JComponent) {
            final Object value = ((JComponent)c).getClientProperty(key);
            if (value != null) {
                return value;
            }
        }
        return this.tabPane.getClientProperty(key);
    }
    
    protected int getTabClientPropertyInt(final int tabIndex, final String key, final int defaultValue) {
        final Object value = this.getTabClientProperty(tabIndex, key);
        return (int)((value instanceof Integer) ? value : defaultValue);
    }
    
    protected void ensureCurrentLayout() {
        super.getTabRunCount(this.tabPane);
    }
    
    private boolean isLastInRun(final int tabIndex) {
        final int run = this.getRunForTab(this.tabPane.getTabCount(), tabIndex);
        return this.lastTabInRun(this.tabPane.getTabCount(), run) == tabIndex;
    }
    
    private boolean isScrollTabLayout() {
        return this.tabPane.getTabLayoutPolicy() == 1;
    }
    
    private boolean isLeftToRight() {
        return this.tabPane.getComponentOrientation().isLeftToRight();
    }
    
    protected boolean isHorizontalTabPlacement() {
        final int tabPlacement = this.tabPane.getTabPlacement();
        return tabPlacement == 1 || tabPlacement == 3;
    }
    
    protected boolean isSmoothScrollingEnabled() {
        return Animator.useAnimation() && UIManager.getBoolean("ScrollPane.smoothScrolling");
    }
    
    protected boolean hideTabArea() {
        return this.tabPane.getTabCount() == 1 && this.leadingComponent == null && this.trailingComponent == null && FlatClientProperties.clientPropertyBoolean(this.tabPane, "JTabbedPane.hideTabAreaWithOneTab", false);
    }
    
    protected int getTabsPopupPolicy() {
        final Object value = this.tabPane.getClientProperty("JTabbedPane.tabsPopupPolicy");
        return (value instanceof String) ? parseTabsPopupPolicy((String)value) : this.tabsPopupPolicy;
    }
    
    protected int getScrollButtonsPolicy() {
        final Object value = this.tabPane.getClientProperty("JTabbedPane.scrollButtonsPolicy");
        return (value instanceof String) ? parseScrollButtonsPolicy((String)value) : this.scrollButtonsPolicy;
    }
    
    protected int getScrollButtonsPlacement() {
        final Object value = this.tabPane.getClientProperty("JTabbedPane.scrollButtonsPlacement");
        return (value instanceof String) ? parseScrollButtonsPlacement((String)value) : this.scrollButtonsPlacement;
    }
    
    protected int getTabAreaAlignment() {
        final Object value = this.tabPane.getClientProperty("JTabbedPane.tabAreaAlignment");
        if (value instanceof Integer) {
            return (int)value;
        }
        return (value instanceof String) ? parseAlignment((String)value, 10) : this.tabAreaAlignment;
    }
    
    protected int getTabAlignment(final int tabIndex) {
        final Object value = this.getTabClientProperty(tabIndex, "JTabbedPane.tabAlignment");
        if (value instanceof Integer) {
            return (int)value;
        }
        return (value instanceof String) ? parseAlignment((String)value, 0) : this.tabAlignment;
    }
    
    protected int getTabWidthMode() {
        final Object value = this.tabPane.getClientProperty("JTabbedPane.tabWidthMode");
        return (value instanceof String) ? parseTabWidthMode((String)value) : this.tabWidthMode;
    }
    
    protected static int parseTabsPopupPolicy(final String str) {
        if (str == null) {
            return 2;
        }
        switch (str) {
            default: {
                return 2;
            }
            case "never": {
                return 0;
            }
        }
    }
    
    protected static int parseScrollButtonsPolicy(final String str) {
        if (str == null) {
            return 3;
        }
        switch (str) {
            default: {
                return 3;
            }
            case "asNeeded": {
                return 2;
            }
            case "never": {
                return 0;
            }
        }
    }
    
    protected static int parseScrollButtonsPlacement(final String str) {
        if (str == null) {
            return 100;
        }
        switch (str) {
            default: {
                return 100;
            }
            case "trailing": {
                return 11;
            }
        }
    }
    
    protected static int parseAlignment(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        switch (str) {
            case "leading": {
                return 10;
            }
            case "trailing": {
                return 11;
            }
            case "center": {
                return 0;
            }
            case "fill": {
                return 100;
            }
            default: {
                return defaultValue;
            }
        }
    }
    
    protected static int parseTabWidthMode(final String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
            default: {
                return 0;
            }
            case "equal": {
                return 1;
            }
            case "compact": {
                return 2;
            }
        }
    }
    
    private void runWithOriginalLayoutManager(final Runnable runnable) {
        final LayoutManager layout = this.tabPane.getLayout();
        if (layout instanceof FlatTabbedPaneScrollLayout) {
            this.tabPane.setLayout(((FlatTabbedPaneScrollLayout)layout).delegate);
            runnable.run();
            this.tabPane.setLayout(layout);
        }
        else {
            runnable.run();
        }
    }
    
    protected void ensureSelectedTabIsVisibleLater() {
        if (!this.tabPane.isDisplayable() || !EventQueue.isDispatchThread()) {
            return;
        }
        EventQueue.invokeLater(() -> this.ensureSelectedTabIsVisible());
    }
    
    protected void ensureSelectedTabIsVisible() {
        if (this.tabPane == null || this.tabViewport == null || !this.tabPane.isDisplayable()) {
            return;
        }
        this.ensureCurrentLayout();
        final int selectedIndex = this.tabPane.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.rects.length) {
            return;
        }
        ((JComponent)this.tabViewport.getView()).scrollRectToVisible((Rectangle)this.rects[selectedIndex].clone());
    }
    
    private int getLeadingPreferredWidth() {
        return (this.leadingComponent != null) ? this.leadingComponent.getPreferredSize().width : 0;
    }
    
    private int getLeadingPreferredHeight() {
        return (this.leadingComponent != null) ? this.leadingComponent.getPreferredSize().height : 0;
    }
    
    private int getTrailingPreferredWidth() {
        return (this.trailingComponent != null) ? this.trailingComponent.getPreferredSize().width : 0;
    }
    
    private int getTrailingPreferredHeight() {
        return (this.trailingComponent != null) ? this.trailingComponent.getPreferredSize().height : 0;
    }
    
    private void shiftTabs(final int sx, final int sy) {
        if (sx == 0 && sy == 0) {
            return;
        }
        for (int i = 0; i < this.rects.length; ++i) {
            final Rectangle rectangle = this.rects[i];
            rectangle.x += sx;
            final Rectangle rectangle2 = this.rects[i];
            rectangle2.y += sy;
            final Component c = this.tabPane.getTabComponentAt(i);
            if (c != null) {
                c.setLocation(c.getX() + sx, c.getY() + sy);
            }
        }
    }
    
    private void stretchTabsWidth(final int sw, final boolean leftToRight) {
        final int rsw = sw / this.rects.length;
        int x = this.rects[0].x - (leftToRight ? 0 : rsw);
        for (int i = 0; i < this.rects.length; ++i) {
            final Component c = this.tabPane.getTabComponentAt(i);
            if (c != null) {
                c.setLocation(x + (c.getX() - this.rects[i].x) + rsw / 2, c.getY());
            }
            this.rects[i].x = x;
            final Rectangle rectangle = this.rects[i];
            rectangle.width += rsw;
            if (leftToRight) {
                x += this.rects[i].width;
            }
            else if (i + 1 < this.rects.length) {
                x = this.rects[i].x - this.rects[i + 1].width - rsw;
            }
        }
        final int diff = sw - rsw * this.rects.length;
        final Rectangle rectangle2 = this.rects[this.rects.length - 1];
        rectangle2.width += diff;
        if (!leftToRight) {
            final Rectangle rectangle3 = this.rects[this.rects.length - 1];
            rectangle3.x -= diff;
        }
    }
    
    private void stretchTabsHeight(final int sh) {
        final int rsh = sh / this.rects.length;
        int y = this.rects[0].y;
        for (int i = 0; i < this.rects.length; ++i) {
            final Component c = this.tabPane.getTabComponentAt(i);
            if (c != null) {
                c.setLocation(c.getX(), y + (c.getY() - this.rects[i].y) + rsh / 2);
            }
            this.rects[i].y = y;
            final Rectangle rectangle = this.rects[i];
            rectangle.height += rsh;
            y += this.rects[i].height;
        }
        final Rectangle rectangle2 = this.rects[this.rects.length - 1];
        rectangle2.height += sh - rsh * this.rects.length;
    }
    
    private int rectsTotalWidth(final boolean leftToRight) {
        final int last = this.rects.length - 1;
        return leftToRight ? (this.rects[last].x + this.rects[last].width - this.rects[0].x) : (this.rects[0].x + this.rects[0].width - this.rects[last].x);
    }
    
    private int rectsTotalHeight() {
        final int last = this.rects.length - 1;
        return this.rects[last].y + this.rects[last].height - this.rects[0].y;
    }
    
    private class TabCloseButton extends JButton implements UIResource
    {
    }
    
    private class ContainerUIResource extends JPanel implements UIResource
    {
        private ContainerUIResource(final Component c) {
            super(new BorderLayout());
            this.add(c);
        }
    }
    
    protected class FlatTabAreaButton extends FlatArrowButton
    {
        public FlatTabAreaButton(final int direction) {
            super(direction, FlatTabbedPaneUI.this.arrowType, FlatTabbedPaneUI.this.foreground, FlatTabbedPaneUI.this.disabledForeground, null, FlatTabbedPaneUI.this.buttonHoverBackground, null, FlatTabbedPaneUI.this.buttonPressedBackground);
            this.setArrowWidth(10);
        }
        
        @Override
        protected Color deriveBackground(final Color background) {
            return FlatUIUtils.deriveColor(background, FlatTabbedPaneUI.this.tabPane.getBackground());
        }
        
        @Override
        public void paint(final Graphics g) {
            if (FlatTabbedPaneUI.this.tabsOpaque || FlatTabbedPaneUI.this.tabPane.isOpaque()) {
                g.setColor(FlatTabbedPaneUI.this.tabPane.getBackground());
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paint(g);
        }
        
        @Override
        protected void paintBackground(final Graphics2D g) {
            final Insets insets = new Insets(0, 0, 0, 0);
            BasicTabbedPaneUI.rotateInsets(FlatTabbedPaneUI.this.buttonInsets, insets, FlatTabbedPaneUI.this.tabPane.getTabPlacement());
            final int top = UIScale.scale2(insets.top);
            final int left = UIScale.scale2(insets.left);
            final int bottom = UIScale.scale2(insets.bottom);
            final int right = UIScale.scale2(insets.right);
            FlatUIUtils.paintComponentBackground(g, left, top, this.getWidth() - left - right, this.getHeight() - top - bottom, 0.0f, UIScale.scale((float)FlatTabbedPaneUI.this.buttonArc));
        }
    }
    
    protected class FlatMoreTabsButton extends FlatTabAreaButton implements ActionListener, PopupMenuListener
    {
        private boolean popupVisible;
        
        public FlatMoreTabsButton() {
            super(5);
            this.updateDirection();
            this.setToolTipText(FlatTabbedPaneUI.this.moreTabsButtonToolTipText);
            this.addActionListener(this);
        }
        
        protected void updateDirection() {
            int direction = 0;
            switch (FlatTabbedPaneUI.this.tabPane.getTabPlacement()) {
                default: {
                    direction = 5;
                    break;
                }
                case 3: {
                    direction = 1;
                    break;
                }
                case 2: {
                    direction = 3;
                    break;
                }
                case 4: {
                    direction = 7;
                    break;
                }
            }
            this.setDirection(direction);
        }
        
        @Override
        public Dimension getPreferredSize() {
            final Dimension size = super.getPreferredSize();
            final boolean horizontal = this.direction == 5 || this.direction == 1;
            final int margin = UIScale.scale(8);
            return new Dimension(size.width + (horizontal ? margin : 0), size.height + (horizontal ? 0 : margin));
        }
        
        @Override
        public void paint(final Graphics g) {
            if (this.direction == 3 || this.direction == 7) {
                final int xoffset = Math.max(UIScale.unscale((this.getWidth() - this.getHeight()) / 2) - 4, 0);
                this.setXOffset((this.direction == 3) ? ((float)xoffset) : ((float)(-xoffset)));
            }
            else {
                this.setXOffset(0.0f);
            }
            super.paint(g);
        }
        
        @Override
        protected boolean isHover() {
            return super.isHover() || this.popupVisible;
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (FlatTabbedPaneUI.this.tabViewport == null) {
                return;
            }
            final JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.addPopupMenuListener(this);
            final Rectangle viewRect = FlatTabbedPaneUI.this.tabViewport.getViewRect();
            int lastIndex = -1;
            for (int i = 0; i < FlatTabbedPaneUI.this.rects.length; ++i) {
                if (!viewRect.contains(FlatTabbedPaneUI.this.rects[i])) {
                    if (lastIndex >= 0 && lastIndex + 1 != i) {
                        popupMenu.addSeparator();
                    }
                    lastIndex = i;
                    popupMenu.add(this.createTabMenuItem(i));
                }
            }
            final int buttonWidth = this.getWidth();
            final int buttonHeight = this.getHeight();
            final Dimension popupSize = popupMenu.getPreferredSize();
            int x = FlatTabbedPaneUI.this.isLeftToRight() ? (buttonWidth - popupSize.width) : 0;
            int y = buttonHeight - popupSize.height;
            switch (FlatTabbedPaneUI.this.tabPane.getTabPlacement()) {
                default: {
                    y = buttonHeight;
                    break;
                }
                case 3: {
                    y = -popupSize.height;
                    break;
                }
                case 2: {
                    x = buttonWidth;
                    break;
                }
                case 4: {
                    x = -popupSize.width;
                    break;
                }
            }
            popupMenu.show(this, x, y);
        }
        
        protected JMenuItem createTabMenuItem(final int tabIndex) {
            String title = FlatTabbedPaneUI.this.tabPane.getTitleAt(tabIndex);
            if (StringUtils.isEmpty(title)) {
                final Component tabComp = FlatTabbedPaneUI.this.tabPane.getTabComponentAt(tabIndex);
                if (tabComp != null) {
                    title = this.findTabTitle(tabComp);
                }
                if (StringUtils.isEmpty(title)) {
                    title = FlatTabbedPaneUI.this.tabPane.getAccessibleContext().getAccessibleChild(tabIndex).getAccessibleContext().getAccessibleName();
                }
                if (StringUtils.isEmpty(title) && tabComp instanceof Accessible) {
                    title = this.findTabTitleInAccessible((Accessible)tabComp);
                }
                if (StringUtils.isEmpty(title)) {
                    title = tabIndex + 1 + ". Tab";
                }
            }
            final JMenuItem menuItem = new JMenuItem(title, FlatTabbedPaneUI.this.tabPane.getIconAt(tabIndex));
            menuItem.setDisabledIcon(FlatTabbedPaneUI.this.tabPane.getDisabledIconAt(tabIndex));
            menuItem.setToolTipText(FlatTabbedPaneUI.this.tabPane.getToolTipTextAt(tabIndex));
            final Color foregroundAt = FlatTabbedPaneUI.this.tabPane.getForegroundAt(tabIndex);
            if (foregroundAt != FlatTabbedPaneUI.this.tabPane.getForeground()) {
                menuItem.setForeground(foregroundAt);
            }
            final Color backgroundAt = FlatTabbedPaneUI.this.tabPane.getBackgroundAt(tabIndex);
            if (backgroundAt != FlatTabbedPaneUI.this.tabPane.getBackground()) {
                menuItem.setBackground(backgroundAt);
                menuItem.setOpaque(true);
            }
            if (!FlatTabbedPaneUI.this.tabPane.isEnabledAt(tabIndex)) {
                menuItem.setEnabled(false);
            }
            menuItem.addActionListener(e -> this.selectTab(tabIndex));
            return menuItem;
        }
        
        private String findTabTitle(final Component c) {
            String title = null;
            if (c instanceof JLabel) {
                title = ((JLabel)c).getText();
            }
            else if (c instanceof JTextComponent) {
                title = ((JTextComponent)c).getText();
            }
            if (!StringUtils.isEmpty(title)) {
                return title;
            }
            if (c instanceof Container) {
                for (final Component child : ((Container)c).getComponents()) {
                    title = this.findTabTitle(child);
                    if (title != null) {
                        return title;
                    }
                }
            }
            return null;
        }
        
        private String findTabTitleInAccessible(final Accessible accessible) {
            final AccessibleContext context = accessible.getAccessibleContext();
            if (context == null) {
                return null;
            }
            String title = context.getAccessibleName();
            if (!StringUtils.isEmpty(title)) {
                return title;
            }
            for (int childrenCount = context.getAccessibleChildrenCount(), i = 0; i < childrenCount; ++i) {
                title = this.findTabTitleInAccessible(context.getAccessibleChild(i));
                if (title != null) {
                    return title;
                }
            }
            return null;
        }
        
        protected void selectTab(final int tabIndex) {
            FlatTabbedPaneUI.this.tabPane.setSelectedIndex(tabIndex);
            FlatTabbedPaneUI.this.ensureSelectedTabIsVisible();
        }
        
        @Override
        public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
            this.popupVisible = true;
            this.repaint();
        }
        
        @Override
        public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
            this.popupVisible = false;
            this.repaint();
        }
        
        @Override
        public void popupMenuCanceled(final PopupMenuEvent e) {
            this.popupVisible = false;
            this.repaint();
        }
    }
    
    protected class FlatScrollableTabButton extends FlatTabAreaButton implements MouseListener
    {
        private Timer autoRepeatTimer;
        
        protected FlatScrollableTabButton(final int direction) {
            super(direction);
            this.addMouseListener(this);
        }
        
        @Override
        protected void fireActionPerformed(final ActionEvent event) {
            FlatTabbedPaneUI.this.runWithOriginalLayoutManager(() -> super.fireActionPerformed(event));
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && this.isEnabled()) {
                if (this.autoRepeatTimer == null) {
                    (this.autoRepeatTimer = new Timer(60, e2 -> {
                        if (this.isEnabled()) {
                            this.doClick();
                        }
                        return;
                    })).setInitialDelay(300);
                }
                this.autoRepeatTimer.start();
            }
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
            if (this.autoRepeatTimer != null) {
                this.autoRepeatTimer.stop();
            }
        }
        
        @Override
        public void mouseClicked(final MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(final MouseEvent e) {
            if (this.autoRepeatTimer != null && this.isPressed()) {
                this.autoRepeatTimer.start();
            }
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
            if (this.autoRepeatTimer != null) {
                this.autoRepeatTimer.stop();
            }
        }
    }
    
    protected class FlatWheelTabScroller extends MouseAdapter
    {
        private int lastMouseX;
        private int lastMouseY;
        private boolean inViewport;
        private boolean scrolled;
        private Timer rolloverTimer;
        private Timer exitedTimer;
        private Animator animator;
        private Point startViewPosition;
        private Point targetViewPosition;
        
        protected void uninstall() {
            if (this.rolloverTimer != null) {
                this.rolloverTimer.stop();
            }
            if (this.exitedTimer != null) {
                this.exitedTimer.stop();
            }
            if (this.animator != null) {
                this.animator.cancel();
            }
        }
        
        @Override
        public void mouseWheelMoved(final MouseWheelEvent e) {
            if (FlatTabbedPaneUI.this.tabPane.getMouseWheelListeners().length > 1) {
                return;
            }
            if (!this.isInViewport(e.getX(), e.getY())) {
                return;
            }
            this.lastMouseX = e.getX();
            this.lastMouseY = e.getY();
            final double preciseWheelRotation = e.getPreciseWheelRotation();
            final boolean isPreciseWheel = preciseWheelRotation != 0.0 && preciseWheelRotation != e.getWheelRotation();
            int amount = (int)(FlatTabbedPaneUI.this.maxTabHeight * preciseWheelRotation);
            if (amount == 0) {
                if (preciseWheelRotation > 0.0) {
                    amount = 1;
                }
                else if (preciseWheelRotation < 0.0) {
                    amount = -1;
                }
            }
            final Point viewPosition = (this.targetViewPosition != null) ? this.targetViewPosition : FlatTabbedPaneUI.this.tabViewport.getViewPosition();
            final Dimension viewSize = FlatTabbedPaneUI.this.tabViewport.getViewSize();
            final boolean horizontal = FlatTabbedPaneUI.this.isHorizontalTabPlacement();
            int x = viewPosition.x;
            int y = viewPosition.y;
            if (horizontal) {
                x += (FlatTabbedPaneUI.this.isLeftToRight() ? amount : (-amount));
            }
            else {
                y += amount;
            }
            if ((isPreciseWheel && FlatTabbedPaneUI.this.getScrollButtonsPlacement() == 100 && FlatTabbedPaneUI.this.getScrollButtonsPolicy() == 3 && (FlatTabbedPaneUI.this.isLeftToRight() || !horizontal)) || FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize != null) {
                if (horizontal) {
                    if (viewPosition.x == 0 && x > 0) {
                        x += FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize.width;
                    }
                    else if (amount < 0 && x <= FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize.width) {
                        x = 0;
                    }
                }
                else if (viewPosition.y == 0 && y > 0) {
                    y += FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize.height;
                }
                else if (amount < 0 && y <= FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize.height) {
                    y = 0;
                }
            }
            if (horizontal) {
                x = Math.min(Math.max(x, 0), viewSize.width - FlatTabbedPaneUI.this.tabViewport.getWidth());
            }
            else {
                y = Math.min(Math.max(y, 0), viewSize.height - FlatTabbedPaneUI.this.tabViewport.getHeight());
            }
            final Point newViewPosition = new Point(x, y);
            if (newViewPosition.equals(viewPosition)) {
                return;
            }
            if (isPreciseWheel) {
                if (this.animator != null) {
                    this.animator.stop();
                }
                FlatTabbedPaneUI.this.tabViewport.setViewPosition(newViewPosition);
                this.updateRolloverDelayed();
            }
            else {
                this.setViewPositionAnimated(newViewPosition);
            }
            this.scrolled = true;
        }
        
        protected void setViewPositionAnimated(final Point viewPosition) {
            if (viewPosition.equals(FlatTabbedPaneUI.this.tabViewport.getViewPosition())) {
                return;
            }
            if (!FlatTabbedPaneUI.this.isSmoothScrollingEnabled()) {
                FlatTabbedPaneUI.this.tabViewport.setViewPosition(viewPosition);
                this.updateRolloverDelayed();
                return;
            }
            this.startViewPosition = FlatTabbedPaneUI.this.tabViewport.getViewPosition();
            this.targetViewPosition = viewPosition;
            if (this.animator == null) {
                final int duration = 200;
                final int resolution = 10;
                (this.animator = new Animator(duration, fraction -> {
                    if (FlatTabbedPaneUI.this.tabViewport == null || !FlatTabbedPaneUI.this.tabViewport.isShowing()) {
                        this.animator.stop();
                        return;
                    }
                    else {
                        final int x = this.startViewPosition.x + Math.round((this.targetViewPosition.x - this.startViewPosition.x) * fraction);
                        final int y = this.startViewPosition.y + Math.round((this.targetViewPosition.y - this.startViewPosition.y) * fraction);
                        FlatTabbedPaneUI.this.tabViewport.setViewPosition(new Point(x, y));
                        return;
                    }
                }, () -> {
                    final Point point;
                    this.targetViewPosition = point;
                    this.startViewPosition = point;
                    if (FlatTabbedPaneUI.this.tabPane != null) {
                        FlatTabbedPaneUI.this.setRolloverTab(this.lastMouseX, this.lastMouseY);
                    }
                    return;
                })).setResolution(resolution);
                this.animator.setInterpolator(new CubicBezierEasing(0.5f, 0.5f, 0.5f, 1.0f));
            }
            this.animator.restart();
        }
        
        protected void updateRolloverDelayed() {
            FlatTabbedPaneUI.this.blockRollover = true;
            final int oldIndex = BasicTabbedPaneUI.this.getRolloverTab();
            if (oldIndex >= 0) {
                final int index = FlatTabbedPaneUI.this.tabForCoordinate(FlatTabbedPaneUI.this.tabPane, this.lastMouseX, this.lastMouseY);
                if (index >= 0 && index != oldIndex) {
                    FlatTabbedPaneUI.this.blockRollover = false;
                    FlatTabbedPaneUI.this.setRolloverTab(-1);
                    FlatTabbedPaneUI.this.blockRollover = true;
                }
            }
            if (this.rolloverTimer == null) {
                (this.rolloverTimer = new Timer(150, e -> {
                    FlatTabbedPaneUI.this.blockRollover = false;
                    if (FlatTabbedPaneUI.this.tabPane != null) {
                        FlatTabbedPaneUI.this.setRolloverTab(this.lastMouseX, this.lastMouseY);
                    }
                    return;
                })).setRepeats(false);
            }
            this.rolloverTimer.restart();
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
            this.checkViewportExited(e.getX(), e.getY());
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
            this.checkViewportExited(e.getX(), e.getY());
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            FlatTabbedPaneUI.this.setRolloverTab(e.getX(), e.getY());
        }
        
        protected boolean isInViewport(final int x, final int y) {
            return FlatTabbedPaneUI.this.tabViewport != null && FlatTabbedPaneUI.this.tabViewport.getBounds().contains(x, y);
        }
        
        protected void checkViewportExited(final int x, final int y) {
            this.lastMouseX = x;
            this.lastMouseY = y;
            final boolean wasInViewport = this.inViewport;
            this.inViewport = this.isInViewport(x, y);
            if (this.inViewport != wasInViewport) {
                if (!this.inViewport) {
                    this.viewportExited();
                }
                else if (this.exitedTimer != null) {
                    this.exitedTimer.stop();
                }
            }
        }
        
        protected void viewportExited() {
            if (!this.scrolled) {
                return;
            }
            if (this.exitedTimer == null) {
                (this.exitedTimer = new Timer(500, e -> this.ensureSelectedTabVisible())).setRepeats(false);
            }
            this.exitedTimer.start();
        }
        
        protected void ensureSelectedTabVisible() {
            if (FlatTabbedPaneUI.this.tabPane == null || FlatTabbedPaneUI.this.tabViewport == null) {
                return;
            }
            if (!this.scrolled || FlatTabbedPaneUI.this.tabViewport == null) {
                return;
            }
            this.scrolled = false;
            FlatTabbedPaneUI.this.ensureSelectedTabIsVisible();
        }
    }
    
    private class Handler implements MouseListener, MouseMotionListener, PropertyChangeListener, ChangeListener, ComponentListener, ContainerListener
    {
        MouseListener mouseDelegate;
        PropertyChangeListener propertyChangeDelegate;
        ChangeListener changeDelegate;
        private final PropertyChangeListener contentListener;
        private int pressedTabIndex;
        private int lastTipTabIndex;
        private String lastTip;
        
        private Handler() {
            this.contentListener = this::contentPropertyChange;
            this.pressedTabIndex = -1;
            this.lastTipTabIndex = -1;
        }
        
        void installListeners() {
            FlatTabbedPaneUI.this.tabPane.addMouseMotionListener(this);
            FlatTabbedPaneUI.this.tabPane.addComponentListener(this);
            FlatTabbedPaneUI.this.tabPane.addContainerListener(this);
            for (final Component c : FlatTabbedPaneUI.this.tabPane.getComponents()) {
                if (!(c instanceof UIResource)) {
                    c.addPropertyChangeListener(this.contentListener);
                }
            }
        }
        
        void uninstallListeners() {
            FlatTabbedPaneUI.this.tabPane.removeMouseMotionListener(this);
            FlatTabbedPaneUI.this.tabPane.removeComponentListener(this);
            FlatTabbedPaneUI.this.tabPane.removeContainerListener(this);
            for (final Component c : FlatTabbedPaneUI.this.tabPane.getComponents()) {
                if (!(c instanceof UIResource)) {
                    c.removePropertyChangeListener(this.contentListener);
                }
            }
        }
        
        @Override
        public void mouseClicked(final MouseEvent e) {
            this.mouseDelegate.mouseClicked(e);
        }
        
        @Override
        public void mousePressed(final MouseEvent e) {
            this.updateRollover(e);
            if (!FlatTabbedPaneUI.this.isPressedTabClose()) {
                this.mouseDelegate.mousePressed(e);
            }
        }
        
        @Override
        public void mouseReleased(final MouseEvent e) {
            if (FlatTabbedPaneUI.this.isPressedTabClose()) {
                this.updateRollover(e);
                if (this.pressedTabIndex >= 0 && this.pressedTabIndex == BasicTabbedPaneUI.this.getRolloverTab()) {
                    this.restoreTabToolTip();
                    FlatTabbedPaneUI.this.closeTab(this.pressedTabIndex);
                }
            }
            else {
                this.mouseDelegate.mouseReleased(e);
            }
            this.pressedTabIndex = -1;
            this.updateRollover(e);
        }
        
        @Override
        public void mouseEntered(final MouseEvent e) {
            this.updateRollover(e);
        }
        
        @Override
        public void mouseExited(final MouseEvent e) {
            this.updateRollover(e);
        }
        
        @Override
        public void mouseDragged(final MouseEvent e) {
            this.updateRollover(e);
        }
        
        @Override
        public void mouseMoved(final MouseEvent e) {
            this.updateRollover(e);
        }
        
        private void updateRollover(final MouseEvent e) {
            final int x = e.getX();
            final int y = e.getY();
            final int tabIndex = FlatTabbedPaneUI.this.tabForCoordinate(FlatTabbedPaneUI.this.tabPane, x, y);
            FlatTabbedPaneUI.this.setRolloverTab(tabIndex);
            final boolean hitClose = FlatTabbedPaneUI.this.isTabClosable(tabIndex) && FlatTabbedPaneUI.this.getTabCloseHitArea(tabIndex).contains(x, y);
            if (e.getID() == 501) {
                this.pressedTabIndex = (hitClose ? tabIndex : -1);
            }
            FlatTabbedPaneUI.this.setRolloverTabClose(hitClose);
            FlatTabbedPaneUI.this.setPressedTabClose(hitClose && tabIndex == this.pressedTabIndex);
            if (tabIndex >= 0 && hitClose) {
                final Object closeTip = FlatTabbedPaneUI.this.getTabClientProperty(tabIndex, "JTabbedPane.tabCloseToolTipText");
                if (closeTip instanceof String) {
                    this.setCloseToolTip(tabIndex, (String)closeTip);
                }
                else {
                    this.restoreTabToolTip();
                }
            }
            else {
                this.restoreTabToolTip();
            }
        }
        
        private void setCloseToolTip(final int tabIndex, final String closeTip) {
            if (tabIndex == this.lastTipTabIndex) {
                return;
            }
            if (tabIndex != this.lastTipTabIndex) {
                this.restoreTabToolTip();
            }
            this.lastTipTabIndex = tabIndex;
            this.lastTip = FlatTabbedPaneUI.this.tabPane.getToolTipTextAt(this.lastTipTabIndex);
            FlatTabbedPaneUI.this.tabPane.setToolTipTextAt(this.lastTipTabIndex, closeTip);
        }
        
        private void restoreTabToolTip() {
            if (this.lastTipTabIndex < 0) {
                return;
            }
            if (this.lastTipTabIndex < FlatTabbedPaneUI.this.tabPane.getTabCount()) {
                FlatTabbedPaneUI.this.tabPane.setToolTipTextAt(this.lastTipTabIndex, this.lastTip);
            }
            this.lastTip = null;
            this.lastTipTabIndex = -1;
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent e) {
            final String propertyName = e.getPropertyName();
            switch (propertyName) {
                case "tabPlacement":
                case "opaque":
                case "background":
                case "indexForTabComponent": {
                    FlatTabbedPaneUI.this.runWithOriginalLayoutManager(() -> this.propertyChangeDelegate.propertyChange(e));
                    break;
                }
                default: {
                    this.propertyChangeDelegate.propertyChange(e);
                    break;
                }
            }
            final String propertyName2 = e.getPropertyName();
            switch (propertyName2) {
                case "tabPlacement": {
                    if (FlatTabbedPaneUI.this.moreTabsButton instanceof FlatMoreTabsButton) {
                        ((FlatMoreTabsButton)FlatTabbedPaneUI.this.moreTabsButton).updateDirection();
                        break;
                    }
                    break;
                }
                case "componentOrientation": {
                    FlatTabbedPaneUI.this.ensureSelectedTabIsVisibleLater();
                    break;
                }
                case "JTabbedPane.showTabSeparators":
                case "JTabbedPane.showContentSeparator":
                case "JTabbedPane.hasFullBorder":
                case "JTabbedPane.hideTabAreaWithOneTab":
                case "JTabbedPane.minimumTabWidth":
                case "JTabbedPane.maximumTabWidth":
                case "JTabbedPane.tabHeight":
                case "JTabbedPane.tabInsets":
                case "JTabbedPane.tabAreaInsets":
                case "JTabbedPane.tabsPopupPolicy":
                case "JTabbedPane.scrollButtonsPolicy":
                case "JTabbedPane.scrollButtonsPlacement":
                case "JTabbedPane.tabAreaAlignment":
                case "JTabbedPane.tabAlignment":
                case "JTabbedPane.tabWidthMode":
                case "JTabbedPane.tabIconPlacement":
                case "JTabbedPane.tabClosable": {
                    FlatTabbedPaneUI.this.tabPane.revalidate();
                    FlatTabbedPaneUI.this.tabPane.repaint();
                    break;
                }
                case "JTabbedPane.leadingComponent": {
                    FlatTabbedPaneUI.this.uninstallLeadingComponent();
                    FlatTabbedPaneUI.this.installLeadingComponent();
                    FlatTabbedPaneUI.this.tabPane.revalidate();
                    FlatTabbedPaneUI.this.tabPane.repaint();
                    FlatTabbedPaneUI.this.ensureSelectedTabIsVisibleLater();
                    break;
                }
                case "JTabbedPane.trailingComponent": {
                    FlatTabbedPaneUI.this.uninstallTrailingComponent();
                    FlatTabbedPaneUI.this.installTrailingComponent();
                    FlatTabbedPaneUI.this.tabPane.revalidate();
                    FlatTabbedPaneUI.this.tabPane.repaint();
                    FlatTabbedPaneUI.this.ensureSelectedTabIsVisibleLater();
                    break;
                }
            }
        }
        
        @Override
        public void stateChanged(final ChangeEvent e) {
            this.changeDelegate.stateChanged(e);
            if (FlatTabbedPaneUI.this.moreTabsButton != null) {
                FlatTabbedPaneUI.this.ensureSelectedTabIsVisible();
            }
        }
        
        protected void contentPropertyChange(final PropertyChangeEvent e) {
            final String propertyName = e.getPropertyName();
            switch (propertyName) {
                case "JTabbedPane.minimumTabWidth":
                case "JTabbedPane.maximumTabWidth":
                case "JTabbedPane.tabInsets":
                case "JTabbedPane.tabAlignment":
                case "JTabbedPane.tabClosable": {
                    FlatTabbedPaneUI.this.tabPane.revalidate();
                    FlatTabbedPaneUI.this.tabPane.repaint();
                    break;
                }
            }
        }
        
        @Override
        public void componentResized(final ComponentEvent e) {
            FlatTabbedPaneUI.this.ensureSelectedTabIsVisibleLater();
        }
        
        @Override
        public void componentMoved(final ComponentEvent e) {
        }
        
        @Override
        public void componentShown(final ComponentEvent e) {
        }
        
        @Override
        public void componentHidden(final ComponentEvent e) {
        }
        
        @Override
        public void componentAdded(final ContainerEvent e) {
            final Component c = e.getChild();
            if (!(c instanceof UIResource)) {
                c.addPropertyChangeListener(this.contentListener);
            }
        }
        
        @Override
        public void componentRemoved(final ContainerEvent e) {
            final Component c = e.getChild();
            if (!(c instanceof UIResource)) {
                c.removePropertyChangeListener(this.contentListener);
            }
        }
    }
    
    protected class FlatTabbedPaneLayout extends TabbedPaneLayout
    {
        @Override
        protected Dimension calculateSize(final boolean minimum) {
            if (this.isContentEmpty()) {
                return this.calculateTabAreaSize();
            }
            return super.calculateSize(minimum);
        }
        
        protected boolean isContentEmpty() {
            final int tabCount = FlatTabbedPaneUI.this.tabPane.getTabCount();
            if (tabCount == 0) {
                return false;
            }
            for (int i = 0; i < tabCount; ++i) {
                final Component c = FlatTabbedPaneUI.this.tabPane.getComponentAt(i);
                if (c != null) {
                    final Dimension cs = c.getPreferredSize();
                    if (cs.width != 0 || cs.height != 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        protected Dimension calculateTabAreaSize() {
            final boolean horizontal = FlatTabbedPaneUI.this.isHorizontalTabPlacement();
            final int tabPlacement = FlatTabbedPaneUI.this.tabPane.getTabPlacement();
            final FontMetrics metrics = BasicTabbedPaneUI.this.getFontMetrics();
            final int fontHeight = metrics.getHeight();
            int width = 0;
            int height = 0;
            for (int tabCount = FlatTabbedPaneUI.this.tabPane.getTabCount(), i = 0; i < tabCount; ++i) {
                if (horizontal) {
                    width += FlatTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
                    height = Math.max(height, FlatTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight));
                }
                else {
                    width = Math.max(width, FlatTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics));
                    height += FlatTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
                }
            }
            if (horizontal) {
                height += UIScale.scale(FlatTabbedPaneUI.this.contentSeparatorHeight);
            }
            else {
                width += UIScale.scale(FlatTabbedPaneUI.this.contentSeparatorHeight);
            }
            final Insets insets = FlatTabbedPaneUI.this.tabPane.getInsets();
            final Insets tabAreaInsets = FlatTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
            return new Dimension(width + insets.left + insets.right + tabAreaInsets.left + tabAreaInsets.right, height + insets.bottom + insets.top + tabAreaInsets.top + tabAreaInsets.bottom);
        }
        
        @Override
        public void layoutContainer(final Container parent) {
            super.layoutContainer(parent);
            final Rectangle bounds = FlatTabbedPaneUI.this.tabPane.getBounds();
            final Insets insets = FlatTabbedPaneUI.this.tabPane.getInsets();
            final int tabPlacement = FlatTabbedPaneUI.this.tabPane.getTabPlacement();
            final int tabAreaAlignment = FlatTabbedPaneUI.this.getTabAreaAlignment();
            final Insets tabAreaInsets = FlatTabbedPaneUI.this.getRealTabAreaInsets(tabPlacement);
            final boolean leftToRight = FlatTabbedPaneUI.this.isLeftToRight();
            if (tabPlacement == 1 || tabPlacement == 3) {
                if (!leftToRight) {
                    FlatTabbedPaneUI.this.shiftTabs(insets.left + tabAreaInsets.right + FlatTabbedPaneUI.this.getTrailingPreferredWidth(), 0);
                }
                final int tabAreaHeight = (FlatTabbedPaneUI.this.maxTabHeight > 0) ? FlatTabbedPaneUI.this.maxTabHeight : Math.max(Math.max(FlatTabbedPaneUI.this.getLeadingPreferredHeight(), FlatTabbedPaneUI.this.getTrailingPreferredHeight()), UIScale.scale(FlatClientProperties.clientPropertyInt(FlatTabbedPaneUI.this.tabPane, "JTabbedPane.tabHeight", FlatTabbedPaneUI.this.tabHeight)));
                final int tx = insets.left;
                final int ty = (tabPlacement == 1) ? (insets.top + tabAreaInsets.top) : (bounds.height - insets.bottom - tabAreaInsets.bottom - tabAreaHeight);
                final int tw = bounds.width - insets.left - insets.right;
                final int th = tabAreaHeight;
                int leadingWidth = FlatTabbedPaneUI.this.getLeadingPreferredWidth();
                int trailingWidth = FlatTabbedPaneUI.this.getTrailingPreferredWidth();
                if (FlatTabbedPaneUI.this.runCount == 1 && FlatTabbedPaneUI.this.rects.length > 0) {
                    final int availWidth = tw - leadingWidth - trailingWidth - tabAreaInsets.left - tabAreaInsets.right;
                    final int totalTabWidth = FlatTabbedPaneUI.this.rectsTotalWidth(leftToRight);
                    final int diff = availWidth - totalTabWidth;
                    switch (tabAreaAlignment) {
                        case 10: {
                            trailingWidth += diff;
                            break;
                        }
                        case 11: {
                            FlatTabbedPaneUI.this.shiftTabs(leftToRight ? diff : (-diff), 0);
                            leadingWidth += diff;
                            break;
                        }
                        case 0: {
                            FlatTabbedPaneUI.this.shiftTabs((leftToRight ? diff : (-diff)) / 2, 0);
                            leadingWidth += diff / 2;
                            trailingWidth += diff - diff / 2;
                            break;
                        }
                        case 100: {
                            FlatTabbedPaneUI.this.stretchTabsWidth(diff, leftToRight);
                            break;
                        }
                    }
                }
                else if (FlatTabbedPaneUI.this.rects.length == 0) {
                    trailingWidth = tw - leadingWidth;
                }
                final Container leftComponent = leftToRight ? FlatTabbedPaneUI.this.leadingComponent : FlatTabbedPaneUI.this.trailingComponent;
                if (leftComponent != null) {
                    final int leftWidth = leftToRight ? leadingWidth : trailingWidth;
                    leftComponent.setBounds(tx, ty, leftWidth, th);
                }
                final Container rightComponent = leftToRight ? FlatTabbedPaneUI.this.trailingComponent : FlatTabbedPaneUI.this.leadingComponent;
                if (rightComponent != null) {
                    final int rightWidth = leftToRight ? trailingWidth : leadingWidth;
                    rightComponent.setBounds(tx + tw - rightWidth, ty, rightWidth, th);
                }
            }
            else {
                final int tabAreaWidth = (FlatTabbedPaneUI.this.maxTabWidth > 0) ? FlatTabbedPaneUI.this.maxTabWidth : Math.max(FlatTabbedPaneUI.this.getLeadingPreferredWidth(), FlatTabbedPaneUI.this.getTrailingPreferredWidth());
                final int tx = (tabPlacement == 2) ? (insets.left + tabAreaInsets.left) : (bounds.width - insets.right - tabAreaInsets.right - tabAreaWidth);
                final int ty = insets.top;
                final int tw = tabAreaWidth;
                final int th = bounds.height - insets.top - insets.bottom;
                int topHeight = FlatTabbedPaneUI.this.getLeadingPreferredHeight();
                int bottomHeight = FlatTabbedPaneUI.this.getTrailingPreferredHeight();
                if (FlatTabbedPaneUI.this.runCount == 1 && FlatTabbedPaneUI.this.rects.length > 0) {
                    final int availHeight = th - topHeight - bottomHeight - tabAreaInsets.top - tabAreaInsets.bottom;
                    final int totalTabHeight = FlatTabbedPaneUI.this.rectsTotalHeight();
                    final int diff = availHeight - totalTabHeight;
                    switch (tabAreaAlignment) {
                        case 10: {
                            bottomHeight += diff;
                            break;
                        }
                        case 11: {
                            FlatTabbedPaneUI.this.shiftTabs(0, diff);
                            topHeight += diff;
                            break;
                        }
                        case 0: {
                            FlatTabbedPaneUI.this.shiftTabs(0, diff / 2);
                            topHeight += diff / 2;
                            bottomHeight += diff - diff / 2;
                            break;
                        }
                        case 100: {
                            FlatTabbedPaneUI.this.stretchTabsHeight(diff);
                            break;
                        }
                    }
                }
                else if (FlatTabbedPaneUI.this.rects.length == 0) {
                    bottomHeight = th - topHeight;
                }
                if (FlatTabbedPaneUI.this.leadingComponent != null) {
                    FlatTabbedPaneUI.this.leadingComponent.setBounds(tx, ty, tw, topHeight);
                }
                if (FlatTabbedPaneUI.this.trailingComponent != null) {
                    FlatTabbedPaneUI.this.trailingComponent.setBounds(tx, ty + th - bottomHeight, tw, bottomHeight);
                }
            }
        }
    }
    
    protected class FlatTabbedPaneScrollLayout extends FlatTabbedPaneLayout implements LayoutManager
    {
        private final TabbedPaneLayout delegate;
        
        protected FlatTabbedPaneScrollLayout(final TabbedPaneLayout delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public void calculateLayoutInfo() {
            this.delegate.calculateLayoutInfo();
        }
        
        @Override
        protected Dimension calculateTabAreaSize() {
            final Dimension size = super.calculateTabAreaSize();
            if (FlatTabbedPaneUI.this.isHorizontalTabPlacement()) {
                size.width = Math.min(size.width, UIScale.scale(100));
            }
            else {
                size.height = Math.min(size.height, UIScale.scale(100));
            }
            return size;
        }
        
        @Override
        public Dimension preferredLayoutSize(final Container parent) {
            if (this.isContentEmpty()) {
                return this.calculateTabAreaSize();
            }
            return this.delegate.preferredLayoutSize(parent);
        }
        
        @Override
        public Dimension minimumLayoutSize(final Container parent) {
            if (this.isContentEmpty()) {
                return this.calculateTabAreaSize();
            }
            return this.delegate.minimumLayoutSize(parent);
        }
        
        @Override
        public void addLayoutComponent(final String name, final Component comp) {
            this.delegate.addLayoutComponent(name, comp);
        }
        
        @Override
        public void removeLayoutComponent(final Component comp) {
            this.delegate.removeLayoutComponent(comp);
        }
        
        @Override
        public void layoutContainer(final Container parent) {
            FlatTabbedPaneUI.this.runWithOriginalLayoutManager(() -> this.delegate.layoutContainer(parent));
            final int tabsPopupPolicy = FlatTabbedPaneUI.this.getTabsPopupPolicy();
            final int scrollButtonsPolicy = FlatTabbedPaneUI.this.getScrollButtonsPolicy();
            final int scrollButtonsPlacement = FlatTabbedPaneUI.this.getScrollButtonsPlacement();
            boolean useMoreTabsButton = tabsPopupPolicy == 2;
            boolean useScrollButtons = scrollButtonsPolicy == 2 || scrollButtonsPolicy == 3;
            final boolean hideDisabledScrollButtons = scrollButtonsPolicy == 3 && scrollButtonsPlacement == 100;
            final boolean trailingScrollButtons = scrollButtonsPlacement == 11;
            final boolean leftToRight = FlatTabbedPaneUI.this.isLeftToRight();
            if (!leftToRight && FlatTabbedPaneUI.this.isHorizontalTabPlacement()) {
                useMoreTabsButton = true;
                useScrollButtons = false;
            }
            JButton backwardButton = null;
            JButton forwardButton = null;
            for (final Component c : FlatTabbedPaneUI.this.tabPane.getComponents()) {
                if (c instanceof FlatScrollableTabButton) {
                    final int direction = ((FlatScrollableTabButton)c).getDirection();
                    if (direction == 7 || direction == 1) {
                        backwardButton = (JButton)c;
                    }
                    else if (direction == 3 || direction == 5) {
                        forwardButton = (JButton)c;
                    }
                }
            }
            if (backwardButton == null || forwardButton == null) {
                return;
            }
            final Rectangle bounds = FlatTabbedPaneUI.this.tabPane.getBounds();
            final Insets insets = FlatTabbedPaneUI.this.tabPane.getInsets();
            final int tabPlacement = FlatTabbedPaneUI.this.tabPane.getTabPlacement();
            final int tabAreaAlignment = FlatTabbedPaneUI.this.getTabAreaAlignment();
            final Insets tabAreaInsets = FlatTabbedPaneUI.this.getRealTabAreaInsets(tabPlacement);
            boolean moreTabsButtonVisible = false;
            boolean backwardButtonVisible = false;
            boolean forwardButtonVisible = false;
            if (tabAreaInsets.left != 0 || tabAreaInsets.top != 0) {
                FlatTabbedPaneUI.this.shiftTabs(-tabAreaInsets.left, -tabAreaInsets.top);
                final Component view = FlatTabbedPaneUI.this.tabViewport.getView();
                final Dimension viewSize = view.getPreferredSize();
                final boolean horizontal = tabPlacement == 1 || tabPlacement == 3;
                view.setPreferredSize(new Dimension(viewSize.width - (horizontal ? tabAreaInsets.left : 0), viewSize.height - (horizontal ? 0 : tabAreaInsets.top)));
            }
            if (tabPlacement == 1 || tabPlacement == 3) {
                if (useScrollButtons && hideDisabledScrollButtons) {
                    final Point viewPosition = FlatTabbedPaneUI.this.tabViewport.getViewPosition();
                    if (viewPosition.x <= backwardButton.getPreferredSize().width) {
                        FlatTabbedPaneUI.this.tabViewport.setViewPosition(new Point(0, viewPosition.y));
                    }
                }
                final int tabAreaHeight = (FlatTabbedPaneUI.this.maxTabHeight > 0) ? FlatTabbedPaneUI.this.maxTabHeight : Math.max(Math.max(FlatTabbedPaneUI.this.getLeadingPreferredHeight(), FlatTabbedPaneUI.this.getTrailingPreferredHeight()), UIScale.scale(FlatClientProperties.clientPropertyInt(FlatTabbedPaneUI.this.tabPane, "JTabbedPane.tabHeight", FlatTabbedPaneUI.this.tabHeight)));
                final int tx = insets.left;
                final int ty = (tabPlacement == 1) ? (insets.top + tabAreaInsets.top) : (bounds.height - insets.bottom - tabAreaInsets.bottom - tabAreaHeight);
                final int tw = bounds.width - insets.left - insets.right;
                final int th = tabAreaHeight;
                int leadingWidth = FlatTabbedPaneUI.this.getLeadingPreferredWidth();
                int trailingWidth = FlatTabbedPaneUI.this.getTrailingPreferredWidth();
                final int availWidth = tw - leadingWidth - trailingWidth - tabAreaInsets.left - tabAreaInsets.right;
                int totalTabWidth = (FlatTabbedPaneUI.this.rects.length > 0) ? FlatTabbedPaneUI.this.rectsTotalWidth(leftToRight) : 0;
                if (totalTabWidth < availWidth && FlatTabbedPaneUI.this.rects.length > 0) {
                    final int diff = availWidth - totalTabWidth;
                    switch (tabAreaAlignment) {
                        case 10: {
                            trailingWidth += diff;
                            break;
                        }
                        case 11: {
                            leadingWidth += diff;
                            break;
                        }
                        case 0: {
                            leadingWidth += diff / 2;
                            trailingWidth += diff - diff / 2;
                            break;
                        }
                        case 100: {
                            FlatTabbedPaneUI.this.stretchTabsWidth(diff, leftToRight);
                            totalTabWidth = FlatTabbedPaneUI.this.rectsTotalWidth(leftToRight);
                            break;
                        }
                    }
                }
                else if (FlatTabbedPaneUI.this.rects.length == 0) {
                    trailingWidth = tw - leadingWidth;
                }
                final Container leftComponent = leftToRight ? FlatTabbedPaneUI.this.leadingComponent : FlatTabbedPaneUI.this.trailingComponent;
                final int leftWidth = leftToRight ? leadingWidth : trailingWidth;
                if (leftComponent != null) {
                    leftComponent.setBounds(tx, ty, leftWidth, th);
                }
                final Container rightComponent = leftToRight ? FlatTabbedPaneUI.this.trailingComponent : FlatTabbedPaneUI.this.leadingComponent;
                final int rightWidth = leftToRight ? trailingWidth : leadingWidth;
                if (rightComponent != null) {
                    rightComponent.setBounds(tx + tw - rightWidth, ty, rightWidth, th);
                }
                if (FlatTabbedPaneUI.this.rects.length > 0) {
                    final int txi = tx + leftWidth + (leftToRight ? tabAreaInsets.left : tabAreaInsets.right);
                    final int twi = tw - leftWidth - rightWidth - tabAreaInsets.left - tabAreaInsets.right;
                    int x = txi;
                    int w = twi;
                    if (w < totalTabWidth) {
                        if (useMoreTabsButton) {
                            final int buttonWidth = FlatTabbedPaneUI.this.moreTabsButton.getPreferredSize().width;
                            FlatTabbedPaneUI.this.moreTabsButton.setBounds(leftToRight ? (x + w - buttonWidth) : x, ty, buttonWidth, th);
                            x += (leftToRight ? 0 : buttonWidth);
                            w -= buttonWidth;
                            moreTabsButtonVisible = true;
                        }
                        if (useScrollButtons) {
                            if (!hideDisabledScrollButtons || forwardButton.isEnabled()) {
                                final int buttonWidth = forwardButton.getPreferredSize().width;
                                forwardButton.setBounds(leftToRight ? (x + w - buttonWidth) : x, ty, buttonWidth, th);
                                x += (leftToRight ? 0 : buttonWidth);
                                w -= buttonWidth;
                                forwardButtonVisible = true;
                            }
                            if (!hideDisabledScrollButtons || backwardButton.isEnabled()) {
                                final int buttonWidth = backwardButton.getPreferredSize().width;
                                if (trailingScrollButtons) {
                                    backwardButton.setBounds(leftToRight ? (x + w - buttonWidth) : x, ty, buttonWidth, th);
                                    x += (leftToRight ? 0 : buttonWidth);
                                }
                                else {
                                    backwardButton.setBounds(leftToRight ? x : (x + w - buttonWidth), ty, buttonWidth, th);
                                    x += (leftToRight ? buttonWidth : 0);
                                }
                                w -= buttonWidth;
                                backwardButtonVisible = true;
                            }
                        }
                    }
                    FlatTabbedPaneUI.this.tabViewport.setBounds(x, ty, w, th);
                    if (!leftToRight) {
                        FlatTabbedPaneUI.this.tabViewport.doLayout();
                        FlatTabbedPaneUI.this.shiftTabs(FlatTabbedPaneUI.this.tabViewport.getView().getWidth() - (FlatTabbedPaneUI.this.rects[0].x + FlatTabbedPaneUI.this.rects[0].width), 0);
                    }
                }
            }
            else {
                if (useScrollButtons && hideDisabledScrollButtons) {
                    final Point viewPosition = FlatTabbedPaneUI.this.tabViewport.getViewPosition();
                    if (viewPosition.y <= backwardButton.getPreferredSize().height) {
                        FlatTabbedPaneUI.this.tabViewport.setViewPosition(new Point(viewPosition.x, 0));
                    }
                }
                final int tabAreaWidth = (FlatTabbedPaneUI.this.maxTabWidth > 0) ? FlatTabbedPaneUI.this.maxTabWidth : Math.max(FlatTabbedPaneUI.this.getLeadingPreferredWidth(), FlatTabbedPaneUI.this.getTrailingPreferredWidth());
                final int tx = (tabPlacement == 2) ? (insets.left + tabAreaInsets.left) : (bounds.width - insets.right - tabAreaInsets.right - tabAreaWidth);
                final int ty = insets.top;
                final int tw = tabAreaWidth;
                final int th = bounds.height - insets.top - insets.bottom;
                int topHeight = FlatTabbedPaneUI.this.getLeadingPreferredHeight();
                int bottomHeight = FlatTabbedPaneUI.this.getTrailingPreferredHeight();
                final int availHeight = th - topHeight - bottomHeight - tabAreaInsets.top - tabAreaInsets.bottom;
                int totalTabHeight = (FlatTabbedPaneUI.this.rects.length > 0) ? FlatTabbedPaneUI.this.rectsTotalHeight() : 0;
                if (totalTabHeight < availHeight && FlatTabbedPaneUI.this.rects.length > 0) {
                    final int diff = availHeight - totalTabHeight;
                    switch (tabAreaAlignment) {
                        case 10: {
                            bottomHeight += diff;
                            break;
                        }
                        case 11: {
                            topHeight += diff;
                            break;
                        }
                        case 0: {
                            topHeight += diff / 2;
                            bottomHeight += diff - diff / 2;
                            break;
                        }
                        case 100: {
                            FlatTabbedPaneUI.this.stretchTabsHeight(diff);
                            totalTabHeight = FlatTabbedPaneUI.this.rectsTotalHeight();
                            break;
                        }
                    }
                }
                else if (FlatTabbedPaneUI.this.rects.length == 0) {
                    bottomHeight = th - topHeight;
                }
                if (FlatTabbedPaneUI.this.leadingComponent != null) {
                    FlatTabbedPaneUI.this.leadingComponent.setBounds(tx, ty, tw, topHeight);
                }
                if (FlatTabbedPaneUI.this.trailingComponent != null) {
                    FlatTabbedPaneUI.this.trailingComponent.setBounds(tx, ty + th - bottomHeight, tw, bottomHeight);
                }
                if (FlatTabbedPaneUI.this.rects.length > 0) {
                    final int tyi = ty + topHeight + tabAreaInsets.top;
                    final int thi = th - topHeight - bottomHeight - tabAreaInsets.top - tabAreaInsets.bottom;
                    int y = tyi;
                    int h = thi;
                    if (h < totalTabHeight) {
                        if (useMoreTabsButton) {
                            final int buttonHeight = FlatTabbedPaneUI.this.moreTabsButton.getPreferredSize().height;
                            FlatTabbedPaneUI.this.moreTabsButton.setBounds(tx, y + h - buttonHeight, tw, buttonHeight);
                            h -= buttonHeight;
                            moreTabsButtonVisible = true;
                        }
                        if (useScrollButtons) {
                            if (!hideDisabledScrollButtons || forwardButton.isEnabled()) {
                                final int buttonHeight = forwardButton.getPreferredSize().height;
                                forwardButton.setBounds(tx, y + h - buttonHeight, tw, buttonHeight);
                                h -= buttonHeight;
                                forwardButtonVisible = true;
                            }
                            if (!hideDisabledScrollButtons || backwardButton.isEnabled()) {
                                final int buttonHeight = backwardButton.getPreferredSize().height;
                                if (trailingScrollButtons) {
                                    backwardButton.setBounds(tx, y + h - buttonHeight, tw, buttonHeight);
                                }
                                else {
                                    backwardButton.setBounds(tx, y, tw, buttonHeight);
                                    y += buttonHeight;
                                }
                                h -= buttonHeight;
                                backwardButtonVisible = true;
                            }
                        }
                    }
                    FlatTabbedPaneUI.this.tabViewport.setBounds(tx, y, tw, h);
                }
            }
            FlatTabbedPaneUI.this.tabViewport.setVisible(FlatTabbedPaneUI.this.rects.length > 0);
            FlatTabbedPaneUI.this.moreTabsButton.setVisible(moreTabsButtonVisible);
            backwardButton.setVisible(backwardButtonVisible);
            forwardButton.setVisible(forwardButtonVisible);
            FlatTabbedPaneUI.this.scrollBackwardButtonPrefSize = backwardButton.getPreferredSize();
        }
    }
    
    private static class RunWithOriginalLayoutManagerDelegateAction implements Action
    {
        private final Action delegate;
        
        static void install(final ActionMap map, final String key) {
            final Action oldAction = map.get(key);
            if (oldAction == null || oldAction instanceof RunWithOriginalLayoutManagerDelegateAction) {
                return;
            }
            map.put(key, new RunWithOriginalLayoutManagerDelegateAction(oldAction));
        }
        
        private RunWithOriginalLayoutManagerDelegateAction(final Action delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public Object getValue(final String key) {
            return this.delegate.getValue(key);
        }
        
        @Override
        public boolean isEnabled() {
            return this.delegate.isEnabled();
        }
        
        @Override
        public void putValue(final String key, final Object value) {
        }
        
        @Override
        public void setEnabled(final boolean b) {
        }
        
        @Override
        public void addPropertyChangeListener(final PropertyChangeListener listener) {
        }
        
        @Override
        public void removePropertyChangeListener(final PropertyChangeListener listener) {
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            final JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
            final ComponentUI ui = tabbedPane.getUI();
            if (ui instanceof FlatTabbedPaneUI) {
                ((FlatTabbedPaneUI)ui).runWithOriginalLayoutManager(() -> this.delegate.actionPerformed(e));
            }
            else {
                this.delegate.actionPerformed(e);
            }
        }
    }
}
