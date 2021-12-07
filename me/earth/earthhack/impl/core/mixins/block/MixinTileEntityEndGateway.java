//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TileEntityEndGateway.class })
public abstract class MixinTileEntityEndGateway extends TileEntityEndPortal
{
    private static final Minecraft MC;
    
    @Redirect(method = { "shouldRenderFace" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntityEndGateway;getBlockType()Lnet/minecraft/block/Block;"))
    private Block shouldRenderFaceHook(final TileEntityEndGateway tileEntityEndGateway) {
        Block block = this.getBlockType();
        if (block != null) {
            return block;
        }
        if (this.world != null || MixinTileEntityEndGateway.MC.world == null) {
            return Blocks.END_GATEWAY;
        }
        this.setWorldObj((World)MixinTileEntityEndGateway.MC.world);
        block = MixinTileEntityEndGateway.MC.world.getBlockState(this.getPos()).getBlock();
        if (block == null) {
            Earthhack.getLogger().warn("EndGateway still null!");
            return Blocks.END_GATEWAY;
        }
        return block;
    }
    
    static {
        MC = Minecraft.getMinecraft();
    }
}
