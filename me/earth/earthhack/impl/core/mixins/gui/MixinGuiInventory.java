// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.inventory.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiInventory.class })
public abstract class MixinGuiInventory
{
    private static final ModuleCache<XCarry> XCARRY;
    
    @Inject(method = { "onGuiClosed" }, at = { @At("HEAD") }, cancellable = true)
    private void onGuiClosedHook(final CallbackInfo info) {
        if (MixinGuiInventory.XCARRY.isEnabled()) {
            Keyboard.enableRepeatEvents(false);
            info.cancel();
        }
    }
    
    static {
        XCARRY = Caches.getModule(XCarry.class);
    }
}
