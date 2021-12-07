// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import javax.swing.plaf.basic.*;
import java.util.function.*;
import java.awt.image.*;
import javax.swing.text.html.*;
import java.lang.reflect.*;
import com.formdev.flatlaf.util.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import java.io.*;
import java.beans.*;
import java.awt.*;
import com.formdev.flatlaf.ui.*;
import javax.swing.*;
import java.util.*;

public abstract class FlatLaf extends BasicLookAndFeel
{
    private static final String DESKTOPFONTHINTS = "awt.font.desktophints";
    private static List<Object> customDefaultsSources;
    private String desktopPropertyName;
    private String desktopPropertyName2;
    private PropertyChangeListener desktopPropertyListener;
    private static boolean aquaLoaded;
    private static boolean updateUIPending;
    private PopupFactory oldPopupFactory;
    private MnemonicHandler mnemonicHandler;
    private Consumer<UIDefaults> postInitialization;
    
    public static boolean setup(final LookAndFeel newLookAndFeel) {
        try {
            UIManager.setLookAndFeel(newLookAndFeel);
            return true;
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere("FlatLaf: Failed to setup look and feel '" + newLookAndFeel.getClass().getName() + "'.", ex);
            return false;
        }
    }
    
    @Deprecated
    public static boolean install(final LookAndFeel newLookAndFeel) {
        return setup(newLookAndFeel);
    }
    
    public static void installLafInfo(final String lafName, final Class<? extends LookAndFeel> lafClass) {
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(lafName, lafClass.getName()));
    }
    
    @Override
    public String getID() {
        return "FlatLaf - " + this.getName();
    }
    
    public abstract boolean isDark();
    
    public static boolean isLafDark() {
        final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        return lookAndFeel instanceof FlatLaf && ((FlatLaf)lookAndFeel).isDark();
    }
    
    @Override
    public boolean getSupportsWindowDecorations() {
        return !SystemInfo.isProjector && !SystemInfo.isWebswing && !SystemInfo.isWinPE && (!SystemInfo.isWindows_10_orLater || !FlatNativeWindowBorder.isSupported()) && SystemInfo.isWindows_10_orLater;
    }
    
    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }
    
    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    @Override
    public Icon getDisabledIcon(final JComponent component, final Icon icon) {
        if (icon instanceof DisabledIconProvider) {
            final Icon disabledIcon = ((DisabledIconProvider)icon).getDisabledIcon();
            return (disabledIcon instanceof UIResource) ? disabledIcon : new IconUIResource(disabledIcon);
        }
        if (icon instanceof ImageIcon) {
            final Object grayFilter = UIManager.get("Component.grayFilter");
            final ImageFilter filter = (grayFilter instanceof ImageFilter) ? ((ImageFilter)grayFilter) : GrayFilter.createDisabledIconFilter(this.isDark());
            final Function<Image, Image> mapper = (Function<Image, Image>)(img -> {
                final ImageProducer producer = new FilteredImageSource(img.getSource(), filter);
                return Toolkit.getDefaultToolkit().createImage(producer);
            });
            final Image image = ((ImageIcon)icon).getImage();
            return new ImageIconUIResource(MultiResolutionImageSupport.map(image, mapper));
        }
        return null;
    }
    
    @Override
    public void initialize() {
        if (SystemInfo.isMacOS) {
            this.initializeAqua();
        }
        super.initialize();
        this.oldPopupFactory = PopupFactory.getSharedInstance();
        PopupFactory.setSharedInstance(new FlatPopupFactory());
        (this.mnemonicHandler = new MnemonicHandler()).install();
        if (SystemInfo.isWindows) {
            this.desktopPropertyName = "win.messagebox.font";
        }
        else if (SystemInfo.isLinux) {
            this.desktopPropertyName = "gnome.Gtk/FontName";
            this.desktopPropertyName2 = "gnome.Xft/DPI";
        }
        if (this.desktopPropertyName != null) {
            this.desktopPropertyListener = (e -> {
                final String propertyName = e.getPropertyName();
                if (this.desktopPropertyName.equals(propertyName) || propertyName.equals(this.desktopPropertyName2)) {
                    reSetLookAndFeel();
                }
                else if ("awt.font.desktophints".equals(propertyName) && UIManager.getLookAndFeel() instanceof FlatLaf) {
                    this.putAATextInfo(UIManager.getLookAndFeelDefaults());
                    updateUILater();
                }
                return;
            });
            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.addPropertyChangeListener(this.desktopPropertyName, this.desktopPropertyListener);
            if (this.desktopPropertyName2 != null) {
                toolkit.addPropertyChangeListener(this.desktopPropertyName2, this.desktopPropertyListener);
            }
            toolkit.addPropertyChangeListener("awt.font.desktophints", this.desktopPropertyListener);
        }
        this.postInitialization = (defaults -> {
            final Color linkColor = defaults.getColor("Component.linkColor");
            if (linkColor != null) {
                new HTMLEditorKit().getStyleSheet().addRule(String.format("a, address { color: #%06x; }", linkColor.getRGB() & 0xFFFFFF));
            }
        });
    }
    
    @Override
    public void uninitialize() {
        if (this.desktopPropertyListener != null) {
            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.removePropertyChangeListener(this.desktopPropertyName, this.desktopPropertyListener);
            if (this.desktopPropertyName2 != null) {
                toolkit.removePropertyChangeListener(this.desktopPropertyName2, this.desktopPropertyListener);
            }
            toolkit.removePropertyChangeListener("awt.font.desktophints", this.desktopPropertyListener);
            this.desktopPropertyName = null;
            this.desktopPropertyName2 = null;
            this.desktopPropertyListener = null;
        }
        if (this.oldPopupFactory != null) {
            PopupFactory.setSharedInstance(this.oldPopupFactory);
            this.oldPopupFactory = null;
        }
        if (this.mnemonicHandler != null) {
            this.mnemonicHandler.uninstall();
            this.mnemonicHandler = null;
        }
        new HTMLEditorKit().getStyleSheet().addRule("a, address { color: blue; }");
        this.postInitialization = null;
        super.uninitialize();
    }
    
    private void initializeAqua() {
        if (FlatLaf.aquaLoaded) {
            return;
        }
        FlatLaf.aquaLoaded = true;
        final String aquaLafClassName = "com.apple.laf.AquaLookAndFeel";
        BasicLookAndFeel aquaLaf;
        try {
            if (SystemInfo.isJava_9_orLater) {
                final Method m = UIManager.class.getMethod("createLookAndFeel", String.class);
                aquaLaf = (BasicLookAndFeel)m.invoke(null, "Mac OS X");
            }
            else {
                aquaLaf = (BasicLookAndFeel)Class.forName(aquaLafClassName).getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere("FlatLaf: Failed to initialize Aqua look and feel '" + aquaLafClassName + "'.", ex);
            throw new IllegalStateException();
        }
        final PopupFactory oldPopupFactory = PopupFactory.getSharedInstance();
        aquaLaf.initialize();
        aquaLaf.uninitialize();
        PopupFactory.setSharedInstance(oldPopupFactory);
    }
    
    @Override
    public UIDefaults getDefaults() {
        final UIDefaults defaults = super.getDefaults();
        defaults.put("laf.dark", this.isDark());
        this.initResourceBundle(defaults, "com.formdev.flatlaf.resources.Bundle");
        this.putDefaults(defaults, defaults.getColor("control"), "Button.disabledBackground", "EditorPane.disabledBackground", "EditorPane.inactiveBackground", "FormattedTextField.disabledBackground", "PasswordField.disabledBackground", "Spinner.disabledBackground", "TextArea.disabledBackground", "TextArea.inactiveBackground", "TextField.disabledBackground", "TextPane.disabledBackground", "TextPane.inactiveBackground", "ToggleButton.disabledBackground");
        this.putDefaults(defaults, defaults.getColor("textInactiveText"), "Button.disabledText", "CheckBox.disabledText", "CheckBoxMenuItem.disabledForeground", "Menu.disabledForeground", "MenuItem.disabledForeground", "RadioButton.disabledText", "RadioButtonMenuItem.disabledForeground", "Spinner.disabledForeground", "ToggleButton.disabledText");
        this.putDefaults(defaults, defaults.getColor("textText"), "DesktopIcon.foreground");
        this.initFonts(defaults);
        initIconColors(defaults, this.isDark());
        FlatInputMaps.initInputMaps(defaults);
        final Object icon = ((Hashtable<K, Object>)defaults).remove("InternalFrame.icon");
        defaults.put("InternalFrame.icon", icon);
        defaults.put("TitlePane.icon", icon);
        final ServiceLoader<FlatDefaultsAddon> addonLoader = ServiceLoader.load(FlatDefaultsAddon.class);
        final List<FlatDefaultsAddon> addons = new ArrayList<FlatDefaultsAddon>();
        for (final FlatDefaultsAddon addon : addonLoader) {
            addons.add(addon);
        }
        FlatDefaultsAddon addon2 = null;
        addons.sort((addon1, addon2) -> addon1.getPriority() - addon2.getPriority());
        final List<Class<?>> lafClassesForDefaultsLoading = this.getLafClassesForDefaultsLoading();
        if (lafClassesForDefaultsLoading != null) {
            UIDefaultsLoader.loadDefaultsFromProperties(lafClassesForDefaultsLoading, addons, this.getAdditionalDefaults(), this.isDark(), defaults);
        }
        else {
            UIDefaultsLoader.loadDefaultsFromProperties(this.getClass(), addons, this.getAdditionalDefaults(), this.isDark(), defaults);
        }
        if (SystemInfo.isMacOS && Boolean.getBoolean("apple.laf.useScreenMenuBar")) {
            defaults.put("MenuBarUI", "com.apple.laf.AquaMenuBarUI");
            defaults.put("MenuBar.backgroundPainter", BorderFactory.createEmptyBorder());
        }
        this.putAATextInfo(defaults);
        this.applyAdditionalDefaults(defaults);
        final Iterator<FlatDefaultsAddon> iterator2 = addons.iterator();
        while (iterator2.hasNext()) {
            addon2 = iterator2.next();
            addon2.afterDefaultsLoading(this, defaults);
        }
        defaults.put("laf.scaleFactor", t -> UIScale.getUserScaleFactor());
        if (this.postInitialization != null) {
            this.postInitialization.accept(defaults);
            this.postInitialization = null;
        }
        return defaults;
    }
    
    void applyAdditionalDefaults(final UIDefaults defaults) {
    }
    
    protected List<Class<?>> getLafClassesForDefaultsLoading() {
        return null;
    }
    
    protected Properties getAdditionalDefaults() {
        return null;
    }
    
    private void initResourceBundle(final UIDefaults defaults, final String bundleName) {
        defaults.addResourceBundle(bundleName);
        if (defaults.get("FileChooser.fileNameHeaderText") != null) {
            return;
        }
        try {
            final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, defaults.getDefaultLocale());
            final Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                final String key = keys.nextElement();
                final String value = bundle.getString(key);
                final String baseKey = StringUtils.removeTrailing(key, ".textAndMnemonic");
                if (baseKey != key) {
                    final String text = value.replace("&", "");
                    String mnemonic = null;
                    final int index = value.indexOf(38);
                    if (index >= 0) {
                        mnemonic = Integer.toString(Character.toUpperCase(value.charAt(index + 1)));
                    }
                    defaults.put(baseKey + "Text", text);
                    if (mnemonic == null) {
                        continue;
                    }
                    defaults.put(baseKey + "Mnemonic", mnemonic);
                }
                else {
                    defaults.put(key, value);
                }
            }
        }
        catch (MissingResourceException ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }
    
    private void initFonts(final UIDefaults defaults) {
        FontUIResource uiFont = null;
        if (SystemInfo.isWindows) {
            final Font winFont = (Font)Toolkit.getDefaultToolkit().getDesktopProperty("win.messagebox.font");
            if (winFont != null) {
                if (SystemInfo.isWinPE) {
                    final Font winPEFont = (Font)Toolkit.getDefaultToolkit().getDesktopProperty("win.defaultGUI.font");
                    if (winPEFont != null) {
                        uiFont = createCompositeFont(winPEFont.getFamily(), winPEFont.getStyle(), winFont.getSize());
                    }
                }
                else {
                    uiFont = createCompositeFont(winFont.getFamily(), winFont.getStyle(), winFont.getSize());
                }
            }
        }
        else if (SystemInfo.isMacOS) {
            String fontName;
            if (SystemInfo.isMacOS_10_15_Catalina_orLater) {
                if (SystemInfo.isJetBrainsJVM_11_orLater) {
                    fontName = ".AppleSystemUIFont";
                }
                else {
                    fontName = "Helvetica Neue";
                }
            }
            else if (SystemInfo.isMacOS_10_11_ElCapitan_orLater) {
                fontName = ".SF NS Text";
            }
            else {
                fontName = "Lucida Grande";
            }
            uiFont = createCompositeFont(fontName, 0, 13);
        }
        else if (SystemInfo.isLinux) {
            final Font font = LinuxFontPolicy.getFont();
            uiFont = (FontUIResource)((font instanceof FontUIResource) ? font : new FontUIResource(font));
        }
        if (uiFont == null) {
            uiFont = createCompositeFont("SansSerif", 0, 12);
        }
        uiFont = UIScale.applyCustomScaleFactor(uiFont);
        final Object activeFont = new ActiveFont(1.0f);
        for (final Object key : ((Hashtable<Object, V>)defaults).keySet()) {
            if (key instanceof String && (((String)key).endsWith(".font") || ((String)key).endsWith("Font"))) {
                defaults.put(key, activeFont);
            }
        }
        defaults.put("ProgressBar.font", new ActiveFont(0.85f));
        defaults.put("defaultFont", uiFont);
    }
    
    static FontUIResource createCompositeFont(final String family, final int style, final int size) {
        final Font font = StyleContext.getDefaultStyleContext().getFont(family, style, size);
        return (FontUIResource)((font instanceof FontUIResource) ? font : new FontUIResource(font));
    }
    
    public static UIDefaults.ActiveValue createActiveFontValue(final float scaleFactor) {
        return new ActiveFont(scaleFactor);
    }
    
    public static void initIconColors(final UIDefaults defaults, final boolean dark) {
        for (final FlatIconColors c : FlatIconColors.values()) {
            if (c.light == !dark || c.dark == dark) {
                defaults.put(c.key, new ColorUIResource(c.rgb));
            }
        }
    }
    
    private void putAATextInfo(final UIDefaults defaults) {
        if (SystemInfo.isMacOS && SystemInfo.isJetBrainsJVM) {
            defaults.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        else if (SystemInfo.isJava_9_orLater) {
            Object desktopHints = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
            if (desktopHints == null) {
                desktopHints = this.fallbackAATextInfo();
            }
            if (desktopHints instanceof Map) {
                final Map<Object, Object> hints = (Map<Object, Object>)desktopHints;
                final Object aaHint = hints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
                if (aaHint != null && aaHint != RenderingHints.VALUE_TEXT_ANTIALIAS_OFF && aaHint != RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT) {
                    defaults.put(RenderingHints.KEY_TEXT_ANTIALIASING, aaHint);
                    defaults.put(RenderingHints.KEY_TEXT_LCD_CONTRAST, hints.get(RenderingHints.KEY_TEXT_LCD_CONTRAST));
                }
            }
        }
        else {
            try {
                final Object key = Class.forName("sun.swing.SwingUtilities2").getField("AA_TEXT_PROPERTY_KEY").get(null);
                Object value = Class.forName("sun.swing.SwingUtilities2$AATextInfo").getMethod("getAATextInfo", Boolean.TYPE).invoke(null, true);
                if (value == null) {
                    value = this.fallbackAATextInfo();
                }
                defaults.put(key, value);
            }
            catch (Exception ex) {
                LoggingFacade.INSTANCE.logSevere(null, ex);
                throw new RuntimeException(ex);
            }
        }
    }
    
    private Object fallbackAATextInfo() {
        if (System.getProperty("awt.useSystemAAFontSettings") != null) {
            return null;
        }
        Object aaHint = null;
        final Integer lcdContrastHint = null;
        if (SystemInfo.isLinux) {
            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            if (toolkit.getDesktopProperty("gnome.Xft/Antialias") == null && toolkit.getDesktopProperty("fontconfig/Antialias") == null) {
                aaHint = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
            }
        }
        if (aaHint == null) {
            return null;
        }
        if (SystemInfo.isJava_9_orLater) {
            final Map<Object, Object> hints = new HashMap<Object, Object>();
            hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, aaHint);
            hints.put(RenderingHints.KEY_TEXT_LCD_CONTRAST, lcdContrastHint);
            return hints;
        }
        try {
            return Class.forName("sun.swing.SwingUtilities2$AATextInfo").getConstructor(Object.class, Integer.class).newInstance(aaHint, lcdContrastHint);
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    private void putDefaults(final UIDefaults defaults, final Object value, final String... keys) {
        for (final String key : keys) {
            defaults.put(key, value);
        }
    }
    
    static List<Object> getCustomDefaultsSources() {
        return FlatLaf.customDefaultsSources;
    }
    
    public static void registerCustomDefaultsSource(final String packageName) {
        registerCustomDefaultsSource(packageName, null);
    }
    
    public static void unregisterCustomDefaultsSource(final String packageName) {
        unregisterCustomDefaultsSource(packageName, null);
    }
    
    public static void registerCustomDefaultsSource(final String packageName, final ClassLoader classLoader) {
        if (FlatLaf.customDefaultsSources == null) {
            FlatLaf.customDefaultsSources = new ArrayList<Object>();
        }
        FlatLaf.customDefaultsSources.add(packageName);
        FlatLaf.customDefaultsSources.add(classLoader);
    }
    
    public static void unregisterCustomDefaultsSource(final String packageName, final ClassLoader classLoader) {
        if (FlatLaf.customDefaultsSources == null) {
            return;
        }
        for (int size = FlatLaf.customDefaultsSources.size(), i = 0; i < size - 1; ++i) {
            final Object source = FlatLaf.customDefaultsSources.get(i);
            if (packageName.equals(source) && FlatLaf.customDefaultsSources.get(i + 1) == classLoader) {
                FlatLaf.customDefaultsSources.remove(i + 1);
                FlatLaf.customDefaultsSources.remove(i);
                break;
            }
        }
    }
    
    public static void registerCustomDefaultsSource(final File folder) {
        if (FlatLaf.customDefaultsSources == null) {
            FlatLaf.customDefaultsSources = new ArrayList<Object>();
        }
        FlatLaf.customDefaultsSources.add(folder);
    }
    
    public static void unregisterCustomDefaultsSource(final File folder) {
        if (FlatLaf.customDefaultsSources == null) {
            return;
        }
        FlatLaf.customDefaultsSources.remove(folder);
    }
    
    private static void reSetLookAndFeel() {
        EventQueue.invokeLater(() -> {
            final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(lookAndFeel);
                final PropertyChangeEvent e = new PropertyChangeEvent(UIManager.class, "lookAndFeel", lookAndFeel, lookAndFeel);
                UIManager.getPropertyChangeListeners();
                final PropertyChangeListener[] array;
                int i = 0;
                for (int length = array.length; i < length; ++i) {
                    final PropertyChangeListener l = array[i];
                    l.propertyChange(e);
                }
                updateUI();
            }
            catch (UnsupportedLookAndFeelException ex) {
                LoggingFacade.INSTANCE.logSevere("FlatLaf: Failed to reinitialize look and feel '" + lookAndFeel.getClass().getName() + "'.", ex);
            }
        });
    }
    
    public static void updateUI() {
        for (final Window w : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(w);
        }
    }
    
    public static void updateUILater() {
        synchronized (FlatLaf.class) {
            if (FlatLaf.updateUIPending) {
                return;
            }
            FlatLaf.updateUIPending = true;
        }
        EventQueue.invokeLater(() -> {
            updateUI();
            synchronized (FlatLaf.class) {
                FlatLaf.updateUIPending = false;
            }
        });
    }
    
    public static boolean supportsNativeWindowDecorations() {
        return SystemInfo.isWindows_10_orLater && FlatNativeWindowBorder.isSupported();
    }
    
    public static boolean isUseNativeWindowDecorations() {
        return UIManager.getBoolean("TitlePane.useWindowDecorations");
    }
    
    public static void setUseNativeWindowDecorations(final boolean enabled) {
        UIManager.put("TitlePane.useWindowDecorations", enabled);
        if (!(UIManager.getLookAndFeel() instanceof FlatLaf)) {
            return;
        }
        for (final Window w : Window.getWindows()) {
            if (isDisplayableFrameOrDialog(w)) {
                FlatRootPaneUI.updateNativeWindowBorder(((RootPaneContainer)w).getRootPane());
            }
        }
    }
    
    public static void revalidateAndRepaintAllFramesAndDialogs() {
        for (final Window w : Window.getWindows()) {
            if (isDisplayableFrameOrDialog(w)) {
                w.revalidate();
                w.repaint();
            }
        }
    }
    
    public static void repaintAllFramesAndDialogs() {
        for (final Window w : Window.getWindows()) {
            if (isDisplayableFrameOrDialog(w)) {
                w.repaint();
            }
        }
    }
    
    private static boolean isDisplayableFrameOrDialog(final Window w) {
        return w.isDisplayable() && (w instanceof JFrame || w instanceof JDialog);
    }
    
    public static boolean isShowMnemonics() {
        return MnemonicHandler.isShowMnemonics();
    }
    
    public static void showMnemonics(final Component c) {
        MnemonicHandler.showMnemonics(true, c);
    }
    
    public static void hideMnemonics() {
        MnemonicHandler.showMnemonics(false, null);
    }
    
    @Override
    public final boolean equals(final Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    private static class ActiveFont implements UIDefaults.ActiveValue
    {
        private final float scaleFactor;
        private Font font;
        private Font lastDefaultFont;
        
        ActiveFont(final float scaleFactor) {
            this.scaleFactor = scaleFactor;
        }
        
        @Override
        public Object createValue(final UIDefaults table) {
            Font defaultFont = UIManager.getFont("defaultFont");
            if (defaultFont == null) {
                defaultFont = UIManager.getFont("Label.font");
            }
            if (this.lastDefaultFont != defaultFont) {
                this.lastDefaultFont = defaultFont;
                if (this.scaleFactor != 1.0f) {
                    final int newFontSize = Math.round(defaultFont.getSize() * this.scaleFactor);
                    this.font = new FontUIResource(defaultFont.deriveFont((float)newFontSize));
                }
                else {
                    this.font = ((defaultFont instanceof UIResource) ? defaultFont : new FontUIResource(defaultFont));
                }
            }
            return this.font;
        }
    }
    
    private static class ImageIconUIResource extends ImageIcon implements UIResource
    {
        ImageIconUIResource(final Image image) {
            super(image);
        }
    }
    
    public interface DisabledIconProvider
    {
        Icon getDisabledIcon();
    }
}
