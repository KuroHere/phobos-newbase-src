//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;

final class ListenerChat extends ModuleListener<Tracker, PacketEvent.Receive<SPacketChat>>
{
    public ListenerChat(final Tracker module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketChat.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketChat> event) {
        if (((Tracker)this.module).autoEnable.getValue() && !((Tracker)this.module).awaiting && !((Tracker)this.module).isEnabled()) {
            final String s = event.getPacket().getChatComponent().getFormattedText();
            if (!s.contains("<") && (s.contains("has accepted your duel request") || s.contains("Accepted the duel request from"))) {
                Scheduler.getInstance().scheduleAsynchronously(() -> {
                    ModuleUtil.sendMessage((Module)this.module, "§dDuel accepted. Tracker will enable in §f5.0§d seconds!");
                    ((Tracker)this.module).timer.reset();
                    ((Tracker)this.module).awaiting = true;
                });
            }
        }
    }
}
