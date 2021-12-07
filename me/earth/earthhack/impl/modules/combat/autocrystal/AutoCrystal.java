//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.awt.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.util.math.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.helpers.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.instance.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import me.earth.earthhack.impl.util.math.*;
import org.lwjgl.input.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.*;

public class AutoCrystal extends Module
{
    private static final ScheduledExecutorService EXECUTOR;
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private static final AtomicBoolean ATOMIC_STARTED;
    private static boolean started;
    protected final Setting<ACPages> pages;
    protected final Setting<Boolean> place;
    protected final Setting<Target> targetMode;
    protected final Setting<Float> placeRange;
    protected final Setting<Float> placeTrace;
    protected final Setting<Float> minDamage;
    protected final Setting<Integer> placeDelay;
    protected final Setting<Float> maxSelfPlace;
    protected final Setting<Integer> multiPlace;
    protected final Setting<Float> slowPlaceDmg;
    protected final Setting<Integer> slowPlaceDelay;
    protected final Setting<Boolean> override;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> newVerEntities;
    protected final Setting<SwingTime> placeSwing;
    protected final Setting<Boolean> smartTrace;
    protected final Setting<Double> traceWidth;
    protected final Setting<Boolean> fallbackTrace;
    protected final Setting<Integer> simulatePlace;
    protected final Setting<Attack> attackMode;
    protected final Setting<Boolean> attack;
    protected final Setting<Float> breakRange;
    protected final Setting<Integer> breakDelay;
    protected final Setting<Float> breakTrace;
    protected final Setting<Float> minBreakDamage;
    protected final Setting<Float> maxSelfBreak;
    protected final Setting<Float> slowBreakDamage;
    protected final Setting<Integer> slowBreakDelay;
    protected final Setting<Boolean> instant;
    protected final Setting<Boolean> asyncCalc;
    protected final Setting<Boolean> alwaysCalc;
    protected final Setting<Integer> packets;
    protected final Setting<Boolean> overrideBreak;
    protected final Setting<AntiWeakness> antiWeakness;
    protected final Setting<Boolean> instantAntiWeak;
    protected final Setting<Boolean> efficient;
    protected final Setting<Boolean> manually;
    protected final Setting<Integer> manualDelay;
    protected final Setting<SwingTime> breakSwing;
    protected final Setting<ACRotate> rotate;
    protected final Setting<RotateMode> rotateMode;
    protected final Setting<Float> smoothSpeed;
    protected final Setting<Integer> endRotations;
    protected final Setting<Float> angle;
    protected final Setting<Float> placeAngle;
    protected final Setting<Float> height;
    protected final Setting<Double> placeHeight;
    protected final Setting<Integer> rotationTicks;
    protected final Setting<Boolean> focusRotations;
    protected final Setting<Boolean> focusAngleCalc;
    protected final Setting<Double> focusExponent;
    protected final Setting<Double> focusDiff;
    protected final Setting<Double> rotationExponent;
    protected final Setting<Double> minRotDiff;
    protected final Setting<Integer> existed;
    protected final Setting<Boolean> pingExisted;
    protected final Setting<Float> targetRange;
    protected final Setting<Float> pbTrace;
    protected final Setting<Float> range;
    protected final Setting<Boolean> suicide;
    protected final Setting<Boolean> multiTask;
    protected final Setting<Boolean> multiPlaceCalc;
    protected final Setting<Boolean> multiPlaceMinDmg;
    protected final Setting<Boolean> yCalc;
    protected final Setting<Boolean> dangerSpeed;
    protected final Setting<Float> dangerHealth;
    protected final Setting<Integer> cooldown;
    protected final Setting<Integer> placeCoolDown;
    protected final Setting<AntiFriendPop> antiFriendPop;
    protected final Setting<Boolean> antiFeetPlace;
    protected final Setting<Integer> feetBuffer;
    protected final Setting<Boolean> motionCalc;
    protected final Setting<Boolean> holdFacePlace;
    protected final Setting<Float> facePlace;
    protected final Setting<Float> minFaceDmg;
    protected final Setting<Float> armorPlace;
    protected final Setting<Boolean> pickAxeHold;
    protected final Setting<Boolean> antiNaked;
    protected final Setting<Boolean> fallBack;
    protected final Setting<Float> fallBackDiff;
    protected final Setting<Float> fallBackDmg;
    protected final Setting<AutoSwitch> autoSwitch;
    protected final Setting<Boolean> mainHand;
    protected final Setting<Bind> switchBind;
    protected final Setting<Boolean> switchBack;
    protected final Setting<Boolean> useAsOffhand;
    protected final Setting<Boolean> instantOffhand;
    protected final Setting<Boolean> pingBypass;
    protected final Setting<SwingType> swing;
    protected final Setting<SwingType> placeHand;
    protected final Setting<CooldownBypass> cooldownBypass;
    protected final Setting<CooldownBypass> obsidianBypass;
    protected final Setting<CooldownBypass> antiWeaknessBypass;
    protected final Setting<CooldownBypass> mineBypass;
    protected final Setting<SwingType> obbyHand;
    protected final Setting<Boolean> render;
    protected final Setting<Integer> renderTime;
    protected final Setting<Boolean> box;
    protected final Setting<Color> boxColor;
    protected final Setting<Color> outLine;
    protected final Setting<Color> indicatorColor;
    protected final Setting<Boolean> fade;
    protected final Setting<Integer> fadeTime;
    protected final Setting<Boolean> realtime;
    protected final Setting<RenderDamagePos> renderDamage;
    protected final Setting<RenderDamage> renderMode;
    protected final Setting<Boolean> setDead;
    protected final Setting<Boolean> instantSetDead;
    protected final Setting<Boolean> pseudoSetDead;
    protected final Setting<Boolean> simulateExplosion;
    protected final Setting<Boolean> soundRemove;
    protected final Setting<Integer> deathTime;
    protected final Setting<Boolean> obsidian;
    protected final Setting<Boolean> obbySwitch;
    protected final Setting<Integer> obbyDelay;
    protected final Setting<Integer> obbyCalc;
    protected final Setting<Integer> helpingBlocks;
    protected final Setting<Float> obbyMinDmg;
    protected final Setting<Boolean> terrainCalc;
    protected final Setting<Boolean> obbySafety;
    protected final Setting<RayTraceMode> obbyTrace;
    protected final Setting<Boolean> obbyTerrain;
    protected final Setting<Boolean> obbyPreSelf;
    protected final Setting<Integer> fastObby;
    protected final Setting<Integer> maxDiff;
    protected final Setting<Double> maxDmgDiff;
    protected final Setting<Boolean> setState;
    protected final Setting<PlaceSwing> obbySwing;
    protected final Setting<Boolean> obbyFallback;
    protected final Setting<Rotate> obbyRotate;
    protected final Setting<Boolean> interact;
    protected final Setting<Boolean> inside;
    protected final Setting<Boolean> lava;
    protected final Setting<Boolean> water;
    protected final Setting<Boolean> liquidObby;
    protected final Setting<Boolean> liquidRayTrace;
    protected final Setting<Integer> liqDelay;
    protected final Setting<Rotate> liqRotate;
    protected final Setting<Boolean> pickaxeOnly;
    protected final Setting<Boolean> interruptSpeedmine;
    protected final Setting<Boolean> setAir;
    protected final Setting<Boolean> absorb;
    protected final Setting<Boolean> requireOnGround;
    protected final Setting<Boolean> ignoreLavaItems;
    protected final Setting<Boolean> sponges;
    protected final Setting<Boolean> antiTotem;
    protected final Setting<Float> totemHealth;
    protected final Setting<Float> minTotemOffset;
    protected final Setting<Float> maxTotemOffset;
    protected final Setting<Float> popDamage;
    protected final Setting<Boolean> totemSync;
    protected final Setting<Boolean> forceAntiTotem;
    protected final Setting<Boolean> forceSlow;
    protected final Setting<Boolean> syncForce;
    protected final Setting<Boolean> dangerForce;
    protected final Setting<Integer> forcePlaceConfirm;
    protected final Setting<Integer> forceBreakConfirm;
    protected final Setting<Integer> attempts;
    protected final Setting<Boolean> damageSync;
    protected final Setting<Boolean> preSynCheck;
    protected final Setting<Boolean> discreteSync;
    protected final Setting<Boolean> dangerSync;
    protected final Setting<Integer> placeConfirm;
    protected final Setting<Integer> breakConfirm;
    protected final Setting<Integer> syncDelay;
    protected final Setting<Boolean> surroundSync;
    protected final Setting<Integer> bExtrapol;
    protected final Setting<Integer> placeExtrapolation;
    protected final Setting<Boolean> selfExtrapolation;
    protected final Setting<Boolean> fullExtrapol;
    protected final Setting<Boolean> idPredict;
    protected final Setting<Integer> idOffset;
    protected final Setting<Integer> idDelay;
    protected final Setting<Integer> idPackets;
    protected final Setting<Boolean> godAntiTotem;
    protected final Setting<Boolean> holdingCheck;
    protected final Setting<Boolean> toolCheck;
    protected final Setting<PlaceSwing> godSwing;
    protected final Setting<PreCalc> preCalc;
    protected final Setting<Float> preCalcDamage;
    protected final Setting<Boolean> multiThread;
    protected final Setting<Boolean> smartPost;
    protected final Setting<RotationThread> rotationThread;
    protected final Setting<Float> partial;
    protected final Setting<Integer> maxCancel;
    protected final Setting<Integer> timeOut;
    protected final Setting<Boolean> blockDestroyThread;
    protected final Setting<Integer> threadDelay;
    protected final Setting<Integer> tickThreshold;
    protected final Setting<Integer> preSpawn;
    protected final Setting<Integer> maxEarlyThread;
    protected final Setting<Boolean> explosionThread;
    protected final Setting<Boolean> soundThread;
    protected final Setting<Boolean> entityThread;
    protected final Setting<Boolean> spawnThread;
    protected final Setting<Boolean> destroyThread;
    protected final Setting<Boolean> serverThread;
    protected final Setting<Boolean> asyncServerThread;
    protected final Setting<Boolean> earlyFeetThread;
    protected final Setting<Boolean> lateBreakThread;
    protected final Setting<Boolean> motionThread;
    protected final Setting<Boolean> blockChangeThread;
    protected final Setting<Integer> priority;
    protected final Setting<Boolean> spectator;
    protected final Setting<Boolean> clearPost;
    protected final Setting<Integer> removeTime;
    protected final Map<BlockPos, CrystalTimeStamp> placed;
    protected final ListenerSound soundObserver;
    protected final AtomicInteger motionID;
    protected final DiscreteTimer placeTimer;
    protected final DiscreteTimer breakTimer;
    protected final StopWatch renderTimer;
    protected final StopWatch obbyTimer;
    protected final StopWatch obbyCalcTimer;
    protected final StopWatch targetTimer;
    protected final StopWatch cTargetTimer;
    protected final StopWatch forceTimer;
    protected final StopWatch liquidTimer;
    protected final Queue<Runnable> post;
    protected volatile RotationFunction rotation;
    protected EntityPlayer target;
    protected Entity crystal;
    protected Entity focus;
    protected BlockPos renderPos;
    protected boolean switching;
    protected boolean isSpoofing;
    protected boolean noGod;
    protected String damage;
    protected final PositionHelper positionHelper;
    protected final IDHelper idHelper;
    protected final HelperLiquids liquidHelper;
    protected final PositionHistoryHelper positionHistoryHelper;
    protected final HelperPlace placeHelper;
    protected final HelperBreak breakHelper;
    protected final HelperObby obbyHelper;
    protected final HelperBreakMotion breakHelperMotion;
    protected final AntiTotemHelper antiTotemHelper;
    protected final WeaknessHelper weaknessHelper;
    protected final RotationCanceller rotationCanceller;
    protected final ThreadHelper threadHelper;
    protected final DamageHelper damageHelper;
    protected final DamageSyncHelper damageSyncHelper;
    protected final ForceAntiTotemHelper forceHelper;
    protected final FakeCrystalRender crystalRender;
    protected final HelperRotation rotationHelper;
    protected final ServerTimeHelper serverTimeHelper;
    
    public AutoCrystal() {
        super("AutoCrystal", Category.Combat);
        this.pages = this.register(new EnumSetting("Page", ACPages.Place));
        this.place = this.register(new BooleanSetting("Place", true));
        this.targetMode = this.register(new EnumSetting("Target", Target.Closest));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0f, 0.0f, 6.0f));
        this.placeTrace = this.register(new NumberSetting("PlaceTrace", 6.0f, 0.0f, 6.0f));
        this.minDamage = this.register(new NumberSetting("MinDamage", 6.0f, 0.1f, 20.0f));
        this.placeDelay = this.register(new NumberSetting("PlaceDelay", 25, 0, 500));
        this.maxSelfPlace = this.register(new NumberSetting("MaxSelfPlace", 9.0f, 0.0f, 20.0f));
        this.multiPlace = this.register(new NumberSetting("MultiPlace", 1, 1, 5));
        this.slowPlaceDmg = this.register(new NumberSetting("SlowPlace", 4.0f, 0.1f, 20.0f));
        this.slowPlaceDelay = this.register(new NumberSetting("SlowPlaceDelay", 500, 0, 500));
        this.override = this.register(new BooleanSetting("OverridePlace", false));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.placeSwing = this.register(new EnumSetting("PlaceSwing", SwingTime.Post));
        this.smartTrace = this.register(new BooleanSetting("Smart-Trace", false));
        this.traceWidth = this.register(new NumberSetting("TraceWidth", -1.0, -1.0, 1.0));
        this.fallbackTrace = this.register(new BooleanSetting("Fallback-Trace", true));
        this.simulatePlace = this.register(new NumberSetting("Simulate-Place", 0, 0, 10));
        this.attackMode = this.register(new EnumSetting("Attack", Attack.Crystal));
        this.attack = this.register(new BooleanSetting("Break", true));
        this.breakRange = this.register(new NumberSetting("BreakRange", 6.0f, 0.0f, 6.0f));
        this.breakDelay = this.register(new NumberSetting("BreakDelay", 25, 0, 500));
        this.breakTrace = this.register(new NumberSetting("BreakTrace", 3.0f, 0.0f, 6.0f));
        this.minBreakDamage = this.register(new NumberSetting("MinBreakDmg", 2.0f, 0.0f, 20.0f));
        this.maxSelfBreak = this.register(new NumberSetting("MaxSelfBreak", 10.0f, 0.0f, 20.0f));
        this.slowBreakDamage = this.register(new NumberSetting("SlowBreak", 3.0f, 0.1f, 20.0f));
        this.slowBreakDelay = this.register(new NumberSetting("SlowBreakDelay", 500, 0, 500));
        this.instant = this.register(new BooleanSetting("Instant", false));
        this.asyncCalc = this.register(new BooleanSetting("Async-Calc", false));
        this.alwaysCalc = this.register(new BooleanSetting("Always-Calc", false));
        this.packets = this.register(new NumberSetting("Packets", 1, 1, 5));
        this.overrideBreak = this.register(new BooleanSetting("OverrideBreak", false));
        this.antiWeakness = this.register(new EnumSetting("AntiWeakness", AntiWeakness.None));
        this.instantAntiWeak = this.register(new BooleanSetting("AW-Instant", true));
        this.efficient = this.register(new BooleanSetting("Efficient", true));
        this.manually = this.register(new BooleanSetting("Manually", true));
        this.manualDelay = this.register(new NumberSetting("ManualDelay", 500, 0, 500));
        this.breakSwing = this.register(new EnumSetting("BreakSwing", SwingTime.Post));
        this.rotate = this.register(new EnumSetting("Rotate", ACRotate.None));
        this.rotateMode = this.register(new EnumSetting("Rotate-Mode", RotateMode.Normal));
        this.smoothSpeed = this.register(new NumberSetting("Smooth-Speed", 0.5f, 0.1f, 2.0f));
        this.endRotations = this.register(new NumberSetting("End-Rotations", 250, 0, 1000));
        this.angle = this.register(new NumberSetting("Break-Angle", 180.0f, 0.1f, 180.0f));
        this.placeAngle = this.register(new NumberSetting("Place-Angle", 180.0f, 0.1f, 180.0f));
        this.height = this.register(new NumberSetting("Height", 0.05f, 0.0f, 1.0f));
        this.placeHeight = this.register(new NumberSetting("Place-Height", 1.0, 0.0, 1.0));
        this.rotationTicks = this.register(new NumberSetting("Rotations-Existed", 0, 0, 500));
        this.focusRotations = this.register(new BooleanSetting("Focus-Rotations", false));
        this.focusAngleCalc = this.register(new BooleanSetting("FocusRotationCompare", false));
        this.focusExponent = this.register(new NumberSetting("FocusExponent", 0.0, 0.0, 10.0));
        this.focusDiff = this.register(new NumberSetting("FocusDiff", 0.0, 0.0, 180.0));
        this.rotationExponent = this.register(new NumberSetting("RotationExponent", 0.0, 0.0, 10.0));
        this.minRotDiff = this.register(new NumberSetting("MinRotationDiff", 0.0, 0.0, 180.0));
        this.existed = this.register(new NumberSetting("Existed", 0, 0, 500));
        this.pingExisted = this.register(new BooleanSetting("Ping-Existed", false));
        this.targetRange = this.register(new NumberSetting("TargetRange", 12.0f, 0.1f, 20.0f));
        this.pbTrace = this.register(new NumberSetting("CombinedTrace", 3.0f, 0.0f, 6.0f));
        this.range = this.register(new NumberSetting("Range", 12.0f, 0.1f, 20.0f));
        this.suicide = this.register(new BooleanSetting("Suicide", false));
        this.multiTask = this.register(new BooleanSetting("MultiTask", true));
        this.multiPlaceCalc = this.register(new BooleanSetting("MultiPlace-Calc", true));
        this.multiPlaceMinDmg = this.register(new BooleanSetting("MultiPlace-MinDmg", true));
        this.yCalc = this.register(new BooleanSetting("Y-Calc", false));
        this.dangerSpeed = this.register(new BooleanSetting("Danger-Speed", false));
        this.dangerHealth = this.register(new NumberSetting("Danger-Health", 0.0f, 0.0f, 36.0f));
        this.cooldown = this.register(new NumberSetting("CoolDown", 500, 0, 500));
        this.placeCoolDown = this.register(new NumberSetting("PlaceCooldown", 0, 0, 500));
        this.antiFriendPop = this.register(new EnumSetting("AntiFriendPop", AntiFriendPop.None));
        this.antiFeetPlace = this.register(new BooleanSetting("AntiFeetPlace", false));
        this.feetBuffer = this.register(new NumberSetting("FeetBuffer", 5, 0, 50));
        this.motionCalc = this.register(new BooleanSetting("Motion-Calc", false));
        this.holdFacePlace = this.register(new BooleanSetting("HoldFacePlace", false));
        this.facePlace = this.register(new NumberSetting("FacePlace", 10.0f, 0.0f, 36.0f));
        this.minFaceDmg = this.register(new NumberSetting("Min-FP", 2.0f, 0.0f, 5.0f));
        this.armorPlace = this.register(new NumberSetting("ArmorPlace", 5.0f, 0.0f, 100.0f));
        this.pickAxeHold = this.register(new BooleanSetting("PickAxe-Hold", false));
        this.antiNaked = this.register(new BooleanSetting("AntiNaked", false));
        this.fallBack = this.register(new BooleanSetting("FallBack", true));
        this.fallBackDiff = this.register(new NumberSetting("Fallback-Difference", 10.0f, 0.0f, 16.0f));
        this.fallBackDmg = this.register(new NumberSetting("FallBackDmg", 3.0f, 0.0f, 6.0f));
        this.autoSwitch = this.register(new EnumSetting("AutoSwitch", AutoSwitch.Bind));
        this.mainHand = this.register(new BooleanSetting("MainHand", false));
        this.switchBind = this.register(new BindSetting("SwitchBind", Bind.none()));
        this.switchBack = this.register(new BooleanSetting("SwitchBack", true));
        this.useAsOffhand = this.register(new BooleanSetting("UseAsOffHandBind", false));
        this.instantOffhand = this.register(new BooleanSetting("Instant-Offhand", true));
        this.pingBypass = this.register(new BooleanSetting("PingBypass", true));
        this.swing = this.register(new EnumSetting("BreakHand", SwingType.MainHand));
        this.placeHand = this.register(new EnumSetting("PlaceHand", SwingType.MainHand));
        this.cooldownBypass = this.register(new EnumSetting("CooldownBypass", CooldownBypass.None));
        this.obsidianBypass = this.register(new EnumSetting("ObsidianBypass", CooldownBypass.None));
        this.antiWeaknessBypass = this.register(new EnumSetting("AntiWeaknessBypass", CooldownBypass.None));
        this.mineBypass = this.register(new EnumSetting("MineBypass", CooldownBypass.None));
        this.obbyHand = this.register(new EnumSetting("ObbyHand", SwingType.MainHand));
        this.render = this.register(new BooleanSetting("Render", true));
        this.renderTime = this.register(new NumberSetting("Render-Time", 600, 0, 5000));
        this.box = this.register(new BooleanSetting("Draw-Box", true));
        this.boxColor = this.register(new ColorSetting("Box", new Color(255, 255, 255, 120)));
        this.outLine = this.register(new ColorSetting("Outline", new Color(255, 255, 255, 240)));
        this.indicatorColor = this.register(new ColorSetting("IndicatorColor", new Color(190, 5, 5, 255)));
        this.fade = this.register(new BooleanSetting("Fade", true));
        this.fadeTime = this.register(new NumberSetting("Fade-Time", 1000, 0, 5000));
        this.realtime = this.register(new BooleanSetting("Realtime", false));
        this.renderDamage = this.register(new EnumSetting("DamageRender", RenderDamagePos.None));
        this.renderMode = this.register(new EnumSetting("DamageMode", RenderDamage.Normal));
        this.setDead = this.register(new BooleanSetting("SetDead", false));
        this.instantSetDead = this.register(new BooleanSetting("Instant-Dead", false));
        this.pseudoSetDead = this.register(new BooleanSetting("Pseudo-Dead", false));
        this.simulateExplosion = this.register(new BooleanSetting("SimulateExplosion", false));
        this.soundRemove = this.register(new BooleanSetting("SoundRemove", true));
        this.deathTime = this.register(new NumberSetting("Death-Time", 0, 0, 500));
        this.obsidian = this.register(new BooleanSetting("Obsidian", false));
        this.obbySwitch = this.register(new BooleanSetting("Obby-Switch", false));
        this.obbyDelay = this.register(new NumberSetting("ObbyDelay", 500, 0, 5000));
        this.obbyCalc = this.register(new NumberSetting("ObbyCalc", 500, 0, 5000));
        this.helpingBlocks = this.register(new NumberSetting("HelpingBlocks", 1, 0, 5));
        this.obbyMinDmg = this.register(new NumberSetting("Obby-MinDamage", 7.0f, 0.1f, 36.0f));
        this.terrainCalc = this.register(new BooleanSetting("TerrainCalc", true));
        this.obbySafety = this.register(new BooleanSetting("ObbySafety", false));
        this.obbyTrace = this.register(new EnumSetting("Obby-Raytrace", RayTraceMode.Fast));
        this.obbyTerrain = this.register(new BooleanSetting("Obby-Terrain", true));
        this.obbyPreSelf = this.register(new BooleanSetting("Obby-PreSelf", true));
        this.fastObby = this.register(new NumberSetting("Fast-Obby", 0, 0, 3));
        this.maxDiff = this.register(new NumberSetting("Max-Difference", 1, 0, 5));
        this.maxDmgDiff = this.register(new NumberSetting("Max-DamageDiff", 0.0, 0.0, 10.0));
        this.setState = this.register(new BooleanSetting("Client-Blocks", false));
        this.obbySwing = this.register(new EnumSetting("Obby-Swing", PlaceSwing.Once));
        this.obbyFallback = this.register(new BooleanSetting("Obby-Fallback", false));
        this.obbyRotate = this.register(new EnumSetting("Obby-Rotate", Rotate.None));
        this.interact = this.register(new BooleanSetting("Interact", false));
        this.inside = this.register(new BooleanSetting("Inside", false));
        this.lava = this.register(new BooleanSetting("Lava", false));
        this.water = this.register(new BooleanSetting("Water", false));
        this.liquidObby = this.register(new BooleanSetting("LiquidObby", false));
        this.liquidRayTrace = this.register(new BooleanSetting("LiquidRayTrace", false));
        this.liqDelay = this.register(new NumberSetting("LiquidDelay", 500, 0, 1000));
        this.liqRotate = this.register(new EnumSetting("LiquidRotate", Rotate.None));
        this.pickaxeOnly = this.register(new BooleanSetting("PickaxeOnly", false));
        this.interruptSpeedmine = this.register(new BooleanSetting("InterruptSpeedmine", false));
        this.setAir = this.register(new BooleanSetting("SetAir", true));
        this.absorb = this.register(new BooleanSetting("Absorb", false));
        this.requireOnGround = this.register(new BooleanSetting("RequireOnGround", true));
        this.ignoreLavaItems = this.register(new BooleanSetting("IgnoreLavaItems", false));
        this.sponges = this.register(new BooleanSetting("Sponges", false));
        this.antiTotem = this.register(new BooleanSetting("AntiTotem", false));
        this.totemHealth = this.register(new NumberSetting("Totem-Health", 1.5f, 0.0f, 10.0f));
        this.minTotemOffset = this.register(new NumberSetting("Min-Offset", 0.5f, 0.0f, 5.0f));
        this.maxTotemOffset = this.register(new NumberSetting("Max-Offset", 2.0f, 0.0f, 5.0f));
        this.popDamage = this.register(new NumberSetting("Pop-Damage", 12.0f, 10.0f, 20.0f));
        this.totemSync = this.register(new BooleanSetting("TotemSync", true));
        this.forceAntiTotem = this.register(new BooleanSetting("Force-AntiTotem", false));
        this.forceSlow = this.register(new BooleanSetting("Force-Slow", false));
        this.syncForce = this.register(new BooleanSetting("Sync-Force", true));
        this.dangerForce = this.register(new BooleanSetting("Danger-Force", false));
        this.forcePlaceConfirm = this.register(new NumberSetting("Force-Place", 100, 0, 500));
        this.forceBreakConfirm = this.register(new NumberSetting("Force-Break", 100, 0, 500));
        this.attempts = this.register(new NumberSetting("Attempts", 500, 0, 10000));
        this.damageSync = this.register(new BooleanSetting("DamageSync", false));
        this.preSynCheck = this.register(new BooleanSetting("Pre-SyncCheck", false));
        this.discreteSync = this.register(new BooleanSetting("Discrete-Sync", false));
        this.dangerSync = this.register(new BooleanSetting("Danger-Sync", false));
        this.placeConfirm = this.register(new NumberSetting("Place-Confirm", 250, 0, 500));
        this.breakConfirm = this.register(new NumberSetting("Break-Confirm", 250, 0, 500));
        this.syncDelay = this.register(new NumberSetting("SyncDelay", 500, 0, 500));
        this.surroundSync = this.register(new BooleanSetting("SurroundSync", true));
        this.bExtrapol = this.register(new NumberSetting("BreakExtrapolation", 0, 0, 50));
        this.placeExtrapolation = this.register(new NumberSetting("PlaceExtrapolation", 0, 0, 50));
        this.selfExtrapolation = this.register(new BooleanSetting("SelfExtrapolation", false));
        this.fullExtrapol = this.register(new BooleanSetting("Full-Extrapolation", false));
        this.idPredict = this.register(new BooleanSetting("ID-Predict", false));
        this.idOffset = this.register(new NumberSetting("ID-Offset", 1, 1, 10));
        this.idDelay = this.register(new NumberSetting("ID-Delay", 0, 0, 500));
        this.idPackets = this.register(new NumberSetting("ID-Packets", 1, 1, 10));
        this.godAntiTotem = this.register(new BooleanSetting("God-AntiTotem", false));
        this.holdingCheck = this.register(new BooleanSetting("Holding-Check", true));
        this.toolCheck = this.register(new BooleanSetting("Tool-Check", true));
        this.godSwing = this.register(new EnumSetting("God-Swing", PlaceSwing.Once));
        this.preCalc = this.register(new EnumSetting("Pre-Calc", PreCalc.None));
        this.preCalcDamage = this.register(new NumberSetting("Pre-CalcDamage", 15.0f, 0.0f, 36.0f));
        this.multiThread = this.register(new BooleanSetting("MultiThread", false));
        this.smartPost = this.register(new BooleanSetting("Smart-Post", true));
        this.rotationThread = this.register(new EnumSetting("RotationThread", RotationThread.Predict));
        this.partial = this.register(new NumberSetting("Partial", 0.8f, 0.0f, 1.0f));
        this.maxCancel = this.register(new NumberSetting("MaxCancel", 10, 1, 50));
        this.timeOut = this.register(new NumberSetting("Wait", 2, 1, 10));
        this.blockDestroyThread = this.register(new BooleanSetting("BlockDestroyThread", false));
        this.threadDelay = this.register(new NumberSetting("ThreadDelay", 25, 0, 100));
        this.tickThreshold = this.register(new NumberSetting("TickThreshold", 5, 1, 20));
        this.preSpawn = this.register(new NumberSetting("PreSpawn", 3, 1, 20));
        this.maxEarlyThread = this.register(new NumberSetting("MaxEarlyThread", 8, 1, 20));
        this.explosionThread = this.register(new BooleanSetting("ExplosionThread", false));
        this.soundThread = this.register(new BooleanSetting("SoundThread", false));
        this.entityThread = this.register(new BooleanSetting("EntityThread", false));
        this.spawnThread = this.register(new BooleanSetting("SpawnThread", false));
        this.destroyThread = this.register(new BooleanSetting("DestroyThread", false));
        this.serverThread = this.register(new BooleanSetting("ServerThread", false));
        this.asyncServerThread = this.register(new BooleanSetting("AsyncServerThread", false));
        this.earlyFeetThread = this.register(new BooleanSetting("EarlyFeetThread", false));
        this.lateBreakThread = this.register(new BooleanSetting("LateBreakThread", false));
        this.motionThread = this.register(new BooleanSetting("MotionThread", true));
        this.blockChangeThread = this.register(new BooleanSetting("BlockChangeThread", false));
        this.priority = this.register(new NumberSetting("Priority", 1500, Integer.MIN_VALUE, Integer.MAX_VALUE));
        this.spectator = this.register(new BooleanSetting("Spectator", false));
        this.clearPost = this.register(new BooleanSetting("ClearPost", true));
        this.removeTime = this.register(new NumberSetting("Remove-Time", 1000, 0, 2500));
        this.placed = new ConcurrentHashMap<BlockPos, CrystalTimeStamp>();
        this.soundObserver = new ListenerSound(this);
        this.motionID = new AtomicInteger();
        this.placeTimer = new GuardTimer(1000L, 5L).reset(this.placeDelay.getValue());
        this.breakTimer = new GuardTimer(1000L, 5L).reset(this.breakDelay.getValue());
        this.renderTimer = new StopWatch();
        this.obbyTimer = new StopWatch();
        this.obbyCalcTimer = new StopWatch();
        this.targetTimer = new StopWatch();
        this.cTargetTimer = new StopWatch();
        this.forceTimer = new StopWatch();
        this.liquidTimer = new StopWatch();
        this.post = new ConcurrentLinkedQueue<Runnable>();
        this.positionHelper = new PositionHelper(this);
        this.idHelper = new IDHelper();
        this.liquidHelper = new HelperLiquids();
        this.positionHistoryHelper = new PositionHistoryHelper();
        this.placeHelper = new HelperPlace(this);
        this.breakHelper = new HelperBreak(this);
        this.obbyHelper = new HelperObby(this);
        this.breakHelperMotion = new HelperBreakMotion(this);
        this.antiTotemHelper = new AntiTotemHelper(this.totemHealth);
        this.weaknessHelper = new WeaknessHelper(this.antiWeakness, this.cooldown);
        this.rotationCanceller = new RotationCanceller(this, this.maxCancel);
        this.threadHelper = new ThreadHelper(this, this.multiThread, this.threadDelay, this.rotationThread, this.rotate);
        this.damageHelper = new DamageHelper(this.positionHelper, this.terrainCalc, this.placeExtrapolation, this.bExtrapol, this.selfExtrapolation, this.obbyTerrain);
        this.damageSyncHelper = new DamageSyncHelper(Bus.EVENT_BUS, this.discreteSync, this.syncDelay, this.dangerSync);
        this.forceHelper = new ForceAntiTotemHelper(Bus.EVENT_BUS, this.discreteSync, this.syncDelay, this.forcePlaceConfirm, this.forceBreakConfirm, this.dangerForce);
        this.crystalRender = new FakeCrystalRender(this.simulatePlace);
        this.rotationHelper = new HelperRotation(this);
        this.serverTimeHelper = new ServerTimeHelper(this, this.rotate, this.placeSwing, this.antiFeetPlace, this.newVer, this.feetBuffer);
        Bus.EVENT_BUS.subscribe(this.positionHistoryHelper);
        Bus.EVENT_BUS.subscribe(this.idHelper);
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerBlockMulti(this));
        this.listeners.add(new ListenerDestroyEntities(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerKeyboard(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerNoMotion(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerPostPlace(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerRenderEntities(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerDestroyBlock(this));
        this.listeners.add(new ListenerUseEntity(this));
        this.listeners.addAll(new ListenerCPlayers(this).getListeners());
        this.listeners.addAll(new ListenerEntity(this).getListeners());
        new PageBuilder<ACPages>(this, this.pages).addPage(p -> p == ACPages.Place, this.place, this.simulatePlace).addPage(p -> p == ACPages.Break, this.attackMode, this.breakSwing).addPage(p -> p == ACPages.Rotate, this.rotate, this.pingExisted).addPage(p -> p == ACPages.Misc, this.targetRange, this.motionCalc).addPage(p -> p == ACPages.FacePlace, this.holdFacePlace, this.fallBackDmg).addPage(p -> p == ACPages.Switch, this.autoSwitch, this.obbyHand).addPage(p -> p == ACPages.Render, this.render, this.renderMode).addPage(p -> p == ACPages.SetDead, this.setDead, this.deathTime).addPage(p -> p == ACPages.Obsidian, this.obsidian, this.obbyRotate).addPage(p -> p == ACPages.Liquids, this.interact, this.sponges).addPage(p -> p == ACPages.AntiTotem, this.antiTotem, this.attempts).addPage(p -> p == ACPages.DamageSync, this.damageSync, this.surroundSync).addPage(p -> p == ACPages.Extrapolation, this.bExtrapol, this.fullExtrapol).addPage(p -> p == ACPages.GodModule, this.idPredict, this.godSwing).addPage(p -> p == ACPages.MultiThread, this.preCalc, this.blockChangeThread).addPage(p -> p == ACPages.Development, this.priority, this.removeTime).register(Visibilities.VISIBILITY_MANAGER);
        this.priority.addObserver(e -> {
            if (Bus.EVENT_BUS.isSubscribed(this)) {
                Bus.EVENT_BUS.unsubscribe(this);
                Bus.EVENT_BUS.subscribe(this);
            }
            return;
        });
        this.setData(new AutoCrystalData(this));
    }
    
    @Override
    protected void onEnable() {
        this.reset();
        Managers.SET_DEAD.addObserver(this.soundObserver);
    }
    
    @Override
    protected void onDisable() {
        Managers.SET_DEAD.removeObserver(this.soundObserver);
        this.reset();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.switching) {
            return "§aSwitching";
        }
        final EntityPlayer t = this.getTarget();
        return (t == null) ? null : t.getName();
    }
    
    public void setRenderPos(final BlockPos pos, final float damage) {
        this.setRenderPos(pos, MathUtil.round(damage, 1) + "");
    }
    
    public void setRenderPos(final BlockPos pos, final String text) {
        this.renderTimer.reset();
        this.renderPos = pos;
        this.damage = text;
    }
    
    public BlockPos getRenderPos() {
        if (this.renderTimer.passed(this.renderTime.getValue())) {
            this.renderPos = null;
        }
        return this.renderPos;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.targetTimer.reset();
        this.target = target;
    }
    
    public EntityPlayer getTarget() {
        if (this.targetTimer.passed(600L)) {
            this.target = null;
        }
        return this.target;
    }
    
    public void setCrystal(final Entity crystal) {
        if (this.focusRotations.getValue() && !this.rotate.getValue().noRotate(ACRotate.Break)) {
            this.focus = crystal;
        }
        this.cTargetTimer.reset();
        this.crystal = crystal;
    }
    
    public Entity getCrystal() {
        if (this.cTargetTimer.passed(600L)) {
            this.crystal = null;
        }
        return this.crystal;
    }
    
    public boolean isPingBypass() {
        return this.pingBypass.getValue() && AutoCrystal.PINGBYPASS.isEnabled();
    }
    
    public float getMinDamage() {
        return (this.holdFacePlace.getValue() && AutoCrystal.mc.currentScreen == null && Mouse.isButtonDown(0) && (!(AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) || this.pickAxeHold.getValue())) ? this.minFaceDmg.getValue() : ((float)this.minDamage.getValue());
    }
    
    public void runPost() {
        CollectionUtil.emptyQueue(this.post);
    }
    
    protected void reset() {
        this.target = null;
        this.crystal = null;
        this.renderPos = null;
        this.rotation = null;
        this.switching = false;
        this.post.clear();
        AutoCrystal.mc.addScheduledTask(this.crystalRender::clear);
        try {
            this.placed.clear();
            this.threadHelper.reset();
            this.rotationCanceller.reset();
            this.antiTotemHelper.setTarget(null);
            this.antiTotemHelper.setTargetPos(null);
            this.idHelper.setUpdated(false);
            this.idHelper.setHighestID(0);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    protected boolean shouldDanger() {
        return this.dangerSpeed.getValue() && (!Managers.SAFETY.isSafe() || EntityUtil.getHealth((EntityLivingBase)AutoCrystal.mc.player) < this.dangerHealth.getValue());
    }
    
    protected void checkExecutor() {
        if (!AutoCrystal.started && this.asyncServerThread.getValue() && this.serverThread.getValue() && this.multiThread.getValue() && this.rotate.getValue() == ACRotate.None) {
            synchronized (AutoCrystal.class) {
                if (!AutoCrystal.ATOMIC_STARTED.get()) {
                    this.startExecutor();
                    AutoCrystal.ATOMIC_STARTED.set(true);
                    AutoCrystal.started = true;
                }
            }
        }
    }
    
    private void startExecutor() {
        AutoCrystal.EXECUTOR.scheduleAtFixedRate(this::doExecutorTick, 0L, 1L, TimeUnit.MILLISECONDS);
    }
    
    private void doExecutorTick() {
        if (this.isEnabled() && AutoCrystal.mc.player != null && AutoCrystal.mc.world != null && this.asyncServerThread.getValue() && this.rotate.getValue() == ACRotate.None && this.serverThread.getValue() && this.multiThread.getValue()) {
            if (Managers.TICK.valid(Managers.TICK.getTickTimeAdjusted(), Managers.TICK.normalize(Managers.TICK.getSpawnTime() - this.tickThreshold.getValue()), Managers.TICK.normalize(Managers.TICK.getSpawnTime() - this.preSpawn.getValue()))) {
                if (!this.earlyFeetThread.getValue()) {
                    this.threadHelper.startThread(new BlockPos[0]);
                }
                else if (this.lateBreakThread.getValue()) {
                    this.threadHelper.startThread(true, false, new BlockPos[0]);
                }
            }
            else {
                final EntityPlayer closest = EntityUtil.getClosestEnemy();
                if (closest != null && BlockUtil.isSemiSafe(closest, true, this.newVer.getValue()) && BlockUtil.canBeFeetPlaced(closest, true, this.newVer.getValue()) && this.earlyFeetThread.getValue() && Managers.TICK.valid(Managers.TICK.getTickTimeAdjusted(), 0, this.maxEarlyThread.getValue())) {
                    this.threadHelper.startThread(false, true, new BlockPos[0]);
                }
            }
        }
    }
    
    static {
        EXECUTOR = ThreadUtil.newDaemonScheduledExecutor("AutoCrystal");
        PINGBYPASS = Caches.getModule(PingBypass.class);
        ATOMIC_STARTED = new AtomicBoolean();
    }
}
