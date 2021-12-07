//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.icespeed;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.init.*;

public class IceSpeed extends Module
{
    protected final Setting<Float> speed;
    
    public IceSpeed() {
        super("IceSpeed", Category.Movement);
        this.speed = this.register(new NumberSetting("Speed", 0.4f, 0.0f, 1.5f));
        this.listeners.add(new ListenerTick(this));
        final SimpleData data = new SimpleData(this, "Makes you faster when walking on ice.");
        data.register(this.speed, "Modify your speed by this value.");
        this.setData(data);
    }
    
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}
