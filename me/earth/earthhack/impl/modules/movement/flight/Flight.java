//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class Flight extends Module
{
    protected final Setting<FlightMode> mode;
    protected final Setting<Double> speed;
    protected final Setting<Boolean> animation;
    protected final Setting<Boolean> damage;
    protected final Setting<Boolean> antiKick;
    protected final Setting<Boolean> glide;
    protected final Setting<Double> glideSpeed;
    protected final Setting<Double> aacY;
    protected int counter;
    protected int antiCounter;
    protected int constantiamStage;
    protected int constantiamTicks;
    protected double moveSpeed;
    protected int stage;
    protected int ticks;
    protected double y;
    protected int constNewStage;
    protected int constNewTicks;
    protected double constNewOffset;
    protected double constY;
    protected double constMovementSpeed;
    protected double lastDist;
    protected boolean clipped;
    protected int oHareCounter;
    protected int oHareLevel;
    protected double oHareMoveSpeed;
    protected double oHareLastDist;
    
    public Flight() {
        super("Flight", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", FlightMode.Normal));
        this.speed = this.register(new NumberSetting("Speed", 2.5, 0.0, 50.0));
        this.animation = this.register(new BooleanSetting("Animation", true));
        this.damage = this.register(new BooleanSetting("Damage", false));
        this.antiKick = this.register(new BooleanSetting("AntiKick", true));
        this.glide = this.register(new BooleanSetting("Glide", true));
        this.glideSpeed = this.register(new NumberSetting("Glide-Speed", 0.03126, -2.0, 2.0));
        this.aacY = this.register(new NumberSetting("AAC-Y", 0.83, 0.0, 10.0));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerOnground(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.addAll(new ListenerPlayerPacket(this).getListeners());
        this.setData(new FlightData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
    
    @Override
    protected void onEnable() {
        this.constantiamStage = 0;
        this.constantiamTicks = 0;
        this.moveSpeed = 0.0;
        this.constNewStage = 0;
        this.constNewTicks = 0;
        this.constY = 0.0;
        this.constMovementSpeed = 0.0;
        this.oHareLevel = 1;
        this.oHareMoveSpeed = 0.1;
        this.oHareLastDist = 0.0;
        if (this.damage.getValue()) {
            damage();
        }
        if (this.mode.getValue() == FlightMode.Constantiam) {
            PacketUtil.doY(Flight.mc.player.posY + 0.22534, false);
            PacketUtil.doY(Flight.mc.player.posY + 0.04534, false);
        }
        if (this.mode.getValue() == FlightMode.ConstoHareFast && Flight.mc.player != null && Flight.mc.player.onGround) {
            Flight.mc.player.motionY = 0.40244999527931213;
        }
    }
    
    @Override
    protected void onDisable() {
        if (this.mode.getValue() == FlightMode.Jump && Flight.mc.player != null) {
            this.counter = 0;
            Flight.mc.player.jumpMovementFactor = 0.02f;
        }
        if (this.mode.getValue() == FlightMode.ConstantiamNew) {
            Flight.mc.player.setPosition(Flight.mc.player.posX, Flight.mc.player.posY + this.constY, Flight.mc.player.posZ);
        }
        if (this.mode.getValue() == FlightMode.ConstoHare && Flight.mc.player != null) {
            Flight.mc.player.motionX = 0.0;
            Flight.mc.player.motionZ = 0.0;
            this.oHareLevel = 0;
            this.oHareCounter = 0;
            this.oHareMoveSpeed = 0.1;
            this.oHareLastDist = 0.0;
        }
    }
    
    public static void damage() {
        final double offset = 0.0625;
        if (Flight.mc.player != null && Flight.mc.player.onGround) {
            for (int i = 0; i <= 4.0 / offset; ++i) {
                Flight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Flight.mc.player.posX, Flight.mc.player.posY + offset, Flight.mc.player.posZ, false));
                Flight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Flight.mc.player.posX, Flight.mc.player.posY, Flight.mc.player.posZ, i == 4.0 / offset));
            }
        }
    }
    
    public static float getMaxFallDist() {
        final PotionEffect potioneffect = Flight.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
        final int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
        return (float)(Flight.mc.player.getMaxFallHeight() + f);
    }
}
