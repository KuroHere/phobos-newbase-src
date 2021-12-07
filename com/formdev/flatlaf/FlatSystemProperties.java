// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

public interface FlatSystemProperties
{
    public static final String UI_SCALE = "flatlaf.uiScale";
    public static final String UI_SCALE_ENABLED = "flatlaf.uiScale.enabled";
    public static final String UI_SCALE_ALLOW_SCALE_DOWN = "flatlaf.uiScale.allowScaleDown";
    public static final String USE_UBUNTU_FONT = "flatlaf.useUbuntuFont";
    public static final String USE_WINDOW_DECORATIONS = "flatlaf.useWindowDecorations";
    public static final String USE_JETBRAINS_CUSTOM_DECORATIONS = "flatlaf.useJetBrainsCustomDecorations";
    public static final String MENUBAR_EMBEDDED = "flatlaf.menuBarEmbedded";
    public static final String ANIMATION = "flatlaf.animation";
    public static final String USE_TEXT_Y_CORRECTION = "flatlaf.useTextYCorrection";
    
    default boolean getBoolean(final String key, final boolean defaultValue) {
        final String value = System.getProperty(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    default Boolean getBooleanStrict(final String key, final Boolean defaultValue) {
        final String value = System.getProperty(key);
        if ("true".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        }
        return defaultValue;
    }
}
