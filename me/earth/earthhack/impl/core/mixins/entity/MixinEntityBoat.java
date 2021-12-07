//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.boatfly.*;
import java.util.function.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ EntityBoat.class })
public abstract class MixinEntityBoat extends MixinEntity
{
    private static final ModuleCache<BoatFly> BOAT_FLY;
    
    @Redirect(method = { "updateMotion" }, at = @At(value = "INVOKE", target = "net/minecraft/entity/item/EntityBoat.hasNoGravity()Z"))
    private boolean updateMotionHook(final EntityBoat boat) {
        return this.hasNoGravity() || (MixinEntityBoat.BOAT_FLY.isEnabled() && MixinEntityBoat.BOAT_FLY.returnIfPresent((Function<BoatFly, Number>)BoatFly::getGlideSpeed, 1.0E-4f).floatValue() == 0.0f);
    }
    
    static {
        BOAT_FLY = Caches.getModule(BoatFly.class);
    }
}
