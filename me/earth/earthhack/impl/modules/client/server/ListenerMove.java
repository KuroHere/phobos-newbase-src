// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.client.server.util.*;

final class ListenerMove extends ModuleListener<ServerModule, MoveEvent>
{
    public ListenerMove(final ServerModule module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((ServerModule)this.module).currentMode == ServerMode.Client && ((ServerModule)this.module).sync.getValue()) {
            event.setX(((ServerModule)this.module).getLastX());
            event.setY(((ServerModule)this.module).getLastY());
            event.setZ(((ServerModule)this.module).getLastZ());
        }
    }
}
