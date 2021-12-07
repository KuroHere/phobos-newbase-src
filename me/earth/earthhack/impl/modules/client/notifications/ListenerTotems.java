//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.notifications;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerTotems extends ModuleListener<Notifications, PacketEvent.Receive<SPacketEntityStatus>>
{
    public ListenerTotems(final Notifications module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityStatus.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityStatus> event) {
        switch (event.getPacket().getOpCode()) {
            case 3: {
                ListenerTotems.mc.addScheduledTask(() -> {
                    if (ListenerTotems.mc.world != null) {
                        final Entity entity = ((SPacketEntityStatus)event.getPacket()).getEntity((World)ListenerTotems.mc.world);
                        if (entity instanceof EntityPlayer) {
                            final int pops = Managers.COMBAT.getPops(entity);
                            if (pops > 0) {
                                ((Notifications)this.module).onDeath(entity, Managers.COMBAT.getPops(entity));
                            }
                        }
                    }
                    return;
                });
                break;
            }
            case 35: {
                ListenerTotems.mc.addScheduledTask(() -> {
                    final Entity entity2 = ((SPacketEntityStatus)event.getPacket()).getEntity((World)ListenerTotems.mc.world);
                    if (entity2 instanceof EntityPlayer) {
                        ((Notifications)this.module).onPop(entity2, Managers.COMBAT.getPops(entity2) + 1);
                    }
                    return;
                });
                break;
            }
        }
    }
}
