//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.noglitchblocks;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class NoGlitchBlocks extends Module
{
    protected final Setting<Boolean> place;
    protected final Setting<Boolean> crack;
    protected final Setting<Boolean> ground;
    
    public NoGlitchBlocks() {
        super("NoGlitchBlocks", Category.Player);
        this.place = this.register(new BooleanSetting("Place", true));
        this.crack = this.register(new BooleanSetting("Break", true));
        this.ground = this.register(new BooleanSetting("Ground", false));
        this.listeners.add(new ListenerBlockDestroy(this));
        final SimpleData data = new SimpleData(this, "Tries to prevent Glitchblocks.");
        data.register(this.place, "Prevents Glitchblocks when placing.");
        data.register(this.crack, "Prevents Glitchblocks when breaking blocks.");
        data.register(this.ground, "Always check for GlitchBlocks, not only when on the ground.");
        this.setData(data);
    }
    
    public boolean noPlace() {
        return this.isEnabled() && this.place.getValue() && (this.ground.getValue() || NoGlitchBlocks.mc.player.onGround);
    }
    
    public boolean noBreak() {
        return this.isEnabled() && this.crack.getValue();
    }
}
