//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.jesus;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.jesus.mode.*;
import java.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.math.*;

final class ListenerCollision extends ModuleListener<Jesus, CollisionEvent>
{
    public ListenerCollision(final Jesus module) {
        super(module, (Class<? super Object>)CollisionEvent.class);
    }
    
    public void invoke(final CollisionEvent event) {
        if (event.getEntity() != null && ListenerCollision.mc.player != null && ((event.getEntity().equals((Object)ListenerCollision.mc.player) && ((Jesus)this.module).mode.getValue() != JesusMode.Dolphin) || (event.getEntity().getControllingPassenger() != null && Objects.equals(event.getEntity().getControllingPassenger(), ListenerCollision.mc.player))) && event.getBlock() instanceof BlockLiquid && !ListenerCollision.mc.player.isSneaking() && ListenerCollision.mc.player.fallDistance < 3.0f && !PositionUtil.inLiquid() && PositionUtil.inLiquid(false) && PositionUtil.isAbove(event.getPos())) {
            final BlockPos pos = event.getPos();
            event.setBB(new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), pos.getY() + 0.99, (double)(pos.getZ() + 1)));
        }
    }
}
