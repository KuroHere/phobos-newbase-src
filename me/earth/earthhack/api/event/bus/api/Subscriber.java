// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus.api;

import java.util.*;

public interface Subscriber
{
    Collection<Listener<?>> getListeners();
}
