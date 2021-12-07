//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;

final class ListenerTotems extends ModuleListener<Announcer, PacketEvent.Receive<SPacketEntityStatus>>
{
    public ListenerTotems(final Announcer module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityStatus.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityStatus> event) {
        if (((Announcer)this.module).totems.getValue()) {
            final SPacketEntityStatus packet = event.getPacket();
            if (packet.getOpCode() == 35) {
                final Entity entity = packet.getEntity((World)ListenerTotems.mc.world);
                if (entity instanceof EntityPlayer && (!((Announcer)this.module).friends.getValue() || !Managers.FRIENDS.contains((EntityPlayer)entity))) {
                    final Announcement announcement = ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Totems, entity.getName());
                    announcement.setAmount(Managers.COMBAT.getPops(entity) + 1);
                }
            }
        }
    }
}
