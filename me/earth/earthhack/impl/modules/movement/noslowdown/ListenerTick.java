//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.item.*;

final class ListenerTick extends ModuleListener<NoSlowDown, TickEvent>
{
    public ListenerTick(final NoSlowDown module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        Managers.NCP.setStrict(((NoSlowDown)this.module).guiMove.getValue() && ((NoSlowDown)this.module).legit.getValue());
        if (event.isSafe() && ((NoSlowDown)this.module).legit.getValue() && ((NoSlowDown)this.module).items.getValue()) {
            final Item item = ListenerTick.mc.player.getActiveItemStack().getItem();
            if ((MovementUtil.isMoving() && item instanceof ItemFood) || item instanceof ItemBow || item instanceof ItemPotion) {
                ListenerTick.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, ListenerTick.mc.player.getPosition(), EnumFacing.DOWN));
            }
            if (!ListenerTick.mc.player.isHandActive() && Managers.ACTION.isSprinting() && ((NoSlowDown)this.module).sneakPacket.getValue()) {
                ListenerTick.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerTick.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (((IEntity)ListenerTick.mc.player).inWeb() && !ListenerTick.mc.player.onGround && ((NoSlowDown)this.module).useTimerWeb.getValue()) {
                Managers.TIMER.setTimer(((NoSlowDown)this.module).timerSpeed.getValue().floatValue());
            }
            else {
                Managers.TIMER.reset();
            }
        }
    }
}
