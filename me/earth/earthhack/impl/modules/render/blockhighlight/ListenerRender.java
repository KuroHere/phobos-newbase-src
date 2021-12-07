//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.blockhighlight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerRender extends ModuleListener<BlockHighlight, Render3DEvent>
{
    private static final ModuleCache<Speedmine> SPEED_MINE;
    
    public ListenerRender(final BlockHighlight module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (ListenerRender.mc.objectMouseOver != null && ListenerRender.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos pos = ListenerRender.mc.objectMouseOver.getBlockPos();
            if (ListenerRender.mc.world.getWorldBorder().contains(pos) && (!ListenerRender.SPEED_MINE.isEnabled() || !pos.equals((Object)ListenerRender.SPEED_MINE.get().getPos()))) {
                final IBlockState state = ListenerRender.mc.world.getBlockState(pos);
                if (state.getMaterial() != Material.AIR) {
                    final AxisAlignedBB bb = Interpolation.interpolateAxis(state.getSelectedBoundingBox((World)ListenerRender.mc.world, pos).expandXyz(0.0020000000949949026));
                    ((BlockHighlight)this.module).renderInterpAxis(bb);
                }
            }
        }
    }
    
    static {
        SPEED_MINE = Caches.getModule(Speedmine.class);
    }
}
