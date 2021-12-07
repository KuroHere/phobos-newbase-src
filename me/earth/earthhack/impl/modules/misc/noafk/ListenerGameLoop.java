//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.noafk;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

final class ListenerGameLoop extends ModuleListener<NoAFK, GameLoopEvent>
{
    public ListenerGameLoop(final NoAFK module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (ListenerGameLoop.mc.player != null) {
            if (((NoAFK)this.module).rotate.getValue()) {
                final EntityPlayerSP player = ListenerGameLoop.mc.player;
                player.rotationYaw += (float)0.003;
            }
            if (((NoAFK)this.module).swing.getValue() && ((NoAFK)this.module).swing_timer.passed(2000L)) {
                ListenerGameLoop.mc.player.swingArm(EnumHand.MAIN_HAND);
                ((NoAFK)this.module).swing_timer.reset();
            }
        }
    }
}
