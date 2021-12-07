// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import net.minecraft.client.multiplayer.*;

public class WorldClientEvent
{
    private final WorldClient client;
    
    private WorldClientEvent(final WorldClient client) {
        this.client = client;
    }
    
    public WorldClient getClient() {
        return this.client;
    }
    
    public static class Load extends WorldClientEvent
    {
        public Load(final WorldClient client) {
            super(client, null);
        }
    }
    
    public static class Unload extends WorldClientEvent
    {
        public Unload(final WorldClient client) {
            super(client, null);
        }
    }
}
