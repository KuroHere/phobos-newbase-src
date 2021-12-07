//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antiaim;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.item.*;
import org.lwjgl.input.*;

public class AntiAim extends Module
{
    protected final Setting<AntiAimMode> mode;
    protected final Setting<Float> hSpeed;
    protected final Setting<Float> vSpeed;
    protected final Setting<Boolean> strict;
    protected final Setting<Boolean> sneak;
    protected final Setting<Integer> sneakDelay;
    protected final Setting<Float> yaw;
    protected final Setting<Float> pitch;
    protected final Setting<Integer> skip;
    protected final StopWatch timer;
    protected float lastYaw;
    protected float lastPitch;
    
    public AntiAim() {
        super("AntiAim", Category.Misc);
        this.mode = this.register(new EnumSetting("Mode", AntiAimMode.Spin));
        this.hSpeed = this.register(new NumberSetting("H-Speed", 10.0f, 0.1f, 180.0f));
        this.vSpeed = this.register(new NumberSetting("V-Speed", 10.0f, 0.1f, 180.0f));
        this.strict = this.register(new BooleanSetting("Strict", true));
        this.sneak = this.register(new BooleanSetting("Sneak", false));
        this.sneakDelay = this.register(new NumberSetting("Sneak-Delay", 500, 0, 5000));
        this.yaw = this.register(new NumberSetting("Yaw", 0.0f, -360.0f, 360.0f));
        this.pitch = this.register(new NumberSetting("Pitch", 0.0f, -90.0f, 90.0f));
        this.skip = this.register(new NumberSetting("Skip", 1, 1, 20));
        this.timer = new StopWatch();
        this.listeners.add(new ListenerMotion(this));
        this.setData(new AntiAimData(this));
    }
    
    @Override
    protected void onEnable() {
        if (AntiAim.mc.player != null) {
            this.lastYaw = AntiAim.mc.player.rotationYaw;
            this.lastPitch = AntiAim.mc.player.rotationPitch;
        }
    }
    
    public boolean dontRotate() {
        return this.strict.getValue() && (((!(AntiAim.mc.player.getActiveItemStack().getItem() instanceof ItemFood) || AntiAim.mc.gameSettings.keyBindAttack.isKeyDown()) && (AntiAim.mc.gameSettings.keyBindAttack.isKeyDown() || AntiAim.mc.gameSettings.keyBindUseItem.isKeyDown())) || Mouse.isButtonDown(2));
    }
}
