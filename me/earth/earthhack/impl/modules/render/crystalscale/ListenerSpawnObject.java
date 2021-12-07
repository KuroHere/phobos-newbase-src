//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crystalscale;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.animation.*;

final class ListenerSpawnObject extends ModuleListener<CrystalScale, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final CrystalScale module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (event.getPacket().getType() == 51) {
            ((CrystalScale)this.module).scaleMap.put(event.getPacket().getEntityID(), new TimeAnimation(((CrystalScale)this.module).time.getValue(), 0.10000000149011612, ((CrystalScale)this.module).scale.getValue(), false, AnimationMode.LINEAR));
        }
    }
}
