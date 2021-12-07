//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.managers.*;
import java.util.function.*;
import java.util.*;

final class ListenerTick extends ModuleListener<NoRender, TickEvent>
{
    private boolean previous;
    
    public ListenerTick(final NoRender module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        final boolean shouldUpdate = ((NoRender)this.module).items.getValue();
        if (event.isSafe()) {
            if (this.previous != shouldUpdate) {
                if (shouldUpdate) {
                    for (final Entity entity : ListenerTick.mc.world.loadedEntityList) {
                        if (entity instanceof EntityItem && !entity.isDead) {
                            Managers.SET_DEAD.setDeadCustom(entity, Long.MAX_VALUE);
                            ((NoRender)this.module).ids.add(entity.getEntityId());
                        }
                    }
                }
                else {
                    ((NoRender)this.module).ids.forEach(Managers.SET_DEAD::revive);
                    ((NoRender)this.module).ids.clear();
                }
            }
        }
        else {
            ((NoRender)this.module).ids.forEach(Managers.SET_DEAD::confirmKill);
            ((NoRender)this.module).ids.clear();
        }
        this.previous = shouldUpdate;
    }
}
