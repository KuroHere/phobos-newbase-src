//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;

final class ListenerPostPlace extends ModuleListener<AutoCrystal, PacketEvent.Post<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerPostPlace(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItemOnBlock> event) {
        if (((AutoCrystal)this.module).idPredict.getValue() && !((AutoCrystal)this.module).noGod && ((AutoCrystal)this.module).breakTimer.passed(((AutoCrystal)this.module).breakDelay.getValue()) && ListenerPostPlace.mc.player.getHeldItem(event.getPacket().getHand()).getItem() == Items.END_CRYSTAL && ((AutoCrystal)this.module).idHelper.isSafe(Managers.ENTITIES.getPlayersAsync(), ((AutoCrystal)this.module).holdingCheck.getValue(), ((AutoCrystal)this.module).toolCheck.getValue())) {
            ((AutoCrystal)this.module).idHelper.attack(((AutoCrystal)this.module).breakSwing.getValue(), ((AutoCrystal)this.module).godSwing.getValue(), ((AutoCrystal)this.module).idOffset.getValue(), ((AutoCrystal)this.module).idPackets.getValue(), ((AutoCrystal)this.module).idDelay.getValue());
            ((AutoCrystal)this.module).breakTimer.reset(((AutoCrystal)this.module).breakDelay.getValue());
        }
    }
}
