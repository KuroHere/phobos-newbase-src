// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.serializer.friend;

import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.impl.managers.client.event.*;

final class ListenerFriends implements Observer<PlayerEvent>
{
    private final FriendSerializer serializer;
    
    public ListenerFriends(final FriendSerializer serializer) {
        this.serializer = serializer;
    }
    
    @Override
    public void onChange(final PlayerEvent event) {
        this.serializer.onChange(event);
    }
}
