//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;
import net.minecraft.block.*;

final class ListenerDigging extends ModuleListener<Announcer, PacketEvent.Post<CPacketPlayerDigging>>
{
    public ListenerDigging(final Announcer module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerDigging> event) {
        if (((Announcer)this.module).mine.getValue()) {
            final CPacketPlayerDigging p = event.getPacket();
            if (p.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                final Block block = ListenerDigging.mc.world.getBlockState(p.getPosition()).getBlock();
                ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Mine, block.getLocalizedName());
            }
        }
    }
}
