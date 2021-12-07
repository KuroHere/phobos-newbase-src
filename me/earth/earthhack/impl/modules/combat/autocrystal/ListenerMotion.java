//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;

final class ListenerMotion extends ModuleListener<AutoCrystal, MotionUpdateEvent>
{
    private final MouseFilter pitchMouseFilter;
    private final MouseFilter yawMouseFilter;
    
    public ListenerMotion(final AutoCrystal module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 1500);
        this.pitchMouseFilter = new MouseFilter();
        this.yawMouseFilter = new MouseFilter();
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (AbstractCalculation.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false) || AbstractCalculation.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
            return;
        }
        if (event.getStage() == Stage.PRE) {
            if (!((AutoCrystal)this.module).multiThread.getValue() && ((AutoCrystal)this.module).motionCalc.getValue() && (Managers.POSITION.getX() != event.getX() || Managers.POSITION.getY() != event.getY() || Managers.POSITION.getZ() != event.getZ())) {
                final CalculationMotion calc = new CalculationMotion((AutoCrystal)this.module, ListenerMotion.mc.world.loadedEntityList, ListenerMotion.mc.world.playerEntities);
                ((AutoCrystal)this.module).threadHelper.start(calc, false);
            }
            else if (((AutoCrystal)this.module).motionThread.getValue()) {
                ((AutoCrystal)this.module).threadHelper.startThread(new BlockPos[0]);
            }
            final AbstractCalculation<?> current = ((AutoCrystal)this.module).threadHelper.getCurrentCalc();
            if (current != null && !current.isFinished() && ((AutoCrystal)this.module).rotate.getValue() != ACRotate.None && ((AutoCrystal)this.module).rotationThread.getValue() == RotationThread.Wait) {
                synchronized ((AutoCrystal)this.module) {
                    try {
                        this.module.wait(((AutoCrystal)this.module).timeOut.getValue());
                    }
                    catch (InterruptedException e) {
                        Earthhack.getLogger().warn("Minecraft Main-Thread interrupted!");
                        Thread.currentThread().interrupt();
                    }
                }
            }
            final RotationFunction rotation = ((AutoCrystal)this.module).rotation;
            if (rotation != null) {
                ((AutoCrystal)this.module).isSpoofing = true;
                final float[] rotations = rotation.apply(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch());
                if (((AutoCrystal)this.module).rotateMode.getValue() == RotateMode.Smooth) {
                    final float yaw = this.yawMouseFilter.smooth(rotations[0] + MathUtil.getRandomInRange(-1.0f, 5.0f), (float)((AutoCrystal)this.module).smoothSpeed.getValue());
                    final float pitch = this.pitchMouseFilter.smooth(rotations[1] + MathUtil.getRandomInRange(-1.2f, 3.5f), (float)((AutoCrystal)this.module).smoothSpeed.getValue());
                    event.setYaw(yaw);
                    event.setPitch(pitch);
                }
                else {
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                }
            }
        }
        else {
            ((AutoCrystal)this.module).motionID.incrementAndGet();
            synchronized (((AutoCrystal)this.module).post) {
                ((AutoCrystal)this.module).runPost();
            }
            ((AutoCrystal)this.module).isSpoofing = false;
        }
    }
    
    @Override
    public int getPriority() {
        return ((AutoCrystal)this.module).priority.getValue();
    }
}
