//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerSetSlot extends ModuleListener<Offhand, PacketEvent.Receive<SPacketSetSlot>>
{
    public ListenerSetSlot(final Offhand module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSetSlot.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSetSlot> event) {
        ((Offhand)this.module).setSlotTimer.reset();
        if (!((Offhand)this.module).async.getValue() || ((Offhand)this.module).asyncTimer.passed(((Offhand)this.module).asyncCheck.getValue()) || ((Offhand)this.module).asyncSlot == -1 || event.getPacket().getSlot() != ((Offhand)this.module).asyncSlot) {
            return;
        }
        event.setCancelled(true);
        ((Offhand)this.module).asyncSlot = -1;
    }
}
