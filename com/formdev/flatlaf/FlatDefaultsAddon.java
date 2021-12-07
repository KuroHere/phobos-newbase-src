// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import java.io.*;
import javax.swing.*;

public abstract class FlatDefaultsAddon
{
    public InputStream getDefaults(final Class<?> lafClass) {
        final Class<?> addonClass = this.getClass();
        final String propertiesName = '/' + addonClass.getPackage().getName().replace('.', '/') + '/' + lafClass.getSimpleName() + ".properties";
        return addonClass.getResourceAsStream(propertiesName);
    }
    
    public void afterDefaultsLoading(final LookAndFeel laf, final UIDefaults defaults) {
    }
    
    public int getPriority() {
        return 10000;
    }
}
