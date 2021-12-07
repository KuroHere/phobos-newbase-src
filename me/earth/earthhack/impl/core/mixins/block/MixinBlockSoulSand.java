// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.modules.movement.noslowdown.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BlockSoulSand.class })
public abstract class MixinBlockSoulSand
{
    private static final ModuleCache<NoSlowDown> NO_SLOW_DOWN;
    private static final SettingCache<Boolean, BooleanSetting, NoSlowDown> SOUL_SAND;
    
    @Inject(method = { "onEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    private void onEntityCollisionHook(final CallbackInfo info) {
        if (MixinBlockSoulSand.NO_SLOW_DOWN.isEnabled() && MixinBlockSoulSand.SOUL_SAND.getValue()) {
            info.cancel();
        }
    }
    
    static {
        NO_SLOW_DOWN = Caches.getModule(NoSlowDown.class);
        SOUL_SAND = Caches.getSetting(NoSlowDown.class, BooleanSetting.class, "SoulSand", true);
    }
}
