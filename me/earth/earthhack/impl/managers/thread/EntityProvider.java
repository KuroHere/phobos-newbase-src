//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import java.util.*;

public class EntityProvider extends SubscriberImpl implements Globals
{
    private volatile List<EntityPlayer> players;
    private volatile List<Entity> entities;
    
    public EntityProvider() {
        this.players = Collections.emptyList();
        this.entities = Collections.emptyList();
        this.listeners.add(new EventListener<TickEvent.PostWorldTick>(TickEvent.PostWorldTick.class) {
            @Override
            public void invoke(final TickEvent.PostWorldTick event) {
                EntityProvider.this.update();
            }
        });
    }
    
    private void update() {
        if (EntityProvider.mc.world != null) {
            this.setLists(new ArrayList<Entity>(EntityProvider.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(EntityProvider.mc.world.playerEntities));
        }
        else {
            this.setLists(Collections.emptyList(), Collections.emptyList());
        }
    }
    
    private void setLists(final List<Entity> loadedEntities, final List<EntityPlayer> playerEntities) {
        this.entities = loadedEntities;
        this.players = playerEntities;
    }
    
    public List<Entity> getEntities() {
        return this.entities;
    }
    
    public List<EntityPlayer> getPlayers() {
        return this.players;
    }
    
    public List<Entity> getEntitiesAsync() {
        return this.getEntities(!EntityProvider.mc.isCallingFromMinecraftThread());
    }
    
    public List<EntityPlayer> getPlayersAsync() {
        return this.getPlayers(!EntityProvider.mc.isCallingFromMinecraftThread());
    }
    
    public List<Entity> getEntities(final boolean async) {
        return async ? this.entities : EntityProvider.mc.world.loadedEntityList;
    }
    
    public List<EntityPlayer> getPlayers(final boolean async) {
        return async ? this.players : EntityProvider.mc.world.playerEntities;
    }
    
    public Entity getEntity(final int id) {
        final List<Entity> entities = this.getEntitiesAsync();
        if (entities != null) {
            return entities.stream().filter(e -> e != null && e.getEntityId() == id).findFirst().orElse(null);
        }
        return null;
    }
}
