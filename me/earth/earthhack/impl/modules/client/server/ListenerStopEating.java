//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.client.server.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import java.io.*;

final class ListenerStopEating extends ModuleListener<ServerModule, AbortEatingEvent>
{
    public ListenerStopEating(final ServerModule module) {
        super(module, (Class<? super Object>)AbortEatingEvent.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final AbortEatingEvent event) {
        if (((ServerModule)this.module).currentMode == ServerMode.Client || !((ServerModule)this.module).sync.getValue() || !((ServerModule)this.module).isEating || !(ListenerStopEating.mc.player.getActiveItemStack().getItem() instanceof ItemFood)) {
            return;
        }
        ((ServerModule)this.module).isEating = false;
        final byte[] packet = new byte[9];
        ProtocolUtil.addInt(11, packet);
        ProtocolUtil.addInt(1, packet, 4);
        packet[8] = 0;
        try {
            ((ServerModule)this.module).connectionManager.send(packet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
