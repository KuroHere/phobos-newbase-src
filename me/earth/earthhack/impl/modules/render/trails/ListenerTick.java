//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trails;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.*;
import me.earth.earthhack.impl.util.animation.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

final class ListenerTick extends ModuleListener<Trails, TickEvent>
{
    public ListenerTick(final Trails module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (ListenerTick.mc.world == null) {
            return;
        }
        if (((Trails)this.module).ids.keySet().isEmpty()) {
            return;
        }
        for (final Integer id : ((Trails)this.module).ids.keySet()) {
            if (id == null) {
                continue;
            }
            if (ListenerTick.mc.world.loadedEntityList == null) {
                return;
            }
            if (ListenerTick.mc.world.loadedEntityList.isEmpty()) {
                return;
            }
            Trace idTrace = ((Trails)this.module).traces.get(id);
            final Entity entity = ListenerTick.mc.world.getEntityByID((int)id);
            if (entity != null) {
                final Vec3d vec = entity.getPositionVector();
                if (vec == null) {
                    continue;
                }
                if (vec.equals((Object)BreadCrumbs.ORIGIN)) {
                    continue;
                }
                if (!((Trails)this.module).traces.containsKey(id) || idTrace == null) {
                    ((Trails)this.module).traces.put(id, new Trace(0, null, ListenerTick.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                    idTrace = ((Trails)this.module).traces.get(id);
                }
                List<Trace.TracePos> trace = idTrace.getTrace();
                final Vec3d vec3d = trace.isEmpty() ? vec : trace.get(trace.size() - 1).getPos();
                if (!trace.isEmpty() && (vec.distanceTo(vec3d) > 100.0 || idTrace.getType() != ListenerTick.mc.world.provider.getDimensionType())) {
                    ((Trails)this.module).traceLists.get(id).add(idTrace);
                    trace = new ArrayList<Trace.TracePos>();
                    ((Trails)this.module).traces.put(id, new Trace(((Trails)this.module).traceLists.get(id).size() + 1, null, ListenerTick.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                }
                if (trace.isEmpty() || !vec.equals((Object)vec3d)) {
                    trace.add(new Trace.TracePos(vec));
                }
            }
            final TimeAnimation animation = ((Trails)this.module).ids.get(id);
            if (entity instanceof EntityArrow && (entity.onGround || entity.isCollided || !entity.isAirBorne)) {
                animation.play();
            }
            if (animation == null || ((Trails)this.module).color.getAlpha() - animation.getCurrent() > 0.0) {
                continue;
            }
            animation.stop();
            ((Trails)this.module).ids.remove(id);
            ((Trails)this.module).traceLists.remove(id);
            ((Trails)this.module).traces.remove(id);
        }
    }
}
