// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.client.server.util.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;

final class ListenerNoUpdate extends ModuleListener<ServerModule, NoMotionUpdateEvent>
{
    public ListenerNoUpdate(final ServerModule module) {
        super(module, (Class<? super Object>)NoMotionUpdateEvent.class);
    }
    
    public void invoke(final NoMotionUpdateEvent event) {
        if (((ServerModule)this.module).currentMode == ServerMode.Client || !((ServerModule)this.module).sync.getValue()) {
            return;
        }
        ProtocolPlayUtil.sendVelocityAndPosition(((ServerModule)this.module).connectionManager, RotationUtil.getRotationPlayer());
    }
}
