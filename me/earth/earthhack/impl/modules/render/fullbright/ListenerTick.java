//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.fullbright;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.render.fullbright.mode.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

final class ListenerTick extends ModuleListener<Fullbright, TickEvent>
{
    public ListenerTick(final Fullbright module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe()) {
            switch (((Fullbright)this.module).mode.getValue()) {
                case Gamma: {
                    ListenerTick.mc.gameSettings.gammaSetting = 1000.0f;
                    break;
                }
                case Potion: {
                    ListenerTick.mc.gameSettings.gammaSetting = 1.0f;
                    ListenerTick.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1215, 0));
                    break;
                }
            }
        }
    }
}
