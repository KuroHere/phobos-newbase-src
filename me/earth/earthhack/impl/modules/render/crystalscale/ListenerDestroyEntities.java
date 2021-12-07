//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crystalscale;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerDestroyEntities extends ModuleListener<CrystalScale, PacketEvent.Receive<SPacketDestroyEntities>>
{
    public ListenerDestroyEntities(final CrystalScale module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketDestroyEntities.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
        ListenerDestroyEntities.mc.addScheduledTask(() -> {
            ((SPacketDestroyEntities)event.getPacket()).getEntityIDs();
            final int[] array;
            int i = 0;
            for (int length = array.length; i < length; ++i) {
                final int id = array[i];
                ((CrystalScale)this.module).scaleMap.remove(id);
            }
        });
    }
}
