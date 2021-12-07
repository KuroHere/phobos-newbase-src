//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;

final class ListenerMove extends ModuleListener<PacketFly, MoveEvent>
{
    public ListenerMove(final PacketFly module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((PacketFly)this.module).mode.getValue() == Mode.Setback || ((PacketFly)this.module).teleportID.get() != 0) {
            if (((PacketFly)this.module).zeroSpeed.getValue()) {
                event.setX(0.0);
                event.setY(0.0);
                event.setZ(0.0);
            }
            else {
                event.setX(ListenerMove.mc.player.motionX);
                event.setY(ListenerMove.mc.player.motionY);
                event.setZ(ListenerMove.mc.player.motionZ);
            }
            if (((PacketFly)this.module).zeroY.getValue()) {
                event.setY(0.0);
            }
            if (((PacketFly)this.module).phase.getValue() == Phase.Semi || ((PacketFly)this.module).isPlayerCollisionBoundingBoxEmpty()) {
                ListenerMove.mc.player.noClip = true;
            }
        }
    }
}
