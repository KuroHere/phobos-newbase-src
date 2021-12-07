// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import java.util.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;

public class CalculationMotion extends AbstractCalculation<CrystalDataMotion>
{
    public CalculationMotion(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players) {
        super(module, entities, players, new BlockPos[0]);
    }
    
    @Override
    protected IBreakHelper<CrystalDataMotion> getBreakHelper() {
        return this.module.breakHelperMotion;
    }
    
    @Override
    protected boolean evaluate(final BreakData<CrystalDataMotion> breakData) {
        boolean slowReset = false;
        BreakValidity validity;
        if (this.breakData.getAntiTotem() != null && (validity = HelperUtil.isValid(this.module, this.breakData.getAntiTotem())) != BreakValidity.INVALID) {
            this.attack(this.breakData.getAntiTotem(), validity);
            this.module.breakTimer.reset(this.module.breakDelay.getValue());
            this.module.antiTotemHelper.setTarget(null);
            this.module.antiTotemHelper.setTargetPos(null);
        }
        else {
            final int packets = this.module.rotate.getValue().noRotate(ACRotate.Break) ? this.module.packets.getValue() : 1;
            CrystalDataMotion firstRotation = null;
            final List<CrystalDataMotion> valids = new ArrayList<CrystalDataMotion>(packets);
            for (final CrystalDataMotion data : this.breakData.getData()) {
                if (data.getTiming() == CrystalDataMotion.Timing.NONE) {
                    continue;
                }
                validity = this.isValid(this.module, data);
                if (validity == BreakValidity.VALID && valids.size() < packets) {
                    valids.add(data);
                }
                else {
                    if (validity != BreakValidity.ROTATIONS || (data.getTiming() != CrystalDataMotion.Timing.BOTH && data.getTiming() != CrystalDataMotion.Timing.POST) || firstRotation != null) {
                        continue;
                    }
                    firstRotation = data;
                }
            }
            final int slowDelay = this.module.slowBreakDelay.getValue();
            final float slow = this.module.slowBreakDamage.getValue();
            if (valids.isEmpty()) {
                if (firstRotation != null && (this.module.shouldDanger() || !(slowReset = (firstRotation.getDamage() <= slow)) || this.module.breakTimer.passed(slowDelay))) {
                    this.attack(firstRotation.getCrystal(), BreakValidity.ROTATIONS);
                }
            }
            else {
                slowReset = !this.module.shouldDanger();
                for (final CrystalDataMotion v : valids) {
                    final boolean high = v.getDamage() > this.module.slowBreakDamage.getValue();
                    if (high || this.module.breakTimer.passed(this.module.slowBreakDelay.getValue())) {
                        slowReset = (slowReset && !high);
                        if (v.getTiming() == CrystalDataMotion.Timing.POST || (v.getTiming() == CrystalDataMotion.Timing.BOTH && v.getPostSelf() < v.getSelfDmg())) {
                            this.attackPost(v.getCrystal());
                        }
                        else {
                            this.attack(v.getCrystal(), BreakValidity.VALID);
                        }
                    }
                }
            }
        }
        if (this.attacking) {
            this.module.breakTimer.reset(slowReset ? ((long)this.module.slowBreakDelay.getValue()) : ((long)this.module.breakDelay.getValue()));
        }
        return this.rotating && !this.module.rotate.getValue().noRotate(ACRotate.Place);
    }
    
    protected void attackPost(final Entity entity) {
        this.attacking = true;
        this.scheduling = true;
        this.rotating = !this.module.rotate.getValue().noRotate(ACRotate.Break);
        final MutableWrapper<Boolean> attacked = new MutableWrapper<Boolean>(false);
        final Runnable post = this.module.rotationHelper.post(entity, attacked);
        this.module.post.add(post);
    }
    
    private BreakValidity isValid(final AutoCrystal module, final CrystalDataMotion dataMotion) {
        final Entity crystal = dataMotion.getCrystal();
        if (module.existed.getValue() != 0 && System.currentTimeMillis() - ((IEntity)crystal).getTimeStamp() + (module.pingExisted.getValue() ? (ServerUtil.getPingNoPingSpoof() / 2.0) : 0.0) < module.existed.getValue()) {
            return BreakValidity.INVALID;
        }
        if (module.rotate.getValue().noRotate(ACRotate.Break) || (RotationUtil.isLegit(crystal, crystal) && module.positionHistoryHelper.arePreviousRotationsLegit(crystal, module.rotationTicks.getValue(), true))) {
            return BreakValidity.VALID;
        }
        return BreakValidity.ROTATIONS;
    }
}
