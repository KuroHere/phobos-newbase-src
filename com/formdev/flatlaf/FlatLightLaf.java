// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import javax.swing.*;

public class FlatLightLaf extends FlatLaf
{
    public static final String NAME = "FlatLaf Light";
    
    public static boolean setup() {
        return FlatLaf.setup(new FlatLightLaf());
    }
    
    @Deprecated
    public static boolean install() {
        return setup();
    }
    
    public static void installLafInfo() {
        FlatLaf.installLafInfo("FlatLaf Light", FlatLightLaf.class);
    }
    
    @Override
    public String getName() {
        return "FlatLaf Light";
    }
    
    @Override
    public String getDescription() {
        return "FlatLaf Light Look and Feel";
    }
    
    @Override
    public boolean isDark() {
        return false;
    }
}
