// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.media;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class MediaData extends DefaultData<Media>
{
    protected MediaData(final Media module) {
        super(module);
        this.register("Replacement", "The name you want to appear with.");
        this.register("Reload", "Use this in case you changed your name using an altmanager.");
        this.register(module.replaceCustom, "Renders custom names.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Allows you to protect your name when recording or streaming.";
    }
}
