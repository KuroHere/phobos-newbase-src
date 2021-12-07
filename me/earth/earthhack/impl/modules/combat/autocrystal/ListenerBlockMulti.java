//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

final class ListenerBlockMulti extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerBlockMulti(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        if (((AutoCrystal)this.module).multiThread.getValue() && ((AutoCrystal)this.module).blockChangeThread.getValue()) {
            final SPacketMultiBlockChange packet = event.getPacket();
            event.addPostEvent(() -> {
                packet.getChangedBlocks();
                final SPacketMultiBlockChange.BlockUpdateData[] array;
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    final SPacketMultiBlockChange.BlockUpdateData data = array[i];
                    if (data.getBlockState().getMaterial() == Material.AIR && HelperUtil.validChange(data.getPos(), ListenerBlockMulti.mc.world.playerEntities)) {
                        ((AutoCrystal)this.module).threadHelper.startThread(new BlockPos[0]);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            });
        }
    }
}
