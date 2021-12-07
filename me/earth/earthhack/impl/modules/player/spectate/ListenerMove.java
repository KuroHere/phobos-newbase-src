//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

final class ListenerMove extends ModuleListener<Spectate, MoveEvent>
{
    public ListenerMove(final Spectate module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((Spectate)this.module).stopMove.getValue()) {
            final double x = event.getX();
            double y = event.getY();
            final double z = event.getZ();
            if (y != 0.0) {
                for (final AxisAlignedBB a : ListenerMove.mc.world.getCollisionBoxes((Entity)ListenerMove.mc.player, ListenerMove.mc.player.getEntityBoundingBox().addCoord(x, y, z))) {
                    y = a.calculateYOffset(ListenerMove.mc.player.getEntityBoundingBox(), y);
                }
            }
            event.setX(0.0);
            event.setY((y == 0.0) ? -0.0784000015258789 : 0.0);
            event.setZ(0.0);
        }
    }
}
