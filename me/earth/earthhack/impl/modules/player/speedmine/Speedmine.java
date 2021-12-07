//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.automine.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.*;

public class Speedmine extends Module
{
    private static final ModuleCache<AutoMine> AUTO_MINE;
    protected final Setting<MineMode> mode;
    protected final Setting<Boolean> noReset;
    public final Setting<Float> limit;
    protected final Setting<Float> range;
    protected final Setting<Boolean> multiTask;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> event;
    protected final Setting<Boolean> display;
    protected final Setting<Integer> delay;
    protected final Setting<ESPMode> esp;
    protected final Setting<Integer> alpha;
    protected final Setting<Integer> outlineA;
    protected final Setting<Integer> realDelay;
    public final Setting<Boolean> onGround;
    protected final Setting<Boolean> toAir;
    protected final Setting<Boolean> swap;
    protected final Setting<Boolean> requireBreakSlot;
    protected final Setting<Boolean> placeCrystal;
    protected final BindSetting breakBind;
    protected final Setting<Boolean> normal;
    protected final Setting<Boolean> resetAfterPacket;
    protected final Setting<Boolean> checkPacket;
    protected final Setting<Boolean> swingStop;
    protected final Setting<Boolean> limitRotations;
    protected final Setting<Integer> confirm;
    public final float[] damages;
    protected final StopWatch timer;
    protected final StopWatch resetTimer;
    protected BlockPos pos;
    protected EnumFacing facing;
    protected AxisAlignedBB bb;
    protected float[] rotations;
    public float maxDamage;
    protected boolean sentPacket;
    protected boolean shouldAbort;
    protected boolean pausing;
    protected final StopWatch delayTimer;
    protected Packet<?> limitRotationPacket;
    protected int limitRotationSlot;
    
    public Speedmine() {
        super("Speedmine", Category.Player);
        this.mode = this.register(new EnumSetting("Mode", MineMode.Smart));
        this.noReset = this.register(new BooleanSetting("Reset", true));
        this.limit = this.register(new NumberSetting("Damage", 1.0f, 0.0f, 2.0f));
        this.range = this.register(new NumberSetting("Range", 7.0f, 0.1f, 100.0f));
        this.multiTask = this.register(new BooleanSetting("MultiTask", false));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.event = this.register(new BooleanSetting("Event", false));
        this.display = this.register(new BooleanSetting("DisplayDamage", false));
        this.delay = this.register(new NumberSetting("ClickDelay", 100, 0, 500));
        this.esp = this.register(new EnumSetting("ESP", ESPMode.Outline));
        this.alpha = this.register(new NumberSetting("BlockAlpha", 100, 0, 255));
        this.outlineA = this.register(new NumberSetting("OutlineAlpha", 100, 0, 255));
        this.realDelay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.onGround = this.register(new BooleanSetting("OnGround", false));
        this.toAir = this.register(new BooleanSetting("ToAir", false));
        this.swap = this.register(new BooleanSetting("SilentSwitch", false));
        this.requireBreakSlot = this.register(new BooleanSetting("RequireBreakSlot", false));
        this.placeCrystal = this.register(new BooleanSetting("PlaceCrystal", false));
        this.breakBind = this.register(new BindSetting("BreakBind", Bind.none()));
        this.normal = this.register(new BooleanSetting("Normal", false));
        this.resetAfterPacket = this.register(new BooleanSetting("ResetAfterPacket", false));
        this.checkPacket = this.register(new BooleanSetting("CheckPacket", true));
        this.swingStop = this.register(new BooleanSetting("Swing-Stop", true));
        this.limitRotations = this.register(new BooleanSetting("Limit-Rotations", true));
        this.confirm = this.register(new NumberSetting("Confirm", 500, 0, 1000));
        this.damages = new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
        this.timer = new StopWatch();
        this.resetTimer = new StopWatch();
        this.delayTimer = new StopWatch();
        this.limitRotationSlot = -1;
        this.listeners.add(new ListenerDamage(this));
        this.listeners.add(new ListenerReset(this));
        this.listeners.add(new ListenerClick(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerMultiBlockChange(this));
        this.listeners.add(new ListenerDeath(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerDigging(this));
        this.listeners.add(new ListenerKeyPress(this));
        this.setData(new SpeedMineData(this));
    }
    
    @Override
    protected void onEnable() {
        this.reset();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.display.getValue() && this.mode.getValue() == MineMode.Smart) {
            return (this.maxDamage >= this.limit.getValue()) ? ("§a" + MathUtil.round(this.limit.getValue(), 1)) : ("" + MathUtil.round(this.maxDamage, 1));
        }
        return this.mode.getValue().toString();
    }
    
    public void abortCurrentPos() {
        Speedmine.AUTO_MINE.computeIfPresent(a -> a.addToBlackList(this.pos));
        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
        ((IPlayerControllerMP)Speedmine.mc.playerController).setIsHittingBlock(false);
        ((IPlayerControllerMP)Speedmine.mc.playerController).setCurBlockDamageMP(0.0f);
        Speedmine.mc.world.sendBlockBreakProgress(Speedmine.mc.player.getEntityId(), this.pos, -1);
        Speedmine.mc.player.resetCooldown();
        this.reset();
    }
    
    public void reset() {
        this.pos = null;
        this.facing = null;
        this.bb = null;
        this.maxDamage = 0.0f;
        this.sentPacket = false;
        this.limitRotationSlot = -1;
        this.limitRotationPacket = null;
        Speedmine.AUTO_MINE.computeIfPresent(AutoMine::reset);
        for (int i = 0; i < 9; ++i) {
            this.damages[i] = 0.0f;
        }
    }
    
    public MineMode getMode() {
        return this.mode.getValue();
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public StopWatch getTimer() {
        return this.timer;
    }
    
    public float getRange() {
        return this.range.getValue();
    }
    
    public int getBlockAlpha() {
        return this.alpha.getValue();
    }
    
    public int getOutlineAlpha() {
        return this.outlineA.getValue();
    }
    
    public boolean isPausing() {
        return this.pausing;
    }
    
    public void setPausing(final boolean pausing) {
        this.pausing = pausing;
    }
    
    protected boolean sendStopDestroy(final BlockPos pos, final EnumFacing facing, final boolean toAir) {
        final CPacketPlayerDigging stop = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing);
        if (toAir) {
            ((ICPacketPlayerDigging)stop).setClientSideBreaking(true);
        }
        if (this.rotate.getValue() && this.limitRotations.getValue() && !RotationUtil.isLegit(pos, facing)) {
            this.limitRotationPacket = (Packet<?>)stop;
            this.limitRotationSlot = Speedmine.mc.player.inventory.currentItem;
            return false;
        }
        if (this.event.getValue()) {
            Speedmine.mc.player.connection.sendPacket((Packet)stop);
        }
        else {
            NetworkUtil.sendPacketNoEvent((Packet<?>)stop, false);
        }
        this.onSendPacket();
        return true;
    }
    
    protected void postSend(final boolean toAir) {
        if (this.swingStop.getValue()) {
            Swing.Packet.swing(EnumHand.MAIN_HAND);
        }
        if (toAir) {
            Speedmine.mc.playerController.onPlayerDestroyBlock(this.pos);
        }
        if (this.resetAfterPacket.getValue()) {
            this.reset();
        }
    }
    
    public void forceSend() {
        if (this.pos != null) {
            if (this.mode.getValue() == MineMode.Instant) {
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing));
                this.sendStopDestroy(this.pos, this.facing, false);
                if (this.mode.getValue() == MineMode.Instant) {
                    Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
                }
            }
            else if (this.mode.getValue() == MineMode.Civ) {
                this.sendStopDestroy(this.pos, this.facing, false);
            }
        }
    }
    
    public void tryBreak() {
        final int breakSlot;
        if (!this.pausing && ((breakSlot = this.findBreakSlot()) != -1 || this.requireBreakSlot.getValue())) {
            final boolean toAir = this.toAir.getValue();
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                final int lastSlot = Speedmine.mc.player.inventory.currentItem;
                if (breakSlot != -1) {
                    InventoryUtil.switchTo(breakSlot);
                }
                final CPacketPlayerDigging packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing);
                if (toAir) {
                    ((ICPacketPlayerDigging)packet).setClientSideBreaking(true);
                }
                NetworkUtil.sendPacketNoEvent((Packet<?>)packet, false);
                if (breakSlot != -1) {
                    InventoryUtil.switchTo(lastSlot);
                }
                return;
            });
            if (toAir) {
                Speedmine.mc.playerController.onPlayerDestroyBlock(this.pos);
            }
            this.onSendPacket();
        }
    }
    
    private int findBreakSlot() {
        int slot = -1;
        for (int i = 0; i < this.damages.length; ++i) {
            if (this.damages[i] >= this.limit.getValue() && (slot = i) >= Speedmine.mc.player.inventory.currentItem) {
                return slot;
            }
        }
        return slot;
    }
    
    public void checkReset() {
        if (this.sentPacket && this.resetTimer.passed(this.confirm.getValue()) && (this.mode.getValue() == MineMode.Packet || this.mode.getValue() == MineMode.Smart)) {
            this.reset();
        }
    }
    
    public void onSendPacket() {
        this.sentPacket = true;
        this.resetTimer.reset();
    }
    
    static {
        AUTO_MINE = Caches.getModule(AutoMine.class);
    }
}
