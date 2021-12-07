//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autothirtytwok;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import org.lwjgl.input.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.inventory.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import me.earth.earthhack.impl.modules.*;

public class Auto32k extends Module
{
    private static final ModuleCache<Freecam> FREECAM;
    protected Setting<Mode> mode;
    protected final Setting<Boolean> swing;
    protected final Setting<Integer> delay;
    protected final Setting<Integer> delayDispenser;
    protected final Setting<Integer> blocksPerPlace;
    protected final Setting<Float> range;
    protected final Setting<Boolean> raytrace;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> autoSwitch;
    protected final Setting<Boolean> withBind;
    protected final Setting<Bind> switchBind;
    protected final Setting<Double> targetRange;
    protected final Setting<Boolean> extra;
    protected final Setting<PlaceType> placeType;
    protected final Setting<Boolean> freecam;
    protected final Setting<Boolean> onOtherHoppers;
    protected final Setting<Boolean> preferObby;
    protected final Setting<Boolean> checkForShulker;
    protected final Setting<Integer> checkDelay;
    protected final Setting<Boolean> drop;
    protected final Setting<Boolean> mine;
    protected final Setting<Boolean> checkStatus;
    protected final Setting<Boolean> packet;
    protected final Setting<Boolean> superPacket;
    protected final Setting<Boolean> secretClose;
    protected final Setting<Boolean> closeGui;
    protected final Setting<Boolean> repeatSwitch;
    protected final Setting<Boolean> simulate;
    protected final Setting<Float> hopperDistance;
    protected final Setting<Integer> trashSlot;
    protected final Setting<Boolean> messages;
    protected final Setting<Boolean> antiHopper;
    private float yaw;
    private float pitch;
    private boolean spoof;
    public boolean switching;
    private int lastHotbarSlot;
    private int shulkerSlot;
    private int hopperSlot;
    private BlockPos hopperPos;
    private EntityPlayer target;
    public Step currentStep;
    private final StopWatch placeTimer;
    private static Auto32k instance;
    private int obbySlot;
    private int dispenserSlot;
    private int redstoneSlot;
    private DispenserData finalDispenserData;
    private int actionsThisTick;
    private boolean checkedThisTick;
    private boolean authSneakPacket;
    private StopWatch disableTimer;
    private boolean shouldDisable;
    private boolean rotationprepared;
    
    public Auto32k() {
        super("Auto32k", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", Mode.NORMAL));
        this.swing = this.register(new BooleanSetting("Swing", false));
        this.delay = this.register(new NumberSetting("Delay/Place", 25, 0, 250));
        this.delayDispenser = this.register(new NumberSetting("Blocks/Place", 1, 1, 8));
        this.blocksPerPlace = this.register(new NumberSetting("Actions/Place", 1, 1, 3));
        this.range = this.register(new NumberSetting("PlaceRange", 4.5f, 0.0f, 6.0f));
        this.raytrace = this.register(new BooleanSetting("Raytrace", false));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.autoSwitch = this.register(new BooleanSetting("AutoSwitch", false));
        this.withBind = this.register(new BooleanSetting("WithBind", false));
        this.switchBind = this.register(new BindSetting("SwitchBind", Bind.none()));
        this.targetRange = this.register(new NumberSetting("TargetRange", 6.0, 0.0, 20.0));
        this.extra = this.register(new BooleanSetting("ExtraRotation", false));
        this.placeType = this.register(new EnumSetting("Place", PlaceType.CLOSE));
        this.freecam = this.register(new BooleanSetting("Freecam", false));
        this.onOtherHoppers = this.register(new BooleanSetting("UseHoppers", false));
        this.preferObby = this.register(new BooleanSetting("UseObby", false));
        this.checkForShulker = this.register(new BooleanSetting("CheckShulker", true));
        this.checkDelay = this.register(new NumberSetting("CheckDelay", 500, 0, 500));
        this.drop = this.register(new BooleanSetting("Drop", false));
        this.mine = this.register(new BooleanSetting("Mine", false));
        this.checkStatus = this.register(new BooleanSetting("CheckState", true));
        this.packet = this.register(new BooleanSetting("Packet", false));
        this.superPacket = this.register(new BooleanSetting("DispExtra", false));
        this.secretClose = this.register(new BooleanSetting("SecretClose", false));
        this.closeGui = this.register(new BooleanSetting("CloseGui", false));
        this.repeatSwitch = this.register(new BooleanSetting("SwitchOnFail", true));
        this.simulate = this.register(new BooleanSetting("Simulate", true));
        this.hopperDistance = this.register(new NumberSetting("HopperRange", 8.0f, 0.0f, 20.0f));
        this.trashSlot = this.register(new NumberSetting("32kSlot", 0, 0, 9));
        this.messages = this.register(new BooleanSetting("Messages", false));
        this.antiHopper = this.register(new BooleanSetting("AntiHopper", false));
        this.lastHotbarSlot = -1;
        this.shulkerSlot = -1;
        this.hopperSlot = -1;
        this.currentStep = Step.PRE;
        this.placeTimer = new StopWatch();
        this.obbySlot = -1;
        this.dispenserSlot = -1;
        this.redstoneSlot = -1;
        this.actionsThisTick = 0;
        this.checkedThisTick = false;
        this.authSneakPacket = false;
        this.disableTimer = new StopWatch();
        this.rotationprepared = false;
        Auto32k.instance = this;
        this.listeners.add(new ListenerCPacketCloseWindow(this));
        this.listeners.addAll(new ListenerCPacketPlayer(this).getListeners());
        this.listeners.add(new ListenerGuiOpen(this));
        this.listeners.add(new ListenerKeyPress(this));
        this.listeners.add(new ListenerMotion(this));
        this.setData(new SimpleData(this, "Port of the awful old Phobos Auto32k."));
    }
    
    public static Auto32k getInstance() {
        if (Auto32k.instance == null) {
            Auto32k.instance = new Auto32k();
        }
        return Auto32k.instance;
    }
    
    public void onEnable() {
        this.checkedThisTick = false;
        this.resetFields();
        if (Auto32k.mc.currentScreen instanceof GuiHopper) {
            this.currentStep = Step.HOPPERGUI;
        }
        if (this.mode.getValue() == Mode.NORMAL && this.autoSwitch.getValue() && !this.withBind.getValue()) {
            this.switching = true;
        }
    }
    
    public void onUpdateWalkingPlayer(final MotionUpdateEvent event) {
        if (event.getStage() != Stage.PRE) {
            return;
        }
        if (this.shouldDisable && this.disableTimer.passed(1000L)) {
            this.shouldDisable = false;
            this.disable();
            return;
        }
        this.checkedThisTick = false;
        this.actionsThisTick = 0;
        if (!this.isEnabled() || (this.mode.getValue() == Mode.NORMAL && this.autoSwitch.getValue() && !this.switching)) {
            return;
        }
        if (this.mode.getValue() == Mode.NORMAL) {
            this.normal32k();
        }
        else {
            this.processDispenser32k();
        }
    }
    
    protected void onGui(final GuiScreenEvent<?> event) {
        if (!this.isEnabled()) {
            return;
        }
        if (!this.secretClose.getValue() && Auto32k.mc.currentScreen instanceof GuiHopper) {
            if (this.drop.getValue() && Auto32k.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD && this.hopperPos != null) {
                Auto32k.mc.player.dropItem(true);
                if (this.mine.getValue() && this.hopperPos != null) {
                    final int pickaxeSlot = InventoryUtil.findHotbarItem(Items.DIAMOND_PICKAXE, new Item[0]);
                    if (pickaxeSlot != -1) {
                        InventoryUtil.switchTo(pickaxeSlot);
                        if (this.rotate.getValue()) {
                            this.rotateToPos(this.hopperPos.up(), null);
                        }
                        Auto32k.mc.playerController.onPlayerDamageBlock(this.hopperPos.up(), Auto32k.mc.player.getHorizontalFacing());
                        Auto32k.mc.playerController.onPlayerDamageBlock(this.hopperPos.up(), Auto32k.mc.player.getHorizontalFacing());
                        Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
            this.resetFields();
            if (this.mode.getValue() != Mode.NORMAL) {
                this.disable();
                return;
            }
            if (!this.autoSwitch.getValue() || this.mode.getValue() == Mode.DISPENSER) {
                this.disable();
            }
            else if (!this.withBind.getValue()) {
                this.disable();
            }
        }
        else if (event.getScreen() instanceof GuiHopper) {
            this.currentStep = Step.HOPPERGUI;
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.switching) {
            return "§aSwitch";
        }
        return null;
    }
    
    protected void onKeyInput(final KeyboardEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (Keyboard.getEventKeyState() && this.switchBind.getValue().getKey() == Keyboard.getEventKey() && this.withBind.getValue()) {
            if (this.switching) {
                this.resetFields();
                this.switching = true;
            }
            this.switching = !this.switching;
        }
    }
    
    protected void onSettingChange(final SettingEvent event) {
        if (event.getSetting().getContainer() == this) {
            this.resetFields();
        }
    }
    
    protected void onCPacketPlayer(final CPacketPlayer packet) {
        if ((packet instanceof CPacketPlayer.PositionRotation || packet instanceof CPacketPlayer.Rotation) && this.spoof) {
            ((ICPacketPlayer)packet).setYaw(this.yaw);
            ((ICPacketPlayer)packet).setPitch(this.pitch);
            this.spoof = false;
        }
    }
    
    protected void onCPacketCloseWindow(final PacketEvent.Send<CPacketCloseWindow> event) {
        if (!this.secretClose.getValue() && Auto32k.mc.currentScreen instanceof GuiHopper && this.hopperPos != null) {
            if (this.drop.getValue() && Auto32k.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                Auto32k.mc.player.dropItem(true);
                if (this.mine.getValue()) {
                    final int pickaxeSlot = InventoryUtil.findHotbarItem(Items.DIAMOND_PICKAXE, new Item[0]);
                    if (pickaxeSlot != -1) {
                        InventoryUtil.switchTo(pickaxeSlot);
                        if (this.rotate.getValue()) {
                            this.rotateToPos(this.hopperPos.up(), null);
                        }
                        Auto32k.mc.playerController.onPlayerDamageBlock(this.hopperPos.up(), Auto32k.mc.player.getHorizontalFacing());
                        Auto32k.mc.playerController.onPlayerDamageBlock(this.hopperPos.up(), Auto32k.mc.player.getHorizontalFacing());
                        Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
            this.resetFields();
            if (!this.autoSwitch.getValue() || this.mode.getValue() == Mode.DISPENSER) {
                this.disable();
            }
            else if (!this.withBind.getValue()) {
                this.disable();
            }
        }
        else if (this.secretClose.getValue() && (!this.autoSwitch.getValue() || this.switching || this.mode.getValue() == Mode.DISPENSER) && this.currentStep == Step.HOPPERGUI) {
            event.setCancelled(true);
        }
    }
    
    private void normal32k() {
        if (this.autoSwitch.getValue()) {
            if (this.switching) {
                this.processNormal32k();
            }
            else {
                this.resetFields();
            }
        }
        else {
            this.processNormal32k();
        }
    }
    
    private void processNormal32k() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.placeTimer.passed(this.delay.getValue())) {
            this.check();
            Label_0184: {
                switch (this.currentStep) {
                    case PRE: {
                        this.runPreStep();
                        if (this.currentStep == Step.PRE) {
                            break;
                        }
                    }
                    case HOPPER: {
                        if (this.currentStep != Step.HOPPER) {
                            break Label_0184;
                        }
                        this.checkState();
                        if (this.currentStep == Step.PRE) {
                            if (this.checkedThisTick) {
                                this.processNormal32k();
                            }
                            return;
                        }
                        this.runHopperStep();
                        if (this.actionsThisTick >= this.blocksPerPlace.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                            break;
                        }
                        break Label_0184;
                    }
                    case SHULKER: {
                        this.checkState();
                        if (this.currentStep == Step.PRE) {
                            if (this.checkedThisTick) {
                                this.processNormal32k();
                            }
                            return;
                        }
                        this.runShulkerStep();
                        if (this.actionsThisTick >= this.blocksPerPlace.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                            break;
                        }
                    }
                    case CLICKHOPPER: {
                        this.checkState();
                        if (this.currentStep == Step.PRE) {
                            if (this.checkedThisTick) {
                                this.processNormal32k();
                            }
                            return;
                        }
                        this.runClickHopper();
                    }
                    case HOPPERGUI: {
                        this.runHopperGuiStep();
                        break;
                    }
                    default: {
                        this.currentStep = Step.PRE;
                        break;
                    }
                }
            }
        }
    }
    
    private void runPreStep() {
        if (!this.isEnabled()) {
            return;
        }
        PlaceType type = this.placeType.getValue();
        if (Auto32k.FREECAM.isEnabled() && !this.freecam.getValue()) {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Disable freecam.", this.getDisplayName(), 2000);
            }
            if (this.autoSwitch.getValue()) {
                this.resetFields();
                if (!this.withBind.getValue()) {
                    this.disable();
                }
            }
            else {
                this.disable();
            }
            return;
        }
        this.lastHotbarSlot = Auto32k.mc.player.inventory.currentItem;
        this.hopperSlot = InventoryUtil.findHotbarBlock((Block)Blocks.HOPPER, new Block[0]);
        this.shulkerSlot = InventoryUtil.findInHotbar(item -> item.getItem() instanceof ItemShulkerBox);
        if (Auto32k.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            final Block block = ((ItemBlock)Auto32k.mc.player.getHeldItemOffhand().getItem()).getBlock();
            if (block instanceof BlockShulkerBox) {
                this.shulkerSlot = -2;
            }
            else if (block instanceof BlockHopper) {
                this.hopperSlot = -2;
            }
        }
        if (this.shulkerSlot == -1 || this.hopperSlot == -1) {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Materials not found.", this.getDisplayName(), 2000);
            }
            if (this.autoSwitch.getValue()) {
                this.resetFields();
                if (!this.withBind.getValue()) {
                    this.disable();
                }
            }
            else {
                this.disable();
            }
            return;
        }
        this.target = EntityUtil.getClosestEnemy();
        if (this.target == null || Auto32k.mc.player.getDistanceSqToEntity((Entity)this.target) > MathUtil.square(this.targetRange.getValue())) {
            if (this.autoSwitch.getValue()) {
                if (this.switching) {
                    this.resetFields();
                    this.switching = true;
                }
                else {
                    this.resetFields();
                }
                return;
            }
            type = ((this.placeType.getValue() == PlaceType.MOUSE) ? PlaceType.MOUSE : PlaceType.CLOSE);
        }
        this.hopperPos = this.findBestPos(type, this.target);
        if (this.hopperPos != null) {
            if (Auto32k.mc.world.getBlockState(this.hopperPos).getBlock() instanceof BlockHopper) {
                this.currentStep = Step.SHULKER;
            }
            else {
                this.currentStep = Step.HOPPER;
            }
        }
        else {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Block not found.", this.getDisplayName(), 2000);
            }
            if (this.autoSwitch.getValue()) {
                this.resetFields();
                if (!this.withBind.getValue()) {
                    this.disable();
                }
            }
            else {
                this.disable();
            }
        }
    }
    
    private void runHopperStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.currentStep == Step.HOPPER) {
            this.runPlaceStep(this.hopperPos, this.hopperSlot);
            this.currentStep = Step.SHULKER;
        }
    }
    
    private void runShulkerStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.currentStep == Step.SHULKER) {
            this.runPlaceStep(this.hopperPos.up(), this.shulkerSlot);
            this.currentStep = Step.CLICKHOPPER;
        }
    }
    
    private void runClickHopper() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.currentStep != Step.CLICKHOPPER) {
            return;
        }
        if (this.mode.getValue() == Mode.NORMAL && !(Auto32k.mc.world.getBlockState(this.hopperPos.up()).getBlock() instanceof BlockShulkerBox) && this.checkForShulker.getValue()) {
            if (this.placeTimer.passed(this.checkDelay.getValue())) {
                this.currentStep = Step.SHULKER;
            }
            return;
        }
        this.clickBlock(this.hopperPos);
        this.currentStep = Step.HOPPERGUI;
    }
    
    private void runHopperGuiStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.currentStep != Step.HOPPERGUI) {
            return;
        }
        if (Auto32k.mc.player.openContainer instanceof ContainerHopper) {
            if (!holding32k((EntityPlayer)Auto32k.mc.player)) {
                int swordIndex = -1;
                for (int i = 0; i < 5; ++i) {
                    if (is32k(Auto32k.mc.player.openContainer.inventorySlots.get(0).inventory.getStackInSlot(i))) {
                        swordIndex = i;
                        break;
                    }
                }
                if (swordIndex == -1) {
                    return;
                }
                if (this.trashSlot.getValue() != 0) {
                    InventoryUtil.switchTo(this.trashSlot.getValue() - 1);
                }
                else if (this.mode.getValue() != Mode.NORMAL && this.shulkerSlot > 35 && this.shulkerSlot != 45) {
                    InventoryUtil.switchTo(44 - this.shulkerSlot);
                }
                Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, swordIndex, (this.trashSlot.getValue() == 0) ? Auto32k.mc.player.inventory.currentItem : (this.trashSlot.getValue() - 1), ClickType.SWAP, (EntityPlayer)Auto32k.mc.player);
            }
            else if (this.closeGui.getValue() && this.secretClose.getValue()) {
                Auto32k.mc.player.closeScreen();
            }
        }
        else if (holding32k((EntityPlayer)Auto32k.mc.player)) {
            if (this.autoSwitch.getValue() && this.mode.getValue() == Mode.NORMAL) {
                this.switching = false;
            }
            else if (!this.autoSwitch.getValue() || this.mode.getValue() == Mode.DISPENSER) {
                this.shouldDisable = true;
                this.disableTimer.reset();
            }
        }
    }
    
    private void runPlaceStep(final BlockPos pos, final int slot) {
        if (!this.isEnabled()) {
            return;
        }
        EnumFacing side = EnumFacing.UP;
        if (this.antiHopper.getValue() && this.currentStep == Step.HOPPER) {
            boolean foundfacing = false;
            for (final EnumFacing facing : EnumFacing.values()) {
                if (Auto32k.mc.world.getBlockState(pos.offset(facing)).getBlock() != Blocks.HOPPER) {
                    if (!Auto32k.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                        foundfacing = true;
                        side = facing;
                        break;
                    }
                }
            }
            if (!foundfacing) {
                this.resetFields();
                return;
            }
        }
        else {
            side = BlockUtil.getFacing(pos);
            if (side == null) {
                this.resetFields();
                return;
            }
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = Auto32k.mc.world.getBlockState(neighbour).getBlock();
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        this.authSneakPacket = false;
        if (this.rotate.getValue()) {
            if (this.blocksPerPlace.getValue() > 1) {
                final float[] angle = RotationUtil.getRotations(hitVec);
                if (this.extra.getValue()) {
                    faceYawAndPitch(angle[0], angle[1]);
                }
            }
            else {
                this.rotateToPos(null, hitVec);
            }
        }
        InventoryUtil.switchTo(slot);
        rightClickBlock(neighbour, hitVec, (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, opposite, this.packet.getValue(), this.swing.getValue());
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.authSneakPacket = false;
        this.placeTimer.reset();
        ++this.actionsThisTick;
    }
    
    private BlockPos findBestPos(final PlaceType type, final EntityPlayer target) {
        BlockPos pos = null;
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        final BlockPos middle = PositionUtil.getPosition();
        BlockPos pos2 = null;
        for (int maxRadius = Sphere.getRadius(this.range.getValue()), i = 1; i < maxRadius; ++i) {
            pos2 = middle.add(Sphere.get(i));
            if (this.canPlace(pos2)) {
                positions.add((Object)pos2);
            }
        }
        if (positions.isEmpty()) {
            return null;
        }
        switch (type) {
            case MOUSE: {
                if (Auto32k.mc.objectMouseOver != null && Auto32k.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                    final BlockPos mousePos = Auto32k.mc.objectMouseOver.getBlockPos();
                    if (mousePos != null && !this.canPlace(mousePos)) {
                        final BlockPos mousePosUp = mousePos.up();
                        if (this.canPlace(mousePosUp)) {
                            pos = mousePosUp;
                        }
                    }
                    else {
                        pos = mousePos;
                    }
                }
                if (pos != null) {
                    break;
                }
            }
            case CLOSE: {
                positions.sort((Comparator)Comparator.comparingDouble(pos2 -> Auto32k.mc.player.getDistanceSq(pos2)));
                pos = (BlockPos)positions.get(0);
                break;
            }
            case ENEMY: {
                positions.sort((Comparator)Comparator.comparingDouble((ToDoubleFunction<? super Object>)target::getDistanceSq));
                pos = (BlockPos)positions.get(0);
                break;
            }
            case MIDDLE: {
                final List<BlockPos> toRemove = new ArrayList<BlockPos>();
                final NonNullList<BlockPos> copy = (NonNullList<BlockPos>)NonNullList.func_191196_a();
                copy.addAll((Collection)positions);
                for (final BlockPos position : copy) {
                    final double difference = Auto32k.mc.player.getDistanceSq(position) - target.getDistanceSq(position);
                    if (difference > 1.0 || difference < -1.0) {
                        toRemove.add(position);
                    }
                }
                copy.removeAll((Collection)toRemove);
                if (copy.isEmpty()) {
                    copy.addAll((Collection)positions);
                }
                copy.sort((Comparator)Comparator.comparingDouble(pos2 -> Auto32k.mc.player.getDistanceSq(pos2)));
                pos = (BlockPos)copy.get(0);
                break;
            }
            case FAR: {
                positions.sort((Comparator)Comparator.comparingDouble(pos2 -> -target.getDistanceSq(pos2)));
                pos = (BlockPos)positions.get(0);
                break;
            }
            case SAFE: {
                positions.sort((Comparator)Comparator.comparingInt(pos2 -> -this.safetyFactor(pos2)));
                pos = (BlockPos)positions.get(0);
                break;
            }
        }
        return pos;
    }
    
    private boolean canPlace(final BlockPos pos) {
        if (pos == null) {
            return false;
        }
        final BlockPos boost = pos.up();
        return this.isGoodMaterial(Auto32k.mc.world.getBlockState(pos).getBlock(), this.onOtherHoppers.getValue()) && this.isGoodMaterial(Auto32k.mc.world.getBlockState(boost).getBlock(), false) && (!this.raytrace.getValue() || (rayTracePlaceCheck(pos, this.raytrace.getValue()) && rayTracePlaceCheck(pos, this.raytrace.getValue()))) && !this.badEntities(pos) && !this.badEntities(boost) && ((this.onOtherHoppers.getValue() && Auto32k.mc.world.getBlockState(pos).getBlock() instanceof BlockHopper) || this.findFacing(pos));
    }
    
    private void check() {
        if (this.currentStep != Step.PRE && this.currentStep != Step.HOPPER && this.hopperPos != null && !(Auto32k.mc.currentScreen instanceof GuiHopper) && !holding32k((EntityPlayer)Auto32k.mc.player) && (Auto32k.mc.player.getDistanceSq(this.hopperPos) > MathUtil.square(this.hopperDistance.getValue()) || Auto32k.mc.world.getBlockState(this.hopperPos).getBlock() != Blocks.HOPPER)) {
            this.resetFields();
            if (!this.autoSwitch.getValue() || !this.withBind.getValue() || this.mode.getValue() != Mode.NORMAL) {
                this.disable();
            }
        }
    }
    
    private void checkState() {
        if (!this.checkStatus.getValue() || this.checkedThisTick || (this.currentStep != Step.HOPPER && this.currentStep != Step.SHULKER && this.currentStep != Step.CLICKHOPPER)) {
            this.checkedThisTick = false;
            return;
        }
        if (this.hopperPos == null || !this.isGoodMaterial(Auto32k.mc.world.getBlockState(this.hopperPos).getBlock(), true) || (!this.isGoodMaterial(Auto32k.mc.world.getBlockState(this.hopperPos.up()).getBlock(), false) && !(Auto32k.mc.world.getBlockState(this.hopperPos.up()).getBlock() instanceof BlockShulkerBox)) || this.badEntities(this.hopperPos) || this.badEntities(this.hopperPos.up())) {
            if (this.autoSwitch.getValue() && this.mode.getValue() == Mode.NORMAL) {
                if (this.switching) {
                    this.resetFields();
                    if (this.repeatSwitch.getValue()) {
                        this.switching = true;
                    }
                }
                else {
                    this.resetFields();
                }
                if (!this.withBind.getValue()) {
                    this.disable();
                }
            }
            else {
                this.disable();
            }
            this.checkedThisTick = true;
        }
    }
    
    private void processDispenser32k() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.placeTimer.passed(this.delay.getValue())) {
            this.check();
            switch (this.currentStep) {
                case PRE: {
                    this.runDispenserPreStep();
                    if (this.currentStep == Step.PRE) {
                        break;
                    }
                }
                case HOPPER: {
                    this.runHopperStep();
                    this.currentStep = Step.DISPENSER;
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                }
                case DISPENSER: {
                    this.runDispenserStep();
                    final boolean quickCheck = !Auto32k.mc.world.getBlockState(this.finalDispenserData.getHelpingPos()).getMaterial().isReplaceable();
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                    if (this.currentStep != Step.DISPENSER_HELPING && this.currentStep != Step.CLICK_DISPENSER) {
                        break;
                    }
                    if (this.rotate.getValue() && quickCheck) {
                        break;
                    }
                }
                case DISPENSER_HELPING: {
                    this.runDispenserStep();
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                    if (this.currentStep != Step.CLICK_DISPENSER && this.currentStep != Step.DISPENSER_HELPING) {
                        break;
                    }
                    if (this.rotate.getValue()) {
                        break;
                    }
                }
                case CLICK_DISPENSER: {
                    this.clickDispenser();
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                }
                case DISPENSER_GUI: {
                    this.dispenserGui();
                    if (this.currentStep == Step.DISPENSER_GUI) {
                        break;
                    }
                }
                case REDSTONE: {
                    this.placeRedstone();
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                }
                case CLICKHOPPER: {
                    this.runClickHopper();
                    if (this.actionsThisTick >= this.delayDispenser.getValue() && !this.placeTimer.passed(this.delay.getValue())) {
                        break;
                    }
                }
                case HOPPERGUI: {
                    this.runHopperGuiStep();
                    if (this.actionsThisTick < this.delayDispenser.getValue() || !this.placeTimer.passed(this.delay.getValue())) {}
                    break;
                }
            }
        }
    }
    
    private void placeRedstone() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.badEntities(this.hopperPos.up()) && !(Auto32k.mc.world.getBlockState(this.hopperPos.up()).getBlock() instanceof BlockShulkerBox)) {
            return;
        }
        this.runPlaceStep(this.finalDispenserData.getRedStonePos(), this.redstoneSlot);
        this.currentStep = Step.CLICKHOPPER;
    }
    
    private void clickDispenser() {
        if (!this.isEnabled()) {
            return;
        }
        this.clickBlock(this.finalDispenserData.getDispenserPos());
        this.currentStep = Step.DISPENSER_GUI;
    }
    
    private void dispenserGui() {
        if (!this.isEnabled()) {
            return;
        }
        if (!(Auto32k.mc.currentScreen instanceof GuiDispenser)) {
            return;
        }
        Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, this.shulkerSlot, 0, ClickType.QUICK_MOVE, (EntityPlayer)Auto32k.mc.player);
        Auto32k.mc.player.closeScreen();
        this.currentStep = Step.REDSTONE;
    }
    
    private void clickBlock(final BlockPos pos) {
        if (!this.isEnabled() || pos == null) {
            return;
        }
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.authSneakPacket = false;
        final Vec3d hitVec = new Vec3d((Vec3i)pos).addVector(0.5, -0.5, 0.5);
        if (this.rotate.getValue()) {
            this.rotateToPos(null, hitVec);
        }
        EnumFacing facing = EnumFacing.UP;
        if (this.finalDispenserData != null && this.finalDispenserData.getDispenserPos() != null && this.finalDispenserData.getDispenserPos().equals((Object)pos) && pos.getY() > new BlockPos(Auto32k.mc.player.getPositionVector()).up().getY()) {
            facing = EnumFacing.DOWN;
        }
        rightClickBlock(pos, hitVec, (this.shulkerSlot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, facing, this.packet.getValue(), this.swing.getValue());
        Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
        ++this.actionsThisTick;
    }
    
    private void runDispenserStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.finalDispenserData == null || this.finalDispenserData.getDispenserPos() == null || this.finalDispenserData.getHelpingPos() == null) {
            this.resetFields();
            return;
        }
        if (this.currentStep != Step.DISPENSER && this.currentStep != Step.DISPENSER_HELPING) {
            return;
        }
        final BlockPos dispenserPos = this.finalDispenserData.getDispenserPos();
        final BlockPos helpingPos = this.finalDispenserData.getHelpingPos();
        if (!Auto32k.mc.world.getBlockState(helpingPos).getMaterial().isReplaceable()) {
            this.placeDispenserAgainstBlock(dispenserPos, helpingPos);
            ++this.actionsThisTick;
            this.currentStep = Step.CLICK_DISPENSER;
            return;
        }
        this.currentStep = Step.DISPENSER_HELPING;
        EnumFacing facing = EnumFacing.DOWN;
        boolean foundHelpingPos = false;
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos position = helpingPos.offset(enumFacing);
            if (!position.equals((Object)this.hopperPos) && !position.equals((Object)this.hopperPos.up()) && !position.equals((Object)dispenserPos) && !position.equals((Object)this.finalDispenserData.getRedStonePos()) && Auto32k.mc.player.getDistanceSq(position) <= MathUtil.square(this.range.getValue()) && (!this.raytrace.getValue() || rayTracePlaceCheck(position, this.raytrace.getValue())) && !Auto32k.mc.world.getBlockState(position).getMaterial().isReplaceable()) {
                foundHelpingPos = true;
                facing = enumFacing;
                break;
            }
        }
        if (!foundHelpingPos) {
            this.disable();
            return;
        }
        final BlockPos neighbour = helpingPos.offset(facing);
        final EnumFacing opposite = facing.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = Auto32k.mc.world.getBlockState(neighbour).getBlock();
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        this.authSneakPacket = false;
        if (this.rotate.getValue()) {
            if (this.blocksPerPlace.getValue() > 1) {
                final float[] angle = RotationUtil.getRotations(hitVec);
                if (this.extra.getValue()) {
                    faceYawAndPitch(angle[0], angle[1]);
                }
            }
            else {
                this.rotateToPos(null, hitVec);
            }
        }
        final int slot = (this.preferObby.getValue() && this.obbySlot != -1) ? this.obbySlot : this.dispenserSlot;
        InventoryUtil.switchTo(slot);
        rightClickBlock(neighbour, hitVec, (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, opposite, this.packet.getValue(), this.swing.getValue());
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.authSneakPacket = false;
        this.placeTimer.reset();
        ++this.actionsThisTick;
    }
    
    private void placeDispenserAgainstBlock(final BlockPos dispenserPos, final BlockPos helpingPos) {
        if (!this.isEnabled()) {
            return;
        }
        EnumFacing facing = EnumFacing.DOWN;
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos position = dispenserPos.offset(enumFacing);
            if (position.equals((Object)helpingPos)) {
                facing = enumFacing;
                break;
            }
        }
        final EnumFacing opposite = facing.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)helpingPos).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = Auto32k.mc.world.getBlockState(helpingPos).getBlock();
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        this.authSneakPacket = false;
        Vec3d rotationVec = null;
        EnumFacing facings = EnumFacing.UP;
        if (this.rotate.getValue()) {
            if (this.blocksPerPlace.getValue() > 1) {
                final float[] angle = RotationUtil.getRotations(hitVec);
                if (this.extra.getValue()) {
                    faceYawAndPitch(angle[0], angle[1]);
                }
            }
            else {
                this.rotateToPos(null, hitVec);
            }
            rotationVec = new Vec3d((Vec3i)helpingPos).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        }
        else if (dispenserPos.getY() <= new BlockPos(Auto32k.mc.player.getPositionVector()).up().getY()) {
            for (final EnumFacing enumFacing2 : EnumFacing.values()) {
                final BlockPos position2 = this.hopperPos.up().offset(enumFacing2);
                if (position2.equals((Object)dispenserPos)) {
                    facings = enumFacing2;
                    break;
                }
            }
            final float[] rotations = simpleFacing(facings);
            this.yaw = rotations[0];
            this.pitch = rotations[1];
            this.spoof = true;
        }
        else {
            final float[] rotations = simpleFacing(facings);
            this.yaw = rotations[0];
            this.pitch = rotations[1];
            this.spoof = true;
        }
        rotationVec = new Vec3d((Vec3i)helpingPos).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final float[] rotations = simpleFacing(facings);
        final float[] angle2 = RotationUtil.getRotations(hitVec);
        if (this.superPacket.getValue()) {
            faceYawAndPitch(((boolean)this.rotate.getValue()) ? angle2[0] : rotations[0], ((boolean)this.rotate.getValue()) ? angle2[1] : rotations[1]);
        }
        InventoryUtil.switchTo(this.dispenserSlot);
        rightClickBlock(helpingPos, rotationVec, (this.dispenserSlot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, opposite, this.packet.getValue(), this.swing.getValue());
        this.authSneakPacket = true;
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Auto32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.authSneakPacket = false;
        this.placeTimer.reset();
        ++this.actionsThisTick;
        this.currentStep = Step.CLICK_DISPENSER;
    }
    
    private void runDispenserPreStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (Auto32k.FREECAM.isEnabled() && !this.freecam.getValue()) {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Disable freecam.", this.getDisplayName(), 2000);
            }
            this.disable();
            return;
        }
        this.lastHotbarSlot = Auto32k.mc.player.inventory.currentItem;
        this.hopperSlot = InventoryUtil.findHotbarBlock((Block)Blocks.HOPPER, new Block[0]);
        this.shulkerSlot = InventoryUtil.findInInventory(item -> item.getItem() instanceof ItemShulkerBox, false);
        this.dispenserSlot = InventoryUtil.findHotbarBlock(Blocks.DISPENSER, new Block[0]);
        this.redstoneSlot = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK, new Block[0]);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
        if (Auto32k.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            final Block block = ((ItemBlock)Auto32k.mc.player.getHeldItemOffhand().getItem()).getBlock();
            if (block instanceof BlockHopper) {
                this.hopperSlot = -2;
            }
            else if (block instanceof BlockDispenser) {
                this.dispenserSlot = -2;
            }
            else if (block == Blocks.REDSTONE_BLOCK) {
                this.redstoneSlot = -2;
            }
            else if (block instanceof BlockObsidian) {
                this.obbySlot = -2;
            }
        }
        if (this.shulkerSlot == -1 || this.hopperSlot == -1 || this.dispenserSlot == -1 || this.redstoneSlot == -1) {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Materials not found.", this.getDisplayName(), 2000);
            }
            this.disable();
            return;
        }
        this.finalDispenserData = this.findBestPos();
        if (this.finalDispenserData.isPlaceable()) {
            this.hopperPos = this.finalDispenserData.getHopperPos();
            if (Auto32k.mc.world.getBlockState(this.hopperPos).getBlock() instanceof BlockHopper) {
                this.currentStep = Step.DISPENSER;
            }
            else {
                this.currentStep = Step.HOPPER;
            }
        }
        else {
            if (this.messages.getValue()) {
                Managers.CHAT.sendDeleteMessage("§c<Auto32k> Block not found.", this.getDisplayName(), 2000);
            }
            this.disable();
        }
    }
    
    private DispenserData findBestPos() {
        PlaceType type = this.placeType.getValue();
        this.target = EntityUtil.getClosestEnemy();
        if (this.target == null || Auto32k.mc.player.getDistanceSqToEntity((Entity)this.target) > MathUtil.square(this.targetRange.getValue())) {
            type = ((this.placeType.getValue() == PlaceType.MOUSE) ? PlaceType.MOUSE : PlaceType.CLOSE);
        }
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        final BlockPos middle = PositionUtil.getPosition();
        for (int maxRadius = Sphere.getRadius(this.range.getValue()), i = 1; i < maxRadius; ++i) {
            positions.add((Object)middle.add(Sphere.get(i)));
        }
        DispenserData data = new DispenserData();
        switch (type) {
            case MOUSE: {
                if (Auto32k.mc.objectMouseOver != null && Auto32k.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                    final BlockPos mousePos = Auto32k.mc.objectMouseOver.getBlockPos();
                    if (mousePos != null) {
                        data = this.analyzePos(mousePos);
                        if (!data.isPlaceable()) {
                            data = this.analyzePos(mousePos.up());
                        }
                    }
                }
                if (data.isPlaceable()) {
                    return data;
                }
            }
            case CLOSE: {
                positions.sort((Comparator)Comparator.comparingDouble(pos2 -> Auto32k.mc.player.getDistanceSq(pos2)));
                break;
            }
            case ENEMY: {
                positions.sort((Comparator)Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.target::getDistanceSq));
                break;
            }
            case MIDDLE: {
                final List<BlockPos> toRemove = new ArrayList<BlockPos>();
                final NonNullList<BlockPos> copy = (NonNullList<BlockPos>)NonNullList.func_191196_a();
                copy.addAll((Collection)positions);
                for (final BlockPos position : copy) {
                    final double difference = Auto32k.mc.player.getDistanceSq(position) - this.target.getDistanceSq(position);
                    if (difference > 1.0 || difference < -1.0) {
                        toRemove.add(position);
                    }
                }
                copy.removeAll((Collection)toRemove);
                if (copy.isEmpty()) {
                    copy.addAll((Collection)positions);
                }
                copy.sort((Comparator)Comparator.comparingDouble(pos2 -> Auto32k.mc.player.getDistanceSq(pos2)));
                break;
            }
            case FAR: {
                positions.sort((Comparator)Comparator.comparingDouble(pos2 -> -this.target.getDistanceSq(pos2)));
                break;
            }
            case SAFE: {
                positions.sort((Comparator)Comparator.comparingInt(pos2 -> -this.safetyFactor(pos2)));
                break;
            }
        }
        data = this.findData(positions);
        return data;
    }
    
    private DispenserData findData(final NonNullList<BlockPos> positions) {
        for (final BlockPos position : positions) {
            final DispenserData data = this.analyzePos(position);
            if (data.isPlaceable()) {
                return data;
            }
        }
        return new DispenserData();
    }
    
    private DispenserData analyzePos(final BlockPos pos) {
        final DispenserData data = new DispenserData(pos);
        if (pos == null) {
            return data;
        }
        if (!this.isGoodMaterial(Auto32k.mc.world.getBlockState(pos).getBlock(), this.onOtherHoppers.getValue()) || !this.isGoodMaterial(Auto32k.mc.world.getBlockState(pos.up()).getBlock(), false)) {
            return data;
        }
        if (this.raytrace.getValue() && !rayTracePlaceCheck(pos, this.raytrace.getValue())) {
            return data;
        }
        if (this.badEntities(pos) || this.badEntities(pos.up())) {
            return data;
        }
        if (this.hasAdjancedRedstone(pos)) {
            return data;
        }
        if (!this.findFacing(pos)) {
            return data;
        }
        final BlockPos[] otherPositions = this.checkForDispenserPos(pos);
        if (otherPositions[0] == null || otherPositions[1] == null || otherPositions[2] == null) {
            return data;
        }
        data.setDispenserPos(otherPositions[0]);
        data.setRedStonePos(otherPositions[1]);
        data.setHelpingPos(otherPositions[2]);
        data.setPlaceable(true);
        return data;
    }
    
    private boolean findFacing(final BlockPos pos) {
        boolean foundFacing = false;
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.UP) {
                if (facing == EnumFacing.DOWN && this.antiHopper.getValue() && Auto32k.mc.world.getBlockState(pos.offset(facing)).getBlock() == Blocks.HOPPER) {
                    foundFacing = false;
                    break;
                }
                if (!Auto32k.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable() && (!this.antiHopper.getValue() || Auto32k.mc.world.getBlockState(pos.offset(facing)).getBlock() != Blocks.HOPPER)) {
                    foundFacing = true;
                }
            }
        }
        return foundFacing;
    }
    
    private BlockPos[] checkForDispenserPos(final BlockPos posIn) {
        final BlockPos[] pos = new BlockPos[3];
        final BlockPos playerPos = new BlockPos(Auto32k.mc.player.getPositionVector());
        if (posIn.getY() < playerPos.down().getY()) {
            return pos;
        }
        final List<BlockPos> possiblePositions = this.getDispenserPositions(posIn);
        if (posIn.getY() < playerPos.getY()) {
            possiblePositions.remove(posIn.up().up());
        }
        else if (posIn.getY() > playerPos.getY()) {
            possiblePositions.remove(posIn.west().up());
            possiblePositions.remove(posIn.north().up());
            possiblePositions.remove(posIn.south().up());
            possiblePositions.remove(posIn.east().up());
        }
        if (this.rotate.getValue() || this.simulate.getValue()) {
            possiblePositions.sort(Comparator.comparingDouble(pos2 -> -Auto32k.mc.player.getDistanceSq(pos2)));
            final BlockPos posToCheck = possiblePositions.get(0);
            if (!this.isGoodMaterial(Auto32k.mc.world.getBlockState(posToCheck).getBlock(), false)) {
                return pos;
            }
            if (Auto32k.mc.player.getDistanceSq(posToCheck) > MathUtil.square(this.range.getValue())) {
                return pos;
            }
            if (this.raytrace.getValue() && !rayTracePlaceCheck(posToCheck, this.raytrace.getValue())) {
                return pos;
            }
            if (this.badEntities(posToCheck)) {
                return pos;
            }
            if (this.hasAdjancedRedstone(posToCheck)) {
                return pos;
            }
            final List<BlockPos> possibleRedStonePositions = this.checkRedStone(posToCheck, posIn);
            if (possiblePositions.isEmpty()) {
                return pos;
            }
            final BlockPos[] helpingStuff = this.getHelpingPos(posToCheck, posIn, possibleRedStonePositions);
            if (helpingStuff != null && helpingStuff[0] != null && helpingStuff[1] != null) {
                pos[0] = posToCheck;
                pos[1] = helpingStuff[1];
                pos[2] = helpingStuff[0];
            }
        }
        else {
            BlockPos position = null;
            possiblePositions.removeIf(position -> Auto32k.mc.player.getDistanceSq(position) > MathUtil.square(this.range.getValue()));
            possiblePositions.removeIf(position -> !this.isGoodMaterial(Auto32k.mc.world.getBlockState(position).getBlock(), false));
            possiblePositions.removeIf(position -> this.raytrace.getValue() && !rayTracePlaceCheck(position, this.raytrace.getValue()));
            possiblePositions.removeIf(this::badEntities);
            possiblePositions.removeIf(this::hasAdjancedRedstone);
            final Iterator<BlockPos> iterator = possiblePositions.iterator();
            while (iterator.hasNext()) {
                position = iterator.next();
                final List<BlockPos> possibleRedStonePositions2 = this.checkRedStone(position, posIn);
                if (possiblePositions.isEmpty()) {
                    continue;
                }
                final BlockPos[] helpingStuff2 = this.getHelpingPos(position, posIn, possibleRedStonePositions2);
                if (helpingStuff2 != null && helpingStuff2[0] != null && helpingStuff2[1] != null) {
                    pos[0] = position;
                    pos[1] = helpingStuff2[1];
                    pos[2] = helpingStuff2[0];
                    break;
                }
            }
        }
        return pos;
    }
    
    private List<BlockPos> checkRedStone(final BlockPos pos, final BlockPos hopperPos) {
        final List<BlockPos> toCheck = new ArrayList<BlockPos>();
        for (final EnumFacing facing : EnumFacing.values()) {
            toCheck.add(pos.offset(facing));
        }
        toCheck.removeIf(position -> position.equals((Object)hopperPos.up()));
        toCheck.removeIf(position -> Auto32k.mc.player.getDistanceSq(position) > MathUtil.square(this.range.getValue()));
        toCheck.removeIf(position -> !this.isGoodMaterial(Auto32k.mc.world.getBlockState(position).getBlock(), false));
        toCheck.removeIf(position -> this.raytrace.getValue() && !rayTracePlaceCheck(position, this.raytrace.getValue()));
        toCheck.removeIf(this::badEntities);
        toCheck.sort(Comparator.comparingDouble(pos2 -> Auto32k.mc.player.getDistanceSq(pos2)));
        return toCheck;
    }
    
    private boolean hasAdjancedRedstone(final BlockPos pos) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final BlockPos position = pos.offset(facing);
            if (Auto32k.mc.world.getBlockState(position).getBlock() == Blocks.REDSTONE_BLOCK || Auto32k.mc.world.getBlockState(position).getBlock() == Blocks.REDSTONE_TORCH) {
                return true;
            }
        }
        return false;
    }
    
    private List<BlockPos> getDispenserPositions(final BlockPos pos) {
        final List<BlockPos> list = new ArrayList<BlockPos>();
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.DOWN) {
                list.add(pos.offset(facing).up());
            }
        }
        return list;
    }
    
    private BlockPos[] getHelpingPos(final BlockPos pos, final BlockPos hopperPos, final List<BlockPos> redStonePositions) {
        final BlockPos[] result = new BlockPos[2];
        final List<BlockPos> possiblePositions = new ArrayList<BlockPos>();
        if (redStonePositions.isEmpty()) {
            return null;
        }
        for (final EnumFacing facing : EnumFacing.values()) {
            final BlockPos facingPos = pos.offset(facing);
            if (!facingPos.equals((Object)hopperPos) && !facingPos.equals((Object)hopperPos.up())) {
                if (!Auto32k.mc.world.getBlockState(facingPos).getMaterial().isReplaceable()) {
                    if (!redStonePositions.contains(facingPos)) {
                        result[0] = facingPos;
                        result[1] = redStonePositions.get(0);
                        return result;
                    }
                    redStonePositions.remove(facingPos);
                    if (!redStonePositions.isEmpty()) {
                        result[0] = facingPos;
                        result[1] = redStonePositions.get(0);
                        return result;
                    }
                    redStonePositions.add(facingPos);
                }
                else {
                    for (final EnumFacing facing2 : EnumFacing.values()) {
                        final BlockPos facingPos2 = facingPos.offset(facing2);
                        if (!facingPos2.equals((Object)hopperPos) && !facingPos2.equals((Object)hopperPos.up()) && !facingPos2.equals((Object)pos) && !Auto32k.mc.world.getBlockState(facingPos2).getMaterial().isReplaceable()) {
                            if (redStonePositions.contains(facingPos)) {
                                redStonePositions.remove(facingPos);
                                if (redStonePositions.isEmpty()) {
                                    redStonePositions.add(facingPos);
                                }
                                else {
                                    possiblePositions.add(facingPos);
                                }
                            }
                            else {
                                possiblePositions.add(facingPos);
                            }
                        }
                    }
                }
            }
        }
        possiblePositions.removeIf(position -> Auto32k.mc.player.getDistanceSq(position) > MathUtil.square(this.range.getValue()));
        possiblePositions.sort(Comparator.comparingDouble(position -> Auto32k.mc.player.getDistanceSq(position)));
        if (!possiblePositions.isEmpty()) {
            redStonePositions.remove(possiblePositions.get(0));
            if (!redStonePositions.isEmpty()) {
                result[0] = possiblePositions.get(0);
                result[1] = redStonePositions.get(0);
            }
            return result;
        }
        return null;
    }
    
    private void rotateToPos(final BlockPos pos, final Vec3d vec3d) {
        float[] angle;
        if (vec3d == null) {
            angle = calcAngle(Auto32k.mc.player.getPositionEyes(Auto32k.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
        }
        else {
            angle = RotationUtil.getRotations(vec3d);
        }
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.spoof = true;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.xCoord - from.xCoord;
        final double difY = (to.yCoord - from.yCoord) * -1.0;
        final double difZ = to.zCoord - from.zCoord;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    private boolean isGoodMaterial(final Block block, final boolean allowHopper) {
        return block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow || (allowHopper && block instanceof BlockHopper);
    }
    
    private void resetFields() {
        this.shouldDisable = false;
        this.spoof = false;
        this.switching = false;
        this.lastHotbarSlot = -1;
        this.shulkerSlot = -1;
        this.hopperSlot = -1;
        this.hopperPos = null;
        this.target = null;
        this.currentStep = Step.PRE;
        this.obbySlot = -1;
        this.dispenserSlot = -1;
        this.redstoneSlot = -1;
        this.finalDispenserData = null;
        this.actionsThisTick = 0;
        this.rotationprepared = false;
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet, final boolean swing) {
        if (packet) {
            final float f = (float)(vec.xCoord - pos.getX());
            final float f2 = (float)(vec.yCoord - pos.getY());
            final float f3 = (float)(vec.zCoord - pos.getZ());
            Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            Auto32k.mc.playerController.processRightClickBlock(Auto32k.mc.player, Auto32k.mc.world, pos, direction, vec, hand);
        }
        if (swing) {
            Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || Auto32k.mc.world.rayTraceBlocks(new Vec3d(Auto32k.mc.player.posX, Auto32k.mc.player.posY + Auto32k.mc.player.getEyeHeight(), Auto32k.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos) {
        return rayTracePlaceCheck(pos, true);
    }
    
    public static void faceYawAndPitch(final float yaw, final float pitch) {
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, Auto32k.mc.player.onGround));
    }
    
    private boolean badEntities(final BlockPos pos) {
        for (final Entity entity : Auto32k.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return true;
            }
        }
        return false;
    }
    
    private int safetyFactor(final BlockPos pos) {
        return this.safety(pos) + this.safety(pos.up());
    }
    
    private int safety(final BlockPos pos) {
        int safety = 0;
        for (final EnumFacing facing : EnumFacing.values()) {
            if (!Auto32k.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                ++safety;
            }
        }
        return safety;
    }
    
    public static float[] simpleFacing(final EnumFacing facing) {
        switch (facing) {
            case DOWN: {
                return new float[] { Auto32k.mc.player.rotationYaw, 90.0f };
            }
            case UP: {
                return new float[] { Auto32k.mc.player.rotationYaw, -90.0f };
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
    
    public static boolean holding32k(final EntityPlayer player) {
        return is32k(player.getHeldItemMainhand());
    }
    
    public static boolean is32k(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getTagCompound() == null) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl >= 42) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    public static boolean simpleIs32k(final ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack) >= 1000;
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
    
    public static class DispenserData
    {
        private BlockPos dispenserPos;
        private BlockPos redStonePos;
        private BlockPos hopperPos;
        private BlockPos helpingPos;
        private boolean isPlaceable;
        
        public DispenserData() {
            this.isPlaceable = false;
        }
        
        public DispenserData(final BlockPos pos) {
            this.isPlaceable = false;
            this.hopperPos = pos;
        }
        
        public void setPlaceable(final boolean placeable) {
            this.isPlaceable = placeable;
        }
        
        public boolean isPlaceable() {
            return this.dispenserPos != null && this.hopperPos != null && this.redStonePos != null && this.helpingPos != null;
        }
        
        public BlockPos getDispenserPos() {
            return this.dispenserPos;
        }
        
        public void setDispenserPos(final BlockPos dispenserPos) {
            this.dispenserPos = dispenserPos;
        }
        
        public BlockPos getRedStonePos() {
            return this.redStonePos;
        }
        
        public void setRedStonePos(final BlockPos redStonePos) {
            this.redStonePos = redStonePos;
        }
        
        public BlockPos getHopperPos() {
            return this.hopperPos;
        }
        
        public void setHopperPos(final BlockPos hopperPos) {
            this.hopperPos = hopperPos;
        }
        
        public BlockPos getHelpingPos() {
            return this.helpingPos;
        }
        
        public void setHelpingPos(final BlockPos helpingPos) {
            this.helpingPos = helpingPos;
        }
    }
    
    public enum PlaceType
    {
        MOUSE, 
        CLOSE, 
        ENEMY, 
        MIDDLE, 
        FAR, 
        SAFE;
    }
    
    public enum Mode
    {
        NORMAL, 
        DISPENSER;
    }
    
    public enum Step
    {
        PRE, 
        HOPPER, 
        SHULKER, 
        CLICKHOPPER, 
        HOPPERGUI, 
        DISPENSER_HELPING, 
        DISPENSER_GUI, 
        DISPENSER, 
        CLICK_DISPENSER, 
        REDSTONE;
    }
}
