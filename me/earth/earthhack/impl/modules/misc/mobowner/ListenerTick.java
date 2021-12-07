//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.mobowner;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import me.earth.earthhack.api.util.interfaces.*;

final class ListenerTick extends ModuleListener<MobOwner, TickEvent>
{
    public ListenerTick(final MobOwner module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (ListenerTick.mc.world != null) {
            for (final Entity entity : ListenerTick.mc.world.getLoadedEntityList()) {
                if (entity != null && !entity.getAlwaysRenderNameTag()) {
                    if (entity instanceof EntityTameable) {
                        final EntityTameable tameable = (EntityTameable)entity;
                        if (!tameable.isTamed()) {
                            continue;
                        }
                        this.renderNametag(entity, tameable.getOwnerId());
                    }
                    else {
                        if (!(entity instanceof AbstractHorse)) {
                            continue;
                        }
                        final AbstractHorse horse = (AbstractHorse)entity;
                        if (!horse.isTame()) {
                            continue;
                        }
                        this.renderNametag(entity, horse.getOwnerUniqueId());
                    }
                }
            }
        }
    }
    
    private void renderNametag(final Entity entity, final UUID id) {
        if (id != null) {
            if (((MobOwner)this.module).cache.containsKey(id)) {
                final String owner = ((MobOwner)this.module).cache.get(id);
                if (owner != null) {
                    entity.setAlwaysRenderNameTag(true);
                    entity.setCustomNameTag(owner);
                }
            }
            else {
                Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.NAME, id) {
                    @Override
                    public void onSuccess() {
                        Globals.mc.addScheduledTask(() -> {
                            final Object val$id = id;
                            return (String)((MobOwner)ListenerTick.this.module).cache.put(id, this.name);
                        });
                    }
                    
                    @Override
                    public void onFailure() {
                        Globals.mc.addScheduledTask(() -> {
                            final Object val$id = id;
                            return (String)((MobOwner)ListenerTick.this.module).cache.put(id, null);
                        });
                    }
                });
            }
        }
    }
}
