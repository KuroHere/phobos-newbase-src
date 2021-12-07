// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import me.earth.earthhack.impl.core.ducks.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.tileentity.*;
import me.earth.earthhack.impl.modules.movement.velocity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ TileEntityShulkerBox.class })
public abstract class MixinTileEntityShulkerBox implements ITileEntityShulkerBox
{
    private static final ModuleCache<Velocity> VELOCITY;
    private static final SettingCache<Boolean, BooleanSetting, Velocity> SHULKERS;
    
    @Accessor("items")
    @Override
    public abstract NonNullList<ItemStack> getItems();
    
    @Inject(method = { "moveCollidedEntities" }, at = { @At("HEAD") }, cancellable = true)
    private void moveCollidedEntitiesHook(final CallbackInfo info) {
        if (MixinTileEntityShulkerBox.VELOCITY.isEnabled() && MixinTileEntityShulkerBox.SHULKERS.getValue()) {
            info.cancel();
        }
    }
    
    static {
        VELOCITY = Caches.getModule(Velocity.class);
        SHULKERS = Caches.getSetting(Velocity.class, BooleanSetting.class, "Shulkers", false);
    }
}
