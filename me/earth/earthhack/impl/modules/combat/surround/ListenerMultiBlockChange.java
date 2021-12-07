//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

final class ListenerMultiBlockChange extends ModuleListener<Surround, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiBlockChange(final Surround module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        final SPacketMultiBlockChange packet = event.getPacket();
        event.addPostEvent(() -> {
            boolean instant = false;
            packet.getChangedBlocks();
            final SPacketMultiBlockChange.BlockUpdateData[] array;
            int i = 0;
            for (int length = array.length; i < length; ++i) {
                final SPacketMultiBlockChange.BlockUpdateData data = array[i];
                if (((Surround)this.module).targets.contains(data.getPos())) {
                    if (data.getBlockState().getBlock() == Blocks.AIR) {
                        ((Surround)this.module).confirmed.remove(data.getPos());
                        if (((Surround)this.module).shouldInstant(false) && !instant) {
                            instant = true;
                            ListenerMotion.start((Surround)this.module);
                        }
                    }
                    else if (!data.getBlockState().getMaterial().isReplaceable()) {
                        ((Surround)this.module).confirmed.add(data.getPos());
                    }
                }
            }
        });
    }
}
