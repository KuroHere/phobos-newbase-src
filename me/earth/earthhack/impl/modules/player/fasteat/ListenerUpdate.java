//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fasteat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.player.fasteat.mode.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

final class ListenerUpdate extends ModuleListener<FastEat, MotionUpdateEvent>
{
    public ListenerUpdate(final FastEat module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ((FastEat)this.module).mode.getValue() == FastEatMode.Update && ((FastEat)this.module).isValid(ListenerUpdate.mc.player.getActiveItemStack())) {
            EnumHand hand = ListenerUpdate.mc.player.getActiveHand();
            if (hand == null) {
                hand = (ListenerUpdate.mc.player.getHeldItemOffhand().equals(ListenerUpdate.mc.player.getActiveItemStack()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            }
            ListenerUpdate.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(hand));
        }
        else if (event.getStage() == Stage.POST && ((FastEat)this.module).mode.getValue() == FastEatMode.Packet && ((FastEat)this.module).isValid(ListenerUpdate.mc.player.getActiveItemStack()) && ListenerUpdate.mc.player.getItemInUseMaxCount() > ((FastEat)this.module).speed.getValue() - 1.0f && ((FastEat)this.module).speed.getValue() < 25.0f) {
            for (int i = 0; i < 32; ++i) {
                ListenerUpdate.mc.player.connection.sendPacket((Packet)new CPacketPlayer(ListenerUpdate.mc.player.onGround));
            }
            ListenerUpdate.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            ListenerUpdate.mc.player.stopActiveHand();
        }
    }
}
