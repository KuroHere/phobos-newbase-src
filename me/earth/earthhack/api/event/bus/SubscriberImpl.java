// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus;

import me.earth.earthhack.api.event.bus.api.*;
import java.util.*;

public class SubscriberImpl implements Subscriber
{
    protected final List<Listener<?>> listeners;
    
    public SubscriberImpl() {
        this.listeners = new ArrayList<Listener<?>>();
    }
    
    @Override
    public Collection<Listener<?>> getListeners() {
        return this.listeners;
    }
}
