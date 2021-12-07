//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.legswitch.modes.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;

final class ListenerMotion extends ModuleListener<LegSwitch, MotionUpdateEvent>
{
    public ListenerMotion(final LegSwitch module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (!((LegSwitch)this.module).isStackValid(ListenerMotion.mc.player.getHeldItemOffhand()) && !((LegSwitch)this.module).isStackValid(ListenerMotion.mc.player.getHeldItemMainhand())) {
            ((LegSwitch)this.module).active = false;
            return;
        }
        if (!InventoryUtil.isHolding(Items.END_CRYSTAL) && (((LegSwitch)this.module).autoSwitch.getValue() == LegAutoSwitch.None || InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]) == -1)) {
            ((LegSwitch)this.module).active = false;
            return;
        }
        if (event.getStage() == Stage.PRE) {
            if (((LegSwitch)this.module).constellation == null || !((LegSwitch)this.module).constellation.isValid((LegSwitch)this.module, (EntityPlayer)ListenerMotion.mc.player, (IBlockAccess)ListenerMotion.mc.world)) {
                ((LegSwitch)this.module).constellation = ConstellationFactory.create((LegSwitch)this.module, ListenerMotion.mc.world.playerEntities);
                if (((LegSwitch)this.module).constellation != null && !((LegSwitch)this.module).obsidian.getValue() && (((LegSwitch)this.module).constellation.firstNeedsObby || ((LegSwitch)this.module).constellation.secondNeedsObby)) {
                    ((LegSwitch)this.module).constellation = null;
                }
            }
            if (((LegSwitch)this.module).constellation == null) {
                ((LegSwitch)this.module).active = false;
                return;
            }
            ((LegSwitch)this.module).active = true;
            ((LegSwitch)this.module).prepare();
            if (((LegSwitch)this.module).rotations != null) {
                event.setYaw(((LegSwitch)this.module).rotations[0]);
                event.setPitch(((LegSwitch)this.module).rotations[1]);
            }
        }
        else {
            ((LegSwitch)this.module).execute();
        }
    }
}
