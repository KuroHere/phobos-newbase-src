//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.item.*;

final class ListenerRightClickItem extends ModuleListener<NoSlowDown, RightClickItemEvent>
{
    public ListenerRightClickItem(final NoSlowDown module) {
        super(module, (Class<? super Object>)RightClickItemEvent.class);
    }
    
    public void invoke(final RightClickItemEvent event) {
        final Item item = ListenerRightClickItem.mc.player.getHeldItem(event.getHand()).getItem();
        if (!((NoSlowDown)this.module).sneakPacket.getValue() || (!(item instanceof ItemFood) && !(item instanceof ItemBow) && !(item instanceof ItemPotion)) || !Managers.ACTION.isSneaking()) {}
    }
}
