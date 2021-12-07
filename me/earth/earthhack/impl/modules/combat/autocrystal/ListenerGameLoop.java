//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;

final class ListenerGameLoop extends ModuleListener<AutoCrystal, GameLoopEvent>
{
    public ListenerGameLoop(final AutoCrystal module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        ((AutoCrystal)this.module).rotationCanceller.onGameLoop();
        if (((AutoCrystal)this.module).multiThread.getValue() && ((AutoCrystal)this.module).rotate.getValue() != ACRotate.None && ((AutoCrystal)this.module).rotationThread.getValue() == RotationThread.Predict && ListenerGameLoop.mc.getRenderPartialTicks() >= ((AutoCrystal)this.module).partial.getValue()) {
            ((AutoCrystal)this.module).threadHelper.startThread(new BlockPos[0]);
        }
        if (((AutoCrystal)this.module).multiThread.getValue() && ((AutoCrystal)this.module).rotate.getValue() == ACRotate.None && ((AutoCrystal)this.module).serverThread.getValue() && ListenerGameLoop.mc.world != null && ListenerGameLoop.mc.player != null) {
            if (Managers.TICK.valid(Managers.TICK.getTickTimeAdjusted(), Managers.TICK.normalize(Managers.TICK.getSpawnTime() - ((AutoCrystal)this.module).tickThreshold.getValue()), Managers.TICK.normalize(Managers.TICK.getSpawnTime() - ((AutoCrystal)this.module).preSpawn.getValue()))) {
                if (!((AutoCrystal)this.module).earlyFeetThread.getValue()) {
                    ((AutoCrystal)this.module).threadHelper.startThread(new BlockPos[0]);
                }
                else if (((AutoCrystal)this.module).lateBreakThread.getValue()) {
                    ((AutoCrystal)this.module).threadHelper.startThread(true, false, new BlockPos[0]);
                }
            }
            else if (EntityUtil.getClosestEnemy() != null && BlockUtil.isSemiSafe(EntityUtil.getClosestEnemy(), true, ((AutoCrystal)this.module).newVer.getValue()) && BlockUtil.canBeFeetPlaced(EntityUtil.getClosestEnemy(), true, ((AutoCrystal)this.module).newVer.getValue()) && ((AutoCrystal)this.module).earlyFeetThread.getValue() && Managers.TICK.valid(Managers.TICK.getTickTimeAdjusted(), 0, ((AutoCrystal)this.module).maxEarlyThread.getValue())) {
                ((AutoCrystal)this.module).threadHelper.startThread(false, true, new BlockPos[0]);
            }
        }
    }
}
