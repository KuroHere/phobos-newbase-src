//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiSubtitleOverlay.class })
public abstract class MixinGuiSubtitleOverlay
{
    @Inject(method = { "renderSubtitles" }, at = { @At("HEAD") })
    private void renderSubtitlesHook(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new Render2DEvent());
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
