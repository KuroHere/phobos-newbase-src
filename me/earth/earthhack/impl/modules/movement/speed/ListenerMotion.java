//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Speed, MotionUpdateEvent>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    private static final ModuleCache<Freecam> FREECAM;
    
    public ListenerMotion(final Speed module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (ListenerMotion.PACKET_FLY.isEnabled() || ListenerMotion.FREECAM.isEnabled()) {
            return;
        }
        if (MovementUtil.noMovementKeys()) {
            ListenerMotion.mc.player.motionX = 0.0;
            ListenerMotion.mc.player.motionZ = 0.0;
        }
        ((Speed)this.module).distance = MovementUtil.getDistance2D();
        if (((Speed)this.module).mode.getValue() == SpeedMode.OnGround && ((Speed)this.module).onGroundStage == 3) {
            event.setY(event.getY() + (PositionUtil.isBoxColliding() ? 0.2 : 0.4) + MovementUtil.getJumpSpeed());
        }
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
        FREECAM = Caches.getModule(Freecam.class);
    }
}
