//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoeat;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.settings.*;

public class AutoEat extends Module
{
    protected final Setting<Float> hunger;
    protected final Setting<Boolean> health;
    protected final Setting<Float> enemyRange;
    protected final Setting<Float> safeHealth;
    protected final Setting<Float> unsafeHealth;
    protected final Setting<Boolean> calcWithAbsorption;
    protected final Setting<Boolean> absorption;
    protected final Setting<Float> absorptionAmount;
    protected final Setting<Boolean> always;
    protected boolean isEating;
    protected boolean server;
    protected boolean force;
    protected int lastSlot;
    
    public AutoEat() {
        super("AutoEat", Category.Misc);
        this.hunger = this.register(new NumberSetting("Hunger", 19.0f, 0.1f, 19.0f));
        this.health = this.register(new BooleanSetting("Health", false));
        this.enemyRange = this.register(new NumberSetting("Enemy-Range", 0.0f, 0.0f, 24.0f));
        this.safeHealth = this.register(new NumberSetting("Safe-Health", 19.0f, 0.1f, 36.0f));
        this.unsafeHealth = this.register(new NumberSetting("Unsafe-Health", 19.0f, 0.1f, 36.0f));
        this.calcWithAbsorption = this.register(new BooleanSetting("CalcWithAbsorption", true));
        this.absorption = this.register(new BooleanSetting("Absorption", false));
        this.absorptionAmount = this.register(new NumberSetting("AbsorptionAmount", 0.0f, 0.0f, 16.0f));
        this.always = this.register(new BooleanSetting("Always", false));
        this.listeners.add(new ListenerTick(this));
    }
    
    @Override
    protected void onEnable() {
        this.force = false;
        this.server = false;
        this.lastSlot = -1;
        this.isEating = false;
    }
    
    @Override
    protected void onDisable() {
        this.reset();
    }
    
    public void reset() {
        this.force = false;
        this.server = false;
        this.lastSlot = -1;
        this.isEating = false;
        KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), KeyBoardUtil.isKeyDown(AutoEat.mc.gameSettings.keyBindUseItem));
    }
    
    public boolean isEating() {
        return this.isEnabled() && this.isEating;
    }
    
    public void setServer(final boolean server) {
        this.server = server;
    }
}
