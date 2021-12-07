//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks;

import me.earth.earthhack.impl.util.helpers.blocks.attack.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.blocks.data.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.api.event.bus.api.*;

public abstract class ObbyModule extends BlockPlacingModule implements AttackingModule
{
    public static final BlockStateHelper HELPER;
    public final Setting<Boolean> attack;
    public final Setting<Pop> pop;
    public final Setting<Integer> popTime;
    public final Setting<Integer> cooldown;
    public final Setting<Boolean> antiWeakness;
    public final Setting<Integer> breakDelay;
    public final Setting<FastHelping> fastHelpingBlocks;
    public final Setting<Boolean> attackAny;
    public final Setting<Double> attackRange;
    public final Setting<Double> attackTrace;
    public final StopWatch attackTimer;
    public CPacketUseEntity attacking;
    
    protected ObbyModule(final String name, final Category category) {
        super(name, category);
        this.attack = this.register(new BooleanSetting("Attack", false));
        this.pop = this.register(new EnumSetting("Pop", Pop.None));
        this.popTime = this.register(new NumberSetting("Pop-Time", 500, 0, 500));
        this.cooldown = this.register(new NumberSetting("Cooldown", 500, 0, 500));
        this.antiWeakness = this.register(new BooleanSetting("AntiWeakness", false));
        this.breakDelay = this.register(new NumberSetting("BreakDelay", 250, 0, 500));
        this.fastHelpingBlocks = this.register(new EnumSetting("Fast-Helping", FastHelping.Fast));
        this.attackAny = this.register(new BooleanSetting("Attack-Any", false));
        this.attackRange = this.register(new NumberSetting("Attack-Range", 6.0, 0.0, 6.0));
        this.attackTrace = this.register(new NumberSetting("Attack-Trace", 3.0, 0.0, 6.0));
        this.attackTimer = new StopWatch();
        this.attacking = null;
        this.setData(new ObbyData<Object>(this));
    }
    
    @Override
    public Pop getPop() {
        return this.pop.getValue();
    }
    
    @Override
    public double getRange() {
        return this.attackRange.getValue();
    }
    
    @Override
    public int getPopTime() {
        return this.popTime.getValue();
    }
    
    @Override
    public double getTrace() {
        return this.attackTrace.getValue();
    }
    
    @Override
    public String getDisplayInfo() {
        final double time = MathUtil.round(this.timer.getTime() / 1000.0, 1);
        return ((time > 0.5) ? ((time > 1.0) ? ((time > 1.5) ? "§a" : "§e") : "§6") : "§c") + time;
    }
    
    @Override
    protected void onEnable() {
        this.attacking = null;
    }
    
    @Override
    public boolean execute() {
        this.lastSlot = -1;
        if (!this.packets.isEmpty()) {
            if (this.attacking != null) {
                boolean switched = false;
                int slot = -1;
                if (!DamageUtil.canBreakWeakness(true)) {
                    if (this.cooldown.getValue() != 0 || !this.antiWeakness.getValue() || (slot = DamageUtil.findAntiWeakness()) == -1) {
                        this.filterPackets();
                        return !this.packets.isEmpty() && super.execute();
                    }
                    this.lastSlot = ObbyModule.mc.player.inventory.currentItem;
                    switch (this.cooldownBypass.getValue()) {
                        case None: {
                            InventoryUtil.switchTo(slot);
                            break;
                        }
                        case Slot: {
                            InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(slot));
                            switched = true;
                            break;
                        }
                        case Pick: {
                            InventoryUtil.bypassSwitch(slot);
                            switched = true;
                            break;
                        }
                    }
                }
                ObbyModule.mc.player.connection.sendPacket((Packet)this.attacking);
                Swing.Packet.swing(EnumHand.MAIN_HAND);
                if (switched) {
                    if (this.cooldownBypass.getValue() == CooldownBypass.Pick) {
                        InventoryUtil.bypassSwitch(slot);
                    }
                    else if (this.cooldownBypass.getValue() == CooldownBypass.Slot) {
                        InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(slot));
                    }
                }
                this.attackTimer.reset();
                this.attacking = null;
            }
            return super.execute();
        }
        return false;
    }
    
    protected void filterPackets() {
        boolean awaitingSwing = false;
        CPacketPlayer.Rotation rotation = null;
        final List<Packet<?>> toRemove = new ArrayList<Packet<?>>();
        for (final Packet<?> p : this.packets) {
            if (p instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock c = (CPacketPlayerTryUseItemOnBlock)p;
                final BlockPos pos = c.getPos().offset(c.getDirection());
                for (final Entity entity : ObbyModule.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                    if (!EntityUtil.isDead(entity) && entity.preventEntitySpawning && (RotationUtil.getRotationPlayer().equals((Object)ObbyModule.mc.player) || !ObbyModule.mc.player.equals((Object)entity))) {
                        if (rotation != null) {
                            toRemove.add((Packet<?>)rotation);
                        }
                        toRemove.add(p);
                        awaitingSwing = true;
                    }
                }
            }
            else if (p instanceof CPacketPlayer.Rotation) {
                rotation = (CPacketPlayer.Rotation)p;
            }
            else {
                if (!awaitingSwing || !(p instanceof CPacketAnimation)) {
                    continue;
                }
                awaitingSwing = false;
                toRemove.add(p);
            }
        }
        this.packets.removeAll(toRemove);
    }
    
    @Override
    protected boolean sneak(final Collection<Packet<?>> packets) {
        return this.smartSneak.getValue() && !Managers.ACTION.isSneaking() && !packets.stream().anyMatch(p -> SpecialBlocks.ACCESS_CHECK.test((Packet<?>)p, (IBlockAccess)ObbyModule.HELPER));
    }
    
    @Override
    public boolean entityCheck(final BlockPos pos) {
        CPacketUseEntity attackPacket = null;
        boolean crystals = false;
        float currentDmg = Float.MAX_VALUE;
        for (final Entity entity : ObbyModule.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !EntityUtil.isDead(entity) && entity.preventEntitySpawning) {
                if (entity instanceof EntityPlayer && !BlockUtil.isBlocking(pos, (EntityPlayer)entity, this.blockingType.getValue())) {
                    continue;
                }
                if (entity instanceof EntityEnderCrystal && this.attackTimer.passed(this.breakDelay.getValue()) && this.attack.getValue() && Managers.SWITCH.getLastSwitch() >= this.cooldown.getValue()) {
                    final float damage = DamageUtil.calculate(entity, (EntityLivingBase)this.getPlayerForRotations());
                    if (damage < currentDmg) {
                        currentDmg = damage;
                        if (this.pop.getValue().shouldPop(damage, this.popTime.getValue())) {
                            attackPacket = new CPacketUseEntity(entity);
                            continue;
                        }
                    }
                    crystals = true;
                }
                else {
                    if (this.blockingType.getValue() != BlockingType.Crystals || !(entity instanceof EntityEnderCrystal)) {
                        return false;
                    }
                    continue;
                }
            }
        }
        if (crystals && attackPacket == null && this.blockingType.getValue() != BlockingType.Crystals) {
            return false;
        }
        if (attackPacket != null) {
            this.attacking = attackPacket;
        }
        return true;
    }
    
    public boolean placeBlock(final BlockPos pos) {
        if (this.smartRay.getValue() != RayTraceMode.Fast) {
            final Entity entity = (Entity)this.getPlayerForRotations();
            Ray forceRay = null;
            Ray forceHelpingRay = null;
            Ray dumbRay = null;
            Ray dumbHelpingRay = null;
            Ray ray = RayTraceFactory.fullTrace(entity, (IBlockAccess)ObbyModule.HELPER, pos, -1.0);
            if (ray == null || this.shouldHelp(ray.getFacing(), pos) || !ray.getPos().offset(ray.getFacing()).equals((Object)pos) || (!ray.isLegit() && (this.smartRay.getValue() == RayTraceMode.Smart || this.smartRay.getValue() == RayTraceMode.Force))) {
                if (ray != null && ray.getPos().offset(ray.getFacing()).equals((Object)pos)) {
                    dumbRay = ray;
                    forceRay = ray;
                }
                for (final EnumFacing facing : EnumFacing.values()) {
                    final BlockPos helpingPos = pos.offset(facing);
                    final IBlockState state = ObbyModule.HELPER.getBlockState(helpingPos);
                    if (state.getMaterial().isReplaceable()) {
                        if (!this.quickEntityCheck(helpingPos)) {
                            final Ray helpingRay = RayTraceFactory.fullTrace(entity, (IBlockAccess)ObbyModule.HELPER, helpingPos, -1.0);
                            if (helpingRay == null || !helpingRay.getPos().offset(helpingRay.getFacing()).equals((Object)helpingPos) || (!helpingRay.isLegit() && (this.smartRay.getValue() == RayTraceMode.Smart || this.smartRay.getValue() == RayTraceMode.Force))) {
                                if (dumbRay == null && helpingRay != null && helpingRay.getPos().offset(helpingRay.getFacing()).equals((Object)helpingPos)) {
                                    dumbHelpingRay = helpingRay;
                                    this.setState(helpingPos);
                                    dumbRay = RayTraceFactory.rayTrace(entity, helpingPos, facing.getOpposite(), (IBlockAccess)ObbyModule.HELPER, state, -1.0);
                                    if (!dumbRay.getPos().offset(dumbRay.getFacing()).equals((Object)pos)) {
                                        dumbRay = null;
                                        dumbHelpingRay = null;
                                    }
                                    ObbyModule.HELPER.delete(helpingPos);
                                }
                            }
                            else {
                                this.setState(helpingPos);
                                ray = RayTraceFactory.rayTrace(entity, helpingPos, facing.getOpposite(), (IBlockAccess)ObbyModule.HELPER, state, -1.0);
                                if (ray != null) {
                                    if (ray.getPos().offset(ray.getFacing()).equals((Object)pos)) {
                                        if (forceRay == null) {
                                            forceRay = ray;
                                            forceHelpingRay = helpingRay;
                                        }
                                        if ((ray.isLegit() || (this.smartRay.getValue() != RayTraceMode.Smart && this.smartRay.getValue() != RayTraceMode.Force)) && this.entityCheck(helpingPos)) {
                                            this.placeBlock(helpingRay.getPos(), helpingRay.getFacing(), helpingRay.getRotations(), helpingRay.getResult().hitVec);
                                            if (this.blocksPlaced >= this.blocks.getValue() || this.noFastHelp(helpingPos, pos)) {
                                                return true;
                                            }
                                            if (this.entityCheck(pos)) {
                                                this.placeBlock(ray.getPos(), ray.getFacing(), ray.getRotations(), ray.getResult().hitVec);
                                                this.setState(pos);
                                                return this.blocksPlaced >= this.blocks.getValue() || this.rotate.getValue() == Rotate.Normal;
                                            }
                                            return false;
                                        }
                                        else {
                                            ObbyModule.HELPER.delete(helpingPos);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if ((ray.isLegit() || (this.smartRay.getValue() != RayTraceMode.Smart && this.smartRay.getValue() != RayTraceMode.Force)) && this.entityCheck(pos)) {
                this.setState(pos);
                this.placeBlock(ray.getPos(), ray.getFacing(), ray.getRotations(), ray.getResult().hitVec);
                return this.blocksPlaced >= this.blocks.getValue() || this.rotate.getValue() == Rotate.Normal;
            }
            if (forceRay == null || !forceRay.getPos().offset(forceRay.getFacing()).equals((Object)pos)) {
                forceRay = dumbRay;
                forceHelpingRay = dumbHelpingRay;
            }
            if (this.smartRay.getValue() != RayTraceMode.Force || forceRay == null || !forceRay.getPos().offset(forceRay.getFacing()).equals((Object)pos)) {
                return false;
            }
            if (forceHelpingRay != null) {
                final BlockPos helping = forceHelpingRay.getPos().offset(forceHelpingRay.getFacing());
                if (!this.entityCheck(helping)) {
                    return false;
                }
                this.placeBlock(forceHelpingRay.getPos(), forceHelpingRay.getFacing(), forceHelpingRay.getRotations(), forceHelpingRay.getResult().hitVec);
                this.setState(helping);
                if (this.blocksPlaced >= this.blocks.getValue() || this.noFastHelp(helping, pos)) {
                    return true;
                }
            }
            final BlockPos forcePos = forceRay.getPos().offset(forceRay.getFacing());
            if (!this.entityCheck(forcePos)) {
                return false;
            }
            this.placeBlock(forceRay.getPos(), forceRay.getFacing(), forceRay.getRotations(), forceRay.getResult().hitVec);
            this.setState(forcePos);
            return this.blocksPlaced >= this.blocks.getValue() || this.rotate.getValue() == Rotate.Normal;
        }
        else {
            EnumFacing initialFacing = BlockUtil.getFacing(pos, (IBlockAccess)ObbyModule.HELPER);
            if (this.shouldHelp(initialFacing, pos)) {
                BlockPos helpingPos2 = null;
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    helpingPos2 = pos.offset(facing2);
                    final EnumFacing helpingFacing = BlockUtil.getFacing(helpingPos2, (IBlockAccess)ObbyModule.HELPER);
                    if (helpingFacing != null && this.entityCheck(helpingPos2)) {
                        initialFacing = facing2;
                        this.placeBlock(helpingPos2.offset(helpingFacing), helpingFacing.getOpposite());
                        this.setState(helpingPos2);
                        break;
                    }
                }
                if (this.blocksPlaced >= this.blocks.getValue() || (helpingPos2 != null && this.noFastHelp(helpingPos2, pos))) {
                    return true;
                }
            }
            if (initialFacing == null || !this.entityCheck(pos)) {
                return false;
            }
            this.placeBlock(pos.offset(initialFacing), initialFacing.getOpposite());
            this.setState(pos);
            return this.blocksPlaced >= this.blocks.getValue() || this.rotate.getValue() == Rotate.Normal;
        }
    }
    
    protected boolean shouldHelp(final EnumFacing facing, final BlockPos pos) {
        return facing == null;
    }
    
    public void setState(final BlockPos pos) {
        Block block = (this.slot <= 0 || this.slot > 8) ? Blocks.ENDER_CHEST : null;
        if (block == null) {
            final Item item = (this.slot == -2) ? ObbyModule.mc.player.getHeldItemOffhand().getItem() : ObbyModule.mc.player.inventory.getStackInSlot(this.slot).getItem();
            if (item instanceof ItemBlock) {
                block = ((ItemBlock)item).getBlock();
            }
        }
        if (block != null) {
            ObbyModule.HELPER.addBlockState(pos, block.getDefaultState());
        }
    }
    
    protected boolean quickEntityCheck(final BlockPos pos) {
        return ObbyModule.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos)).stream().anyMatch(e -> e != null && !EntityUtil.isDead(e) && e.preventEntitySpawning && (!(e instanceof EntityEnderCrystal) || !this.attack.getValue()));
    }
    
    protected boolean noFastHelp(final BlockPos helpingPos, final BlockPos pos) {
        switch (this.fastHelpingBlocks.getValue()) {
            case Off: {
                return this.rotate.getValue() == Rotate.Normal;
            }
            case Down: {
                return this.rotate.getValue() == Rotate.Normal && !pos.down().equals((Object)helpingPos);
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        HELPER = new BlockStateHelper();
        Bus.EVENT_BUS.register(new EventListener<MotionUpdateEvent>((Class)MotionUpdateEvent.class, Integer.MAX_VALUE) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
                if (event.getStage() == Stage.POST) {
                    ObbyModule.HELPER.clearAllStates();
                }
            }
        });
    }
}
