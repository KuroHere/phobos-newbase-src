// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.logoutspots.mode.*;
import java.util.*;
import me.earth.earthhack.impl.modules.render.logoutspots.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;

public class LogoutSpots extends Module
{
    protected final Setting<MessageMode> message;
    protected final Setting<Boolean> render;
    protected final Setting<Boolean> friends;
    protected final Setting<Float> scale;
    protected final Map<UUID, LogoutSpot> spots;
    
    public LogoutSpots() {
        super("LogoutSpots", Category.Render);
        this.message = this.register(new EnumSetting("Message", MessageMode.Render));
        this.render = this.register(new BooleanSetting("Render", true));
        this.friends = this.register(new BooleanSetting("Friends", true));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.spots = new ConcurrentHashMap<UUID, LogoutSpot>();
        this.listeners.add(new ListenerDisconnect(this));
        this.listeners.add(new ListenerJoin(this));
        this.listeners.add(new ListenerLeave(this));
        this.listeners.add(new ListenerRender(this));
    }
    
    @Override
    protected void onDisable() {
        this.spots.clear();
    }
}
