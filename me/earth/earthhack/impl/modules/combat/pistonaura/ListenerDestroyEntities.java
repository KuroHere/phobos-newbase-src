//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerDestroyEntities extends ModuleListener<PistonAura, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerDestroyEntities(final PistonAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        if (((PistonAura)this.module).destroyEntities.getValue()) {
            for (final int id : event.getPacket().getEntityIDs()) {
                if (id == ((PistonAura)this.module).entityId) {
                    ListenerDestroyEntities.mc.addScheduledTask(() -> {
                        if (((PistonAura)this.module).current != null) {
                            ((PistonAura)this.module).current.setValid(false);
                        }
                        return;
                    });
                }
            }
        }
    }
}
