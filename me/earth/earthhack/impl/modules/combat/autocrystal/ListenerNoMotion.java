// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerNoMotion extends ModuleListener<AutoCrystal, NoMotionUpdateEvent>
{
    private float forward;
    
    public ListenerNoMotion(final AutoCrystal module) {
        super(module, (Class<? super Object>)NoMotionUpdateEvent.class);
        this.forward = 0.004f;
    }
    
    public void invoke(final NoMotionUpdateEvent event) {
        if (((AutoCrystal)this.module).multiThread.getValue() && !((AutoCrystal)this.module).isSpoofing && ((AutoCrystal)this.module).rotate.getValue() != ACRotate.None && ((AutoCrystal)this.module).rotationThread.getValue() == RotationThread.Cancel) {
            this.forward = -this.forward;
            final float yaw = Managers.ROTATION.getServerYaw() + this.forward;
            final float pitch = Managers.ROTATION.getServerPitch() + this.forward;
            ((AutoCrystal)this.module).rotationCanceller.onPacket(new PacketEvent.Send<CPacketPlayer>((CPacketPlayer)new CPacketPlayer.Rotation(yaw, pitch, Managers.POSITION.isOnGround())));
        }
        else {
            ((AutoCrystal)this.module).rotationCanceller.reset();
        }
    }
}
