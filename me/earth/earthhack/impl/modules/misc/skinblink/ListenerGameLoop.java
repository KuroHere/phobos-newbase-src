//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.skinblink;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.player.*;

final class ListenerGameLoop extends ModuleListener<SkinBlink, GameLoopEvent>
{
    public ListenerGameLoop(final SkinBlink module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (((SkinBlink)this.module).timer.passed(((SkinBlink)this.module).delay.getValue())) {
            for (final EnumPlayerModelParts parts : EnumPlayerModelParts.values()) {
                ListenerGameLoop.mc.gameSettings.setModelPartEnabled(parts, ((boolean)((SkinBlink)this.module).random.getValue()) ? (Math.random() < 0.5) : (!ListenerGameLoop.mc.gameSettings.getModelParts().contains(parts)));
            }
            ((SkinBlink)this.module).timer.reset();
        }
    }
}
