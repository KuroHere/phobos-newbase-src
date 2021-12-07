// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.*;
import javax.swing.plaf.*;
import java.util.function.*;

public class FlatPopupMenuSeparatorUI extends FlatSeparatorUI
{
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatPopupMenuSeparatorUI.class, (Supplier<ComponentUI>)FlatPopupMenuSeparatorUI::new);
    }
    
    @Override
    protected String getPropertyPrefix() {
        return "PopupMenuSeparator";
    }
}
