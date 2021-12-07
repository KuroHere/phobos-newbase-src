//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

final class ListenerBlockRender extends ModuleListener<Search, BlockRenderEvent>
{
    public ListenerBlockRender(final Search module) {
        super(module, (Class<? super Object>)BlockRenderEvent.class);
    }
    
    public void invoke(final BlockRenderEvent event) {
        if (((Search)this.module).toRender.size() >= 100000) {
            ((Search)this.module).toRender.clear();
        }
        final BlockPos mut = event.getPos();
        final Block block = event.getState().getBlock();
        if (ListenerBlockRender.mc.player.getDistanceSq(mut) <= 65536.0 && block != Blocks.AIR && ((Search)this.module).isValid(block.getLocalizedName())) {
            final BlockPos pos = mut.toImmutable();
            final IBlockState state = event.getState();
            final AxisAlignedBB bb = state.getBoundingBox((IBlockAccess)ListenerBlockRender.mc.world, pos).offset((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            final int stateColor = ((Search)this.module).getColor(state);
            final float r = (stateColor >> 24 & 0xFF) / 255.0f;
            final float g = (stateColor >> 16 & 0xFF) / 255.0f;
            final float b = (stateColor >> 8 & 0xFF) / 255.0f;
            final float a = (stateColor & 0xFF) / 255.0f;
            ((Search)this.module).toRender.put(pos, new SearchResult(pos, bb, r, g, b, a));
        }
    }
}
