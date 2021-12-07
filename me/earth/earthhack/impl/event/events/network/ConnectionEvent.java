//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import net.minecraft.entity.player.*;
import java.util.*;

public class ConnectionEvent
{
    private final EntityPlayer player;
    private final String name;
    private final UUID uuid;
    
    private ConnectionEvent(final String name, final UUID uuid, final EntityPlayer player) {
        this.player = player;
        this.name = name;
        this.uuid = uuid;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public String getName() {
        if (this.name == null && this.player != null) {
            return this.player.getName();
        }
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public static class Join extends ConnectionEvent
    {
        public Join(final String name, final UUID uuid, final EntityPlayer player) {
            super(name, uuid, player, null);
        }
    }
    
    public static class Leave extends ConnectionEvent
    {
        public Leave(final String name, final UUID uuid, final EntityPlayer player) {
            super(name, uuid, player, null);
        }
    }
}
