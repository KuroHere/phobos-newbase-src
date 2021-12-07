//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

public class ListenerSneak extends ModuleListener<Phase, PacketEvent.Send<CPacketEntityAction>>
{
    public ListenerSneak(final Phase module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketEntityAction.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketEntityAction> event) {
        if (event.getPacket().getAction() == CPacketEntityAction.Action.START_SNEAKING && ((Phase)this.module).isPhasing() && ((Phase)this.module).cancelSneak.getValue() && ListenerSneak.mc.gameSettings.keyBindSneak.isKeyDown()) {
            event.setCancelled(true);
        }
    }
}
