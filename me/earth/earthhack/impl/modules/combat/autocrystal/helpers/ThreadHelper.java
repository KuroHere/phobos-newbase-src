//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.impl.event.events.network.*;

public class ThreadHelper implements Globals
{
    private final StopWatch threadTimer;
    private final Setting<Boolean> multiThread;
    private final Setting<Integer> threadDelay;
    private final Setting<RotationThread> rotationThread;
    private final Setting<ACRotate> rotate;
    private final AutoCrystal module;
    private volatile AbstractCalculation<?> currentCalc;
    
    public ThreadHelper(final AutoCrystal module, final Setting<Boolean> multiThread, final Setting<Integer> threadDelay, final Setting<RotationThread> rotationThread, final Setting<ACRotate> rotate) {
        this.threadTimer = new StopWatch();
        this.module = module;
        this.multiThread = multiThread;
        this.threadDelay = threadDelay;
        this.rotationThread = rotationThread;
        this.rotate = rotate;
    }
    
    public synchronized void start(final AbstractCalculation<?> calculation, final boolean multiThread) {
        if (!this.module.isPingBypass() && this.threadTimer.passed(this.threadDelay.getValue()) && (this.currentCalc == null || this.currentCalc.isFinished())) {
            this.execute(this.currentCalc = calculation, multiThread);
        }
    }
    
    public synchronized void startThread(final BlockPos... blackList) {
        if (ThreadHelper.mc.world == null || ThreadHelper.mc.player == null || this.module.isPingBypass() || !this.threadTimer.passed(this.threadDelay.getValue()) || (this.currentCalc != null && !this.currentCalc.isFinished())) {
            return;
        }
        if (ThreadHelper.mc.isCallingFromMinecraftThread()) {
            this.startThread(new ArrayList<Entity>(ThreadHelper.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(ThreadHelper.mc.world.playerEntities), blackList);
        }
        else {
            this.startThread(Managers.ENTITIES.getEntities(), Managers.ENTITIES.getPlayers(), blackList);
        }
    }
    
    public synchronized void startThread(final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        if (ThreadHelper.mc.world == null || ThreadHelper.mc.player == null || this.module.isPingBypass() || !this.threadTimer.passed(this.threadDelay.getValue()) || (this.currentCalc != null && !this.currentCalc.isFinished())) {
            return;
        }
        if (ThreadHelper.mc.isCallingFromMinecraftThread()) {
            this.startThread(new ArrayList<Entity>(ThreadHelper.mc.world.loadedEntityList), new ArrayList<EntityPlayer>(ThreadHelper.mc.world.playerEntities), breakOnly, noBreak, blackList);
        }
        else {
            this.startThread(Managers.ENTITIES.getEntities(), Managers.ENTITIES.getPlayers(), breakOnly, noBreak, blackList);
        }
    }
    
    private void startThread(final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        this.execute(this.currentCalc = new Calculation(this.module, entities, players, breakOnly, noBreak, blackList), this.multiThread.getValue());
    }
    
    private void startThread(final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        this.execute(this.currentCalc = new Calculation(this.module, entities, players, blackList), this.multiThread.getValue());
    }
    
    private void execute(final AbstractCalculation<?> calculation, final boolean multiThread) {
        if (multiThread) {
            Managers.THREAD.submitRunnable(calculation);
            this.threadTimer.reset();
        }
        else {
            this.threadTimer.reset();
            calculation.run();
        }
    }
    
    public void schedulePacket(final PacketEvent.Receive<?> event) {
        if (this.multiThread.getValue() && (this.rotate.getValue() == ACRotate.None || this.rotationThread.getValue() != RotationThread.Predict)) {
            event.addPostEvent(() -> rec$.startThread(new BlockPos[0]));
        }
    }
    
    public AbstractCalculation<?> getCurrentCalc() {
        return this.currentCalc;
    }
    
    public void reset() {
        this.currentCalc = null;
    }
}
