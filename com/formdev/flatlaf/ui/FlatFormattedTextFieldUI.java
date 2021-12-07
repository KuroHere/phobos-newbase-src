// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import javax.swing.plaf.*;

public class FlatFormattedTextFieldUI extends FlatTextFieldUI
{
    public static ComponentUI createUI(final JComponent c) {
        return new FlatFormattedTextFieldUI();
    }
    
    @Override
    protected String getPropertyPrefix() {
        return "FormattedTextField";
    }
}
