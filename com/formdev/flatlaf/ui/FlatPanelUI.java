// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.util.function.*;

public class FlatPanelUI extends BasicPanelUI
{
    public static ComponentUI createUI(final JComponent c) {
        return FlatUIUtils.createSharedUI(FlatPanelUI.class, (Supplier<ComponentUI>)FlatPanelUI::new);
    }
}
