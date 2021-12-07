//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.autotool;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerDamageBlock extends ModuleListener<AutoTool, DamageBlockEvent>
{
    private static final ModuleCache<Speedmine> SPEED_MINE;
    
    public ListenerDamageBlock(final AutoTool module) {
        super(module, (Class<? super Object>)DamageBlockEvent.class);
    }
    
    public void invoke(final DamageBlockEvent event) {
        if (MineUtil.canBreak(event.getPos()) && !ListenerDamageBlock.mc.player.isCreative() && ListenerDamageBlock.mc.gameSettings.keyBindAttack.isKeyDown() && (!ListenerDamageBlock.SPEED_MINE.isPresent() || !ListenerDamageBlock.SPEED_MINE.isEnabled() || ListenerDamageBlock.SPEED_MINE.get().getMode() == MineMode.Damage || ListenerDamageBlock.SPEED_MINE.get().getMode() == MineMode.Reset)) {
            final int slot = MineUtil.findBestTool(event.getPos());
            if (slot != -1) {
                if (!((AutoTool)this.module).set) {
                    ((AutoTool)this.module).lastSlot = ListenerDamageBlock.mc.player.inventory.currentItem;
                    ((AutoTool)this.module).set = true;
                }
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(slot));
            }
        }
        else if (((AutoTool)this.module).set) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(((AutoTool)this.module).lastSlot));
            ((AutoTool)this.module).reset();
        }
    }
    
    static {
        SPEED_MINE = Caches.getModule(Speedmine.class);
    }
}
