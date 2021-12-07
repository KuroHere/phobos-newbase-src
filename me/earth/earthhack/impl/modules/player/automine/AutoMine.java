//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.automine.mode.*;
import net.minecraft.util.math.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.modules.player.automine.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.*;

public class AutoMine extends BlockAddingModule implements IAutomine
{
    private static final ModuleCache<Speedmine> SPEED_MINE;
    protected final Setting<AutoMineMode> mode;
    protected final Setting<Float> range;
    protected final Setting<Boolean> head;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> self;
    protected final Setting<Boolean> prioSelf;
    protected final Setting<Boolean> constellationCheck;
    protected final Setting<Boolean> improve;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> newV;
    protected final Setting<Boolean> newVEntities;
    protected final Setting<Boolean> checkCurrent;
    protected final Setting<Boolean> mineL;
    protected final Setting<Integer> offset;
    protected final Setting<Boolean> shouldBlackList;
    protected final Setting<Integer> blackListFor;
    protected final Setting<Boolean> checkTrace;
    protected final Setting<Float> placeRange;
    protected final Setting<Float> placeTrace;
    protected final Setting<Float> breakTrace;
    protected final Setting<Boolean> selfEchestMine;
    protected final Setting<Boolean> mineBurrow;
    protected final Setting<Boolean> checkPlayerState;
    protected final Setting<Boolean> resetIfNotValid;
    protected final Setting<Boolean> terrain;
    protected final Setting<Boolean> obbyPositions;
    protected final Setting<Boolean> mineObby;
    protected final Setting<Boolean> closestPlayer;
    protected final Setting<Boolean> improveCalcs;
    protected final Setting<Float> minDmg;
    protected final Setting<Float> maxSelfDmg;
    protected final Setting<Integer> terrainDelay;
    protected final Setting<Boolean> suicide;
    protected final Setting<Boolean> echest;
    protected final Setting<Float> echestRange;
    protected final Setting<Integer> maxTime;
    protected final Setting<Boolean> checkCrystalDownTime;
    protected final Setting<Integer> downTime;
    protected final Map<BlockPos, Long> blackList;
    protected final StopWatch constellationTimer;
    protected final StopWatch terrainTimer;
    protected final StopWatch downTimer;
    protected final StopWatch timer;
    protected IConstellation constellation;
    protected Future<?> future;
    protected boolean attacking;
    protected BlockPos current;
    protected BlockPos last;
    
    public AutoMine() {
        super("AutoMine", Category.Player, s -> "White/Blacklist the mining of " + s.getName() + " blocks.");
        this.mode = this.register(new EnumSetting("Mode", AutoMineMode.Combat));
        this.range = this.register(new NumberSetting("Range", 6.0f, 0.1f, 100.0f));
        this.head = this.register(new BooleanSetting("Head", false));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.self = this.register(new BooleanSetting("Self", true));
        this.prioSelf = this.register(new BooleanSetting("Prio-SelfUntrap", true));
        this.constellationCheck = this.register(new BooleanSetting("ConstCheck", true));
        this.improve = this.register(new BooleanSetting("Improve", false));
        this.delay = this.register(new NumberSetting("Delay", 100, 0, 10000));
        this.newV = this.register(new BooleanSetting("1.13+", false));
        this.newVEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.checkCurrent = this.register(new BooleanSetting("CheckCurrent", true));
        this.mineL = this.register(new BooleanSetting("Mine-L", false));
        this.offset = this.register(new NumberSetting("Reset-Offset", 0, 0, 1000));
        this.shouldBlackList = this.register(new BooleanSetting("BlackList", true));
        this.blackListFor = this.register(new NumberSetting("Blacklist-For", 120, 0, 3600));
        this.checkTrace = this.register(new BooleanSetting("Check-Range", false));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0f, 0.1f, 100.0f));
        this.placeTrace = this.register(new NumberSetting("PlaceTrace", 6.0f, 0.1f, 100.0f));
        this.breakTrace = this.register(new NumberSetting("BreakTrace", 3.5f, 0.1f, 100.0f));
        this.selfEchestMine = this.register(new BooleanSetting("Self-EchestBurrow-Mine", false));
        this.mineBurrow = this.register(new BooleanSetting("Mine-Burrow", false));
        this.checkPlayerState = this.register(new BooleanSetting("CheckPlayerState", true));
        this.resetIfNotValid = this.register(new BooleanSetting("Reset-Invalid", false));
        this.terrain = this.register(new BooleanSetting("Terrain", false));
        this.obbyPositions = this.register(new BooleanSetting("ObbyPositions", false));
        this.mineObby = this.register(new BooleanSetting("MineObby", false));
        this.closestPlayer = this.register(new BooleanSetting("ClosestPlayer", true));
        this.improveCalcs = this.register(new BooleanSetting("ImproveCalcs", false));
        this.minDmg = this.register(new NumberSetting("MinDamage", 6.0f, 0.1f, 100.0f));
        this.maxSelfDmg = this.register(new NumberSetting("MaxSelfDmg", 10.0f, 0.1f, 100.0f));
        this.terrainDelay = this.register(new NumberSetting("TerrainDelay", 500, 0, 10000));
        this.suicide = this.register(new BooleanSetting("Suicide", false));
        this.echest = this.register(new BooleanSetting("Echests", false));
        this.echestRange = this.register(new NumberSetting("Echest-Range", 3.0f, 0.1f, 100.0f));
        this.maxTime = this.register(new NumberSetting("MaxTime", 20000, 0, 60000));
        this.checkCrystalDownTime = this.register(new BooleanSetting("CheckCrystalDownTime", false));
        this.downTime = this.register(new NumberSetting("AutoCrystalDownTime", 500, 0, 5000));
        this.blackList = new HashMap<BlockPos, Long>();
        this.constellationTimer = new StopWatch();
        this.terrainTimer = new StopWatch();
        this.downTimer = new StopWatch();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerMultiBlockChange(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerPlace(this));
        this.listType.setValue(ListType.BlackList);
        final SimpleData data = new SimpleData(this, "Automatically mines Blocks.");
        data.register(this.mode, "-Combat will strategically mine enemies out. Uses Speedmine - Smart.\n-AntiTrap will mine you out of traps.");
        data.register(this.range, "Range in which blocks will be mined.");
        data.register(this.head, "Mines the Block above the Target.");
        data.register(this.rotate, "Rotates to mine the block.");
        data.register(this.self, "Touches Blocks in the own Surround so they can be mined quickly if an enemy jumps in.");
        data.register(this.prioSelf, "Prioritizes untrapping yourself.");
        data.register(this.constellationCheck, "Dev Setting, should be on.");
        data.register(this.delay, "Delay between touching blocks.");
        data.register(this.newV, "Takes 1.13+ crystal mechanics into account.");
        data.register(this.checkCurrent, "Dev Setting, should be on.");
        data.register(this.improve, "Will actively search for a better position.");
        data.register(this.mineL, "For Combat: Mines out L-Shape Holes");
        data.register(this.offset, "Time to wait after a block has been destroyed.");
        data.register(this.shouldBlackList, "Blacklists blocks that you reset by touching them again.");
        data.register(this.blackListFor, "Time in seconds a block should be blacklisted for. A value of 0 means it will never be blacklisted.");
        data.register(this.checkTrace, "Checks PlaceRange, PlaceTrace and BreakTrace for the crystal position.");
        data.register(this.placeRange, "PlaceRange of your CA.");
        data.register(this.placeTrace, "PlaceTrace of your CA.");
        data.register(this.breakTrace, "BreakTrace of your CA.");
        data.register(this.selfEchestMine, "Will mine an Echest you burrowed with.");
        data.register(this.resetIfNotValid, "Doesn't keep invalid positions mined.");
        data.register(this.mineBurrow, "Will mine players burrow blocks.");
        data.register(this.checkPlayerState, "Checks if a player burrowed in the meantime.");
        this.setData(data);
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    public void onDisable() {
        this.reset(true);
        this.blackList.clear();
    }
    
    public AutoMineMode getMode() {
        return this.mode.getValue();
    }
    
    public void addToBlackList(final BlockPos pos) {
        if (this.shouldBlackList.getValue()) {
            this.blackList.put(pos, System.currentTimeMillis());
        }
    }
    
    public void reset() {
        this.reset(false);
    }
    
    public void reset(final boolean hard) {
        if (!hard && this.constellation instanceof BigConstellation) {
            return;
        }
        if (!this.attacking) {
            if (this.future != null) {
                this.future.cancel(true);
                this.future = null;
            }
            this.constellation = null;
            this.current = null;
            if (this.offset.getValue() != 0) {
                this.timer.setTime(System.currentTimeMillis() + this.offset.getValue());
            }
        }
    }
    
    protected boolean checkCrystalPos(final BlockPos pos) {
        if (this.checkTrace.getValue()) {
            return BlockUtil.isCrystalPosInRange(pos, this.placeRange.getValue(), this.placeTrace.getValue(), this.breakTrace.getValue()) && BlockUtil.canPlaceCrystal(pos, true, this.newV.getValue(), AutoMine.mc.world.loadedEntityList, this.newVEntities.getValue(), 0L);
        }
        return BlockUtil.canPlaceCrystal(pos, true, this.newV.getValue(), AutoMine.mc.world.loadedEntityList, this.newVEntities.getValue(), 0L);
    }
    
    @Override
    public boolean isValid(final IBlockState state) {
        return super.isValid(state.getBlock().getLocalizedName());
    }
    
    @Override
    public void offer(final IConstellation constellation) {
        if ((this.constellation != null && this.constellation.cantBeImproved()) || AutoMine.mc.player == null || AutoMine.mc.world == null) {
            return;
        }
        if (this.future != null) {
            this.future.cancel(true);
            this.future = null;
        }
        this.constellation = constellation;
        this.constellationTimer.reset();
    }
    
    @Override
    public void attackPos(final BlockPos pos) {
        final EnumFacing facing = RayTraceUtil.getFacing((Entity)RotationUtil.getRotationPlayer(), pos, true);
        AutoMine.SPEED_MINE.get().getTimer().setTime(0L);
        this.current = pos;
        this.attacking = true;
        assert facing != null;
        AutoMine.mc.playerController.onPlayerDamageBlock(pos, facing);
        this.attacking = false;
        this.timer.reset();
    }
    
    @Override
    public void setCurrent(final BlockPos pos) {
        this.current = pos;
    }
    
    @Override
    public BlockPos getCurrent() {
        return this.current;
    }
    
    @Override
    public void setFuture(final Future<?> future) {
        this.future = future;
    }
    
    @Override
    public float getMinDmg() {
        return this.minDmg.getValue();
    }
    
    @Override
    public float getMaxSelfDmg() {
        return this.maxSelfDmg.getValue();
    }
    
    @Override
    public double getBreakTrace() {
        return this.breakTrace.getValue();
    }
    
    @Override
    public boolean getNewVEntities() {
        return this.newVEntities.getValue();
    }
    
    @Override
    public boolean shouldMineObby() {
        return this.mineObby.getValue();
    }
    
    @Override
    public boolean isSuicide() {
        return this.suicide.getValue();
    }
    
    @Override
    public boolean canBigCalcsBeImproved() {
        return this.improveCalcs.getValue();
    }
    
    static {
        SPEED_MINE = Caches.getModule(Speedmine.class);
    }
}
