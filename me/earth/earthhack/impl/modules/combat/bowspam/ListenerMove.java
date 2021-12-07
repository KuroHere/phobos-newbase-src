//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowspam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.item.*;

final class ListenerMove extends ModuleListener<BowSpam, MoveEvent>
{
    private float lastTimer;
    
    public ListenerMove(final BowSpam module) {
        super(module, (Class<? super Object>)MoveEvent.class);
        this.lastTimer = -1.0f;
    }
    
    public void invoke(final MoveEvent event) {
        final ItemStack stack = this.getStack();
        if (((BowSpam)this.module).rape.getValue() && ListenerMove.mc.player.onGround && stack != null && !ListenerMove.mc.player.getActiveItemStack().func_190926_b() && ListenerMove.mc.player.getItemInUseCount() > 0) {
            event.setX(0.0);
            event.setY(0.0);
            event.setZ(0.0);
            ListenerMove.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
    }
    
    private ItemStack getStack() {
        final ItemStack mainHand = ListenerMove.mc.player.getHeldItemMainhand();
        if (mainHand.getItem() instanceof ItemBow) {
            return mainHand;
        }
        final ItemStack offHand = ListenerMove.mc.player.getHeldItemOffhand();
        if (offHand.getItem() instanceof ItemBow) {
            return offHand;
        }
        return null;
    }
}
