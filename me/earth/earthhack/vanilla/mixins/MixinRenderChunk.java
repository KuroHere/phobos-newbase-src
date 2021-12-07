//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderChunk.class })
public abstract class MixinRenderChunk
{
    @Redirect(method = { "rebuildChunk" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderLayer()Lnet/minecraft/util/BlockRenderLayer;"))
    private BlockRenderLayer getRenderLayerHook(final Block block) {
        final BlockLayerEvent event = new BlockLayerEvent(block);
        Bus.EVENT_BUS.post(event);
        if (event.getLayer() != null) {
            return event.getLayer();
        }
        return block.getBlockLayer();
    }
}
