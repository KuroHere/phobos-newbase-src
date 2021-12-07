//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.events.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

final class ListenerDestroyBlock extends ModuleListener<AutoCrystal, BlockDestroyEvent>
{
    public ListenerDestroyBlock(final AutoCrystal module) {
        super(module, (Class<? super Object>)BlockDestroyEvent.class, -10);
    }
    
    public void invoke(final BlockDestroyEvent event) {
        if (((AutoCrystal)this.module).blockDestroyThread.getValue() && event.getStage() == Stage.PRE && ((AutoCrystal)this.module).multiThread.getValue() && !event.isCancelled() && !event.isUsed() && HelperUtil.validChange(event.getPos(), ListenerDestroyBlock.mc.world.playerEntities)) {
            ((AutoCrystal)this.module).threadHelper.startThread(event.getPos().down());
        }
    }
}
