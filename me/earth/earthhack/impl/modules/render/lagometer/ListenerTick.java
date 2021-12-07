// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerTick extends ModuleListener<LagOMeter, TickEvent>
{
    public ListenerTick(final LagOMeter module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((LagOMeter)this.module).lagTime.getValue() || ((LagOMeter)this.module).response.getValue()) {
            ((LagOMeter)this.module).resolution = new ScaledResolution(ListenerTick.mc);
        }
        if (!((LagOMeter)this.module).chat.getValue()) {
            if (((LagOMeter)this.module).sent) {
                Managers.CHAT.deleteMessage("Lag-O-Meter-Res", 2000);
                Managers.CHAT.deleteMessage("Lag-O-Meter-Lag", 2000);
                ((LagOMeter)this.module).sent = false;
            }
            return;
        }
        final boolean sent = ((LagOMeter)this.module).sent;
        ((LagOMeter)this.module).sent = false;
        if (((LagOMeter)this.module).respondingMessage != null) {
            ((LagOMeter)this.module).sent = true;
            Managers.CHAT.sendDeleteMessage("<" + ((LagOMeter)this.module).getDisplayName() + "> " + ((LagOMeter)this.module).respondingMessage + ".", "Lag-O-Meter-Res", 2000);
        }
        if (((LagOMeter)this.module).lagMessage != null) {
            ((LagOMeter)this.module).sent = true;
            Managers.CHAT.sendDeleteMessage("<" + ((LagOMeter)this.module).getDisplayName() + "> " + ((LagOMeter)this.module).lagMessage + ".", "Lag-O-Meter-Lag", 2000);
        }
        if (sent && !((LagOMeter)this.module).sent) {
            Managers.CHAT.deleteMessage("Lag-O-Meter-Res", 2000);
            Managers.CHAT.deleteMessage("Lag-O-Meter-Lag", 2000);
        }
    }
}
