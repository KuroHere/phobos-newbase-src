//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;

final class ListenerSpawnObject extends ModuleListener<Tracker, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final Tracker module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        final SPacketSpawnObject p = event.getPacket();
        if (ListenerSpawnObject.mc.world == null || ListenerSpawnObject.mc.player == null) {
            return;
        }
        if (p.getType() == 51) {
            final BlockPos pos = new BlockPos(p.getX(), p.getY(), p.getZ());
            if (!((Tracker)this.module).placed.remove(pos)) {
                ((Tracker)this.module).crystals.incrementAndGet();
            }
        }
        else if (p.getType() == 75) {
            if (((Tracker)this.module).awaitingExp.get() > 0) {
                if (ListenerSpawnObject.mc.player.getDistanceSq(p.getX(), p.getY(), p.getZ()) < 16.0) {
                    ((Tracker)this.module).awaitingExp.decrementAndGet();
                }
                else {
                    ((Tracker)this.module).exp.incrementAndGet();
                }
            }
            else {
                ((Tracker)this.module).exp.incrementAndGet();
            }
        }
    }
}
