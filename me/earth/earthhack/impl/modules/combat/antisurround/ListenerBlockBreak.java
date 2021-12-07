//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.events.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

final class ListenerBlockBreak extends ModuleListener<AntiSurround, BlockDestroyEvent>
{
    public ListenerBlockBreak(final AntiSurround module) {
        super(module, (Class<? super Object>)BlockDestroyEvent.class);
    }
    
    public void invoke(final BlockDestroyEvent event) {
        if (((AntiSurround)this.module).active.get() || !((AntiSurround)this.module).normal.getValue() || event.isCancelled() || event.isUsed() || event.getStage() != Stage.PRE || ListenerBlockBreak.mc.player == null || ((AntiSurround)this.module).holdingCheck()) {
            return;
        }
        event.setUsed(true);
        ((AntiSurround)this.module).onBlockBreak(event.getPos(), ListenerBlockBreak.mc.world.playerEntities, ListenerBlockBreak.mc.world.loadedEntityList);
    }
}
