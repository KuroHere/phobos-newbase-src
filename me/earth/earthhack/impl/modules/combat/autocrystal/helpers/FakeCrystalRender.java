//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;

public class FakeCrystalRender implements Globals
{
    private final List<EntityEnderCrystal> crystals;
    private final Setting<Integer> simulate;
    
    public FakeCrystalRender(final Setting<Integer> simulate) {
        this.crystals = new ArrayList<EntityEnderCrystal>();
        this.simulate = simulate;
    }
    
    public void addFakeCrystal(final EntityEnderCrystal crystal) {
        crystal.setShowBottom(false);
        FakeCrystalRender.mc.addScheduledTask(() -> {
            if (FakeCrystalRender.mc.world != null) {
                FakeCrystalRender.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, crystal.getEntityBoundingBox()).iterator();
                final Iterator iterator;
                if (iterator.hasNext()) {
                    final EntityEnderCrystal entity = iterator.next();
                    crystal.innerRotation = entity.innerRotation;
                }
            }
            this.crystals.add(crystal);
        });
    }
    
    public void onSpawn(final EntityEnderCrystal crystal) {
        final Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
        while (itr.hasNext()) {
            final EntityEnderCrystal fake = itr.next();
            if (fake.getEntityBoundingBox().intersectsWith(crystal.getEntityBoundingBox())) {
                crystal.innerRotation = fake.innerRotation;
                itr.remove();
            }
        }
    }
    
    public void tick() {
        if (this.simulate.getValue() == 0) {
            this.crystals.clear();
            return;
        }
        final Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
        while (itr.hasNext()) {
            final EntityEnderCrystal crystal = itr.next();
            crystal.onUpdate();
            if (++crystal.ticksExisted >= this.simulate.getValue()) {
                crystal.setDead();
                itr.remove();
            }
        }
    }
    
    public void render(final float partialTicks) {
        final RenderManager manager = FakeCrystalRender.mc.getRenderManager();
        for (final EntityEnderCrystal crystal : this.crystals) {
            manager.renderEntityStatic((Entity)crystal, partialTicks, false);
        }
    }
    
    public void clear() {
        this.crystals.clear();
    }
}
