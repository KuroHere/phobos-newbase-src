// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import javax.swing.plaf.*;
import java.nio.charset.*;
import com.formdev.flatlaf.json.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.util.*;
import java.util.*;

public class IntelliJTheme
{
    public final String name;
    public final boolean dark;
    public final String author;
    private final boolean isMaterialUILite;
    private final Map<String, String> colors;
    private final Map<String, Object> ui;
    private final Map<String, Object> icons;
    private Map<String, ColorUIResource> namedColors;
    private static Map<String, String> uiKeyMapping;
    private static Map<String, String> uiKeyCopying;
    private static Map<String, String> uiKeyInverseMapping;
    private static Map<String, String> checkboxKeyMapping;
    private static Map<String, String> checkboxDuplicateColors;
    
    public static boolean setup(final InputStream in) {
        try {
            return FlatLaf.setup(createLaf(in));
        }
        catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere("FlatLaf: Failed to load IntelliJ theme", ex);
            return false;
        }
    }
    
    @Deprecated
    public static boolean install(final InputStream in) {
        return setup(in);
    }
    
    public static FlatLaf createLaf(final InputStream in) throws IOException {
        return createLaf(new IntelliJTheme(in));
    }
    
    public static FlatLaf createLaf(final IntelliJTheme theme) {
        return new ThemeLaf(theme);
    }
    
    public IntelliJTheme(final InputStream in) throws IOException {
        this.namedColors = Collections.emptyMap();
        Map<String, Object> json;
        try (final Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            json = (Map)Json.parse(reader);
        }
        catch (ParseException ex) {
            throw new IOException(ex.getMessage(), ex);
        }
        this.name = json.get("name");
        this.dark = Boolean.parseBoolean(json.get("dark"));
        this.author = json.get("author");
        this.isMaterialUILite = this.author.equals("Mallowigi");
        this.colors = json.get("colors");
        this.ui = json.get("ui");
        this.icons = json.get("icons");
    }
    
    private void applyProperties(final UIDefaults defaults) {
        if (this.ui == null) {
            return;
        }
        defaults.put("Component.isIntelliJTheme", true);
        defaults.put("Button.paintShadow", true);
        defaults.put("Button.shadowWidth", this.dark ? 2 : 1);
        final Map<Object, Object> themeSpecificDefaults = this.removeThemeSpecificDefaults(defaults);
        this.loadNamedColors(defaults);
        final ArrayList<Object> defaultsKeysCache = new ArrayList<Object>();
        final Set<String> uiKeys = new HashSet<String>();
        for (final Map.Entry<String, Object> e : this.ui.entrySet()) {
            this.apply(e.getKey(), e.getValue(), defaults, defaultsKeysCache, uiKeys);
        }
        this.applyColorPalette(defaults);
        this.applyCheckBoxColors(defaults);
        for (final Map.Entry<String, String> e2 : IntelliJTheme.uiKeyCopying.entrySet()) {
            defaults.put(e2.getKey(), defaults.get(e2.getValue()));
        }
        final Object panelBackground = defaults.get("Panel.background");
        defaults.put("Button.disabledBackground", panelBackground);
        defaults.put("ToggleButton.disabledBackground", panelBackground);
        this.copyIfNotSet(defaults, "Button.focusedBorderColor", "Component.focusedBorderColor", uiKeys);
        defaults.put("Button.hoverBorderColor", defaults.get("Button.focusedBorderColor"));
        defaults.put("HelpButton.hoverBorderColor", defaults.get("Button.focusedBorderColor"));
        Object helpButtonBackground = defaults.get("Button.startBackground");
        Object helpButtonBorderColor = defaults.get("Button.startBorderColor");
        if (helpButtonBackground == null) {
            helpButtonBackground = defaults.get("Button.background");
        }
        if (helpButtonBorderColor == null) {
            helpButtonBorderColor = defaults.get("Button.borderColor");
        }
        defaults.put("HelpButton.background", helpButtonBackground);
        defaults.put("HelpButton.borderColor", helpButtonBorderColor);
        defaults.put("HelpButton.disabledBackground", panelBackground);
        defaults.put("HelpButton.disabledBorderColor", defaults.get("Button.disabledBorderColor"));
        defaults.put("HelpButton.focusedBorderColor", defaults.get("Button.focusedBorderColor"));
        defaults.put("HelpButton.focusedBackground", defaults.get("Button.focusedBackground"));
        defaults.put("ComboBox.editableBackground", defaults.get("TextField.background"));
        defaults.put("Spinner.background", defaults.get("TextField.background"));
        defaults.put("Spinner.buttonBackground", defaults.get("ComboBox.buttonEditableBackground"));
        defaults.put("Spinner.buttonArrowColor", defaults.get("ComboBox.buttonArrowColor"));
        defaults.put("Spinner.buttonDisabledArrowColor", defaults.get("ComboBox.buttonDisabledArrowColor"));
        if (uiKeys.contains("TextField.background")) {
            final Object textFieldBackground = defaults.get("TextField.background");
            if (!uiKeys.contains("FormattedTextField.background")) {
                defaults.put("FormattedTextField.background", textFieldBackground);
            }
            if (!uiKeys.contains("PasswordField.background")) {
                defaults.put("PasswordField.background", textFieldBackground);
            }
            if (!uiKeys.contains("EditorPane.background")) {
                defaults.put("EditorPane.background", textFieldBackground);
            }
            if (!uiKeys.contains("TextArea.background")) {
                defaults.put("TextArea.background", textFieldBackground);
            }
            if (!uiKeys.contains("TextPane.background")) {
                defaults.put("TextPane.background", textFieldBackground);
            }
            if (!uiKeys.contains("Spinner.background")) {
                defaults.put("Spinner.background", textFieldBackground);
            }
        }
        if (!uiKeys.contains("ToggleButton.startBackground") && !uiKeys.contains("*.startBackground")) {
            defaults.put("ToggleButton.startBackground", defaults.get("Button.startBackground"));
        }
        if (!uiKeys.contains("ToggleButton.endBackground") && !uiKeys.contains("*.endBackground")) {
            defaults.put("ToggleButton.endBackground", defaults.get("Button.endBackground"));
        }
        if (!uiKeys.contains("ToggleButton.foreground") && uiKeys.contains("Button.foreground")) {
            defaults.put("ToggleButton.foreground", defaults.get("Button.foreground"));
        }
        final Color desktopBackgroundBase = defaults.getColor("Panel.background");
        final Color desktopBackground = ColorFunctions.applyFunctions(desktopBackgroundBase, new ColorFunctions.HSLIncreaseDecrease(2, this.dark, 5.0f, false, true));
        defaults.put("Desktop.background", new ColorUIResource(desktopBackground));
        if (this.isMaterialUILite) {
            defaults.put("List.background", defaults.get("Tree.background"));
            defaults.put("Table.background", defaults.get("Tree.background"));
        }
        final int rowHeight = defaults.getInt("Tree.rowHeight");
        if (rowHeight > 22) {
            defaults.put("Tree.rowHeight", 22);
        }
        defaults.putAll(themeSpecificDefaults);
    }
    
    private Map<Object, Object> removeThemeSpecificDefaults(final UIDefaults defaults) {
        final ArrayList<String> themeSpecificKeys = new ArrayList<String>();
        for (final Object key : ((Hashtable<Object, V>)defaults).keySet()) {
            if (key instanceof String && ((String)key).startsWith("[")) {
                themeSpecificKeys.add((String)key);
            }
        }
        final Map<Object, Object> themeSpecificDefaults = new HashMap<Object, Object>();
        final String currentThemePrefix = '[' + this.name.replace(' ', '_') + ']';
        final String currentThemeAndAuthorPrefix = '[' + this.name.replace(' ', '_') + "---" + this.author.replace(' ', '_') + ']';
        final String currentAuthorPrefix = "[author-" + this.author.replace(' ', '_') + ']';
        final String allThemesPrefix = "[*]";
        final String[] prefixes = { currentThemePrefix, currentThemeAndAuthorPrefix, currentAuthorPrefix, allThemesPrefix };
        for (final String key2 : themeSpecificKeys) {
            final Object value = ((Hashtable<K, Object>)defaults).remove(key2);
            for (final String prefix : prefixes) {
                if (key2.startsWith(prefix)) {
                    themeSpecificDefaults.put(key2.substring(prefix.length()), value);
                    break;
                }
            }
        }
        return themeSpecificDefaults;
    }
    
    private void loadNamedColors(final UIDefaults defaults) {
        if (this.colors == null) {
            return;
        }
        this.namedColors = new HashMap<String, ColorUIResource>();
        for (final Map.Entry<String, String> e : this.colors.entrySet()) {
            final String value = e.getValue();
            final ColorUIResource color = UIDefaultsLoader.parseColor(value);
            if (color != null) {
                final String key = e.getKey();
                this.namedColors.put(key, color);
                defaults.put("ColorPalette." + key, color);
            }
        }
    }
    
    private void apply(String key, final Object value, final UIDefaults defaults, final ArrayList<Object> defaultsKeysCache, final Set<String> uiKeys) {
        if (value instanceof Map) {
            for (final Map.Entry<String, Object> e : ((Map)value).entrySet()) {
                this.apply(key + '.' + e.getKey(), e.getValue(), defaults, defaultsKeysCache, uiKeys);
            }
        }
        else {
            if ("".equals(value)) {
                return;
            }
            uiKeys.add(key);
            if (this.isMaterialUILite && (key.equals("ComboBox.padding") || key.equals("Spinner.border"))) {
                return;
            }
            key = IntelliJTheme.uiKeyMapping.getOrDefault(key, key);
            if (key.isEmpty()) {
                return;
            }
            String valueStr = value.toString();
            Object uiValue = this.namedColors.get(valueStr);
            if (uiValue == null) {
                if (!valueStr.startsWith("#") && (key.endsWith("ground") || key.endsWith("Color"))) {
                    valueStr = this.fixColorIfValid("#" + valueStr, valueStr);
                }
                else if (valueStr.startsWith("##")) {
                    valueStr = this.fixColorIfValid(valueStr.substring(1), valueStr);
                }
                else if (key.endsWith(".border") || key.endsWith("Border")) {
                    final List<String> parts = StringUtils.split(valueStr, ',');
                    if (parts.size() == 5 && !parts.get(4).startsWith("#")) {
                        parts.set(4, "#" + parts.get(4));
                        valueStr = String.join(",", parts);
                    }
                }
                try {
                    uiValue = UIDefaultsLoader.parseValue(key, valueStr);
                }
                catch (RuntimeException ex) {
                    UIDefaultsLoader.logParseError(key, valueStr, ex, false);
                    return;
                }
            }
            if (key.startsWith("*.")) {
                final String tail = key.substring(1);
                if (defaultsKeysCache.size() != defaults.size()) {
                    defaultsKeysCache.clear();
                    final Enumeration<Object> e2 = ((Hashtable<Object, V>)defaults).keys();
                    while (e2.hasMoreElements()) {
                        defaultsKeysCache.add(e2.nextElement());
                    }
                }
                for (final Object k : defaultsKeysCache) {
                    if (!k.equals("Desktop.background")) {
                        if (k.equals("DesktopIcon.background")) {
                            continue;
                        }
                        if (!(k instanceof String)) {
                            continue;
                        }
                        final String km = IntelliJTheme.uiKeyInverseMapping.getOrDefault(k, (String)k);
                        if (!km.endsWith(tail) || ((String)k).startsWith("CheckBox.icon.")) {
                            continue;
                        }
                        defaults.put(k, uiValue);
                    }
                }
            }
            else {
                defaults.put(key, uiValue);
            }
        }
    }
    
    private String fixColorIfValid(final String newColorStr, final String colorStr) {
        try {
            UIDefaultsLoader.parseColorRGBA(newColorStr);
            return newColorStr;
        }
        catch (IllegalArgumentException ex) {
            return colorStr;
        }
    }
    
    private void applyColorPalette(final UIDefaults defaults) {
        if (this.icons == null) {
            return;
        }
        final Object palette = this.icons.get("ColorPalette");
        if (!(palette instanceof Map)) {
            return;
        }
        final Map<String, Object> colorPalette = (Map<String, Object>)palette;
        for (final Map.Entry<String, Object> e : colorPalette.entrySet()) {
            String key = e.getKey();
            final Object value = e.getValue();
            if (!key.startsWith("Checkbox.")) {
                if (!(value instanceof String)) {
                    continue;
                }
                if (this.dark) {
                    key = StringUtils.removeTrailing(key, ".Dark");
                }
                final ColorUIResource color = this.toColor((String)value);
                if (color == null) {
                    continue;
                }
                defaults.put(key, color);
            }
        }
    }
    
    private ColorUIResource toColor(final String value) {
        final ColorUIResource color = this.namedColors.get(value);
        return (color != null) ? color : UIDefaultsLoader.parseColor(value);
    }
    
    private void applyCheckBoxColors(final UIDefaults defaults) {
        if (this.icons == null) {
            return;
        }
        final Object palette = this.icons.get("ColorPalette");
        if (!(palette instanceof Map)) {
            return;
        }
        boolean checkboxModified = false;
        final Map<String, Object> colorPalette = (Map<String, Object>)palette;
        for (final Map.Entry<String, Object> e : colorPalette.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (key.startsWith("Checkbox.")) {
                if (!(value instanceof String)) {
                    continue;
                }
                if (key.equals("Checkbox.Background.Default") || key.equals("Checkbox.Foreground.Selected")) {
                    value = "#ffffff";
                }
                String key2 = IntelliJTheme.checkboxDuplicateColors.get(key);
                if (this.dark) {
                    key = StringUtils.removeTrailing(key, ".Dark");
                }
                String newKey = IntelliJTheme.checkboxKeyMapping.get(key);
                if (newKey == null) {
                    continue;
                }
                final String checkBoxIconPrefix = "CheckBox.icon.";
                if (!this.dark && newKey.startsWith(checkBoxIconPrefix)) {
                    newKey = "CheckBox.icon[filled].".concat(newKey.substring(checkBoxIconPrefix.length()));
                }
                final ColorUIResource color = this.toColor((String)value);
                if (color != null) {
                    defaults.put(newKey, color);
                    if (key2 != null) {
                        if (this.dark) {
                            key2 = StringUtils.removeTrailing(key2, ".Dark");
                        }
                        final String newKey2 = IntelliJTheme.checkboxKeyMapping.get(key2);
                        if (newKey2 != null) {
                            defaults.put(newKey2, color);
                        }
                    }
                }
                checkboxModified = true;
            }
        }
        if (checkboxModified) {
            defaults.remove("CheckBox.icon.focusWidth");
            defaults.put("CheckBox.icon.hoverBorderColor", defaults.get("CheckBox.icon.focusedBorderColor"));
            defaults.remove("CheckBox.icon[filled].focusWidth");
            defaults.put("CheckBox.icon[filled].hoverBorderColor", defaults.get("CheckBox.icon[filled].focusedBorderColor"));
            defaults.put("CheckBox.icon[filled].selectedFocusedBackground", defaults.get("CheckBox.icon[filled].selectedBackground"));
            if (this.dark) {
                final String[] array;
                final String[] focusedBorderColorKeys = array = new String[] { "CheckBox.icon.focusedBorderColor", "CheckBox.icon.selectedFocusedBorderColor", "CheckBox.icon[filled].focusedBorderColor", "CheckBox.icon[filled].selectedFocusedBorderColor" };
                for (final String key3 : array) {
                    final Color color2 = defaults.getColor(key3);
                    if (color2 != null) {
                        defaults.put(key3, new ColorUIResource(new Color((color2.getRGB() & 0xFFFFFF) | 0xA6000000, true)));
                    }
                }
            }
        }
    }
    
    private void copyIfNotSet(final UIDefaults defaults, final String destKey, final String srcKey, final Set<String> uiKeys) {
        if (!uiKeys.contains(destKey)) {
            defaults.put(destKey, defaults.get(srcKey));
        }
    }
    
    static {
        IntelliJTheme.uiKeyMapping = new HashMap<String, String>();
        IntelliJTheme.uiKeyCopying = new HashMap<String, String>();
        IntelliJTheme.uiKeyInverseMapping = new HashMap<String, String>();
        IntelliJTheme.checkboxKeyMapping = new HashMap<String, String>();
        IntelliJTheme.checkboxDuplicateColors = new HashMap<String, String>();
        IntelliJTheme.uiKeyMapping.put("ComboBox.background", "");
        IntelliJTheme.uiKeyMapping.put("ComboBox.nonEditableBackground", "ComboBox.background");
        IntelliJTheme.uiKeyMapping.put("ComboBox.ArrowButton.background", "ComboBox.buttonEditableBackground");
        IntelliJTheme.uiKeyMapping.put("ComboBox.ArrowButton.disabledIconColor", "ComboBox.buttonDisabledArrowColor");
        IntelliJTheme.uiKeyMapping.put("ComboBox.ArrowButton.iconColor", "ComboBox.buttonArrowColor");
        IntelliJTheme.uiKeyMapping.put("ComboBox.ArrowButton.nonEditableBackground", "ComboBox.buttonBackground");
        IntelliJTheme.uiKeyMapping.put("Component.inactiveErrorFocusColor", "Component.error.borderColor");
        IntelliJTheme.uiKeyMapping.put("Component.errorFocusColor", "Component.error.focusedBorderColor");
        IntelliJTheme.uiKeyMapping.put("Component.inactiveWarningFocusColor", "Component.warning.borderColor");
        IntelliJTheme.uiKeyMapping.put("Component.warningFocusColor", "Component.warning.focusedBorderColor");
        IntelliJTheme.uiKeyMapping.put("Link.activeForeground", "Component.linkColor");
        IntelliJTheme.uiKeyMapping.put("Menu.border", "Menu.margin");
        IntelliJTheme.uiKeyMapping.put("MenuItem.border", "MenuItem.margin");
        IntelliJTheme.uiKeyCopying.put("CheckBoxMenuItem.margin", "MenuItem.margin");
        IntelliJTheme.uiKeyCopying.put("RadioButtonMenuItem.margin", "MenuItem.margin");
        IntelliJTheme.uiKeyMapping.put("PopupMenu.border", "PopupMenu.borderInsets");
        IntelliJTheme.uiKeyCopying.put("MenuItem.underlineSelectionColor", "TabbedPane.underlineColor");
        IntelliJTheme.uiKeyCopying.put("Menu.selectionBackground", "List.selectionBackground");
        IntelliJTheme.uiKeyCopying.put("MenuItem.selectionBackground", "List.selectionBackground");
        IntelliJTheme.uiKeyCopying.put("CheckBoxMenuItem.selectionBackground", "List.selectionBackground");
        IntelliJTheme.uiKeyCopying.put("RadioButtonMenuItem.selectionBackground", "List.selectionBackground");
        IntelliJTheme.uiKeyMapping.put("ProgressBar.background", "");
        IntelliJTheme.uiKeyMapping.put("ProgressBar.foreground", "");
        IntelliJTheme.uiKeyMapping.put("ProgressBar.trackColor", "ProgressBar.background");
        IntelliJTheme.uiKeyMapping.put("ProgressBar.progressColor", "ProgressBar.foreground");
        IntelliJTheme.uiKeyCopying.put("ProgressBar.selectionForeground", "ProgressBar.background");
        IntelliJTheme.uiKeyCopying.put("ProgressBar.selectionBackground", "ProgressBar.foreground");
        IntelliJTheme.uiKeyMapping.put("ScrollBar.trackColor", "ScrollBar.track");
        IntelliJTheme.uiKeyMapping.put("ScrollBar.thumbColor", "ScrollBar.thumb");
        IntelliJTheme.uiKeyMapping.put("Separator.separatorColor", "Separator.foreground");
        IntelliJTheme.uiKeyMapping.put("Slider.trackWidth", "");
        IntelliJTheme.uiKeyCopying.put("Slider.trackValueColor", "ProgressBar.foreground");
        IntelliJTheme.uiKeyCopying.put("Slider.thumbColor", "ProgressBar.foreground");
        IntelliJTheme.uiKeyCopying.put("Slider.trackColor", "ProgressBar.background");
        IntelliJTheme.uiKeyCopying.put("TitlePane.inactiveBackground", "TitlePane.background");
        IntelliJTheme.uiKeyMapping.put("TitlePane.infoForeground", "TitlePane.foreground");
        IntelliJTheme.uiKeyMapping.put("TitlePane.inactiveInfoForeground", "TitlePane.inactiveForeground");
        for (final Map.Entry<String, String> e : IntelliJTheme.uiKeyMapping.entrySet()) {
            IntelliJTheme.uiKeyInverseMapping.put(e.getValue(), e.getKey());
        }
        IntelliJTheme.uiKeyCopying.put("ToggleButton.tab.underlineColor", "TabbedPane.underlineColor");
        IntelliJTheme.uiKeyCopying.put("ToggleButton.tab.disabledUnderlineColor", "TabbedPane.disabledUnderlineColor");
        IntelliJTheme.uiKeyCopying.put("ToggleButton.tab.selectedBackground", "TabbedPane.selectedBackground");
        IntelliJTheme.uiKeyCopying.put("ToggleButton.tab.hoverBackground", "TabbedPane.hoverColor");
        IntelliJTheme.uiKeyCopying.put("ToggleButton.tab.focusBackground", "TabbedPane.focusColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Background.Default", "CheckBox.icon.background");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Background.Disabled", "CheckBox.icon.disabledBackground");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Border.Default", "CheckBox.icon.borderColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Border.Disabled", "CheckBox.icon.disabledBorderColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Focus.Thin.Default", "CheckBox.icon.focusedBorderColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Focus.Wide", "CheckBox.icon.focusColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Foreground.Disabled", "CheckBox.icon.disabledCheckmarkColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Background.Selected", "CheckBox.icon.selectedBackground");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Border.Selected", "CheckBox.icon.selectedBorderColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Foreground.Selected", "CheckBox.icon.checkmarkColor");
        IntelliJTheme.checkboxKeyMapping.put("Checkbox.Focus.Thin.Selected", "CheckBox.icon.selectedFocusedBorderColor");
        IntelliJTheme.checkboxDuplicateColors.put("Checkbox.Background.Default.Dark", "Checkbox.Background.Selected.Dark");
        IntelliJTheme.checkboxDuplicateColors.put("Checkbox.Border.Default.Dark", "Checkbox.Border.Selected.Dark");
        IntelliJTheme.checkboxDuplicateColors.put("Checkbox.Focus.Thin.Default.Dark", "Checkbox.Focus.Thin.Selected.Dark");
        final Map.Entry[] array;
        final Map.Entry<String, String>[] entries = array = IntelliJTheme.checkboxDuplicateColors.entrySet().toArray(new Map.Entry[IntelliJTheme.checkboxDuplicateColors.size()]);
        for (final Map.Entry<String, String> e2 : array) {
            IntelliJTheme.checkboxDuplicateColors.put(e2.getValue(), e2.getKey());
        }
    }
    
    public static class ThemeLaf extends FlatLaf
    {
        private final IntelliJTheme theme;
        
        public ThemeLaf(final IntelliJTheme theme) {
            this.theme = theme;
        }
        
        @Override
        public String getName() {
            return this.theme.name;
        }
        
        @Override
        public String getDescription() {
            return this.getName();
        }
        
        @Override
        public boolean isDark() {
            return this.theme.dark;
        }
        
        public IntelliJTheme getTheme() {
            return this.theme;
        }
        
        @Override
        void applyAdditionalDefaults(final UIDefaults defaults) {
            this.theme.applyProperties(defaults);
        }
        
        @Override
        protected ArrayList<Class<?>> getLafClassesForDefaultsLoading() {
            final ArrayList<Class<?>> lafClasses = new ArrayList<Class<?>>();
            lafClasses.add(FlatLaf.class);
            lafClasses.add((Class<?>)(this.theme.dark ? FlatDarkLaf.class : FlatLightLaf.class));
            lafClasses.add((Class<?>)(this.theme.dark ? FlatDarculaLaf.class : FlatIntelliJLaf.class));
            lafClasses.add(ThemeLaf.class);
            return lafClasses;
        }
    }
}
