//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.waypoints;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.waypoints.mode.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.entity.*;
import java.util.*;
import java.awt.*;

final class ListenerRender extends ModuleListener<WayPoints, Render3DEvent>
{
    public ListenerRender(final WayPoints module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final WayPointRender render = ((WayPoints)this.module).render.getValue();
        if (render == WayPointRender.None) {
            return;
        }
        final WayPointType type = WayPointType.fromDimension(ListenerRender.mc.world.provider.getDimensionType());
        final Entity entity = RenderUtil.getEntity();
        for (final WayPointSetting setting : ((WayPoints)this.module).getWayPoints()) {
            double multiplier = 1.0;
            if (setting.getType() == type || (type == WayPointType.OVW && setting.getType() == WayPointType.Nether && ((WayPoints)this.module).ovwInNether.getValue() && this.assign(multiplier = 8.0)) || (type == WayPointType.Nether && setting.getType() == WayPointType.OVW && ((WayPoints)this.module).netherOvw.getValue() && this.assign(multiplier = 0.125))) {
                final BlockPos pos = setting.getValue();
                final double x = Math.floor(pos.getX() * multiplier);
                final double y = pos.getY();
                final double z = Math.floor(pos.getZ() * multiplier);
                final double distanceSq = entity.getDistanceSq(x, y, z);
                if (distanceSq > MathUtil.squareToLong(((WayPoints)this.module).range.getValue())) {
                    continue;
                }
                final AxisAlignedBB bb = Interpolation.interpolateAxis(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
                final Color c = ((WayPoints)this.module).getColor(setting.getType());
                RenderUtil.startRender();
                RenderUtil.drawOutline(bb, 1.5f, c);
                RenderUtil.endRender();
                final StringBuilder builder = new StringBuilder();
                builder.append(setting.getName()).append(" ");
                switch (render) {
                    case Distance: {
                        this.appendDistance(builder, Math.sqrt(distanceSq));
                        break;
                    }
                    case Coordinates: {
                        this.appendCoordinates(builder, pos);
                        break;
                    }
                    case Both: {
                        this.appendCoordinates(builder, pos);
                        builder.append(" ");
                        this.appendDistance(builder, Math.sqrt(distanceSq));
                        break;
                    }
                }
                RenderUtil.drawNametag(builder.toString(), bb, ((WayPoints)this.module).scale.getValue(), c.getRGB());
            }
        }
    }
    
    private boolean assign(final double assignment) {
        return true;
    }
    
    private void appendCoordinates(final StringBuilder builder, final BlockPos pos) {
        builder.append("XYZ: ").append(pos.getX()).append(", ").append(pos.getY()).append(", ").append(pos.getZ());
    }
    
    private void appendDistance(final StringBuilder builder, final double distance) {
        builder.append("(").append(MathUtil.round(distance, 1)).append(")");
    }
}
