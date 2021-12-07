// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.autotool;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class AutoTool extends Module
{
    protected int lastSlot;
    protected boolean set;
    
    public AutoTool() {
        super("AutoTool", Category.Player);
        this.lastSlot = -1;
        this.set = false;
        this.listeners.add(new ListenerDamageBlock(this));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerDeath(this));
        this.listeners.add(new ListenerDisconnect(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.setData(new SimpleData(this, "Automatically selects the best Tool when mining a block."));
    }
    
    @Override
    protected void onEnable() {
        this.reset();
    }
    
    public void reset() {
        this.lastSlot = -1;
        this.set = false;
    }
}
