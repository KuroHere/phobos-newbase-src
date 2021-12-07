// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import javax.swing.*;

public class FlatIntelliJLaf extends FlatLightLaf
{
    public static final String NAME = "FlatLaf IntelliJ";
    
    public static boolean setup() {
        return FlatLaf.setup(new FlatIntelliJLaf());
    }
    
    @Deprecated
    public static boolean install() {
        return setup();
    }
    
    public static void installLafInfo() {
        FlatLaf.installLafInfo("FlatLaf IntelliJ", FlatIntelliJLaf.class);
    }
    
    @Override
    public String getName() {
        return "FlatLaf IntelliJ";
    }
    
    @Override
    public String getDescription() {
        return "FlatLaf IntelliJ Look and Feel";
    }
}
