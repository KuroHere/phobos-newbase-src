//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.reach;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerReach extends ModuleListener<Reach, ReachEvent>
{
    public ListenerReach(final Reach module) {
        super(module, (Class<? super Object>)ReachEvent.class);
    }
    
    public void invoke(final ReachEvent event) {
        if (ListenerReach.mc.getRenderViewEntity() != null && ListenerReach.mc.world != null && ListenerReach.mc.playerController != null) {
            event.setReach(((Reach)this.module).reach.getValue());
            event.setHitBox(((Reach)this.module).hitBox.getValue());
            event.setCancelled(true);
        }
    }
}
