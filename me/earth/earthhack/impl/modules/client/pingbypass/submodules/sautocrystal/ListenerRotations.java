//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerRotations extends ModuleListener<ServerAutoCrystal, MotionUpdateEvent>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private float offset;
    
    public ListenerRotations(final ServerAutoCrystal module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, Integer.MIN_VALUE);
        this.offset = 4.0E-4f;
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ListenerRotations.PINGBYPASS.isEnabled() && InventoryUtil.isHolding(Items.END_CRYSTAL)) {
            final float yawDif = event.getYaw() - ((IEntityPlayerSP)ListenerRotations.mc.player).getLastReportedYaw();
            final float pitchDif = event.getPitch() - ((IEntityPlayerSP)ListenerRotations.mc.player).getLastReportedPitch();
            if (yawDif == 0.0f && pitchDif == 0.0f) {
                this.offset = -this.offset;
                event.setYaw(event.getYaw() + this.offset);
                event.setPitch(event.getPitch() + this.offset);
            }
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
