//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import java.util.*;

final class ListenerExplosion extends ModuleListener<Surround, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final Surround module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        final SPacketExplosion packet = event.getPacket();
        event.addPostEvent(() -> {
            packet.getAffectedBlockPositions().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final BlockPos pos = iterator.next();
                ((Surround)this.module).confirmed.remove(pos);
                if (((Surround)this.module).shouldInstant(false)) {
                    ListenerMotion.start((Surround)this.module);
                }
            }
        });
    }
}
