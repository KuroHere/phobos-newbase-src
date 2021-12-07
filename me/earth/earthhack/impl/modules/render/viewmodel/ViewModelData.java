// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.viewmodel;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class ViewModelData extends DefaultData<ViewModel>
{
    public ViewModelData(final ViewModel module) {
        super(module);
        this.register(module.offX, "Rotates your Offhand around on the X-Axis.");
        this.register(module.offY, "Set the height of your Offhand.");
        this.register(module.mainX, "Rotates your mainhand around on the X-Axis.");
        this.register(module.mainY, "Set the height of your mainhand.");
        this.register(module.xScale, "Lower values mean off- and mainhand are further together.");
        this.register(module.yScale, "Lower values mean off- and mainhand are higher.");
        this.register(module.zScale, "Lower values mean off- and mainhand are closer to you.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Allows you to change your Viewmodel.";
    }
}
