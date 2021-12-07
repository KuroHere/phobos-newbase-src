//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;
import java.util.concurrent.atomic.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PacketFly extends Module
{
    protected final Setting<Mode> mode;
    protected final Setting<Float> factor;
    protected final Setting<Phase> phase;
    protected final Setting<Type> type;
    protected final Setting<Boolean> antiKick;
    protected final Setting<Boolean> answer;
    protected final Setting<Boolean> bbOffset;
    protected final Setting<Integer> invalidY;
    protected final Setting<Integer> invalids;
    protected final Setting<Integer> sendTeleport;
    protected final Setting<Double> concealY;
    protected final Setting<Double> conceal;
    protected final Setting<Double> ySpeed;
    protected final Setting<Double> xzSpeed;
    protected final Setting<Boolean> positionRotation;
    protected final Setting<Boolean> elytra;
    protected final Setting<Boolean> xzJitter;
    protected final Setting<Boolean> yJitter;
    protected final Setting<Boolean> setPos;
    protected final Setting<Boolean> zeroSpeed;
    protected final Setting<Boolean> zeroY;
    protected final Setting<Boolean> fixPosition;
    protected final Setting<Boolean> zeroTeleport;
    protected final Setting<Integer> zoomer;
    protected final Map<Integer, TimeVec> posLooks;
    protected final Set<CPacketPlayer> playerPackets;
    protected final AtomicInteger teleportID;
    protected Vec3d vecDelServer;
    protected int packetCounter;
    protected boolean zoomies;
    protected float lastFactor;
    protected int ticks;
    protected int zoomTimer;
    
    public PacketFly() {
        super("PacketFly", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", Mode.Factor));
        this.factor = this.register(new NumberSetting("Factor", 1.0f, 0.0f, 10.0f));
        this.phase = this.register(new EnumSetting("Phase", Phase.Full));
        this.type = this.register(new EnumSetting("Type", Type.Up));
        this.antiKick = this.register(new BooleanSetting("AntiKick", true));
        this.answer = this.register(new BooleanSetting("Answer", false));
        this.bbOffset = this.register(new BooleanSetting("BB-Offset", false));
        this.invalidY = this.register(new NumberSetting("Invalid-Offset", 1337, 0, 1337));
        this.invalids = this.register(new NumberSetting("Invalids", 1, 0, 10));
        this.sendTeleport = this.register(new NumberSetting("Teleport", 1, 0, 10));
        this.concealY = this.register(new NumberSetting("C-Y", 0.0, -256.0, 256.0));
        this.conceal = this.register(new NumberSetting("C-Multiplier", 1.0, 0.0, 2.0));
        this.ySpeed = this.register(new NumberSetting("Y-Multiplier", 1.0, 0.0, 2.0));
        this.xzSpeed = this.register(new NumberSetting("X/Z-Multiplier", 1.0, 0.0, 2.0));
        this.positionRotation = this.register(new BooleanSetting("Position-Rotation", false));
        this.elytra = this.register(new BooleanSetting("Elytra", false));
        this.xzJitter = this.register(new BooleanSetting("Jitter-XZ", false));
        this.yJitter = this.register(new BooleanSetting("Jitter-Y", false));
        this.setPos = this.register(new BooleanSetting("Set-Pos", false));
        this.zeroSpeed = this.register(new BooleanSetting("Zero-Speed", false));
        this.zeroY = this.register(new BooleanSetting("Zero-Y", false));
        this.fixPosition = this.register(new BooleanSetting("FixPosition", true));
        this.zeroTeleport = this.register(new BooleanSetting("Zero-Teleport", true));
        this.zoomer = this.register(new NumberSetting("Zoomies", 3, 0, 10));
        this.posLooks = new ConcurrentHashMap<Integer, TimeVec>();
        this.playerPackets = new HashSet<CPacketPlayer>();
        this.teleportID = new AtomicInteger();
        this.zoomTimer = 0;
        this.listeners.add(new ListenerOverlay(this));
        this.listeners.add(new ListenerBlockPush(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.addAll(new ListenerCPacket(this).getListeners());
        this.setData(new PacketFlyData(this));
    }
    
    @Override
    protected void onEnable() {
        this.clearValues();
        if (PacketFly.mc.player == null) {
            this.disable();
            return;
        }
        if (PacketFly.mc.isSingleplayer()) {
            ModuleUtil.disable(this, "§cCan't enable PacketFly in SinglePlayer!");
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
    
    protected void clearValues() {
        this.lastFactor = 1.0f;
        this.packetCounter = 0;
        this.teleportID.set(0);
        this.playerPackets.clear();
        this.posLooks.clear();
        this.vecDelServer = null;
    }
    
    protected void onPacketSend(final PacketEvent<? extends CPacketPlayer> event) {
        if (!this.playerPackets.remove(event.getPacket())) {
            event.setCancelled(true);
        }
    }
    
    protected boolean isPlayerCollisionBoundingBoxEmpty() {
        final double o = this.bbOffset.getValue() ? -0.0625 : 0.0;
        return !PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().addCoord(o, o, o)).isEmpty();
    }
    
    protected boolean checkPackets(final int amount) {
        if (++this.packetCounter >= amount) {
            this.packetCounter = 0;
            return true;
        }
        return false;
    }
    
    protected void sendPackets(final double x, final double y, final double z, final boolean confirm) {
        final Vec3d offset = new Vec3d(x, y, z);
        final Vec3d vec = PacketFly.mc.player.getPositionVector().add(offset);
        this.vecDelServer = vec;
        Vec3d oOB = this.type.getValue().createOutOfBounds(vec, this.invalidY.getValue());
        if (this.positionRotation.getValue()) {
            this.sendCPacket(PacketUtil.positionRotation(vec.xCoord, vec.yCoord, vec.zCoord, Managers.ROTATION.getServerYaw(), Managers.ROTATION.getServerPitch(), PacketFly.mc.player.onGround));
        }
        else {
            this.sendCPacket(PacketUtil.position(vec.xCoord, vec.yCoord, vec.zCoord));
        }
        final double lastX = Managers.POSITION.getX();
        final double lastY = Managers.POSITION.getY();
        final double lastZ = Managers.POSITION.getZ();
        final boolean last = Managers.POSITION.isOnGround();
        for (int i = 0; i < this.invalids.getValue(); ++i) {
            this.sendCPacket(PacketUtil.position(oOB.xCoord, oOB.yCoord, oOB.zCoord));
            oOB = this.type.getValue().createOutOfBounds(oOB, this.invalidY.getValue());
        }
        if (this.fixPosition.getValue()) {
            Managers.POSITION.set(lastX, lastY, lastZ);
            Managers.POSITION.setOnGround(last);
        }
        if (confirm && (this.zeroTeleport.getValue() || this.teleportID.get() != 0)) {
            for (int i = 0; i < this.sendTeleport.getValue(); ++i) {
                this.sendConfirmTeleport(vec);
            }
        }
        if (this.elytra.getValue()) {
            PacketUtil.sendAction(CPacketEntityAction.Action.START_FALL_FLYING);
        }
    }
    
    protected void sendConfirmTeleport(final Vec3d vec) {
        final int id = this.teleportID.incrementAndGet();
        PacketUtil.teleport(id);
        this.posLooks.put(id, new TimeVec(vec));
    }
    
    protected void sendCPacket(final CPacketPlayer packet) {
        this.playerPackets.add(packet);
        PacketFly.mc.player.connection.sendPacket((Packet)packet);
    }
}
