//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.icespeed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.init.*;

final class ListenerTick extends ModuleListener<IceSpeed, TickEvent>
{
    public ListenerTick(final IceSpeed module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        Blocks.ICE.slipperiness = ((IceSpeed)this.module).speed.getValue();
        Blocks.PACKED_ICE.slipperiness = ((IceSpeed)this.module).speed.getValue();
        Blocks.FROSTED_ICE.slipperiness = ((IceSpeed)this.module).speed.getValue();
    }
}
