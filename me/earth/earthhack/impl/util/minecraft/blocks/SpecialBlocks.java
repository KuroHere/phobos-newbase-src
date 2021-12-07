//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class SpecialBlocks implements Globals
{
    public static final Set<Block> BAD_BLOCKS;
    public static final Set<Block> SHULKERS;
    public static final Set<Block> PRESSURE_PLATES;
    public static final Predicate<Packet<?>> PACKETCHECK;
    public static final BiPredicate<Packet<?>, IBlockAccess> ACCESS_CHECK;
    
    public static boolean shouldSneak(final BlockPos pos, final boolean manager) {
        return shouldSneak(pos, (IBlockAccess)SpecialBlocks.mc.world, manager);
    }
    
    public static boolean shouldSneak(final BlockPos pos, final IBlockAccess provider, final boolean manager) {
        return shouldSneak(provider.getBlockState(pos).getBlock(), manager);
    }
    
    public static boolean shouldSneak(final Block block, final boolean manager) {
        return (!manager || !Managers.ACTION.isSneaking()) && (SpecialBlocks.BAD_BLOCKS.contains(block) || SpecialBlocks.SHULKERS.contains(block));
    }
    
    static {
        BAD_BLOCKS = Sets.newHashSet((Object[])new Block[] { Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE });
        SHULKERS = Sets.newHashSet((Object[])new Block[] { Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
        PRESSURE_PLATES = Sets.newHashSet((Object[])new Block[] { Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE });
        PACKETCHECK = (p -> p instanceof CPacketPlayerTryUseItemOnBlock && shouldSneak(((CPacketPlayerTryUseItemOnBlock)p).getPos(), false));
        ACCESS_CHECK = ((p, b) -> p instanceof CPacketPlayerTryUseItemOnBlock && shouldSneak(((CPacketPlayerTryUseItemOnBlock)p).getPos(), b, false));
    }
}
