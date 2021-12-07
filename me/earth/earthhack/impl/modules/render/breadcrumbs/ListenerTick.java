//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.breadcrumbs;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import java.util.*;
import net.minecraft.util.math.*;

final class ListenerTick extends ModuleListener<BreadCrumbs, TickEvent>
{
    public ListenerTick(final BreadCrumbs module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe() && ((BreadCrumbs)this.module).timer.passed(((BreadCrumbs)this.module).delay.getValue())) {
            final Vec3d vec = ListenerTick.mc.player.getPositionVector();
            if (vec.equals((Object)BreadCrumbs.ORIGIN)) {
                return;
            }
            if (((BreadCrumbs)this.module).trace == null) {
                ((BreadCrumbs)this.module).trace = new Trace(0, null, ListenerTick.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>());
            }
            List<Trace.TracePos> trace = ((BreadCrumbs)this.module).trace.getTrace();
            final Vec3d vec3d = trace.isEmpty() ? vec : trace.get(trace.size() - 1).getPos();
            if (!trace.isEmpty() && (vec.distanceTo(vec3d) > 100.0 || ((BreadCrumbs)this.module).trace.getType() != ListenerTick.mc.world.provider.getDimensionType())) {
                ((BreadCrumbs)this.module).positions.add(((BreadCrumbs)this.module).trace);
                trace = new ArrayList<Trace.TracePos>();
                ((BreadCrumbs)this.module).trace = new Trace(((BreadCrumbs)this.module).positions.size() + 1, null, ListenerTick.mc.world.provider.getDimensionType(), vec, trace);
            }
            if (trace.isEmpty() || !vec.equals((Object)vec3d)) {
                trace.add(new Trace.TracePos(vec, System.currentTimeMillis() + ((BreadCrumbs)this.module).fadeDelay.getValue()));
            }
            ((BreadCrumbs)this.module).timer.reset();
        }
    }
}
