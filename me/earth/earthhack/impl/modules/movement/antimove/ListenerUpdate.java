//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.antimove;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.movement.antimove.modes.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerUpdate extends ModuleListener<NoMove, UpdateEvent>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    
    public ListenerUpdate(final NoMove module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (((NoMove)this.module).mode.getValue() == StaticMode.NoVoid && !ListenerUpdate.mc.player.noClip && ListenerUpdate.mc.player.posY <= ((NoMove)this.module).height.getValue() && !ListenerUpdate.PACKET_FLY.isEnabled()) {
            final RayTraceResult trace = ListenerUpdate.mc.world.rayTraceBlocks(ListenerUpdate.mc.player.getPositionVector(), new Vec3d(ListenerUpdate.mc.player.posX, 0.0, ListenerUpdate.mc.player.posZ), false, false, false);
            if (trace == null || trace.typeOfHit != RayTraceResult.Type.BLOCK) {
                if (((NoMove)this.module).timer.getValue()) {
                    Managers.TIMER.setTimer(0.1f);
                }
                else {
                    ListenerUpdate.mc.player.setVelocity(0.0, 0.0, 0.0);
                    if (ListenerUpdate.mc.player.getRidingEntity() != null) {
                        ListenerUpdate.mc.player.getRidingEntity().setVelocity(0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
    }
}
