//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.combat.antisurround.util.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerDigging extends ModuleListener<AntiSurround, PacketEvent.Send<CPacketPlayerDigging>>
{
    private final AntiSurroundFunction function;
    
    public ListenerDigging(final AntiSurround module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, -1000, CPacketPlayerDigging.class);
        this.function = new PreCrystalFunction(module);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerDigging> event) {
        if (event.isCancelled() || event.getPacket().getAction() != CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK || ((AntiSurround)this.module).holdingCheck() || !((AntiSurround)this.module).preCrystal.getValue() || !event.getPacket().isClientSideBreaking()) {
            return;
        }
        ((AntiSurround)this.module).onBlockBreak(event.getPacket().getPosition(), Managers.ENTITIES.getPlayersAsync(), Managers.ENTITIES.getEntitiesAsync(), this.function);
    }
}
