//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.autosprint;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.autosprint.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.client.settings.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;

public class AutoSprint extends Module
{
    protected final Setting<SprintMode> mode;
    
    public AutoSprint() {
        super("Sprint", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", SprintMode.Rage));
        this.listeners.add(new ListenerTick(this));
        this.setData(new AutoSprintData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    @Override
    protected void onDisable() {
        KeyBinding.setKeyBindState(AutoSprint.mc.gameSettings.keyBindSprint.getKeyCode(), KeyBoardUtil.isKeyDown(AutoSprint.mc.gameSettings.keyBindSprint));
    }
    
    public SprintMode getMode() {
        return this.mode.getValue();
    }
    
    public static boolean canSprint() {
        return AutoSprint.mc.player != null && !AutoSprint.mc.player.isSneaking() && !AutoSprint.mc.player.isCollidedHorizontally && MovementUtil.isMoving() && (AutoSprint.mc.player.getFoodStats().getFoodLevel() > 6.0f || AutoSprint.mc.player.capabilities.allowFlying) && !AutoSprint.mc.player.isPotionActive(MobEffects.BLINDNESS);
    }
    
    public static boolean canSprintBetter() {
        return (AutoSprint.mc.gameSettings.keyBindForward.isKeyDown() || AutoSprint.mc.gameSettings.keyBindBack.isKeyDown() || AutoSprint.mc.gameSettings.keyBindLeft.isKeyDown() || AutoSprint.mc.gameSettings.keyBindRight.isKeyDown()) && AutoSprint.mc.player != null && !AutoSprint.mc.player.isSneaking() && !AutoSprint.mc.player.isCollidedHorizontally && AutoSprint.mc.player.getFoodStats().getFoodLevel() > 6.0f;
    }
}
