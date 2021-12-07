//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.tracers;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.*;
import java.util.*;

final class ListenerTick extends ModuleListener<Tracers, TickEvent>
{
    public ListenerTick(final Tracers module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe()) {
            final List<Entity> sorted = new ArrayList<Entity>(ListenerTick.mc.world.loadedEntityList);
            try {
                sorted.sort(Comparator.comparingDouble(entity -> ListenerTick.mc.player.getDistanceSqToEntity(entity)));
            }
            catch (IllegalStateException ex) {}
            ((Tracers)this.module).sorted = sorted;
        }
    }
}
