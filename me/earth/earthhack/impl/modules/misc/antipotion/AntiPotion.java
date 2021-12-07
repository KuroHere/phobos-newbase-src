//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipotion;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.client.resources.*;

public class AntiPotion extends Module
{
    public AntiPotion() {
        super("AntiPotion", Category.Misc);
        final AntiPotionData data = new AntiPotionData(this);
        this.setData(data);
        for (final Potion potion : Potion.REGISTRY) {
            final boolean value = potion == MobEffects.LEVITATION;
            final String name = getPotionString(potion);
            final Setting<?> s = this.register(new BooleanSetting(name, value));
            data.register(s, "Removes " + name + " potion effects.");
        }
        this.listeners.add(new ListenerUpdates(this));
    }
    
    public static String getPotionString(final Potion potion) {
        return I18n.format(potion.getName(), new Object[0]);
    }
}
