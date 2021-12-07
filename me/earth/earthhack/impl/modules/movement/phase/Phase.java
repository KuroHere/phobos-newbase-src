//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import io.netty.util.internal.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.setting.event.*;

public class Phase extends BlockESPModule implements CollisionEvent.Listener
{
    protected final Setting<PhaseMode> mode;
    protected final Setting<Boolean> autoClip;
    protected final Setting<Double> blocks;
    protected final Setting<Double> distance;
    protected final Setting<Double> speed;
    protected final Setting<Double> constSpeed;
    protected final Setting<Boolean> constStrafe;
    protected final Setting<Boolean> constTeleport;
    protected final Setting<Boolean> sneakCheck;
    protected final Setting<Boolean> cancel;
    protected final Setting<Boolean> limit;
    protected final Setting<Integer> skipTime;
    protected final Setting<Boolean> onlyBlock;
    protected final Setting<Boolean> cancelSneak;
    protected final Setting<Boolean> autoSneak;
    protected final Setting<Boolean> autoClick;
    protected final Setting<Integer> clickDelay;
    protected final Setting<Boolean> requireClick;
    protected final Setting<Boolean> clickBB;
    protected final Setting<Boolean> requireForward;
    protected final Setting<Boolean> forwardBB;
    protected final Setting<Boolean> smartClick;
    protected final Setting<Boolean> esp;
    protected final ListenerCollision listenerCollision;
    protected final Set<Packet<?>> packets;
    protected final StopWatch clickTimer;
    protected final StopWatch timer;
    protected BlockPos pos;
    protected int delay;
    
    public Phase() {
        super("Phase", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", PhaseMode.Sand));
        this.autoClip = this.register(new BooleanSetting("AutoClip", false));
        this.blocks = this.register(new NumberSetting("Blocks", 0.003, 0.001, 10.0));
        this.distance = this.register(new NumberSetting("Distance", 0.2, 0.0, 10.0));
        this.speed = this.register(new NumberSetting("Speed", 4.0, 0.1, 10.0));
        this.constSpeed = this.register(new NumberSetting("ConstSpeed", 1.0, 0.1, 10.0));
        this.constStrafe = this.register(new BooleanSetting("ConstStrafe", false));
        this.constTeleport = this.register(new BooleanSetting("ConstTeleport", false));
        this.sneakCheck = this.register(new BooleanSetting("SneakCheck", false));
        this.cancel = this.register(new BooleanSetting("Cancel", false));
        this.limit = this.register(new BooleanSetting("Limit", true));
        this.skipTime = this.register(new NumberSetting("Skip-Time", 150, 0, 1000));
        this.onlyBlock = this.register(new BooleanSetting("OnlyInBlock", false));
        this.cancelSneak = this.register(new BooleanSetting("CancelSneak", false));
        this.autoSneak = this.register(new BooleanSetting("AutoSneak", false));
        this.autoClick = this.register(new BooleanSetting("AutoClick", false));
        this.clickDelay = this.register(new NumberSetting("Click-Delay", 250, 0, 1000));
        this.requireClick = this.register(new BooleanSetting("RequireClick", false));
        this.clickBB = this.register(new BooleanSetting("Click-BB", false));
        this.requireForward = this.register(new BooleanSetting("RequireForward", false));
        this.forwardBB = this.register(new BooleanSetting("Forward-BB", false));
        this.smartClick = this.register(new BooleanSetting("SmartClick", false));
        this.esp = this.register(new BooleanSetting("ESP", false));
        this.packets = (Set<Packet<?>>)new ConcurrentSet();
        this.clickTimer = new StopWatch();
        this.timer = new StopWatch();
        this.listenerCollision = new ListenerCollision(this);
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerSuffocation(this));
        this.listeners.add(new ListenerBlockPush(this));
        this.listeners.add(new ListenerInput(this));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerSneak(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.addAll(new ListenerCPackets(this).getListeners());
        this.unregister(super.color);
        this.unregister(super.outline);
        this.unregister(super.lineWidth);
        this.unregister(super.height);
        super.height.addObserver(e -> {
            e.setValue(1.0f);
            e.setCancelled(true);
            return;
        });
        this.register(super.color);
        this.register(super.outline);
        this.register((Setting<Float>)super.lineWidth);
        this.setData(new PhaseData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    @Override
    protected void onEnable() {
        this.delay = 0;
        final EntityPlayerSP player = Phase.mc.player;
        if (player != null && this.autoClip.getValue()) {
            final double yawCos = Math.cos(Math.toRadians(player.rotationYaw + 90.0f));
            final double yawSin = Math.sin(Math.toRadians(player.rotationYaw + 90.0f));
            player.setPosition(player.posX + 1.0 * this.blocks.getValue() * yawCos + 0.0 * this.blocks.getValue() * yawSin, player.posY, player.posZ + (1.0 * this.blocks.getValue() * yawSin - 0.0 * this.blocks.getValue() * yawCos));
        }
    }
    
    @Override
    protected void onDisable() {
        if (Phase.mc.player != null) {
            Phase.mc.player.noClip = false;
        }
    }
    
    @Override
    public void onCollision(final CollisionEvent event) {
        if (this.isEnabled()) {
            this.listenerCollision.invoke(event);
        }
    }
    
    public boolean isPhasing() {
        final AxisAlignedBB bb = Phase.mc.player.getEntityBoundingBox();
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
            for (int y = MathHelper.floor(bb.minY); y < MathHelper.floor(bb.maxY) + 1; ++y) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                    if (Phase.mc.world.getBlockState(new BlockPos(x, y, z)).getMaterial().blocksMovement() && bb.intersectsWith(new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected void send(final Packet<?> packet) {
        this.packets.add(packet);
        Phase.mc.player.connection.sendPacket((Packet)packet);
    }
    
    protected void onPacket(final PacketEvent.Send<? extends CPacketPlayer> event) {
        if (this.mode.getValue() == PhaseMode.ConstantiamNew && !MovementUtil.isMoving() && Phase.mc.player.posY == Phase.mc.player.lastTickPosY) {
            event.setCancelled(true);
        }
    }
}
