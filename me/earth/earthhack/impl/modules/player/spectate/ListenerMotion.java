//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;

final class ListenerMotion extends ModuleListener<Spectate, MotionUpdateEvent>
{
    public ListenerMotion(final Spectate module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ((Spectate)this.module).rotate.getValue()) {
            final RayTraceResult r = ListenerMotion.mc.objectMouseOver;
            if (r != null && r.typeOfHit != RayTraceResult.Type.MISS && r.hitVec != null) {
                final float[] rotations = RotationUtil.getRotations(r.hitVec.xCoord, r.hitVec.yCoord, r.hitVec.zCoord, (Entity)ListenerMotion.mc.player);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
        }
    }
}
