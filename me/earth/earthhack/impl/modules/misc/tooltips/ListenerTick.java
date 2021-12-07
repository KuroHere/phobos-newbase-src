//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.core.mixins.gui.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.misc.tooltips.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;

final class ListenerTick extends ModuleListener<ToolTips, TickEvent>
{
    public ListenerTick(final ToolTips module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe()) {
            if (ListenerTick.mc.currentScreen instanceof IGuiContainer && KeyBoardUtil.isKeyDown(((ToolTips)this.module).peekBind)) {
                final Slot slot = ((IGuiContainer)ListenerTick.mc.currentScreen).getHoveredSlot();
                if (slot != null) {
                    final ItemStack stack = slot.getStack();
                    if (stack.getItem() instanceof ItemShulkerBox) {
                        ((ToolTips)this.module).displayInventory(stack, null);
                    }
                }
            }
            if (((ToolTips)this.module).shulkerSpy.getValue()) {
                for (final EntityPlayer player : ListenerTick.mc.world.playerEntities) {
                    if (player != null && player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox && !PlayerUtil.isFakePlayer((Entity)player) && (((ToolTips)this.module).own.getValue() || !ListenerTick.mc.player.equals((Object)player))) {
                        final ItemStack stack2 = player.getHeldItemMainhand();
                        ((ToolTips)this.module).spiedPlayers.put(player.getName().toLowerCase(), new TimeStack(stack2, System.nanoTime()));
                    }
                }
            }
        }
    }
}
