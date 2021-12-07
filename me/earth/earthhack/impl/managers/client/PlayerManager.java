//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import me.earth.earthhack.api.observable.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import me.earth.earthhack.impl.managers.client.event.*;
import java.util.*;

public class PlayerManager extends Observable<PlayerEvent>
{
    private final Map<String, UUID> players;
    
    public PlayerManager() {
        this.players = new ConcurrentHashMap<String, UUID>();
    }
    
    public boolean contains(final Entity player) {
        return player instanceof EntityPlayer && this.contains(player.getName());
    }
    
    public boolean contains(final EntityPlayer player) {
        return this.contains(player.getName());
    }
    
    public boolean contains(final String name) {
        return this.players.containsKey(name);
    }
    
    public void add(final EntityPlayer player) {
        this.add(player.getName(), player.getUniqueID());
    }
    
    public void add(final String name) {
        Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.UUID, name) {
            @Override
            public void onSuccess() {
                PlayerManager.this.add(this.name, this.uuid);
            }
            
            @Override
            public void onFailure() {
            }
        });
    }
    
    public void add(final String name, final UUID uuid) {
        final PlayerEvent event = new PlayerEvent(PlayerEventType.ADD, name, uuid);
        this.onChange(event);
        if (!event.isCancelled()) {
            this.players.put(name, uuid);
        }
    }
    
    public void remove(final Entity player) {
        if (player instanceof EntityPlayer) {
            this.remove(player.getName());
        }
    }
    
    public void remove(final String name) {
        final UUID uuid = this.players.get(name);
        final PlayerEvent event = new PlayerEvent(PlayerEventType.DEL, name, uuid);
        this.onChange(event);
        if (!event.isCancelled()) {
            this.players.remove(name);
        }
    }
    
    public Collection<String> getPlayers() {
        return this.players.keySet();
    }
    
    public Map<String, UUID> getPlayersWithUUID() {
        return this.players;
    }
    
    public void clear() {
        this.players.clear();
    }
}
