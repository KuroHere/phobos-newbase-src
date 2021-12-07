//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.legswitch.modes.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.path.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.*;

public class LegSwitch extends RemovingItemAddingModule
{
    private static final ModuleCache<AutoCrystal> AUTO_CRYSTAL;
    protected final Setting<LegAutoSwitch> autoSwitch;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> closest;
    protected final Setting<ACRotate> rotate;
    protected final Setting<Float> minDamage;
    protected final Setting<Float> maxSelfDamage;
    protected final Setting<Float> placeRange;
    protected final Setting<Float> placeTrace;
    protected final Setting<Float> breakRange;
    protected final Setting<Float> breakTrace;
    protected final Setting<Float> combinedTrace;
    protected final Setting<Boolean> instant;
    protected final Setting<Boolean> setDead;
    protected final Setting<Boolean> requireMid;
    protected final Setting<Boolean> soundRemove;
    protected final Setting<Boolean> antiWeakness;
    protected final Setting<Boolean> soundStart;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> newVerEntities;
    protected final Setting<Boolean> rotationPacket;
    protected final Setting<Integer> coolDown;
    protected final Setting<Float> targetRange;
    protected final Setting<Boolean> breakBlock;
    protected final Setting<Boolean> obsidian;
    protected final Setting<Integer> helpingBlocks;
    protected final Setting<RayTraceMode> smartRay;
    protected final Setting<Rotate> obbyRotate;
    protected final Setting<Boolean> normalRotate;
    protected final Setting<Boolean> setBlockState;
    protected final Setting<PlaceSwing> obbySwing;
    protected final SoundObserver observer;
    protected final DiscreteTimer timer;
    protected LegConstellation constellation;
    protected volatile boolean active;
    protected BlockPos targetPos;
    protected float[] bRotations;
    protected float[] rotations;
    protected Runnable post;
    protected int slot;
    
    public LegSwitch() {
        super("LegSwitch", Category.Combat, s -> "Black/Whitelist LegSwitch from being active while you hold " + s.getName() + ".");
        this.autoSwitch = this.register(new EnumSetting("AutoSwitch", LegAutoSwitch.None));
        this.delay = this.register(new NumberSetting("Delay", 500, 0, 500));
        this.closest = this.register(new BooleanSetting("Closest", true));
        this.rotate = this.register(new EnumSetting("Rotate", ACRotate.None));
        this.minDamage = this.register(new NumberSetting("MinDamage", 7.0f, 0.0f, 36.0f));
        this.maxSelfDamage = this.register(new NumberSetting("MaxSelfDamage", 4.0f, 0.0f, 36.0f));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0f, 0.0f, 6.0f));
        this.placeTrace = this.register(new NumberSetting("PlaceTrace", 6.0f, 0.0f, 6.0f));
        this.breakRange = this.register(new NumberSetting("BreakRange", 6.0f, 0.0f, 6.0f));
        this.breakTrace = this.register(new NumberSetting("BreakTrace", 3.0f, 0.0f, 6.0f));
        this.combinedTrace = this.register(new NumberSetting("CombinedTrace", 3.0f, 0.0f, 6.0f));
        this.instant = this.register(new BooleanSetting("Instant", true));
        this.setDead = this.register(new BooleanSetting("SetDead", false));
        this.requireMid = this.register(new BooleanSetting("Mid", false));
        this.soundRemove = this.register(new BooleanSetting("SoundRemove", true));
        this.antiWeakness = this.register(new BooleanSetting("AntiWeakness", false));
        this.soundStart = this.register(new BooleanSetting("SoundStart", false));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.rotationPacket = this.register(new BooleanSetting("Rotation-Packet", false));
        this.coolDown = this.register(new NumberSetting("CoolDown", 0, 0, 500));
        this.targetRange = this.register(new NumberSetting("Target-Range", 10.0f, 0.0f, 20.0f));
        this.breakBlock = this.register(new BooleanSetting("BlockStart", false));
        this.obsidian = this.register(new BooleanSetting("Obsidian", false));
        this.helpingBlocks = this.register(new NumberSetting("HelpingBlocks", 1, 1, 10));
        this.smartRay = this.register(new EnumSetting("Raytrace", RayTraceMode.Fast));
        this.obbyRotate = this.register(new EnumSetting("Obby-Rotate", Rotate.None));
        this.normalRotate = this.register(new BooleanSetting("NormalRotate", false));
        this.setBlockState = this.register(new BooleanSetting("SetBlockState", false));
        this.obbySwing = this.register(new EnumSetting("ObbySwing", PlaceSwing.Once));
        this.observer = new ListenerSound(this);
        this.timer = new GuardTimer(500L);
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerBlockMulti(this));
        this.listeners.add(new ListenerBlockBreak(this));
        this.listType.setValue(ListType.BlackList);
        this.setData(new LegSwitchData(this));
    }
    
    public void onEnable() {
        Managers.SET_DEAD.addObserver(this.observer);
    }
    
    public void onDisable() {
        Managers.SET_DEAD.removeObserver(this.observer);
        this.active = false;
        this.constellation = null;
    }
    
    @Override
    public String getDisplayInfo() {
        return (this.constellation == null || !this.active) ? null : this.constellation.player.getName();
    }
    
    public boolean isActive() {
        return this.isEnabled() && this.active;
    }
    
    protected void startCalculation() {
        this.startCalculation((IBlockAccess)LegSwitch.mc.world);
    }
    
    protected void startCalculation(final IBlockAccess access) {
        if (!this.isStackValid(LegSwitch.mc.player.getHeldItemOffhand()) && !this.isStackValid(LegSwitch.mc.player.getHeldItemMainhand())) {
            this.active = false;
            return;
        }
        if (this.constellation == null || !this.constellation.isValid(this, (EntityPlayer)LegSwitch.mc.player, access)) {
            this.constellation = ConstellationFactory.create(this, LegSwitch.mc.world.playerEntities);
            if (this.constellation != null && !this.obsidian.getValue() && (this.constellation.firstNeedsObby || this.constellation.secondNeedsObby)) {
                this.constellation = null;
            }
        }
        if (this.constellation == null) {
            this.active = false;
        }
        this.active = true;
        this.prepare();
        this.execute();
    }
    
    protected boolean isValid(final BlockPos pos, final IBlockState state, final List<EntityPlayer> players) {
        if (state.getBlock() != Blocks.AIR || players == null) {
            return false;
        }
        for (final EntityPlayer player : players) {
            if (player != null && !Managers.FRIENDS.contains(player) && player.getDistanceSq(pos) < 4.0) {
                return true;
            }
        }
        return false;
    }
    
    protected void prepare() {
        if (!this.timer.passed(this.delay.getValue())) {
            return;
        }
        int weakSlot = -1;
        if (!DamageUtil.canBreakWeakness(true) && (!this.antiWeakness.getValue() || this.coolDown.getValue() != 0 || (weakSlot = DamageUtil.findAntiWeakness()) == -1)) {
            return;
        }
        if (this.constellation == null) {
            this.targetPos = null;
            return;
        }
        Entity crystal = null;
        for (final Entity entity : LegSwitch.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal && !entity.isDead && entity.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(this.constellation.targetPos))) {
                crystal = entity;
                break;
            }
        }
        this.targetPos = this.constellation.firstPos;
        boolean firstNeedsObby = true;
        BlockPos obbyPos = this.constellation.firstNeedsObby ? this.constellation.firstPos : null;
        if (crystal != null) {
            if (Managers.SWITCH.getLastSwitch() < this.coolDown.getValue()) {
                return;
            }
            if (crystal.getPosition().down().equals((Object)this.constellation.firstPos)) {
                obbyPos = (this.constellation.secondNeedsObby ? this.constellation.secondPos : null);
                this.targetPos = this.constellation.secondPos;
                firstNeedsObby = false;
            }
            this.bRotations = RotationUtil.getRotations(crystal);
        }
        int obbySlot = -1;
        Pathable path = null;
        if (obbyPos != null) {
            obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
            if (obbySlot == -1) {
                return;
            }
            path = new BasePath((Entity)RotationUtil.getRotationPlayer(), obbyPos, this.helpingBlocks.getValue());
            final boolean newVersion = this.newVer.getValue();
            final BlockPos[] blacklist = new BlockPos[newVersion ? 4 : 6];
            blacklist[0] = this.constellation.playerPos;
            blacklist[1] = this.constellation.secondPos.up();
            blacklist[2] = this.constellation.firstPos.up();
            blacklist[3] = this.constellation.targetPos;
            if (!newVersion) {
                blacklist[4] = this.constellation.secondPos.up(2);
                blacklist[5] = this.constellation.firstPos.up(2);
            }
            PathFinder.findPath(path, this.placeRange.getValue(), LegSwitch.mc.world.loadedEntityList, this.smartRay.getValue(), ObbyModule.HELPER, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, blacklist);
            if (!path.isValid() || (path.getPath().length > 1 && this.normalRotate.getValue() && this.obbyRotate.getValue() == Rotate.Normal)) {
                this.constellation.invalid = true;
                return;
            }
        }
        assert this.targetPos != null;
        if (path != null) {
            this.rotations = path.getPath()[0].getRotations();
        }
        else {
            this.rotations = RotationUtil.getRotationsToTopMiddle(this.targetPos.up());
        }
        Ray last = null;
        RayTraceResult result;
        if (!this.rotate.getValue().noRotate(ACRotate.Place)) {
            float[] theRotations = this.rotations;
            IBlockAccess access = (IBlockAccess)LegSwitch.mc.world;
            if (path != null) {
                last = path.getPath()[path.getPath().length - 1];
                theRotations = last.getRotations();
                final BlockStateHelper helper = new BlockStateHelper();
                helper.addBlockState(last.getPos().offset(last.getFacing()), Blocks.OBSIDIAN.getDefaultState());
                access = (IBlockAccess)helper;
            }
            final BlockPos thePos = this.targetPos.up();
            result = RotationUtil.rayTraceWithYP(thePos, access, theRotations[0], theRotations[1], (b, p) -> p.equals((Object)thePos));
        }
        else {
            result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
            this.rotations = null;
        }
        final Entity finalCrystal = crystal;
        if (this.rotationPacket.getValue() && this.rotations != null && this.bRotations != null && finalCrystal != null) {
            final int finalWeakSlot = weakSlot;
            final Runnable runnable = () -> {
                LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.bRotations[0], this.bRotations[1], LegSwitch.mc.player.onGround));
                final int lastSlot = LegSwitch.mc.player.inventory.currentItem;
                if (finalWeakSlot != -1) {
                    InventoryUtil.switchTo(finalWeakSlot);
                }
                LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(finalCrystal));
                LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                this.bRotations = null;
                InventoryUtil.switchTo(lastSlot);
                return;
            };
            if (finalWeakSlot != -1) {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, runnable);
            }
            else {
                runnable.run();
            }
            if (this.setDead.getValue()) {
                Managers.SET_DEAD.setDead(finalCrystal);
            }
        }
        if (this.rotations == null) {
            this.rotations = this.bRotations;
        }
        final Pathable finalPath = path;
        final int finalObbySlot = obbySlot;
        final boolean finalFirstNeedsObby = firstNeedsObby;
        final LegConstellation finalConstellation = this.constellation;
        this.post = Locks.wrap(Locks.PLACE_SWITCH_LOCK, () -> {
            int slot = -1;
            final int lastSlot2 = LegSwitch.mc.player.inventory.currentItem;
            if (!InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                slot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
                if (this.autoSwitch.getValue() == LegAutoSwitch.None || slot == -1) {
                    this.active = false;
                    return;
                }
            }
            final EnumHand hand = (LegSwitch.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || slot != -2) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.bRotations != null && finalCrystal != null) {
                LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(finalCrystal));
                LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
            if (finalPath != null) {
                InventoryUtil.switchTo(finalObbySlot);
                for (int i = 0; i < finalPath.getPath().length; ++i) {
                    final Ray ray = finalPath.getPath()[i];
                    if (i != 0 && this.obbyRotate.getValue() == Rotate.Packet) {
                        Managers.ROTATION.setBlocking(true);
                        final float[] r = ray.getRotations();
                        LegSwitch.mc.player.connection.sendPacket((Packet)PacketUtil.rotation(r[0], r[1], LegSwitch.mc.player.onGround));
                        Managers.ROTATION.setBlocking(false);
                    }
                    final float[] f = RayTraceUtil.hitVecToPlaceVec(ray.getPos(), ray.getResult().hitVec);
                    LegSwitch.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(ray.getPos(), ray.getFacing(), hand, f[0], f[1], f[2]));
                    if (this.setBlockState.getValue()) {
                        LegSwitch.mc.addScheduledTask(() -> {
                            if (LegSwitch.mc.world != null) {
                                finalConstellation.states.put(ray.getPos().offset(ray.getFacing()), Blocks.OBSIDIAN.getDefaultState());
                                LegSwitch.mc.world.setBlockState(ray.getPos().offset(ray.getFacing()), Blocks.OBSIDIAN.getDefaultState());
                            }
                            return;
                        });
                    }
                    if (this.obbySwing.getValue() == PlaceSwing.Always) {
                        Swing.Packet.swing(hand);
                    }
                }
                final Ray ray2 = finalPath.getPath()[finalPath.getPath().length - 1];
                final BlockPos last2 = ray2.getPos().offset(ray2.getFacing());
                Managers.BLOCKS.addCallback(last2, s -> {
                    if (s.getBlock() == Blocks.OBSIDIAN) {
                        if (finalFirstNeedsObby) {
                            finalConstellation.firstNeedsObby = false;
                        }
                        else {
                            finalConstellation.secondNeedsObby = false;
                        }
                    }
                    finalConstellation.states.put(last, s);
                    return;
                });
                if (this.obbySwing.getValue() == PlaceSwing.Once) {
                    Swing.Packet.swing(hand);
                }
            }
            if (slot != -1) {
                InventoryUtil.switchTo(slot);
            }
            else {
                InventoryUtil.syncItem();
            }
            final CPacketPlayerTryUseItemOnBlock place = new CPacketPlayerTryUseItemOnBlock(this.targetPos, result.sideHit, hand, (float)result.hitVec.xCoord, (float)result.hitVec.yCoord, (float)result.hitVec.zCoord);
            final CPacketAnimation animation = new CPacketAnimation(hand);
            LegSwitch.mc.player.connection.sendPacket((Packet)place);
            LegSwitch.mc.player.connection.sendPacket((Packet)animation);
            if (slot != -1 && this.autoSwitch.getValue() != LegAutoSwitch.Keep) {
                InventoryUtil.switchTo(lastSlot2);
            }
            LegSwitch.AUTO_CRYSTAL.computeIfPresent(a -> a.setRenderPos(this.targetPos, "LS"));
            if (this.setDead.getValue() && finalCrystal != null) {
                Managers.SET_DEAD.setDead(finalCrystal);
            }
            return;
        });
        this.timer.reset(this.delay.getValue());
        if (this.rotate.getValue().noRotate(ACRotate.Place)) {
            this.execute();
        }
    }
    
    protected void execute() {
        if (this.post != null) {
            this.active = true;
            this.post.run();
        }
        this.post = null;
        this.bRotations = null;
        this.rotations = null;
    }
    
    protected boolean checkPos(final BlockPos pos) {
        if (BlockUtil.getDistanceSq(pos) <= MathUtil.square(this.placeRange.getValue()) && LegSwitch.mc.player.getDistanceSq(pos) > MathUtil.square(this.placeTrace.getValue()) && !RayTraceUtil.raytracePlaceCheck((Entity)LegSwitch.mc.player, pos)) {
            return false;
        }
        final BlockPos up = pos.up();
        final BlockPos upUp = up.up();
        return LegSwitch.mc.world.getBlockState(up).getBlock() == Blocks.AIR && (this.newVer.getValue() || LegSwitch.mc.world.getBlockState(upUp).getBlock() == Blocks.AIR) && BlockUtil.checkEntityList(up, true, null) && (!this.newVerEntities.getValue() || BlockUtil.checkEntityList(upUp, true, null)) && (BlockUtil.getDistanceSq(pos) <= MathUtil.square(this.combinedTrace.getValue()) || RayTraceUtil.canBeSeen(new Vec3d(pos.getX() + 0.5, pos.getY() + 2.7, pos.getZ() + 0.5), (Entity)LegSwitch.mc.player));
    }
    
    static {
        AUTO_CRYSTAL = Caches.getModule(AutoCrystal.class);
    }
}
