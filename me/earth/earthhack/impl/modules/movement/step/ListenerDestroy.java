//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.step;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerDestroy extends ModuleListener<Step, PacketEvent.Post<CPacketPlayerDigging>>
{
    public ListenerDestroy(final Step module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerDigging> event) {
        if (event.getPacket().getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
            ((Step)this.module).onBreak();
        }
    }
}
