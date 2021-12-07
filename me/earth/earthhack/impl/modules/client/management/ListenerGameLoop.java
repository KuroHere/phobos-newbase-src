//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.management;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerGameLoop extends ModuleListener<Management, GameLoopEvent>
{
    public ListenerGameLoop(final Management module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (ListenerGameLoop.mc.world != null && ((Management)this.module).time.getValue() != 0) {
            ListenerGameLoop.mc.world.setWorldTime((long)(-((Management)this.module).time.getValue()));
        }
    }
}
