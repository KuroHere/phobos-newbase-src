//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.blockhighlight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.minecraft.movement.*;
import me.earth.earthhack.impl.util.math.*;

final class ListenerMotion extends ModuleListener<BlockHighlight, MotionUpdateEvent>
{
    public ListenerMotion(final BlockHighlight module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST && ((BlockHighlight)this.module).distance.getValue() && ((BlockHighlight)this.module).current != null && ListenerMotion.mc.objectMouseOver != null && ListenerMotion.mc.objectMouseOver.hitVec != null) {
            final RayTraceResult r = ListenerMotion.mc.objectMouseOver;
            double d;
            boolean see;
            if (r.typeOfHit == RayTraceResult.Type.BLOCK && r.getBlockPos() != null) {
                final BlockPos p = r.getBlockPos();
                if (((BlockHighlight)this.module).hitVec.getValue()) {
                    d = Managers.POSITION.getVec().distanceTo(r.hitVec);
                }
                else {
                    d = Managers.POSITION.getVec().distanceTo(new Vec3d(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5));
                }
                see = this.canSee(r.hitVec, Managers.POSITION);
            }
            else if (r.typeOfHit == RayTraceResult.Type.ENTITY && r.entityHit != null) {
                final Entity e = r.entityHit;
                d = r.entityHit.getDistance(Managers.POSITION.getX(), Managers.POSITION.getY(), Managers.POSITION.getZ());
                see = this.canSee(new Vec3d(e.posX, e.posY + e.getEyeHeight(), e.posZ), Managers.POSITION);
            }
            else {
                d = Managers.POSITION.getVec().distanceTo(r.hitVec);
                see = this.canSee(r.hitVec, Managers.POSITION);
            }
            final StringBuilder builder = new StringBuilder(((BlockHighlight)this.module).current);
            builder.append(", ");
            if (d >= 6.0) {
                builder.append("§c");
            }
            else if (d >= 3.0 && !see) {
                builder.append("§6");
            }
            else {
                builder.append("§a");
            }
            builder.append(MathUtil.round(d, 2));
            ((BlockHighlight)this.module).current = builder.toString();
        }
    }
    
    private boolean canSee(final Vec3d toSee, final PositionManager m) {
        return RayTraceUtil.canBeSeen(toSee, m.getX(), m.getY(), m.getZ(), ListenerMotion.mc.player.getEyeHeight());
    }
}
