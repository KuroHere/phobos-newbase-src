//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.fullbright;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.fullbright.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.init.*;

public class Fullbright extends Module
{
    protected final Setting<BrightMode> mode;
    
    public Fullbright() {
        super("Fullbright", Category.Render);
        this.mode = this.register(new EnumSetting("Mode", BrightMode.Gamma));
        this.listeners.add(new ListenerTick(this));
        final SimpleData data = new SimpleData(this, "Makes the game constantly bright.");
        data.register(this.mode, "-Gamma standard Fullbright.\n-Potion applies a NightVision potion to you.");
        this.setData(data);
    }
    
    @Override
    protected void onDisable() {
        if (Fullbright.mc.player != null && this.mode.getValue() == BrightMode.Potion) {
            Fullbright.mc.gameSettings.gammaSetting = 1.0f;
            Fullbright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
    }
}
