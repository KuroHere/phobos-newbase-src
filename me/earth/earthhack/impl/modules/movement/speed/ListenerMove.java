//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.modules.player.ncptweaks.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMove extends ModuleListener<Speed, MoveEvent>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    private static final ModuleCache<Freecam> FREECAM;
    private static final ModuleCache<NCPTweaks> NCP_TWEAKS;
    
    public ListenerMove(final Speed module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (ListenerMove.PACKET_FLY.isEnabled() || ListenerMove.FREECAM.isEnabled() || (ListenerMove.NCP_TWEAKS.isEnabled() && ListenerMove.NCP_TWEAKS.get().isSpeedStopped())) {
            return;
        }
        if ((!((Speed)this.module).inWater.getValue() && (PositionUtil.inLiquid() || PositionUtil.inLiquid(true))) || ListenerMove.mc.player.isOnLadder() || ListenerMove.mc.player.isEntityInsideOpaqueBlock()) {
            ((Speed)this.module).stop = true;
            return;
        }
        if (((Speed)this.module).stop) {
            ((Speed)this.module).stop = false;
            return;
        }
        ((Speed)this.module).mode.getValue().move(event, (Speed)this.module);
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
        FREECAM = Caches.getModule(Freecam.class);
        NCP_TWEAKS = Caches.getModule(NCPTweaks.class);
    }
}
