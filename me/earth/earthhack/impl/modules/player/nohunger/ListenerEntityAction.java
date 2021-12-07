//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.nohunger;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerEntityAction extends ModuleListener<NoHunger, PacketEvent.Send<CPacketEntityAction>>
{
    public ListenerEntityAction(final NoHunger module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketEntityAction.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketEntityAction> event) {
        if (((NoHunger)this.module).sprint.getValue()) {
            final CPacketEntityAction p = event.getPacket();
            if (p.getAction() == CPacketEntityAction.Action.START_SPRINTING || p.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }
}
