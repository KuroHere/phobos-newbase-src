//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bomber;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.combat.bomber.enums.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.managers.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.*;

public class CrystalBomber extends Module
{
    protected final Setting<CrystalBomberMode> mode;
    protected final Setting<Float> range;
    protected final Setting<Float> toggleAt;
    protected final Setting<Float> enemyRange;
    protected final Setting<Integer> delay;
    protected final Setting<Integer> cooldown;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> reCheckCrystal;
    protected final Setting<Boolean> airCheck;
    protected final Setting<Boolean> smartSneak;
    protected final Setting<Boolean> bypass;
    private static final ModuleCache<Speedmine> SPEEDMINE;
    private static EntityPlayer target;
    private Vec3d lastTargetPos;
    private BlockPos targetPos;
    private int lastSlot;
    private boolean hasHit;
    public boolean rotating;
    private float yaw;
    private float targetYaw;
    private float pitch;
    private int rotationPacketsSpoofed;
    private boolean offhand;
    private final StopWatch timer;
    private final StopWatch delayTimer;
    private final StopWatch cooldownTimer;
    private CrystalBomberStage stage;
    private boolean firstHit;
    
    public CrystalBomber() {
        super("CrystalBomber", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", CrystalBomberMode.Normal));
        this.range = this.register(new NumberSetting("Range", 6.0f, 0.1f, 6.0f));
        this.toggleAt = this.register(new NumberSetting("ToggleAt", 8.0f, 0.1f, 20.0f));
        this.enemyRange = this.register(new NumberSetting("EnemyRange", 6.0f, 0.1f, 16.0f));
        this.delay = this.register(new NumberSetting("Delay", 0, 0, 500));
        this.cooldown = this.register(new NumberSetting("Cooldown", 0, 0, 500));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.reCheckCrystal = this.register(new BooleanSetting("ReCheckCrystal", false));
        this.airCheck = this.register(new BooleanSetting("AirCheck", false));
        this.smartSneak = this.register(new BooleanSetting("Smart-Sneak", true));
        this.bypass = this.register(new BooleanSetting("Bypass", false));
        this.rotating = false;
        this.yaw = 0.0f;
        this.targetYaw = 0.0f;
        this.pitch = 0.0f;
        this.rotationPacketsSpoofed = 0;
        this.timer = new StopWatch();
        this.delayTimer = new StopWatch();
        this.cooldownTimer = new StopWatch();
        this.stage = CrystalBomberStage.FirstHit;
        this.firstHit = false;
        this.listeners.add(new ListenerMotion(this));
    }
    
    @Override
    protected void onEnable() {
        this.targetPos = null;
        this.lastTargetPos = null;
        CrystalBomber.target = null;
        this.stage = CrystalBomberStage.FirstHit;
        this.timer.reset();
        this.delayTimer.reset();
        this.cooldownTimer.reset();
    }
    
    protected void doCrystalBomber(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            this.updateTarget();
            if (CrystalBomber.target != null) {
                if (this.targetPos != null) {
                    this.lastTargetPos = new Vec3d((Vec3i)this.targetPos);
                }
                this.targetPos = PositionUtil.getPosition((Entity)CrystalBomber.target).up().up();
                if (this.lastTargetPos != null && !this.lastTargetPos.equals((Object)new Vec3d((Vec3i)this.targetPos))) {
                    this.stage = CrystalBomberStage.FirstHit;
                    this.firstHit = true;
                }
                if (this.delayTimer.passed(this.delay.getValue())) {
                    if (this.reCheckCrystal.getValue()) {
                        this.recheckCrystal();
                    }
                    Label_0499: {
                        switch (this.stage) {
                            case FirstHit: {
                                if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() != Blocks.AIR && MineUtil.canBreak(this.targetPos)) {
                                    this.rotateToPos(this.targetPos, event);
                                    break;
                                }
                            }
                            case Crystal: {
                                if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() != Blocks.OBSIDIAN) {
                                    this.stage = CrystalBomberStage.PlaceObsidian;
                                    this.delayTimer.reset();
                                    break;
                                }
                                if (BlockUtil.canPlaceCrystal(this.targetPos, false, false)) {
                                    this.rotateToPos(this.targetPos, event);
                                    break;
                                }
                                break;
                            }
                            case Pickaxe: {
                                if (!this.firstHit) {
                                    this.rotateToPos(this.targetPos, event);
                                    break;
                                }
                                if (this.isValidForMining()) {
                                    this.rotateToPos(this.targetPos, event);
                                    break;
                                }
                                break;
                            }
                            case Explode: {
                                final List<Entity> crystals = CrystalBomber.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(this.targetPos.up()));
                                if (!crystals.isEmpty()) {
                                    if ((CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() != Blocks.AIR && this.airCheck.getValue()) || !this.cooldownTimer.passed(this.cooldown.getValue())) {
                                        break Label_0499;
                                    }
                                    final EntityEnderCrystal crystal = (EntityEnderCrystal)crystals.get(0);
                                    if (crystal != null) {
                                        this.rotateTo((Entity)crystal, event);
                                        break;
                                    }
                                    break Label_0499;
                                }
                                else {
                                    if (this.reCheckCrystal.getValue()) {
                                        this.stage = CrystalBomberStage.Crystal;
                                        this.delayTimer.reset();
                                        break;
                                    }
                                    break Label_0499;
                                }
                                break;
                            }
                            case PlaceObsidian: {
                                final int obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
                                final boolean offhand = CrystalBomber.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock && ((ItemBlock)CrystalBomber.mc.player.getHeldItemOffhand().getItem()).getBlock() == Blocks.OBSIDIAN;
                                if (obbySlot == -1 && !offhand) {
                                    break;
                                }
                                if (BlockUtil.isReplaceable(this.targetPos)) {
                                    if (CrystalBomber.mc.player.getDistanceSq(this.targetPos) <= MathUtil.square(this.range.getValue())) {
                                        this.rotateToPos(this.targetPos, event);
                                        break;
                                    }
                                    break;
                                }
                                else {
                                    if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() != Blocks.OBSIDIAN) {
                                        break;
                                    }
                                    if (this.mode.getValue() == CrystalBomberMode.Instant) {
                                        this.stage = CrystalBomberStage.Crystal;
                                        break;
                                    }
                                    this.stage = CrystalBomberStage.FirstHit;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        else if (event.getStage() == Stage.POST) {
            this.updateTarget();
            if (CrystalBomber.target != null && this.delayTimer.passed(this.delay.getValue())) {
                Label_1716: {
                    switch (this.stage) {
                        case FirstHit: {
                            if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() != Blocks.AIR) {
                                if (CrystalBomber.SPEEDMINE.get().getPos() == null || !new Vec3d((Vec3i)CrystalBomber.SPEEDMINE.get().getPos()).equals((Object)new Vec3d((Vec3i)this.targetPos))) {
                                    CrystalBomber.mc.playerController.onPlayerDamageBlock(this.targetPos, CrystalBomber.mc.player.getHorizontalFacing().getOpposite());
                                }
                                else if (new Vec3d((Vec3i)CrystalBomber.SPEEDMINE.get().getPos()).equals((Object)new Vec3d((Vec3i)this.targetPos)) && (CrystalBomber.SPEEDMINE.get().getMode() == MineMode.Instant || CrystalBomber.SPEEDMINE.get().getMode() == MineMode.Civ)) {
                                    this.stage = CrystalBomberStage.Crystal;
                                    this.delayTimer.reset();
                                    this.timer.reset();
                                    this.firstHit = false;
                                    break;
                                }
                                this.stage = CrystalBomberStage.Crystal;
                                this.delayTimer.reset();
                                this.timer.reset();
                                this.firstHit = true;
                                break;
                            }
                        }
                        case Crystal: {
                            final int crystalSlot = this.getCrsytalSlot();
                            if (!(this.offhand = (CrystalBomber.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL))) {
                                this.lastSlot = CrystalBomber.mc.player.inventory.currentItem;
                                if (crystalSlot != -1) {
                                    if (this.bypass.getValue()) {
                                        InventoryUtil.switchToBypass(crystalSlot);
                                    }
                                    else {
                                        InventoryUtil.switchTo(crystalSlot);
                                    }
                                }
                            }
                            if ((this.offhand || CrystalBomber.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) && CrystalBomber.mc.player.getDistanceSq(this.targetPos) <= MathUtil.square(this.range.getValue())) {
                                placeCrystalOnBlock(this.targetPos, this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, true, false);
                            }
                            this.stage = CrystalBomberStage.Pickaxe;
                            this.delayTimer.reset();
                            break;
                        }
                        case Pickaxe: {
                            if (this.firstHit) {
                                if (!this.isValidForMining()) {
                                    break Label_1716;
                                }
                                final int pickSlot = this.getPickSlot();
                                final int lastSlot = CrystalBomber.mc.player.inventory.currentItem;
                                if (pickSlot != -1) {
                                    if (this.bypass.getValue()) {
                                        InventoryUtil.switchToBypass(pickSlot);
                                    }
                                    else {
                                        InventoryUtil.switchTo(pickSlot);
                                    }
                                    CrystalBomber.SPEEDMINE.get().forceSend();
                                    this.stage = CrystalBomberStage.Explode;
                                    if (this.bypass.getValue()) {
                                        InventoryUtil.switchToBypass(pickSlot);
                                    }
                                    else {
                                        InventoryUtil.switchTo(lastSlot);
                                    }
                                    this.delayTimer.reset();
                                    this.cooldownTimer.reset();
                                    this.firstHit = false;
                                    break;
                                }
                                break Label_1716;
                            }
                            else {
                                final int pickSlot = this.getPickSlot();
                                final int lastSlot = CrystalBomber.mc.player.inventory.currentItem;
                                if (pickSlot == -1) {
                                    break Label_1716;
                                }
                                if (this.bypass.getValue()) {
                                    InventoryUtil.switchToBypass(pickSlot);
                                }
                                else {
                                    InventoryUtil.switchTo(pickSlot);
                                }
                                CrystalBomber.SPEEDMINE.get().forceSend();
                                this.stage = CrystalBomberStage.Explode;
                                this.delayTimer.reset();
                                this.cooldownTimer.reset();
                                if (this.bypass.getValue()) {
                                    InventoryUtil.switchToBypass(lastSlot);
                                    break;
                                }
                                InventoryUtil.switchTo(lastSlot);
                                break;
                            }
                            break;
                        }
                        case Explode: {
                            final List<Entity> crystals2 = CrystalBomber.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(this.targetPos.up()));
                            if (!this.cooldownTimer.passed(this.cooldown.getValue())) {
                                this.stage = CrystalBomberStage.Explode;
                                break;
                            }
                            if (!crystals2.isEmpty() && (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() == Blocks.AIR || !this.airCheck.getValue())) {
                                final EntityEnderCrystal crystal2 = (EntityEnderCrystal)crystals2.get(0);
                                if (crystal2 != null) {
                                    this.rotating = false;
                                    if (CrystalBomber.mc.player.getDistanceSqToEntity((Entity)crystal2) <= MathUtil.square(this.range.getValue())) {
                                        attackEntity((Entity)crystal2, true, true);
                                        this.stage = CrystalBomberStage.PlaceObsidian;
                                        this.delayTimer.reset();
                                        break;
                                    }
                                }
                                else if (this.reCheckCrystal.getValue()) {
                                    this.stage = CrystalBomberStage.Crystal;
                                    this.delayTimer.reset();
                                    break;
                                }
                                break Label_1716;
                            }
                            if (this.reCheckCrystal.getValue()) {
                                this.stage = CrystalBomberStage.Crystal;
                                this.delayTimer.reset();
                                break;
                            }
                            break Label_1716;
                        }
                        case PlaceObsidian: {
                            final int obbySlot2 = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
                            final boolean offhand2 = CrystalBomber.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock && ((ItemBlock)CrystalBomber.mc.player.getHeldItemOffhand().getItem()).getBlock() == Blocks.OBSIDIAN;
                            if (obbySlot2 == -1 && !offhand2) {
                                break;
                            }
                            if (BlockUtil.isReplaceable(this.targetPos) || BlockUtil.isAir(this.targetPos)) {
                                if (CrystalBomber.mc.player.getDistanceSq(this.targetPos) <= MathUtil.square(this.range.getValue())) {
                                    final EnumFacing facing = BlockUtil.getFacing(this.targetPos);
                                    if (facing != null) {
                                        final float[] rotations = RotationUtil.getRotations(this.targetPos.offset(facing), facing.getOpposite());
                                        this.placeBlock(this.targetPos.offset(facing), facing.getOpposite(), rotations, obbySlot2);
                                    }
                                }
                                if (this.mode.getValue() == CrystalBomberMode.Instant) {
                                    this.stage = CrystalBomberStage.Crystal;
                                }
                                else {
                                    this.stage = CrystalBomberStage.FirstHit;
                                }
                                this.delayTimer.reset();
                                break;
                            }
                            if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() == Blocks.OBSIDIAN) {
                                if (this.mode.getValue() == CrystalBomberMode.Instant) {
                                    this.stage = CrystalBomberStage.Crystal;
                                }
                                else {
                                    this.stage = CrystalBomberStage.FirstHit;
                                }
                                this.delayTimer.reset();
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void updateTarget() {
        final List<EntityPlayer> players = (List<EntityPlayer>)CrystalBomber.mc.world.playerEntities.stream().filter(entity -> CrystalBomber.mc.player.getDistanceSqToEntity((Entity)entity) <= MathUtil.square(this.enemyRange.getValue())).filter(entity -> !Managers.FRIENDS.contains(entity)).collect(Collectors.toList());
        EntityPlayer currentPlayer = null;
        for (final EntityPlayer player : players) {
            if (player == CrystalBomber.mc.player) {
                continue;
            }
            if (currentPlayer == null) {
                currentPlayer = player;
            }
            if (CrystalBomber.mc.player.getDistanceSqToEntity((Entity)player) >= CrystalBomber.mc.player.getDistanceSqToEntity((Entity)currentPlayer)) {
                continue;
            }
            currentPlayer = player;
        }
        CrystalBomber.target = currentPlayer;
    }
    
    private int getPickSlot() {
        for (int i = 0; i < 9; ++i) {
            if (CrystalBomber.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_PICKAXE) {
                return i;
            }
        }
        return -1;
    }
    
    private int getCrsytalSlot() {
        for (int i = 0; i < 9; ++i) {
            if (CrystalBomber.mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }
    
    private void rotateTo(final Entity entity, final MotionUpdateEvent event) {
        if (this.rotate.getValue()) {
            final float[] angle = RotationUtil.getRotations(entity);
            event.setYaw(angle[0]);
            event.setPitch(angle[1]);
        }
    }
    
    private void rotateToPos(final BlockPos pos, final MotionUpdateEvent event) {
        if (this.rotate.getValue()) {
            final float[] angle = RotationUtil.getRotationsToTopMiddle(pos);
            event.setYaw(angle[0]);
            event.setPitch(angle[1]);
        }
    }
    
    private void recheckCrystal() {
        if (CrystalBomber.mc.world.getBlockState(this.targetPos).getBlock() == Blocks.OBSIDIAN && CrystalBomber.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(this.targetPos.up())).isEmpty() && this.stage != CrystalBomberStage.FirstHit) {
            this.stage = CrystalBomberStage.Crystal;
        }
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand, final boolean swing, final boolean exactHand) {
        final RayTraceResult result = CrystalBomber.mc.world.rayTraceBlocks(new Vec3d(CrystalBomber.mc.player.posX, CrystalBomber.mc.player.posY + CrystalBomber.mc.player.getEyeHeight(), CrystalBomber.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
        if (swing) {
            CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketAnimation(exactHand ? hand : EnumHand.MAIN_HAND));
        }
    }
    
    public static void attackEntity(final Entity entity, final boolean packet, final boolean swingArm) {
        if (packet) {
            CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        }
        else {
            CrystalBomber.mc.playerController.attackEntity((EntityPlayer)CrystalBomber.mc.player, entity);
        }
        if (swingArm) {
            CrystalBomber.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public boolean isValidForMining() {
        final int pickSlot = InventoryUtil.findHotbarItem(Items.DIAMOND_PICKAXE, new Item[0]);
        return pickSlot != -1 && CrystalBomber.SPEEDMINE.get().damages[pickSlot] >= CrystalBomber.SPEEDMINE.get().limit.getValue();
    }
    
    protected void placeBlock(final BlockPos on, final EnumFacing facing, final float[] rotations, final int slot) {
        if (rotations != null) {
            final RayTraceResult result = RayTraceUtil.getRayTraceResult(rotations[0], rotations[1]);
            final float[] f = RayTraceUtil.hitVecToPlaceVec(on, result.hitVec);
            final int lastSlot = CrystalBomber.mc.player.inventory.currentItem;
            final boolean sneaking = this.smartSneak.getValue() && !SpecialBlocks.shouldSneak(on, true);
            if (this.bypass.getValue()) {
                InventoryUtil.switchToBypass(slot);
            }
            else {
                InventoryUtil.switchTo(slot);
            }
            if (!sneaking) {
                CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CrystalBomber.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(on, facing, InventoryUtil.getHand(slot), f[0], f[1], f[2]));
            CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketAnimation(InventoryUtil.getHand(slot)));
            if (!sneaking) {
                CrystalBomber.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CrystalBomber.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (CrystalBomber.mc.player.inventory.getStackInSlot(InventoryUtil.hotbarToInventory(lastSlot)).getItem() != Items.DIAMOND_PICKAXE) {
                if (this.bypass.getValue()) {
                    InventoryUtil.switchToBypass(lastSlot);
                }
                else {
                    InventoryUtil.switchTo(lastSlot);
                }
            }
        }
    }
    
    static {
        SPEEDMINE = Caches.getModule(Speedmine.class);
    }
}
