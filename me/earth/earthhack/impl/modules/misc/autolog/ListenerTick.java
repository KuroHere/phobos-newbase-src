//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autolog;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;

final class ListenerTick extends ModuleListener<AutoLog, TickEvent>
{
    public ListenerTick(final AutoLog module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (ListenerTick.mc.world != null && ListenerTick.mc.player != null) {
            final float health = ((AutoLog)this.module).absorption.getValue() ? EntityUtil.getHealth((EntityLivingBase)ListenerTick.mc.player) : ListenerTick.mc.player.getHealth();
            if (health <= ((AutoLog)this.module).health.getValue()) {
                final EntityPlayer player = (((AutoLog)this.module).enemy.getValue() == 100.0f) ? null : EntityUtil.getClosestEnemy();
                if (((AutoLog)this.module).enemy.getValue() == 100.0f || (player != null && player.getDistanceSqToEntity((Entity)ListenerTick.mc.player) <= MathUtil.square(((AutoLog)this.module).enemy.getValue()))) {
                    final int totems = InventoryUtil.getCount(Items.field_190929_cY);
                    if (totems <= ((AutoLog)this.module).totems.getValue()) {
                        ((AutoLog)this.module).disconnect(health, player, totems);
                    }
                }
            }
        }
    }
}
