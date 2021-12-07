// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityLlama.class })
public abstract class MixinEntityLlama extends MixinAbstractHorse
{
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    private void canBeSteeredHook(final CallbackInfoReturnable<Boolean> info) {
        final ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(true);
        }
    }
}
