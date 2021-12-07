//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.exptweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;

final class ListenerUseItem extends ModuleListener<ExpTweaks, PacketEvent.Send<CPacketPlayerTryUseItem>>
{
    private boolean sending;
    
    public ListenerUseItem(final ExpTweaks module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerTryUseItem.class);
        this.sending = false;
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItem> event) {
        final CPacketPlayerTryUseItem p = event.getPacket();
        if (this.sending || event.isCancelled() || ListenerUseItem.mc.player.getHeldItem(p.getHand()).getItem() != Items.EXPERIENCE_BOTTLE) {
            return;
        }
        if (((ExpTweaks)this.module).wasteStop.getValue() && ((ExpTweaks)this.module).isWasting()) {
            event.setCancelled(true);
            ((ExpTweaks)this.module).justCancelled = true;
            return;
        }
        final int packets = ((ExpTweaks)this.module).isMiddleClick ? ((ExpTweaks)this.module).mcePackets.getValue() : ((int)((ExpTweaks)this.module).expPackets.getValue());
        if (packets != 0 && (((ExpTweaks)this.module).packetsInLoot.getValue() || ListenerUseItem.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty())) {
            for (int i = 0; i < packets; ++i) {
                this.sending = true;
                NetworkUtil.send((Packet<?>)new CPacketPlayerTryUseItem(p.getHand()));
                this.sending = false;
            }
        }
    }
}
