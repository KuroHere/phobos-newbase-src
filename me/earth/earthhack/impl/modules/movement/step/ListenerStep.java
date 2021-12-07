//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.step;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.thread.*;

final class ListenerStep extends ModuleListener<Step, StepEvent>
{
    public ListenerStep(final Step module) {
        super(module, (Class<? super Object>)StepEvent.class);
    }
    
    public void invoke(final StepEvent event) {
        if (!Managers.NCP.passed(((Step)this.module).lagTime.getValue())) {
            ((Step)this.module).stepping = false;
            return;
        }
        if (event.getStage() == Stage.PRE) {
            if (ListenerStep.mc.player.getRidingEntity() != null) {
                ListenerStep.mc.player.getRidingEntity().stepHeight = (((Step)this.module).entityStep.getValue() ? 256.0f : 1.0f);
            }
            ((Step)this.module).y = event.getBB().minY;
            final Step step = (Step)this.module;
            final boolean canStep = ((Step)this.module).canStep();
            step.stepping = canStep;
            if (canStep) {
                event.setHeight(((Step)this.module).height.getValue());
            }
        }
        else {
            if (!((Step)this.module).vanilla.getValue()) {
                final double height = event.getBB().minY - ((Step)this.module).y;
                if (((Step)this.module).stepping && height > event.getHeight()) {
                    double[] offsets = { 0.42, (height < 1.0 && height > 0.8) ? 0.753 : 0.75, 1.0, 1.16, 1.23, 1.2 };
                    if (height >= 2.0) {
                        offsets = new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 };
                    }
                    for (int i = 0; i < ((height > 1.0) ? offsets.length : 2); ++i) {
                        ListenerStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerStep.mc.player.posX, ListenerStep.mc.player.posY + offsets[i], ListenerStep.mc.player.posZ, true));
                    }
                    if (((Step)this.module).autoOff.getValue()) {
                        ((Step)this.module).disable();
                    }
                }
            }
            if (((Step)this.module).gapple.getValue() && ((Step)this.module).stepping && !((Step)this.module).breakTimer.passed(60L) && InventoryUtil.isHolding(ItemPickaxe.class) && !InventoryUtil.isHolding(ItemAppleGold.class)) {
                final Entity closest = (Entity)EntityUtil.getClosestEnemy();
                if (closest != null && closest.getDistanceSqToEntity((Entity)ListenerStep.mc.player) < 144.0) {
                    final int slot = InventoryUtil.findHotbarItem(Items.GOLDEN_APPLE, new Item[0]);
                    if (slot != -1) {
                        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(slot));
                    }
                }
            }
        }
    }
}
