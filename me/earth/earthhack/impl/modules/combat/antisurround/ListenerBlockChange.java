//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerBlockChange extends ModuleListener<AntiSurround, PacketEvent.Post<SPacketBlockChange>>
{
    public ListenerBlockChange(final AntiSurround module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Post<SPacketBlockChange> event) {
        if (!((AntiSurround)this.module).async.getValue() || ((AntiSurround)this.module).active.get() || ListenerBlockChange.mc.player == null || ((AntiSurround)this.module).holdingCheck()) {
            return;
        }
        if (event.getPacket().getBlockState().getMaterial().isReplaceable()) {
            ((AntiSurround)this.module).onBlockBreak(event.getPacket().getBlockPosition(), Managers.ENTITIES.getPlayers(), Managers.ENTITIES.getEntities());
        }
    }
}
