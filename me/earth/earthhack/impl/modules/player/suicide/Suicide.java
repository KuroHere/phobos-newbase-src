//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.suicide;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import com.google.common.collect.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;

public class Suicide extends DisablingModule
{
    protected final Setting<SuicideMode> mode;
    protected final Setting<Boolean> armor;
    protected final Setting<Boolean> offhand;
    protected final Setting<Boolean> throwAwayTotem;
    protected final Setting<Integer> throwDelay;
    protected final Setting<Boolean> ask;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> newVerEntities;
    protected final Setting<Float> breakRange;
    protected final Setting<Float> placeRange;
    protected final Setting<Integer> placeDelay;
    protected final Setting<Float> trace;
    protected final Setting<Integer> breakDelay;
    protected final Setting<Boolean> instant;
    protected final Setting<Float> minInstant;
    protected final Setting<Boolean> instantCalc;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> silent;
    protected final Set<BlockPos> placed;
    protected final StopWatch placeTimer;
    protected final StopWatch breakTimer;
    protected final StopWatch timer;
    protected boolean displaying;
    protected Entity crystal;
    protected RayTraceResult result;
    protected BlockPos pos;
    
    public Suicide() {
        super("Suicide", Category.Player);
        this.mode = this.register(new EnumSetting("Mode", SuicideMode.Command));
        this.armor = this.register(new BooleanSetting("Armor", true));
        this.offhand = this.register(new BooleanSetting("Offhand", true));
        this.throwAwayTotem = this.register(new BooleanSetting("ThrowAwayTotem", true));
        this.throwDelay = this.register(new NumberSetting("Throw-Delay", 500, 0, 1000));
        this.ask = this.register(new BooleanSetting("Ask", true));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.breakRange = this.register(new NumberSetting("BreakRange", 6.0f, 0.0f, 6.0f));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 5.25f, 0.0f, 6.0f));
        this.placeDelay = this.register(new NumberSetting("PlaceDelay", 50, 0, 500));
        this.trace = this.register(new NumberSetting("RayTrace", 3.0f, 0.0f, 6.0f));
        this.breakDelay = this.register(new NumberSetting("BreakDelay", 50, 0, 500));
        this.instant = this.register(new BooleanSetting("Instant", true));
        this.minInstant = this.register(new NumberSetting("Min-Instant", 6.0f, 0.0f, 36.0f));
        this.instantCalc = this.register(new BooleanSetting("Instant-Calc", false));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.silent = this.register(new BooleanSetting("Silent", false));
        this.placed = Sets.newConcurrentHashSet();
        this.placeTimer = new StopWatch();
        this.breakTimer = new StopWatch();
        this.timer = new StopWatch();
        final SimpleData data = new SimpleData(this, "Kills you.");
        data.register(this.mode, "-Command sends a /kill command\n-AutoCrystal makes the AutoCrystal target you.");
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        this.pos = null;
        this.placed.clear();
        if (this.ask.getValue()) {
            this.displaying = true;
            final GuiScreen current = Suicide.mc.currentScreen;
            Suicide.mc.displayGuiScreen((GuiScreen)new YesNoNonPausing((r, id) -> {
                Suicide.mc.displayGuiScreen(current);
                if (r) {
                    this.displaying = false;
                }
                else {
                    this.disable();
                }
            }, "§cDo you want to kill yourself? (recommended)", "If you don't want to get asked again, turn off the \"Ask\" Setting.", 1337));
            return;
        }
        this.displaying = false;
        if (this.mode.getValue() == SuicideMode.Command) {
            NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketChatMessage("/kill"));
            this.disable();
        }
    }
    
    public boolean shouldTakeOffArmor() {
        return this.isEnabled() && !this.displaying && this.mode.getValue() != SuicideMode.Command && this.armor.getValue();
    }
    
    public boolean deactivateOffhand() {
        return this.isEnabled() && !this.displaying && this.mode.getValue() != SuicideMode.Command && this.offhand.getValue();
    }
}
