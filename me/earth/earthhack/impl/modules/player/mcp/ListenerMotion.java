//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.mcp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerMotion extends ModuleListener<MiddleClickPearl, MotionUpdateEvent>
{
    public ListenerMotion(final MiddleClickPearl module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (((MiddleClickPearl)this.module).runnable == null) {
            return;
        }
        if (event.getStage() == Stage.PRE) {
            event.setYaw(ListenerMotion.mc.player.rotationYaw);
            event.setPitch(ListenerMotion.mc.player.rotationPitch);
            Managers.ROTATION.setBlocking(true);
        }
        else {
            ((MiddleClickPearl)this.module).runnable.run();
            ((MiddleClickPearl)this.module).runnable = null;
            Managers.ROTATION.setBlocking(false);
        }
    }
}
