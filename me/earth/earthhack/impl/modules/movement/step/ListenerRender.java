//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.step;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

final class ListenerRender extends ModuleListener<Step, Render3DEvent>
{
    public ListenerRender(final Step module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final StepESP esp = ((Step)this.module).esp.getValue();
        if (esp != StepESP.None) {
            final BlockPos pos = PositionUtil.getPosition((Entity)ListenerRender.mc.player, 1.0);
            final BlockPos up2 = pos.up(2);
            if (ListenerRender.mc.world.getBlockState(up2).getMaterial().blocksMovement()) {
                if (esp == StepESP.Good) {
                    return;
                }
                ((Step)this.module).renderPos(up2);
            }
            for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                BlockPos off = pos.offset(facing);
                if (ListenerRender.mc.world.getBlockState(off).getMaterial().blocksMovement()) {
                    off = off.up();
                    final IBlockState state = ListenerRender.mc.world.getBlockState(off);
                    if (state.getMaterial().blocksMovement() && state.getBoundingBox((IBlockAccess)ListenerRender.mc.world, off) == Block.FULL_BLOCK_AABB) {
                        if (esp == StepESP.Bad) {
                            ((Step)this.module).renderPos(off);
                        }
                    }
                    else {
                        final IBlockState up3 = ListenerRender.mc.world.getBlockState(off.up());
                        if (up3.getMaterial().blocksMovement()) {
                            if (esp == StepESP.Bad) {
                                ((Step)this.module).renderPos(off);
                            }
                        }
                        else if (esp == StepESP.Good) {
                            ((Step)this.module).renderPos(off);
                        }
                    }
                }
            }
        }
    }
}
