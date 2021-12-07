//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.elytraflight;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class ElytraFlight extends Module
{
    protected final Setting<ElytraMode> mode;
    protected final Setting<Double> hSpeed;
    protected final Setting<Double> vSpeed;
    protected final Setting<Boolean> autoStart;
    protected final Setting<Boolean> infDura;
    protected final Setting<Boolean> noWater;
    protected final Setting<Boolean> noGround;
    protected final Setting<Boolean> antiKick;
    protected final Setting<Float> glide;
    protected final Setting<Boolean> ncp;
    protected final Setting<Boolean> vertical;
    protected final Setting<Boolean> accel;
    protected final Setting<Boolean> instant;
    protected final StopWatch timer;
    protected boolean lag;
    protected double speed;
    protected int kick;
    
    public ElytraFlight() {
        super("ElytraFlight", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", ElytraMode.Wasp));
        this.hSpeed = this.register(new NumberSetting("H-Speed", 1.0, 0.0, 100.0));
        this.vSpeed = this.register(new NumberSetting("V-Speed", 1.0, 0.0, 100.0));
        this.autoStart = this.register(new BooleanSetting("AutoStart", false));
        this.infDura = this.register(new BooleanSetting("InfiniteDurability", false));
        this.noWater = this.register(new BooleanSetting("StopInWater", false));
        this.noGround = this.register(new BooleanSetting("StopOnGround", false));
        this.antiKick = this.register(new BooleanSetting("AntiKick", false));
        this.glide = this.register(new NumberSetting("Glide", 1.0E-4f, 0.0f, 0.2f));
        this.ncp = this.register(new BooleanSetting("NCP", false));
        this.vertical = this.register(new BooleanSetting("Vertical", true));
        this.accel = this.register(new BooleanSetting("Accelerate", true));
        this.instant = this.register(new BooleanSetting("Instant", true));
        this.timer = new StopWatch();
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerPosLook(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
    
    @Override
    protected void onEnable() {
        this.lag = true;
        this.timer.reset();
        this.kick = 0;
    }
    
    public ElytraMode getMode() {
        return this.mode.getValue();
    }
    
    public void sendFallPacket() {
        ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
    }
}
