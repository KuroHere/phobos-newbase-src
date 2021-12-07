//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import me.earth.earthhack.impl.gui.module.impl.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.thread.*;

public class ServerAutoCrystal extends SimpleSubModule<PingBypass>
{
    protected final Setting<Boolean> soundR;
    protected final SoundObserver observer;
    protected final StopWatch timer;
    protected BlockPos renderPos;
    
    public ServerAutoCrystal(final PingBypass pingBypass) {
        super(pingBypass, "S-AutoCrystal", Category.Client);
        this.soundR = this.register(new BooleanSetting("SoundRemove", false));
        this.timer = new StopWatch();
        this.register(new BooleanSetting("Place", true));
        this.register(new EnumSetting("Target", Target.Closest));
        this.register(new NumberSetting("PlaceRange", 6.0f, 0.0f, 6.0f));
        this.register(new NumberSetting("PlaceTrace", 6.0f, 0.0f, 6.0f));
        this.register(new NumberSetting("MinDamage", 6.0f, 0.0f, 20.0f));
        this.register(new NumberSetting("PlaceDelay", 0, 0, 500));
        this.register(new NumberSetting("MaxSelfPlace", 9.0f, 0.0f, 20.0f));
        this.register(new NumberSetting("FacePlace", 10.0f, 0.0f, 36.0f));
        this.register(new NumberSetting("MultiPlace", 1, 1, 5));
        this.register(new BooleanSetting("CountMin", true));
        this.register(new BooleanSetting("AntiSurround", true));
        this.register(new BooleanSetting("1.13+", false));
        this.register(new BooleanSetting("Break", true));
        this.register(new NumberSetting("BreakRange", 6.0f, 0.0f, 6.0f));
        this.register(new NumberSetting("BreakTrace", 4.5f, 0.0f, 6.0f));
        this.register(new NumberSetting("BreakDelay", 0, 0, 500));
        this.register(new NumberSetting("MaxSelfBreak", 10.0f, 0.0f, 20.0f));
        this.register(new BooleanSetting("Instant", false));
        this.register(new EnumSetting("Rotate", ACRotate.None));
        this.register(new BooleanSetting("MultiThread", false));
        this.register(new BooleanSetting("Suicide", false));
        this.register(new BooleanSetting("Stay", false));
        this.register(new NumberSetting("Range", 12.0f, 6.0f, 12.0f));
        this.register(new BooleanSetting("Override", false));
        this.register(new NumberSetting("MinFace", 2.0f, 0.1f, 4.0f));
        this.register(new BooleanSetting("AntiFriendPop", true));
        this.register(new NumberSetting("Cooldown", 500, 0, 500));
        this.register(new BooleanSetting("MultiTask", true));
        this.register(new NumberSetting("CombinedTrace", 4.5f, 0.0f, 6.0f));
        this.register(new BooleanSetting("FallBack", true));
        this.register(new NumberSetting("FB-Dmg", 2.0f, 0.0f, 6.0f));
        this.register(new BooleanSetting("Tick", true));
        this.register(new BooleanSetting("SetDead", false));
        this.register(new NumberSetting("ThreadDelay", 30, 0, 100));
        this.register(new BooleanSetting("Post-Tick", false));
        this.register(new BooleanSetting("Gameloop", false));
        this.register(new BooleanSetting("Packet", true));
        this.listeners.add(new ListenerRotations(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerRenderPos(this));
        this.listeners.add(new ListenerTick(this));
        this.observer = new SimpleSoundObserver(this.soundR::getValue);
        this.setData(new ServerAutoCrystalData(this));
    }
    
    @Override
    protected void onEnable() {
        Managers.SET_DEAD.addObserver(this.observer);
    }
    
    @Override
    protected void onDisable() {
        Managers.SET_DEAD.removeObserver(this.observer);
    }
    
    protected void onTick() {
        if (!this.getParent().isEnabled()) {
            return;
        }
        if (this.timer.passed(1000L) || ServerAutoCrystal.mc.player == null || !InventoryUtil.isHolding(Items.END_CRYSTAL)) {
            this.renderPos = null;
            this.timer.reset();
        }
        if (ServerAutoCrystal.mc.player != null && InventoryUtil.isHolding(Items.END_CRYSTAL) && !InventoryUtil.isHoldingServer(Items.END_CRYSTAL) && this.getParent().isEnabled()) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, InventoryUtil::syncItem);
        }
    }
}
