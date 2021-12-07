//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.spammer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerUpdate extends ModuleListener<Spammer, UpdateEvent>
{
    public ListenerUpdate(final Spammer module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (((Spammer)this.module).timer.passed(((Spammer)this.module).delay.getValue() * 1000)) {
            ListenerUpdate.mc.player.sendChatMessage((((Spammer)this.module).greenText.getValue() ? ">" : "") + ((Spammer)this.module).getSuffixedMessage());
            if (((Spammer)this.module).autoOff.getValue()) {
                ((Spammer)this.module).disable();
            }
            ((Spammer)this.module).timer.reset();
        }
    }
}
