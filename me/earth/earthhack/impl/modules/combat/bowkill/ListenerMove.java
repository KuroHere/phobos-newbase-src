//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.item.*;

final class ListenerMove extends ModuleListener<BowKiller, MoveEvent>
{
    public ListenerMove(final BowKiller module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (!ListenerMove.mc.player.isCollidedVertically) {
            return;
        }
        if (((BowKiller)this.module).staticS.getValue() && ListenerMove.mc.player.getActiveItemStack().getItem() instanceof ItemBow && ((BowKiller)this.module).blockUnder) {
            ListenerMove.mc.player.setVelocity(0.0, 0.0, 0.0);
            event.setX(0.0);
            event.setY(0.0);
            event.setZ(0.0);
        }
    }
}
