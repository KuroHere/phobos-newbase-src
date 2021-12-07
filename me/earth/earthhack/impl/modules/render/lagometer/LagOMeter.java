// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;

public class LagOMeter extends BlockESPModule
{
    protected final Setting<Boolean> esp;
    protected final Setting<Boolean> response;
    protected final Setting<Boolean> lagTime;
    protected final Setting<Boolean> nametag;
    protected final Setting<Float> scale;
    protected final ColorSetting textColor;
    protected final Setting<Integer> responseTime;
    protected final Setting<Integer> time;
    protected final Setting<Integer> chatTime;
    protected final Setting<Boolean> chat;
    protected final Setting<Boolean> render;
    protected final AtomicBoolean teleported;
    protected final StopWatch lag;
    protected ScaledResolution resolution;
    protected String respondingMessage;
    protected String lagMessage;
    protected boolean sent;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    
    public LagOMeter() {
        super("Lag-O-Meter", Category.Render);
        this.esp = this.registerBefore(new BooleanSetting("ESP", true), super.color);
        this.response = this.registerBefore(new BooleanSetting("Response", true), super.color);
        this.lagTime = this.registerBefore(new BooleanSetting("Lag", true), super.color);
        this.nametag = this.register(new BooleanSetting("Nametag", false));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.textColor = this.register(new ColorSetting("Name-Color", new Color(255, 255, 255, 255)));
        this.responseTime = this.register(new NumberSetting("ResponseTime", 500, 0, 2500));
        this.time = this.register(new NumberSetting("ESP-Time", 500, 0, 2500));
        this.chatTime = this.register(new NumberSetting("Chat-Time", 3000, 0, 5000));
        this.chat = this.register(new BooleanSetting("Chat", true));
        this.render = this.register(new BooleanSetting("Render-Text", true));
        this.teleported = new AtomicBoolean();
        this.lag = new StopWatch();
        this.unregister(super.height);
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerTeleport(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerText(this));
        this.listeners.add(new ListenerTick(this));
        super.color.setValue(new Color(255, 0, 0, 80));
        super.outline.setValue(new Color(255, 0, 0, 255));
        this.setData(new LagOMeterData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        final long t = System.currentTimeMillis() - Managers.NCP.getTimeStamp();
        if (t > this.time.getValue()) {
            return null;
        }
        return "§c" + MathUtil.round(t / 1000.0, 1);
    }
    
    @Override
    protected void onEnable() {
        this.sent = false;
        this.teleported.set(true);
        this.resolution = new ScaledResolution(LagOMeter.mc);
        this.x = Managers.POSITION.getX();
        this.y = Managers.POSITION.getY();
        this.z = Managers.POSITION.getZ();
        this.yaw = Managers.ROTATION.getServerYaw();
        this.pitch = Managers.ROTATION.getServerPitch();
    }
}
