//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.client.server.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import java.io.*;

final class ListenerStartEating extends ModuleListener<ServerModule, PacketEvent.Send<CPacketPlayerTryUseItem>>
{
    public ListenerStartEating(final ServerModule module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, Integer.MIN_VALUE, CPacketPlayerTryUseItem.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItem> event) {
        if (event.isCancelled() || ((ServerModule)this.module).currentMode == ServerMode.Client || !((ServerModule)this.module).sync.getValue() || !(ListenerStartEating.mc.player.getHeldItem(event.getPacket().getHand()).getItem() instanceof ItemFood)) {
            return;
        }
        ((ServerModule)this.module).isEating = true;
        final byte[] packet = new byte[9];
        ProtocolUtil.addInt(11, packet);
        ProtocolUtil.addInt(1, packet, 4);
        packet[8] = -128;
        try {
            ((ServerModule)this.module).connectionManager.send(packet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
