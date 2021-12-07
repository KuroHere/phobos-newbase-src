//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.impl.core.mixins.entity.living.*;
import me.earth.earthhack.impl.modules.misc.tpssync.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.thread.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.movement.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends MixinEntityLivingBase
{
    private static final ModuleCache<TpsSync> TPS_SYNC;
    private static final SettingCache<Boolean, BooleanSetting, TpsSync> ATTACK;
    @Shadow
    public InventoryPlayer inventory;
    @Shadow
    public Container inventoryContainer;
    
    @Shadow
    public void onUpdate() {
        throw new IllegalStateException("onUpdate was not shadowed!");
    }
    
    @Shadow
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        throw new IllegalStateException("attackEntityFrom wasn't shadowed!");
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("RETURN") })
    private void onUpdateHook(final CallbackInfo ci) {
        if (this.shouldCache()) {
            this.armorValue = this.getTotalArmorValue();
            this.armorToughness = (float)this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
            this.explosionModifier = EnchantmentUtil.getEnchantmentModifierDamage(this.getArmorInventoryList(), DamageSource.field_191552_t);
        }
    }
    
    @Inject(method = { "isEntityInsideOpaqueBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void isEntityInsideOpaqueBlockHook(final CallbackInfoReturnable<Boolean> info) {
        final SuffocationEvent event = new SuffocationEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "attackTargetEntityWithCurrentItem" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/player/EntityPlayer.setSprinting(Z)V"))
    private void attackTargetEntityWithCurrentItemHook(final EntityPlayer entity, final boolean sprinting) {
        final SprintEvent event = new SprintEvent(sprinting);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            this.motionX /= 0.6;
            this.motionZ /= 0.6;
        }
        else {
            entity.setSprinting(event.isSprinting());
        }
    }
    
    @Inject(method = { "getCooldownPeriod" }, at = { @At("HEAD") }, cancellable = true)
    private void getCooldownPeriodHook(final CallbackInfoReturnable<Float> info) {
        if (MixinEntityPlayer.TPS_SYNC.isEnabled() && MixinEntityPlayer.ATTACK.getValue()) {
            info.setReturnValue((float)(1.0 / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * Managers.TPS.getTps()));
        }
    }
    
    static {
        TPS_SYNC = Caches.getModule(TpsSync.class);
        ATTACK = Caches.getSetting(TpsSync.class, BooleanSetting.class, "Attack", false);
    }
}
