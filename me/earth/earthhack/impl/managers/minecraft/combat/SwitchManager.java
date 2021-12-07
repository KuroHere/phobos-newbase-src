//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.network.play.server.*;

public class SwitchManager extends SubscriberImpl
{
    private final StopWatch timer;
    private volatile int last_slot;
    
    public SwitchManager() {
        this.timer = new StopWatch();
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketHeldItemChange>>(PacketEvent.Post.class, CPacketHeldItemChange.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketHeldItemChange> event) {
                SwitchManager.this.timer.reset();
                SwitchManager.this.last_slot = event.getPacket().getSlotId();
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketHeldItemChange>>(PacketEvent.Receive.class, SPacketHeldItemChange.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketHeldItemChange> event) {
                SwitchManager.this.last_slot = event.getPacket().getHeldItemHotbarIndex();
            }
        });
    }
    
    public long getLastSwitch() {
        return this.timer.getTime();
    }
    
    public int getSlot() {
        return this.last_slot;
    }
}
