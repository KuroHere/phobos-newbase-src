//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.impl.core.ducks.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ EntityOtherPlayerMP.class })
public abstract class MixinEntityOtherPlayerMP extends MixinAbstractClientPlayer implements IEntityOtherPlayerMP
{
    private static final ModuleCache<NoInterp> NOINTERP;
    private static final ModuleCache<Spectate> SPECTATE;
    private float theYaw;
    private float thePitch;
    
    @Override
    public boolean attackEntitySuper(final DamageSource source, final float amount) {
        return super.attackEntityFrom(source, amount);
    }
    
    @Inject(method = { "attackEntityFrom" }, at = { @At("HEAD") }, cancellable = true)
    private void attackEntityFromHook(final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldAttackSuper()) {
            cir.setReturnValue(this.returnFromSuperAttack(source, amount));
        }
    }
    
    @Inject(method = { "setPositionAndRotationDirect" }, at = { @At("RETURN") })
    private void setPositionAndRotationDirectHook(final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport, final CallbackInfo ci) {
        if (MixinEntityOtherPlayerMP.NOINTERP.isEnabled()) {
            NoInterp.handleNoInterp(MixinEntityOtherPlayerMP.NOINTERP.get(), Entity.class.cast(this), posRotationIncrements, x, y, z, yaw, pitch);
        }
    }
    
    @Inject(method = { "onLivingUpdate" }, at = { @At("HEAD") })
    private void onLivingUpdateHead(final CallbackInfo ci) {
        this.theYaw = this.rotationYaw;
        this.thePitch = this.rotationPitch;
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityOtherPlayerMP;setRotation(FF)V"))
    private void setRotationHook(final EntityOtherPlayerMP entityOtherPlayerMP, final float yaw, final float pitch) {
        if (MixinEntityOtherPlayerMP.SPECTATE.isEnabled() && MixinEntityOtherPlayerMP.SPECTATE.get().shouldTurn() && entityOtherPlayerMP.equals((Object)MixinEntityOtherPlayerMP.SPECTATE.get().getRender())) {
            this.setRotation(this.theYaw, this.thePitch);
            return;
        }
        this.setRotation(yaw, pitch);
    }
    
    static {
        NOINTERP = Caches.getModule(NoInterp.class);
        SPECTATE = Caches.getModule(Spectate.class);
    }
}
