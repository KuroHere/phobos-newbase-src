//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.blocklag;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.modules.movement.blocklag.mode.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.visibility.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.*;

public class BlockLag extends DisablingModule
{
    protected static final ModuleCache<Freecam> FREECAM;
    protected final Setting<BlockLagPages> pages;
    protected final Setting<OffsetMode> offsetMode;
    protected final Setting<Double> vClip;
    protected final Setting<Double> minDown;
    protected final Setting<Double> maxDown;
    protected final Setting<Double> minUp;
    protected final Setting<Double> maxUp;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> skipZero;
    protected final Setting<Boolean> fallback;
    protected final Setting<Boolean> air;
    protected final Setting<Boolean> discrete;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> anvil;
    protected final Setting<Boolean> echest;
    protected final Setting<Boolean> beacon;
    protected final Setting<Boolean> allowUp;
    protected final Setting<Boolean> onGround;
    protected final Setting<Boolean> conflict;
    protected final Setting<Boolean> noVoid;
    protected final Setting<Boolean> evade;
    protected final Setting<Boolean> freecam;
    protected final Setting<Boolean> highBlock;
    protected final Setting<Boolean> bypass;
    protected final Setting<Double> bypassOffset;
    protected final Setting<Boolean> wait;
    protected final Setting<Boolean> placeDisable;
    protected final Setting<BlockLagStage> stage;
    protected final Setting<Boolean> deltaY;
    protected final Setting<Boolean> attack;
    protected final Setting<Boolean> instantAttack;
    protected final Setting<Boolean> antiWeakness;
    protected final Setting<Boolean> attackBefore;
    protected final Setting<Pop> pop;
    protected final Setting<Integer> popTime;
    protected final Setting<Integer> cooldown;
    protected final Setting<Boolean> scaleExplosion;
    protected final Setting<Boolean> scaleVelocity;
    protected final Setting<Boolean> scaleDown;
    protected final Setting<Integer> scaleDelay;
    protected final Setting<Double> scaleFactor;
    protected final StopWatch scaleTimer;
    protected final StopWatch timer;
    protected double motionY;
    protected BlockPos startPos;
    
    public BlockLag() {
        super("BlockLag", Category.Movement);
        this.pages = this.register(new EnumSetting("Page", BlockLagPages.Offsets));
        this.offsetMode = this.register(new EnumSetting("Mode", OffsetMode.Smart));
        this.vClip = this.register(new NumberSetting("V-Clip", -9.0, -256.0, 256.0));
        this.minDown = this.register(new NumberSetting("Min-Down", 3.0, 0.0, 1337.0));
        this.maxDown = this.register(new NumberSetting("Max-Down", 10.0, 0.0, 1337.0));
        this.minUp = this.register(new NumberSetting("Min-Up", 3.0, 0.0, 1337.0));
        this.maxUp = this.register(new NumberSetting("Max-Up", 10.0, 0.0, 1337.0));
        this.delay = this.register(new NumberSetting("Delay", 100, 0, 1000));
        this.skipZero = this.register(new BooleanSetting("SkipZero", true));
        this.fallback = this.register(new BooleanSetting("Fallback", true));
        this.air = this.register(new BooleanSetting("Air", false));
        this.discrete = this.register(new BooleanSetting("Discrete", true));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.anvil = this.register(new BooleanSetting("Anvil", false));
        this.echest = this.register(new BooleanSetting("E-Chest", false));
        this.beacon = this.register(new BooleanSetting("Beacon", false));
        this.allowUp = this.register(new BooleanSetting("Allow-Up", false));
        this.onGround = this.register(new BooleanSetting("OnGround", true));
        this.conflict = this.register(new BooleanSetting("Conflict", true));
        this.noVoid = this.register(new BooleanSetting("NoVoid", false));
        this.evade = this.register(new BooleanSetting("Evade", false));
        this.freecam = this.register(new BooleanSetting("Freecam", false));
        this.highBlock = this.register(new BooleanSetting("HighBlock", false));
        this.bypass = this.register(new BooleanSetting("Bypass", false));
        this.bypassOffset = this.register(new NumberSetting("BypassOffset", 0.032, 0.001, 0.1));
        this.wait = this.register(new BooleanSetting("Wait", true));
        this.placeDisable = this.register(new BooleanSetting("PlaceDisable", false));
        this.stage = this.register(new EnumSetting("Stage", BlockLagStage.All));
        this.deltaY = this.register(new BooleanSetting("Delta-Y", true));
        this.attack = this.register(new BooleanSetting("Attack", false));
        this.instantAttack = this.register(new BooleanSetting("Instant-Attack", false));
        this.antiWeakness = this.register(new BooleanSetting("AntiWeakness", false));
        this.attackBefore = this.register(new BooleanSetting("Attack-Before", false));
        this.pop = this.register(new EnumSetting("Pop", Pop.None));
        this.popTime = this.register(new NumberSetting("Pop-Time", 500, 0, 500));
        this.cooldown = this.register(new NumberSetting("Cooldown", 500, 0, 500));
        this.scaleExplosion = this.register(new BooleanSetting("Scale-Explosion", false));
        this.scaleVelocity = this.register(new BooleanSetting("Scale-Velocity", false));
        this.scaleDown = this.register(new BooleanSetting("Scale-Down", false));
        this.scaleDelay = this.register(new NumberSetting("Scale-Delay", 250, 0, 1000));
        this.scaleFactor = this.register(new NumberSetting("Scale-Factor", 1.0, 0.1, 10.0));
        this.scaleTimer = new StopWatch();
        this.timer = new StopWatch();
        this.setData(new BlockLagData(this));
        this.listeners.add(new ListenerMotion(this));
        Bus.EVENT_BUS.register(new ListenerVelocity(this));
        Bus.EVENT_BUS.register(new ListenerExplosion(this));
        Bus.EVENT_BUS.register(new ListenerSpawnObject(this));
        new PageBuilder<BlockLagPages>(this, this.pages).addPage(v -> v == BlockLagPages.Offsets, this.offsetMode, this.discrete).addPage(v -> v == BlockLagPages.Misc, this.rotate, this.deltaY).addPage(v -> v == BlockLagPages.Attack, this.attack, this.cooldown).addPage(v -> v == BlockLagPages.Scale, this.scaleExplosion, this.scaleFactor).register(Visibilities.VISIBILITY_MANAGER);
    }
    
    @Override
    protected void onEnable() {
        this.timer.setTime(0L);
        super.onEnable();
        if (BlockLag.mc.world == null || BlockLag.mc.player == null) {
            return;
        }
        this.startPos = this.getPlayerPos();
        if (this.singlePlayerCheck(this.startPos)) {
            this.disable();
        }
    }
    
    protected void attack(final Packet<?> attacking, final int slot) {
        if (slot != -1) {
            InventoryUtil.switchTo(slot);
        }
        NetworkUtil.send(attacking);
        Swing.Packet.swing(EnumHand.MAIN_HAND);
    }
    
    protected double getY(final Entity entity, final OffsetMode mode) {
        if (mode == OffsetMode.Constant) {
            double y = entity.posY + this.vClip.getValue();
            if (this.evade.getValue() && Math.abs(y) < 1.0) {
                y = -1.0;
            }
            return y;
        }
        double d = this.getY(entity, this.minDown.getValue(), this.maxDown.getValue(), true);
        if (Double.isNaN(d)) {
            d = this.getY(entity, -this.minUp.getValue(), -this.maxUp.getValue(), false);
            if (Double.isNaN(d) && this.fallback.getValue()) {
                return this.getY(entity, OffsetMode.Constant);
            }
        }
        return d;
    }
    
    protected double getY(final Entity entity, final double min, final double max, final boolean add) {
        if ((min > max && add) || (max > min && !add)) {
            return Double.NaN;
        }
        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;
        boolean air = false;
        double lastOff = 0.0;
        BlockPos last = null;
        double off = min;
        while (true) {
            if (add) {
                if (off >= max) {
                    break;
                }
            }
            else if (off <= max) {
                break;
            }
            final BlockPos pos = new BlockPos(x, y - off, z);
            if (!this.noVoid.getValue() || pos.getY() >= 0) {
                if (this.skipZero.getValue() && Math.abs(y) < 1.0) {
                    air = false;
                    last = pos;
                    lastOff = y - off;
                }
                else {
                    final IBlockState state = BlockLag.mc.world.getBlockState(pos);
                    if ((!this.air.getValue() && !state.getMaterial().blocksMovement()) || state.getBlock() == Blocks.AIR) {
                        if (air) {
                            if (add) {
                                return this.discrete.getValue() ? pos.getY() : (y - off);
                            }
                            return this.discrete.getValue() ? last.getY() : lastOff;
                        }
                        else {
                            air = true;
                        }
                    }
                    else {
                        air = false;
                    }
                    last = pos;
                    lastOff = y - off;
                }
            }
            off = (add ? (++off) : (--off));
        }
        return Double.NaN;
    }
    
    protected double applyScale(double value) {
        if ((value < BlockLag.mc.player.posY && !this.scaleDown.getValue()) || (!this.scaleExplosion.getValue() && !this.scaleVelocity.getValue()) || this.scaleTimer.passed(this.scaleDelay.getValue()) || this.motionY == 0.0) {
            return value;
        }
        if (value < BlockLag.mc.player.posY) {
            value -= this.motionY * this.scaleFactor.getValue();
        }
        else {
            value += this.motionY * this.scaleFactor.getValue();
        }
        return this.discrete.getValue() ? Math.floor(value) : value;
    }
    
    protected BlockPos getPlayerPos() {
        return (this.deltaY.getValue() && Math.abs(BlockLag.mc.player.motionY) > 0.1) ? new BlockPos((Entity)BlockLag.mc.player) : PositionUtil.getPosition((Entity)BlockLag.mc.player);
    }
    
    protected boolean isInsideBlock() {
        final double x = BlockLag.mc.player.posX;
        final double y = BlockLag.mc.player.posY + 0.2;
        final double z = BlockLag.mc.player.posZ;
        return BlockLag.mc.world.getBlockState(new BlockPos(x, y, z)).getMaterial().blocksMovement() || !BlockLag.mc.player.isCollidedVertically;
    }
    
    protected boolean singlePlayerCheck(final BlockPos pos) {
        if (!BlockLag.mc.isSingleplayer()) {
            return false;
        }
        final EntityPlayer player = (EntityPlayer)BlockLag.mc.getIntegratedServer().getPlayerList().getPlayerByUUID(BlockLag.mc.player.getUniqueID());
        if (player == null) {
            this.disable();
            return true;
        }
        player.getEntityWorld().setBlockState(pos, ((boolean)this.echest.getValue()) ? Blocks.ENDER_CHEST.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
        BlockLag.mc.world.setBlockState(pos, ((boolean)this.echest.getValue()) ? Blocks.ENDER_CHEST.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
        return true;
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
}
