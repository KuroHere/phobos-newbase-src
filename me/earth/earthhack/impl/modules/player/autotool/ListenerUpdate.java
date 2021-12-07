//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.autotool;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerUpdate extends ModuleListener<AutoTool, UpdateEvent>
{
    protected ListenerUpdate(final AutoTool module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (((AutoTool)this.module).set && !ListenerUpdate.mc.gameSettings.keyBindAttack.isKeyDown()) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(((AutoTool)this.module).lastSlot));
            ((AutoTool)this.module).reset();
        }
    }
}
