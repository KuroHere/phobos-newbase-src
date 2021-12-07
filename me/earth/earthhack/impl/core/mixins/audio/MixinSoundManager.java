//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.audio;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.audio.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.audio.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ SoundManager.class })
public class MixinSoundManager
{
    @Shadow
    private boolean loaded;
    
    @Inject(method = { "playSound" }, at = { @At("HEAD") }, cancellable = true)
    private void playSoundHook(final ISound p_sound, final CallbackInfo ci) {
        if (this.loaded) {
            final PlaySoundEvent event = new PlaySoundEvent(p_sound);
            Bus.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
