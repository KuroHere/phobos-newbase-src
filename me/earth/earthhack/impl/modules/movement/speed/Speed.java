//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.step.*;
import me.earth.earthhack.impl.modules.movement.longjump.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

public class Speed extends Module
{
    private static final ModuleCache<Step> STEP;
    protected final ModuleCache<LongJump> LONG_JUMP;
    protected final Setting<SpeedMode> mode;
    protected final Setting<Boolean> inWater;
    protected final Setting<Double> strafeSpeed;
    protected final Setting<Double> speedSet;
    protected final Setting<Integer> constTicks;
    protected final Setting<Integer> constOff;
    protected final Setting<Double> constFactor;
    protected final Setting<Boolean> useTimer;
    protected final Setting<Boolean> explosions;
    protected final Setting<Boolean> velocity;
    protected final Setting<Float> multiplier;
    protected final Setting<Float> vertical;
    protected final Setting<Integer> coolDown;
    protected final Setting<Boolean> directional;
    protected final Setting<Boolean> lagOut;
    protected final Setting<Integer> lagTime;
    protected final Setting<Double> cap;
    protected final Setting<Boolean> scaleCap;
    protected final Setting<Boolean> slow;
    protected final Setting<Boolean> noWaterInstant;
    protected final StopWatch expTimer;
    protected boolean stop;
    protected int vanillaStage;
    protected int onGroundStage;
    protected int oldGroundStage;
    protected double speed;
    protected double distance;
    protected int gayStage;
    protected int stage;
    protected int ncpStage;
    protected int bhopStage;
    protected int vStage;
    protected int lowStage;
    protected int constStage;
    protected double lastExp;
    protected double lastDist;
    protected boolean boost;
    
    public Speed() {
        super("Speed", Category.Movement);
        this.LONG_JUMP = Caches.getModule(LongJump.class);
        this.mode = this.register(new EnumSetting("Mode", SpeedMode.Instant));
        this.inWater = this.register(new BooleanSetting("InWater", false));
        this.strafeSpeed = this.register(new NumberSetting("StrafeSpeed", 0.2873, 0.1, 1.0));
        this.speedSet = this.register(new NumberSetting("Speed", 4.0, 0.1, 10.0));
        this.constTicks = this.register(new NumberSetting("ConstTicks", 10, 1, 40));
        this.constOff = this.register(new NumberSetting("ConstOff", 3, 1, 10));
        this.constFactor = this.register(new NumberSetting("ConstFactor", 2.149, 1.0, 5.0));
        this.useTimer = this.register(new BooleanSetting("UseTimer", false));
        this.explosions = this.register(new BooleanSetting("Explosions", false));
        this.velocity = this.register(new BooleanSetting("Velocity", false));
        this.multiplier = this.register(new NumberSetting("H-Factor", 1.0f, 0.0f, 5.0f));
        this.vertical = this.register(new NumberSetting("V-Factor", 1.0f, 0.0f, 5.0f));
        this.coolDown = this.register(new NumberSetting("CoolDown", 1000, 0, 5000));
        this.directional = this.register(new BooleanSetting("Directional", false));
        this.lagOut = this.register(new BooleanSetting("LagOutBlocks", false));
        this.lagTime = this.register(new NumberSetting("LagTime", 500, 0, 1000));
        this.cap = this.register(new NumberSetting("Cap", 10.0, 0.0, 10.0));
        this.scaleCap = this.register(new BooleanSetting("ScaleCap", false));
        this.slow = this.register(new BooleanSetting("Slowness", false));
        this.noWaterInstant = this.register(new BooleanSetting("NoLiquidInstant", false));
        this.expTimer = new StopWatch();
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerBlockPush(this));
        this.listeners.add(new ListenerVelocity(this));
        this.setData(new SpeedData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
    
    @Override
    protected void onEnable() {
        if (Speed.mc.player != null) {
            this.speed = MovementUtil.getSpeed();
            this.distance = MovementUtil.getDistance2D();
        }
        this.vanillaStage = 0;
        this.onGroundStage = 2;
        this.oldGroundStage = 4;
        this.ncpStage = 0;
        this.gayStage = 1;
        this.vStage = 1;
        this.bhopStage = 4;
        this.stage = 4;
        this.lowStage = 4;
        this.lastDist = 0.0;
        this.constStage = 0;
    }
    
    @Override
    protected void onDisable() {
        Managers.TIMER.reset();
    }
    
    protected boolean notColliding() {
        boolean stepping = false;
        final List<AxisAlignedBB> collisions = Speed.mc.world.getCollisionBoxes((Entity)Speed.mc.player, Speed.mc.player.getEntityBoundingBox().expand(0.1, 0.0, 0.1));
        if (Speed.STEP.isEnabled() && !collisions.isEmpty()) {
            stepping = true;
        }
        return Speed.mc.player.onGround && !stepping && !PositionUtil.inLiquid() && !PositionUtil.inLiquid(true);
    }
    
    public double getCap() {
        double ret = this.cap.getValue();
        if (!this.scaleCap.getValue()) {
            return ret;
        }
        if (Speed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            ret *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (this.slow.getValue() && Speed.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            final int amplifier = Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier();
            ret /= 1.0 + 0.2 * (amplifier + 1);
        }
        return ret;
    }
    
    public SpeedMode getMode() {
        return this.mode.getValue();
    }
    
    static {
        STEP = Caches.getModule(Step.class);
    }
}
