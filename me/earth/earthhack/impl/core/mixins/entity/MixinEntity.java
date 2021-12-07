//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.movement.autosprint.*;
import me.earth.earthhack.impl.modules.movement.velocity.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.management.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.network.datasync.*;
import me.earth.earthhack.impl.util.math.*;
import java.util.function.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.movement.autosprint.mode.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements IEntity, Globals
{
    private static final ModuleCache<AutoSprint> SPRINT;
    private static final ModuleCache<Velocity> VELOCITY;
    private static final ModuleCache<NoInterp> NOINTERP;
    private static final SettingCache<Boolean, BooleanSetting, Velocity> NO_PUSH;
    private static final SettingCache<Integer, NumberSetting<Integer>, Management> DEATH_TIME;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public boolean onGround;
    @Shadow
    public World world;
    @Shadow
    public double prevPosX;
    @Shadow
    public double prevPosY;
    @Shadow
    public double prevPosZ;
    @Shadow
    public double lastTickPosX;
    @Shadow
    public double lastTickPosY;
    @Shadow
    public double lastTickPosZ;
    @Shadow
    protected EntityDataManager dataManager;
    @Shadow
    public float stepHeight;
    @Shadow
    public boolean isDead;
    @Shadow
    public float width;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public float height;
    private final StopWatch pseudoWatch;
    private MoveEvent moveEvent;
    private Float prevHeight;
    private Supplier<EntityType> type;
    private boolean pseudoDead;
    private long stamp;
    private boolean dummy;
    @Shadow
    public boolean noClip;
    
    public MixinEntity() {
        this.pseudoWatch = new StopWatch();
    }
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Shadow
    public abstract boolean isSneaking();
    
    @Shadow
    protected abstract boolean getFlag(final int p0);
    
    @Shadow
    public abstract boolean hasNoGravity();
    
    @Shadow
    @Override
    public abstract boolean equals(final Object p0);
    
    @Shadow
    protected abstract void setRotation(final float p0, final float p1);
    
    @Shadow
    public abstract boolean isRiding();
    
    @Accessor("isInWeb")
    @Override
    public abstract boolean inWeb();
    
    @Override
    public EntityType getType() {
        return this.type.get();
    }
    
    @Override
    public long getDeathTime() {
        return 0L;
    }
    
    @Override
    public boolean isPseudoDead() {
        if (this.pseudoDead && !this.isDead && this.pseudoWatch.passed(MixinEntity.DEATH_TIME.getValue())) {
            this.pseudoDead = false;
        }
        return this.pseudoDead;
    }
    
    @Override
    public void setPseudoDead(final boolean pseudoDead) {
        this.pseudoDead = pseudoDead;
        if (pseudoDead) {
            this.pseudoWatch.reset();
        }
    }
    
    @Override
    public StopWatch getPseudoTime() {
        return this.pseudoWatch;
    }
    
    @Override
    public long getTimeStamp() {
        return this.stamp;
    }
    
    @Override
    public boolean isDummy() {
        return this.dummy;
    }
    
    @Override
    public void setDummy(final boolean dummy) {
        this.dummy = dummy;
    }
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void ctrHook(final CallbackInfo info) {
        this.type = EntityType.getEntityType(Entity.class.cast(this));
        this.stamp = System.currentTimeMillis();
    }
    
    @Inject(method = { "createRunningParticles" }, at = { @At("HEAD") }, cancellable = true)
    private void createRunningParticlesHook(final CallbackInfo ci) {
        if (EntityPlayerSP.class.isInstance(this) && MixinEntity.SPRINT.isEnabled() && MixinEntity.SPRINT.get().getMode() == SprintMode.Rage) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") })
    public void moveEntityHook_Head(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (EntityPlayerSP.class.isInstance(this)) {
            this.moveEvent = new MoveEvent(type, x, y, z, this.isSneaking());
            Bus.EVENT_BUS.post(this.moveEvent);
        }
    }
    
    @ModifyVariable(method = { "move" }, at = @At("HEAD"), ordinal = 0)
    private double setX(final double x) {
        return (this.moveEvent != null) ? this.moveEvent.getX() : x;
    }
    
    @ModifyVariable(method = { "move" }, at = @At("HEAD"), ordinal = 1)
    private double setY(final double y) {
        return (this.moveEvent != null) ? this.moveEvent.getY() : y;
    }
    
    @ModifyVariable(method = { "move" }, at = @At("HEAD"), ordinal = 2)
    private double setZ(final double z) {
        return (this.moveEvent != null) ? this.moveEvent.getZ() : z;
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.isSneaking()Z", ordinal = 0))
    private boolean isSneakingHook(final Entity entity) {
        return (this.moveEvent != null) ? this.moveEvent.isSneaking() : entity.isSneaking();
    }
    
    @Inject(method = { "move" }, at = { @At(value = "FIELD", target = "net/minecraft/entity/Entity.onGround:Z", ordinal = 1) })
    private void onGroundHook(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (EntityPlayerSP.class.isInstance(this)) {
            final StepEvent event = new StepEvent(Stage.PRE, this.getEntityBoundingBox(), this.stepHeight);
            Bus.EVENT_BUS.post(event);
            this.prevHeight = this.stepHeight;
            this.stepHeight = event.getHeight();
        }
    }
    
    @Inject(method = { "move" }, at = { @At(value = "FIELD", target = "net/minecraft/entity/Entity.onGround:Z", ordinal = 2, shift = At.Shift.AFTER) })
    private void onGroundHook2(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (EntityPlayerSP.class.isInstance(this)) {
            final OnGroundEvent event = new OnGroundEvent();
            Bus.EVENT_BUS.post(event);
            this.onGround = (this.onGround || event.isCancelled());
        }
    }
    
    @Inject(method = { "move" }, at = { @At(value = "INVOKE", target = "net/minecraft/entity/Entity.setEntityBoundingBox(Lnet/minecraft/util/math/AxisAlignedBB;)V", ordinal = 7, shift = At.Shift.AFTER) })
    private void setEntityBoundingBoxHook(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (EntityPlayerSP.class.isInstance(this)) {
            final StepEvent event = new StepEvent(Stage.POST, this.getEntityBoundingBox(), (this.prevHeight != null) ? this.prevHeight : 0.0f);
            Bus.EVENT_BUS.postReversed(event, null);
        }
    }
    
    @Inject(method = { "setPositionAndRotation" }, at = { @At("RETURN") })
    private void setPositionAndRotationHook(final double x, final double y, final double z, final float yaw, final float pitch, final CallbackInfo ci) {
        if (this instanceof IEntityNoInterp) {
            ((IEntityNoInterp)this).setNoInterpX(x);
            ((IEntityNoInterp)this).setNoInterpY(y);
            ((IEntityNoInterp)this).setNoInterpZ(z);
        }
    }
    
    @Inject(method = { "move" }, at = { @At(value = "INVOKE", target = "net/minecraft/entity/Entity.resetPositionToBB()V", ordinal = 1) })
    private void resetPositionToBBHook(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (EntityPlayerSP.class.isInstance(this) && this.prevHeight != null) {
            this.stepHeight = this.prevHeight;
            this.prevHeight = null;
        }
    }
    
    @Inject(method = { "move" }, at = { @At("RETURN") })
    public void moveEntityHook_Return(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        this.moveEvent = null;
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("RETURN") }, cancellable = true)
    private void getCollisionBorderSizeHook(final CallbackInfoReturnable<Float> info) {
        final ReachEvent event = new ReachEvent(0.0f, info.getReturnValue());
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(event.getHitBox());
        }
    }
    
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocityHook(final Entity entity, final double x, final double y, final double z) {
        if (entity != null && (!MixinEntity.VELOCITY.isEnabled() || !MixinEntity.NO_PUSH.getValue() || !entity.equals((Object)MixinEntity.mc.player))) {
            entity.addVelocity(x, y, z);
        }
    }
    
    @Inject(method = { "setDead" }, at = { @At("RETURN") })
    private void setDeadHook(final CallbackInfo ci) {
        if (MixinEntity.NOINTERP.isPresent() && MixinEntity.NOINTERP.get().shouldFixDeathJitter()) {
            this.removeInterpolation();
            MixinEntity.mc.addScheduledTask(this::removeInterpolation);
        }
    }
    
    private void removeInterpolation() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.prevHeight = this.height;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (this instanceof IEntityNoInterp) {
            ((IEntityNoInterp)this).setNoInterpX(this.posX);
            ((IEntityNoInterp)this).setNoInterpY(this.posY);
            ((IEntityNoInterp)this).setNoInterpZ(this.posZ);
        }
    }
    
    static {
        SPRINT = Caches.getModule(AutoSprint.class);
        VELOCITY = Caches.getModule(Velocity.class);
        NOINTERP = Caches.getModule(NoInterp.class);
        NO_PUSH = Caches.getSetting(Velocity.class, BooleanSetting.class, "NoPush", false);
        DEATH_TIME = Caches.getSetting(Management.class, Setting.class, "DeathTime", 500);
    }
}
