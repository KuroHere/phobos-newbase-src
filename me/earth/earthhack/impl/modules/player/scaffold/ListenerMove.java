//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.scaffold;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerMove extends ModuleListener<Scaffold, MoveEvent>
{
    public ListenerMove(final Scaffold module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (ListenerMove.mc.player.onGround) {
            event.setSneaking(!((Scaffold)this.module).down.getValue() || !ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown() || ListenerMove.mc.gameSettings.keyBindJump.isKeyDown());
        }
    }
}
