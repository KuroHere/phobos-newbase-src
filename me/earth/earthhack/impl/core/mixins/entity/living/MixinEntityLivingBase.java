//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living;

import me.earth.earthhack.impl.core.mixins.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.*;
import me.earth.earthhack.impl.modules.player.fasteat.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import net.minecraft.network.datasync.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.player.fasteat.mode.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends MixinEntity implements IEntityLivingBase, IEntityNoInterp, ICachedDamage, IEntityRemoteAttack
{
    private static final ModuleCache<ElytraFlight> ELYTRA_FLIGHT;
    private static final ModuleCache<FastEat> FAST_EAT;
    private static final ModuleCache<NoInterp> NOINTERP;
    private static final ModuleCache<Spectate> SPECTATE;
    @Shadow
    @Final
    private static DataParameter<Float> HEALTH;
    @Shadow
    public float moveStrafing;
    @Shadow
    public float field_191988_bg;
    @Shadow
    protected int activeItemStackUseCount;
    @Shadow
    protected ItemStack activeItemStack;
    protected double noInterpX;
    protected double noInterpY;
    protected double noInterpZ;
    protected int noInterpPositionIncrements;
    protected float noInterpPrevSwing;
    protected float noInterpSwingAmount;
    protected float noInterpSwing;
    protected float lowestDura;
    protected boolean noInterping;
    protected int armorValue;
    protected float armorToughness;
    protected int explosionModifier;
    
    public MixinEntityLivingBase() {
        this.lowestDura = Float.MAX_VALUE;
        this.noInterping = true;
        this.armorValue = Integer.MAX_VALUE;
        this.armorToughness = Float.MAX_VALUE;
        this.explosionModifier = Integer.MAX_VALUE;
    }
    
    @Shadow
    public abstract IAttributeInstance getEntityAttribute(final IAttribute p0);
    
    @Shadow
    public abstract int getTotalArmorValue();
    
    @Shadow
    public abstract Iterable<ItemStack> getArmorInventoryList();
    
    @Shadow
    public abstract boolean isServerWorld();
    
    @Invoker("getArmSwingAnimationEnd")
    @Override
    public abstract int armSwingAnimationEnd();
    
    @Accessor("ticksSinceLastSwing")
    @Override
    public abstract int getTicksSinceLastSwing();
    
    @Accessor("activeItemStackUseCount")
    @Override
    public abstract int getActiveItemStackUseCount();
    
    @Accessor("ticksSinceLastSwing")
    @Override
    public abstract void setTicksSinceLastSwing(final int p0);
    
    @Accessor("activeItemStackUseCount")
    @Override
    public abstract void setActiveItemStackUseCount(final int p0);
    
    @Override
    public boolean getElytraFlag() {
        return this.getFlag(7);
    }
    
    @Override
    public double getNoInterpX() {
        return this.isNoInterping() ? this.noInterpX : this.posX;
    }
    
    @Override
    public double getNoInterpY() {
        return this.isNoInterping() ? this.noInterpY : this.posY;
    }
    
    @Override
    public double getNoInterpZ() {
        return this.isNoInterping() ? this.noInterpZ : this.posZ;
    }
    
    @Override
    public void setNoInterpX(final double x) {
        this.noInterpX = x;
    }
    
    @Override
    public void setNoInterpY(final double y) {
        this.noInterpY = y;
    }
    
    @Override
    public void setNoInterpZ(final double z) {
        this.noInterpZ = z;
    }
    
    @Override
    public int getPosIncrements() {
        return this.noInterpPositionIncrements;
    }
    
    @Override
    public void setPosIncrements(final int posIncrements) {
        this.noInterpPositionIncrements = posIncrements;
    }
    
    @Override
    public float getNoInterpSwingAmount() {
        return this.noInterpSwingAmount;
    }
    
    @Override
    public float getNoInterpSwing() {
        return this.noInterpSwing;
    }
    
    @Override
    public float getNoInterpPrevSwing() {
        return this.noInterpPrevSwing;
    }
    
    @Override
    public void setNoInterpSwingAmount(final float noInterpSwingAmount) {
        this.noInterpSwingAmount = noInterpSwingAmount;
    }
    
    @Override
    public void setNoInterpSwing(final float noInterpSwing) {
        this.noInterpSwing = noInterpSwing;
    }
    
    @Override
    public void setNoInterpPrevSwing(final float noInterpPrevSwing) {
        this.noInterpPrevSwing = noInterpPrevSwing;
    }
    
    @Override
    public boolean isNoInterping() {
        final EntityPlayerSP player = MixinEntityLivingBase.mc.player;
        return !this.isRiding() && this.noInterping && (player == null || !player.isRiding());
    }
    
    @Override
    public void setNoInterping(final boolean noInterping) {
        this.noInterping = noInterping;
    }
    
    @Override
    public void setLowestDura(final float lowest) {
        this.lowestDura = lowest;
    }
    
    @Override
    public float getLowestDurability() {
        return this.lowestDura;
    }
    
    @Override
    public int getArmorValue() {
        return this.shouldCache() ? this.armorValue : this.getTotalArmorValue();
    }
    
    @Override
    public float getArmorToughness() {
        return this.shouldCache() ? this.armorToughness : ((float)this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
    }
    
    @Override
    public int getExplosionModifier(final DamageSource source) {
        return this.shouldCache() ? this.explosionModifier : EnchantmentUtil.getEnchantmentModifierDamage(this.getArmorInventoryList(), source);
    }
    
    @Redirect(method = { "attackEntityFrom" }, at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"))
    private boolean isRemoteHook(final World world) {
        return world.isRemote && !this.shouldRemoteAttack();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;posX:D"))
    private double posXHookOnUpdate(final EntityLivingBase base) {
        if (NoInterp.update(MixinEntityLivingBase.NOINTERP.get(), (Entity)base)) {
            return ((IEntityNoInterp)base).getNoInterpX();
        }
        return base.posX;
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;posZ:D"))
    private double posZHookOnUpdate(final EntityLivingBase base) {
        if (MixinEntityLivingBase.NOINTERP.isEnabled() && base instanceof IEntityNoInterp && ((IEntityNoInterp)base).isNoInterping() && MixinEntityLivingBase.NOINTERP.get().isSilent()) {
            return ((IEntityNoInterp)base).getNoInterpZ();
        }
        return base.posZ;
    }
    
    @Inject(method = { "isElytraFlying" }, at = { @At("HEAD") }, cancellable = true)
    private void isElytraFlyingHook(final CallbackInfoReturnable<Boolean> info) {
        if (EntityPlayerSP.class.isInstance(this) && MixinEntityLivingBase.ELYTRA_FLIGHT.isEnabled() && MixinEntityLivingBase.ELYTRA_FLIGHT.get().getMode() == ElytraMode.Packet) {
            info.setReturnValue(false);
        }
    }
    
    @Inject(method = { "notifyDataManagerChange" }, at = { @At("RETURN") })
    public void notifyDataManagerChangeHook(final DataParameter<?> key, final CallbackInfo info) {
        if (key.equals((Object)MixinEntityLivingBase.HEALTH) && (float)this.dataManager.get((DataParameter)MixinEntityLivingBase.HEALTH) <= 0.0 && this.world != null && this.world.isRemote) {
            Bus.EVENT_BUS.post(new DeathEvent(EntityLivingBase.class.cast(this)));
        }
    }
    
    @Inject(method = { "handleJumpWater" }, at = { @At("HEAD") }, cancellable = true)
    private void handleJumpWaterHook(final CallbackInfo info) {
        final LiquidJumpEvent event = new LiquidJumpEvent(EntityLivingBase.class.cast(this));
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "handleJumpLava" }, at = { @At("HEAD") }, cancellable = true)
    private void handleJumpLavaHook(final CallbackInfo info) {
        final LiquidJumpEvent event = new LiquidJumpEvent(EntityLivingBase.class.cast(this));
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "onItemUseFinish" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;resetActiveHand()V"))
    private void resetActiveHandHook(final EntityLivingBase base) {
        if (this.world.isRemote && MixinEntityLivingBase.FAST_EAT.isEnabled() && base instanceof EntityPlayerSP && !MixinEntityLivingBase.mc.isSingleplayer() && MixinEntityLivingBase.FAST_EAT.get().getMode() == FastEatMode.NoDelay && this.activeItemStack.getItem() instanceof ItemFood) {
            this.activeItemStackUseCount = 0;
            ((EntityPlayerSP)base).connection.sendPacket((Packet)new CPacketPlayerTryUseItem(base.getActiveHand()));
        }
        else {
            base.resetActiveHand();
        }
    }
    
    @Inject(method = { "swingArm" }, at = { @At("HEAD") })
    private void swingArmHook(final EnumHand hand, final CallbackInfo ci) {
        if (EntityPlayerSP.class.isInstance(this) && MixinEntityLivingBase.SPECTATE.isEnabled()) {
            final EntityPlayer player = MixinEntityLivingBase.SPECTATE.get().getFake();
            if (player != null) {
                player.swingArm(hand);
            }
        }
    }
    
    @Inject(method = { "setPositionAndRotationDirect" }, at = { @At("RETURN") })
    private void setPositionAndRotationDirectHook(final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport, final CallbackInfo ci) {
        if (MixinEntityLivingBase.NOINTERP.isEnabled()) {
            NoInterp.handleNoInterp(MixinEntityLivingBase.NOINTERP.get(), Entity.class.cast(this), posRotationIncrements, x, y, z, yaw, pitch);
        }
    }
    
    @Redirect(method = { "travel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isServerWorld()Z"))
    public boolean travelHook(final EntityLivingBase entityLivingBase) {
        return this.isServerWorld() || entityLivingBase instanceof MotionTracker;
    }
    
    static {
        ELYTRA_FLIGHT = Caches.getModule(ElytraFlight.class);
        FAST_EAT = Caches.getModule(FastEat.class);
        NOINTERP = Caches.getModule(NoInterp.class);
        SPECTATE = Caches.getModule(Spectate.class);
    }
}
