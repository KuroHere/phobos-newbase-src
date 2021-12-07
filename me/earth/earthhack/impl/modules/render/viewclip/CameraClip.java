// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.viewclip;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class CameraClip extends Module
{
    public CameraClip() {
        super("CameraClip", Category.Render);
        this.register(new BooleanSetting("Extend", false));
        this.register(new NumberSetting("Distance", 10.0, 0.0, 50.0));
        this.setData(new SimpleData(this, "Makes the camera clip through blocks in F5."));
    }
}
