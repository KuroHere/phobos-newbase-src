//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.modes;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

public enum Pop implements Globals
{
    None {
        @Override
        public boolean shouldPop(final float damage, final int popTime) {
            return damage < EntityUtil.getHealth((EntityLivingBase)Pop$1.mc.player) + 1.0;
        }
    }, 
    Time {
        @Override
        public boolean shouldPop(final float damage, final int popTime) {
            return Pop$2.None.shouldPop(damage, popTime) || (Pop$2.mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY && Managers.COMBAT.lastPop((Entity)Pop$2.mc.player) < popTime);
        }
    }, 
    Always {
        @Override
        public boolean shouldPop(final float damage, final int popTime) {
            return Pop$3.None.shouldPop(damage, popTime) || Pop$3.mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY;
        }
    };
    
    public abstract boolean shouldPop(final float p0, final int p1);
}
