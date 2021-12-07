// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import javax.swing.plaf.*;
import java.util.function.*;

public class FlatCheckBoxUI extends FlatRadioButtonUI
{
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatCheckBoxUI.class, (Supplier<ComponentUI>)FlatCheckBoxUI::new);
    }
    
    public String getPropertyPrefix() {
        return "CheckBox.";
    }
}
