//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

final class ListenerBlockChange extends ModuleListener<PistonAura, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final PistonAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        if (!((PistonAura)this.module).change.getValue()) {
            return;
        }
        ListenerBlockChange.mc.addScheduledTask(() -> {
            if (((PistonAura)this.module).current != null) {
                final SPacketBlockChange packet = (SPacketBlockChange)event.getPacket();
                if (((PistonAura)this.module).checkUpdate(packet.getBlockPosition(), packet.getBlockState(), ((PistonAura)this.module).current.getRedstonePos(), Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_TORCH) || ((PistonAura)this.module).checkUpdate(packet.getBlockPosition(), packet.getBlockState(), ((PistonAura)this.module).current.getPistonPos(), (Block)Blocks.PISTON, (Block)Blocks.STICKY_PISTON)) {
                    ((PistonAura)this.module).current.setValid(false);
                }
            }
        });
    }
}
