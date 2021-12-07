//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.pistonaura.modes.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.combat.pistonaura.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.setting.event.*;

public class PistonAura extends BlockPlacingModule
{
    protected final Setting<Boolean> multiDirectional;
    protected final Setting<Boolean> explode;
    protected final Setting<Integer> breakDelay;
    protected final Setting<Float> breakRange;
    protected final Setting<Float> breakTrace;
    protected final Setting<Float> placeRange;
    protected final Setting<Float> placeTrace;
    protected final Setting<Integer> coolDown;
    protected final Setting<Boolean> suicide;
    protected final Setting<Boolean> newVer;
    protected final Setting<PistonTarget> targetMode;
    protected final Setting<Boolean> instant;
    protected final Setting<Integer> confirmation;
    protected final Setting<Integer> next;
    protected final Setting<Boolean> explosions;
    protected final Setting<Boolean> destroyEntities;
    protected final Setting<Boolean> multiChange;
    protected final Setting<Boolean> change;
    protected final Set<Block> clicked;
    protected final Queue<Runnable> actions;
    protected final StopWatch breakTimer;
    protected final StopWatch packetTimer;
    protected final StopWatch nextTimer;
    protected PistonStage stage;
    protected int pistonSlot;
    protected int redstoneSlot;
    protected int crystalSlot;
    protected EntityPlayer target;
    protected PistonData current;
    protected int index;
    protected int entityId;
    protected boolean reset;
    
    public PistonAura() {
        super("PistonAura", Category.Combat);
        this.multiDirectional = this.register(new BooleanSetting("MultiDirectional", false));
        this.explode = this.register(new BooleanSetting("Break", true));
        this.breakDelay = this.register(new NumberSetting("BreakDelay", 50, 0, 500));
        this.breakRange = this.register(new NumberSetting("BreakRange", 4.5f, 0.1f, 6.0f));
        this.breakTrace = this.register(new NumberSetting("BreakTrace", 3.0f, 0.1f, 6.0f));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 4.5f, 0.1f, 6.0f));
        this.placeTrace = this.register(new NumberSetting("PlaceTrace", 4.5f, 0.1f, 6.0f));
        this.coolDown = this.register(new NumberSetting("Cooldown", 500, 0, 500));
        this.suicide = this.register(new BooleanSetting("Suicide", false));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.targetMode = this.register(new EnumSetting("Target", PistonTarget.Calc));
        this.instant = this.register(new BooleanSetting("Instant", true));
        this.confirmation = this.register(new NumberSetting("Confirm", 250, 0, 1000));
        this.next = this.register(new NumberSetting("NextPhase", 1000, 0, 5000));
        this.explosions = this.register(new BooleanSetting("Explosions", true));
        this.destroyEntities = this.register(new BooleanSetting("DestroyEntities", false));
        this.multiChange = this.register(new BooleanSetting("MultiChange", false));
        this.change = this.register(new BooleanSetting("Change", false));
        this.clicked = new HashSet<Block>();
        this.actions = new LinkedList<Runnable>();
        this.breakTimer = new StopWatch();
        this.packetTimer = new StopWatch();
        this.nextTimer = new StopWatch();
        this.stage = PistonStage.PISTON;
        this.pistonSlot = -1;
        this.redstoneSlot = -1;
        this.crystalSlot = -1;
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerDestroyEntities(this));
        this.listeners.add(new ListenerMultiBlockChange(this));
        this.listeners.add(new ListenerBlockChange(this));
        super.packet.setValue(false);
        super.delay.setValue(0);
        this.setData(new PistonAuraData(this));
        this.rotate.setValue(Rotate.Normal);
        this.rotate.addObserver(event -> {
            if (event.getValue() != Rotate.Normal) {
                event.setCancelled(true);
            }
        });
    }
    
    @Override
    public String getDisplayInfo() {
        if (EntityUtil.isValid((Entity)this.target, 9.0)) {
            return this.target.getName();
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        this.pistonSlot = -1;
        this.redstoneSlot = -1;
        this.crystalSlot = -1;
        this.current = null;
        this.index = 0;
        this.reset = false;
        this.packetTimer.reset();
        this.nextTimer.reset();
        if (PistonAura.mc.player == null) {
            this.disable();
            return;
        }
        this.slot = PistonAura.mc.player.inventory.currentItem;
    }
    
    public void disableWithMessage(final String message) {
        this.disable();
        Managers.CHAT.sendDeleteMessage(message, this.getDisplayName(), 2000);
    }
    
    protected PistonData findTarget() {
        this.index = 0;
        this.stage = null;
        this.reset = false;
        this.packetTimer.reset();
        this.nextTimer.reset();
        final List<PistonData> data = new ArrayList<PistonData>();
        switch (this.targetMode.getValue()) {
            case FOV: {
                EntityPlayer closest = null;
                double closestAngle = 360.0;
                BlockPos pos = null;
                for (final EntityPlayer player : PistonAura.mc.world.playerEntities) {
                    if (!EntityUtil.isValid((Entity)player, 9.0)) {
                        continue;
                    }
                    final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                    if (!this.suicide.getValue() && PositionUtil.getPosition().equals((Object)playerPos)) {
                        continue;
                    }
                    if (!HoleUtil.isHole(playerPos, false)[0] && !HoleUtil.is2x1(playerPos)) {
                        continue;
                    }
                    final double angle = RotationUtil.getAngle((Entity)player, 1.4);
                    if (angle >= closestAngle) {
                        continue;
                    }
                    closest = player;
                    closestAngle = angle;
                    pos = playerPos;
                }
                if (closest != null) {
                    data.addAll(this.checkPlayer(closest, pos));
                    break;
                }
                break;
            }
            case Closest: {
                final EntityPlayer closestEnemy = EntityUtil.getClosestEnemy();
                if (closestEnemy == null) {
                    break;
                }
                final BlockPos playerPos2 = PositionUtil.getPosition((Entity)closestEnemy);
                if (!this.suicide.getValue() && PositionUtil.getPosition().equals((Object)playerPos2)) {
                    break;
                }
                if (HoleUtil.isHole(playerPos2, false)[0] || HoleUtil.is2x1(playerPos2)) {
                    data.addAll(this.checkPlayer(closestEnemy, playerPos2));
                }
                break;
            }
            case Calc: {
                for (final EntityPlayer player2 : PistonAura.mc.world.playerEntities) {
                    if (!EntityUtil.isValid((Entity)player2, 9.0)) {
                        continue;
                    }
                    final BlockPos playerPos3 = PositionUtil.getPosition((Entity)player2);
                    if (!this.suicide.getValue() && PositionUtil.getPosition().equals((Object)playerPos3)) {
                        continue;
                    }
                    if (!HoleUtil.isHole(playerPos3, false)[0] && !HoleUtil.is2x1(playerPos3)) {
                        continue;
                    }
                    data.addAll(this.checkPlayer(player2, playerPos3));
                }
                break;
            }
        }
        if (data.isEmpty()) {
            return null;
        }
        final List<PistonData> nonMulti = data.stream().filter(d -> !d.isMulti()).collect((Collector<? super Object, ?, List<PistonData>>)Collectors.toList());
        if (!nonMulti.isEmpty()) {
            nonMulti.sort(Comparator.comparingDouble(d -> PistonAura.mc.player.getDistanceSq(d.getCrystalPos())));
            return nonMulti.get(0);
        }
        data.sort(Comparator.comparingDouble(d -> PistonAura.mc.player.getDistanceSq(d.getCrystalPos())));
        return data.get(0);
    }
    
    private List<PistonData> checkPlayer(final EntityPlayer player, final BlockPos pos) {
        final List<PistonData> data = new ArrayList<PistonData>(this.checkFacings(player, pos));
        if (data.isEmpty() && PistonAura.mc.world.getBlockState(pos.up(2)).getMaterial().isReplaceable()) {
            data.addAll(this.checkFacings(player, pos.up()));
        }
        return data;
    }
    
    protected boolean checkUpdate(final BlockPos pos, final IBlockState state, final BlockPos dataPos, final Block block1, final Block block2) {
        if (pos.equals((Object)dataPos)) {
            final IBlockState before = PistonAura.mc.world.getBlockState(pos);
            return (before.getBlock() == block1 || before.getBlock() == block2) && state.getMaterial().isReplaceable();
        }
        return false;
    }
    
    private List<PistonData> checkFacings(final EntityPlayer player, final BlockPos pos) {
        final List<PistonData> data = new ArrayList<PistonData>();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos offset = pos.offset(facing);
            if (BlockUtil.canPlaceCrystal(offset, true, this.newVer.getValue())) {
                final PistonData d = this.evaluate(new PistonData(player, offset, facing));
                if (d.isValid()) {
                    data.add(d);
                }
            }
        }
        return data;
    }
    
    private PistonData evaluate(final PistonData data) {
        final BlockPos crystal = data.getCrystalPos();
        final double placeDist = PistonAura.mc.player.getDistanceSq(crystal);
        if (placeDist > MathUtil.square(this.placeRange.getValue()) || (placeDist > MathUtil.square(this.placeTrace.getValue()) && !RayTraceUtil.raytracePlaceCheck((Entity)PistonAura.mc.player, crystal))) {
            return data;
        }
        final double breakDist = PistonAura.mc.player.getDistanceSq(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5);
        if (breakDist > MathUtil.square(this.breakRange.getValue()) || (breakDist > MathUtil.square(this.breakTrace.getValue()) && !RayTraceUtil.canBeSeen(new Vec3d(crystal.getX() + 0.5, crystal.getY() + 2.7, crystal.getZ() + 0.5), (Entity)PistonAura.mc.player))) {
            return data;
        }
        final PistonStage[] order = new PistonStage[4];
        final BlockPos piston = data.getCrystalPos().offset(data.getFacing()).up();
        final BlockPos piston2 = piston.offset(data.getFacing());
        boolean using1 = false;
        final IBlockState toPush = PistonAura.mc.world.getBlockState(piston);
        final IBlockState piston1State = PistonAura.mc.world.getBlockState(piston2);
        EnumFacing placeFacing = BlockUtil.getFacing(piston);
        boolean noCrystal = false;
        for (final Entity entity : PistonAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(crystal, crystal.add(1, 2, 1)))) {
            if (entity != null) {
                if (EntityUtil.isDead(entity)) {
                    continue;
                }
                if (!(entity instanceof EntityEnderCrystal) || !crystal.equals((Object)entity.getPosition().down())) {
                    return data;
                }
                noCrystal = true;
                using1 = true;
            }
        }
        if (PistonAura.mc.player.getDistanceSq(piston) > MathUtil.square(this.placeRange.getValue())) {
            using1 = true;
            if (PistonAura.mc.player.getDistanceSq(piston2) > MathUtil.square(this.placeRange.getValue())) {
                return data;
            }
        }
        boolean noPiston = false;
        if (!toPush.getMaterial().isReplaceable()) {
            if (toPush.getBlock() == Blocks.PISTON || toPush.getBlock() == Blocks.STICKY_PISTON) {
                if (toPush.getProperties().get((Object)BlockDirectional.FACING) == data.getFacing().getOpposite()) {
                    noPiston = true;
                    using1 = false;
                }
                else {
                    using1 = true;
                }
            }
            else {
                if ((!PistonAura.mc.world.getBlockState(piston2).getMaterial().isReplaceable() && piston1State.getBlock() != Blocks.PISTON && piston1State.getBlock() != Blocks.STICKY_PISTON) || (toPush.getMobilityFlag() != EnumPushReaction.DESTROY && !BlockPistonBase.canPush(toPush, (World)PistonAura.mc.world, piston, data.getFacing().getOpposite(), false, data.getFacing().getOpposite()))) {
                    return data;
                }
                using1 = true;
            }
        }
        boolean cantPiston1 = false;
        if (piston1State.getBlock() == Blocks.PISTON || piston1State.getBlock() == Blocks.STICKY_PISTON) {
            if (piston1State.getProperties().get((Object)BlockDirectional.FACING) == data.getFacing().getOpposite() && !(boolean)piston1State.getProperties().get((Object)BlockPistonBase.EXTENDED)) {
                using1 = true;
                noPiston = true;
            }
            else {
                cantPiston1 = true;
            }
        }
        if (noPiston) {
            for (final EnumFacing facing : this.getRedstoneFacings(data.getFacing(), using1)) {
                final BlockPos redstone = using1 ? piston2.offset(facing) : piston.offset(facing);
                if (PistonAura.mc.player.getDistanceSq(redstone) <= MathUtil.square(this.placeRange.getValue()) && PistonAura.mc.world.getBlockState(redstone).getMaterial().isReplaceable() && !this.checkEntities(redstone)) {
                    data.setRedstonePos(redstone);
                    break;
                }
            }
            if (data.getRedstonePos() != null) {
                order[0] = (noCrystal ? null : PistonStage.CRYSTAL);
                order[1] = PistonStage.REDSTONE;
                order[2] = null;
                order[3] = PistonStage.BREAK;
                data.setOrder(order);
                data.setValid(true);
                return data;
            }
            if (using1) {
                return data;
            }
            using1 = true;
        }
        boolean noR = false;
        for (final EnumFacing facing2 : EnumFacing.values()) {
            if (facing2 != data.getFacing().getOpposite()) {
                final IBlockState state = PistonAura.mc.world.getBlockState(piston.offset(facing2));
                final IBlockState state2 = PistonAura.mc.world.getBlockState(piston2.offset(facing2));
                if (state.getBlock() == Blocks.REDSTONE_TORCH || state.getBlock() == Blocks.REDSTONE_BLOCK) {
                    using1 = true;
                }
                if (state2.getBlock() == Blocks.REDSTONE_BLOCK || state2.getBlock() == Blocks.REDSTONE_TORCH) {
                    noR = true;
                    using1 = true;
                    break;
                }
            }
        }
        for (final Entity entity2 : PistonAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(piston))) {
            if (entity2 != null && !EntityUtil.isDead(entity2)) {
                if (!entity2.preventEntitySpawning) {
                    continue;
                }
                using1 = true;
                break;
            }
        }
        final EnumFacing pistonFacing = this.getFacing(piston, null);
        if (!using1 && (pistonFacing == EnumFacing.UP || pistonFacing == EnumFacing.DOWN || (!this.multiDirectional.getValue() && pistonFacing != data.getFacing().getOpposite()))) {
            using1 = true;
        }
        if (using1) {
            final EnumFacing pistonFacing2 = this.getFacing(piston2, null);
            if (pistonFacing2 == EnumFacing.UP || pistonFacing2 == EnumFacing.DOWN || (!this.multiDirectional.getValue() && pistonFacing2 != data.getFacing().getOpposite())) {
                return data;
            }
            placeFacing = BlockUtil.getFacing(piston2);
            if (pistonFacing2 != data.getFacing().getOpposite()) {
                data.setMulti(true);
            }
        }
        if (using1 && (this.checkEntities(piston2) || PistonAura.mc.player.getDistanceSq(piston2) > MathUtil.square(this.placeRange.getValue()))) {
            return data;
        }
        EnumFacing redstoneFacing = null;
        if (!noR) {
            for (final EnumFacing facing3 : this.getRedstoneFacings(data.getFacing(), using1)) {
                final BlockPos redstone2 = using1 ? piston2.offset(facing3) : piston.offset(facing3);
                if (PistonAura.mc.player.getDistanceSq(redstone2) <= MathUtil.square(this.placeRange.getValue()) && PistonAura.mc.world.getBlockState(redstone2).getMaterial().isReplaceable()) {
                    if (!this.checkEntities(redstone2)) {
                        redstoneFacing = BlockUtil.getFacing(redstone2);
                        if (redstoneFacing != null || (placeFacing != null && using1)) {
                            data.setRedstonePos(redstone2);
                            break;
                        }
                    }
                }
            }
        }
        if ((!noR && data.getRedstonePos() == null) || (using1 && !PistonAura.mc.world.getBlockState(piston2).getMaterial().isReplaceable()) || (using1 && cantPiston1)) {
            return data;
        }
        if (!using1 && pistonFacing != data.getFacing().getOpposite()) {
            data.setMulti(true);
        }
        data.setPistonPos(using1 ? piston2 : piston);
        final boolean s = redstoneFacing != null && placeFacing == null && using1;
        if (noR) {
            order[0] = null;
            order[1] = (noCrystal ? null : PistonStage.CRYSTAL);
            order[2] = PistonStage.PISTON;
        }
        else {
            order[0] = (s ? PistonStage.REDSTONE : PistonStage.PISTON);
            order[1] = (noCrystal ? null : PistonStage.CRYSTAL);
            order[2] = (s ? PistonStage.PISTON : PistonStage.REDSTONE);
        }
        order[3] = PistonStage.BREAK;
        data.setOrder(order);
        data.setValid(true);
        return data;
    }
    
    protected EnumFacing getFacing(final BlockPos pos, float[] rotations) {
        if (Math.abs(PistonAura.mc.player.posX - (pos.getX() + 0.5f)) < 2.0 && Math.abs(PistonAura.mc.player.posZ - (pos.getZ() + 0.5f)) < 2.0) {
            final double y = PistonAura.mc.player.posY + PistonAura.mc.player.getEyeHeight();
            if (y - pos.getY() > 2.0) {
                return EnumFacing.UP;
            }
            if (pos.getY() - y > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        if (rotations == null) {
            final EnumFacing facing = BlockUtil.getFacing(pos);
            rotations = RotationUtil.getRotations((facing == null) ? pos : pos.offset(facing), (facing == null) ? null : facing.getOpposite());
        }
        return EnumFacing.getHorizontal(MathHelper.floor(rotations[0] * 4.0f / 360.0f + 0.5) & 0x3).getOpposite();
    }
    
    protected boolean checkEntities(final BlockPos pos) {
        for (final Entity entity : PistonAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !EntityUtil.isDead(entity)) {
                if (!entity.preventEntitySpawning) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
    
    private EnumFacing[] getRedstoneFacings(final EnumFacing facing, final boolean piston1) {
        if (piston1) {
            final EnumFacing[] result = new EnumFacing[5];
            int i = 0;
            for (final EnumFacing f : EnumFacing.values()) {
                if (f != facing.getOpposite()) {
                    result[i] = f;
                    ++i;
                }
            }
            return result;
        }
        return new EnumFacing[] { EnumFacing.DOWN, facing };
    }
    
    protected int getSlot() {
        switch (this.stage) {
            case CRYSTAL: {
                return this.crystalSlot;
            }
            case PISTON: {
                return this.pistonSlot;
            }
            case REDSTONE: {
                return this.redstoneSlot;
            }
        }
        return -1;
    }
    
    public boolean usingTorches() {
        if (this.redstoneSlot != -1) {
            ItemStack stack;
            if (this.redstoneSlot == -2) {
                stack = PistonAura.mc.player.getHeldItemOffhand();
            }
            else {
                stack = PistonAura.mc.player.inventory.getStackInSlot(this.redstoneSlot);
            }
            if (stack.getItem() instanceof ItemBlock) {
                return ((ItemBlock)stack.getItem()).getBlock() == Blocks.REDSTONE_TORCH;
            }
        }
        return false;
    }
}
