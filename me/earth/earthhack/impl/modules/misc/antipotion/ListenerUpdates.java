//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipotion;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.potion.*;

final class ListenerUpdates extends ModuleListener<AntiPotion, UpdateEvent>
{
    public ListenerUpdates(final AntiPotion module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        ListenerUpdates.mc.player.getActivePotionEffects().removeIf(effect -> {
            final Setting<Boolean> setting = ((AntiPotion)this.module).getSetting(AntiPotion.getPotionString(effect.getPotion()), BooleanSetting.class);
            return setting != null && setting.getValue();
        });
    }
}
