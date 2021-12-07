//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trails;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.util.animation.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import net.minecraft.util.math.*;
import java.util.*;

final class ListenerSpawnObject extends ModuleListener<Trails, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final Trails module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if ((((Trails)this.module).pearls.getValue() && event.getPacket().getType() == 65) || (((Trails)this.module).arrows.getValue() && event.getPacket().getType() == 60) || (((Trails)this.module).snowballs.getValue() && event.getPacket().getType() == 61)) {
            Earthhack.getLogger().info((Object)event.getPacket().getEntityID());
            final TimeAnimation animation = new TimeAnimation(((Trails)this.module).time.getValue() * 1000, 0.0, ((Trails)this.module).color.getAlpha(), false, AnimationMode.LINEAR);
            animation.stop();
            ((Trails)this.module).ids.put(event.getPacket().getEntityID(), animation);
            ((Trails)this.module).traceLists.put(event.getPacket().getEntityID(), new ArrayList<Trace>());
            ((Trails)this.module).traces.put(event.getPacket().getEntityID(), new Trace(0, null, ListenerSpawnObject.mc.world.provider.getDimensionType(), new Vec3d(event.getPacket().getX(), event.getPacket().getY(), event.getPacket().getZ()), new ArrayList<Trace.TracePos>()));
        }
    }
}
