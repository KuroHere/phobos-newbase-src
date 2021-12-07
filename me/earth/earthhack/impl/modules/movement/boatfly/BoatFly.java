//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;
import me.earth.earthhack.api.util.bind.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.util.math.*;

public class BoatFly extends Module
{
    protected final Setting<Boolean> bypass;
    protected final Setting<Boolean> postBypass;
    protected final Setting<Integer> ticks;
    protected final Setting<Integer> packets;
    protected final Setting<Boolean> noVehicleMove;
    protected final Setting<Boolean> noSteer;
    protected final Setting<Boolean> noPosUpdate;
    protected final Setting<Boolean> noForceRotate;
    protected final Setting<Boolean> remount;
    protected final Setting<Boolean> remountPackets;
    protected final Setting<Boolean> noForceBoatMove;
    protected final Setting<Boolean> invalid;
    protected final Setting<Boolean> boatInvalid;
    protected final Setting<Type> invalidMode;
    protected final Setting<Integer> invalidTicks;
    protected final Setting<Double> upSpeed;
    protected final Setting<Double> downSpeed;
    protected final Setting<Float> glide;
    protected final Setting<Boolean> fixYaw;
    protected final Setting<Bind> downBind;
    protected final Setting<Boolean> schedule;
    protected int tickCount;
    protected int invalidTickCount;
    protected Set<Packet<?>> packetSet;
    
    public BoatFly() {
        super("BoatFly", Category.Movement);
        this.bypass = this.register(new BooleanSetting("Bypass", false));
        this.postBypass = this.register(new BooleanSetting("PostBypass", false));
        this.ticks = this.register(new NumberSetting("Ticks", 2, 0, 20));
        this.packets = this.register(new NumberSetting("Packets", 1, 1, 20));
        this.noVehicleMove = this.register(new BooleanSetting("NoVehicleMove", false));
        this.noSteer = this.register(new BooleanSetting("NoSteer", false));
        this.noPosUpdate = this.register(new BooleanSetting("NoPosUpdate", false));
        this.noForceRotate = this.register(new BooleanSetting("NoForceRotate", false));
        this.remount = this.register(new BooleanSetting("Remount", false));
        this.remountPackets = this.register(new BooleanSetting("RemountPackets", false));
        this.noForceBoatMove = this.register(new BooleanSetting("NoForceBoatMove", false));
        this.invalid = this.register(new BooleanSetting("Invalid", false));
        this.boatInvalid = this.register(new BooleanSetting("BoatInvalid", false));
        this.invalidMode = this.register(new EnumSetting("BoatInvalid", Type.Up));
        this.invalidTicks = this.register(new NumberSetting("InvalidTicks", 1, 0, 10));
        this.upSpeed = this.register(new NumberSetting("Up-Speed", 2.0, 0.0, 10.0));
        this.downSpeed = this.register(new NumberSetting("Down-Speed", 2.0, 0.0, 10.0));
        this.glide = this.register(new NumberSetting("Glide", 1.0E-4f, 0.0f, 0.2f));
        this.fixYaw = this.register(new BooleanSetting("Yaw", false));
        this.downBind = this.register(new BindSetting("Down-Bind"));
        this.schedule = this.register(new BooleanSetting("Schedule", false));
        this.tickCount = 0;
        this.invalidTickCount = 0;
        this.packetSet = new HashSet<Packet<?>>();
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerDismount(this));
        this.listeners.add(new ListenerPlayerPosLook(this));
        this.listeners.add(new ListenerServerVehicleMove(this));
        this.listeners.add(new ListenerSteer(this));
        this.listeners.add(new ListenerVehicleMove(this));
        this.listeners.add(new ListenerPostVehicleMove(this));
        this.listeners.add(new ListenerEntityLook(this));
        this.listeners.add(new ListenerEntityLookMove(this));
        this.listeners.add(new ListenerEntityRelativeMove(this));
        this.listeners.add(new ListenerEntityTeleport(this));
        this.listeners.addAll(new ListenerCPackets(this).getListeners());
        final SimpleData data = new SimpleData(this, "Fly while riding entities.");
        data.register(this.bypass, "Bypasses NCP BoatFly patch.");
        data.register(this.postBypass, "Sends interact packets after vehicle move packets.");
        data.register(this.ticks, "Ticks to wait between sending interact packets.");
        data.register(this.packets, "Number of interact packets to send.");
        data.register(this.noVehicleMove, "Cancels SPacketMoveVehicle, allowing for smoother flight.");
        data.register(this.noSteer, "Cancels CPacketSteerBoat, bypassing some patches.");
        data.register(this.noPosUpdate, "Does not update the player's position along with the boat's (Cancels CPacketPlayer).");
        data.register(this.noForceRotate, "Prevents the server from forcing your rotations.");
        data.register(this.remount, "Automatically remounts the boat after being removed.");
        data.register(this.remountPackets, "Sends extra packets after being dismounted.");
        data.register(this.upSpeed, "Speed to fly upwards with.");
        data.register(this.downSpeed, "Speed to fly downwards with.");
        data.register(this.glide, "Glides down with this speed.");
        data.register(this.fixYaw, "Makes the boat rotate with you.");
        data.register(this.noForceBoatMove, "Prevents the server from forcing your entity to move or rotate.");
        this.setData(data);
    }
    
    public double getGlideSpeed() {
        return this.glide.getValue();
    }
    
    protected void sendPackets(final Entity riding) {
        BoatFly.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(riding, EnumHand.MAIN_HAND));
        BoatFly.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        if (this.invalid.getValue() && this.invalidTickCount++ >= this.invalidTicks.getValue()) {
            final Vec3d playerVec = this.invalidMode.getValue().createOutOfBounds(BoatFly.mc.player.getPositionVector(), 1337);
            PacketUtil.doPosition(playerVec.xCoord, playerVec.yCoord, playerVec.zCoord, false);
            if (this.boatInvalid.getValue() && BoatFly.mc.player.getRidingEntity() != null) {
                final CPacketVehicleMove packet = new CPacketVehicleMove();
                final Vec3d vec = this.invalidMode.getValue().createOutOfBounds(BoatFly.mc.player.getRidingEntity().getPositionVector(), 1337);
                ((ICPacketVehicleMove)packet).setY(vec.yCoord);
                ((ICPacketVehicleMove)packet).setX(vec.xCoord);
                ((ICPacketVehicleMove)packet).setZ(vec.zCoord);
                this.packetSet.add((Packet<?>)packet);
                NetworkUtil.sendPacketNoEvent((Packet<?>)packet);
            }
            this.invalidTickCount = 0;
        }
    }
}
