//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

final class ListenerBlockChange extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        if (((AutoCrystal)this.module).multiThread.getValue() && ((AutoCrystal)this.module).blockChangeThread.getValue()) {
            final SPacketBlockChange packet = event.getPacket();
            if (packet.getBlockState().getBlock() == Blocks.AIR && ListenerBlockChange.mc.player.getDistanceSq(packet.getBlockPosition()) < 40.0) {
                event.addPostEvent(() -> {
                    if (ListenerBlockChange.mc.world != null && HelperUtil.validChange(packet.getBlockPosition(), ListenerBlockChange.mc.world.playerEntities)) {
                        ((AutoCrystal)this.module).threadHelper.startThread(new BlockPos[0]);
                    }
                });
            }
        }
    }
}
