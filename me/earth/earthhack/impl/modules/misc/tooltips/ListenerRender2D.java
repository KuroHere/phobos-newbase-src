//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.misc.tooltips.util.*;
import java.util.*;

final class ListenerRender2D extends ModuleListener<ToolTips, Render2DEvent>
{
    public ListenerRender2D(final ToolTips module) {
        super(module, (Class<? super Object>)Render2DEvent.class);
    }
    
    public void invoke(final Render2DEvent event) {
        final int x = 1;
        int y = (int)(Managers.TEXT.getStringHeight() + 4.0f);
        for (final EntityPlayer player : ListenerRender2D.mc.world.playerEntities) {
            if (player != null && !EntityUtil.isDead((Entity)player)) {
                final TimeStack stack = ((ToolTips)this.module).spiedPlayers.get(player.getName().toLowerCase());
                if (stack == null || (!player.getHeldItemMainhand().equals(stack.getStack()) && System.nanoTime() - stack.getTime() >= 2000000000L)) {
                    continue;
                }
                if (!((ToolTips)this.module).drawShulkerToolTip(stack.getStack(), x, y, player.getName())) {
                    ((ToolTips)this.module).spiedPlayers.remove(player.getName().toLowerCase());
                }
                else {
                    y += 79;
                }
            }
        }
    }
}
