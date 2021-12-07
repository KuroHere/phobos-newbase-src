// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.event;

import me.earth.earthhack.api.event.events.*;
import java.util.*;

public class PlayerEvent extends Event
{
    private final PlayerEventType type;
    private final String name;
    private final UUID uuid;
    
    public PlayerEvent(final PlayerEventType type, final String name, final UUID uuid) {
        this.type = type;
        this.name = name;
        this.uuid = uuid;
    }
    
    public PlayerEventType getType() {
        return this.type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
