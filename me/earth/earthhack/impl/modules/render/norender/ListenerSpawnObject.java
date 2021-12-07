//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerSpawnObject extends ModuleListener<NoRender, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final NoRender module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -10, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (event.isCancelled() || !((NoRender)this.module).items.getValue() || event.getPacket().getType() != 2) {
            return;
        }
        final SPacketSpawnObject p = event.getPacket();
        final Entity e = (Entity)new EntityItem((World)ListenerSpawnObject.mc.world, p.getX(), p.getY(), p.getZ());
        EntityTracker.updateServerPosition(e, p.getX(), p.getY(), p.getZ());
        e.rotationPitch = p.getPitch() * 360 / 256.0f;
        e.rotationYaw = p.getYaw() * 360 / 256.0f;
        final Entity[] parts = e.getParts();
        if (parts != null) {
            final int id = p.getEntityID() - e.getEntityId();
            for (final Entity part : parts) {
                part.setEntityId(part.getEntityId() + id);
            }
        }
        e.setEntityId(p.getEntityID());
        e.setUniqueId(p.getUniqueId());
        if (p.getData() > 0) {
            e.setVelocity(p.getSpeedX() / 8000.0, p.getSpeedY() / 8000.0, p.getSpeedZ() / 8000.0);
        }
        event.setCancelled(true);
        ListenerSpawnObject.mc.addScheduledTask(() -> {
            Managers.SET_DEAD.setDeadCustom(e, Long.MAX_VALUE);
            ((NoRender)this.module).ids.add(p.getEntityID());
        });
    }
}
