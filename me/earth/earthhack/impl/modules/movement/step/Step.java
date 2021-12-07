//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.step;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.module.*;
import java.awt.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;

public class Step extends BlockESPModule
{
    protected final Setting<StepESP> esp;
    protected final Setting<Float> height;
    protected final Setting<Boolean> useTimer;
    protected final Setting<Boolean> vanilla;
    protected final Setting<Boolean> entityStep;
    protected final Setting<Boolean> autoOff;
    protected final Setting<Integer> lagTime;
    protected final Setting<Boolean> gapple;
    protected final StopWatch breakTimer;
    protected boolean stepping;
    protected double y;
    
    public Step() {
        super("Step", Category.Movement);
        this.esp = this.registerBefore(new EnumSetting("ESP", StepESP.None), super.color);
        this.height = this.register(new NumberSetting("Height", 2.0f, 0.6f, 10.0f));
        this.useTimer = this.register(new BooleanSetting("UseTimer", false));
        this.vanilla = this.register(new BooleanSetting("Vanilla", false));
        this.entityStep = this.register(new BooleanSetting("EntityStep", true));
        this.autoOff = this.register(new BooleanSetting("AutoOff", false));
        this.lagTime = this.register(new NumberSetting("LagTime", 0, 0, 250));
        this.gapple = this.register(new BooleanSetting("Mine-Gapple", false));
        this.breakTimer = new StopWatch();
        this.listeners.add(new ListenerStep(this));
        this.listeners.add(new ListenerDestroy(this));
        this.listeners.add(new ListenerBreak(this));
        this.listeners.add(new ListenerRender(this));
        DisablingModule.makeDisablingModule(this);
        super.color.setValue(new Color(0, 255, 255, 76));
        super.outline.setValue(new Color(0, 255, 255));
        this.setData(new StepData(this));
    }
    
    @Override
    protected void onDisable() {
        if (Step.mc.player != null) {
            if (Step.mc.player.getRidingEntity() != null) {
                Step.mc.player.getRidingEntity().stepHeight = 1.0f;
            }
            Step.mc.player.stepHeight = 0.6f;
        }
        Managers.TIMER.reset();
    }
    
    public void onBreak() {
        this.breakTimer.reset();
    }
    
    protected boolean canStep() {
        return !Step.mc.player.isInWater() && Step.mc.player.onGround && !Step.mc.player.isOnLadder() && !Step.mc.player.movementInput.jump && Step.mc.player.isCollidedVertically && Step.mc.player.fallDistance < 0.1;
    }
}
