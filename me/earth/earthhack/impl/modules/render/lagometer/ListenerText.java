//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;

final class ListenerText extends ModuleListener<LagOMeter, Render2DEvent>
{
    public ListenerText(final LagOMeter module) {
        super(module, (Class<? super Object>)Render2DEvent.class);
    }
    
    public void invoke(final Render2DEvent event) {
        if (!((LagOMeter)this.module).response.getValue() && !((LagOMeter)this.module).lagTime.getValue()) {
            return;
        }
        ((LagOMeter)this.module).lagMessage = null;
        ((LagOMeter)this.module).respondingMessage = null;
        if (((LagOMeter)this.module).response.getValue() && Managers.SERVER.lastResponse() > ((LagOMeter)this.module).responseTime.getValue()) {
            ((LagOMeter)this.module).respondingMessage = "§cServer not responding. (" + MathUtil.round(Managers.SERVER.lastResponse() / 1000.0, 1) + ")";
        }
        if (((LagOMeter)this.module).lagTime.getValue()) {
            final long time = ((LagOMeter)this.module).chatTime.getValue() - (System.currentTimeMillis() - Managers.NCP.getTimeStamp());
            if (time >= 0L) {
                ((LagOMeter)this.module).lagMessage = "§cServer lagged you back (" + MathUtil.round(time / 1000.0, 1) + ")";
            }
        }
        String toRender = ((LagOMeter)this.module).respondingMessage;
        if (toRender == null) {
            toRender = ((LagOMeter)this.module).lagMessage;
        }
        if (toRender == null || !((LagOMeter)this.module).render.getValue()) {
            return;
        }
        Managers.TEXT.drawString(toRender, ((LagOMeter)this.module).resolution.getScaledWidth() / 2.0f - Managers.TEXT.getStringWidth(toRender) / 2.0f + 2.0f, 20.0f, -1, true);
    }
}
