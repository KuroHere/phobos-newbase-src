// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import javax.swing.border.*;
import java.awt.*;

public class ScaledEmptyBorder extends EmptyBorder
{
    public ScaledEmptyBorder(final int top, final int left, final int bottom, final int right) {
        super(top, left, bottom, right);
    }
    
    public ScaledEmptyBorder(final Insets insets) {
        super(insets);
    }
    
    @Override
    public Insets getBorderInsets() {
        return new Insets(UIScale.scale(this.top), UIScale.scale(this.left), UIScale.scale(this.bottom), UIScale.scale(this.right));
    }
    
    @Override
    public Insets getBorderInsets(final Component c, final Insets insets) {
        insets.left = UIScale.scale(this.left);
        insets.top = UIScale.scale(this.top);
        insets.right = UIScale.scale(this.right);
        insets.bottom = UIScale.scale(this.bottom);
        return insets;
    }
}
