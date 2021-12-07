//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.helpers.blocks.data.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public abstract class ObbyListenerModule<T extends ObbyListener<?>> extends ObbyModule
{
    public final Setting<Integer> confirm;
    protected final T listener;
    
    protected ObbyListenerModule(final String name, final Category category) {
        super(name, category);
        this.confirm = this.register(new NumberSetting("Confirm", 100, 0, 1000));
        this.listener = this.createListener();
        this.listeners.add(this.listener);
        this.setData(new ObbyListenerData<Object>(this));
    }
    
    @Override
    protected void onEnable() {
        super.onEnable();
        this.listener.onModuleToggle();
        this.checkNull();
    }
    
    @Override
    protected void onDisable() {
        super.onDisable();
        this.listener.onModuleToggle();
        this.checkNull();
    }
    
    @Override
    public void placeBlock(final BlockPos on, final EnumFacing facing, final float[] helpingRotations, final Vec3d hitVec) {
        super.placeBlock(on, facing, helpingRotations, hitVec);
        this.listener.addCallback(on.offset(facing));
    }
    
    protected abstract T createListener();
}
