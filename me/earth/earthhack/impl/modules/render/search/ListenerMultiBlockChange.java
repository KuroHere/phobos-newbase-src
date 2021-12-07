//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;

final class ListenerMultiBlockChange extends ModuleListener<Search, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiBlockChange(final Search module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        if (((Search)this.module).remove.getValue()) {
            for (final SPacketMultiBlockChange.BlockUpdateData data : event.getPacket().getChangedBlocks()) {
                final IBlockState state = data.getBlockState();
                if (state.getMaterial() == Material.AIR || !((Search)this.module).isValid(state.getBlock().getLocalizedName())) {
                    ((Search)this.module).toRender.remove(data.getPos());
                }
            }
        }
    }
}
