// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.newchunks;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.impl.modules.render.newchunks.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import io.netty.util.internal.*;

public class NewChunks extends Module
{
    final Setting<Boolean> unload;
    final Set<ChunkData> data;
    
    public NewChunks() {
        super("NewChunks", Category.Render);
        this.unload = this.register(new BooleanSetting("Unload", false));
        this.data = (Set<ChunkData>)new ConcurrentSet();
        this.listeners.add(new ListenerChunkData(this));
        this.listeners.add(new ListenerRender(this));
    }
    
    @Override
    protected void onDisable() {
        this.data.clear();
    }
}
