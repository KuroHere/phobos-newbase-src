// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.minecraftforge;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.registries.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.management.*;
import java.util.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin(value = { GameData.class }, remap = false)
public class MixinGameData
{
    private static final SettingCache<Boolean, BooleanSetting, Management> IGNORE;
    
    @Redirect(method = { "injectSnapshot" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
    private static int injectSnapshotHook(final List<ResourceLocation> list) {
        if (MixinGameData.IGNORE.getValue() && list.size() != 0) {
            Earthhack.getLogger().info("Ignored " + list.size() + " missing forge registries.");
            return 0;
        }
        return list.size();
    }
    
    static {
        IGNORE = Caches.getSetting(Management.class, BooleanSetting.class, "IgnoreForgeRegistries", false);
    }
}
