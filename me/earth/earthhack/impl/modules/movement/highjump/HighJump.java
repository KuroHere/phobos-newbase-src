//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.helpers.blocks.attack.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;

public class HighJump extends ObbyListenerModule<ListenerObby> implements InstantAttackingModule
{
    protected final Setting<Boolean> scaffold;
    protected final Setting<Double> range;
    protected final Setting<Double> height;
    protected final Setting<Boolean> onGround;
    protected final Setting<Boolean> onlySpecial;
    protected final Setting<Boolean> explosions;
    protected final Setting<Boolean> velocity;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> constant;
    protected final Setting<Double> factor;
    protected final Setting<Double> minY;
    protected final Setting<Boolean> cancelJump;
    protected final Setting<Integer> lagTime;
    protected final Setting<Boolean> resetAlways;
    protected final Setting<Double> alwaysY;
    protected final Setting<Boolean> addY;
    protected final Setting<Double> addFactor;
    protected final Setting<Boolean> addJump;
    protected final Setting<Double> jumpFactor;
    protected final Setting<Double> scaffoldY;
    protected final Setting<Double> scaffoldMaxY;
    protected final Setting<Integer> scaffoldOffset;
    protected final Setting<Boolean> instant;
    protected final StopWatch timer;
    protected double motionY;
    
    public HighJump() {
        super("HighJump", Category.Movement);
        this.height = this.register(new NumberSetting("Height", 0.42, 0.0, 1.0));
        this.onGround = this.register(new BooleanSetting("OnGround", true));
        this.onlySpecial = this.register(new BooleanSetting("OnlySpecial", false));
        this.explosions = this.register(new BooleanSetting("Explosions", false));
        this.velocity = this.register(new BooleanSetting("Velocity", false));
        this.delay = this.register(new NumberSetting("Jump-Delay", 250, 0, 1000));
        this.constant = this.register(new BooleanSetting("Constant", false));
        this.factor = this.register(new NumberSetting("Factor", 1.0, 0.0, 10.0));
        this.minY = this.register(new NumberSetting("MinY", 0.0, 0.0, 5.0));
        this.cancelJump = this.register(new BooleanSetting("Cancel-Jump", false));
        this.lagTime = this.register(new NumberSetting("LagTime", 1000, 0, 2500));
        this.resetAlways = this.register(new BooleanSetting("Reset-Always", false));
        this.alwaysY = this.register(new NumberSetting("Always-Y", 0.0, 0.0, 5.0));
        this.addY = this.register(new BooleanSetting("Add-Y", false));
        this.addFactor = this.register(new NumberSetting("Add-Factor", 0.5, 0.1, 2.0));
        this.addJump = this.register(new BooleanSetting("Add-Jump", false));
        this.jumpFactor = this.register(new NumberSetting("Jump-Factor", 0.5, 0.1, 2.0));
        this.scaffoldY = this.register(new NumberSetting("Scaffold-Y", 0.0, 0.0, 2.0));
        this.scaffoldMaxY = this.register(new NumberSetting("Scaffold-Max-Y", 10.0, 0.0, 10.0));
        this.scaffoldOffset = this.register(new NumberSetting("Scaffold-Offset", 2, 0, 3));
        this.instant = this.register(new BooleanSetting("Instant-Attack", true));
        this.timer = new StopWatch();
        this.listeners.clear();
        this.listeners.add(this.listener);
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerVelocity(this));
        this.listeners.add(new ListenerInput(this));
        this.listeners.add(new InstantAttackListener<Object>(this));
        this.scaffold = this.registerBefore(new BooleanSetting("Scaffold", false), this.blocks);
        this.range = this.registerAfter(new NumberSetting("Range", 5.25, 0.1, 6.0), this.scaffold);
        this.setData(new HighJumpData(this));
    }
    
    @Override
    protected boolean checkNull() {
        this.packets.clear();
        this.blocksPlaced = 0;
        return HighJump.mc.player != null && HighJump.mc.world != null;
    }
    
    @Override
    public String getDisplayInfo() {
        if (!this.velocity.getValue() && !this.explosions.getValue()) {
            return null;
        }
        if (!Managers.NCP.passed(this.lagTime.getValue())) {
            return "§cLag";
        }
        if (this.timer.passed(this.delay.getValue())) {
            return "0.00";
        }
        final double y = MathUtil.round(this.motionY, 2);
        if (this.motionY < this.minY.getValue()) {
            return "§c" + y;
        }
        return "§a" + y;
    }
    
    @Override
    protected void onEnable() {
        this.motionY = 0.0;
    }
    
    @Override
    protected ListenerObby createListener() {
        return new ListenerObby(this, 9);
    }
    
    public void addVelocity(final double y) {
        if (this.timer.passed(this.delay.getValue())) {
            this.motionY = y;
            this.timer.reset();
        }
        else if (this.addY.getValue()) {
            this.motionY += y * this.addFactor.getValue();
        }
        if (this.resetAlways.getValue() && y >= this.alwaysY.getValue()) {
            this.timer.reset();
        }
        if (this.addJump.getValue() && !HighJump.mc.player.onGround && HighJump.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP player = HighJump.mc.player;
            player.motionY += y * this.jumpFactor.getValue();
        }
    }
    
    @Override
    public boolean shouldAttack(final EntityEnderCrystal entity) {
        if (!this.attack.getValue() || !this.instant.getValue()) {
            return false;
        }
        final BlockPos pos = PositionUtil.getPosition();
        for (int i = 0; i <= this.range.getValue(); ++i) {
            if (entity.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(pos.down(i)))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Passable getTimer() {
        return this.attackTimer;
    }
    
    @Override
    public int getBreakDelay() {
        return this.breakDelay.getValue();
    }
    
    @Override
    public int getCooldown() {
        return this.cooldown.getValue();
    }
}
