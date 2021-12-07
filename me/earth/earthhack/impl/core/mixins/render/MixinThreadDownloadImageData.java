// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.impl.managers.thread.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.misc.extratab.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ThreadDownloadImageData.class })
public abstract class MixinThreadDownloadImageData implements GlobalExecutor
{
    private static final SettingCache<Boolean, BooleanSetting, ExtraTab> DOWNLOAD_THREADS;
    
    @Redirect(method = { "loadTextureFromServer" }, at = @At(value = "INVOKE", target = "Ljava/lang/Thread;start()V"))
    private void onStart(final Thread thread) {
        if (MixinThreadDownloadImageData.DOWNLOAD_THREADS.getValue()) {
            GlobalExecutor.FIXED_EXECUTOR.submit(thread);
        }
        else {
            thread.start();
        }
    }
    
    static {
        DOWNLOAD_THREADS = Caches.getSetting(ExtraTab.class, BooleanSetting.class, "Download-Threads", false);
    }
}
