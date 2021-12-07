// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.event.events.movement.*;

@Mixin({ AbstractHorse.class })
public abstract class MixinAbstractHorse extends MixinEntityLivingBase
{
    @Inject(method = { "getHorseJumpStrength" }, at = { @At("HEAD") }, cancellable = true)
    private void getHorseJumpStrengthHook(final CallbackInfoReturnable<Double> info) {
        final HorseEvent event = new HorseEvent();
        Bus.EVENT_BUS.post(event);
        if (event.getJumpHeight() != 0.0) {
            info.setReturnValue(event.getJumpHeight());
        }
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    private void canBeSteeredHook(final CallbackInfoReturnable<Boolean> info) {
        final ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(true);
        }
    }
    
    @Inject(method = { "isHorseSaddled" }, at = { @At("HEAD") }, cancellable = true)
    private void isHorseSaddledHook(final CallbackInfoReturnable<Boolean> info) {
        final ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(true);
        }
    }
}
