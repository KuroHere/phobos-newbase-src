//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autotrap;

import net.minecraft.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autotrap.modes.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.autotrap.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;

public class AutoTrap extends ObbyListenerModule<ListenerAutoTrap>
{
    private static final EnumFacing[] TOP_FACINGS;
    protected final Setting<Float> range;
    protected final Setting<Boolean> noScaffold;
    protected final Setting<Boolean> top;
    protected final Setting<Boolean> noStep;
    protected final Setting<Boolean> upperBody;
    protected final Setting<Boolean> legs;
    protected final Setting<Boolean> platform;
    protected final Setting<Boolean> noDrop;
    protected final Setting<Integer> extend;
    protected final Setting<TrapTarget> targetMode;
    protected final Setting<Float> speed;
    protected final Setting<Boolean> freeCam;
    protected final Setting<Boolean> bigExtend;
    protected final Setting<Boolean> helping;
    protected final Setting<Boolean> smartTop;
    protected final Setting<Boolean> noScaffoldPlus;
    protected final Setting<Boolean> upperFace;
    protected final Map<EntityPlayer, Double> speeds;
    protected final Map<EntityPlayer, List<BlockPos>> cached;
    protected EntityPlayer target;
    
    public AutoTrap() {
        super("AutoTrap", Category.Combat);
        this.range = this.register(new NumberSetting("Range", 6.0f, 0.0f, 6.0f));
        this.noScaffold = this.register(new BooleanSetting("NoScaffold", false));
        this.top = this.register(new BooleanSetting("Top", true));
        this.noStep = this.register(new BooleanSetting("NoStep", false));
        this.upperBody = this.register(new BooleanSetting("UpperBody", true));
        this.legs = this.register(new BooleanSetting("Legs", false));
        this.platform = this.register(new BooleanSetting("Platform", false));
        this.noDrop = this.register(new BooleanSetting("NoDrop", false));
        this.extend = this.register(new NumberSetting("Extend", 2, 1, 3));
        this.targetMode = this.register(new EnumSetting("Target", TrapTarget.Closest));
        this.speed = this.register(new NumberSetting("Speed", 19.0f, 0.0f, 50.0f));
        this.freeCam = this.register(new BooleanSetting("FreeCam", false));
        this.bigExtend = this.register(new BooleanSetting("BigExtend", false));
        this.helping = this.register(new BooleanSetting("Helping", false));
        this.smartTop = this.register(new BooleanSetting("SmartTop", true));
        this.noScaffoldPlus = this.register(new BooleanSetting("NoScaffold+", false));
        this.upperFace = this.register(new BooleanSetting("Upper-FP", false));
        this.speeds = new HashMap<EntityPlayer, Double>();
        this.cached = new HashMap<EntityPlayer, List<BlockPos>>();
        this.setData(new AutoTrapData(this));
    }
    
    @Override
    protected void onDisable() {
        super.onDisable();
        Managers.ROTATION.setBlocking(false);
    }
    
    @Override
    protected boolean checkNull() {
        final boolean checkNull = super.checkNull();
        this.cached.clear();
        this.speeds.clear();
        if (checkNull) {
            this.updateSpeed();
        }
        return checkNull;
    }
    
    @Override
    protected ListenerAutoTrap createListener() {
        return new ListenerAutoTrap(this);
    }
    
    @Override
    public String getDisplayInfo() {
        return (this.target == null) ? null : this.target.getName();
    }
    
    @Override
    protected boolean shouldHelp(final EnumFacing facing, final BlockPos pos) {
        return super.shouldHelp(facing, pos) && this.helping.getValue() && !this.legs.getValue();
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    protected TargetResult getTargets(final TargetResult result) {
        this.cached.clear();
        this.updateSpeed();
        final EntityPlayer newTarget = this.calcTarget();
        if (newTarget == null || !newTarget.equals((Object)this.target)) {
            ((ListenerAutoTrap)this.listener).placed.clear();
        }
        this.target = ((newTarget == null) ? this.target : newTarget);
        if (newTarget == null) {
            return result.setValid(false);
        }
        List<BlockPos> newTrapping = this.cached.get(newTarget);
        if (newTrapping == null) {
            newTrapping = this.getPositions(newTarget);
        }
        return result.setTargets(newTrapping);
    }
    
    private EntityPlayer calcTarget() {
        EntityPlayer closest = null;
        double distance = Double.MAX_VALUE;
        for (final EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            final double playerDist = AutoTrap.mc.player.getDistanceSqToEntity((Entity)player);
            if (playerDist < distance && this.isValid(player)) {
                closest = player;
                distance = playerDist;
            }
        }
        return closest;
    }
    
    private boolean isValid(final EntityPlayer player) {
        if (player == null || EntityUtil.isDead((Entity)player) || player.equals((Object)AutoTrap.mc.player) || Managers.FRIENDS.contains(player) || player.getDistanceSqToEntity((Entity)AutoTrap.mc.player) > 36.0 || this.getSpeed(player) > this.speed.getValue()) {
            return false;
        }
        if (this.targetMode.getValue() == TrapTarget.Untrapped) {
            final List<BlockPos> positions = this.getPositions(player);
            this.cached.put(player, positions);
            return positions.stream().anyMatch(pos -> AutoTrap.mc.world.getBlockState(pos).getMaterial().isReplaceable());
        }
        return true;
    }
    
    private void updateSpeed() {
        for (final EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (EntityUtil.isValid((Entity)player, this.range.getValue() * 2.0f)) {
                final double xDist = player.posX - player.prevPosX;
                final double yDist = player.posY - player.prevPosY;
                final double zDist = player.posZ - player.prevPosZ;
                final double speed = xDist * xDist + yDist * yDist + zDist * zDist;
                this.speeds.put(player, speed);
            }
        }
    }
    
    private double getSpeed(final EntityPlayer player) {
        final Double playerSpeed = this.speeds.get(player);
        if (playerSpeed != null && this.speed.getValue() != 50.0f) {
            return Math.sqrt(playerSpeed) * 20.0 * 3.6;
        }
        return 0.0;
    }
    
    private List<BlockPos> getPositions(final EntityPlayer player) {
        final List<BlockPos> blocked = new ArrayList<BlockPos>();
        final BlockPos playerPos = new BlockPos((Entity)player);
        BlockPos pos = null;
        if (HoleUtil.isHole(playerPos, false)[0] || this.extend.getValue() == 1) {
            blocked.add(playerPos.up());
        }
        else {
            final List<BlockPos> unfiltered = new ArrayList<Object>(PositionUtil.getBlockedPositions((Entity)player)).stream().sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)BlockUtil::getDistanceSq)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
            final List<BlockPos> filtered = new ArrayList<Object>(unfiltered).stream().filter(pos -> AutoTrap.mc.world.getBlockState(pos).getMaterial().isReplaceable() && AutoTrap.mc.world.getBlockState(pos.up()).getMaterial().isReplaceable()).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
            if (this.extend.getValue() == 3 && filtered.size() == 2 && unfiltered.size() == 4 && unfiltered.get(0).equals((Object)filtered.get(0)) && unfiltered.get(3).equals((Object)filtered.get(1))) {
                filtered.clear();
            }
            if ((this.extend.getValue() == 2 && filtered.size() > 2) || (this.extend.getValue() == 3 && filtered.size() == 3)) {
                while (filtered.size() > 2) {
                    filtered.remove(filtered.size() - 1);
                }
            }
            final Iterator<BlockPos> iterator = filtered.iterator();
            while (iterator.hasNext()) {
                pos = iterator.next();
                blocked.add(pos.up());
            }
        }
        if (blocked.isEmpty()) {
            blocked.add(playerPos.up());
        }
        final List<BlockPos> positions = this.positionsFromBlocked(blocked);
        positions.sort(Comparator.comparingDouble(pos -> -BlockUtil.getDistanceSq(pos)));
        positions.sort(Comparator.comparingInt(Vec3i::getY));
        return positions.stream().filter(pos -> BlockUtil.getDistanceSq(pos) <= MathUtil.square(this.range.getValue())).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
    }
    
    private List<BlockPos> positionsFromBlocked(final List<BlockPos> blockedIn) {
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        BlockPos pos = null;
        if (!this.noStep.getValue() && !blockedIn.isEmpty()) {
            final BlockPos[] helping = this.findTopHelping(blockedIn, true);
            for (int i = 0; i < helping.length; ++i) {
                pos = helping[i];
                if (pos != null) {
                    if (i == 1 && !this.upperBody.getValue() && (!blockedIn.contains(PositionUtil.getPosition().up()) || !this.upperFace.getValue()) && helping[5] != null) {
                        positions.add(helping[5]);
                    }
                    positions.add(helping[i]);
                    break;
                }
            }
        }
        boolean scaffold = this.noScaffold.getValue();
        if (this.top.getValue()) {
            blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.TOP, positions)));
        }
        else if (blockedIn.size() == 1 && this.smartTop.getValue() && scaffold && AutoTrap.mc.world.getBlockState(blockedIn.get(0).add(Trap.TOP[0])).getMaterial().isReplaceable() && AutoTrap.mc.world.getBlockState(blockedIn.get(0).add(Trap.NO_SCAFFOLD[0])).getMaterial().isReplaceable() && AutoTrap.mc.world.getBlockState(blockedIn.get(0).add(Trap.NO_SCAFFOLD_P[0])).getMaterial().isReplaceable()) {
            blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.TOP, positions)));
            if (this.noScaffoldPlus.getValue()) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.NO_SCAFFOLD_P, positions)));
            }
            scaffold = false;
            blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.NO_SCAFFOLD, positions)));
        }
        if (this.upperBody.getValue() || (this.upperFace.getValue() && blockedIn.contains(PositionUtil.getPosition().up()))) {
            blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.OFFSETS, positions)));
        }
        if (blockedIn.size() == 1 || this.bigExtend.getValue()) {
            if (scaffold) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.NO_SCAFFOLD, positions)));
            }
            if (this.noStep.getValue()) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.NO_STEP, positions)));
            }
            if (this.legs.getValue()) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.LEGS, positions)));
            }
            if (this.platform.getValue()) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.PLATFORM, positions)));
            }
            if (this.noDrop.getValue()) {
                blockedIn.forEach(pos -> positions.addAll(this.applyOffsets(pos, Trap.NO_DROP, positions)));
            }
        }
        return positions;
    }
    
    private BlockPos[] findTopHelping(final List<BlockPos> positions, final boolean first) {
        final BlockPos[] bestPos = { null, null, null, null, positions.get(0).up().north(), null };
        for (final BlockPos pos : positions) {
            final BlockPos up = pos.up();
            for (final EnumFacing facing : AutoTrap.TOP_FACINGS) {
                final BlockPos helping = up.offset(facing);
                if (!AutoTrap.mc.world.getBlockState(helping).getMaterial().isReplaceable()) {
                    bestPos[0] = helping;
                    return bestPos;
                }
                final EnumFacing helpingFace = BlockUtil.getFacing(helping, (IBlockAccess)AutoTrap.HELPER);
                final byte blockingFactor = this.helpingEntityCheck(helping);
                if (helpingFace == null) {
                    switch (blockingFactor) {
                        case 0: {
                            if (first && bestPos[5] == null) {
                                final List<BlockPos> hPositions = new ArrayList<BlockPos>();
                                for (final BlockPos hPos : positions) {
                                    hPositions.add(hPos.down());
                                }
                                bestPos[5] = this.findTopHelping(hPositions, false)[0];
                                bestPos[1] = helping;
                                break;
                            }
                            break;
                        }
                        case 1: {
                            bestPos[3] = helping;
                            break;
                        }
                    }
                }
                else {
                    switch (blockingFactor) {
                        case 0: {
                            bestPos[0] = helping;
                            break;
                        }
                        case 1: {
                            bestPos[2] = helping;
                            break;
                        }
                    }
                }
            }
        }
        return bestPos;
    }
    
    private byte helpingEntityCheck(final BlockPos pos) {
        byte blocking = 0;
        for (final Entity entity : AutoTrap.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !EntityUtil.isDead(entity) && entity.preventEntitySpawning) {
                if (entity instanceof EntityPlayer && !BlockUtil.isBlocking(pos, (EntityPlayer)entity, this.blockingType.getValue())) {
                    continue;
                }
                if (entity instanceof EntityEnderCrystal && this.attack.getValue()) {
                    final float damage = DamageUtil.calculate(entity, (EntityLivingBase)AutoTrap.mc.player);
                    if (damage <= EntityUtil.getHealth((EntityLivingBase)AutoTrap.mc.player) + 1.0) {
                        blocking = 1;
                        continue;
                    }
                }
                return 2;
            }
        }
        return blocking;
    }
    
    private List<BlockPos> applyOffsets(final BlockPos pos, final Vec3i[] offsets, final List<BlockPos> alreadyAdded) {
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        for (final Vec3i vec3i : offsets) {
            final BlockPos offset = pos.add(vec3i);
            if (!alreadyAdded.contains(offset)) {
                positions.add(offset);
            }
        }
        return positions;
    }
    
    static {
        TOP_FACINGS = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.EAST };
    }
}
