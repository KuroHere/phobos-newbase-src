//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;
import net.minecraft.item.*;

final class ListenerEat extends ModuleListener<Announcer, EatEvent>
{
    public ListenerEat(final Announcer module) {
        super(module, (Class<? super Object>)EatEvent.class);
    }
    
    public void invoke(final EatEvent event) {
        if (((Announcer)this.module).eat.getValue() && event.getEntity().equals((Object)ListenerEat.mc.player)) {
            final ItemStack stack = event.getStack();
            ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Eat, stack.getDisplayName());
        }
    }
}
