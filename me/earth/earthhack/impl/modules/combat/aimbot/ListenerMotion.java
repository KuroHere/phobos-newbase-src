//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.aimbot;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.item.*;

final class ListenerMotion extends ModuleListener<AimBot, MotionUpdateEvent>
{
    public ListenerMotion(final AimBot module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() != Stage.PRE) {
            return;
        }
        if (!(ListenerMotion.mc.player.getActiveItemStack().getItem() instanceof ItemBow)) {
            ((AimBot)this.module).target = null;
            return;
        }
        ((AimBot)this.module).target = ((AimBot)this.module).getTarget();
        if (((AimBot)this.module).target == null) {
            return;
        }
    }
}
