// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import java.io.*;
import java.util.*;

public class FlatPropertiesLaf extends FlatLaf
{
    private final String name;
    private final String baseTheme;
    private final boolean dark;
    private final Properties properties;
    
    public FlatPropertiesLaf(final String name, final File propertiesFile) throws IOException {
        this(name, new FileInputStream(propertiesFile));
    }
    
    public FlatPropertiesLaf(final String name, final InputStream in) throws IOException {
        this(name, loadProperties(in));
    }
    
    private static Properties loadProperties(final InputStream in) throws IOException {
        final Properties properties = new Properties();
        try (final InputStream in2 = in) {
            properties.load(in2);
        }
        return properties;
    }
    
    public FlatPropertiesLaf(final String name, final Properties properties) {
        this.name = name;
        this.properties = properties;
        this.baseTheme = properties.getProperty("@baseTheme", "light");
        this.dark = ("dark".equalsIgnoreCase(this.baseTheme) || "darcula".equalsIgnoreCase(this.baseTheme));
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getDescription() {
        return this.name;
    }
    
    @Override
    public boolean isDark() {
        return this.dark;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    @Override
    protected ArrayList<Class<?>> getLafClassesForDefaultsLoading() {
        final ArrayList<Class<?>> lafClasses = new ArrayList<Class<?>>();
        lafClasses.add(FlatLaf.class);
        final String lowerCase = this.baseTheme.toLowerCase();
        switch (lowerCase) {
            default: {
                lafClasses.add(FlatLightLaf.class);
                break;
            }
            case "dark": {
                lafClasses.add(FlatDarkLaf.class);
                break;
            }
            case "intellij": {
                lafClasses.add(FlatLightLaf.class);
                lafClasses.add(FlatIntelliJLaf.class);
                break;
            }
            case "darcula": {
                lafClasses.add(FlatDarkLaf.class);
                lafClasses.add(FlatDarculaLaf.class);
                break;
            }
        }
        return lafClasses;
    }
    
    @Override
    protected Properties getAdditionalDefaults() {
        return this.properties;
    }
}
