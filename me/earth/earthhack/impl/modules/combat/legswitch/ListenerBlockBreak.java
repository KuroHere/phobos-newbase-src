//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

final class ListenerBlockBreak extends ModuleListener<LegSwitch, BlockDestroyEvent>
{
    private final BlockStateHelper helper;
    
    public ListenerBlockBreak(final LegSwitch module) {
        super(module, (Class<? super Object>)BlockDestroyEvent.class, 11);
        this.helper = new BlockStateHelper();
    }
    
    public void invoke(final BlockDestroyEvent event) {
        if (!((LegSwitch)this.module).breakBlock.getValue() || event.isCancelled() || event.isUsed() || event.getStage() != Stage.PRE) {
            return;
        }
        event.setUsed(true);
        this.helper.addBlockState(event.getPos(), Blocks.AIR.getDefaultState());
        ((LegSwitch)this.module).startCalculation((IBlockAccess)this.helper);
        this.helper.delete(event.getPos());
    }
}
