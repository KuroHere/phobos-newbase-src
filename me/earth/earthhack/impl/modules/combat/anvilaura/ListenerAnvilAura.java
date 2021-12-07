//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.anvilaura;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.modes.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.path.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerAnvilAura extends ObbyListener<AnvilAura>
{
    private static final ModuleCache<AntiSurround> ANTISURROUND;
    private static final Vec3i[] CRYSTAL_OFFSETS;
    private AnvilMode mode;
    private String disableString;
    
    public ListenerAnvilAura(final AnvilAura module, final int priority) {
        super(module, priority);
        this.mode = AnvilMode.Mine;
    }
    
    @Override
    public void invoke(final MotionUpdateEvent event) {
        if (ListenerAnvilAura.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false)) {
            return;
        }
        super.invoke(event);
    }
    
    @Override
    protected void pre(final MotionUpdateEvent event) {
        this.mode = ((AnvilAura)this.module).mode.getValue();
        ((AnvilAura)this.module).action = null;
        ((AnvilAura)this.module).renderBBs = Collections.emptyList();
        ((AnvilAura)this.module).target = null;
        super.pre(event);
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        if (this.mode == AnvilMode.Mine && !((AnvilAura)this.module).confirmMine.getValue() && !((AnvilAura)this.module).awaitTimer.passed(((AnvilAura)this.module).confirm.getValue()) && ((AnvilAura)this.module).awaiting.get()) {
            final BlockPos awaitPos = ((AnvilAura)this.module).awaitPos;
            if (awaitPos != null) {
                ObbyModule.HELPER.addBlockState(((AnvilAura)this.module).awaitPos, Blocks.AIR.getDefaultState());
            }
        }
        final Set<AnvilResult> results = AnvilResult.create(ListenerAnvilAura.mc.world.playerEntities, ListenerAnvilAura.mc.world.loadedEntityList, ((AnvilAura)this.module).yHeight.getValue(), ((AnvilAura)this.module).range.getValue());
        switch (this.mode) {
            case Render: {
                final List<AxisAlignedBB> renderBBs = new ArrayList<AxisAlignedBB>(((AnvilAura)this.module).renderBest.getValue() ? 1 : results.size());
                for (final AnvilResult r : results) {
                    if (r.getMine().stream().anyMatch(p -> p.getY() > r.getPlayerPos().getY())) {
                        continue;
                    }
                    BlockPos first = null;
                    BlockPos last = null;
                    for (final BlockPos pos : r.getPositions()) {
                        if (BlockUtil.getDistanceSq(pos) >= MathUtil.square(((AnvilAura)this.module).range.getValue())) {
                            continue;
                        }
                        if (first == null) {
                            first = pos;
                        }
                        last = pos;
                    }
                    if (first == null) {
                        continue;
                    }
                    final AxisAlignedBB bb = new AxisAlignedBB((double)first.getX(), (double)first.getY(), (double)first.getZ(), (double)(last.getX() + 1), (double)(last.getY() + 1), (double)(last.getZ() + 1));
                    renderBBs.add(bb);
                    if (((AnvilAura)this.module).renderBest.getValue()) {
                        break;
                    }
                }
                ((AnvilAura)this.module).renderBBs = renderBBs;
                break;
            }
            case Pressure: {
                for (final AnvilResult r : results) {
                    if (r.hasValidPressure() || r.hasSpecialPressure()) {
                        if (((AnvilAura)this.module).checkFalling.getValue() && r.hasFallingEntities()) {
                            if (!r.hasSpecialPressure()) {
                                continue;
                            }
                            if (!((AnvilAura)this.module).pressureFalling.getValue()) {
                                continue;
                            }
                        }
                        boolean badMine = false;
                        for (final BlockPos pos2 : r.getMine()) {
                            if (pos2.getY() > r.getPlayerPos().getY()) {
                                badMine = true;
                                break;
                            }
                        }
                        if (badMine) {
                            continue;
                        }
                        if (this.doTrap(r)) {
                            break;
                        }
                        final BlockPos pressure = r.getPressurePos();
                        if (r.hasSpecialPressure() || SpecialBlocks.PRESSURE_PLATES.contains(ObbyModule.HELPER.getBlockState(pressure).getBlock())) {
                            if (this.placeTop(r, result)) {
                                ((AnvilAura)this.module).setCurrentResult(r);
                                break;
                            }
                            continue;
                        }
                        else {
                            if (r.hasValidPressure() && ((AnvilAura)this.module).pressureSlot != -1) {
                                ((AnvilAura)this.module).stage = AnvilStage.PRESSURE;
                                result.getTargets().add(pressure);
                                break;
                            }
                            continue;
                        }
                    }
                }
                break;
            }
            case Mine: {
                if (((AnvilAura)this.module).stage == AnvilStage.MINE) {
                    if (((AnvilAura)this.module).minePos != null && ((AnvilAura)this.module).mineFacing != null) {
                        ((AnvilAura)this.module).rotations = RotationUtil.getRotations(((AnvilAura)this.module).minePos, ((AnvilAura)this.module).mineFacing);
                        if (((AnvilAura)this.module).mineTimer.passed(((AnvilAura)this.module).mineTime.getValue())) {
                            ((AnvilAura)this.module).awaitPos = ((AnvilAura)this.module).minePos;
                            ((AnvilAura)this.module).awaiting.set(true);
                            ((AnvilAura)this.module).action = (() -> {
                                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                                    final int last2 = ListenerAnvilAura.mc.player.inventory.currentItem;
                                    InventoryUtil.switchTo(((AnvilAura)this.module).pickSlot);
                                    PacketUtil.stopDigging(((AnvilAura)this.module).minePos, ((AnvilAura)this.module).mineFacing);
                                    ListenerAnvilAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                                    InventoryUtil.switchTo(last2);
                                    return;
                                });
                                ((AnvilAura)this.module).awaitTimer.reset();
                                Managers.BLOCKS.addCallback(((AnvilAura)this.module).minePos, s -> {
                                    ((AnvilAura)this.module).awaiting.set(false);
                                    ((AnvilAura)this.module).awaitPos = null;
                                    return;
                                });
                                ((AnvilAura)this.module).minePos = null;
                                ((AnvilAura)this.module).mineFacing = null;
                                ((AnvilAura)this.module).action = null;
                                return;
                            });
                            break;
                        }
                        break;
                    }
                    else if (((AnvilAura)this.module).confirmMine.getValue() && !((AnvilAura)this.module).awaitTimer.passed(((AnvilAura)this.module).confirm.getValue()) && ((AnvilAura)this.module).awaiting.get()) {
                        break;
                    }
                }
                for (final AnvilResult r : results) {
                    if (((AnvilAura)this.module).checkFalling.getValue() && (!r.hasSpecialPressure() || !((AnvilAura)this.module).pressureFalling.getValue()) && r.hasFallingEntities()) {
                        continue;
                    }
                    if (this.doMine(r, result)) {
                        ((AnvilAura)this.module).setCurrentResult(r);
                        break;
                    }
                }
                break;
            }
            case High: {
                for (final AnvilResult r : results) {
                    if (((AnvilAura)this.module).checkFalling.getValue() && (!r.hasSpecialPressure() || !((AnvilAura)this.module).pressureFalling.getValue()) && r.hasFallingEntities()) {
                        continue;
                    }
                    boolean badMine = false;
                    for (final BlockPos pos2 : r.getMine()) {
                        if (pos2.getY() > r.getPlayerPos().getY()) {
                            badMine = true;
                            break;
                        }
                    }
                    if (!badMine && (this.doTrap(r) || this.placeTop(r, result))) {
                        ((AnvilAura)this.module).setCurrentResult(r);
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }
    
    @Override
    protected int getSlot() {
        ((AnvilAura)this.module).obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
        ((AnvilAura)this.module).crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
        ((AnvilAura)this.module).crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
        switch (this.mode) {
            case Pressure: {
                ((AnvilAura)this.module).pressureSlot = InventoryUtil.findHotbarBlock(Blocks.WOODEN_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
                if (((AnvilAura)this.module).pressureSlot == -1 && !((AnvilAura)this.module).pressurePass.getValue()) {
                    this.disableString = "Disabled, No Pressure Plates found!";
                    return -1;
                }
                break;
            }
            case Mine: {
                ((AnvilAura)this.module).pickSlot = InventoryUtil.findInHotbar(s -> s.getItem() instanceof ItemPickaxe);
                if (((AnvilAura)this.module).pickSlot == -1) {
                    this.disableString = "Disabled, No Pickaxe found!";
                    return -1;
                }
                break;
            }
        }
        return InventoryUtil.findHotbarBlock(Blocks.ANVIL, new Block[0]);
    }
    
    @Override
    protected void disableModule() {
        if (((AnvilAura)this.module).holdingAnvil.getValue()) {
            ((AnvilAura)this.module).packets.clear();
            ((AnvilAura)this.module).rotations = null;
            ((AnvilAura)this.module).action = null;
            return;
        }
        super.disableModule();
    }
    
    @Override
    protected boolean update() {
        this.disableString = null;
        return (!((AnvilAura)this.module).holdingAnvil.getValue() || InventoryUtil.isHolding(Blocks.ANVIL)) && super.update();
    }
    
    @Override
    protected String getDisableString() {
        if (this.disableString != null) {
            return this.disableString;
        }
        return "Disabled, no Anvils.";
    }
    
    private boolean doTrap(final AnvilResult anvilResult) {
        if (!((AnvilAura)this.module).trap.getValue() || ((AnvilAura)this.module).obbySlot == -1) {
            return false;
        }
        final BlockPos highest = this.findHighest(anvilResult);
        if (highest == null) {
            return false;
        }
        boolean didPlace = false;
        final BlockPos[] ignore = this.toIgnore(anvilResult, highest);
        for (final BlockPos pos : anvilResult.getTrap()) {
            if (!this.placed.containsKey(pos)) {
                if (!ObbyModule.HELPER.getBlockState(pos).getMaterial().isReplaceable()) {
                    continue;
                }
                final BasePath path = new BasePath((Entity)RotationUtil.getRotationPlayer(), pos, ((AnvilAura)this.module).trapHelping.getValue());
                PathFinder.findPath(path, ((AnvilAura)this.module).range.getValue(), ListenerAnvilAura.mc.world.loadedEntityList, ((AnvilAura)this.module).smartRay.getValue(), ObbyModule.HELPER, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, ignore);
                final int before = ((AnvilAura)this.module).slot;
                ((AnvilAura)this.module).slot = ((AnvilAura)this.module).obbySlot;
                didPlace = (ObbyUtil.place((ObbyModule)this.module, path) || didPlace);
                ((AnvilAura)this.module).slot = before;
            }
        }
        if (didPlace) {
            ((AnvilAura)this.module).stage = AnvilStage.OBSIDIAN;
            return true;
        }
        if (((AnvilAura)this.module).crystal.getValue() && ((AnvilAura)this.module).crystalSlot != -1 && ((AnvilAura)this.module).crystalTimer.passed(((AnvilAura)this.module).crystalDelay.getValue())) {
            final BlockPos upUp = anvilResult.getPlayerPos().up(2);
            for (final EntityEnderCrystal entity : ListenerAnvilAura.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(upUp))) {
                if (entity != null && !EntityUtil.isDead((Entity)entity)) {
                    return false;
                }
            }
            for (final Vec3i vec3i : ListenerAnvilAura.CRYSTAL_OFFSETS) {
                final BlockPos pos2 = anvilResult.getPlayerPos().add(vec3i);
                if (BlockUtil.canPlaceCrystal(pos2, false, false)) {
                    final Entity entity2 = (Entity)RotationUtil.getRotationPlayer();
                    final int before2 = ((AnvilAura)this.module).slot;
                    ((AnvilAura)this.module).slot = ((AnvilAura)this.module).crystalSlot;
                    ((AnvilAura)this.module).rotations = RotationUtil.getRotations(pos2.getX() + 0.5, pos2.getY() + 1.0, pos2.getZ() + 0.5, entity2.posX, entity2.posY, entity2.posZ, ListenerAnvilAura.mc.player.getEyeHeight());
                    RayTraceResult result = RayTraceUtil.getRayTraceResult(((AnvilAura)this.module).rotations[0], ((AnvilAura)this.module).rotations[1], 6.0f, entity2);
                    if (!result.getBlockPos().equals((Object)pos2)) {
                        result = new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, BlockPos.ORIGIN);
                    }
                    ((AnvilAura)this.module).placeBlock(pos2, result.sideHit, ((AnvilAura)this.module).rotations, result.hitVec);
                    ((AnvilAura)this.module).slot = before2;
                    ((AnvilAura)this.module).stage = AnvilStage.CRYSTAL;
                    ((AnvilAura)this.module).crystalTimer.reset();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean doMine(final AnvilResult anvilResult, final TargetResult result) {
        if (this.doTrap(anvilResult)) {
            return true;
        }
        BlockPos highest = null;
        for (final BlockPos pos : anvilResult.getMine()) {
            if (BlockUtil.getDistanceSq(pos) >= MathUtil.square(((AnvilAura)this.module).mineRange.getValue())) {
                continue;
            }
            if (highest != null && highest.getY() >= pos.getY()) {
                continue;
            }
            highest = pos;
        }
        if (highest != null) {
            ((AnvilAura)this.module).mineFacing = RayTraceUtil.getFacing((Entity)ListenerAnvilAura.mc.player, highest, true);
            ((AnvilAura)this.module).rotations = RotationUtil.getRotations(highest, ((AnvilAura)this.module).mineFacing);
            ((AnvilAura)this.module).minePos = highest;
            final IBlockState mineState = ObbyModule.HELPER.getBlockState(highest);
            ((AnvilAura)this.module).mineBB = mineState.getSelectedBoundingBox((World)ListenerAnvilAura.mc.world, highest).expandXyz(0.0020000000949949026);
            ((AnvilAura)this.module).mineTimer.reset();
            ((AnvilAura)this.module).stage = AnvilStage.MINE;
            ((AnvilAura)this.module).action = (() -> {
                PacketUtil.startDigging(((AnvilAura)this.module).minePos, ((AnvilAura)this.module).mineFacing);
                ListenerAnvilAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                ((AnvilAura)this.module).mineTimer.reset();
                ((AnvilAura)this.module).action = null;
                return;
            });
            return true;
        }
        return this.placeTop(anvilResult, result);
    }
    
    private boolean placeTop(final AnvilResult anvilResult, final TargetResult result) {
        final BlockPos highest = this.findHighest(anvilResult);
        if (highest == null) {
            return false;
        }
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos pos = highest.offset(facing);
            if (!ObbyModule.HELPER.getBlockState(pos).getMaterial().isReplaceable()) {
                result.getTargets().add(highest);
                ((AnvilAura)this.module).stage = AnvilStage.ANVIL;
                return true;
            }
        }
        if (((AnvilAura)this.module).obbySlot == -1) {
            return false;
        }
        final BlockPos[] ignore = this.toIgnore(anvilResult, highest);
        for (final EnumFacing facing2 : EnumFacing.HORIZONTALS) {
            final BlockPos pos2 = highest.offset(facing2);
            final BasePath path = new BasePath((Entity)RotationUtil.getRotationPlayer(), pos2, ((AnvilAura)this.module).helpingBlocks.getValue());
            PathFinder.findPath(path, ((AnvilAura)this.module).range.getValue(), ListenerAnvilAura.mc.world.loadedEntityList, ((AnvilAura)this.module).smartRay.getValue(), ObbyModule.HELPER, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, ignore);
            final int before = ((AnvilAura)this.module).slot;
            ((AnvilAura)this.module).slot = ((AnvilAura)this.module).obbySlot;
            if (ObbyUtil.place((ObbyModule)this.module, path)) {
                ((AnvilAura)this.module).slot = before;
                ((AnvilAura)this.module).stage = AnvilStage.OBSIDIAN;
                return true;
            }
            ((AnvilAura)this.module).slot = before;
        }
        return false;
    }
    
    private BlockPos findHighest(final AnvilResult anvilResult) {
        BlockPos highest = null;
        for (final BlockPos pos : anvilResult.getPositions()) {
            if (BlockUtil.getDistanceSq(pos) >= MathUtil.square(((AnvilAura)this.module).range.getValue())) {
                continue;
            }
            if (highest != null && highest.getY() >= pos.getY()) {
                continue;
            }
            highest = pos;
        }
        return highest;
    }
    
    private BlockPos[] toIgnore(final AnvilResult anvilResult, final BlockPos highest) {
        final BlockPos base = anvilResult.getPressurePos();
        final int length = highest.getY() - base.getY();
        final BlockPos[] ignore = new BlockPos[length];
        for (int i = 0; i < length; ++i) {
            ignore[i] = base.up(i + 1);
        }
        return ignore;
    }
    
    static {
        ANTISURROUND = Caches.getModule(AntiSurround.class);
        CRYSTAL_OFFSETS = new Vec3i[] { new Vec3i(1, 0, -1), new Vec3i(1, 0, 1), new Vec3i(-1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 1, -1), new Vec3i(1, 1, 1), new Vec3i(-1, 1, -1), new Vec3i(-1, 1, 1) };
    }
}
