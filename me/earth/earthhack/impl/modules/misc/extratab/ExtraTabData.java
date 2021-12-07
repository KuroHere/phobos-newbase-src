// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.extratab;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class ExtraTabData extends DefaultData<ExtraTab>
{
    public ExtraTabData(final ExtraTab module) {
        super(module);
        this.register(module.size, "How many players you want to display when pressing tab.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Extends the tab menu.";
    }
}
