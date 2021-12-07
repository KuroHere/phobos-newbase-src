//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

final class ListenerRightClick extends ModuleListener<Offhand, ClickBlockEvent.Right>
{
    public ListenerRightClick(final Offhand module) {
        super(module, (Class<? super Object>)ClickBlockEvent.Right.class);
    }
    
    public void invoke(final ClickBlockEvent.Right event) {
        if (((Offhand)this.module).noOGC.getValue() && event.getHand() == EnumHand.MAIN_HAND) {
            final Item mainHand = ListenerRightClick.mc.player.getHeldItemMainhand().getItem();
            final Item offHand = ListenerRightClick.mc.player.getHeldItemOffhand().getItem();
            if (mainHand == Items.END_CRYSTAL && offHand == Items.GOLDEN_APPLE && event.getHand() == EnumHand.MAIN_HAND) {
                event.setCancelled(true);
                ListenerRightClick.mc.player.setActiveHand(EnumHand.OFF_HAND);
                ListenerRightClick.mc.playerController.processRightClick((EntityPlayer)ListenerRightClick.mc.player, (World)ListenerRightClick.mc.world, EnumHand.OFF_HAND);
            }
        }
    }
}
