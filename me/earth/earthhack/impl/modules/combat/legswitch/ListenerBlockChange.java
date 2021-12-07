//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.legswitch.modes.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerBlockChange extends ModuleListener<LegSwitch, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final LegSwitch module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        final SPacketBlockChange p = event.getPacket();
        if (((LegSwitch)this.module).breakBlock.getValue() && (InventoryUtil.isHolding(Items.END_CRYSTAL) || ((LegSwitch)this.module).autoSwitch.getValue() != LegAutoSwitch.None) && (((LegSwitch)this.module).rotate.getValue() == ACRotate.None || ((LegSwitch)this.module).rotate.getValue() == ACRotate.Break) && ((LegSwitch)this.module).isValid(p.getBlockPosition(), p.getBlockState(), Managers.ENTITIES.getPlayers())) {
            event.addPostEvent((LegSwitch)this.module::startCalculation);
        }
    }
}
