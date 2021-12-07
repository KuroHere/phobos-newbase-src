//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.entity.player.*;

final class ListenerRemove extends ModuleListener<Spectate, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerRemove(final Spectate module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        if (((Spectate)this.module).spectating) {
            final EntityPlayer player = ((Spectate)this.module).player;
            if (player != null) {
                for (final int id : event.getPacket().getEntityIDs()) {
                    if (id == player.getEntityId()) {
                        ListenerRemove.mc.addScheduledTask(() -> {
                            ((Spectate)this.module).disable();
                            ModuleUtil.sendMessage((Module)this.module, "§cThe Player you spectated got removed.");
                        });
                        return;
                    }
                }
            }
        }
    }
}
