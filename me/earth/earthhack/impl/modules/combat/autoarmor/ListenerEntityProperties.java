//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import java.util.*;

final class ListenerEntityProperties extends ModuleListener<AutoArmor, PacketEvent.Receive<SPacketEntityProperties>>
{
    public ListenerEntityProperties(final AutoArmor module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityProperties.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityProperties> event) {
        final EntityPlayerSP player = ListenerEntityProperties.mc.player;
        if (player != null && event.getPacket().getEntityId() == player.getEntityId()) {
            for (final SPacketEntityProperties.Snapshot snapShot : event.getPacket().getSnapshots()) {
                if (snapShot.getName().equals(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName())) {
                    ((AutoArmor)this.module).propertyTimer.reset();
                    break;
                }
            }
        }
    }
}
