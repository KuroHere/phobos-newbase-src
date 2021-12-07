//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.popchams;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerTotemPop extends ModuleListener<PopChams, TotemPopEvent>
{
    public ListenerTotemPop(final PopChams module) {
        super(module, (Class<? super Object>)TotemPopEvent.class);
    }
    
    public void invoke(final TotemPopEvent event) {
        if (!((PopChams)this.module).isValidEntity(event.getEntity())) {
            return;
        }
        ((PopChams)this.module).getPopDataHashMap().put(event.getEntity().getName(), new PopChams.PopData(event.getEntity(), System.currentTimeMillis(), event.getEntity().rotationYaw, event.getEntity().rotationPitch, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ));
    }
}
