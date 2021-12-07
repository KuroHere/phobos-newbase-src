// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.misc.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPig.class })
public abstract class MixinEntityPig extends EntityAnimal
{
    public MixinEntityPig(final World world) {
        super(world);
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    private void canBeSteeredHook(final CallbackInfoReturnable<Boolean> info) {
        final ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue(true);
        }
    }
    
    @Redirect(method = { "travel" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/passive/EntityAnimal.travel(FFF)V"))
    private void travelHook(final EntityAnimal var1, final float strafe, final float vertical, final float forward) {
        final AIEvent event = new AIEvent();
        Bus.EVENT_BUS.post(event);
        super.func_191986_a(strafe, vertical, event.isCancelled() ? 0.0f : forward);
    }
}
