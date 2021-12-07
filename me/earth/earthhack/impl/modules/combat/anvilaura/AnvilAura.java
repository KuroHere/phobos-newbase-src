//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.anvilaura;

import me.earth.earthhack.api.setting.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.modes.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;

public class AnvilAura extends ObbyListenerModule<ListenerAnvilAura>
{
    protected final Setting<AnvilMode> mode;
    protected final Setting<Integer> fastDelay;
    protected final Setting<Double> range;
    protected final Setting<Boolean> holdingAnvil;
    protected final Setting<Integer> yHeight;
    protected final Setting<Boolean> trap;
    protected final Setting<Boolean> mineESP;
    protected final Setting<Boolean> renderBest;
    protected final Setting<Boolean> checkFalling;
    protected final Setting<Boolean> pressureFalling;
    protected final Setting<Integer> helpingBlocks;
    protected final Setting<Integer> trapHelping;
    protected final Setting<Double> mineRange;
    public final ColorSetting box;
    public final ColorSetting outline;
    public final Setting<Float> lineWidth;
    protected final Setting<Integer> mineTime;
    protected final Setting<Boolean> confirmMine;
    protected final Setting<Boolean> pressurePass;
    protected final Setting<Boolean> crystal;
    protected final Setting<Integer> crystalDelay;
    protected List<AxisAlignedBB> renderBBs;
    protected final AtomicBoolean awaiting;
    protected final StopWatch renderTimer;
    protected final StopWatch mineTimer;
    protected final StopWatch awaitTimer;
    protected final StopWatch crystalTimer;
    protected AnvilStage stage;
    protected AnvilResult currentResult;
    protected EntityPlayer target;
    protected AxisAlignedBB mineBB;
    protected Runnable action;
    protected int pressureSlot;
    protected int crystalSlot;
    protected int pickSlot;
    protected int obbySlot;
    protected BlockPos awaitPos;
    protected BlockPos minePos;
    protected EnumFacing mineFacing;
    
    public AnvilAura() {
        super("AnvilAura", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", AnvilMode.Mine));
        this.fastDelay = this.register(new NumberSetting("Fast-Delay", 0, 0, 1000));
        this.range = this.register(new NumberSetting("Range", 5.25, 0.1, 6.0));
        this.holdingAnvil = this.register(new BooleanSetting("HoldingAnvil", false));
        this.yHeight = this.register(new NumberSetting("Y-Offset", 3, 0, 256));
        this.trap = this.register(new BooleanSetting("Trap", true));
        this.mineESP = this.register(new BooleanSetting("Mine-ESP", true));
        this.renderBest = this.register(new BooleanSetting("RenderBest", false));
        this.checkFalling = this.register(new BooleanSetting("CheckFalling", true));
        this.pressureFalling = this.register(new BooleanSetting("PressureFalling", false));
        this.helpingBlocks = this.register(new NumberSetting("HelpingBlocks", 6, 0, 12));
        this.trapHelping = this.register(new NumberSetting("Trap-Helping", 2, 0, 3));
        this.mineRange = this.register(new NumberSetting("Mine-Range", 6.0, 0.1, 10.0));
        this.box = this.register(new ColorSetting("Box", new Color(100, 100, 100, 155)));
        this.outline = this.register(new ColorSetting("Outline", new Color(0, 0, 0, 0)));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 1.5f, 0.0f, 10.0f));
        this.mineTime = this.register(new NumberSetting("Mine-Time", 250, 0, 1000));
        this.confirmMine = this.register(new BooleanSetting("ConfirmMine", true));
        this.pressurePass = this.register(new BooleanSetting("PressurePass", false));
        this.crystal = this.register(new BooleanSetting("Crystal", false));
        this.crystalDelay = this.register(new NumberSetting("CrystalDelay", 500, 0, 1000));
        this.renderBBs = Collections.emptyList();
        this.awaiting = new AtomicBoolean();
        this.renderTimer = new StopWatch();
        this.mineTimer = new StopWatch();
        this.awaitTimer = new StopWatch();
        this.crystalTimer = new StopWatch();
        this.stage = AnvilStage.ANVIL;
        this.listeners.clear();
        this.listeners.add(this.listener);
        this.listeners.add(new ListenerRender(this));
        super.delay.setValue(500);
    }
    
    @Override
    protected boolean checkNull() {
        this.renderBBs = Collections.emptyList();
        this.mineBB = null;
        this.packets.clear();
        this.blocksPlaced = 0;
        if (AnvilAura.mc.player == null || AnvilAura.mc.world == null) {
            if (!this.holdingAnvil.getValue() && this.mode.getValue() != AnvilMode.Render) {
                this.disable();
            }
            return false;
        }
        return true;
    }
    
    @Override
    protected void onDisable() {
        super.onDisable();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.renderTimer.passed(600L)) {
            this.currentResult = null;
            this.target = null;
        }
        return (this.target != null) ? this.target.getName() : null;
    }
    
    @Override
    public boolean execute() {
        switch (this.stage) {
            case OBSIDIAN: {
                if (this.obbySlot == -1) {
                    return false;
                }
                this.slot = this.obbySlot;
                break;
            }
            case PRESSURE: {
                if (this.pressureSlot == -1) {
                    return false;
                }
                this.slot = this.pressureSlot;
                break;
            }
            case CRYSTAL: {
                if (this.crystalSlot == -1) {
                    return false;
                }
                this.slot = this.crystalSlot;
                break;
            }
        }
        if (this.action != null) {
            this.action.run();
            return true;
        }
        return super.execute();
    }
    
    @Override
    protected ListenerAnvilAura createListener() {
        return new ListenerAnvilAura(this, 500);
    }
    
    @Override
    protected boolean entityCheckSimple(final BlockPos pos) {
        return this.stage == AnvilStage.PRESSURE || super.entityCheckSimple(pos);
    }
    
    @Override
    public boolean entityCheck(final BlockPos pos) {
        return this.stage == AnvilStage.PRESSURE || super.entityCheck(pos);
    }
    
    @Override
    protected boolean quickEntityCheck(final BlockPos pos) {
        return this.stage != AnvilStage.PRESSURE && super.quickEntityCheck(pos);
    }
    
    @Override
    public int getDelay() {
        final AnvilResult r = this.currentResult;
        if (r != null && r.hasSpecialPressure()) {
            return this.fastDelay.getValue();
        }
        return super.getDelay();
    }
    
    public void setCurrentResult(final AnvilResult result) {
        this.renderTimer.reset();
        this.currentResult = result;
        this.target = result.getPlayer();
    }
    
    public boolean isMining() {
        return this.mode.getValue() == AnvilMode.Mine && (!this.holdingAnvil.getValue() || InventoryUtil.isHolding(Blocks.ANVIL));
    }
}
