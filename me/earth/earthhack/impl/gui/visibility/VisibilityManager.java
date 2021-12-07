// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.visibility;

import me.earth.earthhack.api.setting.*;
import java.util.*;

public class VisibilityManager
{
    private static final VisibilitySupplier ALWAYS;
    private final Map<Setting<?>, VisibilitySupplier> visibilities;
    
    public VisibilityManager() {
        this.visibilities = new HashMap<Setting<?>, VisibilitySupplier>();
    }
    
    public VisibilitySupplier getVisibility(final Setting<?> setting) {
        return this.visibilities.getOrDefault(setting, VisibilityManager.ALWAYS);
    }
    
    public void registerVisibility(final Setting<?> setting, final VisibilitySupplier visibility) {
        if (visibility == null) {
            this.visibilities.remove(setting);
            return;
        }
        this.visibilities.compute(setting, (k, v) -> {
            if (v == null) {
                return visibility;
            }
            else {
                return visibility.compose(v);
            }
        });
    }
    
    public boolean isVisible(final Setting<?> setting) {
        return this.getVisibility(setting).isVisible();
    }
    
    static {
        ALWAYS = (() -> true);
    }
}
