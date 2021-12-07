//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

final class ListenerBlockChange extends ModuleListener<Surround, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final Surround module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        final SPacketBlockChange packet = event.getPacket();
        event.addPostEvent(() -> {
            if (((Surround)this.module).targets.contains(packet.getBlockPosition())) {
                if (packet.getBlockState().getBlock() == Blocks.AIR) {
                    ((Surround)this.module).confirmed.remove(packet.getBlockPosition());
                    if (((Surround)this.module).shouldInstant(false)) {
                        ListenerMotion.start((Surround)this.module);
                    }
                }
                else if (!packet.getBlockState().getMaterial().isReplaceable()) {
                    ((Surround)this.module).confirmed.add(packet.getBlockPosition());
                }
            }
        });
    }
}
