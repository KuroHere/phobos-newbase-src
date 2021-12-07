//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.modules.player.blocktweaks.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ItemEndCrystal.class })
public abstract class MixinItemEnderCrystal
{
    private static final ModuleCache<Freecam> FREECAM;
    private static final ModuleCache<BlockTweaks> BLOCK_TWEAKS;
    
    @Redirect(method = { "onItemUse" }, at = @At(value = "NEW", target = "net/minecraft/util/math/AxisAlignedBB"))
    private AxisAlignedBB newBBHook(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        if (MixinItemEnderCrystal.BLOCK_TWEAKS.returnIfPresent(BlockTweaks::areNewVerEntitiesActive, false)) {
            return new AxisAlignedBB(x1, y1, z1, x2, y2 - 1.0, z2);
        }
        return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }
    
    @Redirect(method = { "onItemUse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesWithinAABBExcludingEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;", remap = false))
    private List<Entity> getEntitiesWithinAABBExcludingEntityHook(final World world, final Entity entityIn, final AxisAlignedBB bb) {
        final List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entityIn, bb);
        if (!MixinItemEnderCrystal.FREECAM.isEnabled()) {
            return entities;
        }
        final Entity player = (Entity)Minecraft.getMinecraft().player;
        if (player == null) {
            return entities;
        }
        for (final Entity entity : entities) {
            if (player.equals((Object)entity)) {
                continue;
            }
            return entities;
        }
        return new ArrayList<Entity>(0);
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
        BLOCK_TWEAKS = Caches.getModule(BlockTweaks.class);
    }
}
