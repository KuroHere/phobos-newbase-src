//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bedbomb;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.entity.player.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;
import net.minecraft.block.material.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.tileentity.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import java.util.stream.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.block.*;

public class BedBomb extends Module
{
    private final Setting<Boolean> place;
    private final Setting<Integer> placeDelay;
    private final Setting<Float> placeRange;
    private final Setting<Boolean> extraPacket;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> explode;
    private final Setting<BreakLogic> breakMode;
    private final Setting<Integer> breakDelay;
    private final Setting<Float> breakRange;
    private final Setting<Float> minDamage;
    private final Setting<Float> range;
    private final Setting<Boolean> suicide;
    private final Setting<Boolean> removeTiles;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> oneDot15;
    private final Setting<Logic> logic;
    private final Setting<Boolean> craft;
    private final Setting<Boolean> placeCraftingTable;
    private final Setting<Boolean> openCraftingTable;
    private final Setting<Boolean> craftTable;
    private final Setting<Float> tableRange;
    private final Setting<Integer> craftDelay;
    private final Setting<Integer> tableSlot;
    private final StopWatch breakTimer;
    private final StopWatch placeTimer;
    private final StopWatch craftTimer;
    private EntityPlayer target;
    private boolean sendRotationPacket;
    private final AtomicDouble yaw;
    private final AtomicDouble pitch;
    private final AtomicBoolean shouldRotate;
    private MotionUpdateEvent current;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private BlockPos maxPos;
    private boolean shouldCraft;
    private int craftStage;
    private int bedSlot;
    private BlockPos finalPos;
    private EnumFacing finalFacing;
    
    public BedBomb() {
        super("BedBomb", Category.Combat);
        this.place = this.register(new BooleanSetting("Place", false));
        this.placeDelay = this.register(new NumberSetting("Placedelay", 50, 0, 500));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0f, 1.0f, 10.0f));
        this.extraPacket = this.register(new BooleanSetting("InsanePacket", false));
        this.packet = this.register(new BooleanSetting("Packet", false));
        this.explode = this.register(new BooleanSetting("Break", true));
        this.breakMode = this.register(new EnumSetting("BreakMode", BreakLogic.ALL));
        this.breakDelay = this.register(new NumberSetting("Breakdelay", 50, 0, 500));
        this.breakRange = this.register(new NumberSetting("BreakRange", 6.0f, 1.0f, 10.0f));
        this.minDamage = this.register(new NumberSetting("MinDamage", 5.0f, 1.0f, 36.0f));
        this.range = this.register(new NumberSetting("Range", 10.0f, 1.0f, 12.0f));
        this.suicide = this.register(new BooleanSetting("Suicide", false));
        this.removeTiles = this.register(new BooleanSetting("RemoveTiles", false));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.oneDot15 = this.register(new BooleanSetting("1.15", false));
        this.logic = this.register(new EnumSetting("Logic", Logic.BREAKPLACE));
        this.craft = this.register(new BooleanSetting("Craft", false));
        this.placeCraftingTable = this.register(new BooleanSetting("PlaceTable", false));
        this.openCraftingTable = this.register(new BooleanSetting("OpenTable", false));
        this.craftTable = this.register(new BooleanSetting("CraftTable", false));
        this.tableRange = this.register(new NumberSetting("TableRange", 6.0f, 1.0f, 10.0f));
        this.craftDelay = this.register(new NumberSetting("CraftDelay", 4, 1, 10));
        this.tableSlot = this.register(new NumberSetting("TableSlot", 8, 0, 8));
        this.breakTimer = new StopWatch();
        this.placeTimer = new StopWatch();
        this.craftTimer = new StopWatch();
        this.target = null;
        this.sendRotationPacket = false;
        this.yaw = new AtomicDouble(-1.0);
        this.pitch = new AtomicDouble(-1.0);
        this.shouldRotate = new AtomicBoolean(false);
        this.maxPos = null;
        this.craftStage = 0;
        this.bedSlot = -1;
        this.setData(new SimpleData(this, "Quick and dirty Port of the awful old Phobos BedBomb."));
        this.listeners.add(new EventListener<MotionUpdateEvent>(MotionUpdateEvent.class) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
                BedBomb.this.onUpdateWalkingPlayer(event);
            }
        });
        this.listeners.addAll(new CPacketPlayerListener() {
            @Override
            protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
                BedBomb.this.onPacket(event.getPacket());
            }
            
            @Override
            protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
                BedBomb.this.onPacket(event.getPacket());
            }
            
            @Override
            protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
                BedBomb.this.onPacket(event.getPacket());
            }
            
            @Override
            protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
                BedBomb.this.onPacket(event.getPacket());
            }
        }.getListeners());
    }
    
    @Override
    protected void onEnable() {
        this.current = null;
        this.bedSlot = -1;
        this.sendRotationPacket = false;
        this.target = null;
        this.yaw.set(-1.0);
        this.pitch.set(-1.0);
        this.shouldRotate.set(false);
        this.shouldCraft = false;
    }
    
    public void onPacket(final CPacketPlayer packet) {
        if (this.shouldRotate.get()) {
            ((ICPacketPlayer)packet).setYaw((float)this.yaw.get());
            ((ICPacketPlayer)packet).setPitch((float)this.pitch.get());
            this.shouldRotate.set(false);
        }
    }
    
    public static int findInventoryWool() {
        return InventoryUtil.findInInventory(s -> {
            if (s.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)s.getItem()).getBlock();
                return block.getDefaultState().getMaterial() == Material.CLOTH;
            }
            else {
                return false;
            }
        }, true);
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.xCoord - pos.getX());
            final float f2 = (float)(vec.yCoord - pos.getY());
            final float f3 = (float)(vec.zCoord - pos.getZ());
            BedBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BedBomb.mc.playerController.processRightClickBlock(BedBomb.mc.player, BedBomb.mc.world, pos, direction, vec, hand);
        }
        BedBomb.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public void onUpdateWalkingPlayer(final MotionUpdateEvent event) {
        this.current = event;
        if (BedBomb.mc.player.dimension != -1 && BedBomb.mc.player.dimension != 1) {
            return;
        }
        if (event.getStage() == Stage.PRE) {
            this.doBedBomb();
            if (this.shouldCraft && BedBomb.mc.currentScreen instanceof GuiCrafting) {
                final int woolSlot = findInventoryWool();
                final int woodSlot = InventoryUtil.findInInventory(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks, true);
                if (woolSlot == -1 || woodSlot == -1 || woolSlot == -2 || woodSlot == -2) {
                    BedBomb.mc.displayGuiScreen((GuiScreen)null);
                    BedBomb.mc.currentScreen = null;
                    this.shouldCraft = false;
                    return;
                }
                if (this.craftStage > 1 && !this.one) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 1, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.one = true;
                }
                else if (this.craftStage > 1 + this.craftDelay.getValue() && !this.two) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 2, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.two = true;
                }
                else if (this.craftStage > 1 + this.craftDelay.getValue() * 2 && !this.three) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 3, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.three = true;
                }
                else if (this.craftStage > 1 + this.craftDelay.getValue() * 3 && !this.four) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 4, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.four = true;
                }
                else if (this.craftStage > 1 + this.craftDelay.getValue() * 4 && !this.five) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 5, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.five = true;
                }
                else if (this.craftStage > 1 + this.craftDelay.getValue() * 5 && !this.six) {
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 6, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                    this.recheckBedSlots(woolSlot, woodSlot);
                    BedBomb.mc.playerController.windowClick(((GuiContainer)BedBomb.mc.currentScreen).inventorySlots.windowId, 0, 0, ClickType.QUICK_MOVE, (EntityPlayer)BedBomb.mc.player);
                    this.six = true;
                    this.one = false;
                    this.two = false;
                    this.three = false;
                    this.four = false;
                    this.five = false;
                    this.six = false;
                    this.craftStage = -2;
                    this.shouldCraft = false;
                }
                ++this.craftStage;
            }
        }
        else if (event.getStage() == Stage.POST && this.finalPos != null) {
            final Vec3d hitVec = new Vec3d((Vec3i)this.finalPos.down()).addVector(0.5, 0.5, 0.5).add(new Vec3d(this.finalFacing.getOpposite().getDirectionVec()).scale(0.5));
            BedBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedBomb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            InventoryUtil.switchTo(this.bedSlot);
            rightClickBlock(this.finalPos.down(), hitVec, (this.bedSlot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, EnumFacing.UP, this.packet.getValue());
            BedBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedBomb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.placeTimer.reset();
            this.finalPos = null;
        }
    }
    
    public void recheckBedSlots(final int woolSlot, final int woodSlot) {
        for (int i = 1; i <= 3; ++i) {
            if (BedBomb.mc.player.openContainer.getInventory().get(i) == ItemStack.field_190927_a) {
                BedBomb.mc.playerController.windowClick(1, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(1, i, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(1, woolSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            }
        }
        for (int i = 4; i <= 6; ++i) {
            if (BedBomb.mc.player.openContainer.getInventory().get(i) == ItemStack.field_190927_a) {
                BedBomb.mc.playerController.windowClick(1, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(1, i, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(1, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            }
        }
    }
    
    public void incrementCraftStage() {
        if (this.craftTimer.passed(this.craftDelay.getValue())) {
            ++this.craftStage;
            if (this.craftStage > 9) {
                this.craftStage = 0;
            }
            this.craftTimer.reset();
        }
    }
    
    private void doBedBomb() {
        switch (this.logic.getValue()) {
            case BREAKPLACE: {
                this.mapBeds();
                this.breakBeds();
                this.placeBeds();
                break;
            }
            case PLACEBREAK: {
                this.mapBeds();
                this.placeBeds();
                this.breakBeds();
                break;
            }
        }
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = PositionUtil.getEyePos();
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BedBomb.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BedBomb.mc.player.rotationYaw), BedBomb.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BedBomb.mc.player.rotationPitch) };
    }
    
    private void breakBeds() {
        if (this.explode.getValue() && this.breakTimer.passed(this.breakDelay.getValue())) {
            if (this.breakMode.getValue() == BreakLogic.CALC) {
                if (this.maxPos != null) {
                    final Vec3d hitVec = new Vec3d((Vec3i)this.maxPos).addVector(0.5, 0.5, 0.5);
                    final float[] rotations = getLegitRotations(hitVec);
                    this.yaw.set((double)rotations[0]);
                    if (this.rotate.getValue()) {
                        this.shouldRotate.set(true);
                        this.pitch.set((double)rotations[1]);
                    }
                    final RayTraceResult result = BedBomb.mc.world.rayTraceBlocks(new Vec3d(BedBomb.mc.player.posX, BedBomb.mc.player.posY + BedBomb.mc.player.getEyeHeight(), BedBomb.mc.player.posZ), new Vec3d(this.maxPos.getX() + 0.5, this.maxPos.getY() - 0.5, this.maxPos.getZ() + 0.5));
                    final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
                    rightClickBlock(this.maxPos, hitVec, EnumHand.MAIN_HAND, facing, true);
                    this.breakTimer.reset();
                }
            }
            else {
                for (final TileEntity entityBed : BedBomb.mc.world.loadedTileEntityList) {
                    if (!(entityBed instanceof TileEntityBed)) {
                        continue;
                    }
                    if (BedBomb.mc.player.getDistanceSq(entityBed.getPos()) > MathUtil.square(this.breakRange.getValue())) {
                        continue;
                    }
                    final Vec3d hitVec2 = new Vec3d((Vec3i)entityBed.getPos()).addVector(0.5, 0.5, 0.5);
                    final float[] rotations2 = getLegitRotations(hitVec2);
                    this.yaw.set((double)rotations2[0]);
                    if (this.rotate.getValue()) {
                        this.shouldRotate.set(true);
                        this.pitch.set((double)rotations2[1]);
                    }
                    final RayTraceResult result2 = BedBomb.mc.world.rayTraceBlocks(new Vec3d(BedBomb.mc.player.posX, BedBomb.mc.player.posY + BedBomb.mc.player.getEyeHeight(), BedBomb.mc.player.posZ), new Vec3d(entityBed.getPos().getX() + 0.5, entityBed.getPos().getY() - 0.5, entityBed.getPos().getZ() + 0.5));
                    final EnumFacing facing2 = (result2 == null || result2.sideHit == null) ? EnumFacing.UP : result2.sideHit;
                    rightClickBlock(entityBed.getPos(), hitVec2, EnumHand.MAIN_HAND, facing2, true);
                    this.breakTimer.reset();
                }
            }
        }
    }
    
    public static boolean canTakeDamage(final boolean suicide) {
        return !BedBomb.mc.player.capabilities.isCreativeMode && !suicide;
    }
    
    private void mapBeds() {
        this.maxPos = null;
        float maxDamage = 0.5f;
        if (this.removeTiles.getValue()) {
            final List<BedData> removedBlocks = new ArrayList<BedData>();
            for (final TileEntity tile : BedBomb.mc.world.loadedTileEntityList) {
                if (tile instanceof TileEntityBed) {
                    final TileEntityBed bed = (TileEntityBed)tile;
                    final BedData data = new BedData(tile.getPos(), BedBomb.mc.world.getBlockState(tile.getPos()), bed, bed.func_193050_e());
                    removedBlocks.add(data);
                }
            }
            for (final BedData data2 : removedBlocks) {
                BedBomb.mc.world.setBlockToAir(data2.getPos());
            }
            for (final BedData data2 : removedBlocks) {
                if (data2.isHeadPiece()) {
                    final BlockPos pos = data2.getPos();
                    if (BedBomb.mc.player.getDistanceSq(pos) > MathUtil.square(this.breakRange.getValue())) {
                        continue;
                    }
                    final float selfDamage = DamageUtil.calculate(pos, (EntityLivingBase)BedBomb.mc.player);
                    if (selfDamage + 1.0 >= EntityUtil.getHealth((EntityLivingBase)BedBomb.mc.player) && canTakeDamage(this.suicide.getValue())) {
                        continue;
                    }
                    for (final EntityPlayer player : BedBomb.mc.world.playerEntities) {
                        if (player.getDistanceSq(pos) < MathUtil.square(this.range.getValue()) && EntityUtil.isValid((Entity)player, this.range.getValue() + this.breakRange.getValue())) {
                            final float damage = DamageUtil.calculate(pos, (EntityLivingBase)player);
                            if ((damage <= selfDamage && (damage <= this.minDamage.getValue() || canTakeDamage(this.suicide.getValue())) && damage <= EntityUtil.getHealth((EntityLivingBase)player)) || damage <= maxDamage) {
                                continue;
                            }
                            maxDamage = damage;
                            this.maxPos = pos;
                        }
                    }
                }
            }
            for (final BedData data2 : removedBlocks) {
                BedBomb.mc.world.setBlockState(data2.getPos(), data2.getState());
            }
        }
        else {
            for (final TileEntity tile2 : BedBomb.mc.world.loadedTileEntityList) {
                if (tile2 instanceof TileEntityBed) {
                    final TileEntityBed bed2 = (TileEntityBed)tile2;
                    if (!bed2.func_193050_e()) {
                        continue;
                    }
                    final BlockPos pos = bed2.getPos();
                    if (BedBomb.mc.player.getDistanceSq(pos) > MathUtil.square(this.breakRange.getValue())) {
                        continue;
                    }
                    final float selfDamage = DamageUtil.calculate(pos, (EntityLivingBase)BedBomb.mc.player);
                    if (selfDamage + 1.0 >= EntityUtil.getHealth((EntityLivingBase)BedBomb.mc.player) && canTakeDamage(this.suicide.getValue())) {
                        continue;
                    }
                    for (final EntityPlayer player : BedBomb.mc.world.playerEntities) {
                        if (player.getDistanceSq(pos) < MathUtil.square(this.range.getValue()) && EntityUtil.isValid((Entity)player, this.range.getValue() + this.breakRange.getValue())) {
                            final float damage = DamageUtil.calculate(pos, (EntityLivingBase)player);
                            if ((damage <= selfDamage && (damage <= this.minDamage.getValue() || canTakeDamage(this.suicide.getValue())) && damage <= EntityUtil.getHealth((EntityLivingBase)player)) || damage <= maxDamage) {
                                continue;
                            }
                            maxDamage = damage;
                            this.maxPos = pos;
                        }
                    }
                }
            }
        }
    }
    
    private void placeBeds() {
        if (this.place.getValue() && this.placeTimer.passed(this.placeDelay.getValue()) && this.maxPos == null) {
            this.bedSlot = this.findBedSlot();
            if (this.bedSlot == -1) {
                if (BedBomb.mc.player.getHeldItemOffhand().getItem() != Items.BED) {
                    if (this.craft.getValue() && !this.shouldCraft && EntityUtil.getClosestEnemy(BedBomb.mc.world.playerEntities) != null) {
                        this.doBedCraft();
                    }
                    return;
                }
                this.bedSlot = -2;
            }
            this.target = EntityUtil.getClosestEnemy(BedBomb.mc.world.playerEntities);
            if (this.target != null && this.target.getDistanceSqToEntity((Entity)BedBomb.mc.player) < 49.0) {
                final BlockPos targetPos = new BlockPos(this.target.getPositionVector());
                this.placeBed(targetPos, true);
                if (this.craft.getValue()) {
                    this.doBedCraft();
                }
            }
        }
    }
    
    private void placeBed(final BlockPos pos, final boolean firstCheck) {
        if (BedBomb.mc.world.getBlockState(pos).getBlock() == Blocks.BED) {
            return;
        }
        final float damage = DamageUtil.calculate(pos, (EntityLivingBase)BedBomb.mc.player);
        if (damage > EntityUtil.getHealth((EntityLivingBase)BedBomb.mc.player) + 0.5) {
            if (firstCheck && this.oneDot15.getValue()) {
                this.placeBed(pos.up(), false);
            }
            return;
        }
        if (!BedBomb.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            if (firstCheck && this.oneDot15.getValue()) {
                this.placeBed(pos.up(), false);
            }
            return;
        }
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        final Map<BlockPos, EnumFacing> facings = new HashMap<BlockPos, EnumFacing>();
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.DOWN) {
                if (facing != EnumFacing.UP) {
                    final BlockPos position = pos.offset(facing);
                    if (BedBomb.mc.player.getDistanceSq(position) <= MathUtil.square(this.placeRange.getValue()) && BedBomb.mc.world.getBlockState(position).getMaterial().isReplaceable() && !BedBomb.mc.world.getBlockState(position.down()).getMaterial().isReplaceable()) {
                        positions.add(position);
                        facings.put(position, facing.getOpposite());
                    }
                }
            }
        }
        if (positions.isEmpty()) {
            if (firstCheck && this.oneDot15.getValue()) {
                this.placeBed(pos.up(), false);
            }
            return;
        }
        positions.sort(Comparator.comparingDouble(pos2 -> BedBomb.mc.player.getDistanceSq(pos2)));
        this.finalPos = positions.get(0);
        this.finalFacing = facings.get(this.finalPos);
        final float[] rotation = simpleFacing(this.finalFacing);
        if (!this.sendRotationPacket && this.extraPacket.getValue()) {
            faceYawAndPitch(rotation[0], rotation[1]);
            this.sendRotationPacket = true;
        }
        this.yaw.set((double)rotation[0]);
        this.pitch.set((double)rotation[1]);
        this.shouldRotate.set(true);
        if (this.current != null) {
            this.current.setYaw(rotation[0]);
            this.current.setPitch(rotation[1]);
        }
    }
    
    public static void faceYawAndPitch(final float yaw, final float pitch) {
        BedBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, BedBomb.mc.player.onGround));
    }
    
    public static float[] simpleFacing(final EnumFacing facing) {
        switch (facing) {
            case DOWN: {
                return new float[] { BedBomb.mc.player.rotationYaw, 90.0f };
            }
            case UP: {
                return new float[] { BedBomb.mc.player.rotationYaw, -90.0f };
            }
            case NORTH: {
                return new float[] { 180.0f, 0.0f };
            }
            case SOUTH: {
                return new float[] { 0.0f, 0.0f };
            }
            case WEST: {
                return new float[] { 90.0f, 0.0f };
            }
            default: {
                return new float[] { 270.0f, 0.0f };
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target != null) {
            return this.target.getName();
        }
        return null;
    }
    
    public static List<BlockPos> getSphere(final BlockPos pos, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = pos.getX();
        final int cy = pos.getY();
        final int cz = pos.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static List<BlockPos> getBlockSphere(final float breakRange, final Class clazz) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)getSphere(BedBomb.mc.player.getPosition(), breakRange, (int)breakRange, false, true, 0).stream().filter(pos -> clazz.isInstance(BedBomb.mc.world.getBlockState(pos).getBlock())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean rayTrace) {
        return isPositionPlaceable(pos, rayTrace, true);
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || BedBomb.mc.world.rayTraceBlocks(new Vec3d(BedBomb.mc.player.posX, BedBomb.mc.player.posY + BedBomb.mc.player.getEyeHeight(), BedBomb.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        if (BedBomb.mc.world == null || pos == null) {
            return facings;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            final IBlockState blockState = BedBomb.mc.world.getBlockState(neighbour);
            if (blockState != null && blockState.getBlock().canCollideCheck(blockState, false) && !blockState.getMaterial().isReplaceable()) {
                facings.add(side);
            }
        }
        return facings;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return BedBomb.mc.world.getBlockState(pos);
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean rayTrace, final boolean entityCheck) {
        final Block block = BedBomb.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return 0;
        }
        if (!rayTracePlaceCheck(pos, rayTrace, 0.0f)) {
            return -1;
        }
        if (entityCheck) {
            for (final Entity entity : BedBomb.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    return 1;
                }
            }
        }
        for (final EnumFacing side : getPossibleSides(pos)) {
            if (canBeClicked(pos.offset(side))) {
                return 3;
            }
        }
        return 2;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static boolean placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BedBomb.mc.world.getBlockState(neighbour).getBlock();
        if (!BedBomb.mc.player.isSneaking() && (SpecialBlocks.BAD_BLOCKS.contains(neighbourBlock) || SpecialBlocks.SHULKERS.contains(neighbourBlock))) {
            BedBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedBomb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BedBomb.mc.player.setSneaking(true);
            sneaking = true;
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BedBomb.mc.player.swingArm(EnumHand.MAIN_HAND);
        return sneaking || isSneaking;
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        BedBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], BedBomb.mc.player.onGround));
    }
    
    public void doBedCraft() {
        final int woolSlot = findInventoryWool();
        final int woodSlot = InventoryUtil.findInInventory(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks, true);
        if (woolSlot == -1 || woodSlot == -1) {
            if (BedBomb.mc.currentScreen instanceof GuiCrafting) {
                BedBomb.mc.displayGuiScreen((GuiScreen)null);
                BedBomb.mc.currentScreen = null;
            }
            return;
        }
        if (this.placeCraftingTable.getValue() && getBlockSphere(this.tableRange.getValue() - 1.0f, BlockWorkbench.class).size() == 0) {
            final List<BlockPos> targets = getSphere(BedBomb.mc.player.getPosition(), this.tableRange.getValue(), this.tableRange.getValue().intValue(), false, true, 0).stream().filter(pos -> isPositionPlaceable(pos, false) == 3).sorted(Comparator.comparingInt(pos -> -this.safety(pos))).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
            if (!targets.isEmpty()) {
                final BlockPos target = targets.get(0);
                int tableSlot = InventoryUtil.findInInventory(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks, true);
                if (tableSlot != -1) {
                    BedBomb.mc.player.inventory.currentItem = tableSlot;
                    placeBlock(target, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
                }
                else {
                    if (this.craftTable.getValue()) {
                        this.craftTable();
                    }
                    tableSlot = InventoryUtil.findInHotbar(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks);
                    if (tableSlot != -1 && tableSlot != -2) {
                        BedBomb.mc.player.inventory.currentItem = tableSlot;
                        placeBlock(target, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
                    }
                }
            }
        }
        if (this.openCraftingTable.getValue()) {
            final List<BlockPos> tables = getBlockSphere(this.tableRange.getValue(), BlockWorkbench.class);
            tables.sort(Comparator.comparingDouble(pos -> BedBomb.mc.player.getDistanceSq(pos)));
            if (!tables.isEmpty() && !(BedBomb.mc.currentScreen instanceof GuiCrafting)) {
                final BlockPos target = tables.get(0);
                BedBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedBomb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                if (BedBomb.mc.player.getDistanceSq(target) > MathUtil.square(this.breakRange.getValue())) {
                    return;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)target);
                final float[] rotations = getLegitRotations(hitVec);
                this.yaw.set((double)rotations[0]);
                if (this.rotate.getValue()) {
                    this.shouldRotate.set(true);
                    this.pitch.set((double)rotations[1]);
                }
                final RayTraceResult result = BedBomb.mc.world.rayTraceBlocks(new Vec3d(BedBomb.mc.player.posX, BedBomb.mc.player.posY + BedBomb.mc.player.getEyeHeight(), BedBomb.mc.player.posZ), new Vec3d(target.getX() + 0.5, target.getY() - 0.5, target.getZ() + 0.5));
                final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
                rightClickBlock(target, hitVec, EnumHand.MAIN_HAND, facing, true);
                this.breakTimer.reset();
                if (BedBomb.mc.player.isSneaking()) {
                    BedBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedBomb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
            }
            if (BedBomb.mc.currentScreen instanceof GuiCrafting) {
                this.shouldCraft = true;
            }
            else {
                this.shouldCraft = false;
            }
            this.craftStage = 0;
            this.craftTimer.reset();
        }
    }
    
    public void craftTable() {
        final int woodSlot = InventoryUtil.findInInventory(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks, true);
        if (woodSlot != -1) {
            BedBomb.mc.playerController.windowClick(0, woodSlot, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            BedBomb.mc.playerController.windowClick(0, 1, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            BedBomb.mc.playerController.windowClick(0, 2, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            BedBomb.mc.playerController.windowClick(0, 3, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            BedBomb.mc.playerController.windowClick(0, 4, 1, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            BedBomb.mc.playerController.windowClick(0, 0, 0, ClickType.QUICK_MOVE, (EntityPlayer)BedBomb.mc.player);
            final int table = InventoryUtil.findInInventory(s -> s.getItem() instanceof ItemBlock && ((ItemBlock)s.getItem()).getBlock() instanceof BlockPlanks, true);
            if (table != -1) {
                BedBomb.mc.playerController.windowClick(0, table, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(0, (int)this.tableSlot.getValue(), 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
                BedBomb.mc.playerController.windowClick(0, table, 0, ClickType.PICKUP, (EntityPlayer)BedBomb.mc.player);
            }
        }
    }
    
    private int findBedSlot() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = BedBomb.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.field_190927_a) {
                if (stack.getItem() == Items.BED) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private int safety(final BlockPos pos) {
        int safety = 0;
        for (final EnumFacing facing : EnumFacing.values()) {
            if (!BedBomb.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                ++safety;
            }
        }
        return safety;
    }
    
    public static class BedData
    {
        private final BlockPos pos;
        private final IBlockState state;
        private final boolean isHeadPiece;
        private final TileEntityBed entity;
        
        public BedData(final BlockPos pos, final IBlockState state, final TileEntityBed bed, final boolean isHeadPiece) {
            this.pos = pos;
            this.state = state;
            this.entity = bed;
            this.isHeadPiece = isHeadPiece;
        }
        
        public BlockPos getPos() {
            return this.pos;
        }
        
        public IBlockState getState() {
            return this.state;
        }
        
        public boolean isHeadPiece() {
            return this.isHeadPiece;
        }
        
        public TileEntityBed getEntity() {
            return this.entity;
        }
    }
    
    public enum Logic
    {
        BREAKPLACE, 
        PLACEBREAK;
    }
    
    public enum BreakLogic
    {
        ALL, 
        CALC;
    }
}
