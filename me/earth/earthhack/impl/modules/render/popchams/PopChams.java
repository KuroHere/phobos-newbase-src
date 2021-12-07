//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.popchams;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;

public class PopChams extends BlockESPModule
{
    protected final Setting<Integer> fadeTime;
    protected final Setting<Boolean> selfPop;
    public final ColorSetting selfColor;
    public final ColorSetting selfOutline;
    protected final Setting<Boolean> friendPop;
    public final ColorSetting friendColor;
    public final ColorSetting friendOutline;
    private final HashMap<String, PopData> popDataHashMap;
    
    public PopChams() {
        super("PopChams", Category.Render);
        this.fadeTime = this.register(new NumberSetting("Fade-Time", 1500, 0, 5000));
        this.selfPop = this.register(new BooleanSetting("Self-Pop", false));
        this.selfColor = this.register(new ColorSetting("Self-Color", new Color(80, 80, 255, 80)));
        this.selfOutline = this.register(new ColorSetting("Self-Outline", new Color(80, 80, 255, 255)));
        this.friendPop = this.register(new BooleanSetting("Friend-Pop", false));
        this.friendColor = this.register(new ColorSetting("Friend-Color", new Color(45, 255, 45, 80)));
        this.friendOutline = this.register(new ColorSetting("Friend-Outline", new Color(45, 255, 45, 255)));
        this.popDataHashMap = new HashMap<String, PopData>();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerTotemPop(this));
        super.color.setValue(new Color(255, 45, 45, 80));
        super.outline.setValue(new Color(255, 45, 45, 255));
        this.unregister(super.height);
    }
    
    @Override
    protected void onEnable() {
        this.popDataHashMap.clear();
    }
    
    public HashMap<String, PopData> getPopDataHashMap() {
        return this.popDataHashMap;
    }
    
    protected Color getColor(final EntityPlayer entity) {
        if (entity == PopChams.mc.player) {
            return this.selfColor.getValue();
        }
        if (Managers.FRIENDS.contains(entity)) {
            return this.friendColor.getValue();
        }
        return this.color.getValue();
    }
    
    protected Color getOutlineColor(final EntityPlayer entity) {
        if (entity == PopChams.mc.player) {
            return this.selfOutline.getValue();
        }
        if (Managers.FRIENDS.contains(entity)) {
            return this.friendOutline.getValue();
        }
        return this.outline.getValue();
    }
    
    protected boolean isValidEntity(final EntityPlayer entity) {
        return (entity != PopChams.mc.player || this.selfPop.getValue()) && (!Managers.FRIENDS.contains(entity) || entity == PopChams.mc.player || this.friendPop.getValue());
    }
    
    public static class PopData
    {
        private final EntityPlayer player;
        private final long time;
        private final float yaw;
        private final float pitch;
        private final double x;
        private final double y;
        private final double z;
        
        public PopData(final EntityPlayer player, final long time, final float yaw, final float pitch, final double x, final double y, final double z) {
            this.player = player;
            this.time = time;
            this.yaw = yaw;
            this.pitch = pitch;
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public EntityPlayer getPlayer() {
            return this.player;
        }
        
        public long getTime() {
            return this.time;
        }
        
        public float getYaw() {
            return this.yaw;
        }
        
        public float getPitch() {
            return this.pitch;
        }
        
        public double getX() {
            return this.x;
        }
        
        public double getY() {
            return this.y;
        }
        
        public double getZ() {
            return this.z;
        }
    }
}
