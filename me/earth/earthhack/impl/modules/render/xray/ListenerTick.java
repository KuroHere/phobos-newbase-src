//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.xray;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;

final class ListenerTick extends ModuleListener<XRay, TickEvent>
{
    private int lastOpacity;
    
    public ListenerTick(final XRay module) {
        super(module, (Class<? super Object>)TickEvent.class);
        this.lastOpacity = 0;
    }
    
    public void invoke(final TickEvent event) {
        if (((XRay)this.module).getMode() == XrayMode.Opacity) {
            if (this.lastOpacity != ((XRay)this.module).getOpacity()) {
                ((XRay)this.module).loadRenderers();
                this.lastOpacity = ((XRay)this.module).getOpacity();
            }
            ListenerTick.mc.gameSettings.gammaSetting = 11.0f;
        }
    }
}
