//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Redirect(method = { "onPlayerDestroyBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"))
    private boolean setBlockStateHook(final World world, final BlockPos pos, final IBlockState newState, final int flags) {
        final BlockDestroyEvent event = new BlockDestroyEvent(Stage.PRE, pos);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            return false;
        }
        final boolean result = world.setBlockState(pos, newState, flags);
        if (result) {
            Bus.EVENT_BUS.post(new BlockDestroyEvent(Stage.POST, pos));
        }
        return result;
    }
}
