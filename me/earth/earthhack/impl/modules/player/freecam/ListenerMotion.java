//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.player.freecam.mode.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

final class ListenerMotion extends ModuleListener<Freecam, MotionUpdateEvent>
{
    public ListenerMotion(final Freecam module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 99999999);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ((Freecam)this.module).mode.getValue() == CamMode.Position) {
            final RayTraceResult result = ListenerMotion.mc.objectMouseOver;
            if (result != null) {
                final float[] rotations = RotationUtil.getRotations(result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord, (Entity)((Freecam)this.module).getPlayer());
                ((Freecam)this.module).rotate(rotations[0], rotations[1]);
            }
            ((Freecam)this.module).getPlayer().setHeldItem(EnumHand.MAIN_HAND, ListenerMotion.mc.player.getHeldItemMainhand());
            ((Freecam)this.module).getPlayer().setHeldItem(EnumHand.OFF_HAND, ListenerMotion.mc.player.getHeldItemOffhand());
            event.setX(((Freecam)this.module).getPlayer().posX);
            event.setY(((Freecam)this.module).getPlayer().getEntityBoundingBox().minY);
            event.setZ(((Freecam)this.module).getPlayer().posZ);
            event.setYaw(((Freecam)this.module).getPlayer().rotationYaw);
            event.setPitch(((Freecam)this.module).getPlayer().rotationPitch);
            event.setOnGround(((Freecam)this.module).getPlayer().onGround);
        }
    }
}
