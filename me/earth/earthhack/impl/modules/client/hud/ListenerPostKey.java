//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.modules.client.hud.modes.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import java.util.*;

final class ListenerPostKey extends ModuleListener<HUD, KeyboardEvent.Post>
{
    public ListenerPostKey(final HUD module) {
        super(module, (Class<? super Object>)KeyboardEvent.Post.class);
    }
    
    public void invoke(final KeyboardEvent.Post event) {
        if (ListenerPostKey.mc.player == null || ListenerPostKey.mc.world == null) {
            return;
        }
        ((HUD)this.module).resolution = new ScaledResolution(ListenerPostKey.mc);
        ((HUD)this.module).width = ((HUD)this.module).resolution.getScaledWidth();
        ((HUD)this.module).height = ((HUD)this.module).resolution.getScaledHeight();
        ((HUD)this.module).modules.clear();
        if (((HUD)this.module).renderModules.getValue() != Modules.None) {
            Map.Entry<String, Module> entry = null;
            for (final Module mod : Managers.MODULES.getRegistered()) {
                if (mod.isEnabled() && mod.isHidden() != Hidden.Hidden) {
                    entry = new AbstractMap.SimpleEntry<String, Module>(ModuleUtil.getHudName(mod), mod);
                    ((HUD)this.module).modules.add(entry);
                }
            }
            if (((HUD)this.module).renderModules.getValue() == Modules.Length) {
                ((HUD)this.module).modules.sort(Comparator.comparing(entry -> Managers.TEXT.getStringWidth(entry.getKey()) * -1));
            }
            else {
                ((HUD)this.module).modules.sort((Comparator<? super Map.Entry<String, Module>>)Map.Entry.comparingByKey());
            }
        }
    }
}
