//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerSpawnObject extends ModuleListener<SafetyManager, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final SafetyManager manager) {
        super(manager, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        final SPacketSpawnObject p = event.getPacket();
        if (p.getType() == 51 && ListenerSpawnObject.mc.player != null && DamageUtil.calculate(new BlockPos(p.getX(), p.getY(), p.getZ()).down()) > ((SafetyManager)this.module).damage.getValue()) {
            ((SafetyManager)this.module).setSafe(false);
        }
    }
}
