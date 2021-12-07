//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.holefiller;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.managers.thread.holes.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

final class ListenerObby extends ObbyListener<HoleFiller>
{
    private boolean wasRunning;
    
    public ListenerObby(final HoleFiller module) {
        super(module, 10);
    }
    
    @Override
    public void onModuleToggle() {
        super.onModuleToggle();
        this.wasRunning = false;
    }
    
    @Override
    public void invoke(final MotionUpdateEvent event) {
        ((HoleFiller)this.module).target = null;
        if ((this.wasRunning || !((HoleFiller)this.module).requireTarget.getValue()) && ((HoleFiller)this.module).disable.getValue() != 0 && ((HoleFiller)this.module).disableTimer.passed(((HoleFiller)this.module).disable.getValue())) {
            ((HoleFiller)this.module).disable();
            return;
        }
        if (((HoleFiller)this.module).requireTarget.getValue()) {
            ((HoleFiller)this.module).target = EntityUtil.getClosestEnemy();
            if (((HoleFiller)this.module).target == null || ((HoleFiller)this.module).target.getDistanceSqToEntity((Entity)ListenerObby.mc.player) > MathUtil.square(((HoleFiller)this.module).targetRange.getValue()) || (((HoleFiller)this.module).waitForHoleLeave.getValue() && (HoleUtil.is1x1(PositionUtil.getPosition((Entity)((HoleFiller)this.module).target))[0] || HoleUtil.is2x1(PositionUtil.getPosition((Entity)((HoleFiller)this.module).target), false) || HoleUtil.is2x1(PositionUtil.getPosition((Entity)((HoleFiller)this.module).target), false)))) {
                ((HoleFiller)this.module).waiting = true;
                return;
            }
        }
        ((HoleFiller)this.module).waiting = false;
        if (!this.wasRunning) {
            ((HoleFiller)this.module).disableTimer.reset();
        }
        this.wasRunning = true;
        super.invoke(event);
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        if (((HoleFiller)this.module).calcTimer.passed(((HoleFiller)this.module).calcDelay.getValue())) {
            final HoleRunnable runnable = new HoleRunnable((IHoleManager)this.module, (HoleObserver)this.module);
            runnable.run();
            ((HoleFiller)this.module).calcTimer.reset();
        }
        final List<BlockPos> targets = new ArrayList<BlockPos>(((HoleFiller)this.module).safes.size() + ((HoleFiller)this.module).unsafes.size() + ((HoleFiller)this.module).longs.size() + ((HoleFiller)this.module).bigs.size());
        targets.addAll(((HoleFiller)this.module).safes);
        targets.addAll(((HoleFiller)this.module).unsafes);
        if (((HoleFiller)this.module).longHoles.getValue()) {
            targets.addAll(((HoleFiller)this.module).longs);
        }
        if (((HoleFiller)this.module).bigHoles.getValue()) {
            targets.addAll(((HoleFiller)this.module).bigs);
        }
        final BlockPos playerPos = PositionUtil.getPosition();
        EntityPlayer p = null;
        if (!HoleUtil.isHole(playerPos, false)[0] && !HoleUtil.is2x1(playerPos) && !HoleUtil.is2x2(playerPos) && (!((HoleFiller)this.module).safety.getValue() || !Managers.SAFETY.isSafe())) {
            p = RotationUtil.getRotationPlayer();
            final Vec3d v = p.getPositionVector().addVector(p.motionX, p.motionY, p.motionZ);
            targets.removeIf(pos -> pos.distanceSq(v.xCoord, v.yCoord, v.zCoord) < MathUtil.square(((HoleFiller)this.module).minSelf.getValue()));
            targets.sort(Comparator.comparingDouble(pos -> -BlockUtil.getDistanceSq(pos)));
        }
        ((HoleFiller)this.module).target = EntityUtil.getClosestEnemy();
        if (((HoleFiller)this.module).target != null) {
            targets.removeIf(p -> BlockUtil.getDistanceSq((Entity)((HoleFiller)this.module).target, p) > MathUtil.square(((HoleFiller)this.module).targetDistance.getValue()));
            targets.sort(Comparator.comparingDouble(p -> BlockUtil.getDistanceSq((Entity)((HoleFiller)this.module).target, p)));
        }
        result.setTargets(targets);
        return result;
    }
}
