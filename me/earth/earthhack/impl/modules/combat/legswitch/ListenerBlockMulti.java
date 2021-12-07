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
import java.util.*;
import net.minecraft.entity.player.*;

final class ListenerBlockMulti extends ModuleListener<LegSwitch, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerBlockMulti(final LegSwitch module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        final SPacketMultiBlockChange p = event.getPacket();
        if (((LegSwitch)this.module).breakBlock.getValue() && (InventoryUtil.isHolding(Items.END_CRYSTAL) || ((LegSwitch)this.module).autoSwitch.getValue() != LegAutoSwitch.None) && (((LegSwitch)this.module).rotate.getValue() == ACRotate.None || ((LegSwitch)this.module).rotate.getValue() == ACRotate.Break)) {
            final List<EntityPlayer> players = Managers.ENTITIES.getPlayers();
            for (final SPacketMultiBlockChange.BlockUpdateData d : p.getChangedBlocks()) {
                if (((LegSwitch)this.module).isValid(d.getPos(), d.getBlockState(), players)) {
                    event.addPostEvent((LegSwitch)this.module::startCalculation);
                    return;
                }
            }
        }
    }
}
