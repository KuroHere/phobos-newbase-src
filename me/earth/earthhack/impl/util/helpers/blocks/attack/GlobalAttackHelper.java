//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.attack;

import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.api.event.bus.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.network.*;
import java.util.*;

public class GlobalAttackHelper implements AttackHelper
{
    private static final GlobalAttackHelper INSTANCE;
    private boolean noCrystal;
    private boolean attacked;
    private boolean invalid;
    
    private GlobalAttackHelper() {
        Bus.EVENT_BUS.register(new EventListener<MotionUpdateEvent>(MotionUpdateEvent.class, Integer.MIN_VALUE) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
                if (event.getStage() == Stage.POST) {
                    GlobalAttackHelper.this.attacked = false;
                    GlobalAttackHelper.this.noCrystal = false;
                    GlobalAttackHelper.this.invalid = false;
                }
            }
        });
    }
    
    public static GlobalAttackHelper getInstance() {
        return GlobalAttackHelper.INSTANCE;
    }
    
    @Override
    public boolean attackAny(final List<Entity> entities, final AttackingModule module) {
        if (this.invalid) {
            return false;
        }
        if (this.attacked || this.noCrystal) {
            return true;
        }
        Entity best = null;
        boolean noCrystal = true;
        float minDamage = Float.MAX_VALUE;
        for (final Entity entity : entities) {
            if (entity instanceof EntityEnderCrystal && !EntityUtil.isDead(entity) && entity.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer()) < 36.0) {
                noCrystal = false;
                if (!HelperUtil.valid(entity, module.getRange(), module.getTrace())) {
                    continue;
                }
                final float damage = DamageUtil.calculate(entity);
                if (!module.getPop().shouldPop(damage, module.getPopTime()) || damage >= minDamage) {
                    continue;
                }
                best = entity;
                minDamage = damage;
            }
        }
        if (best != null) {
            PacketUtil.attack(best);
            return this.attacked = true;
        }
        if (!noCrystal) {
            this.invalid = true;
            return false;
        }
        return true;
    }
    
    public void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }
    
    static {
        INSTANCE = new GlobalAttackHelper();
    }
}
