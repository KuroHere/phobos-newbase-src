//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.*;

public class ActionManager extends SubscriberImpl
{
    private volatile boolean sneaking;
    private volatile boolean sprinting;
    
    public ActionManager() {
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketEntityAction>>(PacketEvent.Post.class, CPacketEntityAction.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketEntityAction> event) {
                switch (event.getPacket().getAction()) {
                    case START_SPRINTING: {
                        ActionManager.this.sprinting = true;
                        break;
                    }
                    case STOP_SPRINTING: {
                        ActionManager.this.sprinting = false;
                        break;
                    }
                    case START_SNEAKING: {
                        ActionManager.this.sneaking = true;
                        break;
                    }
                    case STOP_SNEAKING: {
                        ActionManager.this.sneaking = false;
                        break;
                    }
                }
            }
        });
    }
    
    public boolean isSprinting() {
        return this.sprinting;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
}
