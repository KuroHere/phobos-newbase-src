// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.serializer.setting;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerTick extends EventListener<TickEvent>
{
    private final SettingSerializer serializer;
    
    public ListenerTick(final SettingSerializer serializer) {
        super(TickEvent.class);
        this.serializer = serializer;
    }
    
    @Override
    public void invoke(final TickEvent event) {
        this.serializer.onTick();
    }
}
